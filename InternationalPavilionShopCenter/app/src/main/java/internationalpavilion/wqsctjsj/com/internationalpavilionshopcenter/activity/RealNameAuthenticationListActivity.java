package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.Items;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeViewBinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.ViewHolder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.AuthenticationListImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.JSONUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAuthenticationListCallback;

public class RealNameAuthenticationListActivity extends BaseAppcompatActivity implements OnAuthenticationListCallback {

    public static final String TAG = "[IPSC][RealNameAuthenticationListActivity]";

    @BindView(R.id.ll_add_auth_man)
    LinearLayout llAddMan;
    @BindView(R.id.rv)
    RecyclerView mR;


    Items mData = new Items();
    MultiTypeAdapter adapter = new MultiTypeAdapter(mData);

    AuthenticationListImp authenticationListImp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        authenticationListImp = new AuthenticationListImp();

        initAdapter();
        getAuthenticationList();
    }

    /**
     * 获取实名认证列表
     */
    private void getAuthenticationList() {
        RequestParams params = new RequestParams(MainUrls.authenticationListUrl);
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
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

    }

    @Override
    public void onSuccess(String result) {

        mData.clear();
        AuthenticationListBean bean = JSONUtils.jsonFormat(result, AuthenticationListBean.class);

        if(bean==null || bean.getData() == null){
            return;
        }

        mData.addAll(bean.getData());

        adapter.notifyDataSetChanged();

    }


    @OnClick({R.id.ll_add_auth_man, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_auth_man:
                Intent intentToRealNameAuthor = new Intent(this, RealNameAuthenticationActivity.class);
                startActivity(intentToRealNameAuthor);
                break;
            case R.id.iv_back:
                RealNameAuthenticationListActivity.this.finish();
                break;
        }
    }



    private void initAdapter(){

        MultiTypeViewBinder<AuthenticationListBean.DataEntity> binder = new MultiTypeViewBinder<AuthenticationListBean.DataEntity>(this,R.layout.item_auth_layout) {
            @Override
            protected void convert(ViewHolder holder, final AuthenticationListBean.DataEntity data, int position) {

                final SwipeMenuLayout swipe= holder.getView(R.id.swipe);


                TextView nameTv = holder.getView(R.id.tv_name);
                TextView phoneTv = holder.getView(R.id.tv_phone);
                TextView isAuthTv = holder.getView(R.id.tv_is_auth);
                nameTv.setText(data.getName() == null ? "" : data.getName());
                // TODO 缺少电话字段
                phoneTv.setText(data.getCard() == null ? "" : data.getCard());
                // TODO 缺少判断是否认证标准
                isAuthTv.setText("已认证");

                //设为默认
                holder.getView(R.id.tv_default).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setDefault(data.getId());
                        swipe.smoothClose();
                    }
                });

                holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAuth(data.getId());
                        swipe.smoothClose();
                    }
                });

            }
        };
        adapter.register(AuthenticationListBean.DataEntity.class,binder);
        mR.setAdapter(adapter);
        mR.setLayoutManager(new LinearLayoutManager(this));
    }


    private void deleteAuth(int id){

        RequestParams params = new RequestParams(MainUrls.deleteAuthUrl);
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
        params.addBodyParameter("id", id+"");
        params.addBodyParameter("access_token", IPSCApplication.accessToken);

        x.http().post(params, new Callback.CommonCallback<JSONObject>() {

            @Override
            public void onSuccess(JSONObject result) {
                if(result!=null){
                    try {
                        boolean res = result.getBoolean("data");
                        if(res){
                            getAuthenticationList();
                            Toast.makeText(RealNameAuthenticationListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RealNameAuthenticationListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void setDefault(int id){
        RequestParams params = new RequestParams(MainUrls.setDefaultAuthInfoUrl);
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
        params.addBodyParameter("id", id+"");
        params.addBodyParameter("status","是");
        params.addBodyParameter("access_token", IPSCApplication.accessToken);

        x.http().post(params, new Callback.CommonCallback<JSONObject>() {

            @Override
            public void onSuccess(JSONObject result) {
                if(result!=null){
                    try {
                        boolean res = result.getBoolean("data");
                        if(res){
                            getAuthenticationList();
                            Toast.makeText(RealNameAuthenticationListActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RealNameAuthenticationListActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }





}



