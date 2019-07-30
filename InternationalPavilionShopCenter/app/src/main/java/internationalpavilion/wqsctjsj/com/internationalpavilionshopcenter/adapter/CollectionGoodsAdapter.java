package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CollectionGoodsBean;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class CollectionGoodsAdapter extends RecyclerView.Adapter<CollectionGoodsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<CollectionGoodsBean> data;
    private LayoutInflater inflater;

    public CollectionGoodsAdapter(Context context, ArrayList<CollectionGoodsBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_collection_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getGoodsPic())
                .apply(new RequestOptions().error(R.mipmap.ic_default)).into(holder.ivGoodsPic);
        holder.tvGoodsName.setText(data.get(position).getGoodsName());
        holder.tvGoodsPrice.setText("￥"+data.get(position).getGoodsPrice());
//        holder.tvGoodsSpec.setText(data.get(position).getDescription());
        holder.tvToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("goodsId",data.get(position).getGoodsId());
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
        TextView tvGoodsSpec;
        TextView tvGoodsPrice;
        TextView tvToBuy;
        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsPic = itemView.findViewById(R.id.iv_goods_pic);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsSpec = itemView.findViewById(R.id.tv_goods_spec);
            tvGoodsPrice = itemView.findViewById(R.id.tv_goods_price);
            tvToBuy = itemView.findViewById(R.id.tv_to_buy);
        }
    }
}
