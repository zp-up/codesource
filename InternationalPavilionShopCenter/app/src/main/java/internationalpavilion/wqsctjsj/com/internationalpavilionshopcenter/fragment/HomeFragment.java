package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.chrisjason.baseui.util.DpUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.SearchActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.HomeAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.BondedGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBanner.BannerRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBanner.Data;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBondedGoodsBean.HomeBondedGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeHotSaleGoods.HotGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeLimitGoods.LimitTimeGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeOverseasGoodsList.HomeOverSeasGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homePopularityGoods.HomePopularityGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeWishGoodsBean.HomeWishGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.HomeDataImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.HomeDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnHomeDataCallBack;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class HomeFragment extends Fragment implements OnHomeDataCallBack {

    private static final String TAG = "[IPSC][HomeFragment]";
    private Unbinder unbinder;
    private DelegateAdapter adapter;
    private HomeAdapter homeAdapter1, homeAdapter2, homeAdapter3, homeAdapter4, homeAdapter5, homeAdapter6, homeAdapter7, homeAdapter8, homeAdapter9, homeAdapter10;
    private List<DelegateAdapter.Adapter> subAdapterList = new LinkedList<>();
    private VirtualLayoutManager layoutManager;
    private ArrayList<HashMap<String, Object>> bannerData = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> limitTimeGoodsData = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> hotSaleGoodsData = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> bondedGoodsData = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> overseasGoodsData = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> wishGoodsData = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> popularityGoodsData = new ArrayList<>();
    @BindView(R.id.rv_home)
    RecyclerView mRV;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.rl_search_container)
    RelativeLayout rlSearchContainer;
    @BindView(R.id.view_status_bar)
    View viewStatusBar;
    @BindView(R.id.refresh)
    SmartRefreshLayout srlContent;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.return_top)
    ImageView ivTop;

    private HomeDataInterface homePresenter;
    private int pageIndex = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRVL();
        homePresenter = new HomeDataImp();
        return view;
    }

    //获取banner数据
    private void getBannerData() {
        try {
            if (TextUtils.isEmpty(IPSCApplication.accessToken)) {
                ToastUtils.show(getActivity(), "程序出错，请重启应用。");
                return;
            }
            RequestParams params = new RequestParams(MainUrls.getBannerDataUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("app", "1");
            params.addBodyParameter("type", "轮播大屏");
            homePresenter.getBannerData(params, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_search_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_search_container:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void initData() {
        getBannerData();
        getLimitTimeGoodsList();
        getHotSaleGoodsList();
        getBondedGoodsList();
        getOverseasGoodsList();
        getWishGoodsList();
        getPopularityGoodsList();
    }

    private void getMorePopularityGoodsList() {
        pageIndex++;
        RequestParams params = new RequestParams(MainUrls.getPopularityGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        homePresenter.getPopularityGoodsList(params, this);
    }

    private void getPopularityGoodsList() {
        pageIndex = 1;
        RequestParams params = new RequestParams(MainUrls.getPopularityGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        homePresenter.getPopularityGoodsList(params, this);
    }

    private void getWishGoodsList() {
        RequestParams params = new RequestParams(MainUrls.getWishGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page", "1");
        params.addBodyParameter("limit", "9");
        UserBean userInfo = ((IPSCApplication) getActivity().getApplicationContext()).getUserInfo();
        if (userInfo != null) {
            params.addBodyParameter("user", userInfo.getId() + "");
        }
        homePresenter.getWishGoodsList(params, this);
    }

    private void getOverseasGoodsList() {
        RequestParams params = new RequestParams(MainUrls.getOverseasGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page", "1");
        params.addBodyParameter("limit", "9");
        homePresenter.getOverseasGoodsList(params, this);
    }

    private void getBondedGoodsList() {
        RequestParams params = new RequestParams(MainUrls.getBondedGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page", "1");
        params.addBodyParameter("limit", "9");
        homePresenter.getBondedGoodsList(params, this);
    }

    private void getHotSaleGoodsList() {
        RequestParams params = new RequestParams(MainUrls.getHotSaleGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page", "1");
        params.addBodyParameter("limit", "9");
        homePresenter.getHotSaleGoodsList(params, this);
    }

    private void getLimitTimeGoodsList() {
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int startTime = hour;
        int endTime = 0;
        if (hour % 4 == 1) {
            startTime -= 1;
        } else if (hour % 4 == 2) {
            startTime -= 2;
        } else if (hour % 4 == 3) {
            startTime -= 3;
        } else if (hour % 4 == 0) {
            startTime = hour;
        }
        endTime = startTime + 4;
        Log.e(TAG, "startTime:" + startTime + " ---  endTime:" + endTime);
        RequestParams params = new RequestParams(MainUrls.getLimitTimeGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("time", "" + startTime);
        params.addBodyParameter("space", "4");
        homePresenter.getLimitTimeGoodsList(params, this);
    }

    private void initRVL() {
        layoutManager = new VirtualLayoutManager(getActivity());
        mRV.setLayoutManager(layoutManager);

        //2.设置组件复用回收池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(1, 1);
        recycledViewPool.setMaxRecycledViews(2, 1);
        recycledViewPool.setMaxRecycledViews(3, 1);
        recycledViewPool.setMaxRecycledViews(4, 1);
        recycledViewPool.setMaxRecycledViews(5, 1);
        recycledViewPool.setMaxRecycledViews(6, 1);
        recycledViewPool.setMaxRecycledViews(7, 1);
        recycledViewPool.setMaxRecycledViews(8, 1);
        recycledViewPool.setMaxRecycledViews(9, 1);
        recycledViewPool.setMaxRecycledViews(10, 1);
        mRV.setRecycledViewPool(recycledViewPool);

        initLayoutHelper1();
        initLayoutHelper2();
        initLayoutHelper3();
        initLayoutHelper4();
        initLayoutHelper5();
        initLayoutHelper6();
        initLayoutHelper7();
        initLayoutHelper8();
        initLayoutHelper9();
        initLayoutHelper10();
        //设置适配器
        adapter = new DelegateAdapter(layoutManager, false);
        adapter.setAdapters(subAdapterList);
        mRV.setAdapter(adapter);
        mRV.setItemViewCacheSize(30);
        mRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float y = getScollYDistance(layoutManager);
                float trueY = DpUtils.pxToDp(getActivity(), y);
                if (trueY > 145) {
                    llHeader.setBackgroundColor(Color.parseColor("#ffffff"));
                    rlSearchContainer.setBackgroundResource(R.drawable.shape_of_search_cotainer_gray);
                    viewStatusBar.setBackgroundColor(Color.parseColor("#c0c0c0"));
                } else {
                    llHeader.setBackgroundColor(Color.parseColor("#00ffffff"));
                    rlSearchContainer.setBackgroundResource(R.drawable.shape_of_search_cotainer);
                    viewStatusBar.setBackgroundColor(Color.parseColor("#00ffffff"));
                }
                if (layoutManager.findFirstVisibleItemPosition() >= 14) {
                    ivTop.setVisibility(View.VISIBLE);
                } else {
                    ivTop.setVisibility(View.GONE);
                }
            }
        });
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
                getMorePopularityGoodsList();
            }
        });

        ivTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRV.smoothScrollToPosition(0);
            }
        });
    }

    private void initLayoutHelper1() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        homeAdapter1 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, bannerData, 1);
        subAdapterList.add(homeAdapter1);
    }

    private void initLayoutHelper2() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
        homeAdapter2 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, data, 2);
        subAdapterList.add(homeAdapter2);
    }

    private void initLayoutHelper3() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        homeAdapter3 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, limitTimeGoodsData, 3);
        subAdapterList.add(homeAdapter3);
    }

    private void initLayoutHelper4() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
        homeAdapter4 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, data, 4);
        subAdapterList.add(homeAdapter4);
    }

    private void initLayoutHelper5() {
        GridLayoutHelper singleLayoutHelper = new GridLayoutHelper(3);
        homeAdapter5 = new HomeAdapter(getActivity(), singleLayoutHelper, hotSaleGoodsData.size(), hotSaleGoodsData, 5);
        subAdapterList.add(homeAdapter5);
    }

    private void initLayoutHelper6() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        homeAdapter6 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, overseasGoodsData, 6);
        subAdapterList.add(homeAdapter6);
    }

    private void initLayoutHelper7() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        homeAdapter7 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, bondedGoodsData, 7);
        subAdapterList.add(homeAdapter7);
    }

    private void initLayoutHelper8() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        homeAdapter8 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, wishGoodsData, 8);
        subAdapterList.add(homeAdapter8);
    }

    private void initLayoutHelper9() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
        homeAdapter9 = new HomeAdapter(getActivity(), singleLayoutHelper, 1, data, 9);
        subAdapterList.add(homeAdapter9);
    }

    private void initLayoutHelper10() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        homeAdapter10 = new HomeAdapter(getActivity(), gridLayoutHelper, popularityGoodsData.size(), popularityGoodsData, 10);
        subAdapterList.add(homeAdapter10);
    }

    public int getScollYDistance(LinearLayoutManager manager) {
        LinearLayoutManager layoutManager = manager;
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    //请求开始
    @Override
    public void onStarted() {
        ((BaseAppcompatActivity)getActivity()).showLoading(false,"获取数据中...");
    }

    //请求结束
    @Override
    public void onFinished() {
        ((BaseAppcompatActivity)getActivity()).dismissLoading();
        srlContent.finishLoadmore();
        srlContent.finishRefresh();
    }

    //请求错误
    @Override
    public void onError(String error) {
        Log.e(TAG, "出错:" + error);
    }

    //请求成功
    @Override
    public void onGetBannerData(String result) {
        LogUtil.d(TAG, "banner请求结果:" + result);
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    Gson gson = new Gson();
                    BannerRootBean rootBean = gson.fromJson(result, BannerRootBean.class);
                    if (rootBean.getData() != null && rootBean.getData().size() != 0) {
                        if (bannerData.size() != 0) {
                            bannerData.clear();
                            HashMap<String, Object> dataMap = new HashMap<>();
                            dataMap.put("bannerData", rootBean.getData());
                            bannerData.add(dataMap);
                            homeAdapter1.notifyDataSetChanged();
                        } else {
                            HashMap<String, Object> dataMap = new HashMap<>();
                            dataMap.put("bannerData", rootBean.getData());
                            bannerData.add(dataMap);
                            homeAdapter1.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取限时购的商品
    @Override
    public void onLimitTimeGoodsLoaded(String result) {
        LogUtil.d(TAG, "限时购商品:" + result);
        if (result != null) {
            long time = System.currentTimeMillis();
            final Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(time);
            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            int startTime = hour;
            int endTime = 0;
            if (hour % 4 == 1) {
                startTime -= 1;
            } else if (hour % 4 == 2) {
                startTime -= 2;
            } else if (hour % 4 == 3) {
                startTime -= 3;
            } else if (hour % 4 == 0) {
                startTime = hour;
            }
            endTime = startTime + 4;
            LogUtil.d(TAG, "startTime:" + startTime + " ---  endTime:" + endTime);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data")) {
                        ArrayList<LimitTimeGoodsBean> goodsList = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if(jsonArray.length()>0){
                            limitTimeGoodsData.clear();
                            if (!subAdapterList.contains(homeAdapter3)) {
                                subAdapterList.add(homeAdapter3);
                                adapter.setAdapters(subAdapterList);
                            }
                            for (int i = 0; i < jsonArray.length(); i++) {
                                int id = jsonArray.getJSONObject(i).getInt("id");
                                String goodsName = jsonArray.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                                double price = jsonArray.getJSONObject(i).getDouble("price");
                                String picUrl = jsonArray.getJSONObject(i).getJSONObject("goods_goods").getJSONArray("img").getString(0);
                                long startTimes = jsonArray.getJSONObject(i).getLong("pron_add_time");
                                long endTimes = jsonArray.getJSONObject(i).getLong("pron_end_time");
                                LimitTimeGoodsBean goodsBean = new LimitTimeGoodsBean();
                                goodsBean.setEndTime(endTimes);
                                goodsBean.setGoodsImg(picUrl);
                                goodsBean.setGoodsName(goodsName);
                                goodsBean.setId(id);
                                goodsBean.setEndHour(endTime);
                                goodsBean.setPrice(price);
                                goodsBean.setStartTime(startTimes);
                                goodsList.add(goodsBean);
                            }
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("limitTimeGoods", goodsList);
                            limitTimeGoodsData.add(map);
                            homeAdapter3.notifyDataSetChanged();
                        }else{
                            subAdapterList.remove(homeAdapter3);
                            adapter.setAdapters(subAdapterList);
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取到热门商品
    @Override
    public void onHotSaleGoodsLoaded(String result) {
        if (result != null) {
            LogUtil.d(TAG, "热门商品获取:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (state == 0 && code == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data != null && data.length() != 0) {
                            hotSaleGoodsData.clear();
                            homeAdapter5.notifyDataSetChanged();
                            for (int i = 0; i < data.length(); i++) {
                                HotGoodsBean bean = new HotGoodsBean();
                                int id = data.getJSONObject(i).getInt("id");
                                String name = data.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                                double price = data.getJSONObject(i).getDouble("price_m");
                                String goodsPic = TextUtils.isEmpty(data.getJSONObject(i).getJSONObject("goods_goods").getString("img"))?"":data.getJSONObject(i).getJSONObject("goods_goods").getJSONArray("img").getString(0);
                                bean.setGoodsId(id);
                                bean.setGoodsName(name);
                                bean.setGoodsPic(goodsPic);
                                bean.setGoodsPrice(price);
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("hotGoods", bean);
                                hotSaleGoodsData.add(map);
                            }
                            homeAdapter5.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取保税商品列表
    @Override
    public void onBondedGoodsLoaded(String result) {
        if (result != null) {
            LogUtil.d(TAG, "保税商品列表:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (state == 0 && code == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data != null && data.length() != 0) {
                            bondedGoodsData.clear();
                            homeAdapter7.notifyDataSetChanged();
                            ArrayList<HomeBondedGoodsBean> goodsBeans = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                HomeBondedGoodsBean bean = new HomeBondedGoodsBean();
                                int id = data.getJSONObject(i).getInt("id");
                                String name = data.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                                double price = data.getJSONObject(i).getDouble("price_m");
                                String goodsPic = data.getJSONObject(i).getJSONObject("goods_goods").getJSONArray("img").getString(0);
                                bean.setGoodsId(id);
                                bean.setGoodsName(name);
                                bean.setGoodsPic(goodsPic);
                                bean.setGoodsPrice(price);
                                goodsBeans.add(bean);
                            }
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("bondedGoods", goodsBeans);
                            bondedGoodsData.add(map);
                            homeAdapter7.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOverseasGoodsLoaded(String result) {
        if (result != null) {
            LogUtil.d(TAG, "海外直邮商品列表:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (state == 0 && code == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data != null && data.length() != 0) {
                            overseasGoodsData.clear();
                            homeAdapter6.notifyDataSetChanged();
                            ArrayList<HomeOverSeasGoodsBean> goodsBeans = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                HomeOverSeasGoodsBean bean = new HomeOverSeasGoodsBean();
                                int id = data.getJSONObject(i).getInt("id");
                                String name = data.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                                double price = data.getJSONObject(i).getDouble("price_m");
                                String goodsPic = data.getJSONObject(i).getJSONObject("goods_goods").getJSONArray("img").getString(0);
                                bean.setGoodsId(id);
                                bean.setGoodsName(name);
                                bean.setGoodsPic(goodsPic);
                                bean.setGoodsPrice(price);
                                goodsBeans.add(bean);
                            }
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("overseasGoods", goodsBeans);
                            overseasGoodsData.add(map);
                            homeAdapter6.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //心愿商品
    @Override
    public void onWishGoodsLoaded(String result) {
        if (result != null) {
            LogUtil.d(TAG, "心愿清单:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (state == 0 && code == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data != null && data.length() != 0) {
                            wishGoodsData.clear();
                            homeAdapter8.notifyDataSetChanged();
                            ArrayList<HomeWishGoodsBean> goodsBeans = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                HomeWishGoodsBean bean = new HomeWishGoodsBean();
                                int id = data.getJSONObject(i).getInt("id");
                                String name = data.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                                double price = data.getJSONObject(i).getDouble("price_m");
                                String goodsPic = data.getJSONObject(i).getJSONObject("goods_goods").getJSONArray("img").getString(0);
                                bean.setGoodsId(id);
                                bean.setGoodsName(name);
                                bean.setGoodsPic(goodsPic);
                                bean.setGoodsPrice(price);
                                goodsBeans.add(bean);
                            }
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("wishGoods", goodsBeans);
                            wishGoodsData.add(map);
                            homeAdapter8.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //人气榜
    @Override
    public void onPopularityGoodsLoaded(String result) {
        if (result != null) {
            LogUtil.d(TAG, "人气榜:" + result);
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
                                homeAdapter10.notifyDataSetChanged();
                            }
                            for (int i = 0; i < data.length(); i++) {
                                HomePopularityGoodsBean bean = new HomePopularityGoodsBean();
                                int id = data.getJSONObject(i).getInt("id");
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
                            homeAdapter10.notifyDataSetChanged();
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
