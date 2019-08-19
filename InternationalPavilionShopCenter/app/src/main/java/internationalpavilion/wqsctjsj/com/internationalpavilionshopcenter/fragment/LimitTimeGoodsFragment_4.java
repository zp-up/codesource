package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.LimitTimeGoodsAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.LimitTimeBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.LimitTimeGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.LimitTimeGoodsInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnLimitTimeGoodsCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView;

/**
 * Created by wuqaing on 2018/12/7.
 */

public class LimitTimeGoodsFragment_4 extends Fragment implements OnLimitTimeGoodsCallback {
    private Unbinder unbinder;

    @BindView(R.id.tv_surplus)
    TimerView tvSurplus;
    @BindView(R.id.rv_goods_list)
    RecyclerView rvGoodsList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;

    private static LimitTimeGoodsFragment_4 INSTANCE;

    private LimitTimeGoodsAdapter adapter;
    private ArrayList<LimitTimeBean> data = new ArrayList<>();
    private static int startTime = -100;
    private static int space = -1;
    private static int currentPosition;
    private LimitTimeGoodsInterface limitTimeGoodsPresenter;
    private int pageIndex = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_limit_time_goods_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        limitTimeGoodsPresenter = new LimitTimeGoodsImp();
        initView();
        return view;
    }

    public static LimitTimeGoodsFragment_4 getInstance(int startTime, int space, int currentPosition) {
        if (INSTANCE == null) {
            INSTANCE = new LimitTimeGoodsFragment_4();
        }
        LimitTimeGoodsFragment_4.startTime = startTime;
        LimitTimeGoodsFragment_4.space = space;
        LimitTimeGoodsFragment_4.currentPosition = currentPosition;
        return INSTANCE;
    }

    private void initView() {
        if (currentPosition == 2) {
            tvTitle.setText("本场还剩");
            tvSurplus.setVisibility(View.VISIBLE);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = sdf.format(new Date());
            String lastString = nowDate.substring(nowDate.indexOf(" ") + 1, nowDate.length());
            String[] timeArray = lastString.split(":");
            int nowHour = Integer.valueOf(timeArray[0]);
            int nowMinute = Integer.valueOf(timeArray[1]);
            int nowSecond = Integer.valueOf(timeArray[2]);
            int endHour = startTime + space;
            if (tvSurplus.getRunning()) {
                tvSurplus.stop();
            }
            tvSurplus.setTime(((60 - nowSecond) * 1000 + (60 - nowMinute - 1) * 60 * 1000 + (endHour - nowHour - 1) * 60 * 60 * 1000), TimerView.TIMETYPE_MS);
            tvSurplus.start();
        } else {
            if (currentPosition == 1) {
                tvSurplus.setVisibility(View.GONE);
                tvTitle.setText("已开抢");
            } else {
                tvSurplus.setVisibility(View.GONE);
                tvTitle.setText("即将开始");
            }
        }
        srlContent.setEnableLoadMore(true);
        srlContent.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
        srlContent.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }

        });
        adapter = new LimitTimeGoodsAdapter(getActivity(), data);
        rvGoodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvGoodsList.setAdapter(adapter);
        initData();
    }

    private void initData() {
        Log.e("TAG", "开始时间:" + startTime + "  区间:" + space);
        pageIndex = 1;
        RequestParams params = new RequestParams(MainUrls.getLimitTimeGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("time", "" + startTime);
        params.addBodyParameter("space", "" + space);
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        limitTimeGoodsPresenter.getLimitTimeGoodsList(params, this);
    }

    private void loadMore() {
        pageIndex++;
        RequestParams params = new RequestParams(MainUrls.getLimitTimeGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("time", "" + startTime);
        params.addBodyParameter("space", "" + space);
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        limitTimeGoodsPresenter.getLimitTimeGoodsList(params, this);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onStarted() {
        ((BaseAppcompatActivity) getActivity()).showLoading(false, "获取数据中...");
    }

    @Override
    public void onFinished() {
        ((BaseAppcompatActivity) getActivity()).dismissLoading();
        srlContent.finishRefresh();
        srlContent.finishLoadMore();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onLimitTimeGoodsLoaded(String result) {
        Log.e("TAG", "限时购商品:" + result);
        if (result != null) {
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
            Log.e("TAG", "startTime:" + startTime + " ---  endTime:" + endTime);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data")) {
                        if (pageIndex == 1) {
                            data.clear();
                            adapter.notifyDataSetChanged();
                        }
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int id = jsonArray.getJSONObject(i).getInt("id");
                            String goodsName = jsonArray.getJSONObject(i).getJSONObject("goods_goods").getString("name");
                            double price = jsonArray.getJSONObject(i).getDouble("price_m");
                            String picUrl = jsonArray.getJSONObject(i).getJSONObject("goods_goods").getJSONArray("img").getString(0);
                            long startTimes = jsonArray.getJSONObject(i).getLong("pron_add_time");
                            long endTimes = jsonArray.getJSONObject(i).getLong("pron_end_time");
                            double pron_price = jsonArray.getJSONObject(i).getDouble("pron_price");
                            String storeName = jsonArray.getJSONObject(i).getJSONObject("store_store").getString("name");
                            String storeType = jsonArray.getJSONObject(i).getJSONObject("store_store").getString("type");
                            String spec = jsonArray.getJSONObject(i).getJSONObject("goods_goods").getString("spec");
                            LimitTimeBean goodsBean = new LimitTimeBean();
                            goodsBean.setPron_price(pron_price);
                            goodsBean.setSpec(spec);
                            goodsBean.setStoreName(storeName);
                            goodsBean.setStoreType(storeType);
                            goodsBean.setEndTime(endTimes);
                            goodsBean.setGoodsImg(picUrl);
                            goodsBean.setGoodsName(goodsName);
                            goodsBean.setId(id);
                            goodsBean.setEndHour(endTime);
                            goodsBean.setPrice(price);
                            goodsBean.setStartTime(startTimes);
                            data.add(goodsBean);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
