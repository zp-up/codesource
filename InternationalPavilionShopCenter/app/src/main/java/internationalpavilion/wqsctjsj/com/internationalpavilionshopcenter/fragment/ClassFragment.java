package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.chrisjason.baseui.util.DpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.SearchActivity;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class ClassFragment extends Fragment{
    private Unbinder unbinder;
    @BindView(R.id.tv_all_class)
    TextView tvAllClass;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;
    @BindView(R.id.v_scroll)
    View viewScroll;
    @BindView(R.id.vp_class_container)
    ViewPager vpContainer;

    private Fragment classInClassFragment;
    private Fragment classInBrandFragment;
    private Fragment classInPopularityFragment;
    private Fragment[] fragments;

    private int currentPosition = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_layout,container,false);
        unbinder = ButterKnife.bind(this,view);
        initFragments();
        return view;
    }

    private void initFragments() {
        fragments = new Fragment[3];
        fragments[0] = new ClassInClassFragment();
        fragments[1] = new ClassInBrandFragment();
        fragments[2] = new ClassInPopularityFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        vpContainer.setAdapter(adapter);
        vpContainer.setOffscreenPageLimit(3);
        vpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @OnClick({R.id.tv_all_class,R.id.tv_brand,R.id.tv_popularity,R.id.rl_search_container})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_all_class:
                change(1);
                break;
            case R.id.tv_brand:
                change(2);
                break;
            case R.id.tv_popularity:
                change(3);
                break;
            case R.id.rl_search_container:
                if(getActivity()!=null && isAdded()){
                    getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                }

                break;
        }
    }
    private void change(int index) {
        switch (index) {
            case 1:
                tvAllClass.setTextColor(Color.parseColor("#ff7d66"));
                tvBrand.setTextColor(Color.parseColor("#636363"));
                tvPopularity.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(1);
                vpContainer.setCurrentItem(0);
                break;
            case 2:
                tvAllClass.setTextColor(Color.parseColor("#636363"));
                tvBrand.setTextColor(Color.parseColor("#ff7d66"));
                tvPopularity.setTextColor(Color.parseColor("#636363"));
                scrollAnimal(2);
                vpContainer.setCurrentItem(1);
                break;
            case 3:
                tvAllClass.setTextColor(Color.parseColor("#636363"));
                tvBrand.setTextColor(Color.parseColor("#636363"));
                tvPopularity.setTextColor(Color.parseColor("#ff7d66"));
                scrollAnimal(3);
                vpContainer.setCurrentItem(2);
                break;
        }
    }
    private void scrollAnimal(int position) {
        switch (position) {
            case 1:
                int pL = DpUtils.dpToPx(getActivity(), 10);
                if (currentPosition == 2) {
                    Log.e("TAG", "执行:滑动距离:" + viewScroll.getWidth());
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", viewScroll.getWidth() + 2 * pL, 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", (2 * viewScroll.getWidth() + 4 * pL), 0);
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 1;
                break;
            case 2:
                pL = DpUtils.dpToPx(getActivity(), 10);
                if (currentPosition == 1) {
                    Log.e("TAG", "执行:滑动距离:" + (viewScroll.getWidth() + pL));
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (viewScroll.getWidth() + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 3) {
                    Log.e("TAG", "执行:滑动距离:" + (viewScroll.getWidth() + pL));
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 4 * pL + 2 * viewScroll.getWidth(), (viewScroll.getWidth() + 2 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 2;
                break;
            case 3:
                pL = DpUtils.dpToPx(getActivity(), 10);
                if (currentPosition == 1) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 0, (2 * viewScroll.getWidth() + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                } else if (currentPosition == 2) {
                    ObjectAnimator obA = ObjectAnimator.ofFloat(viewScroll, "translationX", 2 * pL + viewScroll.getWidth(), (2 * viewScroll.getWidth() + 4 * pL));
                    obA.setDuration(300);
                    obA.setInterpolator(new LinearInterpolator());
                    obA.start();
                }
                currentPosition = 3;
                break;
        }
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

    }
    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
