package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.ClassPopularityAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homePopularityGoods.HomePopularityGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.HomeDataImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.HomeDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnHomeDataCallBack;

/**
 * Created by wuqaing on 2018/11/30.
 */

public class ClassInPopularityFragment extends Fragment implements OnHomeDataCallBack{

    public static final String TAG = "[IPSC][ClassInPopularityFragment]";
    private Unbinder unbinder;
    @BindView(R.id.rv_class_in_popularity)
    RecyclerView rvClassInPopularity;
    @BindView(R.id.refresh)
    SmartRefreshLayout srlContent;
    @BindView(R.id.tv_remind)
    TextView tvRemind;

    private DelegateAdapter adapter;
    private ClassPopularityAdapter classAdapter1,classAdapter2;
    private List<DelegateAdapter.Adapter> subAdapterList = new LinkedList<>();
    private VirtualLayoutManager layoutManager;
    private HomeDataInterface homeDataPresenter;
    private ArrayList<HashMap<String, Object>> popularityGoodsData = new ArrayList<>();
    private int pageIndex = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_in_popularity, container, false);
        unbinder = ButterKnife.bind(this, view);
        homeDataPresenter = new HomeDataImp();
        initRVLayoutHelper();
        return view;
    }

    private void initRVLayoutHelper() {
        layoutManager = new VirtualLayoutManager(getActivity());
        rvClassInPopularity.setLayoutManager(layoutManager);

        //2.设置组件复用回收池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(1, 1);
        recycledViewPool.setMaxRecycledViews(2, 1);
        rvClassInPopularity.setRecycledViewPool(recycledViewPool);
        initLayoutHelper1();
        initLayoutHelper2();
        //设置适配器
        adapter = new DelegateAdapter(layoutManager, false);
        adapter.setAdapters(subAdapterList);
        rvClassInPopularity.setAdapter(adapter);
        rvClassInPopularity.setItemViewCacheSize(30);
        srlContent.setEnableLoadmore(true);
        srlContent.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
        srlContent.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMoreData();
            }
        });
        initData();
    }

    private void initData() {
        pageIndex = 1;
        RequestParams params = new RequestParams(MainUrls.getPopularityGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page",pageIndex+"");
        params.addBodyParameter("limit","10");
        homeDataPresenter.getPopularityGoodsList(params,this);
    }
    private void getMoreData() {
        pageIndex++;
        RequestParams params = new RequestParams(MainUrls.getPopularityGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page",pageIndex+"");
        params.addBodyParameter("limit","10");
        homeDataPresenter.getPopularityGoodsList(params,this);
    }
    private void initLayoutHelper1() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
        classAdapter1 = new ClassPopularityAdapter(getActivity(), singleLayoutHelper, data.size(), data, 1);
        subAdapterList.add(classAdapter1);
    }

    private void initLayoutHelper2() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        classAdapter2 = new ClassPopularityAdapter(getActivity(), gridLayoutHelper, popularityGoodsData.size(), popularityGoodsData, 2);
        subAdapterList.add(classAdapter2);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onStarted() {
        ((BaseAppcompatActivity)getActivity()).showLoading(false,"获取数据中...");
    }

    @Override
    public void onFinished() {
        ((BaseAppcompatActivity)getActivity()).dismissLoading();
        srlContent.finishRefresh();
        srlContent.finishLoadmore();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onGetBannerData(String result) {

    }

    @Override
    public void onLimitTimeGoodsLoaded(String result) {

    }

    @Override
    public void onHotSaleGoodsLoaded(String result) {

    }

    @Override
    public void onBondedGoodsLoaded(String result) {

    }

    @Override
    public void onOverseasGoodsLoaded(String result) {

    }

    @Override
    public void onWishGoodsLoaded(String result) {

    }
    //获取人气榜
    @Override
    public void onPopularityGoodsLoaded(String result) {
        if (result != null) {
            Log.e(TAG, "人气榜:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (state == 0 && code == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data != null && data.length() != 0) {
                            if (pageIndex == 1){
                                popularityGoodsData.clear();
                                classAdapter2.notifyDataSetChanged();
                            }
                            for (int i = 0; i < data.length(); i++) {
                                HomePopularityGoodsBean bean = new HomePopularityGoodsBean();
                                int id = data.getJSONObject(i).getJSONObject("goods_goods").getInt("id");
                                String name = data.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                                double price = data.getJSONObject(i).getDouble("price_m");
                                String goodsPic = !TextUtils.isEmpty(data.getJSONObject(i).getJSONObject("goods_goods").getString("img"))?data.getJSONObject(i).getJSONObject("goods_goods").getJSONArray("img").getString(0):"";
                                String storeType = data.getJSONObject(i).getJSONObject("store_store").getString("type");
                                bean.setGoodsId(id);
                                bean.setGoodsName(name);
                                bean.setGoodsPic(goodsPic);
                                bean.setGoodsPrice(price);
                                bean.setStoreType(storeType);
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("popularityGoods", bean);
                                popularityGoodsData.add(map);
                            }
                            classAdapter2.notifyDataSetChanged();
                        }else {
                            tvRemind.setVisibility(View.VISIBLE);
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (tvRemind != null){
                                                tvRemind.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                            },1500);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
