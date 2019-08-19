package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CartGood;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CartRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.WARNING_TYPE;

/**
 * Created by wuqaing on 2018/12/3.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CartRootBean> data;
    private LayoutInflater inflater;
    private OnShoppingCartOperationCallBack listener;


    public CartAdapter(Context context, ArrayList<CartRootBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    public void setListener(OnShoppingCartOperationCallBack listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_item_cart_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cartContentLinearLayout = view.findViewById(R.id.cartContents);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ArrayList<CartGood> goodsList = data.get(position).getmCartGood();
        if (data.get(position).getCartGoodsName() == null || data.get(position).getCartGoodsName().isEmpty()) {
            ((View) holder.itemCarts.getParent()).setVisibility(View.GONE);
        }
        holder.itemCarts.setText(data.get(position).getStoreType());
        holder.cartContentLinearLayout.removeAllViews();
        holder.tvStoreGoodsTotalPrice.setText("￥" + data.get(position).getTotalPrice());
        for (int i = 0; i < goodsList.size(); i++) {
            final CartGood goods = goodsList.get(i);
            View cartBean = View.inflate(context, R.layout.item_cart_goods_layout, null);
            TextView cartGoodName, cartGoodInfo, cartGoodPrice, cartGoodOriginalPrice, cartGoodTaxation, cartGoodNum, cartAdd, cartSub;
            ImageView ivChecked;
            ImageView ivGoodsPic;
            ImageView ivDelete;

            ivGoodsPic = cartBean.findViewById(R.id.cart_good_image);
            Glide.with(context).load(goods.getImagePath()).apply(new RequestOptions().error(R.drawable.icon_no_image)).into(ivGoodsPic);
            ivChecked = cartBean.findViewById(R.id.cart_good_checkImage);
            if (goods.isChecked()) {
                ivChecked.setImageResource(R.mipmap.icon_cart_box_selected);
            } else {
                ivChecked.setImageResource(R.mipmap.icon_cart_box_unselected);
            }
            final int index = i;
            ivChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onGoodsChanged(goods.getId());
                    }
                }
            });
            cartAdd = cartBean.findViewById(R.id.cart_good_maxNum);

            cartSub = cartBean.findViewById(R.id.cart_good_minNum);

            ivDelete = cartBean.findViewById(R.id.cart_good_delete);

            cartGoodName = cartBean.findViewById(R.id.cart_good_name);

            cartGoodInfo = cartBean.findViewById(R.id.cart_good_info);

            cartGoodPrice = cartBean.findViewById(R.id.cart_good_price);

            cartGoodOriginalPrice = cartBean.findViewById(R.id.cart_good_originalPrice);

            cartGoodTaxation = cartBean.findViewById(R.id.cart_good_taxation);

            cartGoodNum = cartBean.findViewById(R.id.cart_good_num);
            cartGoodName.setText(data.get(position).getmCartGood().get(i).getName());
            cartGoodInfo.setText(data.get(position).getmCartGood().get(i).getInfo());
            cartGoodPrice.setText("￥" + data.get(position).getmCartGood().get(i).getPrice());
            cartGoodOriginalPrice.setText("￥" + data.get(position).getmCartGood().get(i).getOriginalPrice());
            cartGoodOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            cartGoodTaxation.setText("￥" + data.get(position).getmCartGood().get(i).getTaxation());
            cartGoodNum.setText(String.valueOf(data.get(position).getmCartGood().get(i).getNum()));
            holder.cartContentLinearLayout.addView(cartBean);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SweetAlertDialog dialog = new SweetAlertDialog(context, WARNING_TYPE);
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.dismissWithAnimation();
                            if (listener != null) {
                                listener.onDeleteGoodsCart(goods.getId());
                            }
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
                    dialog.setContentText("是否从购物车移除?");
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });
            cartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
//                        listener.onNumAdd(goods.getId(), goods.getNum());
                        listener.onNumAdd(goods.getGoodsPriceId(), goods.getNum());
                    }
                }
            });
            cartSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int currentNum = goods.getNum();
                    if(currentNum==1){
                        Toast.makeText(context, "该商品数量不能再减了哦", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (listener != null) {
                        listener.onNumSub(goods.getGoodsPriceId(), goods.getNum());
                    }
                }
            });
        }
        holder.ivAllChecked.setImageResource(isAllChecked(data.get(position).getmCartGood()) ? R.mipmap.icon_cart_box_selected : R.mipmap.icon_cart_box_unselected);
        holder.ivAllChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onStoreChanged(data.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cartContentLinearLayout;
        TextView itemCarts;
        TextView tvStoreGoodsTotalPrice;
        ImageView ivAllChecked;


        public ViewHolder(View itemView) {
            super(itemView);
            itemCarts = itemView.findViewById(R.id.item_carts);
            tvStoreGoodsTotalPrice = itemView.findViewById(R.id.tv_store_total);
            ivAllChecked = itemView.findViewById(R.id.iv_all_selected);
        }
    }

    public interface OnShoppingCartOperationCallBack {
        void onGoodsChanged(int goodsCartId);

        void onStoreChanged(int storeId);

        void onNumAdd(int goodsCartId, int number);

        void onNumSub(int goodsCartId, int number);

        void onDeleteGoodsCart(int goodsCartId);
    }

    private boolean isAllChecked(ArrayList<CartGood> goodList) {
        if (goodList == null) {
            return false;
        }
        for (int i = 0; i < goodList.size(); i++) {
            if (!goodList.get(i).isChecked()) {
                return false;
            }
        }
        return true;
    }

}
