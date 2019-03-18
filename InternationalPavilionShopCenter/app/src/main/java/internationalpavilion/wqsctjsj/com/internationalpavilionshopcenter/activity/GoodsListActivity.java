package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.GroupProductEntity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.Items;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeViewBinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.ViewHolder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.GoodsListPresenterImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.IGoodsListPresenter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.NetCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ScreenUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.SpannableUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;

//新品列表和折扣列表共用界面
public class GoodsListActivity extends BaseAppcompatActivity {

    private int type;//1.新品列表 2折扣列表

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    @BindView(R.id.rv)
    RecyclerView mRV;

    @BindView(R.id.tv_title)
    TextView mTvTitle;


    private MultiTypeAdapter adapter;
    private Items items = new Items();
    private int pageIndex = 1;
    private String rmb = Html.fromHtml("&yen").toString();
    private IGoodsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        presenter = new GoodsListPresenterImp();



        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                request(type);
            }
        });

        mRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                request(type);
            }
        });

        initRV();

        items.clear();
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 1);
        }

        if(1==type){
            mTvTitle.setText("新品列表");
        }

        if(2==type){
            mTvTitle.setText("折扣商品");
        }
        request(type);
    }

    private void initRV() {

        adapter = new MultiTypeAdapter(items);
        MultiTypeViewBinder<GroupProductEntity> binder = new MultiTypeViewBinder<GroupProductEntity>(this, R.layout.item_product_search_result) {
            @Override
            protected void convert(ViewHolder holder, final GroupProductEntity productEntity, int position) {
                android.widget.LinearLayout llBox = holder.getView(R.id.ll_box);
                ImageView ivProduct = holder.getView(R.id.iv_product);
                TextView tvProductName = holder.getView(R.id.tv_name);
                TextView tvDescribe = holder.getView(R.id.tv_describe);
                TextView tvSinglePrice = holder.getView(R.id.tv_price);

                //商品名字
                tvProductName.setText(productEntity.getGoods_goods().getName());

                //商品描述
                tvDescribe.setText(productEntity.getGoods_goods().getDescribe());
                //商品单价
                tvSinglePrice.setText(SpannableUtil.setSizeSpan(rmb + productEntity.getPrice_m(), 0, 1, 0.8f));

                //商品图片
                if (productEntity.getGoods_goods().getImg() != null && productEntity.getGoods_goods().getImg().size() > 0) {
                    Glide.with(GoodsListActivity.this).load(productEntity.getGoods_goods().getImg().get(0)).
                            apply(new RequestOptions().override(300, 300).
                                    placeholder(R.mipmap.ic_launcher).
                                    error(R.mipmap.ic_launcher)).
                            into(ivProduct);
                }

                llBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GoodsListActivity.this, GoodsDetailActivity.class);
                        intent.putExtra("goodsId", productEntity.getGoods_goods().getId());
                        startActivity(intent);
                    }
                });


            }
        };
        adapter.register(GroupProductEntity.class, binder);
        mRV.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildPosition(view);
                if (position % 2 == 0) {
                    outRect.left = ScreenUtil.dip2px(GoodsListActivity.this, 15f);
                    outRect.right = ScreenUtil.dip2px(GoodsListActivity.this, 7.5f);
                } else {
                    outRect.right = ScreenUtil.dip2px(GoodsListActivity.this, 15f);
                    outRect.left = ScreenUtil.dip2px(GoodsListActivity.this, 7.5f);
                }
            }
        });
        mRV.setLayoutManager(new GridLayoutManager(this, 2));
        mRV.setAdapter(adapter);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void reloadData() {

    }

    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void request(int type) {

        switch (type) {

            case 1:
                presenter.getNewlyGoodsList(generateParams(type), new NetCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stopRefreshAndLoadMore();
                        if (result != null) {
                            com.alibaba.fastjson.JSONObject obj = JSON.parseObject(result);
                            if (obj != null) {

                                int code = obj.getIntValue("code");
                                int state = obj.getIntValue("state");
                                if (0 == code && 0 == state) {

                                    com.alibaba.fastjson.JSONArray data_array = obj.getJSONArray("data");
                                    if (data_array != null && data_array.size() > 0) {
                                        List<GroupProductEntity> data = new ArrayList<>();

                                        for (int i = 0; i < data_array.size(); i++) {
                                            com.alibaba.fastjson.JSONObject obj_i = data_array.getJSONObject(i);
                                            GroupProductEntity gp = new GroupProductEntity();
                                            gp.setId(obj_i.getIntValue("id"));

                                            GroupProductEntity.GoodsGoodsBean goodsGoodsBean = new GroupProductEntity.GoodsGoodsBean();
                                            GroupProductEntity.StoreStoreBean storeStoreBean = new GroupProductEntity.StoreStoreBean();

                                            com.alibaba.fastjson.JSONObject goods_goods = obj_i.getJSONObject("goods_goods");

                                            goodsGoodsBean.setId(goods_goods.getIntValue("id"));
                                            //商品图片
                                            List<String> imgs = new ArrayList<>();
                                            if (!TextUtils.isEmpty(goods_goods.getString("img"))) {
                                                com.alibaba.fastjson.JSONArray array = JSON.parseArray(goods_goods.getString("img"));
                                                if (array != null) {
                                                    imgs = array.toJavaList(String.class);
                                                }
                                            }
                                            goodsGoodsBean.setImg(imgs);

                                            //商品名字
                                            goodsGoodsBean.setName(goods_goods.getString("name"));
                                            //商品描述
                                            goodsGoodsBean.setDescribe(goods_goods.getString("describe"));

                                            //拼团人数
                                            gp.setGroup_num_p(obj_i.getIntValue("group_num_p"));
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
                                            items.clear();
                                            if (data != null && data.size() > 0) {
                                                //showSuccessLayout();
                                                items.addAll(data);

                                                //已经加载完所有数据
                                                if (data.size() < 10) {
                                                    ToastUtils.show(GoodsListActivity.this, "已加载完所有数据");
                                                    mRefresh.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    mRefresh.setEnableLoadmore(true);
                                                }

                                            } else {
                                                //刷新或首次加载失败
                                                ToastUtils.show(GoodsListActivity.this, "数据加载失败");
                                                //showEmptyLayout();
                                            }

                                        } else if (pageIndex > 1) {
                                            //上拉加载时
                                            if (data != null && data.size() > 0) {

                                                items.addAll(data);

                                                if (data.size() < 10) {
//                                if (mItems.contains(bottomEntity)) {
//                                    mItems.remove(bottomEntity);
//                                    mItems.add(bottomEntity);
//                                } else {
//                                    mItems.add(bottomEntity);
//                                }
                                                    ToastUtils.show(GoodsListActivity.this, "已加载完所有数据");
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
                                                ToastUtils.show(GoodsListActivity.this, "已加载完所有数据");
                                                mRefresh.setEnableLoadmore(false);
                                            }

                                        }

                                        adapter.notifyDataSetChanged();

                                        if(items.size()>0){
                                            mRefresh.setVisibility(View.VISIBLE);
                                        }else {
                                            mRefresh.setVisibility(View.GONE);
                                        }


                                    }

                                }

                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        stopRefreshAndLoadMore();
                    }
                });
                break;

            case 2:
                presenter.getDiscountGoodsList(generateParams(type), new NetCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stopRefreshAndLoadMore();
                        if (result != null) {
                            com.alibaba.fastjson.JSONObject obj = JSON.parseObject(result);
                            if (obj != null) {

                                int code = obj.getIntValue("code");
                                int state = obj.getIntValue("state");
                                if (0 == code && 0 == state) {

                                    com.alibaba.fastjson.JSONArray data_array = obj.getJSONArray("data");
                                    if (data_array != null && data_array.size() > 0) {
                                        List<GroupProductEntity> data = new ArrayList<>();

                                        for (int i = 0; i < data_array.size(); i++) {
                                            com.alibaba.fastjson.JSONObject obj_i = data_array.getJSONObject(i);
                                            GroupProductEntity gp = new GroupProductEntity();
                                            gp.setId(obj_i.getIntValue("id"));

                                            GroupProductEntity.GoodsGoodsBean goodsGoodsBean = new GroupProductEntity.GoodsGoodsBean();
                                            GroupProductEntity.StoreStoreBean storeStoreBean = new GroupProductEntity.StoreStoreBean();

                                            com.alibaba.fastjson.JSONObject goods_goods = obj_i.getJSONObject("goods_goods");

                                            goodsGoodsBean.setId(goods_goods.getIntValue("id"));
                                            //商品图片
                                            List<String> imgs = new ArrayList<>();
                                            if (!TextUtils.isEmpty(goods_goods.getString("img"))) {
                                                com.alibaba.fastjson.JSONArray array = JSON.parseArray(goods_goods.getString("img"));
                                                if (array != null) {
                                                    imgs = array.toJavaList(String.class);
                                                }
                                            }
                                            goodsGoodsBean.setImg(imgs);

                                            //商品名字
                                            goodsGoodsBean.setName(goods_goods.getString("name"));
                                            //商品描述
                                            goodsGoodsBean.setDescribe(goods_goods.getString("describe"));

                                            //拼团人数
                                            gp.setGroup_num_p(obj_i.getIntValue("group_num_p"));
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
                                            items.clear();
                                            if (data != null && data.size() > 0) {
                                                //showSuccessLayout();
                                                items.addAll(data);

                                                //已经加载完所有数据
                                                if (data.size() < 10) {
                                                    ToastUtils.show(GoodsListActivity.this, "已加载完所有数据");
                                                    mRefresh.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    mRefresh.setEnableLoadmore(true);
                                                }

                                            } else {
                                                //刷新或首次加载失败
                                                ToastUtils.show(GoodsListActivity.this, "数据加载失败");
                                                //showEmptyLayout();
                                            }

                                        } else if (pageIndex > 1) {
                                            //上拉加载时
                                            if (data != null && data.size() > 0) {

                                                items.addAll(data);

                                                if (data.size() < 10) {
//                                if (mItems.contains(bottomEntity)) {
//                                    mItems.remove(bottomEntity);
//                                    mItems.add(bottomEntity);
//                                } else {
//                                    mItems.add(bottomEntity);
//                                }
                                                    ToastUtils.show(GoodsListActivity.this, "已加载完所有数据");
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
                                                ToastUtils.show(GoodsListActivity.this, "已加载完所有数据");
                                                mRefresh.setEnableLoadmore(false);
                                            }

                                        }

                                        adapter.notifyDataSetChanged();

                                        if(items.size()>0){
                                            mRefresh.setVisibility(View.VISIBLE);
                                        }else {
                                            mRefresh.setVisibility(View.GONE);
                                        }


                                    }

                                }

                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        stopRefreshAndLoadMore();
                    }
                });
                break;
        }

    }


    private void stopRefreshAndLoadMore() {
        if (mRefresh != null) {
            mRefresh.finishLoadmore();
            mRefresh.finishRefresh(true);
        }
    }


    private RequestParams generateParams(int type) {

        RequestParams params = null;

        if (1 == type) {
            params = new RequestParams(MainUrls.getNewlyGoodsListUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("page", pageIndex + "");
        }

        if (2 == type) {
            params = new RequestParams(MainUrls.getDiscountGoodsListUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("page", pageIndex + "");
        }


        return params;
    }

}