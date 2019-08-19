package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.chrisjason.baseui.util.DpUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaeger.library.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.GoodsDetailAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CartGood;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CartRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.EvaluateImage;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.HomeBannerBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.MainSwitchEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.GoodsDetailRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.Goods_brand;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.Speclist;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.Storelist;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GoodsDetailFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GoodsInstructionsFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GoodsScopelFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.GoodsDetailImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.GoodsDetailInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.StringUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnGoodsDetailInfoCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.CustomScrollViewPager;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.HoldTabScrollView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.RatingView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.popupwindow.GoodsPropsSelectPop;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView.TIMETYPE_MS;
import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView.TIMETYPE_S;

public class GoodsDetailActivity extends BaseAppcompatActivity implements OnGoodsDetailInfoCallback {

    private static final String TAG = "[IPSC][GoodsDetailActivity]";
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.rl_open_select_spec)
    RelativeLayout rlOpenSelectSpec;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_pic_index)
    TextView tvPicIndex;
    //秒杀
    @BindView(R.id.seckill_timer_1)
    TimerView secKillTimer;
    @BindView(R.id.tv_kill_price)
    TextView tvKillPrice;
    @BindView(R.id.tv_kill_general_price)
    TextView tvKillGeneralPrice;
    @BindView(R.id.ll_kill_container)
    LinearLayout llKillContainer;
    @BindView(R.id.ll_killing)
    LinearLayout llKilling;
    @BindView(R.id.tv_kill_state)
    TextView tvKillState;
    @BindView(R.id.tv_day)
    TextView tvDay;
    //商品信息
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_description)
    TextView tvGoodsDescription;
    @BindView(R.id.tv_price)
    TextView tvNowPrice;
    @BindView(R.id.tv_original_price)
    TextView tvOriginalPrice;
    @BindView(R.id.tv_taxation_price)
    TextView tvTaxationPrice;
    @BindView(R.id.tv_stock)
    TextView tvStock;
    @BindView(R.id.ll_tag_container_goods)
    internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.LinearLayout llGoodsTagContainer;
    @BindView(R.id.iv_brand)
    ImageView ivBrand;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.rl_brand_container)
    RelativeLayout rlBrandContainer;
    @BindView(R.id.rl_no_evaluate)
    RelativeLayout rlNoEvaluate;
    @BindView(R.id.rl_to_evaluate_list)
    RelativeLayout rlToEvaluateList;
    @BindView(R.id.ll_item_evaluate)
    LinearLayout llItemEvaluate;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    //评论信息
    @BindView(R.id.ll_image_container)
    LinearLayout llImageContainer;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.rv_star)
    RatingView rvStar;
    @BindView(R.id.tv_insert_time)
    TextView tvInsertTime;
    @BindView(R.id.tv_evaluate_total)
    TextView tvEvaluateTotal;
    @BindView(R.id.tv_evaluate_content)
    TextView tvEvaluateContent;
    @BindView(R.id.ll_goods_container)
    LinearLayout llEvaluatePicContainer;

    @BindView(R.id.tv_cart_num)
    TextView tv_cart_num;

    private ArrayList<Fragment> fgList = new ArrayList<>();
    private GoodsDetailInterface goodsDetailPresenter;
    private int goodsId = -1;
    private GoodsDetailRootBean goodsBean;
    private GoodsPropsSelectPop goodsPropsSelectPop;

    private ArrayList<Speclist> specLists = new ArrayList<>();
    private ArrayList<Storelist> storeLists = new ArrayList<>();
    private int cunt = 0;
    /**
     *  0 未收藏  1 已收藏
      */
    private int isCollection = 0;

    private ArrayList<CartRootBean> carts = new ArrayList<>();

    private boolean isMul = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goodsId", -1);
        goodsDetailPresenter = new GoodsDetailImp();
        initData(goodsId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initData(int goodsId) {
        this.goodsId = goodsId;
        RequestParams params = new RequestParams(MainUrls.getGoodsDetailUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", goodsId + "");
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
        goodsDetailPresenter.getGoodsBaseInfo(params, this);
    }

    //添加到购物车
    public void addToCart(int currentSpecId, int currentStoreType, int number) {
        if (((IPSCApplication) getApplication()).getUserInfo() == null) {
            ToastUtils.show(GoodsDetailActivity.this, "请先登录");
            Intent intent = new Intent(GoodsDetailActivity.this, LoginByPasswordActivity.class);
            startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams(MainUrls.addGoodsToCartUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", currentSpecId + "");
        params.addBodyParameter("number", number + "");
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
        goodsDetailPresenter.addGoodsToCart(params, this);
    }

    private void initView() {
        if (goodsBean == null) {
            return;
        }

        //购物车数量
        if(goodsBean.getData().getCartnumber()>0){
            tv_cart_num.setVisibility(View.VISIBLE);
            tv_cart_num.setText(goodsBean.getData().getCartnumber()+"");
        }


        //商品信息数据
        tvGoodsName.setText(goodsBean.getData().getGoods_goods().getName());
        tvGoodsDescription.setText(goodsBean.getData().getGoods_goods().getDescribe());
        tvNowPrice.setText("" + new DecimalFormat("######0.00").format(goodsBean.getData().getPrice()));
        tvOriginalPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsBean.getData().getPrice_m()));
        tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        // 新增需求,国内仓商品详情 去掉税费
        if (goodsBean.getData().getStore_store() != null && goodsBean.getData().getStore_store().getType().equals("国内仓库")) {
            tvTaxationPrice.setVisibility(View.GONE);
        }
        if (goodsBean.getData().getIs_btax() == 0) {
            tvTaxationPrice.setText("税费:￥" + new DecimalFormat("#######0.00").format(goodsBean.getData().getPrice_tax()));
        } else {
            tvTaxationPrice.setText("包税");
        }
        tvStock.setText("库存:" + goodsBean.getData().getUsenumber());
        llGoodsTagContainer.removeAllViews();
        if (goodsBean.getData().getStore_store() != null && goodsBean.getData().getStore_store().getType() != null) {
            View view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.store_type_tag_layout, null);
            ImageView ivTagIcon = view.findViewById(R.id.iv_tag_icon);
            TextView tvTagName = view.findViewById(R.id.tv_tag_name);
            ivTagIcon.setImageResource(R.mipmap.icon_goods_detail_airplan);
            tvTagName.setText(goodsBean.getData().getStore_store().getType());
            llGoodsTagContainer.addView(view);
        }
        if (goodsBean.getData().getIs_pron().equals("进行中")) {
            View view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.store_type_tag_layout, null);
            ImageView ivTagIcon = view.findViewById(R.id.iv_tag_icon);
            TextView tvTagName = view.findViewById(R.id.tv_tag_name);
            ivTagIcon.setImageResource(R.mipmap.icon_goods_detail_package);
            tvTagName.setText("限时促销");
            llGoodsTagContainer.addView(view);
        }
        if (TextUtils.isEmpty(goodsBean.getData().getGoods_goods().getGoods_brand().getName())) {
            Glide.with(GoodsDetailActivity.this).load(goodsBean.getData().getGoods_goods().getGoods_brand().getLogo()).apply(new RequestOptions().override(100, 100).placeholder(R.drawable.icon_no_image).error(R.drawable.ic_launcher_logo)).into(ivBrand);
            tvBrandName.setText("暂无品牌信息");
        } else {

            Glide.with(GoodsDetailActivity.this).load(goodsBean.getData().getGoods_goods().getGoods_brand().getLogo()).apply(new RequestOptions().override(100, 100).placeholder(R.drawable.icon_no_image).error(R.drawable.ic_launcher_logo)).into(ivBrand);
            tvBrandName.setText(goodsBean.getData().getGoods_goods().getGoods_brand().getName());
        }

        //秒杀
        if (goodsBean.getData().getIs_pron().equals("已结束")) {
            llKillContainer.setVisibility(View.GONE);
        } else {
            llKillContainer.setVisibility(View.VISIBLE);
            if (goodsBean.getData().getIs_pron().equals("即将开始")) {
                llKilling.setVisibility(View.GONE);
                tvKillState.setVisibility(View.VISIBLE);
                tvKillState.setText("即将开始");
            } else if (goodsBean.getData().getIs_pron().equals("进行中")) {
                double endTime = ((long) goodsBean.getData().getPron_end_time()) * 1000;
                double nowTime = (new Date()).getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                double sub = endTime - nowTime;
                llKilling.setVisibility(View.VISIBLE);
                tvKillState.setVisibility(View.GONE);
                tvKillPrice.setText(new DecimalFormat("######0.00").format(goodsBean.getData().getPron_price()));
                tvKillGeneralPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsBean.getData().getPrice()));
                tvKillGeneralPrice.setPaintFlags(tvKillGeneralPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                if (!secKillTimer.getRunning()) {
                    secKillTimer.setTime((int) sub, TIMETYPE_MS);
                    secKillTimer.start();
                }
                if (Integer.valueOf(secKillTimer.getDay()) != 0) {
                    tvDay.setText(secKillTimer.getDay() + "天");
                }
            }

        }

        initBanner(banner, goodsBean.getData().getGoods_goods().getGoods_temp().getImg());
        tvPicIndex.setText("1" + "/" + goodsBean.getData().getGoods_goods().getGoods_temp().getImg().size());
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //TODO
                if (tvPicIndex != null) {
                    tvPicIndex.setText((position + 1) + "/" + goodsBean.getData().getGoods_goods().getGoods_temp().getImg().size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (cunt == 1) {
            String[] strings = new String[3];
            strings[0] = "商品详情";
            strings[1] = "适用范围";
            strings[2] = "购物须知";
            fgList.clear();
            fgList.add(GoodsDetailFragment.getInstance(viewPager, goodsBean.getData().getGoods_goods().getDetail()));
            fgList.add(GoodsScopelFragment.getInstance(viewPager, goodsBean.getData().getGoods_goods().getGoods_attr_html()));
            fgList.add(GoodsInstructionsFragment.getInstance(viewPager, goodsBean.getData().getGoods_goods().getInstructions()));
            viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
            viewPager.setOffscreenPageLimit(3);
            slidingTabLayout.setViewPager(viewPager, strings);
        }
        if (isCollection == 0) {// 未收藏
            ivCollection.setImageResource(R.mipmap.icon_goods_detail_collection);
        } else {
            ivCollection.setImageResource(R.mipmap.icon_mine_collection);
        }
    }


    @Override
    public int initLayout() {
        return R.layout.activity_goods_detail;
    }

    /**
     * 联系客服
     */
    public void contactService() {
        if (isLogin()) {
            startActivity(new Intent(this, ContactCustomerServiceActivity.class));
        } else {
            startActivity(new Intent(this, LoginByPasswordActivity.class));
        }
    }

    /**
     * 打开选择面板
     */
    public void openSelectSpec() {
        if (goodsPropsSelectPop == null) {
            goodsPropsSelectPop = new GoodsPropsSelectPop(this, goodsBean, specLists, storeLists);
        }
        goodsPropsSelectPop.show(rlParent, new GoodsPropsSelectPop.ShoppingCartClick() {
            @Override
            public void onClick(int type, double rentPrice, double buyPrice) {

            }
        });
    }

    /**
     * 跳转到评价列表界面
     */
    public void toEvaluateList() {
        Intent intent = new Intent(GoodsDetailActivity.this, GoodsEvalateListActivity.class);
        intent.putExtra("goodsId", goodsId);
        startActivity(intent);
    }

    /**
     * 添加购物车
     */
    public void addToCart() {
        if (isLogin()) {
            if (goodsPropsSelectPop == null) {
                goodsPropsSelectPop = new GoodsPropsSelectPop(this, goodsBean, specLists, storeLists);
            }
            goodsPropsSelectPop.show(rlParent, new GoodsPropsSelectPop.ShoppingCartClick() {
                @Override
                public void onClick(int type, double rentPrice, double buyPrice) {

                }
            });
        } else {
            startActivity(new Intent(this, LoginByPasswordActivity.class));
        }
    }

    /**
     * 打开购物车
     */
    public void openShoppingCar() {
        if (isLogin()) {
            EventBus.getDefault().post(new MainSwitchEvent(3));
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(this, LoginByPasswordActivity.class));
        }
    }

    /**
     * 收藏
     */
    public void addToCollection() {
        if (isLogin()) {
            RequestParams params = new RequestParams(MainUrls.addToCollectionUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            if (((IPSCApplication) getApplication()).getUserInfo() != null) {
                params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
            }
            params.addBodyParameter("goods", goodsBean.getData().getGoods_goods().getId() + "");
            goodsDetailPresenter.addGoodsToCollection(params, this, isCollection == 0 ? 1 : 2);
        } else {
            ToastUtils.show(GoodsDetailActivity.this, "请先登录");
            Intent intentToCollection = new Intent(GoodsDetailActivity.this, LoginByPasswordActivity.class);
            startActivityForResult(intentToCollection, 100);
        }
    }

    private boolean isBuyImmediately = false;// 是否是立即购买
    /**
     * 立即购买
     */
    public void buyImmediately(int currentSpecId, int currentStoreType, int number) {
        if(!isLogin()){
            startActivity(new Intent(this, LoginByPasswordActivity.class));
            return;
        }

        isBuyImmediately = true;

        if (((IPSCApplication) getApplication()).getUserInfo() == null) {
            ToastUtils.show(GoodsDetailActivity.this, "请先登录");
            Intent intent = new Intent(GoodsDetailActivity.this, LoginByPasswordActivity.class);
            startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams(MainUrls.addGoodsToCartUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", currentSpecId + "");
        params.addBodyParameter("number", number + "");
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }

        showLoading(false, "加载数据中...");

        x.http().post(params, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {

                if(result!=null){
                    try {
                        int code = result.getInt("code");
                        int state = result.getInt("state");
                        //添加购物车成功
                        if(code ==0 && state ==0){
                            goodsPropsSelectPop.dismiss();
                            //查询购物车，然后结算
                            queryCart();
                        }else {
                            Toast.makeText(GoodsDetailActivity.this, "订单创建失败", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(GoodsDetailActivity.this, "订单差U功能键失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void queryCart(){
        RequestParams params = new RequestParams(MainUrls.getGoodsCartUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("page", "1");
        params.addBodyParameter("limit", "10000");
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication)getApplication()).getUserInfo().getId() + "");
        }

        x.http().post(params, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {

                    int state = jsonObject.getInt("state");
                    int code = jsonObject.getInt("code");
                    if (state == 0 && code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");

                        carts.clear();

                        JSONArray storeList = data.getJSONArray("list");
                        if (storeList != null && storeList.length() != 0) {
                            for (int i = 0; i < storeList.length(); i++) {
                                CartRootBean cartRootBean = new CartRootBean();
                                JSONObject carRoot = storeList.getJSONObject(i);
                                if (carRoot.get("store_store") != null && carRoot.get("store_store").toString() != "null" && carRoot.getJSONObject("store_store") != null) {
                                    cartRootBean.setCartGoodsName(carRoot.getJSONObject("store_store").getString("name"));
                                    cartRootBean.setStoreType(carRoot.getJSONObject("store_store").getString("type"));
                                    cartRootBean.setId(carRoot.getJSONObject("store_store").getInt("id"));
                                }
                                cartRootBean.setTotalPrice(carRoot.getDouble("total"));
                                JSONArray goodsList = carRoot.getJSONArray("list");
                                ArrayList<CartGood> cartGoodsList = new ArrayList<>();
                                if (goodsList != null && goodsList.length() != 0) {
                                    for (int j = 0; j < goodsList.length(); j++) {
                                        JSONObject goods = goodsList.getJSONObject(j);
                                        CartGood cartGood = new CartGood();
                                        cartGood.setId(goods.getInt("id"));
                                        cartGood.setGoodsPriceId(goods.getJSONObject("store_price").getInt("id"));
                                        cartGood.setChecked(goods.getInt("cart_state") == 1 ? true : false);


                                        JSONObject goods_temp = goods.getJSONObject("store_price").getJSONObject("goods_goods").getJSONObject("goods_temp");
                                        String imgUrl = "";
                                        if(goods_temp !=null){
                                            Object o = goods_temp.get("img");
                                            if(o instanceof String){
                                                imgUrl = goods_temp.getString("img");
                                            }else if(o instanceof JSONArray){
                                                JSONArray imgArray = goods_temp.getJSONArray("img");
                                                if(imgArray!=null && imgArray.length()>0){
                                                    imgUrl = imgArray.getString(0);
                                                }
                                            }
                                        }


                                        cartGood.setImagePath(imgUrl);

                                        cartGood.setName(goods.getJSONObject("store_price").getJSONObject("goods_goods").getJSONObject("goods_temp").getString("name"));
                                        cartGood.setNum(goods.getInt("number"));
                                        cartGood.setPrice(goods.getDouble("price"));
                                        cartGood.setOriginalPrice(goods.getDouble("price"));
                                        cartGood.setTaxation(goods.getDouble("tax"));
                                        cartGood.setInfo(goods.getJSONObject("store_price").getJSONObject("goods_goods")
                                                .getString("spec"));
                                        cartGoodsList.add(cartGood);
                                    }
                                    cartRootBean.setmCartGood(cartGoodsList);
                                }
                                carts.add(cartRootBean);
                            }
                        } else {
                        }
                    }


                } catch (Exception e) {
                    LogUtil.e(TAG, "onCommonGoodsCallBack()", e);
                }finally {

                    //只有一单
                    if(countChecked(carts)==1){
                        isMul = false;
                    }else {
                        //拆单
                        isMul = true;
                    }

                    submit();

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

    private int countChecked(ArrayList<CartRootBean> rootBean) {
        int checkedCount = 0;
        try {
            for (int i = 0; i < rootBean.size(); i++) {
                if (rootBean.get(i).getmCartGood() != null && rootBean.get(i).getmCartGood().size() != 0) {
                    // TODO这里存在潜在问题，后台反馈的数据有时候会存在没有对应仓库，当前我们默认后台数据正常
                    for (int j = 0; j < rootBean.get(i).getmCartGood().size(); j++) {
                        if (rootBean.get(i).getmCartGood().get(j).isChecked()) {
                            checkedCount++;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return checkedCount;
    }

    private void submit(){


        RequestParams params = new RequestParams(MainUrls.cartSubmitUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        if(((IPSCApplication)getApplicationContext()).getUserInfo()!=null){
            params.addBodyParameter("user", ((IPSCApplication)getApplicationContext()).getUserInfo().getId() + "");
        }

        x.http().post(params, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    int code = jsonObject.getInt("code");
                    int state = jsonObject.getInt("state");
                    if(code ==0 && state ==0){

                        //提交成功
                        if (jsonObject.has("data")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            String orderId = data.has("0") ? data.getString("0") : "-1";
                            if (isMul) {// 提交购物车成功后，如果是多订单，则会跳转到待付款界面
                                //跳转到代付款界面
                                Intent intentToWaitPayOrder = new Intent(GoodsDetailActivity.this, OrderActivity.class);
                                intentToWaitPayOrder.putExtra("index", 1);
                                startActivity(intentToWaitPayOrder);
                                return;
                            }
                            if (!orderId.equals("-1")) {
                                Intent intent = new Intent(GoodsDetailActivity.this, ConfirmOrderActivity.class);
                                intent.putExtra("id", orderId);
                                startActivity(intent);
                            }
                        }

                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                dismissLoading();
            }
        });

    }


    /**
     * 立即购买
     */
    public void buyImmediately() {
        if (isLogin()) {
            if (goodsPropsSelectPop == null) {
                goodsPropsSelectPop = new GoodsPropsSelectPop(this, goodsBean, specLists, storeLists);
            }
            goodsPropsSelectPop.show(rlParent, new GoodsPropsSelectPop.ShoppingCartClick() {
                @Override
                public void onClick(int type, double rentPrice, double buyPrice) {

                }
            });
        } else {
            startActivity(new Intent(this, LoginByPasswordActivity.class));
        }
    }

    @OnClick({R.id.iv_back, R.id.rl_open_select_spec, R.id.rl_to_evaluate_list, R.id.tv_add_to_cart, R.id.rl_add_to_collection,
            R.id.tv_buy_immediately, R.id.rl_car, R.id.rl_contact_service,R.id.rl_brand_container})
    public void onClick(View view) {
        switch (view.getId()) {
            //跳转品牌
            case R.id.rl_brand_container:
                Goods_brand brand =null;
                try{
                    brand  = goodsBean.getData().getGoods_goods().getGoods_brand();
                }catch (Exception e){}

                if(brand==null){
                    return;
                }

                Intent intent = new Intent(this, BrandDetailActivity.class);
                intent.putExtra("brandId", brand.getId());
                intent.putExtra("brandName", brand.getName());
                startActivity(intent);
                break;
            case R.id.rl_contact_service:
                contactService();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_open_select_spec:
                openSelectSpec();
                break;
            case R.id.rl_to_evaluate_list:
                toEvaluateList();
                break;
            //添加购物车
            case R.id.tv_add_to_cart:
                addToCart();
                break;
            //打开购物车
            case R.id.rl_car:
                openShoppingCar();
                break;
            // 收藏
            case R.id.rl_add_to_collection:
                addToCollection();
                break;
            // 立即购买
            case R.id.tv_buy_immediately:
                buyImmediately();
                break;
        }
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取商品信息...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onGoodsInfoLoaded(String result) {
        if (result != null) {
            LogUtil.d(TAG, "商品信息:" + result);
            try {
                Gson gson = new GsonBuilder().serializeNulls().create();
                // 后台反馈数据不和协议一致，本应该提供数组array对象，结果反馈空字符串“”，解析不过，为了兼容，特殊处理
                // 此处使用fastjson是为了应急处理，后续需要跟进处理
                com.alibaba.fastjson.JSONObject resultJson = JSON.parseObject(result);
                try {

                    if (resultJson.getJSONObject("data").getJSONObject("goods_goods").getJSONObject("goods_temp").get("img").toString().isEmpty()) {
                        resultJson.getJSONObject("data").getJSONObject("goods_goods").getJSONObject("goods_temp").put("img", new com.alibaba.fastjson.JSONArray());
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "onGoodsInfoLoaded() parse img node exception!", e);
                }

                goodsBean = gson.fromJson(resultJson.toJSONString(), GoodsDetailRootBean.class);
                isCollection = goodsBean.getData().getColle();
                if (cunt == 0) {
                    specLists.addAll(goodsBean.getData().getSpeclist());
                    storeLists.addAll(goodsBean.getData().getStorelist());
                    cunt++;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            initView();
                        } catch (Exception e) {
                            LogUtil.e(TAG, "occur Exception: ", e);
                        }
                        if (goodsPropsSelectPop != null) {
                            goodsPropsSelectPop.setGoodsBean(goodsBean);
                        }
                    }
                });
                getGoodsEvaluate(goodsId);
            } catch (Exception e) {
                LogUtil.e(TAG, "onGoodsInfoLoaded() occur Exception: ", e);
            }
        }
    }

    private void getGoodsEvaluate(int goodsId) {
        RequestParams params = new RequestParams(MainUrls.getGoodsEvaluateUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("goods", goodsId + "");
        params.addBodyParameter("page", "1");
        params.addBodyParameter("limit", "10000");
        goodsDetailPresenter.getGoodsEvaluate(params, this);
    }

    //商品评论
    @Override
    public void onGoodsEvaluateLoaded(String result) {
        if (result != null) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                if (code == 0) {
                    if (jsonObject.has("data") && jsonObject.getJSONArray("data") != null) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data.length() != 0) {
                            String userName = data.getJSONObject(0).getJSONObject("user_user").getString("nickname");
                            String userHeadUrl = data.getJSONObject(0).getJSONObject("user_user").getString("img");
                            double rate = data.getJSONObject(0).getDouble("rate");
                            String content = data.getJSONObject(0).getString("name");
                            long inset_time = (long) data.getJSONObject(0).getDouble("create_time") * 1000;
                            ArrayList<EvaluateImage> imagesList = new ArrayList<>();
                            if (!TextUtils.isEmpty(data.getJSONObject(0).getString("img"))) {
                                JSONArray images = data.getJSONObject(0).getJSONArray("img");
                                for (int i = 0; i < images.length(); i++) {
                                    String url = images.getString(i);
                                    EvaluateImage ei = new EvaluateImage();
                                    ei.setId(0);
                                    ei.setOriginalUrl(url);
                                    ei.setUrl(url);
                                    imagesList.add(ei);
                                }
                            }
                            rlNoEvaluate.setVisibility(View.GONE);
                            llItemEvaluate.setVisibility(View.VISIBLE);
                            llEvaluatePicContainer.removeAllViews();
                            if (imagesList.size() != 0) {
                                for (int i = 0; i < imagesList.size(); i++) {
                                    View view = LayoutInflater.from(this).inflate(R.layout.rv_item_order_image_view, null);
                                    ImageView ivEvaluatePic = view.findViewById(R.id.iv_evaluate_pic);
                                    Glide.with(GoodsDetailActivity.this).load(imagesList.get(i).getUrl())
                                            .apply(new RequestOptions().placeholder(R.drawable.icon_no_image).error(R.drawable.ic_launcher_logo))
                                            .into(ivEvaluatePic);
                                    llEvaluatePicContainer.addView(view);
                                }
                            }
                            Glide.with(GoodsDetailActivity.this).load(userHeadUrl)
                                    .apply(new RequestOptions().error(R.mipmap.icon_mine_defaul_head).placeholder(R.mipmap.icon_mine_defaul_head))
                                    .into(civHead);
                            tvNickName.setText(userName);
                            String insertTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(inset_time));
                            tvInsertTime.setText(insertTime);
                            tvEvaluateContent.setText(content);
                            tvEvaluateTotal.setText("用户评价(" + data.length() + ")");
                            rvStar.setRating((float) rate);
                        } else {
                            rlNoEvaluate.setVisibility(View.VISIBLE);
                            llItemEvaluate.setVisibility(View.GONE);
                            llEvaluatePicContainer.removeAllViews();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //添加购物车结果
    @Override
    public void onGoodsAddedToCart(String result) {
        if (result != null) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    ToastUtils.show(GoodsDetailActivity.this, "添加购物车成功!");
                    goodsPropsSelectPop.dismiss();
                    if (isBuyImmediately) {
                        openShoppingCar();
                    }

                    int number = jsonObject.getJSONObject("data").getInt("number");

                    int ori =0;
                    if(!TextUtils.isEmpty(tv_cart_num.getText().toString())){
                       ori = Integer.valueOf(tv_cart_num.getText().toString());
                    }

                    if(ori<0){
                        ori=0;
                    }


                    if(ori + number>0){
                        tv_cart_num.setVisibility(View.VISIBLE);
                        tv_cart_num.setText((ori+number)+"");
                    }else {
                        tv_cart_num.setVisibility(View.GONE);
                    }


                } else {
                    ToastUtils.show(GoodsDetailActivity.this, "添加购物车失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isBuyImmediately = false;
            }
        }
    }

    //收藏或取消收藏
    @Override
    public void onGoodsAddedToCollection(int type, String msg) {
        initData(goodsId);
        if (type == 1) {
            ToastUtils.show(GoodsDetailActivity.this, msg);
        } else {
            ToastUtils.show(GoodsDetailActivity.this, msg);
        }
    }


    class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fgList.get(position);
        }

        @Override
        public int getCount() {
            return fgList.size();
        }
    }

    private void initBanner(Banner banner, List<String> bannerBeanList) {
        LogUtil.d(TAG, "initBanner() bannerBeanList:" + StringUtil.listToString(bannerBeanList));
        //显示圆形指示器
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //指示器居中
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(bannerBeanList);
        banner.setImageLoader(new MyImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.start();
    }

    class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (path instanceof String) {
                String b = (String) path;
                RequestOptions options = new RequestOptions();
                options.placeholder(R.drawable.pic_banner_place_holder);
                options.error(R.drawable.pic_banner_error);
                Glide.with(context).load(b).
                        apply(options).into(imageView);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            initData(goodsId);
        }
    }

    //判断是否登录
    private boolean isLogin() {
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            return true;
        }
        return false;
    }
}
