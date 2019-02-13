package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LimitedTimeActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.HomeBannerBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.TimerView.TIMETYPE_S;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class GroupGoodsListAdapter extends DelegateAdapter.Adapter<GroupGoodsListAdapter.ViewHolder> {
    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private ArrayList<HashMap<String, Object>> data;
    // 用于存放数据列表

    private Context context;
    private LayoutInflater inflater;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;
    private int type;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public GroupGoodsListAdapter(Context context, LayoutHelper layoutHelper, int count,
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

    public GroupGoodsListAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull RecyclerView.LayoutParams layoutParams,
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
                view = inflater.inflate(R.layout.rv_group_goods_header_layout, parent, false);
                break;
            case TYPE_2:
                view = inflater.inflate(R.layout.rv_group_goods_list_layout, parent, false);
                break;

        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_1:

                break;
            case TYPE_2:
                holder.tvGroupTime.setTime(6 * 60 * 60, TIMETYPE_S);
                holder.tvGroupTime.start();
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
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TimerView tvGroupTime;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case TYPE_1:

                    break;
                case TYPE_2:
                    tvGroupTime = itemView.findViewById(R.id.tv_group_time);
                    break;
            }
        }
    }


}
