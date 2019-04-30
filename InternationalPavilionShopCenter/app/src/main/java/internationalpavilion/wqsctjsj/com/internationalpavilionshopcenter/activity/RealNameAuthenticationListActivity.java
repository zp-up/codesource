package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AuthenticationListBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBanner.BannerRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.AuthenticationListImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.JSONUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAuthenticationListCallback;

public class RealNameAuthenticationListActivity extends BaseAppcompatActivity implements OnAuthenticationListCallback {

    public static final String TAG = "[IPSC][RealNameAuthenticationListActivity]";
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.ll_add_auth_man)
    LinearLayout llAddMan;
    AuthenticationListImp authenticationListImp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        authenticationListImp = new AuthenticationListImp();
        getAuthenticationList();
    }

    /**
     * 获取实名认证列表
     */
    private void getAuthenticationList() {
        RequestParams params = new RequestParams(MainUrls.authenticationListUrl);
        params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        params.addBodyParameter("page", "1");
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("limit", "10");
        authenticationListImp.getAuthenticationList(params, this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_real_name_authentication_list;
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
        Log.e(TAG, "出错:" + error);
    }

    @Override
    public void onSuccess(String result) {
        Log.d(TAG, "实名认证列表数据:" + result);
        AuthenticationListBean bean = JSONUtils.jsonFormat(result, AuthenticationListBean.class);
        if (null != bean) {
            List<AuthenticationListBean.DataEntity> data = bean.getData();
            if (null != data && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    AuthenticationListBean.DataEntity dataEntity = data.get(i);
                    View view = LayoutInflater.from(RealNameAuthenticationListActivity.this).inflate(R.layout.item_authentication, null);
                    TextView nameTv = view.findViewById(R.id.tv_name);
                    TextView phoneTv = view.findViewById(R.id.tv_phone);
                    TextView isAuthTv = view.findViewById(R.id.tv_is_auth);
                    nameTv.setText(dataEntity.getName() == null ? "" : dataEntity.getName());
                    // TODO 缺少电话字段
                    phoneTv.setText(dataEntity.getCard() == null ? "" : dataEntity.getCard());
                    // TODO 缺少判断是否认证标准
                    isAuthTv.setText("已认证");
                    llList.addView(view);
                }
            } else {
                Log.d(TAG, "data = " + data);
            }
        } else {
            Log.d(TAG, "AuthenticationListBean = " + bean);
        }

    }

    @OnClick({R.id.ll_add_auth_man})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_auth_man:
                Intent intentToRealNameAuthor = new Intent(this, RealNameAuthenticationActivity.class);
                startActivity(intentToRealNameAuthor);
                break;
        }
    }
}
