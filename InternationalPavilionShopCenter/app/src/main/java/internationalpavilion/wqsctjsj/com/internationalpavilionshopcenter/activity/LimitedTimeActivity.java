package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.LimitTimeGoodsFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.LimitTimeGoodsFragment_1;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.LimitTimeGoodsFragment_2;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.LimitTimeGoodsFragment_3;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.LimitTimeGoodsFragment_4;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.LimitTimeGoodsFragment_5;

public class LimitedTimeActivity extends BaseAppcompatActivity {
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ArrayList<Fragment> fgList = new ArrayList<>();
    private String[] tabs = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initView();
    }

    private void initView() {
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int startTime = hour;
        int endTime = 0;
        if (hour % 4 == 1) {
            startTime -= 1;
        } else if (hour % 4 == 2) {
            startTime -= 2;
        } else if (hour % 4 == 3) {
            startTime -= 3;
        } else if (hour % 4 == 0) {
            startTime = hour;
        }
        endTime = startTime + 4;


        tabs[0] = ((startTime - 4) < 0 ? (24 - Math.abs(startTime - 4)) : (startTime - 4)) + ":00已开抢";
        tabs[1] = startTime + ":00\n抢购中";
        tabs[2] = endTime + ":00\n即将开始";
        tabs[3] = (endTime + 4 > 24 ? endTime + 4 - 24 : endTime + 4) + ":00\n即将开始";
        tabs[4] = (endTime + 8 > 24 ? endTime + 8 - 24 : endTime + 8) + ":00\n即将开始";
        tabs[5] = (endTime + 12 > 24 ? endTime + 12 - 24 : endTime + 12) + ":00\n即将开始";

        fgList.add(LimitTimeGoodsFragment.getInstance((startTime - 4), 4, 1));
        fgList.add(LimitTimeGoodsFragment_1.getInstance(startTime, 4, 2));
        fgList.add(LimitTimeGoodsFragment_2.getInstance(endTime, 4, 3));
        fgList.add(LimitTimeGoodsFragment_3.getInstance((endTime + 4), 4, 4));
        fgList.add(LimitTimeGoodsFragment_4.getInstance((endTime + 8), 4, 5));
        fgList.add(LimitTimeGoodsFragment_5.getInstance((endTime + 12), 4, 6));

        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(new MainVPAdapter(getSupportFragmentManager()));
        slidingTabLayout.setViewPager(viewPager, tabs);
        viewPager.setCurrentItem(1);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_limited_time;
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                LimitedTimeActivity.this.finish();
                break;
        }
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
}
