package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.IntegralListAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.IntegralInOrOutBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;
import io.reactivex.annotations.NonNull;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class IntegralIncomeFragment extends Fragment implements OnCommonGoodsCallBack {
    private Unbinder unbinder;
    @BindView(R.id.rv_all_order)
    RecyclerView rvIntegral;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.tv_time_month)
    TextView tvTimeMonth;

    private ArrayList<IntegralInOrOutBean> data = new ArrayList<>();
    private IntegralListAdapter adapter;
    private CommonDataInterface commonPresenter;
    private int pageIndex = 1;
    private String selectedDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_integral_income, container, false);
        unbinder = ButterKnife.bind(this, view);
        commonPresenter = new CommonGoodsImp();
        initView();
        return view;
    }

    private void initView() {
        srlContent.setEnableLoadMore(true);
        srlContent.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                initData();
            }
        });
        srlContent.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                initData();
            }

        });
    }

    @OnClick({R.id.iv_select_month})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_select_month:
                boolean[] type = new boolean[]{true, true, false, false, false, false};//显示类型 默认全部显示
                TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                        selectedDate = simpleDateFormat.format(date);
                        tvTimeMonth.setText(selectedDate);
                        Log.e("TAG", selectedDate);
                        pageIndex = 1;
                        initData();
                    }
                }).setType(type)
                        .build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter == null) {
            adapter = new IntegralListAdapter(getActivity(), data);
            rvIntegral.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvIntegral.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getExperienceListUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        if(getActivity()!=null && isAdded()){
            if(((IPSCApplication) getActivity().getApplication()).getUserInfo()!=null){
                params.addBodyParameter("user", ((IPSCApplication) getActivity().getApplication()).getUserInfo().getId() + "");
            }
        }

        params.addBodyParameter("type", "收入");
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        if (!TextUtils.isEmpty(selectedDate)) {
            params.addBodyParameter("create_time", selectedDate.split("-")[0] + selectedDate.split("-")[1]);
        }
        commonPresenter.getCommonGoodsData(params, this);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onStarted() {
        ((BaseAppcompatActivity) getActivity()).showLoading(false, "获取数据中...");
        srlContent.finishLoadMore();
        srlContent.finishRefresh();
    }

    @Override
    public void onFinished() {
        ((BaseAppcompatActivity) getActivity()).dismissLoading();
        srlContent.finishLoadMore();
        srlContent.finishRefresh();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "访问出错:" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            Log.e("TAG", "获取积分数据:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (pageIndex == 1) {
                            IntegralIncomeFragment.this.data.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (data != null && data.length() != 0) {
                            for (int i = 0; i < data.length(); i++) {
                                IntegralInOrOutBean inOrOutBean = new IntegralInOrOutBean(data.getJSONObject(i).getDouble("number")
                                        , data.getJSONObject(i).getString("type").equals("收入") ? 1 : 0, data.getJSONObject(i).getString("name"));
                                IntegralIncomeFragment.this.data.add(inOrOutBean);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                IntegralIncomeFragment.this.data.clear();
                adapter.notifyDataSetChanged();
                e.printStackTrace();
            }
        }
    }
}
