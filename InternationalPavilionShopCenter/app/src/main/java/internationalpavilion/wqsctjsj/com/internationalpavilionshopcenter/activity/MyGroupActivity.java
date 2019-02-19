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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GroupFailedFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.GroupSuccessFragment;

public class MyGroupActivity extends BaseAppcompatActivity {
    @BindView(R.id.tv_join_group_success)
    TextView tvGroupSuccess;
    @BindView(R.id.tv_join_group_failed)
    TextView tvGroupFailed;

    @BindView(R.id.v_scroll)
    View viewScroll;
    @BindView(R.id.vp_group_list)
    ViewPager vpGroupList;

    private int screenWidth;
    private int currentPosition = 1;
    private MainVPAdapter vpAdapter;
    private List<Fragment> frgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        initView();
    }

    @OnClick({R.id.tv_join_group_success, R.id.tv_join_group_failed,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_join_group_success:
                change(1);
                break;
            case R.id.tv_join_group_failed:
                change(2);
                break;
            case R.id.iv_back:
                MyGroupActivity.this.finish();
                break;
        }
    }

    private void initView() {
        frgList.add(new GroupSuccessFragment());
        //frgList.add(new GroupFailedFragment());
        vpAdapter = new MainVPAdapter(getSupportFragmentManager());
        vpGroupList.setAdapter(vpAdapter);
        vpGroupList.setOffscreenPageLimit(1);
        vpGroupList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @Override
    public int initLayout() {
        return R.layout.activity_my_group;
    }

    @Override
    public void reloadData() {

    }
    class MainVPAdapter extends FragmentPagerAdapter{

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
    private void change(int index) {
        switch (index) {
            case 1:
                tvGroupSuccess.setTextColor(Color.parseColor("#ff7d66"));
                tvGroupFailed.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(1);
                vpGroupList.setCurrentItem(0);
                break;
            case 2:
                tvGroupSuccess.setTextColor(Color.parseColor("#636363"));
                tvGroupFailed.setTextColor(Color.parseColor("#ff7d66"));
                scrollAnimal(2);
                vpGroupList.setCurrentItem(1);
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
}
