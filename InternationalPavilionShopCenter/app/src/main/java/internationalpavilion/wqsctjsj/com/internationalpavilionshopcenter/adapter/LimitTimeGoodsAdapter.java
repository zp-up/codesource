package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.FriendsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.LimitTimeBean;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class LimitTimeGoodsAdapter extends RecyclerView.Adapter<LimitTimeGoodsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<LimitTimeBean> data;
    private LayoutInflater inflater;

    public LimitTimeGoodsAdapter(Context context, ArrayList<LimitTimeBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_limit_time_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LimitTimeBean bean = data.get(position);
        Glide.with(context).load(bean.getGoodsImg()).apply(new RequestOptions().override(300,300).placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1)).into(holder.ivGoodsPic);
        holder.tvStoreType.setText(bean.getStoreType());
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvPronPrice.setText("限时价 ￥"+new DecimalFormat("#####0.00").format(bean.getPron_price()));
        holder.tvOriginalPrice.setText("￥"+new DecimalFormat("#####0.00").format(bean.getPrice()));
        holder.tvSpec.setText(bean.getSpec());
        holder.llClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("goodsId",bean.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGoodsPic;
        TextView tvGoodsName;
        TextView tvSpec;
        TextView tvPronPrice;
        TextView tvOriginalPrice;
        TextView tvStoreType;
        LinearLayout llClickItem;
        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsPic = itemView.findViewById(R.id.iv_goods_pic);
            tvStoreType = itemView.findViewById(R.id.tv_store_type);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvSpec = itemView.findViewById(R.id.tv_goods_spec);
            tvPronPrice = itemView.findViewById(R.id.tv_pron_price);
            tvOriginalPrice = itemView.findViewById(R.id.tv_original_price);
            llClickItem = itemView.findViewById(R.id.ll_click_item);
        }
    }
}
