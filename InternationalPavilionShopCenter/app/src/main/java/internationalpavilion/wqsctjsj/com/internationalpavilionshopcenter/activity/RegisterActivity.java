package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.UserOptionImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UserOptionInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PasswordCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerButton;

public class RegisterActivity extends BaseAppcompatActivity implements OnNetCallBack {
    @BindView(R.id.tv_phone_number_notice)
    TextView tvPhoneNumberNotice;
    @BindView(R.id.tv_password_notice)
    TextView tvPasswordNotice;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    private boolean phoneIsOk = false;
    private boolean passwordIsOk = false;
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
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() < 8) {
                    if (charSequence.toString().trim().length() == 0) {
                        tvPasswordNotice.setVisibility(View.GONE);
                        InputIsOk(-2);
                    } else {
                        tvPasswordNotice.setVisibility(View.VISIBLE);
                        tvPasswordNotice.setText("* 密码长度为8-16位");
                        InputIsOk(-2);
                    }
                } else {
                    if (PasswordCheckUtils.whatIsInputChinese(charSequence.toString().trim()) == 3) {
                        tvPasswordNotice.setVisibility(View.VISIBLE);
                        tvPasswordNotice.setText("* 密码不能含有中文");
                        InputIsOk(-2);
                    } else if (!PasswordCheckUtils.isContainAll(charSequence.toString().trim())) {
                        tvPasswordNotice.setVisibility(View.VISIBLE);
                        tvPasswordNotice.setText("* 必须包含大小写字母和数字");
                        InputIsOk(-2);
                    } else {
                        tvPasswordNotice.setVisibility(View.GONE);
                        InputIsOk(2);
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
                    InputIsOk(-3);
                } else {
                    InputIsOk(3);
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
            passwordIsOk = true;
        }
        if (index == -2) {
            passwordIsOk = false;
        }
        if (index == 3) {
            verifyCodeIsOk = true;
        }
        if (index == -3) {
            verifyCodeIsOk = false;
        }
        if (phoneIsOk && passwordIsOk && verifyCodeIsOk) {
            tvRegister.setEnabled(true);
            tvRegister.setBackgroundColor(Color.parseColor("#ff0000"));
        } else {
            tvRegister.setEnabled(false);
            tvRegister.setBackgroundColor(Color.parseColor("#aaaaaa"));
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_get_code, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                if (!phoneIsOk) {
                    ToastUtils.show(this, "请输入正确的手机号");
                }
                if (TextUtils.isEmpty(IPSCApplication.accessToken)) {
                    ToastUtils.show(RegisterActivity.this, "程序出错，请重启app。");
                    return;
                }

                RequestParams params = new RequestParams(MainUrls.getVerifyCodeUrl);
                params.addBodyParameter("mobile", etPhoneNumber.getText().toString().trim());
                params.addBodyParameter("access_token", IPSCApplication.accessToken);
                userOptionPresenter.getVerifyCode(params, this);

                break;
            case R.id.tv_register:
                if (TextUtils.isEmpty(IPSCApplication.accessToken)) {
                    ToastUtils.show(RegisterActivity.this, "程序出错，请重启app。");
                    return;
                }
                RequestParams paramsRegister = new RequestParams(MainUrls.phoneRegisterUrl);
                paramsRegister.addBodyParameter("name", etPhoneNumber.getText().toString().trim());
                paramsRegister.addBodyParameter("code", etVerifyCode.getText().toString().trim());
                paramsRegister.addBodyParameter("password", etPassword.getText().toString().trim());
                paramsRegister.addBodyParameter("access_token", IPSCApplication.accessToken);
                paramsRegister.addBodyParameter("app", IPSCApplication.id + "");
                userOptionPresenter.doRegister(paramsRegister, this);
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void reloadData() {

    }

    //网络请求开始
    @Override
    public void onStarted() {
        showLoading(false, "请求数据中...");
    }

    //网络请求结束
    @Override
    public void onFinished() {
        dismissLoading();
    }

    //网络请求出错
    @Override
    public void onError(String error) {

    }

    //网络请求成功
    @Override
    public void onGetCodeSuccess(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    TimerButton timerButton = new TimerButton(60 * 1000, 1000, tvGetCode, "获取验证码");
                    ToastUtils.show(RegisterActivity.this, "验证码已发送");
                    timerButton.start();
                } else {
                    if (msg != null) {
                        ToastUtils.show(RegisterActivity.this, msg);
                    } else {
                        ToastUtils.show(RegisterActivity.this, "获取验证码失败");
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

    }

    @Override
    public void onRegisterSuccess(String result) {

        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    ToastUtils.show(RegisterActivity.this, "注册成功");
                    finish();
                } else {
                    ToastUtils.show(RegisterActivity.this, msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
