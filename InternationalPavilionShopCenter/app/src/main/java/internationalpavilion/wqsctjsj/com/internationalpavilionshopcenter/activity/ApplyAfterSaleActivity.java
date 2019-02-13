package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AfterSaleGoodsBean;

public class ApplyAfterSaleActivity extends BaseAppcompatActivity {
    @BindView(R.id.rv_goods_list)
    RecyclerView rvGoodsList;
    private ArrayList<AfterSaleGoodsBean> data = new ArrayList<>();
    private AfterSaleGoodsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);

    }
    @OnClick({R.id.iv_back,R.id.tv_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                ApplyAfterSaleActivity.this.finish();
                break;
            case R.id.tv_next:
                Intent intent = new Intent(ApplyAfterSaleActivity.this,AfterSaleFormSubmitActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (data.size() == 0){
            data.add(new AfterSaleGoodsBean());
            data.add(new AfterSaleGoodsBean());
            data.add(new AfterSaleGoodsBean());
            data.add(new AfterSaleGoodsBean());
            data.add(new AfterSaleGoodsBean());
        }
        if (adapter == null){
            adapter = new AfterSaleGoodsAdapter(this,data);
            rvGoodsList.setLayoutManager(new LinearLayoutManager(this));
            rvGoodsList.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_apply_after_sale;
    }

    @Override
    public void reloadData() {

    }
}
