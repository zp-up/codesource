package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LimitedTimeActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.GroupProductEntity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.HomeBannerBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView.TIMETYPE_S;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class GroupGoodsListAdapter extends DelegateAdapter.Adapter<GroupGoodsListAdapter.ViewHolder> {
    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private ArrayList<HashMap<String, Object>> data;
    // 用于存放数据列表

    private Context context;
    private LayoutInflater inflater;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;
    private int type;
    private String rmb = Html.fromHtml("&yen").toString();

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public GroupGoodsListAdapter(Context context, LayoutHelper layoutHelper, int count,
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

    public GroupGoodsListAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull RecyclerView.LayoutParams layoutParams,
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
                view = inflater.inflate(R.layout.rv_group_goods_header_layout, parent, false);
                break;
            case TYPE_2:
                view = inflater.inflate(R.layout.rv_group_goods_list_layout, parent, false);
                break;

        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_1:

                break;
            case TYPE_2:
                bind_2(holder, position);

                //holder.tvGroupTime.setTime(6 * 60 * 60, TIMETYPE_S);
                //holder.tvGroupTime.start();
                break;
        }

    }

    private void bind_2(ViewHolder holder, int position) {
        HashMap<String, Object> map = data.get(position);
        if (map != null) {
            GroupProductEntity productEntity = (GroupProductEntity) map.get("product");
            if (productEntity != null) {
                //拼团人数
                holder.tvGroupNum.setText(String.valueOf(productEntity.getGroup_num_p()));
                //商品图片
                if(productEntity.getGoods_goods().getImg()!=null && productEntity.getGoods_goods().getImg().size()>0){
                    Glide.with(context).load(productEntity.getGoods_goods().getImg().get(0)).
                            apply(new RequestOptions().override(300, 300).
                                    placeholder(R.mipmap.ic_launcher).
                                    error(R.mipmap.ic_launcher)).
                            into(holder.ivProduct);
                }

                //商品名字
                holder.tvProductName.setText(productEntity.getGoods_goods().getName());

                //商品描述
                holder.tvDescribe.setText(productEntity.getGoods_goods().getDescribe());
                //商品单价
                holder.tvSinglePrice.setText(rmb+productEntity.getPrice_m());
                //商品团购价
                holder.tvGroupPrice.setText(rmb+productEntity.getGroup_price());
                //团购结束时间
                holder.tvGroupEndDate.setText("截止时间："+productEntity.getPron_end_time());

                //计算倒计时
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date date_now,date_end;
                try {
                     date_now = new Date(System.currentTimeMillis());
                     date_end = sdf.parse(productEntity.getPron_end_time());
                     long time =(date_end.getTime()-date_now.getTime());
                     if(time>0){
                         holder.tvGroupTime.setVisibility(View.VISIBLE);
                         if (holder.tvGroupTime.getRunning()) {
                             holder.tvGroupTime.stop();
                         }
                         holder.tvGroupTime.setTime(time);
                         holder.tvGroupTime.start();
                     }else {
                         holder.tvGroupTime.setVisibility(View.GONE);
                     }
                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }
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
                return 99;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TimerView tvGroupTime;

        TextView tvGroupNum;//参团人数
        ImageView ivProduct;//商品主图
        TextView tvProductName;//商品名字
        TextView tvDescribe;//商品描述
        TextView tvSinglePrice;//商品单独购买价格
        TextView tvGroupPrice;//团购价格
        TextView tvGroupEndDate;//团购结束时间

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case TYPE_1:

                    break;
                case TYPE_2:
                    tvGroupNum = itemView.findViewById(R.id.tv_group_num);
                    ivProduct = itemView.findViewById(R.id.iv_goods_pic);
                    tvProductName = itemView.findViewById(R.id.tv_product_name);
                    tvDescribe = itemView.findViewById(R.id.tv_describe);
                    tvSinglePrice = itemView.findViewById(R.id.tv_single_price);
                    tvGroupPrice =itemView.findViewById(R.id.tv_group_price);
                    tvGroupEndDate =itemView.findViewById(R.id.tv_group_end_date);


                    tvGroupTime = itemView.findViewById(R.id.tv_group_time);
                    break;
            }
        }
    }


}
