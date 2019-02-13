package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ApplyAfterSaleActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LogisticsActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.popupwindow.AfterSaleSelectPop;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class AfterSaleAdapter extends RecyclerView.Adapter<AfterSaleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderRootBean> data;
    private LayoutInflater inflater;


    public AfterSaleAdapter(Context context, ArrayList<OrderRootBean> data) {
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
                view = inflater.inflate(R.layout.rv_order_after_sale_doing_layout, parent, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.rv_order_after_sale_success_layout, parent, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.rv_order_after_sale_failed_layout, parent, false);
                break;
            case 4:
                view = inflater.inflate(R.layout.rv_order_after_sale_complete_layout, parent, false);
                break;
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        OrderRootBean rootBean = data.get(position);
        switch (viewType) {
            case 1:
                if (rootBean.getCount() == 1) {
                    holder.hsvParent1.setVisibility(View.GONE);
                    holder.rlOneGoodsParent1.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    holder.hsvParent1.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent1.setVisibility(View.GONE);
                    holder.llGoodsContainer1.removeAllViews();
                    for (int i = 0; i < rootBean.getCount(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer1.addView(view);
                    }
                }
                if (rootBean.getType() == 2){
                    holder.tvTAG1.setVisibility(View.VISIBLE);
                }else {
                    holder.tvTAG1.setVisibility(View.GONE);
                }
                holder.tvAfterSaleState1.setText("售后\n"+
                        "处理中");
                break;
            case 2:
                if (rootBean.getCount() == 1) {
                    holder.hsvParent2.setVisibility(View.GONE);
                    holder.rlOneGoodsParent2.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    holder.hsvParent2.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent2.setVisibility(View.GONE);
                    holder.llGoodsContainer2.removeAllViews();
                    for (int i = 0; i < rootBean.getCount(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer2.addView(view);
                    }
                }
                if (rootBean.getType() == 2){
                    holder.tvTAG2.setVisibility(View.VISIBLE);
                }else {
                    holder.tvTAG2.setVisibility(View.GONE);
                }
                holder.tvAfterSaleState2.setText("售后\n" +
                        "申请成功");
                break;
            case 3:
                if (rootBean.getCount() == 1) {
                    holder.hsvParent3.setVisibility(View.GONE);
                    holder.rlOneGoodsParent3.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    holder.hsvParent3.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent3.setVisibility(View.GONE);
                    holder.llGoodsContainer3.removeAllViews();
                    for (int i = 0; i < rootBean.getCount(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer3.addView(view);
                    }
                }
                if (rootBean.getType() == 2){
                    holder.tvTAG3.setVisibility(View.VISIBLE);
                }else {
                    holder.tvTAG3.setVisibility(View.GONE);
                }
                holder.tvAfterSaleState3.setText("售后\n" +
                        "被驳回");
                break;
            case 4:
                if (rootBean.getCount() == 1) {
                    holder.hsvParent4.setVisibility(View.GONE);
                    holder.rlOneGoodsParent4.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    holder.hsvParent4.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent4.setVisibility(View.GONE);
                    holder.llGoodsContainer4.removeAllViews();
                    for (int i = 0; i < rootBean.getCount(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer4.addView(view);
                    }
                }
                if (rootBean.getType() == 2){
                    holder.tvTAG4.setVisibility(View.VISIBLE);
                }else {
                    holder.tvTAG4.setVisibility(View.GONE);
                }
                holder.tvApplyAfterSale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AfterSaleSelectPop pop = new AfterSaleSelectPop(context);
                        pop.show(((OrderActivity) context).llParent, new AfterSaleSelectPop.ItemClick() {
                            @Override
                            public void onClick(int type, double rentPrice, double buyPrice) {
                                switch (type){
                                    case 1:
                                        Intent intent = new Intent(context,ApplyAfterSaleActivity.class);
                                        context.startActivity(intent);
                                        break;
                                    case 2:

                                        break;
                                    case 3:

                                        break;
                                }
                            }
                        });
                    }
                });
                holder.tvAfterSaleState4.setText("售后\n" +
                        "已完成");
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = data.get(position).getState();
        return type;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //type = 1
        HorizontalScrollView hsvParent1;
        LinearLayout llGoodsContainer1;
        RelativeLayout rlOneGoodsParent1;
        TextView tvTAG1;
        TextView tvAfterSaleState1;

        //type = 2
        HorizontalScrollView hsvParent2;
        LinearLayout llGoodsContainer2;
        RelativeLayout rlOneGoodsParent2;
        TextView tvTAG2;
        TextView tvAfterSaleState2;

        //type = 3
        HorizontalScrollView hsvParent3;
        LinearLayout llGoodsContainer3;
        RelativeLayout rlOneGoodsParent3;
        TextView tvTAG3;
        TextView tvAfterSaleState3;
        //type = 4
        HorizontalScrollView hsvParent4;
        LinearLayout llGoodsContainer4;
        RelativeLayout rlOneGoodsParent4;
        TextView tvTAG4;
        TextView tvApplyAfterSale;
        TextView tvAfterSaleState4;

        public ViewHolder(View item, int viewType) {
            super(item);
            switch (viewType) {
                case 1:
                    hsvParent1 = item.findViewById(R.id.hsView);
                    llGoodsContainer1 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent1 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG1 = item.findViewById(R.id.tv_tag);
                    tvAfterSaleState1 = item.findViewById(R.id.tv_order_state);
                    break;
                case 2:
                    hsvParent2 = item.findViewById(R.id.hsView);
                    llGoodsContainer2 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent2 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG2 = item.findViewById(R.id.tv_tag);
                    tvAfterSaleState2 = item.findViewById(R.id.tv_order_state);
                    break;
                case 3:
                    hsvParent3 = item.findViewById(R.id.hsView);
                    llGoodsContainer3 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent3 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG3 = item.findViewById(R.id.tv_tag);
                    tvAfterSaleState3 = item.findViewById(R.id.tv_order_state);

                    break;
                case 4:
                    hsvParent4 = item.findViewById(R.id.hsView);
                    llGoodsContainer4 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent4 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG4 = item.findViewById(R.id.tv_tag);
                    tvApplyAfterSale = item.findViewById(R.id.tv_apply_after_sale);
                    tvAfterSaleState4 = item.findViewById(R.id.tv_order_state);
                    break;
            }
        }
    }
}
