package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.os.Handler;
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

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.CollectionGoodsAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CollectionGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBondedGoodsBean.HomeBondedGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class CollectionActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {
    private ArrayList<CollectionGoodsBean> data = new ArrayList<>();
    private CollectionGoodsAdapter adapter;
    private CommonDataInterface commonPresenter;
    private int pageIndex = 1;

    @BindView(R.id.rv_collection)
    RecyclerView rvCollection;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.tv_remind)
    TextView tvRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        commonPresenter = new CommonGoodsImp();
        initViews();
    }

    private void initViews() {
        srlContent.setEnableLoadmore(true);
        srlContent.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                initData();
            }
        });
        srlContent.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
                initData();
            }
        });
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.collectionGoodsListUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        }
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        commonPresenter.getCommonGoodsData(params, this);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter == null) {
            adapter = new CollectionGoodsAdapter(this, data);
            rvCollection.setLayoutManager(new LinearLayoutManager(this));
            rvCollection.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_collection;
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取数据中...");
        srlContent.finishRefresh();
        srlContent.finishLoadmore();
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "请求出错：" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            Log.e("TAG", "收藏列表:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (pageIndex == 1) {
                            CollectionActivity.this.data.clear();
                            adapter.notifyDataSetChanged();
                        }
                        if (data != null && data.length() != 0) {

                            for (int i = 0; i < data.length(); i++) {
                                CollectionGoodsBean bean = new CollectionGoodsBean();
                                int id = data.getJSONObject(i).getJSONObject("goods_goods").getInt("id");
                                String name = data.getJSONObject(i).getJSONObject("goods_goods").getJSONObject("goods_temp").getString("name");
//                                String description = data.getJSONObject(i).getJSONObject("goods_goods").getString("keyword");
                                String baseAreaName = data.getJSONObject(i).getJSONObject("goods_goods").getString("base_area").equals("0") ? "国内" : data.getJSONObject(i).getJSONObject("goods_goods").getJSONObject("base_area").getString("name");
                                double price = data.getJSONObject(i).getDouble("price");

                                String imgUrl = "";

                                JSONObject goods_goods = data.getJSONObject(i).getJSONObject("goods_goods");
                                if(goods_goods!=null){
                                    Object o = goods_goods.get("img");
                                    if(o!=null){
                                        if(o instanceof String){
                                            imgUrl = goods_goods.getString("img");
                                        }else if(o instanceof JSONArray){
                                            JSONArray imgArray = goods_goods.getJSONArray("img");
                                            if(imgArray!=null && imgArray.length()>0){
                                                imgUrl = imgArray.getString(0);
                                            }
                                        }
                                    }
                                }


                                bean.setGoodsId(id);
                                bean.setGoodsName(name);
                                bean.setBrandName(baseAreaName);
//                                bean.setDescription(description);
                                bean.setGoodsPic(imgUrl);
                                bean.setGoodsPrice(price);
                                CollectionActivity.this.data.add(bean);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            tvRemind.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (tvRemind != null) {
                                        tvRemind.setVisibility(View.GONE);
                                    } else {
                                        tvRemind = null;
                                        System.gc();
                                    }
                                }
                            }, 2000);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
