package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.GroupGoodsListAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.GroupProductEntity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.GroupProductListPresenterImpl;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.IGroupProductListPresenter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.JSONUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.IGroupProductListView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnGroupProductListCallback;


public class GroupGoodsListActivity extends BaseAppcompatActivity implements OnGroupProductListCallback {
    @BindView(R.id.rv_group_goods_list)
    RecyclerView rvGroupGoodsList;

    private VirtualLayoutManager layoutManager;
    private DelegateAdapter adapter;
    private GroupGoodsListAdapter adapter1, adapter2;
    private ArrayList<DelegateAdapter.Adapter> subAdapterList = new ArrayList<>();
    ArrayList<HashMap<String, Object>> data_2 = new ArrayList<>();

    private IGroupProductListPresenter presenter;
    private int pageIndex = 1;
    private SmartRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);

        presenter = new GroupProductListPresenterImpl();

        mRefresh = findViewById(R.id.refresh);

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
        mRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                initData();
            }
        });
        initRVLayout();
        initData();
    }

    private void initRVLayout() {
        layoutManager = new VirtualLayoutManager(this);
        rvGroupGoodsList.setLayoutManager(layoutManager);

        //2.设置组件复用回收池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(1, 1);
        recycledViewPool.setMaxRecycledViews(2, 30);
        rvGroupGoodsList.setRecycledViewPool(recycledViewPool);

        initLayoutHelper1();
        initLayoutHelper2();

        //设置适配器
        adapter = new DelegateAdapter(layoutManager, false);
        adapter.setAdapters(subAdapterList);
        rvGroupGoodsList.setAdapter(adapter);
        rvGroupGoodsList.setItemViewCacheSize(30);
    }

    private void initLayoutHelper1() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
        adapter1 = new GroupGoodsListAdapter(this, singleLayoutHelper, 1, data, 1);
        subAdapterList.add(adapter1);
    }

    private void initLayoutHelper2() {
        LinearLayoutHelper singleLayoutHelper = new LinearLayoutHelper();
        adapter2 = new GroupGoodsListAdapter(this, singleLayoutHelper, 0, data_2, 2);
        subAdapterList.add(adapter2);
    }

    private void initData() {

        try {
            if (TextUtils.isEmpty(IPSCApplication.accessToken)) {
                ToastUtils.show(this, "程序出错，请重启应用。");
                return;
            }
            RequestParams params = new RequestParams(MainUrls.getGroupProductListUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("page", String.valueOf(pageIndex));
            presenter.pullGroupProductList(params, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        presenter.pullGroupProductList(params, this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_group_goods_list;
    }

    @Override
    public void reloadData() {

    }

    @OnClick({R.id.iv_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }


    @Override
    public void onError(Throwable error) {
        stopRefreshAndLoadMore();
    }

    @Override
    public void onSuccess(String result) {

        stopRefreshAndLoadMore();

        if (TextUtils.isEmpty(result)) {
            return;
        }

        try {
            JSONObject root = new JSONObject(result);

            if (root != null) {
                int code = root.getInt("code");
                int state = root.getInt("state");
                if (state == 0 && code == 0) {
                    JSONArray data_array = root.getJSONArray("data");
                    if (data_array != null && data_array.length() > 0) {

                        List<GroupProductEntity> data = new ArrayList<>();
                        //List<GroupProductEntity> data = data_array.toJavaList(GroupProductEntity.class);

                        for (int i=0;i<data_array.length();i++){
                            JSONObject obj_i = data_array.getJSONObject(i);

                            GroupProductEntity gp = new GroupProductEntity();
                            gp.setId(obj_i.getInt("id"));

                            GroupProductEntity.GoodsGoodsBean goodsGoodsBean = new GroupProductEntity.GoodsGoodsBean();
                            GroupProductEntity.StoreStoreBean storeStoreBean = new GroupProductEntity.StoreStoreBean();

                            JSONObject goods_goods = obj_i.getJSONObject("goods_goods");

                            goodsGoodsBean.setId(goods_goods.getInt("id"));
                            //商品图片
                            List<String> imgs=new ArrayList<>();
                            if(!TextUtils.isEmpty(goods_goods.getString("img"))){
                                JSONArray array = new JSONArray(goods_goods.getString("img"));
                                if(array!=null){
                                    for (int p= 0;p < array.length();p++){
                                        imgs.add(array.getString(p));
                                    }
                                    //imgs = array.toJavaList(String.class);
                                }
                            }
                            goodsGoodsBean.setImg(imgs);

                            //商品名字
                            goodsGoodsBean.setName(goods_goods.getString("name"));
                            //商品描述
                            goodsGoodsBean.setDescribe(goods_goods.getString("describe"));



                            //拼团人数
                            gp.setGroup_num_p(obj_i.getInt("group_num_p"));
                            //商品单价
                            gp.setPrice_m(obj_i.getString("price_m"));
                            //商品团购价
                            gp.setGroup_price(obj_i.getString("group_price"));
                            //团购截止时间
                            gp.setPron_end_time(obj_i.getString("pron_end_time"));

                            gp.setGoods_goods(goodsGoodsBean);
                            gp.setStore_store(storeStoreBean);
                            data.add(gp);
                        }





                        //刷新或第一次加载
                        if (pageIndex == 1) {
                            data_2.clear();
                            if (data != null && data.size() > 0) {
                                //showSuccessLayout();
                                for (GroupProductEntity p : data) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("product", p);
                                    data_2.add(map);
                                }

                                //已经加载完所有数据
                                if (data.size() < 10) {
                                    ToastUtils.show(this, "已加载完所有数据");
                                    mRefresh.setEnableLoadmore(false);
                                } else {
                                    pageIndex++;
                                    mRefresh.setEnableLoadmore(true);
                                }

                            } else {
                                //刷新或首次加载失败
                                ToastUtils.show(this, "数据加载失败");
                                //showEmptyLayout();
                            }

                        } else if (pageIndex > 1) {
                            //上拉加载时
                            if (data != null && data.size() > 0) {

                                for (GroupProductEntity p : data) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("product", p);
                                    data_2.add(map);
                                }

                                if (data.size() < 10) {
//                                if (mItems.contains(bottomEntity)) {
//                                    mItems.remove(bottomEntity);
//                                    mItems.add(bottomEntity);
//                                } else {
//                                    mItems.add(bottomEntity);
//                                }
                                    ToastUtils.show(this, "已加载完所有数据");
                                    //上拉加载完所有数据，禁止上拉事件
                                    mRefresh.setEnableLoadmore(false);
                                } else {
                                    pageIndex++;
                                    mRefresh.setEnableLoadmore(true);
                                }
                            } else {
                                //上拉加载完了所有数据
//                            if (mItems.contains(bottomEntity)) {
//                                mItems.remove(bottomEntity);
//                                mItems.add(bottomEntity);
//                            } else {
//                                mItems.add(bottomEntity);
//                            }
                                ToastUtils.show(this, "已加载完所有数据");
                                mRefresh.setEnableLoadmore(false);
                            }

                        }

                        adapter2.notifyDataSetChanged();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void stopRefreshAndLoadMore() {
        if (mRefresh != null) {
            mRefresh.finishLoadmore();
            mRefresh.finishRefresh(true);
        }
    }


}
