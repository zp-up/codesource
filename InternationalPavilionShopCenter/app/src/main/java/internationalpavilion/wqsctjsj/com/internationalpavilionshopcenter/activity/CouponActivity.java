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
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.chrisjason.baseui.util.DpUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.CouponOverdueFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.CouponUnUseFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.CouponUsedFragment;

public class CouponActivity extends BaseAppcompatActivity {
    @BindView(R.id.vp_coupon)
    ViewPager vpCoupon;
    @BindView(R.id.tv_coupon_un_use)
    TextView tvCouponUnUse;
    @BindView(R.id.tv_coupon_used)
    TextView tvCouponUsed;
    @BindView(R.id.tv_coupon_overdue)
    TextView tvCouponOverdue;
    @BindView(R.id.v_scroll)
    View viewScroll;

    private MainVPAdapter vpAdapter;
    private ArrayList<Fragment> fgList = new ArrayList<>();
    private int currentPosition = 1;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        initView();
    }

    private void initView() {
        fgList.add(new CouponUnUseFragment());
        fgList.add(new CouponUsedFragment());
        fgList.add(new CouponOverdueFragment());

        vpAdapter = new MainVPAdapter(getSupportFragmentManager());
        vpCoupon.setOffscreenPageLimit(3);
        vpCoupon.setAdapter(vpAdapter);
        vpCoupon.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        change(1);
                        break;
                    case 1:
                        change(2);
                        break;
                    case 2:
                        change(3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.tv_coupon_un_use, R.id.tv_coupon_used, R.id.tv_coupon_overdue,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_coupon_un_use:
                change(1);
                break;
            case R.id.tv_coupon_used:
                change(2);
                break;
            case R.id.tv_coupon_overdue:
                change(3);
                break;
            case R.id.iv_back:
                CouponActivity.this.finish();
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_coupon;
    }

    @Override
    public void reloadData() {

    }

    class MainVPAdapter extends FragmentPagerAdapter {

        public MainVPAdapter(FragmentManager fm) {
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

    private void change(int index) {
        switch (index) {
            case 1:
                tvCouponUnUse.setTextColor(Color.parseColor("#ff7d66"));
                tvCouponUsed.setTextColor(Color.parseColor("#636363"));
                tvCouponOverdue.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(1);
                vpCoupon.setCurrentItem(0);
                break;
            case 2:
                tvCouponUnUse.setTextColor(Color.parseColor("#636363"));
                tvCouponUsed.setTextColor(Color.parseColor("#ff7d66"));
                tvCouponOverdue.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(2);
                vpCoupon.setCurrentItem(1);
                break;
            case 3:
                tvCouponUnUse.setTextColor(Color.parseColor("#636363"));
                tvCouponUsed.setTextColor(Color.parseColor("#636363"));
                tvCouponOverdue.setTextColor(Color.parseColor("#ff7d66"));
                scrollAnimal(3);
                vpCoupon.setCurrentItem(2);
                break;
        }
    }

    private void scrollAnimal(int position) {
        int width = screenWidth / 3 - 2 * DpUtils.dpToPx(this, 40);
        switch (position) {
            case 1:
                int pL = DpUtils.dpToPx(this, 40);
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
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (2 * width + 4 * pL), (width + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 2;
                break;
            case 3:
                pL = DpUtils.dpToPx(this, 40);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (width + 2 * pL), (2 * width + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 3;
                break;
        }
    }
}
