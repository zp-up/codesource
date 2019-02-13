package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.AddressAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AddressBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class ReceivedAddressListActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;

    private ArrayList<AddressBean> data = new ArrayList<>();
    private AddressAdapter adapter;

    private CommonDataInterface commonPresenter;
    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initViews();
        commonPresenter = new CommonGoodsImp();
        initData();

    }

    private void initViews() {
        srlContent.setEnableLoadmore(true);
        srlContent.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                initData();
            }
        });
        srlContent.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
                initData();
            }
        });
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getAddressListUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        commonPresenter.getCommonGoodsData(params, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter == null) {
            adapter = new AddressAdapter(this, data);
            rvAddress.setLayoutManager(new LinearLayoutManager(this));
            rvAddress.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_add_new_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ReceivedAddressListActivity.this.finish();
                break;
            case R.id.tv_add_new_address:
                Intent intent = new Intent(ReceivedAddressListActivity.this, AddOrEditAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_received_address_list;
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
        srlContent.finishLoadmore();
        srlContent.finishRefresh();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "获取数据中...");
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (pageIndex == 1) {
                        ReceivedAddressListActivity.this.data.clear();
                        adapter.notifyDataSetChanged();
                    }
                    if (data != null && data.length() != 0) {
                        for (int i = 0; i < data.length(); i++) {
                            AddressBean addressBean = new AddressBean();
                            int id = data.getJSONObject(i).getInt("id");
                            String name = data.getJSONObject(i).getString("name");
                            String area = data.getJSONObject(i).getString("area");
                            String city = data.getJSONObject(i).getString("city");
                            String province = data.getJSONObject(i).getString("province");
                            int isDefault = data.getJSONObject(i).getInt("status");
                            String detailPlace = data.getJSONObject(i).getString("address");
                            String phone = data.getJSONObject(i).getString("telphone");
                            addressBean.setArea(area);
                            addressBean.setChecked(isDefault == 0 ? true : false);
                            addressBean.setCity(city);
                            addressBean.setDetailPlace(detailPlace);
                            addressBean.setId(id);
                            addressBean.setProvince(province);
                            addressBean.setReceiveName(name);
                            addressBean.setReceivePhone(phone);
                            ReceivedAddressListActivity.this.data.add(addressBean);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
