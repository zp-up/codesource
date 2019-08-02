package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.BondedGoodsListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GroupGoodsListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LimitedTimeActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OverseasGoodsListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.WishGoodsListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.HomeBannerBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBanner.Data;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBondedGoodsBean.HomeBondedGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeHotSaleGoods.HotGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeLimitGoods.LimitTimeGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeOverseasGoodsList.HomeOverSeasGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homePopularityGoods.HomePopularityGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeWishGoodsBean.HomeWishGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class HomeAdapter extends DelegateAdapter.Adapter<HomeAdapter.ViewHolder> {
    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private final int TYPE_3 = 3;
    private final int TYPE_4 = 4;
    private final int TYPE_5 = 5;
    private final int TYPE_6 = 6;
    private final int TYPE_7 = 7;
    private final int TYPE_8 = 8;
    private final int TYPE_9 = 9;
    private final int TYPE_10 = 10;
    private ArrayList<HashMap<String, Object>> data;
    // 用于存放数据列表

    private Context context;
    private LayoutInflater inflater;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;
    private int type;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public HomeAdapter(Context context, LayoutHelper layoutHelper, int count,
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

    public HomeAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull RecyclerView.LayoutParams layoutParams,
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
                view = inflater.inflate(R.layout.rv_home_lay1, parent, false);
                break;
            case TYPE_2:
                view = inflater.inflate(R.layout.rv_home_lay2, parent, false);
                break;
            case TYPE_3:
                view = inflater.inflate(R.layout.rv_home_lay3, parent, false);
                break;
            case TYPE_4:
                view = inflater.inflate(R.layout.rv_home_lay4, parent, false);
                break;
            case TYPE_5:
                view = inflater.inflate(R.layout.rv_home_lay5, parent, false);
                break;
            case TYPE_6:
                view = inflater.inflate(R.layout.rv_home_lay6, parent, false);
                break;
            case TYPE_7:
                view = inflater.inflate(R.layout.rv_home_lay7, parent, false);
                break;
            case TYPE_8:
                view = inflater.inflate(R.layout.rv_home_lay8, parent, false);
                break;
            case TYPE_9:
                view = inflater.inflate(R.layout.rv_home_lay9, parent, false);
                break;
            case TYPE_10:
                view = inflater.inflate(R.layout.rv_home_lay10, parent, false);
                break;

        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_1:
                if (holder.banner != null) {
                    if (data.get(position).containsKey("bannerData") && data.get(position).get("bannerData") != null) {
                        ArrayList<Data> bannerData = (ArrayList<Data>) data.get(position).get("bannerData");
                        initBanner(holder.banner, bannerData);
                    }
                }
                break;
            case TYPE_2:
                //拼团列表
                holder.rlToGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, GroupGoodsListActivity.class);
                        context.startActivity(intent);
                    }
                });
                //新品列表
                holder.rlToNewly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsListActivity.class);
                        intent.putExtra("type",1);
                        context.startActivity(intent);
                    }
                });
                //折扣列表
                holder.rlToDiscount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsListActivity.class);
                        intent.putExtra("type",2);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_3:// 限时购
                if (data.get(position).containsKey("limitTimeGoods") && data.get(position).get("limitTimeGoods") != null) {
                    final ArrayList<LimitTimeGoodsBean> goodsList = (ArrayList<LimitTimeGoodsBean>) data.get(position).get("limitTimeGoods");
                    if (goodsList != null && goodsList.size() != 0) {

                        // default visibility is visible
                        holder.itemView.setVisibility(View.VISIBLE);
                        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        if (holder.llGoodsContainer != null) {
                            holder.llGoodsContainer.removeAllViews();
                            for (int i = 0; i < goodsList.size(); i++) {
                                final int index = i;
                                View view = inflater.inflate(R.layout.rv_home_item_goods, null);
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                                        intent.putExtra("goodsId", goodsList.get(index).getId());
                                        context.startActivity(intent);
                                    }
                                });
                                TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
                                tvGoodsName.setText(goodsList.get(i).getGoodsName());
                                TextView tvGoodsPrice = view.findViewById(R.id.tv_goods_price);
                                tvGoodsPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsList.get(i).getPrice()));
                                ImageView ivGoodsPic = view.findViewById(R.id.iv_goods_pic);
                                Glide.with(context).load(goodsList.get(i).getGoodsImg()).apply(new RequestOptions().placeholder(R.drawable.icon_no_image).error(R.drawable.icon_no_image).override(300, 300))
                                        .into(ivGoodsPic);
                                holder.llGoodsContainer.addView(view);
                            }
                        }
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String nowDate = sdf.format(new Date());
                            String lastString = nowDate.substring(nowDate.indexOf(" ") + 1, nowDate.length());
                            String[] timeArray = lastString.split(":");
                            int nowHour = Integer.valueOf(timeArray[0]);
                            int nowMinute = Integer.valueOf(timeArray[1]);
                            int nowSecond = Integer.valueOf(timeArray[2]);
                            int endHour = goodsList.get(0).getEndHour();
                            if (!holder.tvCountDown.getRunning()) {
                                holder.tvCountDown.setTime(((60 - nowSecond) * 1000 + (60 - nowMinute - 1) * 60 * 1000 + (endHour - nowHour - 1) * 60 * 60 * 1000), TimerView.TIMETYPE_MS);
                                holder.tvCountDown.start();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 无数据的情况下隐藏
                        holder.itemView.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                    }
                }
                holder.llMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, LimitedTimeActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_5:
                if (data.get(position).containsKey("hotGoods") && data.get(position).get("hotGoods") != null) {
                    final HotGoodsBean bean = (HotGoodsBean) data.get(position).get("hotGoods");
                    holder.tvHotGoodsPrice.setText("￥" + new DecimalFormat("######0.00").format(bean.getGoodsPrice()));
                    holder.tvHotGoodsName.setText(bean.getGoodsName());
                    Glide.with(context).load(bean.getGoodsPic())
                            .apply(new RequestOptions().override(500, 500).placeholder(R.drawable.icon_no_image).error(R.drawable.icon_no_image))
                            .into(holder.ivHotGoodsPic);
                    holder.llClickParent5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,GoodsDetailActivity.class);
                            intent.putExtra("goodsId",bean.getGoodsId());
                            context.startActivity(intent);
                        }
                    });
                }

                VirtualLayoutManager.LayoutParams layoutParams = (VirtualLayoutManager.LayoutParams) holder.llClickParent5.getLayoutParams();
                int leftMargin1 = DpUtils.dpToPx(context, 10);
//                Log.e("TAG", "宽度:" + leftMargin1);
                if (position % 3 == 0) {
                    layoutParams.setMargins(leftMargin1, leftMargin1, leftMargin1 / 2, 0);

                } else if (position % 3 == 1) {
                    layoutParams.setMargins(leftMargin1 / 2, leftMargin1, leftMargin1 / 2, 0);

                } else if (position % 3 == 2) {
                    layoutParams.setMargins(leftMargin1 / 2, leftMargin1, leftMargin1, 0);
                }
                DisplayMetrics dm1 = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm1);
                int width1 = dm1.widthPixels;
                layoutParams.width = (width1 - 4 * leftMargin1) / 3;
                holder.llClickParent5.setLayoutParams(layoutParams);
                break;
            case TYPE_6:
                if (data.get(position).containsKey("overseasGoods") && data.get(position).get("overseasGoods") != null) {
                    final ArrayList<HomeOverSeasGoodsBean> goodsList = (ArrayList<HomeOverSeasGoodsBean>) data.get(position).get("overseasGoods");
                    if (goodsList.size() != 0) {
                        if (holder.llGoodsContainer6 != null) {
                            holder.llGoodsContainer6.removeAllViews();
                            for (int i = 0; i < goodsList.size(); i++) {
                                final int index = i;
                                View view = inflater.inflate(R.layout.rv_home_item_goods, null);
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                                        intent.putExtra("goodsId", goodsList.get(index).getGoodsId());
                                        context.startActivity(intent);
                                    }
                                });
                                TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
                                tvGoodsName.setText(goodsList.get(i).getGoodsName());
                                TextView tvGoodsPrice = view.findViewById(R.id.tv_goods_price);
                                tvGoodsPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsList.get(i).getGoodsPrice()));
                                ImageView ivGoodsPic = view.findViewById(R.id.iv_goods_pic);
                                Glide.with(context).load(goodsList.get(i).getGoodsPic()).apply(new RequestOptions().placeholder(R.drawable.icon_no_image).error(R.drawable.icon_no_image).override(200, 200))
                                        .into(ivGoodsPic);
                                holder.llGoodsContainer6.addView(view);
                            }
                        } else {
                            Log.e("TAG", "控件为空");
                        }
                    } else {
                        Log.e("TAG", "数据为空");
                    }
                } else {

                }

                if (holder.rlToOverseasGoodsList != null) {
                    holder.rlToOverseasGoodsList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OverseasGoodsListActivity.class);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case TYPE_7:
                if (data.get(position).containsKey("bondedGoods") && data.get(position).get("bondedGoods") != null) {
                    final ArrayList<HomeBondedGoodsBean> goodsList = (ArrayList<HomeBondedGoodsBean>) data.get(position).get("bondedGoods");
                    if (goodsList.size() != 0) {
                        if (holder.llGoodsContainer7 != null) {
                            holder.llGoodsContainer7.removeAllViews();
                            for (int i = 0; i < goodsList.size(); i++) {
                                final int index = i;
                                View view = inflater.inflate(R.layout.rv_home_item_goods, null);
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                                        intent.putExtra("goodsId", goodsList.get(index).getGoodsId());
                                        context.startActivity(intent);
                                    }
                                });
                                TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
                                tvGoodsName.setText(goodsList.get(i).getGoodsName());
                                TextView tvGoodsPrice = view.findViewById(R.id.tv_goods_price);
                                tvGoodsPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsList.get(i).getGoodsPrice()));
                                ImageView ivGoodsPic = view.findViewById(R.id.iv_goods_pic);
                                Glide.with(context).load(goodsList.get(i).getGoodsPic()).apply(new RequestOptions().placeholder(R.drawable.icon_no_image).error(R.drawable.icon_no_image).override(200, 200))
                                        .into(ivGoodsPic);
                                holder.llGoodsContainer7.addView(view);
                            }
                        }
                    }
                }
                if (holder.rlToBondedGoods != null) {
                    holder.rlToBondedGoods.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, BondedGoodsListActivity.class);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case TYPE_8:
                if (data.get(position).containsKey("wishGoods") && data.get(position).get("wishGoods") != null) {
                    final ArrayList<HomeWishGoodsBean> goodsList = (ArrayList<HomeWishGoodsBean>) data.get(position).get("wishGoods");
                    if (goodsList.size() != 0) {
                        if (holder.llGoodsContainer8 != null) {
                            holder.llGoodsContainer8.removeAllViews();
                            for (int i = 0; i < goodsList.size(); i++) {
                                View view = inflater.inflate(R.layout.rv_home_item_goods, null);
                                final int index = i;
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.e("goods","goodsID:"+goodsList.get(index).getGoodsId());
                                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                                        intent.putExtra("goodsId", goodsList.get(index).getGoodsId());
                                        context.startActivity(intent);
                                    }
                                });
                                TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
                                tvGoodsName.setText(goodsList.get(i).getGoodsName());
                                TextView tvGoodsPrice = view.findViewById(R.id.tv_goods_price);
                                tvGoodsPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsList.get(i).getGoodsPrice()));
                                ImageView ivGoodsPic = view.findViewById(R.id.iv_goods_pic);
                                Glide.with(context).load(goodsList.get(i).getGoodsPic()).apply(new RequestOptions().placeholder(R.drawable.icon_no_image).error(R.drawable.icon_no_image).override(200, 200))
                                        .into(ivGoodsPic);
                                holder.llGoodsContainer8.addView(view);
                            }
                        }
                    }
                }
                if (holder.llToWishMore != null) {
                    holder.llToWishMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, WishGoodsListActivity.class);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case TYPE_10:
                if (data.get(position).containsKey("popularityGoods") && data.get(position).get("popularityGoods") != null) {
                    final HomePopularityGoodsBean goodsBean = (HomePopularityGoodsBean) data.get(position).get("popularityGoods");
                    if (goodsBean != null) {
                        Glide.with(context).load(goodsBean.getGoodsPic()).apply(new RequestOptions().error(R.drawable.icon_no_image).placeholder(R.drawable.icon_no_image).override(300, 300)).into(holder.ivPopGoodsPic);
                        holder.tvPopGoodsName.setText(goodsBean.getGoodsName());
                        holder.tvPopGoodsPrice.setText("￥" + new DecimalFormat("######0.00").format(goodsBean.getGoodsPrice()));
                        if (TextUtils.isEmpty(goodsBean.getStoreType()) || "国内仓库".equals(goodsBean.getStoreType())) {
                            holder.tvPopStoreType.setVisibility(View.GONE);
                        } else {
                            holder.tvPopStoreType.setVisibility(View.VISIBLE);
                            holder.tvPopStoreType.setText(goodsBean.getStoreType());
                        }
                    }
                    holder.llClickParent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            intent.putExtra("goodsId", goodsBean.getGoodsId());
                            context.startActivity(intent);
                        }
                    });
                }
                VirtualLayoutManager.LayoutParams layoutParamsP = (VirtualLayoutManager.LayoutParams) holder.llClickParent.getLayoutParams();
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
            case 3:
                return TYPE_3;
            case 4:
                return TYPE_4;
            case 5:
                return TYPE_5;
            case 6:
                return TYPE_6;
            case 7:
                return TYPE_7;
            case 8:
                return TYPE_8;
            case 9:
                return TYPE_9;
            case 10:
                return TYPE_10;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //type = 1
        Banner banner;
        //type = 2
        RelativeLayout rlToGroup,rlToNewly,rlToDiscount;
        //type = 3
        TimerView tvCountDown;
        LinearLayout llGoodsContainer;
        LinearLayout llMore;
        //type = 5
        LinearLayout llClickParent5;
        ImageView ivHotGoodsPic;
        TextView tvHotGoodsName;
        TextView tvHotGoodsPrice;
        //type = 6
        LinearLayout llGoodsContainer6;
        RelativeLayout rlToOverseasGoodsList;
        //type = 7
        LinearLayout llGoodsContainer7;
        RelativeLayout rlToBondedGoods;
        //type = 8
        LinearLayout llGoodsContainer8;
        LinearLayout llToWishMore;

        //type = 10
        LinearLayout llClickParent;
        ImageView ivPopGoodsPic;
        TextView tvPopGoodsName;
        TextView tvPopGoodsPrice;
        TextView tvPopStoreType;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case TYPE_1:
                    banner = itemView.findViewById(R.id.home_banner);
                    break;
                case TYPE_2:
                    rlToGroup = itemView.findViewById(R.id.rl_to_group);
                    rlToNewly =itemView.findViewById(R.id.rl_to_newly_goods_list);
                    rlToDiscount = itemView.findViewById(R.id.rl_to_discount);
                break;
                case TYPE_3:
                    tvCountDown = itemView.findViewById(R.id.tv_count_down);
                    llGoodsContainer = itemView.findViewById(R.id.ll_goods_container);
                    llMore = itemView.findViewById(R.id.ll_limit_time_more);
                    break;
                case TYPE_5:
                    llClickParent5 = itemView.findViewById(R.id.ll_parent);
                    ivHotGoodsPic = itemView.findViewById(R.id.iv_goods_pic);
                    tvHotGoodsName = itemView.findViewById(R.id.tv_goods_name);
                    tvHotGoodsPrice = itemView.findViewById(R.id.tv_goods_price);
                    break;
                case TYPE_6:
                    llGoodsContainer6 = itemView.findViewById(R.id.ll_goods_container);
                    rlToOverseasGoodsList = itemView.findViewById(R.id.rl_to_overseas_goods_list);
                    break;
                case TYPE_7:
                    llGoodsContainer7 = itemView.findViewById(R.id.ll_goods_container);
                    rlToBondedGoods = itemView.findViewById(R.id.rl_to_bonded_goods);
                    break;
                case TYPE_8:
                    llGoodsContainer8 = itemView.findViewById(R.id.ll_goods_container);
                    llToWishMore = itemView.findViewById(R.id.ll_to_wish_more);
                    break;
                case TYPE_10:
                    llClickParent = itemView.findViewById(R.id.ll_click_item);
                    ivPopGoodsPic = itemView.findViewById(R.id.iv_goods_pic);
                    tvPopGoodsName = itemView.findViewById(R.id.tv_goods_name);
                    tvPopGoodsPrice = itemView.findViewById(R.id.tv_goods_price);
                    tvPopStoreType = itemView.findViewById(R.id.tv_store_type);
                    break;
            }
        }
    }

    private void initBanner(Banner banner, ArrayList<Data> bannerBeanList) {
        //显示圆形指示器
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //指示器居中
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(bannerBeanList);
        banner.setImageLoader(new MyImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.start();
    }

    class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (path instanceof Data) {
                Data b = (Data) path;
                RequestOptions options = new RequestOptions();
                options.placeholder(R.drawable.pic_banner_place_holder).error(R.drawable.pic_banner_error);
                Glide.with(context).load(b.getImg() != null && b.getImg().size() != 0 ? b.getImg().get(0) : "").apply(options).into(imageView);
            }
        }

    }
}
