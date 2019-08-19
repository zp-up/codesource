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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.CouponAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CouponBean;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class CouponUnUseFragment extends Fragment {
    private Unbinder unbinder;
    @BindView(R.id.rv_coupon)
    RecyclerView rvCoupon;
    private ArrayList<CouponBean> data = new ArrayList<>();
    private CouponAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon_list_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (data.size() == 0) {
            data.add(new CouponBean(1, false));
            data.add(new CouponBean(1, false));
            data.add(new CouponBean(1, false));
            data.add(new CouponBean(1, false));
            data.add(new CouponBean(1, false));
            data.add(new CouponBean(1, false));
        }
        if (adapter == null) {
            adapter = new CouponAdapter(getActivity(), data);
            rvCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvCoupon.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
