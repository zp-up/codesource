package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.EvaluateListBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.FriendsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsEvaluateList.GoodsEvaluateListBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.RatingView;


/**
 * Created by wuqaing on 2018/12/5.
 */

public class EvaluateListAdapter extends RecyclerView.Adapter<EvaluateListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GoodsEvaluateListBean> data;
    private LayoutInflater inflater;

    public EvaluateListAdapter(Context context, ArrayList<GoodsEvaluateListBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_evaluate_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GoodsEvaluateListBean evaluateListBean = data.get(position);
        Glide.with(context).load(evaluateListBean.getUserHeadImgUrl())
                .apply(new RequestOptions().error(R.mipmap.icon_mine_defaul_head).placeholder(R.mipmap.icon_mine_defaul_head))
                .into(holder.civHead);
        holder.tvNickName.setText(evaluateListBean.getUserNickName());
        String insertTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(evaluateListBean.getInsertTime()));
        holder.tvInsertTime.setText(insertTime);
        holder.tvEvaluateContent.setText(evaluateListBean.getEvaluateContent());
        if (holder.llGoodsContainer != null) {
            holder.llGoodsContainer.removeAllViews();
            if (evaluateListBean.getImages() != null && evaluateListBean.getImages().size() != 0) {
                holder.llImageContainer.setVisibility(View.VISIBLE);
                for (int i = 0; i < evaluateListBean.getImages().size(); i++) {
                    View view = inflater.from(context).inflate(R.layout.rv_item_order_image_view, null);
                    ImageView ivEvaluatePic = view.findViewById(R.id.iv_evaluate_pic);
                    Glide.with(context).load(evaluateListBean.getImages().get(i).getUrl())
                            .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
                            .into(ivEvaluatePic);
                    holder.llGoodsContainer.addView(view);
                }
            }else {
                holder.llImageContainer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llGoodsContainer;
        LinearLayout llImageContainer;
        CircleImageView civHead;
        TextView tvNickName;
        TextView tvInsertTime;
        TextView tvEvaluateContent;

        public ViewHolder(View itemView) {
            super(itemView);
            llGoodsContainer = itemView.findViewById(R.id.ll_goods_container);
            llImageContainer = itemView.findViewById(R.id.ll_image_container);
            civHead = itemView.findViewById(R.id.civ_head);
            tvNickName = itemView.findViewById(R.id.tv_nick_name);
            tvInsertTime = itemView.findViewById(R.id.tv_insert_time);
            tvEvaluateContent = itemView.findViewById(R.id.tv_evaluate_content);
        }
    }
}
