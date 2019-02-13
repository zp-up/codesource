package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.HomeBannerBean;



/**
 * Created by wuqaing on 2018/11/29.
 */

public class GoodsDetailAdapter extends DelegateAdapter.Adapter<GoodsDetailAdapter.ViewHolder> {
    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private final int TYPE_3 = 3;
    private final int TYPE_4 = 4;
    private ArrayList<HashMap<String, Object>> data;
    // 用于存放数据列表

    private Context context;
    private LayoutInflater inflater;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count ;
    private int type ;
    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public GoodsDetailAdapter(Context context, LayoutHelper layoutHelper, int count,
                              ArrayList<HashMap<String, Object>> data, int type) {
        //宽度占满，高度随意
        this(context, layoutHelper, count,
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT), data, type);
    }
    public int getType() {
        return type;
    }
    public ArrayList<HashMap<String, Object>> getData() {
        return data;
    }

    public RecyclerView.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public int getCount() {
        return data == null ? 0 : data.size();
    }
    public GoodsDetailAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull RecyclerView.LayoutParams layoutParams,
                              ArrayList<HashMap<String, Object>> data, int type) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.data = data;
        this.type = type;

        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_1:
                view = inflater.inflate(R.layout.rv_goods_detail_lay1, parent, false);
                break;
            case TYPE_2:
                view = inflater.inflate(R.layout.rv_goods_detail_layout2, parent, false);
                break;
            case TYPE_3:
                view = inflater.inflate(R.layout.rv_goods_detail_layout3, parent, false);
                break;
            case TYPE_4:
                view = inflater.inflate(R.layout.rv_goods_detail_layout4, parent, false);
                break;

        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_1:
                if (holder.banner != null){
                    ArrayList<HomeBannerBean> bannerBeanList = new ArrayList();
                    for (int i = 0 ; i < 6; i++){
                        HomeBannerBean bannerBean = new HomeBannerBean(1,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543505103010&di=589138e3cc0059d5b96aa2d945fe405d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d1d257747cce0000012e7e28e6ef.jpg%402o.jpg","https://www.baidu.com","",1);
                        bannerBeanList.add(bannerBean);
                    }
                    initBanner(holder.banner, bannerBeanList);
                    holder.tvPicIndex.setText("1"+"/6");
                    holder.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            //TODO
                            holder.tvPicIndex.setText((position+1)+"/6");
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }
                break;
            case TYPE_3:

                break;
            case TYPE_4:
                final WebView webview = new WebView(context);
                webview.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        webview.loadUrl("javascript:window.handler.resize(document.body.getBoundingClientRect().height)");
                        super.onPageFinished(view, url);
                    }
                });
//                webview.addJavascriptInterface(new JavascriptHandler(webview), "handler");
                WebSettings webSettings = webview.getSettings();
                if (Build.VERSION.SDK_INT >= 21) {
                    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }

                // 设置支持javascript脚本
                webSettings.setJavaScriptEnabled(true);

                // 设置此属性，可任意比例缩放
                webSettings.setUseWideViewPort(true);
                // 设置不出现缩放工具
                webSettings.setBuiltInZoomControls(false);
                // 设置不可以缩放
                webSettings.setSupportZoom(false);
                webSettings.setDisplayZoomControls(false);

                //自适应屏幕
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
                // 自适应 屏幕大小界面
                webSettings.setLoadWithOverviewMode(true);

                webview.loadUrl("http://www.everynew.cn/goods_pic_details@PC.htm?goodsId=198");
                //mWeb.loadDataWithBaseURL("about:blank", pish + productDetailResult.mainItem.summary + pas, mimeType, encoding, "about:blank");
                holder.llWebViewContainer.addView(webview);
                break;

        }

    }



    @Override
    public int getItemViewType(int position) {
        switch (type) {
            case 1:
                return TYPE_1;
            case 2:
                return TYPE_2;
            case 3:
                return TYPE_3;
            case 4:
                return TYPE_4;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //type = 1
        Banner banner;
        TextView tvPicIndex;

        //type = 4
        LinearLayout llWebViewContainer;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType){
                case TYPE_1:
                    banner = itemView.findViewById(R.id.goods_banner);
                    tvPicIndex = itemView.findViewById(R.id.tv_pic_index);
                    break;
                case TYPE_3:
                    break;
                case TYPE_4:
                    llWebViewContainer = itemView.findViewById(R.id.ll_webView_content);
                    break;
            }
        }
    }
    private void initBanner(Banner banner, ArrayList<HomeBannerBean> bannerBeanList) {
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
            if (path instanceof HomeBannerBean) {
                HomeBannerBean b = (HomeBannerBean) path;
                Log.e("TAG","bannerUrl："+b.getImgUrl());
                Glide.with(context).load(b.getImgUrl()).
                        apply(new RequestOptions().placeholder(R.drawable.pic_banner_place_holder).
                                error(R.drawable.pic_banner_error)).into(imageView);
            }
        }

    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            // 重新测量
            view.measure(w, h);

        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
        }
    }
}
