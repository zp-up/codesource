package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.BrandDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;

/**
 * Created by wuqaing on 2018/12/1.
 */

public class ClassBrandAdapter extends RecyclerView.Adapter<ClassBrandAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RightClassBean> data;
    private LayoutInflater inflater;

    public ClassBrandAdapter(Context context, ArrayList<RightClassBean> classBeans) {
        this.context = context;
        this.data = classBeans;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_class_item_bran_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RightClassBean rightClassBean = data.get(position);
        holder.tvClassType.setText(rightClassBean.getMainClassName());
        final int count = rightClassBean.getChildBeans().size();
        if (holder.llClassDetailContainer != null) {
            holder.llClassDetailContainer.removeAllViews();
        }
        for (int i = 0; i < Math.floor(count / 3); i++) {
            View view = inflater.inflate(R.layout.item_child_class_in_bran, null);

            ImageView ivChildPic1 = view.findViewById(R.id.iv_child_class_pic_1);
            Glide.with(context).load(rightClassBean.getChildBeans().get(i * 3).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_class_in_brand_1).error(R.drawable.bg_class_in_brand_1)).into(ivChildPic1);
            final int brandId = rightClassBean.getChildBeans().get(i * 3).getId();
            final String brandName = rightClassBean.getChildBeans().get(i * 3).getClassName();
            ivChildPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("brandId", brandId);
                    intent.putExtra("brandName", brandName);
                    context.startActivity(intent);
                }
            });

            ImageView ivChildPic2 = view.findViewById(R.id.iv_child_class_pic_2);
            Glide.with(context).load(rightClassBean.getChildBeans().get(i * 3 + 1).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_class_in_brand_1).error(R.drawable.bg_class_in_brand_1)).into(ivChildPic2);
            final int brandId1 = rightClassBean.getChildBeans().get(i * 3 + 1).getId();
            final String brandName1 = rightClassBean.getChildBeans().get(i * 3 + 1).getClassName();
            ivChildPic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("brandId", brandId1);
                    intent.putExtra("brandName", brandName1);
                    context.startActivity(intent);
                }
            });
            ImageView ivChildPic3 = view.findViewById(R.id.iv_child_class_pic_3);
            Glide.with(context).load(rightClassBean.getChildBeans().get(i * 3 + 2).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_class_in_brand_1).error(R.drawable.bg_class_in_brand_1)).into(ivChildPic3);
            final int brandId2 = rightClassBean.getChildBeans().get(i * 3 + 2).getId();
            final String brandName2 = rightClassBean.getChildBeans().get(i * 3 + 2).getClassName();
            ivChildPic3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("brandId", brandId2);
                    intent.putExtra("brandName", brandName2);
                    context.startActivity(intent);
                }
            });

            holder.llClassDetailContainer.addView(view);
        }
        if (count % 3 != 0 && count % 3 == 1) {
            View view = inflater.inflate(R.layout.item_child_class_in_bran, null);
            LinearLayout llChild1 = view.findViewById(R.id.ll_class_detail_item_1);
            LinearLayout llChild2 = view.findViewById(R.id.ll_class_detail_item_2);
            LinearLayout llChild3 = view.findViewById(R.id.ll_class_detail_item_3);
            llChild2.setVisibility(View.INVISIBLE);
            llChild3.setVisibility(View.INVISIBLE);

            ImageView ivChildPic1 = view.findViewById(R.id.iv_child_class_pic_1);
            Glide.with(context).load(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_class_in_brand_1).error(R.drawable.bg_class_in_brand_1)).into(ivChildPic1);
            final int brandId = rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getId();
            final String brandName = rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getClassName();
            ivChildPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("brandId", brandId);
                    intent.putExtra("brandName", brandName);
                    context.startActivity(intent);
                }
            });
            holder.llClassDetailContainer.addView(view);
        } else if (count % 3 != 0 && count % 3 == 2) {
            View view = inflater.inflate(R.layout.item_child_class_in_bran, null);
            LinearLayout llChild1 = view.findViewById(R.id.ll_class_detail_item_1);
            LinearLayout llChild2 = view.findViewById(R.id.ll_class_detail_item_2);
            LinearLayout llChild3 = view.findViewById(R.id.ll_class_detail_item_3);
            llChild3.setVisibility(View.INVISIBLE);

            ImageView ivChildPic1 = view.findViewById(R.id.iv_child_class_pic_1);
            Glide.with(context).load(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 2).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_class_in_brand_1).error(R.drawable.bg_class_in_brand_1)).into(ivChildPic1);
            final int brandId1 = rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 2).getId();
            final String brandName1 = rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 2).getClassName();
            ivChildPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("brandId", brandId1);
                    intent.putExtra("brandName", brandName1);
                    context.startActivity(intent);
                }
            });
            ImageView ivChildPic2 = view.findViewById(R.id.iv_child_class_pic_2);
            Glide.with(context).load(rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getImgUrl()).apply(new RequestOptions().placeholder(R.drawable.bg_class_in_brand_1).error(R.drawable.bg_class_in_brand_1)).into(ivChildPic2);
            final int brandId2 = rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getId();
            final String brandName2 = rightClassBean.getChildBeans().get(rightClassBean.getChildBeans().size() - 1).getClassName();
            ivChildPic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("brandId", brandId2);
                    intent.putExtra("brandName", brandName2);
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

        public ViewHolder(View itemView) {
            super(itemView);
            llClassDetailContainer = itemView.findViewById(R.id.ll_class_detail_container);
            tvClassType = itemView.findViewById(R.id.tv_class_type);
        }
    }

}
