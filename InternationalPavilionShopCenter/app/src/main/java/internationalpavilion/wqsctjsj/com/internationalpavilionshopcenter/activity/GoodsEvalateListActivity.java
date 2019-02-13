package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.EvaluateListAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.EvaluateListBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsEvaluateList.GoodsEvaluateListBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.GoodsEvaluateImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.GoodsEvaluateInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnEvaluateListCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.RatingView;

public class GoodsEvalateListActivity extends BaseAppcompatActivity implements OnEvaluateListCallback {
    @BindView(R.id.rv_evaluate_list)
    RecyclerView rvEvaluateList;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.rv_star)
    RatingView rvStar;


    private ArrayList<GoodsEvaluateListBean> data = new ArrayList<>();
    private EvaluateListAdapter adapter;
    private GoodsEvaluateInterface goodsEvaluatePresenter;

    private int goodsId = -1;
    private int pageIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goodsId", -1);
        goodsEvaluatePresenter = new GoodsEvaluateImp();
        srlContent.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
        srlContent.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
                loadMore();
            }
        });
        srlContent.setEnableLoadmore(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter == null) {
            adapter = new EvaluateListAdapter(this, data);
            rvEvaluateList.setLayoutManager(new LinearLayoutManager(this));
            rvEvaluateList.setAdapter(adapter);
        }
        initData();
    }

    private void loadMore() {
        pageIndex = 1;
        RequestParams params = new RequestParams(MainUrls.getGoodsEvaluateUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("goods", goodsId + "");
        params.addBodyParameter("page", "" + pageIndex);
        params.addBodyParameter("limit", "10");
        goodsEvaluatePresenter.getAllEvaluateList(params, this);
    }

    private void initData() {
        pageIndex = 1;
        RequestParams params = new RequestParams(MainUrls.getGoodsEvaluateUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("goods", goodsId + "");
        params.addBodyParameter("page", "" + pageIndex);
        params.addBodyParameter("limit", "10");
        goodsEvaluatePresenter.getAllEvaluateList(params, this);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                GoodsEvalateListActivity.this.finish();
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_goods_evalate_list;
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取评论信息中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
        srlContent.finishLoadmore();
        srlContent.finishRefresh();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "出错:" + error);
    }

    @Override
    public void onGetEvaluateListSuccess(ArrayList<GoodsEvaluateListBean> result, double rating, double percent) {
        if (result != null) {
            if (pageIndex == 1) {
                data.clear();
                adapter.notifyDataSetChanged();
            }
            data.addAll(result);
            adapter.notifyDataSetChanged();

            if (pageIndex == 1) {
                tvPercent.setText(percent+"%好评");
                rvStar.setRating((float) rating);
            }
        }
    }
}
