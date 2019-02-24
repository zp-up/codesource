package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AddressBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.AddressUpdateEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.orderBeans.OrderGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.ConfirmOrderImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.ConfirmOrderInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnConfirmDataCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.WalletDialog;

public class ConfirmOrderActivity extends BaseAppcompatActivity implements OnConfirmDataCallback {
    private String orderId = "-1";
    private ConfirmOrderInterface confirmPresenter;
    private OrderRootBean orderRootBean;
    @BindView(R.id.ll_address_container)
    LinearLayout llAddressContainer;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address_phone)
    TextView tvAddressPhone;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.ll_goods_list_container)
    LinearLayout llGoodsListContainer;
    @BindView(R.id.tv_goods_total_price)
    TextView tvGoodsTotalPrice;
    @BindView(R.id.tv_banded_price)
    TextView tvBondedPrice;
    @BindView(R.id.tv_pay_total)
    TextView tvPayTotal;
    @BindView(R.id.tv_post_price)
    TextView tvPostPrice;
    @BindView(R.id.rl_wallet_add)
    RelativeLayout rlWalletAdd;
    @BindView(R.id.tv_activity_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_to_true_name)
    TextView tvToTrueName;
    private int walletMoney = 0;
    private int setMoney = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        confirmPresenter = new ConfirmOrderImp();
        if (getIntent().getStringExtra("id") != null) {
            orderId = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!"-1".equals(orderId)) {
            initData();
        }
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getOrderDetailByIdUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId);
        confirmPresenter.getOrderInfo(params, this);

        RequestParams params1 = new RequestParams(MainUrls.getUserWalletMoneyUrl);
        params1.addBodyParameter("access_token", IPSCApplication.accessToken);
        params1.addBodyParameter("order", orderId);
        confirmPresenter.getWalletData(params1, this);
    }

    @OnClick({R.id.iv_back, R.id.ll_address_container, R.id.tv_to_pay, R.id.rl_wallet_add, R.id.tv_to_true_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ConfirmOrderActivity.this.finish();
                break;
            case R.id.ll_address_container:
                Intent intent = new Intent(ConfirmOrderActivity.this, ReceivedAddressListActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            case R.id.tv_to_pay:
                Intent intent2 = new Intent(ConfirmOrderActivity.this, PayWayActivity.class);
                intent2.putExtra("oderId", orderId);
                startActivity(intent2);
                //checkOrderInfo();
                break;
            case R.id.rl_wallet_add:
                if (walletMoney <= 0) {
                    ToastUtils.show(ConfirmOrderActivity.this, "暂无可用零钱");
                    return;
                }
                final WalletDialog dialog = new WalletDialog(ConfirmOrderActivity.this);
                dialog.showDialog(new WalletDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onProgress(int progress) {
                        setWalletToOrder(progress);
                    }
                }, walletMoney);
                break;
            case R.id.tv_to_true_name:
                Intent intent1 = new Intent(ConfirmOrderActivity.this, RealNameAuthenticationActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void checkOrderInfo() {
        Log.e("TAG", "点击了");
        RequestParams params = new RequestParams(MainUrls.getPayInfoUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId);
        confirmPresenter.checkPayInfo(params, this);
    }

    private void setWalletToOrder(int progress) {
        setMoney = progress;
        RequestParams params = new RequestParams(MainUrls.setWalletMoneyToOrderUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("order", orderId);
        params.addBodyParameter("money", progress + "");
        confirmPresenter.setWalletMoneyToOrder(params, this);
    }

    @Subscribe
    public void onSub(AddressUpdateEvent event) {
        Log.e("TAG", "hah");
        if (event != null) {
            Log.e("TAG", "hah:" + event.getOp());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData();
                }
            }, 1000);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取数据中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "ConfirmOrderActivity->访问网络错误:" + error);
    }

    @Override
    public void onOrderInfoLoaded(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            if (code == 0 && state == 0) {
                JSONObject data = jsonObject.getJSONObject("data");
                if (data.has("error")) {
                    String error = data.getString("error");
                    if ("请添加实名认证".equals(error)) {
                        tvToTrueName.setVisibility(View.VISIBLE);
                    } else {
                        tvToTrueName.setVisibility(View.GONE);
                    }
                }
                orderRootBean = new OrderRootBean();
                orderRootBean.setId(data.getInt("id"));
                orderRootBean.setCreate_time(data.getString("create_time"));
                orderRootBean.setAfterSaleState(data.getString("asse"));
                orderRootBean.setOrderNumber(data.getString("sn"));
                orderRootBean.setPay_total(data.getDouble("pay_total"));
                orderRootBean.setRefund_goods(data.has("refund_goods") ? data.getString("refund_goods") : "无退货");
                orderRootBean.setRefund_goods_time(data.has("refund_goods_time") ? data.getLong("refund_goods_time") : 0);
                orderRootBean.setRefund_state(data.has("refund_state") ? data.getString("refund_state") : "无退货");
                orderRootBean.setRefund_time(data.has("refund_time") ? data.getLong("refund_time") : 0);
                orderRootBean.setStatus(data.getString("status"));
                orderRootBean.setStoreType(data.getJSONObject("store").getString("type"));
                orderRootBean.setPost(data.getString("post"));
                orderRootBean.setTotal_tax(data.getDouble("total_tax"));
                orderRootBean.setTotal_goods(data.getDouble("total_goods"));
                orderRootBean.setTotal_total(data.getDouble("total_total"));
                orderRootBean.setPostPrice(data.getDouble("total_post"));
                orderRootBean.setWeight(data.getDouble("weight"));
                List<OrderGoodsBean> orderGoodsBeans = new ArrayList<>();
                if (data.has("order_orderlist") && data.getJSONArray("order_orderlist") != null && data.getJSONArray("order_orderlist").length() != 0) {
                    JSONArray list = data.getJSONArray("order_orderlist");
                    for (int j = 0; j < list.length(); j++) {
                        OrderGoodsBean orderGoodsBean = new OrderGoodsBean();
                        orderGoodsBean.setGoodsId(list.getJSONObject(j).getJSONObject("goods_goods").getInt("id"));
                        orderGoodsBean.setGoodsName(list.getJSONObject(j).getJSONObject("goods_goods").getString("name"));
                        orderGoodsBean.setGoodsDescription(list.getJSONObject(j).getJSONObject("goods_goods").getString("spec"));
                        orderGoodsBean.setImageUrl(list.getJSONObject(j).getJSONObject("goods_goods").getJSONArray("img").getString(0));
                        orderGoodsBean.setCount(list.getJSONObject(j).getInt("number"));
                        orderGoodsBean.setGoodsPrice(list.getJSONObject(j).getDouble("price"));
                        orderGoodsBean.setBondedPrice(list.getJSONObject(j).getDouble("total_tax"));
                        orderGoodsBeans.add(orderGoodsBean);
                    }
                }
                if (data.has("address") && data.getJSONObject("address") != null) {
                    AddressBean addressBean = new AddressBean();
                    addressBean.setId(data.getJSONObject("address").getInt("id"));
                    addressBean.setReceiveName(data.getJSONObject("address").getString("name"));
                    addressBean.setReceivePhone(data.getJSONObject("address").getString("telphone"));
                    addressBean.setDetailPlace(data.getJSONObject("address").getString("address"));
                    addressBean.setProvince(data.getJSONObject("address").getString("province"));
                    addressBean.setCity(data.getJSONObject("address").getString("city"));
                    addressBean.setArea(data.getJSONObject("address").getString("area"));
                    orderRootBean.setAddressBean(addressBean);
                }
                orderRootBean.setGoodsBeans(orderGoodsBeans);
            }
            bindDataToView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWalletDataLoaded(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            if (code == 0 && state == 0) {
                JSONObject data = jsonObject.getJSONObject("data");
                walletMoney = (int) Math.floor(data.getDouble("money"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWalletSetSuccessed(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            if (code == 0 && state == 0) {
                tvDiscount.setText("-￥" + setMoney);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckInfoCallBack(String result) {

        try {
            Log.e("TAG", "checkInfo:" + result);
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "提交订单失败";
            if (code == 0 && state == 0) {

            } else {
                ToastUtils.show(ConfirmOrderActivity.this, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindDataToView() {
        llAddressContainer.setVisibility(View.VISIBLE);
        tvAddressName.setText(orderRootBean.getAddressBean().getReceiveName());
        tvAddressPhone.setText(orderRootBean.getAddressBean().getReceivePhone());
        tvAddressDetail.setText(orderRootBean.getAddressBean().getProvince()
                + orderRootBean.getAddressBean().getCity() + orderRootBean.getAddressBean().getArea()
                + orderRootBean.getAddressBean().getDetailPlace());
        llGoodsListContainer.removeAllViews();
        for (int i = 0; i < orderRootBean.getGoodsBeans().size(); i++) {
            View view = LayoutInflater.from(ConfirmOrderActivity.this).inflate(R.layout.item_confirm_activity_goods, null);
            ImageView ivGoodsPic = view.findViewById(R.id.iv_goods_pic);
            Glide.with(ConfirmOrderActivity.this).load(orderRootBean.getGoodsBeans()
                    .get(i).getImageUrl()).apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
            TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
            tvGoodsName.setText(orderRootBean.getGoodsBeans().get(i).getGoodsName());
            TextView tvGoodsSpec = view.findViewById(R.id.tv_goods_spec);
            tvGoodsSpec.setText(orderRootBean.getGoodsBeans().get(i).getGoodsDescription());
            TextView tvGoodsPrice = view.findViewById(R.id.tv_goods_price);
            tvGoodsPrice.setText("￥" + orderRootBean.getGoodsBeans().get(i).getGoodsPrice());
            TextView tvBandedPrice = view.findViewById(R.id.tv_banded_price);
            tvBandedPrice.setText("税费:￥" + orderRootBean.getGoodsBeans().get(i).getBondedPrice());
            TextView tvCount = view.findViewById(R.id.tv_goods_count);
            tvCount.setText("x" + orderRootBean.getGoodsBeans().get(i).getCount());
            llGoodsListContainer.addView(view);
        }
        tvGoodsTotalPrice.setText("￥" + orderRootBean.getTotal_goods());
        tvBondedPrice.setText("￥" + orderRootBean.getTotal_tax());
        tvPayTotal.setText("应付:￥" + orderRootBean.getPay_total());
        tvPostPrice.setText("￥" + orderRootBean.getPostPrice());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
