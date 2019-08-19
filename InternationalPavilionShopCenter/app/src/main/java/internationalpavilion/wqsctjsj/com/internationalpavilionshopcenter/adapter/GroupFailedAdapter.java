package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class GroupFailedAdapter extends RecyclerView.Adapter<GroupFailedAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderRootBean> data;
    private LayoutInflater inflater;

    public GroupFailedAdapter(Context context, ArrayList<OrderRootBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_order_group_failed_layout, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderRootBean rootBean = data.get(position);
        if (rootBean.getCount() == 1) {
            holder.rlOneGoodsParent1.setVisibility(View.VISIBLE);
        } else {
            holder.rlOneGoodsParent1.setVisibility(View.GONE);
            holder.llGoodsContainer1.removeAllViews();
            for (int i = 0; i < rootBean.getCount(); i++) {
                View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                holder.llGoodsContainer1.addView(view);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //type = 1
        LinearLayout llGoodsContainer1;
        RelativeLayout rlOneGoodsParent1;
        TextView tvTAG1;

        public ViewHolder(View item, int viewType) {
            super(item);
            llGoodsContainer1 = item.findViewById(R.id.ll_goods_container);
            rlOneGoodsParent1 = item.findViewById(R.id.rl_one_goods_parent);
            tvTAG1 = item.findViewById(R.id.tv_tag);
        }
    }
}
