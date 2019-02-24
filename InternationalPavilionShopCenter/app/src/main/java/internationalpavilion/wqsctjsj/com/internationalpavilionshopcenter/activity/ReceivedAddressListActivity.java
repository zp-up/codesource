package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.View;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.AddressAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AddressBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.AddressUpdateEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class ReceivedAddressListActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;

    private ArrayList<AddressBean> data = new ArrayList<>();
    private AddressAdapter adapter;

    private CommonDataInterface commonPresenter;
    private NetRequestInterface netRequestInterface;
    private int pageIndex = 1;
    private int flag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        flag = getIntent().getIntExtra("flag", -1);
        EventBus.getDefault().register(this);
        initViews();
        commonPresenter = new CommonGoodsImp();
        netRequestInterface = new NetRequestImp();
        initData();
    }


    @Subscribe
    public void onEvent(AddressUpdateEvent event) {
        if (event != null) {
            if (event.isRes()) {
                data.clear();
                pageIndex = 1;
                initData();
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initViews() {

        if (adapter == null) {
            adapter = new AddressAdapter(this, data);
            rvAddress.setLayoutManager(new LinearLayoutManager(this));
            rvAddress.setAdapter(adapter);
        }

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
                initData();
            }
        });
        if (flag == 1) {
            adapter.setListener(new AddressAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int id) {
                    Log.e("TAG", "地址Id:" + id);
                    RequestParams params = new RequestParams(MainUrls.setAddressDefaultUrl);
                    params.addBodyParameter("access_token", IPSCApplication.accessToken);
                    params.addBodyParameter("id", id + "");
                    netRequestInterface.doNetRequest(params, new NetCallBack() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public void onError(Throwable error) {

                        }

                        @Override
                        public void onSuccess(String result) {
                            Log.e("TAG", "设置默认地址:" + result);
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int code = jsonObject.getInt("code");
                                int state = jsonObject.getInt("state");
                                if (code == 0 && state == 0) {
                                    AddressUpdateEvent event = new AddressUpdateEvent();
                                    event.setOp(1);
                                    event.setRes(false);
                                    EventBus.getDefault().post(event);
                                    ReceivedAddressListActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getAddressListUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        commonPresenter.getCommonGoodsData(params, this);
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
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    //临时存放数据，只为了手动解析数据
                    List<AddressBean> tmpList = new ArrayList<>();

                    if (data != null && dataArray.length() > 0) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            AddressBean addressBean = new AddressBean();
                            int id = dataArray.getJSONObject(i).getInt("id");
                            String name = dataArray.getJSONObject(i).getString("name");
                            String area = dataArray.getJSONObject(i).getString("area");
                            String city = dataArray.getJSONObject(i).getString("city");
                            String province = dataArray.getJSONObject(i).getString("province");
                            int isDefault = dataArray.getJSONObject(i).getInt("status");
                            String detailPlace = dataArray.getJSONObject(i).getString("address");
                            String phone = dataArray.getJSONObject(i).getString("telphone");
                            addressBean.setArea(area);
                            addressBean.setChecked(isDefault == 0 ? true : false);
                            addressBean.setCity(city);
                            addressBean.setDetailPlace(detailPlace);
                            addressBean.setId(id);
                            addressBean.setProvince(province);
                            addressBean.setReceiveName(name);
                            addressBean.setReceivePhone(phone);
                            tmpList.add(addressBean);
                        }
                    }


                    //刷新或第一次加载
                    if (pageIndex == 1) {
                        data.clear();
                        if (tmpList != null && tmpList.size() > 0) {
                            data.addAll(tmpList);

                            //已经加载完所有数据
                            if (tmpList.size() < 10) {
                                ToastUtils.show(this, "已加载完所有数据");
                                srlContent.setEnableLoadmore(false);
                            } else {
                                pageIndex++;
                                srlContent.setEnableLoadmore(true);
                            }
                        } else {
                            //刷新或首次加载失败
                            ToastUtils.show(this, "数据加载失败");
                        }

                    } else if (pageIndex > 1) {
                        //上拉加载时
                        if (tmpList != null && tmpList.size() > 0) {

                            data.addAll(tmpList);
                            if (tmpList.size() < 10) {
                                ToastUtils.show(this, "已加载完所有数据");
                                //上拉加载完所有数据，禁止上拉事件
                                srlContent.setEnableLoadmore(false);
                            } else {
                                pageIndex++;
                                srlContent.setEnableLoadmore(true);
                            }
                        } else {
                            //上拉加载完了所有数据
                            ToastUtils.show(this, "已加载完所有数据");
                            srlContent.setEnableLoadmore(false);
                        }

                    }

                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {

            }

        }

    }
}
