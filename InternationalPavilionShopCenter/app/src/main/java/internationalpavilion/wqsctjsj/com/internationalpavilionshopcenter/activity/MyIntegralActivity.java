package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.chrisjason.baseui.util.DpUtils;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.IntegralIncomeFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.IntegralOutFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class MyIntegralActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {
    @BindView(R.id.tv_integral_income)
    TextView tvIntegralIncome;
    @BindView(R.id.tv_integral_out)
    TextView tvIntegralOut;
    @BindView(R.id.vp_integral_list)
    ViewPager vpIntegral;
    @BindView(R.id.v_scroll)
    View viewScroll;
    @BindView(R.id.tv_my_exp)
    TextView tvMyExp;

    private int screenWidth;
    private int currentPosition = 1;
    private int pageIndex = 1;
    private MainVPAdapter vpAdapter;
    private List<Fragment> frgList = new ArrayList<>();

    private CommonDataInterface commonPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        commonPresenter = new CommonGoodsImp();
        initView();
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getUserInfoUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        if(((IPSCApplication) getApplication()).getUserInfo()!=null){
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
        commonPresenter.getCommonGoodsData(params, this);
    }

    private void initView() {
        frgList.add(new IntegralIncomeFragment());
        frgList.add(new IntegralOutFragment());
        vpAdapter = new MainVPAdapter(getSupportFragmentManager());
        vpIntegral.setAdapter(vpAdapter);
        vpIntegral.setOffscreenPageLimit(2);
        vpIntegral.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        change(1);
                        break;
                    case 1:
                        change(2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_integral_income, R.id.tv_integral_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_integral_income:
                change(1);
                break;
            case R.id.tv_integral_out:
                change(2);
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_integral;
    }

    @Override
    public void reloadData() {

    }

    private void change(int index) {
        switch (index) {
            case 1:
                tvIntegralIncome.setTextColor(Color.parseColor("#ff7d66"));
                tvIntegralOut.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(1);
                vpIntegral.setCurrentItem(0);
                break;
            case 2:
                tvIntegralIncome.setTextColor(Color.parseColor("#636363"));
                tvIntegralOut.setTextColor(Color.parseColor("#ff7d66"));
                scrollAnimal(2);
                vpIntegral.setCurrentItem(1);
                break;
        }
    }

    private void scrollAnimal(int position) {
        int width = screenWidth / 2 - 2 * DpUtils.dpToPx(this, 40);
        switch (position) {
            case 1:
                int pL = DpUtils.dpToPx(this, 40);
                if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", width + 2 * pL, 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 1;
                break;
            case 2:
                pL = DpUtils.dpToPx(this, 40);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 2;
                break;
        }
    }

    @Override
    public void onStarted() {
        showLoading(false, "获取数据中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "获取信息出错:" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            Log.e("TAG", "个人信息:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int exp = data.getInt("exp");
                        tvMyExp.setText("" + exp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MainVPAdapter extends FragmentPagerAdapter {

        public MainVPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return frgList.get(position);
        }

        @Override
        public int getCount() {
            return frgList.size();
        }
    }
}
