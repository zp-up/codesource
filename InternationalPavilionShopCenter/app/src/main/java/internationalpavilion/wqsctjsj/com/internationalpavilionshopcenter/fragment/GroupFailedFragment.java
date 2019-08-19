package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.GroupFailedAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class GroupFailedFragment extends Fragment{
    private Unbinder unbinder;

    private ArrayList<OrderRootBean> rootBeans = new ArrayList<>();
    private GroupFailedAdapter adapter;
    @BindView(R.id.rv_all_order)
    RecyclerView rvGroupFailed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_order,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootBeans.size() == 0 ){
            rootBeans.add(new OrderRootBean(1,1,3));
            rootBeans.add(new OrderRootBean(1,1,3));
            rootBeans.add(new OrderRootBean(1,1,3));
            rootBeans.add(new OrderRootBean(1,1,3));
            rootBeans.add(new OrderRootBean(1,1,3));
        }
        if (adapter == null){
            adapter = new GroupFailedAdapter(getActivity(),rootBeans);
        }
        rvGroupFailed.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvGroupFailed.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
