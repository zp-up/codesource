package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.TokenEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.WxEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.UserOptionImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UserOptionInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.AuthResult;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PasswordCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.wxapi.WXEntryActivity;

public class LoginByPasswordActivity extends BaseAppcompatActivity implements OnNetCallBack{

    private static final String TAG = "[IPSC][LoginByPasswordActivity]";
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    private String wechatAuthCode = "";// 微信授权码
    private String wechatAccount = "";// 微信账户id
    private String alipayAuthCode = "";// 支付宝授权码
    private String alipayAccount = "";// 支付宝账户id
    private int loginWay = LOGIN_WAY_BY_PASSWORD;

    private static final int LOGIN_WAY_BY_PASSWORD = 0x00;// 密码登录
    private static final int LOGIN_WAY_WECHAT = 0x01;// 微信登录
    private static final int LOGIN_WAY_ALIPAY = 0x02;// 支付宝登录
    private static final int TYPE_GET_LOGIN_LIST = 0x01;//获取登录列表数据
    private static final int TYPE_GET_ALIPAY_AUTH_DATA = 0x02;//获取支付宝授权需要的数据

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_password_notice)
    TextView tvPasswordNotice;
    @BindView(R.id.tv_phone_number_notice)
    TextView tvPhoneNumberNotice;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.iv_wechat_login)
    ImageView ivWechatLogin;
    @BindView(R.id.iv_alipay_login)
    ImageView ivAlipayLogin;

    private boolean phoneIsOk = false;
    private boolean passwordIsOk = false;

    private UserOptionInterface userOptionPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
        userOptionPresenter = new UserOptionImp();
        initListener();
        regToWx();
        EventBus.getDefault().register(this);
    }

    private void initListener() {
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() != 11){
                    if (charSequence.toString().trim().length() == 0){
                        tvPhoneNumberNotice.setVisibility(View.GONE);
                        InputIsOk(-1);
                    }else {
                        tvPhoneNumberNotice.setVisibility(View.VISIBLE);
                        tvPhoneNumberNotice.setText("* 手机号长度有误");
                        InputIsOk(-1);
                    }
                }else {
                    if (PhoneNumberCheckUtils.isRealPhoneNumber(etPhoneNumber.getText().toString().trim())){
                        tvPhoneNumberNotice.setVisibility(View.GONE);
                        InputIsOk(1);
                    }else {
                        tvPhoneNumberNotice.setVisibility(View.VISIBLE);
                        tvPhoneNumberNotice.setText("* 手机号格式有误");
                        InputIsOk(-1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() < 8){
                    if (charSequence.toString().trim().length() == 0){
                        tvPasswordNotice.setVisibility(View.GONE);
                        InputIsOk(-2);
                    }else {
                        tvPasswordNotice.setVisibility(View.VISIBLE);
                        tvPasswordNotice.setText("* 密码长度为8-16位");
                        InputIsOk(-2);
                    }
                }else {
                    if (PasswordCheckUtils.whatIsInputChinese(charSequence.toString().trim()) == 3){
                        tvPasswordNotice.setVisibility(View.VISIBLE);
                        tvPasswordNotice.setText("* 密码不能含有中文");
                        InputIsOk(-2);
//                    }else if (!PasswordCheckUtils.isContainAll(charSequence.toString().trim())){
//                        tvPasswordNotice.setVisibility(View.VISIBLE);
//                        tvPasswordNotice.setText("* 必须包含大小写字母和数字");
//                        InputIsOk(-2);
                    }else {
                        tvPasswordNotice.setVisibility(View.GONE);
                        InputIsOk(2);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void InputIsOk(int index){
        if (index == 1){
            phoneIsOk = true;
        }
        if (index == -1){
            phoneIsOk = false;
        }
        if (index == 2){
            passwordIsOk = true;
        }
        if (index == -2){
            passwordIsOk = false;
        }
        if (phoneIsOk && passwordIsOk){
            tvLogin.setEnabled(true);
//            tvLogin.setBackgroundColor(Color.parseColor("#ff0000"));
            tvLogin.setBackgroundResource(R.drawable.shape_of_red_btn);
        }else {
            tvLogin.setEnabled(false);
//            tvLogin.setBackgroundColor(Color.parseColor("#aaaaaa"));
            tvLogin.setBackgroundResource(R.drawable.shape_of_gray_btn);
        }
    }

    @OnClick({R.id.tv_to_register, R.id.tv_to_verify_login, R.id.iv_back, R.id.tv_login, R.id.rl_wechat_login, R.id.rl_alipay_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_to_register:
                Intent intent = new Intent(LoginByPasswordActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_to_verify_login:
                Intent intentToVerifyLogin = new Intent(LoginByPasswordActivity.this,LoginByVerifyCodeActivity.class);
                startActivity(intentToVerifyLogin);
                LoginByPasswordActivity.this.finish();
                break;
            case R.id.iv_back:
                LoginByPasswordActivity.this.finish();
                break;
            case R.id.tv_login:
                if (phoneIsOk && passwordIsOk){
                    RequestParams params = new RequestParams(MainUrls.userLoginUrl);
                    params.addBodyParameter("name",etPhoneNumber.getText().toString().trim());
                    params.addBodyParameter("password",etPassword.getText().toString().trim());
                    params.addBodyParameter("access_token", IPSCApplication.accessToken);
                    userOptionPresenter.doLogin(params,this);
                }
                break;
            case R.id.rl_wechat_login:// 微信三方登录
                wechatLogin();
                break;
            case R.id.rl_alipay_login:// 支付宝三方登录
                alipayLogin();
                break;
        }
    }
    @Override
    public int initLayout() {
        return R.layout.activity_login_by_password;
    }

    @Override
    public void reloadData() {

    }
    //网络请求开始
    @Override
    public void onStarted() {
        showLoading(false,"登录中...");
    }
    //网络请求结束
    @Override
    public void onFinished() {
        dismissLoading();
    }
    //网络请求出错
    @Override
    public void onError(String error) {
        Log.i(TAG, "onError() error:");
    }

    @Override
    public void onGetCodeSuccess(String result) {

    }

    /**
     * 访问第三方登录列表成功
     * @param result
     */
    @Override
    public void onCommonSuccess(String result, int resultType) {

        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (isTokenExpired(msg)) {
                    return;
                }
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data") && jsonObject.getString("data") != null && !jsonObject.getString("data").equals("null")) {
                        JSONObject data = jsonObject.getJSONObject("data");

                        switch (resultType) {
                            case TYPE_GET_LOGIN_LIST:
                                int id = data.getInt("id");
                                String name = data.getString("name");//
                                String type = data.getString("type");// Android / IOS
                                JSONObject wechat_account = data.getJSONObject("wechat_account");
                                JSONObject alipay_account = data.getJSONObject("alipay_account");
                                String appid = data.getString("appid");
                                String appkey = data.getString("appkey");
                                int status = data.getInt("status");
                                String update_time = data.getString("update_time");
                                String create_time = data.getString("create_time");

                                switch (loginWay) {
                                    // 注意传入参数的key与登录接口的key不对应
                                    case LOGIN_WAY_WECHAT:
                                        wechatAccount = wechat_account.getString("appid");
                                        thirdCodeLogin(wechatAccount, wechatAuthCode);
                                        break;
                                    case LOGIN_WAY_ALIPAY:
                                        alipayAccount = alipay_account.getString("appid");
                                        alipayAuthData(alipayAccount);
                                        break;
                                }
                                break;
                            case TYPE_GET_ALIPAY_AUTH_DATA:
                                StringBuffer authInfo = new StringBuffer();
                                com.alibaba.fastjson.JSONObject jsonObject1 = com.alibaba.fastjson.JSONObject.parseObject(data.toString());
                                for (Map.Entry<String, Object> set : jsonObject1.entrySet()) {
                                    authInfo.append(set.getKey() + "=" + (String) set.getValue() + "&");
                                }
                                authInfo.deleteCharAt(authInfo.length() - 1);
                                alipayLoginAuth(authInfo.toString());
                                break;
                        }
                    } else {
                        ToastUtils.show(LoginByPasswordActivity.this, "后台出错，请重试");
                    }

                } else {
                    ToastUtils.show(LoginByPasswordActivity.this, msg);
                }
            } catch (Exception e) {
                Log.e(TAG, "onCommonSuccess() occur an Exception", e);
            }
        }
    }

    //登录访问成功
    @Override
    public void onLoginSuccess(String result) {
        if (result != null) {
            Log.e(TAG, "登录返回信息:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg")?jsonObject.getString("msg"):"";
                if (isTokenExpired(msg)) {
                    return;
                }
                if (code == 0 && state == 0){
                    if (jsonObject.has("data") && jsonObject.getString("data") != null && !jsonObject.getString("data").equals("null")){
                        JSONObject data = jsonObject.getJSONObject("data");
                        UserBean userBean = new UserBean();
                        userBean.setId(data.getInt("id"));
                        userBean.setName(data.getString("name"));
                        userBean.setUserPhone(etPhoneNumber.getText().toString().trim());
                        userBean.setPassword(etPassword.getText().toString().trim());
                        userBean.setNickName(data.has("nickname")?data.getString("nickname"):"");
                        userBean.setImg(data.has("img")?data.getString("img"):"");
                        ((IPSCApplication)getApplication()).saveUserInfo(new Gson().toJson(userBean));

                        // 更新token和userid
                        try {
                            if (code == 0 && state == 0 && jsonObject.has("data") && jsonObject.getJSONObject("data").has("token")) {
                                String token = jsonObject.getJSONObject("data").getJSONObject("token").getString("token");
                                int id = jsonObject.getJSONObject("data").getJSONObject("token").getInt("user_id");
                                ((IPSCApplication) getApplication()).setAppToken(token);
                                IPSCApplication.id = id;
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onLoginSuccess()", e);
                        }

                        ToastUtils.show(LoginByPasswordActivity.this,"登录成功");
                        TokenEvent event = new TokenEvent();
                        event.code = 1;
                        EventBus.getDefault().post(event);
                        setResult(10);
                        LoginByPasswordActivity.this.finish();
                    }else {
                        ToastUtils.show(LoginByPasswordActivity.this,"登录出错，请重试");
                    }

                }else {
                    ToastUtils.show(LoginByPasswordActivity.this,msg);
                }
            }catch (Exception e){
                Log.e(TAG, "onLoginSuccess() occur an Exception", e);
            }
        }
    }

    @Override
    public void onRegisterSuccess(String result) {

    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WXEntryActivity.WX_APP_ID, false);

        // 将应用的appId注册到微信
        api.registerApp(WXEntryActivity.WX_APP_ID);
    }

    /**
     * 微信登录
     */
    private void wechatLogin() {
        Log.d(TAG, "wechatLogin()");
        loginWay = LOGIN_WAY_WECHAT;
        // 获取微信授权
        wechatAuth();
    }

    /**
     * 获取微信授权
     */
    private void wechatAuth() {
        if (api != null && api.isWXAppInstalled()) {// 用户是否安装微信客户端
            // send oauth request
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        } else {
            // TODO: 这里需要引导用户去下载微信客户端
            Toast.makeText(this, "用户没有安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    // 支付宝登录
    private void alipayLogin() {
        loginWay = LOGIN_WAY_ALIPAY;
        // 支付宝支付首先需要获取一些参数
        thirdLoginList();
    }

    /**
     * 获取支付宝登录授权
     */
    private void alipayLoginAuth(final String authInfo) {
        Log.d(TAG, "alipayLoginAuth() authInfo:" + authInfo);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(LoginByPasswordActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mAlipayHandler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 支付宝三方登录：获取授权动作所需的数据
     * @param account
     */
    private void alipayAuthData(String account) {

        RequestParams params = new RequestParams(MainUrls.userThirdLoginUrl);
        params.addBodyParameter("app", "Android");
        params.addBodyParameter("type", loginWay == LOGIN_WAY_WECHAT ? "wechat" : "alipay");
        params.addBodyParameter("account", account);// "2018071860649417");
        params.addBodyParameter("code", loginWay == LOGIN_WAY_WECHAT ? "openid" : "auth_token");//微信openid,支付宝auth_token
//        params.addBodyParameter("app", "Android");
//        params.addBodyParameter("type", loginWay == LOGIN_WAY_WECHAT ? "wechat" : "alipay");
//        params.addBodyParameter("account", account);// "wx936ef706f9fb1fe7");
//        params.addBodyParameter("code", code);//微信openid,支付宝auth_token
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        Log.d(TAG, "alipayAuthData() params:" + params.toString());
        userOptionPresenter.commonData(params, this, TYPE_GET_ALIPAY_AUTH_DATA);
    }

    /**
     * 后台接口设计命名比较奇怪
     * 微信登录测试通过
     * @param code
     */
    private void thirdCodeLogin(String account, String code) {
        RequestParams params = new RequestParams(MainUrls.userThirdCodeLoginUrl);
        params.addBodyParameter("app", "Android");// Android
        params.addBodyParameter("type", loginWay == LOGIN_WAY_WECHAT ? "wechat" : "alipay");
        params.addBodyParameter("account", account);
        params.addBodyParameter("code", code);//微信openid,支付宝auth_token 真实的授权id
//        params.addBodyParameter("app", "Android");
//        params.addBodyParameter("type", loginWay == LOGIN_WAY_WECHAT ? "wechat" : "alipay");
//        params.addBodyParameter("account", "wx936ef706f9fb1fe7");
//        params.addBodyParameter("code", code);//微信openid,支付宝auth_token
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        Log.d(TAG, "thirdCodeLogin() params:" + params.toString());
        userOptionPresenter.doLogin(params, this);
    }

    /**
     * 第三方登录列表,获取三方登录所需部分数据
     */
    private void thirdLoginList() {
        RequestParams params = new RequestParams(MainUrls.userThirdLoginListUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        Log.d(TAG, "thirdLoginList() params:" + params.toString());
        userOptionPresenter.commonData(params, this, TYPE_GET_LOGIN_LIST);
    }

    /**
     * 微信授权回调
     * @param event
     */
    @Subscribe
    public void onEvent(WxEvent event) {
        Log.d(TAG, "onEvent() code:" + event.getCode());
        if (event != null && event.getCode() == 4) {// 微信授权成功
            wechatAuthCode = event.getMsg();
            thirdLoginList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mAlipayHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    Log.d(TAG, "handleMessage() resultStatus:" + resultStatus + ",resultCode:" + authResult.getResultCode());

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Log.d(TAG, "handleMessage() alipay auth success!");
                        alipayAuthCode = authResult.getAuthCode();
                        thirdCodeLogin(alipayAccount, alipayAuthCode);
                    } else {
                        // 其他状态值则为授权失败
                        Log.d(TAG, "handleMessage() alipay auth failed!");
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private Boolean isTokenExpired(String msg) {
        if (msg.contains("接口无权限")) {
            ((IPSCApplication) getApplication()).getTokenFromNet();
            return true;
        }
        return false;
    }
}
