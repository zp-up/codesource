package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.CustomScrollViewPager;

/**
 * Created by wuqaing on 2018/12/6.
 */

public class GoodsScopelFragment extends Fragment {
    private Unbinder unbinder;
    private WebView webview;
    @BindView(R.id.ll_webView_container)
    LinearLayout llWebViewContent;
    private static GoodsScopelFragment instance;
    private ViewPager viewPager;
    private String html = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_detail_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initWebView();
        return view;
    }

    private void initWebView() {
        if (true) {
            llWebViewContent.removeAllViews();
            webview = new WebView(getActivity());
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
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

            //webview.loadUrl("http://www.everynew.cn/goods_pic_details@PC.htm?goodsId=198");
            webview.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
            llWebViewContent.addView(webview);
        }
    }

    public static GoodsScopelFragment getInstance(ViewPager viewPager,String html) {
        instance = new GoodsScopelFragment();
        instance.setViewPager(viewPager);
        instance.setHtmlText(html);
        return instance;
    }

    private void setHtmlText(String html) {
        this.html = html;
    }

    private void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
