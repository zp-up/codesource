package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnRefundGoodsAddressCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.NORMAL_TYPE;
import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.WARNING_TYPE;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class AfterSaleAdapter extends RecyclerView.Adapter<AfterSaleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderRootBean> data;
    private LayoutInflater inflater;
    public static final int NO_REFUND = 0x0;// 无售后
    public static final int REFUNDIND = 0x1;// 售后中
    public static final int REFUND_GOODS = 0x2;// 退货中
    public static final int REFUND_MONEY = 0x3;// 退款中
    public static final int REFUND_DONE = 0x4;// 已售后


    public AfterSaleAdapter(Context context, ArrayList<OrderRootBean> data) {
        this.context = context;
        this.data = data;
        Log.i("[IPSC][AfterSaleAdapter]", " data size:" + (data == null ? "null" : data.size()));
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    public void setData(ArrayList<OrderRootBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case NO_REFUND: // 无售后
            case REFUNDIND: // 售后中
            case REFUND_GOODS: //
            case REFUND_MONEY:
            case REFUND_DONE:
                view = inflater.inflate(R.layout.rv_order_after_sale_layout, parent, false);
                break;
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        int viewType = getItemViewType(position);
        OrderRootBean rootBean = data.get(position);

        // 下面为不同状态共有的设置
        holder.tvGoodsCount.setText("共" + (rootBean.getGoodsBeans() != null ? rootBean.getGoodsBeans().size() : 0) + "种商品");
        holder.tvStoreType.setText(rootBean.getStoreType());
        holder.tvOrderStatus.setText(rootBean.getStatus());
        holder.tvOrderSn.setText("订单编号:" + rootBean.getOrderNumber());
        if (rootBean.getGoodsBeans() != null && rootBean.getGoodsBeans().size() == 1) {
            holder.tvGoodsName.setText(rootBean.getGoodsBeans().get(0).getGoodsName());
            holder.tvDescription.setText(rootBean.getGoodsBeans().get(0).getGoodsDescription());
            Glide.with(context).load(rootBean.getGoodsBeans().get(0).getImageUrl())
                    .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(holder.ivGoodsPic);
            holder.hsvParent.setVisibility(View.GONE);
            holder.rlOneGoodsParent.setVisibility(View.VISIBLE);
            holder.rlOneGoodsParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("orderId", data.get(position).getId());
                    context.startActivity(intent);
                }
            });
        } else if (rootBean.getGoodsBeans() != null && rootBean.getGoodsBeans().size() > 1) {
            holder.hsvParent.setVisibility(View.VISIBLE);
            holder.rlOneGoodsParent.setVisibility(View.GONE);
            holder.llGoodsContainer.removeAllViews();
            for (int i = 0; i < rootBean.getGoodsBeans().size(); i++) {
                View view = inflater.inflate(R.layout.rv_item_order_image_view, null);
                ImageView ivGoodsPic = view.findViewById(R.id.iv_evaluate_pic);
                Glide.with(context).load(rootBean.getGoodsBeans().get(i).getImageUrl())
                        .apply(new RequestOptions().error(R.drawable.bg_home_lay10_1)).into(ivGoodsPic);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra("orderId", data.get(position).getId());
                        context.startActivity(intent);
                    }
                });
                holder.llGoodsContainer.addView(view);
            }
        }
//        if (rootBean.getStatus().contains("拼团")) {
//            holder.tvTAG.setVisibility(View.VISIBLE);
//        } else {
//            holder.tvTAG.setVisibility(View.GONE);
//        }
        holder.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showExpressAddress(position);
                showBuyAgain(position);
            }
        });

        switch (viewType) {
            case NO_REFUND:// 售后单列表中应该不会出现这种未售后的单子
                holder.tvLeft.setText("退货");
                holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((OrderActivity) context).showRequestAfterSale(data.get(position).getId());
                    }
                });
                holder.tvLeft.setVisibility(View.VISIBLE);
                holder.tvRight.setVisibility(View.VISIBLE);
                break;
            case REFUNDIND:
                holder.tvLeft.setText("退货");
                holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRefunding();
                    }
                });
                holder.tvLeft.setVisibility(View.VISIBLE);
                holder.tvRight.setVisibility(View.VISIBLE);
                break;
            case REFUND_GOODS:
                if ("无退货".equals(rootBean.getRefund_goods())) {
                    holder.tvLeft.setText("退货");
                    holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showRefundGoodsAddress(position);
                        }
                    });
                } else {
                    holder.tvLeft.setText("售后中");
                    holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showReceiveGoods();
                        }
                    });
                }
                holder.tvLeft.setVisibility(View.VISIBLE);
                holder.tvRight.setVisibility(View.VISIBLE);
                break;
            case REFUND_MONEY:
                holder.tvLeft.setText("售后中");
                holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showReceiveGoods();
                    }
                });
                holder.tvLeft.setVisibility(View.VISIBLE);
                holder.tvRight.setVisibility(View.VISIBLE);
                break;
            case REFUND_DONE:
                holder.tvLeft.setText("已售后");
                holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRefundDone();
                    }
                });
                holder.tvLeft.setVisibility(View.VISIBLE);
                holder.tvRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
//        int type = data.get(position).getState();
        int type = NO_REFUND;
        String afterSaleState = data.get(position).getAfterSaleState();
        Log.d("IPSC", "getItemViewType position:" + position + ",data:" + data.get(position).toString());
        if ("无售后".equals(afterSaleState)) {
            type = NO_REFUND;
        } else if ("售后中".equals(afterSaleState)) {
            type = REFUNDIND;
        } else if ("退货中".equals(afterSaleState)) {
            type = REFUND_GOODS;
        } else if ("退款中".equals(afterSaleState)) {
            type = REFUND_MONEY;
        } else if ("已售后".equals(afterSaleState)) {
            type = REFUND_DONE;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        HorizontalScrollView hsvParent;
        LinearLayout llGoodsContainer;
        RelativeLayout rlOneGoodsParent;
        TextView tvTAG;
        TextView tvOrderStatus;// 订单状态
        TextView tvLeft;
        TextView tvRight;
        TextView tvOrderSn;// 订单编号
        ImageView ivGoodsPic;
        TextView tvGoodsName;
        TextView tvDescription;
        TextView tvStoreType;
        TextView tvGoodsCount;

        public ViewHolder(View item, int viewType) {
            super(item);
            switch (viewType) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    hsvParent = item.findViewById(R.id.hsView);
                    llGoodsContainer = item.findViewById(R.id.ll_goods_container);
                    rlOneGoodsParent = item.findViewById(R.id.rl_one_goods_parent);
                    tvTAG = item.findViewById(R.id.tv_tag);
                    tvOrderStatus = item.findViewById(R.id.tv_order_state);
                    tvLeft = item.findViewById(R.id.tv_left);
                    tvRight = item.findViewById(R.id.tv_right);

                    tvOrderSn = item.findViewById(R.id.tv_order_sn);
                    tvDescription = item.findViewById(R.id.tv_goods_description);
                    tvGoodsName = item.findViewById(R.id.tv_goods_name);
                    ivGoodsPic = item.findViewById(R.id.iv_goods_pic);
                    tvStoreType = item.findViewById(R.id.tv_store_type);
                    tvGoodsCount = item.findViewById(R.id.tv_goods_count);
                    break;
            }
        }
    }

    /**
     * 弹出售后申请已提对话框
     */
    public void showRefunding() {
        ((OrderActivity) context).showRefunding();
/*        final SweetAlertDialog dialog = new SweetAlertDialog(context, NORMAL_TYPE);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismissWithAnimation();
                sweetAlertDialog.hide();
            }
        });
        dialog.showCancelButton(false);
        dialog.setConfirmText("确定");
        dialog.setTitleText("售后申请已提");
        dialog.setContentText("亲～后台正在处理申请ing～");
        dialog.setCancelable(false);
        dialog.show();*/
    }

    /**
     * 弹出再次购买确认对话框
     * @param position
     */
    public void showBuyAgain(final int position) {
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

    /**
     * 弹出申请售后确认对话框
     * @param position
     */
//    public void showRequestAfterSale(final int position) {
//        final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
//        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                dialog.dismissWithAnimation();
////                ((OrderActivity) context).requestAfterSale(data.get(position).getId());
//                ((OrderActivity) context).toAfterSaleFormSubmit(data.get(position).getId());
//            }
//        });
//        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                dialog.dismissWithAnimation();
//            }
//        });
//        dialog.showCancelButton(true);
//        dialog.setCancelText("取消");
//        dialog.setConfirmText("确定");
//        dialog.setTitleText("温馨提示");
//        dialog.setContentText("是否要申请售后?");
//        dialog.setCancelable(false);
//        dialog.show();
//    }

    /**
     * 弹出收货中对话框
     */
    public void showReceiveGoods() {
        ((OrderActivity) context).showReceiveGoods();
/*        final SweetAlertDialog dialog = new SweetAlertDialog(context, NORMAL_TYPE);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismissWithAnimation();
                sweetAlertDialog.hide();
            }
        });
        dialog.showCancelButton(false);
        dialog.setConfirmText("确定");
        dialog.setTitleText("售后提示");
        dialog.setContentText("亲～售后中，等待商家收货ing～");
        dialog.setCancelable(false);
        dialog.show();*/
    }

    /**
     * 弹出退货快递地址信息填写对话框
     */
    public void showRefundGoodsAddress(final int position) {
        final int orderId = data.get(position).getId();
        ((OrderActivity) context).showRefundGoodsAddress(orderId);
/*        ((OrderActivity) context).getRefundGoodsAddress(orderId, new OnRefundGoodsAddressCallBack() {
            @Override
            public void getRefundGoodsAddress(String refundGoodsAddress) {
                Log.i("TAG", "refundGoodsAddress:" + refundGoodsAddress);
                String phone = "";
                String address = "";
                String person = "";
                try {
                    phone = refundGoodsAddress.split("<br>")[1].split(":")[1];
                    address = refundGoodsAddress.split("<br>")[2].split(":")[1];
                    person = refundGoodsAddress.split("<br>")[0];
                } catch (Exception e) {
                    Log.e("TAG", "parse data occur exception!", e);
                }
                Log.i("TAG", "phone:" + phone
                        + ",address:" + address
                        + ",person:" + person);


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                View expressAddressView = LayoutInflater.from(context).inflate(R.layout.dialog_express_address, null);
                builder.setView(expressAddressView);
                builder.setCancelable(true);
                final AlertDialog alertDialog = builder.create();
                expressAddressView.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
//                Toast.makeText(context, "取消填写退货快递地址信息", Toast.LENGTH_LONG).show();
                    }
                });
                expressAddressView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
//                Toast.makeText(context, "成功填写退货快递地址信息", Toast.LENGTH_LONG).show();
                        EditText editText = (EditText) alertDialog.findViewById(R.id.express_content);
                        ((OrderActivity) context).setExpressInfo(orderId, editText.getText().toString());
                    }
                });
                ((TextView) expressAddressView.findViewById(R.id.phone_content)).setText(phone);
                ((TextView) expressAddressView.findViewById(R.id.address_content)).setText(address + "\n" + person);

                alertDialog.show();
            }
        });*/
    }

    /**
     * 弹出售后已完成对话框
     */
    public void showRefundDone() {
        ((OrderActivity) context).showRefundDone();
    }
}
