package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.FriendsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.LogisticsBean;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<LogisticsBean> data;
    private LayoutInflater inflater;

    public LogisticsAdapter(Context context, ArrayList<LogisticsBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_logistic_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvContent.setText(data.get(position).acceptStation);
        holder.tvTime.setText(data.get(position).acceptTime);
        if (position == 0) {
            holder.viewLine1.setVisibility(View.INVISIBLE);
            holder.viewLine2.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.mipmap.icon_yuan_all);
            holder.tvContent.setTextColor(Color.parseColor("#636363"));
            holder.tvTime.setTextColor(Color.parseColor("#636363"));
        }else if(position == (data.size() - 1)){
            holder.viewLine1.setVisibility(View.VISIBLE);
            holder.viewLine2.setVisibility(View.INVISIBLE);
            holder.ivIcon.setImageResource(R.mipmap.kdi);
            holder.tvContent.setTextColor(Color.parseColor("#aa636363"));
            holder.tvTime.setTextColor(Color.parseColor("#aa636363"));
        }else {
            holder.viewLine1.setVisibility(View.VISIBLE);
            holder.viewLine2.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource(R.mipmap.kdi);
            holder.tvContent.setTextColor(Color.parseColor("#aa636363"));
            holder.tvTime.setTextColor(Color.parseColor("#aa636363"));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View viewLine1, viewLine2;
        ImageView ivIcon;
        TextView tvContent, tvTime;
        public ViewHolder(View itemView) {
            super(itemView);
            viewLine1 = itemView.findViewById(R.id.view_1);
            viewLine2 = itemView.findViewById(R.id.view_2);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_delivary_icon);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
