package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.GoodsDetailImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.OrderAfterSaleDealImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.OrderDealImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.GoodsDetailInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderAfterSaleDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.StringUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAfterSaleOrderCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnOrderDealCallBack;

/**
 * 退货信息填写和提交界面
 */
public class AfterSaleFormSubmitActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack, OnOrderDealCallBack,OnAfterSaleOrderCallBack {
    public static final String TAG = "[IPSC][AfterSaleFormSubmitActivity]";
    @BindView(R.id.tv_reason_text)
    TextView tvReasonText;
    private CommonDataInterface commonPresenter;
    private OrderDealInterface orderDealPresenter;
    private OrderAfterSaleDealInterface orderAfterSaleDealInterface;
    private int orderId = -1;
    private byte[] rootBean = null;
    //订单状态
    private String orderState=null;
    private OrderRootBean orderRootBean;
    @BindView(R.id.ll_goods_list_container)
    LinearLayout llGoodsListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getIntExtra("orderId", -1) != -1) {
            orderId = getIntent().getIntExtra("orderId", -1);
        }
        if (getIntent().getByteArrayExtra("orderRootBean") != null) {
            rootBean = getIntent().getByteArrayExtra("orderRootBean");
        }
        if(!TextUtils.isEmpty(getIntent().getStringExtra("state"))){
            orderState=getIntent().getStringExtra("state");
        }
        /**
         * 如果订单状态为代发货  。就调用退货接口： order.order.cancelorder ；参数是orderId 提示退款成功。不知道怎么改了。 需要马哥帮忙
         * */
        commonPresenter = new CommonGoodsImp();
        orderDealPresenter = new OrderDealImp();
        orderAfterSaleDealInterface = new OrderAfterSaleDealImp();
        initData();
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getOrderDetailByIdUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        commonPresenter.getCommonGoodsData(params, this);
    }

    @OnClick({R.id.iv_back, R.id.rl_reason_select, R.id.tv_submit_after_sale_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_reason_select:
                final ArrayList<String> data = new ArrayList<>();
                data.add("包装破碎");
                data.add("商品太贵，想买更便宜的");
                data.add("不想买了");
                data.add("商品与描述不符");
                data.add("商品已损坏");
                final OptionsPickerView pickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvReasonText.setText(data.get(options1));
                    }
                })
                        .setTitleText("请选择退货原因")
                        .build();
                pickerView.setPicker(data);
                pickerView.show();
                break;
            case R.id.tv_submit_after_sale_info:
                //如果是待发货需要退货就调用退货接口否则调用售后接口 .没有执行到这个方法
                if(TextUtils.equals(orderState,"待发货")){
                    requestRefund(orderId);
                }else{
                    requestAfterSale(orderId);
                }

                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_after_sale_form_submit;
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
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            LogUtil.d(TAG, "订单信息:" + result);
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

                            String img="";
                            Object o = list.getJSONObject(j).getJSONObject("goods_goods").get("img");
                            if(o!=null){
                                if(o instanceof String){
                                    img = list.getJSONObject(j).getJSONObject("goods_goods").getString("img");
                                }else if(o instanceof JSONArray){
                                    JSONArray array = list.getJSONObject(j).getJSONObject("goods_goods").getJSONArray("img");
                                    if(array!=null && array.length()>0){
                                        img = array.getString(0);
                                    }
                                }
                            }

                            orderGoodsBean.setImageUrl(img);
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
                LogUtil.e(TAG, "onCommonGoodsCallBack()", e);
            }
        }
    }

    private void bindDataToView() {
        llGoodsListContainer.removeAllViews();
        for (int i = 0; i < orderRootBean.getGoodsBeans().size(); i++) {
            View view = LayoutInflater.from(AfterSaleFormSubmitActivity.this).inflate(R.layout.item_confirm_activity_goods, null);
            ImageView ivGoodsPic = view.findViewById(R.id.iv_goods_pic);
            Glide.with(AfterSaleFormSubmitActivity.this).load(orderRootBean.getGoodsBeans()
                    .get(i).getImageUrl()).apply(new RequestOptions().error(R.drawable.icon_no_image)).into(ivGoodsPic);
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
    }

    @Override
    public void onBuyAgainCallBack(String result) {

    }

    @Override
    public void onCancelOrderCallBack(String result) {

    }

    @Override
    public void onBackMoneyOnlyCallBack(String result) {

    }

    @Override
    public void onConfirmOrderCallBack(String result) {

    }

    @Override
    public void onRequestAfterSale(String result) {
        if (result != null) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.getString("msg");
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
                    ToastUtils.show(this, "申请售后成功");
                    finish();
                } else {
                    ToastUtils.show(this, "申请售后失败:" + msg);
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "onRequestAfterSale()", e);
            }
        }
    }

    @Override
    public void onSetExpressInfo(String result) {

    }

    @Override
    public void onGetRefundGoodsAddressUrl(String result) {

    }

    public void requestAfterSale(int orderId) {
        RequestParams params = new RequestParams(MainUrls.requestAfterSaleUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        orderAfterSaleDealInterface.requestAfterSale(params, this);
    }


    public void requestRefund(int orderId){
        RequestParams params=new RequestParams(MainUrls.backMoneyOnlyUrl);
        params.addBodyParameter("access_token",IPSCApplication.accessToken);
        params.addBodyParameter("id",orderId+"");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    int state = jsonObject.getInt("state");
                    String msg = jsonObject.getString("msg");
                    if (jsonObject.has("msg")) {
                        msg = jsonObject.getString("msg");
                    }
                    if (code == 0 && state == 0) {
                        ToastUtils.show(x.app(), "申请退款成功");
                        finish();
                    } else {
                        ToastUtils.show(x.app(), "申请退款失败:" + msg);
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "onRequestAfterSale()", e);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }




}
