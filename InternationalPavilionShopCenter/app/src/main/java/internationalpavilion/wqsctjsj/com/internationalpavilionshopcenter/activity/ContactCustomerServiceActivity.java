package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;

public class ContactCustomerServiceActivity extends BaseAppcompatActivity {
    @BindView(R.id.et_im_input)
    EditText etImInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
        initView();
    }

    private void initView() {
        etImInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND){
                    ToastUtils.show(ContactCustomerServiceActivity.this,"发送成功");
                }
                return false;
            }
        });
    }
    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                ContactCustomerServiceActivity.this.finish();
                break;
        }
    }
    @Override
    public int initLayout() {
        return R.layout.activity_contact_customer_service;
    }

    @Override
    public void reloadData() {

    }
}
