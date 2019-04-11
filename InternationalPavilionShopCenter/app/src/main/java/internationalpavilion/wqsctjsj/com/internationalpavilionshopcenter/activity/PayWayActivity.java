package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.WxPayEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.EasyPermissionsEx;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PayResult;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.RandCharsUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.WXSignUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class PayWayActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx936ef706f9fb1fe7";
    private static final String TAG = "[IPSC][PayWayActivity]";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    private CommonDataInterface commonPresenter;
    private int alipayId = -1;
    private int wechatId = -1;

    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    private int currentId = -1;
    private String orderId = "-1";

    private int flag = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtils.show(PayWayActivity.this, "支付成功");
                        Intent intent = new Intent(PayWayActivity.this, PayResultActivity.class);
                        startActivity(intent);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.show(PayWayActivity.this, "支付失败:" + resultInfo);
                    }
                    break;
            }
        }
    };

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        EventBus.getDefault().register(this);
        regToWx();
        commonPresenter = new CommonGoodsImp();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("oderId"))) {
            Log.e(TAG, "订单Id:" + getIntent().getStringExtra("oderId"));
            orderId = getIntent().getStringExtra("oderId");
            initData(getIntent().getStringExtra("oderId"));
        }
        requestPermis();
    }

    private void requestPermis() {
        if (!EasyPermissionsEx.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissionsEx.requestPermissions(this, "分享商品信息需要获取读写sd卡权限", 1, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!EasyPermissionsEx.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
            EasyPermissionsEx.requestPermissions(this, "分享商品信息需要获取读写sd卡权限", 2, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void initData(String oderId) {
        flag = 1;
        RequestParams params = new RequestParams(MainUrls.getPayInfoUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", oderId);
        commonPresenter.getCommonGoodsData(params, this);
    }

    @Subscribe
    public void onEvent(WxPayEvent event) {
        if (event != null) {
            if (event.getCode() == 1) {
                Intent intent = new Intent(PayWayActivity.this, PayResultActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_pay_way;
    }

    @OnClick({R.id.tv_confirm, R.id.iv_back, R.id.ll_alipay, R.id.ll_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                confirmSetPayWay();
//                Intent intent = new Intent(PayWayActivity.this, PayResultActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_alipay:
                currentId = alipayId;
                selectPayWay();
                break;
            case R.id.ll_wechat:
                currentId = wechatId;
                selectPayWay();
                break;
        }
    }

    private void confirmSetPayWay() {
        flag = 3;
        try {
            RequestParams params = new RequestParams(MainUrls.toPayUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("order", orderId);
            params.addBodyParameter("method", "APP");
            commonPresenter.getCommonGoodsData(params, this);
        } catch (Exception e) {
            Log.e(TAG, "confirmSetPayWay() occur an exception!", e);
        }
    }

    private void selectPayWay() {
        flag = 2;
        try {
            RequestParams params = new RequestParams(MainUrls.setPayWayUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("order", orderId);
            params.addBodyParameter("fin_pay", currentId + "");
            commonPresenter.getCommonGoodsData(params, this);
        } catch (Exception e) {
            Log.e(TAG, "selectPayWay() occur an exception!", e);
        }

    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取支付信息中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e(TAG, "网络请求出错PayWayActivity->onError:" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        Log.e(TAG, "支付信息:" + result + ",flag:" + flag);
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            if (code == 0 && state == 0) {
                if (flag == 1) {
                    JSONArray finpaydata = jsonObject.getJSONObject("data").getJSONArray("finpaydata");
                    if (finpaydata != null && finpaydata.length() != 0) {
                        for (int i = 0; i < finpaydata.length(); i++) {
                            if (!TextUtils.isEmpty(finpaydata.getJSONObject(i).getString("name")) && finpaydata.getJSONObject(i).getString("name").equals("微信支付")) {
                                wechatId = finpaydata.getJSONObject(i).getInt("id");
                            } else if (!TextUtils.isEmpty(finpaydata.getJSONObject(i).getString("name")) && finpaydata.getJSONObject(i).getString("name").equals("支付宝")) {
                                alipayId = finpaydata.getJSONObject(i).getInt("id");
                                currentId = alipayId;
                                selectPayWay();
                            }
                        }
                    }
                } else if (flag == 2) {
                    if (currentId == alipayId) {
                        ivAlipay.setImageResource(R.mipmap.icon_cart_box_selected);
                        ivWechat.setImageResource(R.mipmap.icon_cart_box_unselected);
                    } else if (currentId == wechatId) {
                        ivWechat.setImageResource(R.mipmap.icon_cart_box_selected);
                        ivAlipay.setImageResource(R.mipmap.icon_cart_box_unselected);
                    }
                } else if (flag == 3) {
                    String orderInfo = jsonObject.getString("data");
                    if (currentId == alipayId) {
                        payWithAlipay(orderInfo);
                    } else if (currentId == wechatId) {
                        payWithWechat(orderInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void payWithWechat(String orderInfo) {
        if (!checkWeixin(PayWayActivity.this)) {
            ToastUtils.show(PayWayActivity.this, "您未安装微信程序。");
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(orderInfo);
            JSONObject pay = jsonObject.getJSONObject("pay");
            String appId = pay.getString("appid");
            String timeStamp = pay.getString("timestamp");
            String nonceStr = pay.getString("noncestr");
            String packageStr = pay.getString("package");
            String signType = jsonObject.getString("type");
            String paySign = pay.getString("sign");
            String prepayid = pay.getString("prepayid");
            String mch_id = pay.getString("partnerid");
            wxpay(paySign, mch_id, prepayid, nonceStr, appId, timeStamp, packageStr, signType);
        } catch (Exception e) {
            Log.e(TAG, "make weixin parameter occur an exception!", e);
        }

    } //唤起微信支付

    private void wxpay(String signs, String partnerId, String prepayId, String nonceStr, String appId, String timeStamp, String packageStr, String signType) {
//        String time=String.valueOf(System.currentTimeMillis()/1000);
////        //随机字符串
//        String ranStr= RandCharsUtils.getRandomString(16);
        //生成签名
//        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
//        parameters.put("appid",appId);
//        parameters.put("noncestr",ranStr);
//        parameters.put("partnerid", partnerId);
//        parameters.put("prepayid", prepayId);
//        String str="Sign=WXPay";
//        parameters.put("package",str);
//        parameters.put("timestamp",time);
//        String xSign= WXSignUtils.createSign("UTF-8",parameters);


        PayReq req = new PayReq();
        //应用 wxd34f7dc698b271af
        req.appId = appId;
        //随机字符串
        req.nonceStr = nonceStr;
        //扩展字段
        req.packageValue = packageStr;
        //商户号
        req.partnerId = partnerId;

        req.signType = signType;
        //预支付交易 id
        req.prepayId = prepayId;
        //时间戳
        req.timeStamp = timeStamp;
        req.sign = signs;
        api.sendReq(req);
    }

    private boolean checkWeixin(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
//            Log.e(TAG, "包名：" + packageName);
            if (packageName.equalsIgnoreCase("com.tencent.mm")) {
                return true;
            }
        }

        return false;
    }

    private void payWithAlipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayWayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
