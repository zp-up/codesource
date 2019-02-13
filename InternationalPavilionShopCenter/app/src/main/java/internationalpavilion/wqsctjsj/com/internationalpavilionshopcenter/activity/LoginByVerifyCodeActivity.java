package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;

public class LoginByVerifyCodeActivity extends BaseAppcompatActivity {
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.tv_phone_number_notice)
    TextView tvPhoneNumberNotice;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;

    private boolean phoneIsOk = false;
    private boolean verifyCodeIsOk = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
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
        etVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length()< 4){
                    InputIsOk(-2);
                }else {
                    InputIsOk(2);
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
            verifyCodeIsOk = true;
        }
        if (index == -2){
            verifyCodeIsOk = false;
        }
        if (phoneIsOk && verifyCodeIsOk){
            tvLogin.setEnabled(true);
            tvLogin.setBackgroundColor(Color.parseColor("#ff0000"));
        }else {
            tvLogin.setEnabled(false);
            tvLogin.setBackgroundColor(Color.parseColor("#aaaaaa"));
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_to_register,R.id.tv_to_password_login,R.id.tv_login,R.id.tv_get_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                LoginByVerifyCodeActivity.this.finish();
                break;
            case R.id.tv_to_register:
                Intent intent = new Intent(LoginByVerifyCodeActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_to_password_login:
                Intent intentToPasswordLogin = new Intent(LoginByVerifyCodeActivity.this,LoginByPasswordActivity.class);
                startActivity(intentToPasswordLogin);
                LoginByVerifyCodeActivity.this.finish();
                break;
            case R.id.tv_login:

                break;
            case R.id.tv_get_code:
                if (!phoneIsOk){
                    ToastUtils.show(this,"请输入正确的手机号");
                    return;
                }
                break;
        }
    }
    @Override
    public int initLayout() {
        return R.layout.activity_login_by_verify_code;
    }

    @Override
    public void reloadData() {

    }
}
