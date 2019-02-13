package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PasswordCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

public class LoginByPasswordActivity extends BaseAppcompatActivity implements OnNetCallBack{

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
    private boolean phoneIsOk = false;
    private boolean passwordIsOk = false;

    private UserOptionInterface userOptionPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
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
                    }else if (!PasswordCheckUtils.isContainAll(charSequence.toString().trim())){
                        tvPasswordNotice.setVisibility(View.VISIBLE);
                        tvPasswordNotice.setText("* 必须包含大小写字母和数字");
                        InputIsOk(-2);
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
            tvLogin.setBackgroundColor(Color.parseColor("#ff0000"));
        }else {
            tvLogin.setEnabled(false);
            tvLogin.setBackgroundColor(Color.parseColor("#aaaaaa"));
        }
    }
    @OnClick({R.id.tv_to_register,R.id.tv_to_verify_login,R.id.iv_back,R.id.tv_login})
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

    }

    @Override
    public void onGetCodeSuccess(String result) {

    }
    //登录访问成功
    @Override
    public void onLoginSuccess(String result) {
        if (result != null){
            Log.e("TAG","登录返回信息:"+result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg")?jsonObject.getString("msg"):"";
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
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRegisterSuccess(String result) {

    }
}
