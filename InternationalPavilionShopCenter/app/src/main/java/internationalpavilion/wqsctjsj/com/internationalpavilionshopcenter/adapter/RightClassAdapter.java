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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ClassGoodsListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;

/**
 * Created by wuqaing on 2018/12/1.
 */

public class RightClassAdapter extends RecyclerView.Adapter<RightClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RightClassBean> data;
    private LayoutInflater inflater;

    public RightClassAdapter(Context context, ArrayList<RightClassBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_class_item_right_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.ivTopPic.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(position).getCurrentUrl()).apply(new RequestOptions().error(R.drawable.bg_class_in_class_1)).into(holder.ivTopPic);
        } else {
            holder.ivTopPic.setVisibility(View.GONE);
        }

        final RightClassBean rightClassBean = data.get(position);
        holder.tvClassType.setText(rightClassBean.getMainClassName());
        int count = rightClassBean.getChildBeans().size();
        if (holder.llClassDetailContainer != null) {
            holder.llClassDetailContainer.removeAllViews();
        }
        for (int i = 0; i < Math.floor(count / 3); i++) {
            View view = inflater.inflate(R.layout.item_child_class_in_class, null);
            TextView tvChild1 = view.findViewById(R.id.tv_child_class_name_1);
            tvChild1.setText(rightClassBean.getChildBeans().get(i).getClassName());
            TextView tvChild2 = view.findViewById(R.id.tv_child_class_name_2);
            tvChild2.setText(rightClassBean.getChildBeans().get(i + 1).getClassName());
            TextView tvChild3 = view.findViewById(R.id.tv_child_class_name_3);
            tvChild3.setText(rightClassBean.getChildBeans().get(i + 2).getClassName());

            ImageView ivChildPic1 = view.findViewById(R.id.iv_child_class_pic_1);
            Glide.with(context).load(rightClassBean.getChildBeans().get(i).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1)).into(ivChildPic1);
            final int index = i;
            ivChildPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassGoodsListActivity.class);
                    intent.putExtra("classId",rightClassBean.getChildBeans().get(index).getId());
                    intent.putExtra("className",rightClassBean.getChildBeans().get(index).getClassName());
                    context.startActivity(intent);
                }
            });
            ImageView ivChildPic2 = view.findViewById(R.id.iv_child_class_pic_2);
            Glide.with(context).load(rightClassBean.getChildBeans().get(i + 1).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1)).into(ivChildPic2);
            ivChildPic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassGoodsListActivity.class);
                    intent.putExtra("classId",rightClassBean.getChildBeans().get(index + 1).getId());
                    intent.putExtra("className",rightClassBean.getChildBeans().get(index + 1).getClassName());
                    context.startActivity(intent);
                }
            });
            ImageView ivChildPic3 = view.findViewById(R.id.iv_child_class_pic_3);
            Glide.with(context).load(rightClassBean.getChildBeans().get(i + 2).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1)).into(ivChildPic3);
            ivChildPic3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassGoodsListActivity.class);
                    intent.putExtra("classId",rightClassBean.getChildBeans().get(index + 2).getId());
                    intent.putExtra("className",rightClassBean.getChildBeans().get(index + 2).getClassName());
                    context.startActivity(intent);
                }
            });

            holder.llClassDetailContainer.addView(view);
        }
        if (count % 3 != 0 && count % 3 == 1) {
            View view = inflater.inflate(R.layout.item_child_class_in_class, null);
            LinearLayout llChild1 = view.findViewById(R.id.ll_class_detail_item_1);
            LinearLayout llChild2 = view.findViewById(R.id.ll_class_detail_item_2);
            LinearLayout llChild3 = view.findViewById(R.id.ll_class_detail_item_3);
            llChild2.setVisibility(View.INVISIBLE);
            llChild3.setVisibility(View.INVISIBLE);
            TextView tvChild1 = view.findViewById(R.id.tv_child_class_name_1);
            tvChild1.setText(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getClassName());

            ImageView ivChildPic1 = view.findViewById(R.id.iv_child_class_pic_1);
            Glide.with(context).load(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1)).into(ivChildPic1);
            ivChildPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassGoodsListActivity.class);
                    intent.putExtra("classId",rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getId());
                    intent.putExtra("className",rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getClassName());
                    context.startActivity(intent);
                }
            });

            holder.llClassDetailContainer.addView(view);
        } else if (count % 3 != 0 && count % 3 == 2) {
            View view = inflater.inflate(R.layout.item_child_class_in_class, null);
            LinearLayout llChild1 = view.findViewById(R.id.ll_class_detail_item_1);
            LinearLayout llChild2 = view.findViewById(R.id.ll_class_detail_item_2);
            LinearLayout llChild3 = view.findViewById(R.id.ll_class_detail_item_3);
            llChild3.setVisibility(View.INVISIBLE);

            TextView tvChild1 = view.findViewById(R.id.tv_child_class_name_1);
            tvChild1.setText(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 2).getClassName());
            TextView tvChild2 = view.findViewById(R.id.tv_child_class_name_2);
            tvChild2.setText(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getClassName());


            ImageView ivChildPic1 = view.findViewById(R.id.iv_child_class_pic_1);
            Glide.with(context).load(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 2).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1)).into(ivChildPic1);
            ivChildPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassGoodsListActivity.class);
                    intent.putExtra("classId",rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 2).getId());
                    intent.putExtra("className",rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 2).getClassName());
                    context.startActivity(intent);
                }
            });
            ImageView ivChildPic2 = view.findViewById(R.id.iv_child_class_pic_2);
            Glide.with(context).load(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1)).into(ivChildPic2);
            ivChildPic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassGoodsListActivity.class);
                    intent.putExtra("classId",rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getId());
                    intent.putExtra("className",rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getClassName());
                    context.startActivity(intent);
                }
            });

            holder.llClassDetailContainer.addView(view);
        }
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llClassDetailContainer;
        TextView tvClassType;
        ImageView ivTopPic;

        public ViewHolder(View itemView) {
            super(itemView);
            llClassDetailContainer = itemView.findViewById(R.id.ll_class_detail_container);
            tvClassType = itemView.findViewById(R.id.tv_class_type);
            ivTopPic = itemView.findViewById(R.id.iv_top_pic);
        }
    }
}
