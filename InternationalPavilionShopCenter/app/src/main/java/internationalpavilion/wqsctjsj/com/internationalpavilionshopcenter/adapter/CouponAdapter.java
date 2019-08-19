package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CouponBean;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CouponBean> data;
    private LayoutInflater inflater;

    public CouponAdapter(Context context, ArrayList<CouponBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.rv_coupon_item_layout, parent, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.rv_coupon_item_used_layout, parent, false);
                break;
        }
        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int type = data.get(position).getType();
        CouponBean couponBean = data.get(position);
        switch (type){
            case 1:
                if (couponBean.isOpen()){
                    holder.ivOpenOrClose1.setImageResource(R.mipmap.icon_coupon_up);
                    holder.tvUnOpen1.setVisibility(View.GONE);
                    holder.tvOpenMore1.setVisibility(View.VISIBLE);
                }else {
                    holder.ivOpenOrClose1.setImageResource(R.mipmap.icon_coupon_down);
                    holder.tvUnOpen1.setVisibility(View.VISIBLE);
                    holder.tvOpenMore1.setVisibility(View.GONE);
                }
                holder.tvUnOpen1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.get(position).setOpen(true);
                        notifyItemChanged(position);
                    }
                });
                holder.tvOpenMore1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.get(position).setOpen(false);
                        notifyItemChanged(position);
                    }
                });
                break;
            case 2:
                if (couponBean.isOpen()){
                    holder.ivOpenOrClose2.setImageResource(R.mipmap.icon_coupon_up);
                    holder.tvUnOpen2.setVisibility(View.GONE);
                    holder.tvOpenMore2.setVisibility(View.VISIBLE);
                }else {
                    holder.ivOpenOrClose2.setImageResource(R.mipmap.icon_coupon_down);
                    holder.tvUnOpen2.setVisibility(View.VISIBLE);
                    holder.tvOpenMore2.setVisibility(View.GONE);
                }
                holder.tvUnOpen2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.get(position).setOpen(true);
                        notifyItemChanged(position);
                    }
                });
                holder.tvOpenMore2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.get(position).setOpen(false);
                        notifyItemChanged(position);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = data.get(position).getType();
        return type;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //type = 1
        TextView tvUnOpen1,tvOpenMore1;
        ImageView ivOpenOrClose1;

        //type = 2
        TextView tvUnOpen2,tvOpenMore2;
        ImageView ivOpenOrClose2;

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            switch (viewType){
                case 1:
                    tvUnOpen1 = itemView.findViewById(R.id.tv_un_open);
                    tvOpenMore1 = itemView.findViewById(R.id.tv_open_more);
                    ivOpenOrClose1 = itemView.findViewById(R.id.iv_open_or_close);
                    break;
                case 2:
                    tvUnOpen2 = itemView.findViewById(R.id.tv_un_open);
                    tvOpenMore2 = itemView.findViewById(R.id.tv_open_more);
                    ivOpenOrClose2 = itemView.findViewById(R.id.iv_open_or_close);
                    break;
            }
        }
    }
}
