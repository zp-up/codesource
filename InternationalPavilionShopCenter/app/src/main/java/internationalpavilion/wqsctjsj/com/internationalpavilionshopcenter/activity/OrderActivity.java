package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.chrisjason.baseui.util.DpUtils;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.AfterSaleOrderFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.AllOrderFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.WaitDeliveryOrderFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.WaitPayOrderFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.WaitReveivedOrderFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.OrderAfterSaleDealImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.OrderDealImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderAfterSaleDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAfterSaleOrderCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnOrderDealCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnRefundGoodsAddressCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.NORMAL_TYPE;
import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.WARNING_TYPE;

public class OrderActivity extends BaseAppcompatActivity implements OnOrderDealCallBack,OnAfterSaleOrderCallBack {

    public static final String TAG = "[IPSC][OrderActivity]";
    @BindView(R.id.vp_order)
    ViewPager vpOrder;

    @BindView(R.id.tv_all)
    TextView tvAllOrder;
    @BindView(R.id.tv_wait_pay)
    TextView tvWaitPay;
    @BindView(R.id.tv_wait_delivery)
    TextView tvWaitDelivery;
    @BindView(R.id.tv_wait_received)
    TextView tvWaitReceived;
    @BindView(R.id.tv_after_sale)
    TextView tvAfterSale;

    @BindView(R.id.ll_parent)
    public LinearLayout llParent;

    @BindView(R.id.v_scroll)
    View viewScroll;
    private int currentPosition = 1;

    private MainVPAdapter vpAdapter;
    private List<Fragment> frgList = new ArrayList<>();
    private int screenWidth;
    private OrderDealInterface orderDealPresenter;
    private OrderAfterSaleDealInterface orderAfterSaleDealPresenter;
    private AllOrderFragment allOrderFragment;
    private WaitPayOrderFragment waitPayFragment;
    private WaitDeliveryOrderFragment waitDeliveryFragment;
    private WaitReveivedOrderFragment waitReceivedFragment;
    private AfterSaleOrderFragment afterSaleFragment;

    private OnRefundGoodsAddressCallBack refundGoodsAddressCallBack = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initView();
        orderDealPresenter = new OrderDealImp();
        orderAfterSaleDealPresenter = new OrderAfterSaleDealImp();
        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        if (getIntent().getIntExtra("index", -1) == 1) {
            change(2);
        } else if (getIntent().getIntExtra("index", -1) == 2) {
            change(3);
        } else if (getIntent().getIntExtra("index", -1) == 3) {
            change(4);
        } else if (getIntent().getIntExtra("index", -1) == 4) {
            change(5);
        }
    }

    @OnClick({R.id.tv_all, R.id.tv_wait_pay, R.id.tv_wait_delivery, R.id.tv_wait_received, R.id.tv_after_sale, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                change(1);
                break;
            case R.id.tv_wait_pay:
                change(2);
                break;
            case R.id.tv_wait_delivery:
                change(3);
                break;
            case R.id.tv_wait_received:
                change(4);
                break;
            case R.id.tv_after_sale:
                change(5);
                break;
            case R.id.iv_back:
                OrderActivity.this.finish();
                break;
        }
    }

    private void fragmentInitData() {
        if (allOrderFragment != null) {
            allOrderFragment.onResume();
        }
        if (waitPayFragment != null) {
            waitPayFragment.onResume();
        }
        if (waitDeliveryFragment != null) {
            waitDeliveryFragment.onResume();
        }
        if (waitReceivedFragment != null) {
            waitReceivedFragment.onResume();
        }
        if (afterSaleFragment != null) {
            afterSaleFragment.onResume();
        }
    }

    private void initView() {
        allOrderFragment = new AllOrderFragment();
        frgList.add(allOrderFragment);
        waitPayFragment = new WaitPayOrderFragment();
        frgList.add(waitPayFragment);
        waitDeliveryFragment = new WaitDeliveryOrderFragment();
        frgList.add(waitDeliveryFragment);
        waitReceivedFragment = new WaitReveivedOrderFragment();
        frgList.add(waitReceivedFragment);
        afterSaleFragment = new AfterSaleOrderFragment();
        frgList.add(afterSaleFragment);
        vpAdapter = new MainVPAdapter(getSupportFragmentManager());
        vpOrder.setAdapter(vpAdapter);
        vpOrder.setOffscreenPageLimit(5);
        vpOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        change(1);
                        break;
                    case 1:
                        change(2);
                        break;
                    case 2:
                        change(3);
                        break;
                    case 3:
                        change(4);
                        break;
                    case 4:
                        change(5);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onStarted() {
        showLoading(false, "请求数据中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e(TAG, "请求出错:" + error);
    }

    @Override
    public void onBuyAgainCallBack(String result) {
        if (result != null) {
            Log.e(TAG, "再次购买:" + result + ",buyAgainOrderId:" + buyAgainOrderId);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = "再次购买失败";
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
                    ToastUtils.show(OrderActivity.this, "再次购买订单已生成，请确认并完成支付。");
                    Intent intent = new Intent(OrderActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("id", buyAgainOrderId + "");
                    startActivity(intent);
                } else {
                    ToastUtils.show(OrderActivity.this, "再次购买失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCancelOrderCallBack(String result) {
        if (result != null) {
            Log.e(TAG, "取消订单:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.getString("msg");
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
                    ToastUtils.show(OrderActivity.this, "取消订单成功");
                    fragmentInitData();
                } else {
                    ToastUtils.show(OrderActivity.this, "取消订单失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackMoneyOnlyCallBack(String result) {
        if (result != null) {
            Log.e(TAG, "申请退款:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.getString("msg");
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
                    ToastUtils.show(OrderActivity.this, "申请退款成功");
                    fragmentInitData();
                } else {
                    ToastUtils.show(OrderActivity.this, "申请退款失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConfirmOrderCallBack(String result) {
        if (result != null) {
            Log.e(TAG, "确认收货:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.getString("msg");
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
                    ToastUtils.show(OrderActivity.this, "确认收货成功");
                    fragmentInitData();
                } else {
                    ToastUtils.show(OrderActivity.this, "确认收货失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestAfterSale(String result) {
        if (result != null) {
            Log.e(TAG, "申请售后结果:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.getString("msg");
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
                    ToastUtils.show(OrderActivity.this, "申请售后成功");
                    fragmentInitData();
                } else {
                    ToastUtils.show(OrderActivity.this, "申请售后失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSetExpressInfo(String result) {
        if (result != null) {
            Log.e(TAG, "设置退货信息结果:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.getString("msg");
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
                    ToastUtils.show(OrderActivity.this, "设置退货信息成功");
                    fragmentInitData();
                } else {
                    ToastUtils.show(OrderActivity.this, "设置退货信息失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGetRefundGoodsAddressUrl(String result) {
        if (result != null) {
            Log.e(TAG, "获取退货地址结果:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.getString("msg");
                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (code == 0 && state == 0) {
//                    ToastUtils.show(OrderActivity.this, "获取退货地址成功");
                    refundGoodsAddressCallBack.getRefundGoodsAddress(jsonObject.getString("data"));
                } else {
//                    ToastUtils.show(OrderActivity.this, "设获取退货地址失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MainVPAdapter extends FragmentPagerAdapter {

        public MainVPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return frgList.get(position);
        }

        @Override
        public int getCount() {
            return frgList.size();
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void reloadData() {

    }

    private void change(int index) {
        switch (index) {
            case 1:
                tvAllOrder.setTextColor(Color.parseColor("#ff7d66"));
                tvWaitPay.setTextColor(Color.parseColor("#636363"));
                tvWaitDelivery.setTextColor(Color.parseColor("#636363"));
                tvWaitReceived.setTextColor(Color.parseColor("#636363"));
                tvAfterSale.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(1);
                vpOrder.setCurrentItem(0);
                break;
            case 2:
                tvAllOrder.setTextColor(Color.parseColor("#636363"));
                tvWaitPay.setTextColor(Color.parseColor("#ff7d66"));
                tvWaitDelivery.setTextColor(Color.parseColor("#636363"));
                tvWaitReceived.setTextColor(Color.parseColor("#636363"));
                tvAfterSale.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(2);
                vpOrder.setCurrentItem(1);
                break;
            case 3:
                tvAllOrder.setTextColor(Color.parseColor("#636363"));
                tvWaitPay.setTextColor(Color.parseColor("#636363"));
                tvWaitDelivery.setTextColor(Color.parseColor("#ff7d66"));
                tvWaitReceived.setTextColor(Color.parseColor("#636363"));
                tvAfterSale.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(3);
                vpOrder.setCurrentItem(2);
                break;
            case 4:
                tvAllOrder.setTextColor(Color.parseColor("#636363"));
                tvWaitPay.setTextColor(Color.parseColor("#636363"));
                tvWaitDelivery.setTextColor(Color.parseColor("#636363"));
                tvWaitReceived.setTextColor(Color.parseColor("#ff7d66"));
                tvAfterSale.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(4);
                vpOrder.setCurrentItem(3);
                break;
            case 5:
                tvAllOrder.setTextColor(Color.parseColor("#636363"));
                tvWaitPay.setTextColor(Color.parseColor("#636363"));
                tvWaitDelivery.setTextColor(Color.parseColor("#636363"));
                tvWaitReceived.setTextColor(Color.parseColor("#636363"));
                tvAfterSale.setTextColor(Color.parseColor("#ff7d66"));
                scrollAnimal(5);
                vpOrder.setCurrentItem(4);
                break;
        }
    }

    private void scrollAnimal(int position) {
        int width = screenWidth / 5 - 2 * DpUtils.dpToPx(this, 10);
        switch (position) {
            case 1:
                int pL = DpUtils.dpToPx(this, 10);
                if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", width + 2 * pL, 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (2 * width + 4 * pL), 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 4) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (3 * width + 6 * pL), 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 5) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (4 * width + 8 * pL), 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 1;
                break;
            case 2:
                pL = DpUtils.dpToPx(this, 10);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 4 * pL + 2 * width, (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 4) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (3 * width + 6 * pL), (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 5) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (4 * width + 8 * pL), (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 2;
                break;
            case 3:
                pL = DpUtils.dpToPx(this, 10);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 2 * pL + width, (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 4) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (3 * width + 6 * pL), (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 5) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (4 * width + 8 * pL), (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 3;
                break;
            case 4:
                pL = DpUtils.dpToPx(this, 10);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (3 * width + 6 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (1 * width + 2 * pL), (3 * width + 6 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (2 * width + 4 * pL), (3 * width + 6 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 5) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (4 * width + 8 * pL), (3 * width + 6 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 4;
                break;
            case 5:
                pL = DpUtils.dpToPx(this, 10);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (4 * width + 8 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (1 * width + 2 * pL), (4 * width + 8 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (2 * width + 4 * pL), (4 * width + 8 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 4) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (3 * width + 6 * pL), (4 * width + 8 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 5;
                break;
        }
    }

    private int buyAgainOrderId = -1;

    public void buyAgain(int orderId) {
        buyAgainOrderId = orderId;
        RequestParams params = new RequestParams(MainUrls.buyAgainUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        orderDealPresenter.buyAgain(params, this);
    }
    public void cancelOrder(int orderId) {
        RequestParams params = new RequestParams(MainUrls.cancelOrderUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        orderDealPresenter.cancelOrder(params, this);
    }
    public void backMoneyOnly(int orderId){
        RequestParams params = new RequestParams(MainUrls.backMoneyOnlyUrl);
        params.addBodyParameter("access_token",IPSCApplication.accessToken);
        params.addBodyParameter("id",orderId + "");
        orderDealPresenter.backMoneyOnly(params,this);
    }
    public void confirmReceived(int orderId){
        RequestParams params = new RequestParams(MainUrls.comfirmOrderUrl);
        params.addBodyParameter("access_token",IPSCApplication.accessToken);
        params.addBodyParameter("id",orderId + "");
        orderDealPresenter.backMoneyOnly(params,this);
    }

    /**
     * 申请售后
     */
    public void requestAfterSale(int orderId) {
        Log.d(TAG, "requestAfterSale() orderId:" + orderId);
        RequestParams params = new RequestParams(MainUrls.requestAfterSaleUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        orderAfterSaleDealPresenter.requestAfterSale(params, this);
    }

    /**
     * 填写快递信息
     */
    public void setExpressInfo(int orderId,String expressCode) {
        Log.d(TAG, "setExpressInfo() orderId:" + orderId + ",expressCode:" + expressCode);
        RequestParams params = new RequestParams(MainUrls.setExpressInfoUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        params.addBodyParameter("code", expressCode + "");
        orderAfterSaleDealPresenter.setExpressInfo(params, this);
    }

    /**
     * 跳转到售后信息提交界面
     */
    public void toAfterSaleFormSubmit(/*OrderRootBean orderRootBean*/int orderId) {
        Log.d(TAG, "showRequestAfterSale() orderId:" + orderId);
        Intent intent = new Intent(this, AfterSaleFormSubmitActivity.class);
        intent.putExtra("orderId", orderId);
//        Bundle bundle = intent.getBundleExtra("orderRootBean");
//        bundle.putSerializable();
//        intent.putExtra("orderRootBean", orderRootBean.toString().getBytes());
        startActivity(intent);
    }

    /**
     * 获取退货地址信息
     */
    public void getRefundGoodsAddress(int orderId, OnRefundGoodsAddressCallBack callBack) {
        Log.d(TAG, "setExpressInfo() orderId:" + orderId);
        this.refundGoodsAddressCallBack = callBack;
        RequestParams params = new RequestParams(MainUrls.getRefundGoodsAddressUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId + "");
        orderAfterSaleDealPresenter.getRefundGoodsAddressUrl(params, this);
    }

    /**
     * 弹出申请售后确认对话框
     * @param orderId
     */
    public void showRequestAfterSale(final int orderId) {
        Log.d(TAG, "showRequestAfterSale() orderId:" + orderId);
        final SweetAlertDialog dialog = new SweetAlertDialog(this, WARNING_TYPE);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismissWithAnimation();
                OrderActivity.this.toAfterSaleFormSubmit(orderId);
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismissWithAnimation();
            }
        });
        dialog.showCancelButton(true);
        dialog.setCancelText("取消");
        dialog.setConfirmText("确定");
        dialog.setTitleText("温馨提示");
        dialog.setContentText("是否要申请售后?");
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 弹出售后申请已提对话框
     */
    public void showRefunding() {
        final SweetAlertDialog dialog = new SweetAlertDialog(this, NORMAL_TYPE);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismissWithAnimation();
                sweetAlertDialog.hide();
            }
        });
        dialog.showCancelButton(false);
        dialog.setConfirmText("确定");
        dialog.setTitleText("售后申请已提");
        dialog.setContentText("亲～后台正在处理申请ing～");
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 弹出退货快递地址信息填写对话框
     */
    public void showRefundGoodsAddress(final int orderId) {
        (OrderActivity.this).getRefundGoodsAddress(orderId, new OnRefundGoodsAddressCallBack() {
            @Override
            public void getRefundGoodsAddress(String refundGoodsAddress) {
                Log.i("TAG", "refundGoodsAddress:" + refundGoodsAddress);
                String phone = "";
                String address = "";
                String person = "";
                try {
                    phone = refundGoodsAddress.split("<br>")[1].split(":")[1];
                    address = refundGoodsAddress.split("<br>")[2].split(":")[1];
                    person = refundGoodsAddress.split("<br>")[0];
                } catch (Exception e) {
                    Log.e("TAG", "parse data occur exception!", e);
                }
                Log.i("TAG", "phone:" + phone
                        + ",address:" + address
                        + ",person:" + person);


                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);

                View expressAddressView = LayoutInflater.from(OrderActivity.this).inflate(R.layout.dialog_express_address, null);
                builder.setView(expressAddressView);
                builder.setCancelable(true);
                final AlertDialog alertDialog = builder.create();
                expressAddressView.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
//                Toast.makeText(context, "取消填写退货快递地址信息", Toast.LENGTH_LONG).show();
                    }
                });
                expressAddressView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
//                Toast.makeText(context, "成功填写退货快递地址信息", Toast.LENGTH_LONG).show();
                        EditText editText = (EditText) alertDialog.findViewById(R.id.express_content);
                        (OrderActivity.this).setExpressInfo(orderId, editText.getText().toString());
                    }
                });
                ((TextView) expressAddressView.findViewById(R.id.phone_content)).setText(phone);
                ((TextView) expressAddressView.findViewById(R.id.address_content)).setText(address + "\n" + person);

                alertDialog.show();
            }
        });
    }

    /**
     * 弹出收货中对话框
     */
    public void showReceiveGoods() {
        final SweetAlertDialog dialog = new SweetAlertDialog(this, NORMAL_TYPE);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismissWithAnimation();
                sweetAlertDialog.hide();
            }
        });
        dialog.showCancelButton(false);
        dialog.setConfirmText("确定");
        dialog.setTitleText("售后提示");
        dialog.setContentText("亲～售后中，等待商家收货ing～");
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 弹出售后已完成对话框
     */
    public void showRefundDone() {
        final SweetAlertDialog dialog = new SweetAlertDialog(this, NORMAL_TYPE);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismissWithAnimation();
                sweetAlertDialog.hide();
            }
        });
        dialog.showCancelButton(false);
        dialog.setConfirmText("确定");
        dialog.setTitleText("售后已完成");
        dialog.setContentText("亲～商家已退款完成，请使用相关支付工具查看");
        dialog.setCancelable(false);
        dialog.show();
    }
}
