package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class MyBalanceActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {
    private CommonDataInterface commonPresenter;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        commonPresenter = new CommonGoodsImp();
        initData();
    }

    private void initData() {
        if (((IPSCApplication) getApplication()).getUserInfo() == null) {
            return;
        }
        RequestParams params = new RequestParams(MainUrls.getUserbalanceUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        if(((IPSCApplication) getApplication()).getUserInfo()!=null){
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
        Log.e("TAG ","params:"+params.toJSONString());
        commonPresenter.getCommonGoodsData(params, this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_balance;
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MyBalanceActivity.this.finish();
                break;
        }
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取数据中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("MyBalanceActivity", "请求出错:" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            Log.e("TAG", "余额:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0){
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (data != null){
                        double balance1 = data.getDouble("可用");
                        double balance2 = data.getDouble("充值可用");
                        Log.e("TAG","执行到这里："+"￥"+new DecimalFormat("######0.00").format(balance1+balance2));
                        tvBalance.setText("￥"+new DecimalFormat("######0.00").format(balance1+balance2));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
