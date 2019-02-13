package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.IntegralInOrOutBean;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class IntegralListAdapter extends RecyclerView.Adapter<IntegralListAdapter.ViewHolder>{
    private Context context;
    private ArrayList<IntegralInOrOutBean> data;
    private LayoutInflater inflater;

    public IntegralListAdapter(Context context, ArrayList<IntegralInOrOutBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_integral_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IntegralInOrOutBean bean = data.get(position);
        if (bean.getInOrOut() == 1){
            holder.tvInOrOutPrice.setText("+"+bean.getPrice());
        }else {
            holder.tvInOrOutPrice.setText("-"+bean.getPrice());
        }
        holder.tvContent.setText(bean.getContent());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvContent;
        TextView tvInOrOutPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvInOrOutPrice = itemView.findViewById(R.id.tv_in_or_out_price);
        }
    }
}
