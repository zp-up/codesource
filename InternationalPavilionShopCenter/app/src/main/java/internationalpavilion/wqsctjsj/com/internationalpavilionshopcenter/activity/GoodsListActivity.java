package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.chrisjason.baseui.util.DpUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.GroupProductEntity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.PriceData;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassInChildBean;
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

//新品列表和折扣列表共用商品列表界面
public class GoodsListActivity extends BaseAppcompatActivity {

    public static final String TAG = "[IPSC][GoodsListActivity]";
    private int type;//1.新品列表 2折扣列表

    @BindView(R.id.rv)
    RecyclerView mRV;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.ll_screen_container)
    LinearLayout llScreenContainer;
    @BindView(R.id.sv_animal)
    ScrollView svAnimal;
    @BindView(R.id.rl_animal)
    RelativeLayout rlAnimal;

    @BindView(R.id.ll_touch)
    LinearLayout llTouch;

    @BindView(R.id.tv_filter_popularity)
    TextView tvFilterPopularity;
    @BindView(R.id.tv_filter_discount)
    TextView tvFilterDiscount;
    @BindView(R.id.tv_filter_screen)
    TextView tvFilterScreen;
    @BindView(R.id.tv_filter_price)
    TextView tvFilterPrice;
    @BindView(R.id.iv_price_pointer)
    ImageView ivPricePointer;
    @BindView(R.id.iv_filter_screen_pointer)
    ImageView ivFilterScreenPointer;
    @BindView(R.id.ll_goods_tag_container_price)
    internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.LinearLayout llPriceContainer;
    @BindView(R.id.ll_goods_tag_container_brand)
    internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.LinearLayout llBrandsContainer;
    @BindView(R.id.ll_goods_tag_container_class)
    internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.LinearLayout llClassContainer;
//    @BindView(R.id.tv_class_name)
//    TextView tvClassName;

    @BindView(R.id.v_scroll)
    View viewScroll;
    @BindView(R.id.iv_brand_more)
    ImageView ivBrandMore;
    @BindView(R.id.iv_class_more)
    ImageView ivClassMore;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;


    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @BindView(R.id.et_min_price)
    EditText etMinPrice;
    @BindView(R.id.et_max_price)
    EditText etMaxPrice;


    private MultiTypeAdapter adapter;
    private Items items = new Items();
    private String rmb = Html.fromHtml("&yen").toString();
    private IGoodsListPresenter presenter;


    private int screenWidth;
    private int currentPosition = 1;// 功能栏当前选择功能
    private boolean brandIsOpen = false;
    private boolean classIsOpen = false;
    private int pageIndex = 1;
    private int priceState = 2;// 1 升序 2 降序


    private int currentBrandId = -1;
    private int currentClassId = -1;
    private int currentPriceId = -1;

    private ArrayList<RightClassInChildBean> branList = new ArrayList<>();
    private ArrayList<RightClassInChildBean> rightClassData = new ArrayList<>();
    private ArrayList<PriceData> priceData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        presenter = new GoodsListPresenterImp();

        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        initRV();

        items.clear();
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 1);
        }

        if (1 == type) {
            mTvTitle.setText("新品列表");
        } else if (2 == type) {
            mTvTitle.setText("折扣商品");
        }

        // 初始化右侧筛选的产品品牌
        initBrandData();
        initView();
        initData();

        initPriceSection();
        //添加价格
        initPriceData();
    }

    /**
     * 初始化右侧筛选的产品品牌
     */
    private void initBrandData() {
        RequestParams paramsBrand = new RequestParams(MainUrls.getAllClassBrandUrl);
        paramsBrand.addBodyParameter("access_token", IPSCApplication.accessToken);
        paramsBrand.addBodyParameter("page", "1");
        paramsBrand.addBodyParameter("limit", "100");
        presenter.getBrandData(paramsBrand, new NetCallback() {
            @Override
            public void onStart() {
                showLoading(false, "获取数据中...");
            }

            @Override
            public void onFinished() {
                srlContent.finishRefresh();
                srlContent.finishLoadmore();
                dismissLoading();
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Log.e("TAG", "品牌分类:" + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        int state = jsonObject.getInt("state");
                        if (code == 0 && state == 0) {
                            if (jsonObject.has("data")) {
                                JSONArray data = jsonObject.getJSONArray("data");
                                branList.clear();
                                if (data != null && data.length() != 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        RightClassBean rightClassBean = new RightClassBean();
                                        int id = data.getJSONObject(i).getInt("id");
                                        String name = data.getJSONObject(i).getString("name");

                                        rightClassBean.setId(id);
                                        rightClassBean.setMainClassName(name);

                                        if (data.getJSONObject(i).has("list") && !TextUtils.isEmpty(data.getJSONObject(i).getString("list"))) {
                                            JSONArray list = data.getJSONObject(i).getJSONArray("list");
                                            if (list != null && list.length() != 0) {
                                                ArrayList<RightClassInChildBean> childBeans = new ArrayList<>();
                                                for (int j = 0; j < list.length(); j++) {
                                                    RightClassInChildBean childBean = new RightClassInChildBean();
                                                    int childId = list.getJSONObject(j).getInt("id");
                                                    String childName = list.getJSONObject(j).getString("name");
                                                    String childLogo = list.getJSONObject(j).getString("logo");
                                                    childBean.setClassName(childName);
                                                    childBean.setId(childId);
                                                    childBean.setImgUrl(childLogo);
                                                    childBean.setChecked(false);
                                                    if (childBean.getClassName() != null && !TextUtils.isEmpty(childBean.getClassName())) {
                                                        childBeans.add(childBean);
                                                    }
                                                }
                                                rightClassBean.setChildBeans(childBeans);
                                            }
                                        }
                                        branList.addAll(rightClassBean.getChildBeans());
                                        initBrand();
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(Throwable e) {
                Log.e("GoodsListActivity", "出错:", e);
                stopRefreshAndLoadMore();
            }
        });
    }

    /**
     * 初始化价格区间
     */
    private void initPriceSection() {
        if (priceData.size() == 0) {
            PriceData pd = new PriceData();
            pd.setId(1);
            pd.setPriceSection("200以下");
            pd.setChecked(false);
            priceData.add(pd);

            PriceData pd1 = new PriceData();
            pd1.setId(2);
            pd1.setChecked(false);
            pd1.setPriceSection("200-499");
            priceData.add(pd1);

            PriceData pd2 = new PriceData();
            pd2.setId(3);
            pd2.setChecked(false);
            pd2.setPriceSection("500-999");
            priceData.add(pd2);

            PriceData pd3 = new PriceData();
            pd3.setId(4);
            pd3.setChecked(false);
            pd3.setPriceSection("1000-1999");
            priceData.add(pd3);

            PriceData pd4 = new PriceData();
            pd4.setId(5);
            pd4.setPriceSection("2000以上");
            priceData.add(pd4);

        }
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

//    @OnClick({R.id.iv_back})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//        }
//    }

    private void request(final int type) {

        switch (type) {

            case 1: // 新品
                presenter.getNewlyGoodsList(generateParams(type), new NetCallback() {
                    @Override
                    public void onStart() {
                        showLoading(false, "获取数据中...");
                    }

                    @Override
                    public void onFinished() {
                        srlContent.finishRefresh();
                        srlContent.finishLoadmore();
                        dismissLoading();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG, "onSuccess() type:" + type + ",result:" + result);
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
                                                    srlContent.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    srlContent.setEnableLoadmore(true);
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
                                                    srlContent.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    srlContent.setEnableLoadmore(true);
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
                                                srlContent.setEnableLoadmore(false);
                                            }

                                        }

                                        adapter.notifyDataSetChanged();

                                        if (items.size() > 0) {
                                            srlContent.setVisibility(View.VISIBLE);
                                        } else {
                                            srlContent.setVisibility(View.GONE);
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

            case 2: // 折扣
                presenter.getDiscountGoodsList(generateParams(type), new NetCallback() {
                    @Override
                    public void onStart() {
                        showLoading(false, "获取数据中...");
                    }

                    @Override
                    public void onFinished() {
                        srlContent.finishRefresh();
                        srlContent.finishLoadmore();
                        dismissLoading();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG, "onSuccess() type:" + type + ",result:" + result);
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
                                                    srlContent.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    srlContent.setEnableLoadmore(true);
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
                                                    srlContent.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    srlContent.setEnableLoadmore(true);
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
                                                srlContent.setEnableLoadmore(false);
                                            }

                                        }

                                        adapter.notifyDataSetChanged();

                                        if (items.size() > 0) {
                                            srlContent.setVisibility(View.VISIBLE);
                                        } else {
                                            srlContent.setVisibility(View.GONE);
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
        if (srlContent != null) {
            srlContent.finishLoadmore();
            srlContent.finishRefresh(true);
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

        // 筛选功能附带
        params.addBodyParameter("limit", "10");
        if (currentPosition == 1) {
            params.addBodyParameter("num_r", "1");
        }
        if (currentPosition == 2) {
            params.addBodyParameter("is_full", "1");
        }
        if (currentPosition == 3) {
            if (priceState == 1) {
                params.addBodyParameter("price", "0");
                Log.e("TAG", "升序");
            } else if (priceState == 2) {
                params.addBodyParameter("price", "1");
                Log.e("TAG", "降序");
            }
        }

        /***************特别参数：分类列表才有***********************/
        if (currentBrandId != -1) {
            params.addBodyParameter("brand", currentBrandId + "");
        }
        if (currentClassId != -1) {
            params.addBodyParameter("cate", currentClassId + "");
        }
        /**************************************/
        if (etMinPrice.getText().toString().trim().length() != 0) {
            params.addBodyParameter("pricemin", etMinPrice.getText().toString().trim());
        }
        if (etMaxPrice.getText().toString().trim().length() != 0) {
            params.addBodyParameter("pricemax", etMaxPrice.getText().toString().trim());
        }
        if (currentPriceId != -1) {
            switch (currentPriceId) {
                case 1:
                    params.addBodyParameter("pricemax", "200");
                    break;
                case 2:
                    params.addBodyParameter("pricemin", "200");
                    params.addBodyParameter("pricemax", "499");
                    break;
                case 3:
                    params.addBodyParameter("pricemin", "500");
                    params.addBodyParameter("pricemax", "999");
                    break;
                case 4:
                    params.addBodyParameter("pricemin", "1000");
                    params.addBodyParameter("pricemax", "1999");
                    break;
                case 5:
                    params.addBodyParameter("pricemin", "2000");
                    break;
            }
        }
        Log.d(TAG, "generateParams() params:" + params.toString());
        return params;
    }

    //--------------筛选---------------------
    private void initPriceData() {
        llPriceContainer.removeAllViews();
        for (int i = 0; i < priceData.size(); i++) {
            final int index = i;
            final View price = LayoutInflater.from(this).inflate(R.layout.text_tag_view, null);
            TextView tag = price.findViewById(R.id.tv_tag_name);
            if (priceData.get(i).isChecked()) {
                tag.setTextColor(Color.parseColor("#ff0000"));
                tag.setBackgroundResource(R.drawable.shape_of_goods_spe_selected);
            } else {
                tag.setTextColor(Color.parseColor("#aaaaaa"));
                tag.setBackgroundResource(R.drawable.shape_of_verify_code_btn);
            }
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyAllPriceUnChecked();
                    priceData.get(index).setChecked(true);
                    currentPriceId = priceData.get(index).getId();
                    initPriceData();
                    etMinPrice.setText("");
                    etMaxPrice.setText("");
                }
            });
            tag.setText(priceData.get(i).getPriceSection());
            llPriceContainer.addView(price);
        }
    }

    private void initClassData() {
        llClassContainer.removeAllViews();
        if (rightClassData.size() != 0) {
            for (int i = 0; i < rightClassData.size(); i++) {
                final int index = i;
                View classView = LayoutInflater.from(this).inflate(R.layout.text_tag_view, null);
                TextView classes = classView.findViewById(R.id.tv_tag_name);
                if (rightClassData.get(i).isChecked()) {
                    classes.setTextColor(Color.parseColor("#ff0000"));
                    classes.setBackgroundResource(R.drawable.shape_of_goods_spe_selected);
                } else {
                    classes.setTextColor(Color.parseColor("#aaaaaa"));
                    classes.setBackgroundResource(R.drawable.shape_of_verify_code_btn);
                }
                classes.setText(rightClassData.get(i).getClassName());
                classes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyAllClassUnChecked();
                        rightClassData.get(index).setChecked(true);
                        currentClassId = rightClassData.get(index).getId();
                        initClassData();
                    }
                });
                llClassContainer.addView(classView);
            }
        }
    }

    private void notifyAllClassUnChecked() {
        for (int i = 0; i < rightClassData.size(); i++) {
            rightClassData.get(i).setChecked(false);
        }
    }

    private void notifyAllPriceUnChecked() {
        for (int i = 0; i < priceData.size(); i++) {
            priceData.get(i).setChecked(false);
        }
    }

    private void notifyAllUnChecked() {
        for (int i = 0; i < branList.size(); i++) {
            branList.get(i).setChecked(false);
        }
    }

    private void initBrand() {
        llBrandsContainer.removeAllViews();
        if (branList.size() != 0) {
            for (int i = 0; i < branList.size(); i++) {
                final int index = i;
                final View brandView = LayoutInflater.from(this).inflate(R.layout.text_tag_view, null);

                TextView brand = brandView.findViewById(R.id.tv_tag_name);
                if (branList.get(i).isChecked()) {
                    brand.setTextColor(Color.parseColor("#ff0000"));
                    brand.setBackgroundResource(R.drawable.shape_of_goods_spe_selected);
                } else {
                    brand.setTextColor(Color.parseColor("#aaaaaa"));
                    brand.setBackgroundResource(R.drawable.shape_of_verify_code_btn);
                }
                brand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyAllUnChecked();
                        branList.get(index).setChecked(true);
                        currentBrandId = branList.get(index).getId();
                        initBrand();
                    }
                });
                brand.setText(branList.get(i).getClassName());
                llBrandsContainer.addView(brandView);
            }
        }

    }

    private void initView() {
//        mTvTitle.setText(className);
        llTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    animalOut(rlAnimal);
                }
                return true;
            }
        });
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
                loadMore();
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyAllClassUnChecked();
                notifyAllUnChecked();
                notifyAllPriceUnChecked();
                currentClassId = -1;
                currentBrandId = -1;
                currentPriceId = -1;
                initPriceData();
                initClassData();
                initBrand();
                etMaxPrice.setText("");
                etMinPrice.setText("");
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMinPrice.getText().toString().trim().length() != 0 && etMaxPrice.getText().toString().trim().length() != 0) {
                    if (Integer.valueOf(etMinPrice.getText().toString().trim()) < Integer.valueOf(etMaxPrice.getText().toString().trim())) {

                    } else {
                        ToastUtils.show(GoodsListActivity.this, "最低价必须小于最高价");
                    }
                }
                pageIndex = 1;
                initData();
                animalOut(rlAnimal);
            }
        });
    }

    public void animalIn(View view) {
        Animation animator = AnimationUtils.loadAnimation(this, R.anim.pop_in);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animator);
    }

    public void animalOut(View view) {
        Animation animator = AnimationUtils.loadAnimation(this, R.anim.pop_out);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llScreenContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animator);
    }

    private void initData() {
        request(type);
//        bondedGoodsPresenter.getBondedGoods(params, this);

        if (adapter == null) {
//            adapter = new BondedGoodsListAdapter(this, data);
//            mRV.setLayoutManager(new GridLayoutManager(this, 2));
//            mRV.setAdapter(adapter);
            initRV();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void loadMore() {
//        pageIndex++;
        Log.d(TAG, "loadMore() pageIndex:" + pageIndex);
        request(type);
//        bondedGoodsPresenter.getBondedGoods(params, this);
    }

    @OnClick({R.id.iv_back, R.id.ll_filter_screen, R.id.tv_filter_popularity, R.id.tv_filter_discount, R.id.ll_filter_price,
            R.id.ll_brand_more, R.id.ll_class_more
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                GoodsListActivity.this.finish();
                break;
            case R.id.ll_filter_screen:// 筛选
                llScreenContainer.setVisibility(View.VISIBLE);
                animalIn(rlAnimal);
                change(4);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen_selected);
                ivPricePointer.setImageResource(R.mipmap.icon_filter_price);
                break;
            case R.id.tv_filter_popularity:// 人气排序
                change(1);
                ivPricePointer.setImageResource(R.mipmap.icon_filter_price);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen);
                pageIndex = 1;
                initData();
                break;
            case R.id.tv_filter_discount:// 折扣排序
                change(2);
                ivPricePointer.setImageResource(R.mipmap.icon_filter_price);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen);
                pageIndex = 1;
                initData();
                break;
            case R.id.ll_filter_price:// 价格排序
                if (priceState == 1) {
                    priceState = 2;
                    ivPricePointer.setImageResource(R.mipmap.icon_filter_price_down);
                } else if (priceState == 2) {
                    priceState = 1;
                    ivPricePointer.setImageResource(R.mipmap.icon_filter_price_up);
                }
                change(3);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen);
                pageIndex = 1;
                initData();
                break;
            case R.id.ll_brand_more:// 更多品牌
                if (brandIsOpen) {
                    ViewGroup.LayoutParams params = llBrandsContainer.getLayoutParams();
                    params.height = DpUtils.dpToPx(this, 80);
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    llBrandsContainer.setLayoutParams(params);
                    ivBrandMore.setImageResource(R.mipmap.icon_class_down);
                    brandIsOpen = false;
                } else {
                    ViewGroup.LayoutParams params = llBrandsContainer.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    llBrandsContainer.setLayoutParams(params);
                    ivBrandMore.setImageResource(R.mipmap.icon_class_up);
                    brandIsOpen = true;
                }
                break;
            case R.id.ll_class_more:// 更多分类
                if (classIsOpen) {
                    ViewGroup.LayoutParams params = llClassContainer.getLayoutParams();
                    params.height = DpUtils.dpToPx(this, 80);
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    llClassContainer.setLayoutParams(params);
                    ivClassMore.setImageResource(R.mipmap.icon_class_down);
                    classIsOpen = false;
                } else {
                    ViewGroup.LayoutParams params = llClassContainer.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    llClassContainer.setLayoutParams(params);
                    ivClassMore.setImageResource(R.mipmap.icon_class_up);
                    classIsOpen = true;
                }
                break;
        }
    }

    private void change(int index) {
        switch (index) {
            case 1:// 人气
                tvFilterPopularity.setTextColor(Color.parseColor("#ff0000"));
                tvFilterDiscount.setTextColor(Color.parseColor("#636363"));
                tvFilterPrice.setTextColor(Color.parseColor("#636363"));
                tvFilterScreen.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(1);
                break;
            case 2:// 折扣
                tvFilterPopularity.setTextColor(Color.parseColor("#636363"));
                tvFilterDiscount.setTextColor(Color.parseColor("#ff0000"));
                tvFilterPrice.setTextColor(Color.parseColor("#636363"));
                tvFilterScreen.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(2);
                break;
            case 3:// 价格
                tvFilterPopularity.setTextColor(Color.parseColor("#636363"));
                tvFilterDiscount.setTextColor(Color.parseColor("#636363"));
                tvFilterPrice.setTextColor(Color.parseColor("#ff0000"));
                tvFilterScreen.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(3);
                break;
            case 4:// 筛选
                tvFilterPopularity.setTextColor(Color.parseColor("#636363"));
                tvFilterDiscount.setTextColor(Color.parseColor("#636363"));
                tvFilterPrice.setTextColor(Color.parseColor("#636363"));
                tvFilterScreen.setTextColor(Color.parseColor("#ff0000"));
                scrollAnimal(4);
                break;
        }
    }

    /**
     * 当前选择线滚动动画
     * @param position
     */
    private void scrollAnimal(int position) {
        int width = screenWidth / 4 - 2 * DpUtils.dpToPx(this, 20);
        switch (position) {
            case 1:
                int pL = DpUtils.dpToPx(this, 20);
                if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", width + 2 * pL, 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 2 * width + 4 * pL, 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 4) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 3 * width + 6 * pL, 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 1;
                break;
            case 2:
                pL = DpUtils.dpToPx(this, 20);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 2 * width + 4 * pL, (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 4) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 3 * width + 6 * pL, (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 2;
                break;
            case 3:
                pL = DpUtils.dpToPx(this, 20);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", width + 2 * pL, (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 4) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 3 * width + 6 * pL, (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 3;
                break;
            case 4:
                pL = DpUtils.dpToPx(this, 20);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (3 * width + 6 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", width + 2 * pL, (3 * width + 6 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 2 * width + 4 * pL, (3 * width + 6 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 4;
                break;
        }
    }
}