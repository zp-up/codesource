package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CollectionGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.FriendsBean;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<FriendsBean> data;
    private LayoutInflater inflater;

    public FriendsAdapter(Context context, ArrayList<FriendsBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_friends_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
