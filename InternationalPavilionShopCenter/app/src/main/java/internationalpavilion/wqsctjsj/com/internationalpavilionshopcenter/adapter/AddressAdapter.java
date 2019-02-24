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

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.AddOrEditAddressActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AddressBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.WARNING_TYPE;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AddressBean> data;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public AddressAdapter(Context context, ArrayList<AddressBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_address_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final AddressBean addressBean = data.get(position);
        holder.tvReceiveName.setText(addressBean.getReceiveName());
        holder.tvReceivePhone.setText(addressBean.getReceivePhone());
        if (addressBean.isChecked()) {
            holder.tvIsDefault.setVisibility(View.VISIBLE);
        } else {
            holder.tvIsDefault.setVisibility(View.INVISIBLE);
        }
        holder.tvDetailPlace.setText(addressBean.getDetailPlace());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog dialog = new SweetAlertDialog(context,WARNING_TYPE);
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismissWithAnimation();
                        Intent intent = new Intent(context, AddOrEditAddressActivity.class);
                        intent.putExtra("isEdit",true);
                        intent.putExtra("addressInfo",addressBean);
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
                dialog.setContentText("是否要修改收货地址?");
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        holder.llItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvReceiveName;
        TextView tvReceivePhone;
        TextView tvIsDefault;
        TextView tvDetailPlace;
        ImageView ivEdit;
        LinearLayout llItemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            tvReceiveName = itemView.findViewById(R.id.tv_receive_name);
            tvReceivePhone = itemView.findViewById(R.id.tv_phone_number);
            tvIsDefault = itemView.findViewById(R.id.tv_is_default);
            tvDetailPlace = itemView.findViewById(R.id.tv_detail_place);
            ivEdit = itemView.findViewById(R.id.iv_to_edit_place);
            llItemClick = itemView.findViewById(R.id.ll_item_click);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int id);
    }
}
