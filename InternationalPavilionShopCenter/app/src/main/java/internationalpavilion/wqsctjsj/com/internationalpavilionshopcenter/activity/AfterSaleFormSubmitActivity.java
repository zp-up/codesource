package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;

public class AfterSaleFormSubmitActivity extends BaseAppcompatActivity {
    @BindView(R.id.tv_reason_text)
    TextView tvReasonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }
    @OnClick({R.id.iv_back,R.id.rl_reason_select})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                AfterSaleFormSubmitActivity.this.finish();
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
        }
    }
    @Override
    public int initLayout() {
        return R.layout.activity_after_sale_form_submit;
    }

    @Override
    public void reloadData() {

    }
}
