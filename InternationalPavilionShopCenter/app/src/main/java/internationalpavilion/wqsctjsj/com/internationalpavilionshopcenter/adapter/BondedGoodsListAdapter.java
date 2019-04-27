package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.util.DpUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.BondedGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.FriendsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBondedGoodsBean.HomeBondedGoodsBean;


/**
 * 分类商品列表
 */

public class BondedGoodsListAdapter extends RecyclerView.Adapter<BondedGoodsListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HomeBondedGoodsBean> data;
    private LayoutInflater inflater;

    public BondedGoodsListAdapter(Context context, ArrayList<HomeBondedGoodsBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_bonded_goods_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GridLayoutManager.LayoutParams layoutParamsP = (GridLayoutManager.LayoutParams) holder.llClickParent.getLayoutParams();
        int leftMargin = DpUtils.dpToPx(context, 6);
        if (position % 2 == 0) {
            layoutParamsP.setMargins(leftMargin + leftMargin / 3, leftMargin + leftMargin / 3, (leftMargin + leftMargin / 3) / 2, 0);

        } else {
            layoutParamsP.setMargins((leftMargin + leftMargin / 3) / 2, leftMargin + leftMargin / 3, leftMargin + leftMargin / 3, 0);

        }
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        layoutParamsP.width = width / 2 - (int) (leftMargin * 2);
        holder.llClickParent.setLayoutParams(layoutParamsP);

        final HomeBondedGoodsBean goodsBean = data.get(position);
        Glide.with(context).load(goodsBean.getGoodsPic()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)).into(holder.ivGoodsPic);
        holder.tvGoodsName.setText(goodsBean.getGoodsName());
        holder.tvGoodsPrice.setText("￥" + new DecimalFormat("#######0.00").format(goodsBean.getGoodsPrice()));
        holder.tvBrandArea.setText(goodsBean.getBrandName());
        holder.tvGoodsDescription.setText(goodsBean.getDescription());
        holder.llClickParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("goodsId", goodsBean.getGoodsId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llClickParent;
        ImageView ivGoodsPic;
        TextView tvGoodsDescription;
        TextView tvBrandArea;
        TextView tvGoodsName;
        TextView tvGoodsPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            llClickParent = itemView.findViewById(R.id.ll_click_container);
            ivGoodsPic = itemView.findViewById(R.id.iv_goods_pic);
            tvGoodsDescription = itemView.findViewById(R.id.tv_goods_description);
            tvBrandArea = itemView.findViewById(R.id.tv_created_by);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsPrice = itemView.findViewById(R.id.tv_goods_price);
        }
    }
}
