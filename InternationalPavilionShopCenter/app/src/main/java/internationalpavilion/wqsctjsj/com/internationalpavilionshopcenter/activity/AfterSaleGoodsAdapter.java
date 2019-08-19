package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AfterSaleGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.FriendsBean;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class AfterSaleGoodsAdapter extends RecyclerView.Adapter<AfterSaleGoodsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<AfterSaleGoodsBean> data;
    private LayoutInflater inflater;

    public AfterSaleGoodsAdapter(Context context, ArrayList<AfterSaleGoodsBean> data) {
        this.context = context;
        this.data = data;
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_after_sale_goods_layout,parent,false);
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
