package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.orderBeans.OrderGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class OrderDetailActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {

    private static final String TAG = "[IPSC][OrderDetailActivity]";
    private int orderId = -1;
    private CommonDataInterface commonPresenter;
    private OrderRootBean orderRootBean;
    @BindView(R.id.ll_address_container)
    LinearLayout llAddressContainer;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address_phone)
    TextView tvAddressPhone;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
//    @BindView(R.id.tv_status)
//    TextView tvStatus;
    @BindView(R.id.tv_store_type)
    TextView tvStoreType;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.tv_goods_weight)
    TextView tvGoodsWeight;
    @BindView(R.id.ll_goods_list_container)
    LinearLayout llGoodsListContainer;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_goods_total_price)
    TextView tvGoodsTotalPrice;
    @BindView(R.id.tv_banded_price)
    TextView tvBondedPrice;
    @BindView(R.id.tv_post_type)
    TextView tvPostType;
    @BindView(R.id.tv_pay_total)
    TextView tvPayTotal;
    @BindView(R.id.tv_post_price)
    TextView tvPostPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        commonPresenter = new CommonGoodsImp();
        Intent intent = getIntent();
        if (intent.getIntExtra("orderId", -1) != -1) {
            orderId = intent.getIntExtra("orderId", -1);
            Log.e(TAG, "订单id：" + orderId);
        }
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getOrderDetailByIdUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        commonPresenter.getCommonGoodsData(params, this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_order_detail;
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                OrderDetailActivity.this.finish();
                break;
        }
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
        Log.e(TAG, "获取订单信息出错:" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        LogUtil.d(TAG, "onCommonGoodsCallBack() 订单信息:" + result);
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
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
                    if (data.has("store") && data.get("store") != null &&
                            data.get("store").toString() != "null" && data.getJSONObject("store") != null) {
                        AddressBean addressBean = new AddressBean();
                        addressBean.setId(data.getJSONObject("store").getInt("id"));
                        addressBean.setReceiveName(data.getJSONObject("store").getString("name"));
                        addressBean.setReceivePhone(data.getJSONObject("store").getString("telphone"));
                        addressBean.setDetailPlace(data.getJSONObject("store").getString("address"));
                        addressBean.setProvince(data.getJSONObject("store").getString("province"));
                        addressBean.setCity(data.getJSONObject("store").getString("city"));
                        addressBean.setArea(data.getJSONObject("store").getString("area"));
                        orderRootBean.setAddressBean(addressBean);
                    }
                    orderRootBean.setGoodsBeans(orderGoodsBeans);
                }
                bindDataToView();
            } catch (Exception e) {
                LogUtil.e(TAG, "onCommonGoodsCallBack() ", e);
            }
        }
    }

    private void bindDataToView() {
        if (orderRootBean.getAddressBean() == null){
            llAddressContainer.setVisibility(View.GONE);
        }else {
            llAddressContainer.setVisibility(View.VISIBLE);
            tvOrderNumber.setText("" + orderRootBean.getOrderNumber());
            tvAddressName.setText(orderRootBean.getAddressBean().getReceiveName());
            tvAddressPhone.setText(orderRootBean.getAddressBean().getReceivePhone());
            tvAddressDetail.setText(orderRootBean.getAddressBean().getProvince()
                    +orderRootBean.getAddressBean().getCity()+orderRootBean.getAddressBean().getArea()
                    +orderRootBean.getAddressBean().getDetailPlace());
            tvCreateTime.setText(orderRootBean.getCreate_time());
//            tvStatus.setText(orderRootBean.getStatus());
            tvStoreType.setText(orderRootBean.getStoreType());
            tvGoodsCount.setText("共"+(orderRootBean.getGoodsBeans() == null ? 0 : orderRootBean.getGoodsBeans().size())+"种"+getGoodsCount(orderRootBean.getGoodsBeans())+"件商品");
            llGoodsListContainer.removeAllViews();
            for (int i = 0;i < orderRootBean.getGoodsBeans().size();i++){
                View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.item_confirm_activity_goods,null);
                ImageView ivGoodsPic = view.findViewById(R.id.iv_goods_pic);
                Glide.with(OrderDetailActivity.this).load(orderRootBean.getGoodsBeans()
                        .get(i).getImageUrl()).apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
                TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
                tvGoodsName.setText(orderRootBean.getGoodsBeans().get(i).getGoodsName());
                TextView tvGoodsSpec = view.findViewById(R.id.tv_goods_spec);
                tvGoodsSpec.setText(orderRootBean.getGoodsBeans().get(i).getGoodsDescription());
                TextView tvGoodsPrice = view.findViewById(R.id.tv_goods_price);
                tvGoodsPrice.setText("￥"+orderRootBean.getGoodsBeans().get(i).getGoodsPrice());
                TextView tvBandedPrice = view.findViewById(R.id.tv_banded_price);
                tvBandedPrice.setText("税费:￥"+orderRootBean.getGoodsBeans().get(i).getBondedPrice());
                TextView tvCount= view.findViewById(R.id.tv_goods_count);
                tvCount.setText("x"+orderRootBean.getGoodsBeans().get(i).getCount());
                llGoodsListContainer.addView(view);
            }
            tvCreateTime.setText(orderRootBean.getCreate_time());
            tvGoodsTotalPrice.setText("商品金额:￥"+orderRootBean.getTotal_goods());
            tvBondedPrice.setText("税收金额:￥"+orderRootBean.getTotal_tax());
            tvPostType.setText("配送方式:"+orderRootBean.getPost());
            tvPayTotal.setText("实付金额:￥"+orderRootBean.getPay_total());
            tvPostPrice.setText("运费:￥"+orderRootBean.getPostPrice());
            tvGoodsWeight.setText(orderRootBean.getWeight() + "KG");
        }
    }
    private int getGoodsCount(List<OrderGoodsBean> goodsBeans){
        int count = 0;
        for (int i = 0; i < goodsBeans.size();i++){
            count+=goodsBeans.get(i).getCount();
        }
        return count;
    }
}
