package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.AfterSaleGoodsAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.AfterSaleAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.OrderAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.orderBeans.OrderGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class AfterSaleOrderFragment extends Fragment implements OnCommonGoodsCallBack {

    public static final String TAG = "[IPSC][AfterSaleOrderFragment]";
    private Unbinder unbinder;
    @BindView(R.id.rv_all_order)
    RecyclerView rvAfterSaleOrder;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    private ArrayList<OrderRootBean> orderList = new ArrayList<>();
    private AfterSaleAdapter adapter;
//    private OrderAdapter adapter;
    private CommonDataInterface commonPresenter;
    private int pageIndex = 1;

    @Override
    public void onResume() {
        super.onResume();
//        if ( orderList.size() == 0){
//            orderList.add(new OrderRootBean(1,1,1));
//            orderList.add(new OrderRootBean(2,2,1));
//            orderList.add(new OrderRootBean(3,3,1));
//            orderList.add(new OrderRootBean(4,4,1));
//        }
        if (adapter == null){
            adapter = new AfterSaleAdapter(getActivity(),orderList);
//            adapter = new OrderAdapter(getActivity(),orderList);
        }
        rvAfterSaleOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAfterSaleOrder.setAdapter(adapter);
        pageIndex = 1;
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getOrderListUlr);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        if(getActivity()!=null && isAdded()){
            if(((IPSCApplication) getActivity().getApplication()).getUserInfo()!=null){
                params.addBodyParameter("user", ((IPSCApplication)getActivity().getApplication()).getUserInfo().getId() + "");
            }
        }

        Log.d(TAG, "initData() user:" + ((IPSCApplication) getActivity().getApplication()).getUserInfo().getId());
        params.addBodyParameter("type", "5");
        params.addBodyParameter("page", pageIndex + "");
        params.addBodyParameter("limit", "10");
        commonPresenter.getCommonGoodsData(params, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_order,container,false);
        unbinder = ButterKnife.bind(this,view);
        commonPresenter = new CommonGoodsImp();
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
                pageIndex ++;
                initData();
            }
        });
        return view;
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
        srlContent.finishLoadmore();
    }

    @Override
    public void onError(String error) {
        Log.e(TAG, "出错：" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            Log.e(TAG, "订单信息:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (pageIndex == 1) {
                        orderList.clear();
                        adapter.notifyDataSetChanged();
                    }
                    if (data != null && data.length() != 0) {
                        for (int i = 0; i < data.length(); i++) {
                            OrderRootBean orderRootBean = new OrderRootBean();
                            orderRootBean.setId(data.getJSONObject(i).getInt("id"));
                            orderRootBean.setCreate_time(data.getJSONObject(i).getString("create_time"));
                            orderRootBean.setAfterSaleState(data.getJSONObject(i).getString("asse"));
                            orderRootBean.setOrderNumber(data.getJSONObject(i).getString("sn"));
                            orderRootBean.setPay_total(data.getJSONObject(i).getDouble("pay_total"));
                            orderRootBean.setRefund_goods(data.getJSONObject(i).has("refund_goods") ? data.getJSONObject(i).getString("refund_goods") : "无退货");
                            orderRootBean.setRefund_goods_time(data.getJSONObject(i).has("refund_goods_time") ? data.getJSONObject(i).getLong("refund_goods_time") : 0);
                            orderRootBean.setRefund_state(data.getJSONObject(i).has("refund_state") ? data.getJSONObject(i).getString("refund_state") : "无退货");
                            orderRootBean.setRefund_time(data.getJSONObject(i).has("refund_time") ? data.getJSONObject(i).getLong("refund_time") : 0);
                            orderRootBean.setStatus(data.getJSONObject(i).getString("status"));
                            orderRootBean.setStoreType(data.getJSONObject(i).getJSONObject("store_store").getString("type"));
                            List<OrderGoodsBean> orderGoodsBeans = new ArrayList<>();
                            if (data.getJSONObject(i).has("list") && data.getJSONObject(i).getJSONArray("list") != null && data.getJSONObject(i).getJSONArray("list").length() != 0) {
                                JSONArray list = data.getJSONObject(i).getJSONArray("list");
                                for (int j = 0; j < list.length(); j++) {
                                    OrderGoodsBean orderGoodsBean = new OrderGoodsBean();
                                    orderGoodsBean.setGoodsId(list.getJSONObject(j).getJSONObject("goods_goods").getInt("id"));
                                    orderGoodsBean.setGoodsName(list.getJSONObject(j).getJSONObject("goods_goods").getString("name"));
                                    orderGoodsBean.setGoodsDescription(list.getJSONObject(j).getJSONObject("goods_goods").getString("spec"));
                                    orderGoodsBean.setImageUrl(list.getJSONObject(j).getJSONObject("goods_goods").getJSONArray("img").getString(0));
                                    orderGoodsBeans.add(orderGoodsBean);
                                }
                            }
                            orderRootBean.setGoodsBeans(orderGoodsBeans);
                            orderList.add(orderRootBean);
                        }
                    }else {
                        ToastUtils.show(getActivity(),"已无更多订单");
                    }
                    adapter.notifyDataSetChanged();
                }
//                adapter.setData(orderList);
//                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
