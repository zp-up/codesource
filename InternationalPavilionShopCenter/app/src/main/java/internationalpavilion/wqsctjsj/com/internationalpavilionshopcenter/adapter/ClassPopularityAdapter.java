package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.util.DpUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.HomeBannerBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homePopularityGoods.HomePopularityGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class ClassPopularityAdapter extends DelegateAdapter.Adapter<ClassPopularityAdapter.ViewHolder> {
    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;

    private ArrayList<HashMap<String, Object>> data;
    // 用于存放数据列表

    private Context context;
    private LayoutInflater inflater;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count ;
    private int type ;
    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public ClassPopularityAdapter(Context context, LayoutHelper layoutHelper, int count,
                                  ArrayList<HashMap<String, Object>> data, int type) {
        //宽度占满，高度随意
        this(context, layoutHelper, count,
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT), data, type);
    }
    public int getType() {
        return type;
    }
    public ArrayList<HashMap<String, Object>> getData() {
        return data;
    }

    public RecyclerView.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public int getCount() {
        return data == null ? 0 : data.size();
    }
    public ClassPopularityAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull RecyclerView.LayoutParams layoutParams,
                                  ArrayList<HashMap<String, Object>> data, int type) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.data = data;
        this.type = type;

        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_1:
                view = inflater.inflate(R.layout.rv_class_popularity_lay1, parent, false);
                break;
            case TYPE_2:
                view = inflater.inflate(R.layout.rv_home_lay10, parent, false);
                break;

        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_1:

                break;
            case TYPE_2:
                if (data.get(position).containsKey("popularityGoods") && data.get(position).get("popularityGoods") != null) {
                    final HomePopularityGoodsBean goodsBean = (HomePopularityGoodsBean) data.get(position).get("popularityGoods");
                    if (goodsBean != null) {
                        Glide.with(context).load(goodsBean.getGoodsPic()).apply(new RequestOptions().error(R.drawable.bg_home_lay10_1).placeholder(R.drawable.bg_home_lay10_1).override(300, 300)).into(holder.ivPopGoodsPic);
                        holder.tvPopGoodsName.setText(goodsBean.getGoodsName());
                        holder.tvPopGoodsPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsBean.getGoodsPrice()));
                        if (TextUtils.isEmpty(goodsBean.getStoreType())) {
                            holder.tvPopStoreType.setVisibility(View.GONE);
                        } else {
                            holder.tvPopStoreType.setVisibility(View.VISIBLE);
                            holder.tvPopStoreType.setText(goodsBean.getStoreType());
                        }
                    }
                    holder.llParent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            intent.putExtra("goodsId", goodsBean.getGoodsId());
                            context.startActivity(intent);
                        }
                    });
                }
                VirtualLayoutManager.LayoutParams layoutParamsP = (VirtualLayoutManager.LayoutParams) holder.llParent.getLayoutParams();
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
                holder.llParent.setLayoutParams(layoutParamsP);
                holder.llParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
        }

    }



    @Override
    public int getItemViewType(int position) {
        switch (type) {
            case 1:
                return TYPE_1;
            case 2:
                return TYPE_2;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // type = 2
        LinearLayout llParent;
        ImageView ivPopGoodsPic;
        TextView tvPopGoodsName;
        TextView tvPopGoodsPrice;
        TextView tvPopStoreType;
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType){
                case TYPE_2:
                    llParent = itemView.findViewById(R.id.ll_click_item);
                    ivPopGoodsPic = itemView.findViewById(R.id.iv_goods_pic);
                    tvPopGoodsName = itemView.findViewById(R.id.tv_goods_name);
                    tvPopGoodsPrice = itemView.findViewById(R.id.tv_goods_price);
                    tvPopStoreType = itemView.findViewById(R.id.tv_store_type);
                    break;
            }
        }
    }
}
