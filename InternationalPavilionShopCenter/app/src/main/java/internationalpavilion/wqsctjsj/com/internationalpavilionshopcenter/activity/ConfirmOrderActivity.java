package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;

public class ConfirmOrderActivity extends BaseAppcompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    @OnClick({R.id.iv_back, R.id.ll_to_address_list,R.id.tv_to_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ConfirmOrderActivity.this.finish();
                break;
            case R.id.ll_to_address_list:
                Intent intent = new Intent(ConfirmOrderActivity.this, ReceivedAddressListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_to_pay:
                Intent intentToPay = new Intent(ConfirmOrderActivity.this,PayWayActivity.class);

                startActivity(intentToPay);
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void reloadData() {

    }
}
