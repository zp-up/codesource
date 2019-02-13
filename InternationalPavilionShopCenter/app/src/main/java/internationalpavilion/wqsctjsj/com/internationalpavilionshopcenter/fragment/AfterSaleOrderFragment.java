package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.AfterSaleGoodsAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.AfterSaleAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.OrderAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class AfterSaleOrderFragment extends Fragment{
    private Unbinder unbinder;
    @BindView(R.id.rv_all_order)
    RecyclerView rvAfterSaleOrder;
    private ArrayList<OrderRootBean> orderList = new ArrayList<>();
    private AfterSaleAdapter adapter;
    @Override
    public void onResume() {
        super.onResume();
        if ( orderList.size() == 0){
            orderList.add(new OrderRootBean(1,1,1));
            orderList.add(new OrderRootBean(2,2,1));
            orderList.add(new OrderRootBean(3,3,1));
            orderList.add(new OrderRootBean(4,4,1));
        }
        if (adapter == null){
            adapter = new AfterSaleAdapter(getActivity(),orderList);
        }
        rvAfterSaleOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAfterSaleOrder.setAdapter(adapter);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_order,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
