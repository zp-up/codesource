package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.LeftClassBean;

/**
 * Created by wuqaing on 2018/11/30.
 */

public class LeftClassAdapter extends RecyclerView.Adapter<LeftClassAdapter.ViewHolder>{
    private Context context;
    private ArrayList<LeftClassBean> data;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public LeftClassAdapter(ArrayList<LeftClassBean> data, Context context) {
        this.data = data;
        this.context = context;
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_class_item_left,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        LeftClassBean classBean = data.get(position);
        holder.tvClassName.setText(classBean.getClassName());
        if (classBean.isChecked() == true){
            holder.viewChecked.setVisibility(View.VISIBLE);
            holder.tvClassName.setTextColor(Color.parseColor("#ff0000"));
        }else {
            holder.viewChecked.setVisibility(View.INVISIBLE);
            holder.tvClassName.setTextColor(Color.parseColor("#636363"));
        }
        holder.llClickParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for (int i = 0;i < data.size();i++){
//                    data.get(i).setChecked(false);
//                }
//                data.get(position).setChecked(true);
//                notifyDataSetChanged();
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClassName;
        View viewChecked;
        LinearLayout llClickParent;
        public ViewHolder(View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tv_class_name);
            viewChecked = itemView.findViewById(R.id.view_selected);
            llClickParent = itemView.findViewById(R.id.ll_click_item);
        }
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
}
