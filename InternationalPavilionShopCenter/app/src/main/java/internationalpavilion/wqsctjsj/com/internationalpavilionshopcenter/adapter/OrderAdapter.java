package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ApplyAfterSaleActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.EvaluateActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LogisticsActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.PayWayActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.popupwindow.AfterSaleSelectPop;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.ERROR_TYPE;
import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.WARNING_TYPE;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderRootBean> data;
    private LayoutInflater inflater;


    public OrderAdapter(Context context, ArrayList<OrderRootBean> data) {
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
                view = inflater.inflate(R.layout.rv_order_wait_pay_layout, parent, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.rv_order_wait_delivery_layout, parent, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.rv_order_wait_received_layout, parent, false);
                break;
            case 4:
                view = inflater.inflate(R.layout.rv_order_wait_evaluate_layout, parent, false);
                break;
            case 5:
                view = inflater.inflate(R.layout.rv_order_complete_layout, parent, false);
                break;
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        final OrderRootBean rootBean = data.get(position);
        switch (viewType) {
            case 1:
                holder.tvShouldPay.setText("应付:"+rootBean.getPay_total());
                holder.tvToPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PayWayActivity.class);
                        intent.putExtra("oderId", rootBean.getId() + "");
                        Log.e("TAG","---订单:"+rootBean.getId());
                        context.startActivity(intent);
                    }
                });
                holder.tvGoodsCount1.setText("共" + (data.get(position).getGoodsBeans() != null ? data.get(position).getGoodsBeans().size() : 0) + "种商品");
                holder.tvStoreType1.setText(data.get(position).getStoreType());
                holder.tvStatus.setText(data.get(position).getStatus());
                holder.tvOrderCreateTime1.setText("订单时间:" + data.get(position).getCreate_time());
                if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() == 1) {
                    holder.tvGoodsName1.setText(data.get(position).getGoodsBeans().get(0).getGoodsName());
                    holder.tvDescription1.setText(data.get(position).getGoodsBeans().get(0).getGoodsDescription());
                    Glide.with(context).load(data.get(position).getGoodsBeans().get(0).getImageUrl())
                            .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(holder.ivGoodsPic1);
                    holder.hsvParent1.setVisibility(View.GONE);
                    holder.rlOneGoodsParent1.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", data.get(position).getId());
                            context.startActivity(intent);
                        }
                    });
                } else if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() > 1) {
                    holder.hsvParent1.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent1.setVisibility(View.GONE);
                    holder.llGoodsContainer1.removeAllViews();
                    for (int i = 0; i < data.get(position).getGoodsBeans().size(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        ImageView ivGoodsPic = view.findViewById(R.id.iv_evaluate_pic);
                        Glide.with(context).load(data.get(position).getGoodsBeans().get(i).getImageUrl())
                                .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                intent.putExtra("orderId", data.get(position).getId());
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer1.addView(view);
                    }
                }
                if (data.get(position).getStatus().contains("拼团")) {
                    holder.tvTAG1.setVisibility(View.VISIBLE);
                } else {
                    holder.tvTAG1.setVisibility(View.GONE);
                }
                holder.tvCancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                                ((OrderActivity) context).cancelOrder(data.get(position).getId());
                            }
                        });
                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                            }
                        });
                        dialog.showCancelButton(true);
                        dialog.setCancelText("取消");
                        dialog.setConfirmText("确定");
                        dialog.setTitleText("温馨提示");
                        dialog.setContentText("是否取消订单?");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });
                break;
            case 2:
                holder.tvGoodsCount2.setText("共" + (data.get(position).getGoodsBeans() != null ? data.get(position).getGoodsBeans().size() : 0) + "件商品");
                holder.tvStoreType2.setText(data.get(position).getStoreType());
                holder.tvOrderCreateTime2.setText("订单时间:" + data.get(position).getCreate_time());
                holder.tvStatus.setText(data.get(position).getStatus());
                if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() == 1) {
                    holder.tvGoodsName2.setText(data.get(position).getGoodsBeans().get(0).getGoodsName());
                    holder.tvDescription2.setText(data.get(position).getGoodsBeans().get(0).getGoodsDescription());
                    Glide.with(context).load(data.get(position).getGoodsBeans().get(0).getImageUrl())
                            .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(holder.ivGoodsPic2);
                    holder.hsvParent2.setVisibility(View.GONE);
                    holder.rlOneGoodsParent2.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", data.get(position).getId());
                            context.startActivity(intent);
                        }
                    });
                } else if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() > 1) {
                    holder.hsvParent2.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent2.setVisibility(View.GONE);
                    holder.llGoodsContainer2.removeAllViews();
                    for (int i = 0; i < data.get(position).getGoodsBeans().size(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        ImageView ivGoodsPic = view.findViewById(R.id.iv_evaluate_pic);
                        Glide.with(context).load(data.get(position).getGoodsBeans().get(i).getImageUrl())
                                .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                intent.putExtra("orderId", data.get(position).getId());
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer2.addView(view);
                    }
                }
                if (data.get(position).getStatus().contains("拼团")) {
                    holder.tvTAG2.setVisibility(View.VISIBLE);
                } else {
                    holder.tvTAG2.setVisibility(View.GONE);
                }
                holder.tvBackMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((OrderActivity) context).showRequestAfterSale(data.get(position).getId());
//                        AfterSaleSelectPop pop = new AfterSaleSelectPop(context);
//                        pop.show(((OrderActivity) context).llParent, new AfterSaleSelectPop.ItemClick() {
//                            @Override
//                            public void onClick(int type, double rentPrice, double buyPrice) {
//                                switch (type) {
//                                    case 1:
//                                        final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
//                                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                dialog.dismissWithAnimation();
//                                                ((OrderActivity) context).backMoneyOnly(data.get(position).getId());
//                                            }
//                                        });
//                                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                dialog.dismissWithAnimation();
//                                            }
//                                        });
//                                        dialog.showCancelButton(true);
//                                        dialog.setCancelText("取消");
//                                        dialog.setConfirmText("确定");
//                                        dialog.setTitleText("注意");
//                                        dialog.setContentText("当前订单状态只能申请退款，是否要进行退款操作?");
//                                        dialog.setCancelable(false);
//                                        dialog.show();
//                                        break;
//                                    case 2:
//                                        final SweetAlertDialog dialog1 = new SweetAlertDialog(context, WARNING_TYPE);
//                                        dialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                dialog1.dismissWithAnimation();
//                                                ((OrderActivity) context).backMoneyOnly(data.get(position).getId());
//                                            }
//                                        });
//                                        dialog1.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                dialog1.dismissWithAnimation();
//                                            }
//                                        });
//                                        dialog1.showCancelButton(true);
//                                        dialog1.setCancelText("取消");
//                                        dialog1.setConfirmText("确定");
//                                        dialog1.setTitleText("注意");
//                                        dialog1.setContentText("当前订单状态只能申请退款，是否要进行退款操作?");
//                                        dialog1.setCancelable(false);
//                                        dialog1.show();
//                                        break;
//                                    case 3:
//                                        final SweetAlertDialog dialog2 = new SweetAlertDialog(context, WARNING_TYPE);
//                                        dialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                dialog2.dismissWithAnimation();
//                                                ((OrderActivity) context).backMoneyOnly(data.get(position).getId());
//                                            }
//                                        });
//                                        dialog2.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                dialog2.dismissWithAnimation();
//                                            }
//                                        });
//                                        dialog2.showCancelButton(true);
//                                        dialog2.setCancelText("取消");
//                                        dialog2.setConfirmText("确定");
//                                        dialog2.setTitleText("注意");
//                                        dialog2.setContentText("是否要进行退款操作?");
//                                        dialog2.setCancelable(false);
//                                        dialog2.show();
//                                        break;
//                                }
//                            }
//                        });
                    }
                });
                holder.tvBuyAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                                ((OrderActivity) context).buyAgain(data.get(position).getId());
                            }
                        });
                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                            }
                        });
                        dialog.showCancelButton(true);
                        dialog.setCancelText("取消");
                        dialog.setConfirmText("确定");
                        dialog.setTitleText("温馨提示");
                        dialog.setContentText("是否要进行再次购买?");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });
                break;
            case 3:
                holder.tvGoodsCount3.setText("共" + (data.get(position).getGoodsBeans() != null ? data.get(position).getGoodsBeans().size() : 0) + "件商品");
                holder.tvStoreType3.setText(data.get(position).getStoreType());
                holder.tvStatus.setText(data.get(position).getStatus());
                holder.tvOrderCreateTime3.setText("订单时间:" + data.get(position).getCreate_time());
                if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() == 1) {
                    holder.tvGoodsName3.setText(data.get(position).getGoodsBeans().get(0).getGoodsName());
                    holder.tvDescription3.setText(data.get(position).getGoodsBeans().get(0).getGoodsDescription());
                    Glide.with(context).load(data.get(position).getGoodsBeans().get(0).getImageUrl())
                            .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(holder.ivGoodsPic3);
                    holder.hsvParent3.setVisibility(View.GONE);
                    holder.rlOneGoodsParent3.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", data.get(position).getId());
                            context.startActivity(intent);
                        }
                    });
                } else if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() > 1) {
                    holder.hsvParent3.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent3.setVisibility(View.GONE);
                    holder.llGoodsContainer3.removeAllViews();
                    for (int i = 0; i < data.get(position).getGoodsBeans().size(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        ImageView ivGoodsPic = view.findViewById(R.id.iv_evaluate_pic);
                        Glide.with(context).load(data.get(position).getGoodsBeans().get(i).getImageUrl())
                                .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                intent.putExtra("orderId", data.get(position).getId());
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer3.addView(view);
                    }
                }
                holder.tvToSeeLogistics.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, LogisticsActivity.class);
                        intent.putExtra("orderId",data.get(position).getId());
                        context.startActivity(intent);
                    }
                });
                if (data.get(position).getStatus().contains("拼团")) {
                    holder.tvTAG3.setVisibility(View.VISIBLE);
                } else {
                    holder.tvTAG3.setVisibility(View.GONE);
                }
                holder.tvConfirmReveived.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                                ((OrderActivity) context).confirmReceived(data.get(position).getId());
                            }
                        });
                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                            }
                        });
                        dialog.showCancelButton(true);
                        dialog.setCancelText("取消");
                        dialog.setConfirmText("确定");
                        dialog.setTitleText("温馨提示");
                        dialog.setContentText("是否确认收货?");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });
                break;
            case 4:
                holder.tvToEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EvaluateActivity.class);
                        intent.putExtra("orderId",data.get(position).getId());
                        context.startActivity(intent);
                    }
                });
                holder.tvGoodsCount4.setText("共" + (data.get(position).getGoodsBeans() != null ? data.get(position).getGoodsBeans().size() : 0) + "件商品");
                holder.tvStoreType4.setText(data.get(position).getStoreType());
                holder.tvStatus.setText(data.get(position).getStatus());
                holder.tvOrderCreateTime4.setText("订单时间:" + data.get(position).getCreate_time());
                if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() == 1) {
                    holder.tvGoodsName4.setText(data.get(position).getGoodsBeans().get(0).getGoodsName());
                    holder.tvDescription4.setText(data.get(position).getGoodsBeans().get(0).getGoodsDescription());
                    Glide.with(context).load(data.get(position).getGoodsBeans().get(0).getImageUrl())
                            .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(holder.ivGoodsPic4);
                    holder.hsvParent4.setVisibility(View.GONE);
                    holder.rlOneGoodsParent4.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", data.get(position).getId());
                            context.startActivity(intent);
                        }
                    });
                } else if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() > 1) {
                    holder.hsvParent4.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent4.setVisibility(View.GONE);
                    holder.llGoodsContainer4.removeAllViews();
                    for (int i = 0; i < data.get(position).getGoodsBeans().size(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        ImageView ivGoodsPic = view.findViewById(R.id.iv_evaluate_pic);
                        Glide.with(context).load(data.get(position).getGoodsBeans().get(i).getImageUrl())
                                .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                intent.putExtra("orderId", data.get(position).getId());
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer4.addView(view);
                    }
                }
                if (data.get(position).getStatus().contains("拼团")) {
                    holder.tvTAG4.setVisibility(View.VISIBLE);
                } else {
                    holder.tvTAG4.setVisibility(View.GONE);
                }
                holder.tvApplyAfterSale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AfterSaleSelectPop pop = new AfterSaleSelectPop(context);
                        pop.show(((OrderActivity) context).llParent, new AfterSaleSelectPop.ItemClick() {
                            @Override
                            public void onClick(int type, double rentPrice, double buyPrice) {
                                switch (type) {
                                    case 1:
                                        Intent intent = new Intent(context, ApplyAfterSaleActivity.class);
                                        intent.putExtra("orderId", data.get(position).getId());
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
                break;
            case 5:
                holder.tvCompleteBuyAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                                ((OrderActivity) context).buyAgain(data.get(position).getId());
                            }
                        });
                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismissWithAnimation();
                            }
                        });
                        dialog.showCancelButton(true);
                        dialog.setCancelText("取消");
                        dialog.setConfirmText("确定");
                        dialog.setTitleText("温馨提示");
                        dialog.setContentText("是否要进行再次购买?");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });
                holder.tvGoodsCount5.setText("共" + (data.get(position).getGoodsBeans() != null ? data.get(position).getGoodsBeans().size() : 0) + "件商品");
                holder.tvStoreType5.setText(data.get(position).getStoreType());
//                holder.tvOrderCreateTime5.setText("订单时间:" + data.get(position).getCreate_time());
                holder.tvOrderCreateTime5.setText("订单变化:" + data.get(position).getOrderNumber());
                holder.tvStatus.setText(data.get(position).getStatus());
                if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() == 1) {
                    holder.tvGoodsName5.setText(data.get(position).getGoodsBeans().get(0).getGoodsName());
                    holder.tvDescription5.setText(data.get(position).getGoodsBeans().get(0).getGoodsDescription());
                    Glide.with(context).load(data.get(position).getGoodsBeans().get(0).getImageUrl())
                            .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(holder.ivGoodsPic5);
                    holder.hsvParent5.setVisibility(View.GONE);
                    holder.rlOneGoodsParent5.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", data.get(position).getId());
                            context.startActivity(intent);
                        }
                    });
                } else if (data.get(position).getGoodsBeans() != null && data.get(position).getGoodsBeans().size() > 1) {
                    holder.hsvParent5.setVisibility(View.VISIBLE);
                    holder.rlOneGoodsParent5.setVisibility(View.GONE);
                    holder.llGoodsContainer5.removeAllViews();
                    for (int i = 0; i < data.get(position).getGoodsBeans().size(); i++) {
                        View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                        ImageView ivGoodsPic = view.findViewById(R.id.iv_evaluate_pic);
                        Glide.with(context).load(data.get(position).getGoodsBeans().get(i).getImageUrl())
                                .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, OrderDetailActivity.class);
                                intent.putExtra("orderId", data.get(position).getId());
                                context.startActivity(intent);
                            }
                        });
                        holder.llGoodsContainer5.addView(view);
                    }
                }
                if (data.get(position).getStatus() != null && data.get(position).getStatus().contains("拼团")) {
                    holder.tvTAG5.setVisibility(View.VISIBLE);
                    holder.tvCompleteBuyAgain.setVisibility(View.GONE);
                } else {
                    holder.tvTAG5.setVisibility(View.GONE);
                    holder.tvCompleteBuyAgain.setVisibility(View.VISIBLE);
                }
                if (TextUtils.equals(data.get(position).getStatus(),"已关闭")){
                    holder.tvApplyAfterSaleComplete.setVisibility(View.GONE);
                }else {
                    holder.tvApplyAfterSaleComplete.setVisibility(View.VISIBLE);

                    String afterSaleState = data.get(position).getAfterSaleState();
                    if (TextUtils.equals(afterSaleState, "无售后")) {
                        holder.tvApplyAfterSaleComplete.setText("退货");
                        holder.tvApplyAfterSaleComplete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((OrderActivity) context).showRequestAfterSale(data.get(position).getId());
                            }
                        });
                    } else if (TextUtils.equals(afterSaleState, "售后中")) {
                        holder.tvApplyAfterSaleComplete.setText("退货");
                        holder.tvApplyAfterSaleComplete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((OrderActivity) context).showRefunding();
                            }
                        });
                    } else if (TextUtils.equals(afterSaleState, "退货中")) {
                        holder.tvApplyAfterSaleComplete.setText("退货");
                        if ("无退货".equals(rootBean.getRefund_goods())) {
                            holder.tvApplyAfterSaleComplete.setText("退货");
                            holder.tvApplyAfterSaleComplete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((OrderActivity) context).showRefundGoodsAddress(data.get(position).getId());
                                }
                            });
                        } else {
                            holder.tvApplyAfterSaleComplete.setText("售后中");
                            holder.tvApplyAfterSaleComplete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((OrderActivity) context).showReceiveGoods();
                                }
                            });
                        }
                    } else if (TextUtils.equals(afterSaleState, "退款中")) {
                        holder.tvApplyAfterSaleComplete.setText("售后中");
                        holder.tvApplyAfterSaleComplete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((OrderActivity) context).showReceiveGoods();
                            }
                        });
                    } else if (TextUtils.equals(afterSaleState, "已售后")) {
                        holder.tvApplyAfterSaleComplete.setText("已售后");
                        holder.tvApplyAfterSaleComplete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((OrderActivity) context).showRefundDone();
                            }
                        });
                    }
                }
/*                holder.tvApplyAfterSaleComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AfterSaleSelectPop pop = new AfterSaleSelectPop(context);
                        pop.show(((OrderActivity) context).llParent, new AfterSaleSelectPop.ItemClick() {
                            @Override
                            public void onClick(int type, double rentPrice, double buyPrice) {
                                switch (type) {
                                    case 1:
                                        final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
                                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialog.dismissWithAnimation();
                                                Intent intent = new Intent(context,ApplyAfterSaleActivity.class);
                                                intent.putExtra("orderId", data.get(position).getId());
                                                context.startActivity(intent);

                                            }
                                        });
                                        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialog.dismissWithAnimation();
                                            }
                                        });
                                        dialog.showCancelButton(true);
                                        dialog.setCancelText("取消");
                                        dialog.setConfirmText("确定");
                                        dialog.setTitleText("注意");
                                        dialog.setContentText("是否要进行退款退货操作?");
                                        dialog.setCancelable(false);
                                        dialog.show();
                                        break;
                                    case 2:
                                        final SweetAlertDialog dialog1 = new SweetAlertDialog(context, WARNING_TYPE);
                                        dialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialog1.dismissWithAnimation();
                                                Intent intent = new Intent(context,ApplyAfterSaleActivity.class);
                                                intent.putExtra("orderId", data.get(position).getId());
                                                context.startActivity(intent);
                                            }
                                        });
                                        dialog1.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialog1.dismissWithAnimation();
                                            }
                                        });
                                        dialog1.showCancelButton(true);
                                        dialog1.setCancelText("取消");
                                        dialog1.setConfirmText("确定");
                                        dialog1.setTitleText("注意");
                                        dialog1.setContentText("是否要进行换货操作?");
                                        dialog1.setCancelable(false);
                                        dialog1.show();
                                        break;
                                    case 3:
                                        final SweetAlertDialog dialog2 = new SweetAlertDialog(context, WARNING_TYPE);
                                        dialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialog2.dismissWithAnimation();

                                            }
                                        });
                                        dialog2.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialog2.dismissWithAnimation();
                                            }
                                        });
                                        dialog2.showCancelButton(true);
                                        dialog2.setCancelText("取消");
                                        dialog2.setConfirmText("确定");
                                        dialog2.setTitleText("注意");
                                        dialog2.setContentText("已收货订单不能进行只退款操作。");
                                        dialog2.setCancelable(false);
                                        dialog2.show();
                                        break;
                                }
                            }
                        });
                    }
                });*/
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 5;
        if (data.get(position).getStatus() == null) {
            return 5;
        }
        if (data.get(position).getStatus().equals("待付款")) {
            type = 1;
        } else if (data.get(position).getStatus().equals("待发货")) {
            type = 2;
        } else if (data.get(position).getStatus().equals("待收货")) {
            type = 3;
        } else if (data.get(position).getStatus().equals("已关闭")) {
            type = 5;
        } else if (data.get(position).getStatus().equals("已完成")) {
            type = 5;
        }
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
        TextView tvOrderCreateTime1;
        ImageView ivGoodsPic1;
        TextView tvGoodsName1;
        TextView tvDescription1;
        TextView tvStoreType1;
        TextView tvGoodsCount1;
        TextView tvToPay;
        TextView tvCancelOrder;
        TextView tvShouldPay;

        //type = 2
        HorizontalScrollView hsvParent2;
        LinearLayout llGoodsContainer2;
        RelativeLayout rlOneGoodsParent2;
        TextView tvTAG2;
        TextView tvBackMoney;
        TextView tvOrderCreateTime2;
        ImageView ivGoodsPic2;
        TextView tvGoodsName2;
        TextView tvDescription2;
        TextView tvStoreType2;
        TextView tvGoodsCount2;
        TextView tvBuyAgain;

        //type = 3
        HorizontalScrollView hsvParent3;
        LinearLayout llGoodsContainer3;
        RelativeLayout rlOneGoodsParent3;
        TextView tvTAG3;
        TextView tvToSeeLogistics;
        TextView tvOrderCreateTime3;
        ImageView ivGoodsPic3;
        TextView tvGoodsName3;
        TextView tvDescription3;
        TextView tvStoreType3;
        TextView tvGoodsCount3;
        TextView tvConfirmReveived;
        //type = 4
        HorizontalScrollView hsvParent4;
        LinearLayout llGoodsContainer4;
        RelativeLayout rlOneGoodsParent4;
        TextView tvTAG4;
        TextView tvApplyAfterSale;
        TextView tvOrderCreateTime4;
        ImageView ivGoodsPic4;
        TextView tvGoodsName4;
        TextView tvDescription4;
        TextView tvStoreType4;
        TextView tvGoodsCount4;
        TextView tvToEvaluate;

        //type = 5
        HorizontalScrollView hsvParent5;
        LinearLayout llGoodsContainer5;
        RelativeLayout rlOneGoodsParent5;
        TextView tvTAG5;
        TextView tvStatus;
        TextView tvOrderCreateTime5;
        ImageView ivGoodsPic5;
        TextView tvGoodsName5;
        TextView tvDescription5;
        TextView tvStoreType5;
        TextView tvGoodsCount5;
        TextView tvCompleteBuyAgain;
        TextView tvApplyAfterSaleComplete;

        public ViewHolder(View item, int viewType) {
            super(item);
            switch (viewType) {
                case 1:
                    tvShouldPay = item.findViewById(R.id.should_pay);
                    tvStatus = item.findViewById(R.id.tv_status);
                    hsvParent1 = item.findViewById(R.id.hsView);
                    llGoodsContainer1 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent1 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG1 = item.findViewById(R.id.tv_tag);
                    tvOrderCreateTime1 = item.findViewById(R.id.tv_order_create_time);
                    tvDescription1 = item.findViewById(R.id.tv_goods_description_1);
                    tvGoodsName1 = item.findViewById(R.id.tv_goods_name_1);
                    ivGoodsPic1 = item.findViewById(R.id.iv_goods_pic);
                    tvStoreType1 = item.findViewById(R.id.tv_store_type);
                    tvGoodsCount1 = item.findViewById(R.id.tv_goods_count);
                    tvToPay = item.findViewById(R.id.tv_to_pay);
                    tvCancelOrder = item.findViewById(R.id.tv_cancel_order);
                    break;
                case 2:
                    tvStatus = item.findViewById(R.id.tv_status);
                    hsvParent2 = item.findViewById(R.id.hsView);
                    llGoodsContainer2 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent2 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG2 = item.findViewById(R.id.tv_tag);
                    tvBackMoney = item.findViewById(R.id.tv_back_money);
                    tvOrderCreateTime2 = item.findViewById(R.id.tv_order_create_time);
                    tvDescription2 = item.findViewById(R.id.tv_goods_description_1);
                    tvGoodsName2 = item.findViewById(R.id.tv_goods_name_1);
                    ivGoodsPic2 = item.findViewById(R.id.iv_goods_pic);
                    tvStoreType2 = item.findViewById(R.id.tv_store_type);
                    tvGoodsCount2 = item.findViewById(R.id.tv_goods_count);
                    tvBuyAgain = item.findViewById(R.id.tv_buy_again);
                    break;
                case 3:
                    tvStatus = item.findViewById(R.id.tv_status);
                    hsvParent3 = item.findViewById(R.id.hsView);
                    llGoodsContainer3 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent3 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG3 = item.findViewById(R.id.tv_tag);
                    tvToSeeLogistics = item.findViewById(R.id.tv_see_logistic);
                    tvOrderCreateTime3 = item.findViewById(R.id.tv_order_create_time);
                    tvDescription3 = item.findViewById(R.id.tv_goods_description_1);
                    tvGoodsName3 = item.findViewById(R.id.tv_goods_name_1);
                    ivGoodsPic3 = item.findViewById(R.id.iv_goods_pic);
                    tvStoreType3 = item.findViewById(R.id.tv_store_type);
                    tvGoodsCount3 = item.findViewById(R.id.tv_goods_count);
                    tvConfirmReveived = item.findViewById(R.id.tv_confirm_received);
                    break;
                case 4:
                    tvStatus = item.findViewById(R.id.tv_status);
                    hsvParent4 = item.findViewById(R.id.hsView);
                    llGoodsContainer4 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent4 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG4 = item.findViewById(R.id.tv_tag);
                    tvApplyAfterSale = item.findViewById(R.id.tv_apply_after_sale);
                    tvOrderCreateTime4 = item.findViewById(R.id.tv_order_create_time);
                    tvDescription4 = item.findViewById(R.id.tv_goods_description_1);
                    tvGoodsName4 = item.findViewById(R.id.tv_goods_name_1);
                    ivGoodsPic4 = item.findViewById(R.id.iv_goods_pic);
                    tvStoreType4 = item.findViewById(R.id.tv_store_type);
                    tvGoodsCount4 = item.findViewById(R.id.tv_goods_count);
                    tvToEvaluate = item.findViewById(R.id.tv_to_evaluate);
                    break;
                case 5:
                    tvStatus = item.findViewById(R.id.tv_status);
                    hsvParent5 = item.findViewById(R.id.hsView);
                    llGoodsContainer5 = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent5 = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG5 = item.findViewById(R.id.tv_tag);
                    tvOrderCreateTime5 = item.findViewById(R.id.tv_order_create_time);
                    tvDescription5 = item.findViewById(R.id.tv_goods_description_1);
                    tvGoodsName5 = item.findViewById(R.id.tv_goods_name_1);
                    ivGoodsPic5 = item.findViewById(R.id.iv_goods_pic);
                    tvStoreType5 = item.findViewById(R.id.tv_store_type);
                    tvGoodsCount5 = item.findViewById(R.id.tv_goods_count);
                    tvCompleteBuyAgain = item.findViewById(R.id.tv_buy_again);
                    tvApplyAfterSaleComplete = item.findViewById(R.id.tv_apply_after_sale);
                    break;
            }
        }
    }
}
