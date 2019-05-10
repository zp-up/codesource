package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.BondedGoodsListAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.BondedGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.PriceData;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBondedGoodsBean.HomeBondedGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.BrandGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.BrandGoodsInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBrandGoodsLoaded;


public class BrandDetailActivity extends BaseAppcompatActivity implements OnBrandGoodsLoaded {

    private static final String TAG = "[IPSC][BrandDetailActivity]";
    @BindView(R.id.rv_bonded_goods)
    RecyclerView rvBondedGoods;
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

    @BindView(R.id.ll_goods_container)
    LinearLayout llGoodsContainer;

    @BindView(R.id.v_scroll)
    View viewScroll;
    @BindView(R.id.iv_brand_more)
    ImageView ivBrandMore;
    @BindView(R.id.iv_class_more)
    ImageView ivClassMore;
    @BindView(R.id.tv_remind)
    TextView tvRemind;

    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.tv_brand_title)
    TextView tvBrandTitle;
    @BindView(R.id.iv_shop_logo)
    ImageView ivShopLogo;
    @BindView(R.id.iv_brand_detail)
    ImageView ivBrandDetail;
    @BindView(R.id.tv_goods_online)
    TextView tvGoodsOnline;
    @BindView(R.id.tv_shop_description)
    TextView tvDescription;


    private ArrayList<HomeBondedGoodsBean> data = new ArrayList<>();
    private BondedGoodsListAdapter adapter;
    private int screenWidth;
    private int currentPosition = 1;
    private boolean brandIsOpen = false;
    private boolean classIsOpen = false;

    private int pageIndex = 1;
    private int priceState = 2;

    private int brandId = -1;
    private String brandName = "";
    private BrandGoodsInterface brandGoodsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        brandGoodsPresenter = new BrandGoodsImp();
        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        brandId = getIntent().getIntExtra("brandId", -1);
        brandName = getIntent().getStringExtra("brandName");
        tvBrandName.setText(brandName);
        tvBrandTitle.setText(brandName);
        Log.e("TAG", "品牌Id:" + brandId);
        initView();

        RequestParams params = new RequestParams(MainUrls.getBrandInformationUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", brandId + "");
        LogUtil.d(TAG, "params:" + params.toString());
        brandGoodsPresenter.getBrandInformation(params, this);

        initData();
    }

    private void initView() {
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
                pageIndex++;
                initData();
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
        RequestParams params = new RequestParams(MainUrls.getBrandGoodsListUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", brandId + "");
        params.addBodyParameter("page", "" + pageIndex);
        params.addBodyParameter("limit", "10");
        LogUtil.d(TAG, "params:" + params.toString());
        brandGoodsPresenter.getBrandGoodsList(params, this);

        if (adapter == null) {
            adapter = new BondedGoodsListAdapter(this, data);
            rvBondedGoods.setLayoutManager(new GridLayoutManager(this, 2));
            rvBondedGoods.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        llGoodsContainer.removeAllViews();
        for (int i = 0; i < 6; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.rv_home_item_goods, null);
            llGoodsContainer.addView(view);
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_filter_screen, R.id.tv_filter_popularity, R.id.tv_filter_discount, R.id.ll_filter_price,
            R.id.ll_brand_more, R.id.ll_class_more
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                BrandDetailActivity.this.finish();
                break;
            case R.id.ll_filter_screen:
                llScreenContainer.setVisibility(View.VISIBLE);
                animalIn(rlAnimal);
                change(4);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen_selected);
                ivPricePointer.setImageResource(R.mipmap.icon_filter_price);
                break;
            case R.id.tv_filter_popularity:
                change(1);
                ivPricePointer.setImageResource(R.mipmap.icon_filter_price);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen);
                break;
            case R.id.tv_filter_discount:
                change(2);
                ivPricePointer.setImageResource(R.mipmap.icon_filter_price);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen);
                break;
            case R.id.ll_filter_price:
                change(3);
                ivPricePointer.setImageResource(R.mipmap.icon_filter_price_up);
                ivFilterScreenPointer.setImageResource(R.mipmap.icon_filter_screen);
                break;
            case R.id.ll_brand_more:
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
            case R.id.ll_class_more:
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
            case 1:
                tvFilterPopularity.setTextColor(Color.parseColor("#ff0000"));
                tvFilterDiscount.setTextColor(Color.parseColor("#636363"));
                tvFilterPrice.setTextColor(Color.parseColor("#636363"));
                tvFilterScreen.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(1);
                break;
            case 2:
                tvFilterPopularity.setTextColor(Color.parseColor("#636363"));
                tvFilterDiscount.setTextColor(Color.parseColor("#ff0000"));
                tvFilterPrice.setTextColor(Color.parseColor("#636363"));
                tvFilterScreen.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(2);
                break;
            case 3:
                tvFilterPopularity.setTextColor(Color.parseColor("#636363"));
                tvFilterDiscount.setTextColor(Color.parseColor("#636363"));
                tvFilterPrice.setTextColor(Color.parseColor("#ff0000"));
                tvFilterScreen.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(3);
                break;
            case 4:
                tvFilterPopularity.setTextColor(Color.parseColor("#636363"));
                tvFilterDiscount.setTextColor(Color.parseColor("#636363"));
                tvFilterPrice.setTextColor(Color.parseColor("#636363"));
                tvFilterScreen.setTextColor(Color.parseColor("#ff0000"));
                scrollAnimal(4);
                break;
        }
    }

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

    @Override
    public int initLayout() {
        return R.layout.activity_brand_detail;
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
        srlContent.finishRefresh();
        srlContent.finishLoadmore();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "获取品牌商品出错:" + error);
    }

    @Override
    public void onBrandGoodsLoaded(String result) {
        if (result != null) {
            Log.e("TAG", "品牌商品:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (state == 0 && code == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (pageIndex == 1) {
                            if (currentPosition == 3) {
                                if (priceState == 1) {
                                    ivPricePointer.setImageResource(R.mipmap.icon_filter_price_up);
                                } else {
                                    ivPricePointer.setImageResource(R.mipmap.icon_filter_price_down);
                                }
                            } else {
                                ivPricePointer.setImageResource(R.mipmap.icon_filter_price);
                                priceState = 2;
                            }
                            BrandDetailActivity.this.data.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (data != null && data.length() != 0) {
                            for (int i = 0; i < data.length(); i++) {
                                HomeBondedGoodsBean bean = new HomeBondedGoodsBean();
                                int id = data.getJSONObject(i).getJSONObject("goods_goods").getInt("id");
                                String name = data.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                                String description = data.getJSONObject(i).getJSONObject("goods_goods").getString("keyword");
                                String baseAreaName = data.getJSONObject(i).getJSONObject("goods_goods").getString("base_area").contains("0") ? "国内" : data.getJSONObject(i).getJSONObject("goods_goods").getJSONObject("base_area").getString("name");
                                double price = data.getJSONObject(i).getDouble("price");
                                String goodsPic = data.getJSONObject(i).getJSONObject("goods_goods").getJSONObject("goods_temp").getJSONArray("img").getString(0);
                                bean.setGoodsId(id);
                                bean.setGoodsName(name);
                                bean.setBrandName(baseAreaName);
                                bean.setDescription(description);
                                bean.setGoodsPic(goodsPic);
                                bean.setGoodsPrice(price);
                                BrandDetailActivity.this.data.add(bean);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            tvRemind.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (tvRemind != null) {
                                        tvRemind.setVisibility(View.GONE);
                                    } else {
                                        tvRemind = null;
                                        System.gc();
                                    }
                                }
                            }, 2000);
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "parse data occur an exception!", e);
            }
        }
    }

    @Override
    public void onBrandInfoLoaded(String result) {
        if (result != null) {
            Log.e("TAG", "品牌信息:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (data != null && data.has("data")) {
                        String logo = data.getJSONObject("data").getJSONArray("logo").getString(0);

                        // 后台数据不稳定，json数组有时候会以空字符串来反馈
                        String imgExtend = "";
                        if (!TextUtils.isEmpty(data.getJSONObject("data").getString("img_extend"))) {
                            imgExtend = data.getJSONObject("data").getJSONArray("img_extend").getString(0);
                        }
                        String title = data.getJSONObject("data").getString("name");
                        int goodsUp = data.getJSONObject("data").getInt("goodsup");
                        tvBrandTitle.setText(title);
                        Glide.with(BrandDetailActivity.this).load(logo).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
                                .into(ivShopLogo);
                        if (!imgExtend.isEmpty()) {
                            Glide.with(BrandDetailActivity.this).load(imgExtend).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
                                    .into(ivBrandDetail);
                        }
                        int goodsCount = data.getInt("count");
                        tvGoodsOnline.setText("在售商品" + goodsCount + "个");
                        String description = data.getJSONObject("data").getString("descript");
                        tvDescription.setText(description);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
