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

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.chrisjason.baseui.util.DpUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.EvaluateImage;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.HomeBannerBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.GoodsDetailRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.Speclist;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.Storelist;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GoodsDetailFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GoodsInstructionsFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GoodsScopelFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.GoodsDetailImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.GoodsDetailInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
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


    private ArrayList<Fragment> fgList = new ArrayList<>();
    private GoodsDetailInterface goodsDetailPresenter;
    private int goodsId = -1;
    private GoodsDetailRootBean goodsBean;
    private GoodsPropsSelectPop goodsPropsSelectPop;

    private ArrayList<Speclist> specLists = new ArrayList<>();
    private ArrayList<Storelist> storeLists = new ArrayList<>();
    private int cunt = 0;
    private int isCollection = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goodsId", -1);
        Log.e("TAG", "商品Id:" + goodsId);
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
        params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        goodsDetailPresenter.addGoodsToCart(params, this);
    }

    private void initView() {
        if (goodsBean == null) {
            return;
        }
        //商品信息数据
        tvGoodsName.setText(goodsBean.getData().getGoods_goods().getName());
        tvGoodsDescription.setText(goodsBean.getData().getGoods_goods().getDescribe());
        tvNowPrice.setText("" + new DecimalFormat("######0.00").format(goodsBean.getData().getPrice()));
        tvOriginalPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsBean.getData().getPrice_m()));
        tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (goodsBean.getData().getIs_btax() == 0) {
            tvTaxationPrice.setText("税费:￥" + new DecimalFormat("#######0.00").format(goodsBean.getData().getPrice_tax()));
        } else {
            tvTaxationPrice.setText("包税");
        }
        tvStock.setText("库存:" + goodsBean.getData().getUsenumber());
        llGoodsTagContainer.removeAllViews();
        if (goodsBean.getData().getStore_store().getType() != null) {
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
            Glide.with(GoodsDetailActivity.this).load(goodsBean.getData().getGoods_goods().getGoods_brand().getLogo()).apply(new RequestOptions().override(100, 100).placeholder(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)).into(ivBrand);
            tvBrandName.setText("暂无品牌信息");
        } else {

            Glide.with(GoodsDetailActivity.this).load(goodsBean.getData().getGoods_goods().getGoods_brand().getLogo()).apply(new RequestOptions().override(100, 100).placeholder(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)).into(ivBrand);
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
                Log.e("TAG", "结束时间:" + simpleDateFormat.format(new Date((long) endTime)) + "  现在时间:" + simpleDateFormat.format(new Date((long) nowTime)) + "----:" + nowTime);

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
        initBanner(banner, goodsBean.getData().getGoods_goods().getImg());
        tvPicIndex.setText("1" + "/" + goodsBean.getData().getGoods_goods().getImg().size());
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //TODO
                if (tvPicIndex != null) {
                    tvPicIndex.setText((position + 1) + "/" + goodsBean.getData().getGoods_goods().getImg().size());
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
        if (isCollection == 0) {
            ivCollection.setImageResource(R.mipmap.icon_goods_detail_collection);
        } else {
            ivCollection.setImageResource(R.mipmap.icon_mine_collection);
        }
    }


    @Override
    public int initLayout() {
        return R.layout.activity_goods_detail;
    }

    @OnClick({R.id.iv_back, R.id.rl_open_select_spec, R.id.rl_to_evaluate_list, R.id.tv_add_to_cart, R.id.rl_add_to_collection, R.id.tv_buy_immediately})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_open_select_spec:
                goodsPropsSelectPop = new GoodsPropsSelectPop(this, goodsBean, specLists, storeLists);
                goodsPropsSelectPop.show(rlParent, new GoodsPropsSelectPop.ShoppingCartClick() {
                    @Override
                    public void onClick(int type, double rentPrice, double buyPrice) {

                    }
                });
                break;
            case R.id.rl_to_evaluate_list:
                Intent intent = new Intent(GoodsDetailActivity.this, GoodsEvalateListActivity.class);
                intent.putExtra("goodsId", goodsId);
                startActivity(intent);
                break;
            case R.id.tv_add_to_cart:
                goodsPropsSelectPop = new GoodsPropsSelectPop(this, goodsBean, specLists, storeLists);
                goodsPropsSelectPop.show(rlParent, new GoodsPropsSelectPop.ShoppingCartClick() {
                    @Override
                    public void onClick(int type, double rentPrice, double buyPrice) {

                    }
                });
                break;
            case R.id.rl_add_to_collection:
                if (((IPSCApplication) getApplication()).getUserInfo() != null) {
                    RequestParams params = new RequestParams(MainUrls.addToCollectionUrl);
                    params.addBodyParameter("access_token", IPSCApplication.accessToken);
                    params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
                    params.addBodyParameter("goods", goodsId + "");
                    goodsDetailPresenter.addGoodsToCollection(params, this, isCollection == 0 ? 1 : 2);
                } else {
                    ToastUtils.show(GoodsDetailActivity.this, "请先登录");
                    Intent intentToCollection = new Intent(GoodsDetailActivity.this, LoginByPasswordActivity.class);
                    startActivityForResult(intentToCollection, 100);
                }
                break;
            case R.id.tv_buy_immediately:
                goodsPropsSelectPop = new GoodsPropsSelectPop(this, goodsBean, specLists, storeLists);
                goodsPropsSelectPop.show(rlParent, new GoodsPropsSelectPop.ShoppingCartClick() {
                    @Override
                    public void onClick(int type, double rentPrice, double buyPrice) {

                    }
                });
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
            Log.e("TAG", "商品信息:" + result);
            try {
                Gson gson = new Gson();
                goodsBean = gson.fromJson(result, GoodsDetailRootBean.class);
                isCollection = goodsBean.getData().getColle();
                if (cunt == 0) {
                    specLists.addAll(goodsBean.getData().getSpeclist());
                    storeLists.addAll(goodsBean.getData().getStorelist());
                    cunt++;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                        if (goodsPropsSelectPop != null) {
                            goodsPropsSelectPop.setGoodsBean(goodsBean);
                        }
                    }
                });
                getGoodsEvaluate(goodsId);
            } catch (Exception e) {
                e.printStackTrace();
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
            Log.e("TAG", "商品评论:" + result);
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
                                            .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
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
            Log.e("TAG", "添加购物车:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    ToastUtils.show(GoodsDetailActivity.this, "添加购物车成功!");
                    goodsPropsSelectPop.dismiss();
                } else {
                    ToastUtils.show(GoodsDetailActivity.this, "添加购物车失败:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
}
