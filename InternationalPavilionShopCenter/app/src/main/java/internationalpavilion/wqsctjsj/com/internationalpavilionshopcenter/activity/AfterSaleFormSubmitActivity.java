package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.GoodsDetailImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.OrderAfterSaleDealImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.OrderDealImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.GoodsDetailInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderAfterSaleDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
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
    private OrderRootBean orderRootBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getIntExtra("orderId", -1) != -1) {
            orderId = getIntent().getIntExtra("orderId", -1);
        }
        Log.e(TAG, "订单id：" + orderId);
        if (getIntent().getByteArrayExtra("orderRootBean") != null) {
            rootBean = getIntent().getByteArrayExtra("orderRootBean");
        }
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
                requestAfterSale(orderId);
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
        Log.e(TAG, "获取订单信息出错:" + error);
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
//                bindDataToView();
            } catch (Exception e) {
                LogUtil.e(TAG, "onCommonGoodsCallBack()", e);
            }
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
            LogUtil.d(TAG, "申请售后结果:" + result);
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
        Log.d(TAG, "requestAfterSale() orderId:" + orderId);
        RequestParams params = new RequestParams(MainUrls.requestAfterSaleUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        orderAfterSaleDealInterface.requestAfterSale(params, this);
    }


}
