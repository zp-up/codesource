package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.TokenEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.UserOptionImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UserOptionInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerButton;

public class LoginByVerifyCodeActivity extends BaseAppcompatActivity implements OnNetCallBack {

    private static final String TAG = "[IPSC][LoginByVerifyCodeActivity]";
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.tv_phone_number_notice)
    TextView tvPhoneNumberNotice;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    private boolean phoneIsOk = false;
    private boolean verifyCodeIsOk = false;
    private UserOptionInterface userOptionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        userOptionPresenter = new UserOptionImp();
        initListener();

    }

    private void initListener() {
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() != 11) {
                    if (charSequence.toString().trim().length() == 0) {
                        tvPhoneNumberNotice.setVisibility(View.GONE);
                        InputIsOk(-1);
                    } else {
                        tvPhoneNumberNotice.setVisibility(View.VISIBLE);
                        tvPhoneNumberNotice.setText("* 手机号长度有误");
                        InputIsOk(-1);
                    }
                } else {
                    if (PhoneNumberCheckUtils.isRealPhoneNumber(etPhoneNumber.getText().toString().trim())) {
                        tvPhoneNumberNotice.setVisibility(View.GONE);
                        InputIsOk(1);
                    } else {
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
        etVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() < 4) {
                    InputIsOk(-2);
                } else {
                    InputIsOk(2);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void InputIsOk(int index) {
        if (index == 1) {
            phoneIsOk = true;
        }
        if (index == -1) {
            phoneIsOk = false;
        }
        if (index == 2) {
            verifyCodeIsOk = true;
        }
        if (index == -2) {
            verifyCodeIsOk = false;
        }
        if (phoneIsOk && verifyCodeIsOk) {
            tvLogin.setEnabled(true);
//            tvLogin.setBackgroundColor(Color.parseColor("#ff0000"));
            tvLogin.setBackgroundResource(R.drawable.shape_of_red_btn);
        } else {
            tvLogin.setEnabled(false);
//            tvLogin.setBackgroundColor(Color.parseColor("#aaaaaa"));
            tvLogin.setBackgroundResource(R.drawable.shape_of_gray_btn);

        }
    }

    @OnClick({R.id.iv_back, R.id.tv_to_register, R.id.tv_to_password_login, R.id.tv_login, R.id.tv_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                LoginByVerifyCodeActivity.this.finish();
                break;
            case R.id.tv_to_register:
                Intent intent = new Intent(LoginByVerifyCodeActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_to_password_login:
                Intent intentToPasswordLogin = new Intent(LoginByVerifyCodeActivity.this, LoginByPasswordActivity.class);
                startActivity(intentToPasswordLogin);
                LoginByVerifyCodeActivity.this.finish();
                break;
            case R.id.tv_login:
                loginByVerifyCode();
                break;
            case R.id.tv_get_code:
                if (!phoneIsOk) {
                    ToastUtils.show(this, "请输入正确的手机号");
                    return;
                } else {
                    getVerifyCode();
                }
                break;
        }
    }

    private void getVerifyCode() {
        RequestParams params = new RequestParams(MainUrls.sendSMSCodeUrl);
        params.addBodyParameter("mobile", etPhoneNumber.getText().toString().trim());
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        Log.d(TAG, "getVerifyCode() params:" + params.toString());
        userOptionPresenter.getVerifyCode(params, this);
    }

    /**
     * 用验证码登录
     */
    private void loginByVerifyCode() {
        RequestParams params = new RequestParams(MainUrls.userCodeLogin);
        params.addBodyParameter("name", etPhoneNumber.getText().toString().trim());
        params.addBodyParameter("code", etVerifyCode.getText().toString().trim());
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        Log.d(TAG, "loginByVerifyCode() params:" + params.toString());
        userOptionPresenter.doLogin(params, this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_login_by_verify_code;
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "登录中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e(TAG, "onError() occur an Exception! " + error);
    }

    @Override
    public void onGetCodeSuccess(String result) {
        Log.d(TAG, "onGetCodeSuccess() result:" + result);
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
                    TimerButton timerButton = new TimerButton(60 * 1000, 1000, tvGetCode, "获取验证码");
                    ToastUtils.show(this, "验证码已发送");
                    timerButton.start();
                } else {
                    if (msg != null) {
                        ToastUtils.show(this, msg);
                    } else {
                        ToastUtils.show(this, "获取验证码失败");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCommonSuccess(String result, int type) {

    }

    @Override
    public void onLoginSuccess(String result) {
        if (result != null) {
            Log.e(TAG, "登录返回信息:" + result);
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
                        // TODO 下面的数据解析因为反馈数据跟标准的用户登录反馈数据不一致，可能会存在潜在逻辑问题
                        UserBean userBean = new UserBean();
                        userBean.setId(data.getInt("user_id"));
                        userBean.setName(data.getString("name"));
                        userBean.setUserPhone(etPhoneNumber.getText().toString().trim());
//                        userBean.setPassword(etPassword.getText().toString().trim());
                        userBean.setNickName(data.has("name")?data.getString("name"):"");
                        userBean.setImg(data.has("avator")?data.getString("avator"):"");
                        ((IPSCApplication)getApplication()).saveUserInfo(new Gson().toJson(userBean));
                        ToastUtils.show(this,"登录成功");

                        // 更新token和userid
                        try {
                            if (code == 0 && state == 0 && jsonObject.has("data") && jsonObject.getJSONObject("data").has("token")) {
                                String token = jsonObject.getJSONObject("data").getString("token");
                                int id = jsonObject.getJSONObject("data").getInt("user_id");
                                ((IPSCApplication) getApplication()).setAppToken(token);
                                IPSCApplication.id = id;
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onLoginSuccess()", e);
                        }

                        TokenEvent event = new TokenEvent();
                        event.code = 1;
                        EventBus.getDefault().post(event);
                        this.finish();
                    } else {
                        ToastUtils.show(this, "登录出错，请重试");
                    }

                } else {
                    ToastUtils.show(this, msg);
                }
            } catch (Exception e) {
                Log.e(TAG, "onLoginSuccess() occur an Exception", e);
            }
        }
    }

    @Override
    public void onRegisterSuccess(String result) {

    }

    private Boolean isTokenExpired(String msg) {
        if (msg.contains("接口无权限")) {
            ((IPSCApplication) getApplication()).getTokenFromNet();
            return true;
        }
        return false;
    }
}
