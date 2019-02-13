package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.os.Bundle;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;


import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.GroupGoodsListAdapter;


public class GroupGoodsListActivity extends BaseAppcompatActivity {
    @BindView(R.id.rv_group_goods_list)
    RecyclerView rvGroupGoodsList;

   private VirtualLayoutManager layoutManager;
   private DelegateAdapter adapter;
   private GroupGoodsListAdapter adapter1,adapter2;
   private ArrayList<DelegateAdapter.Adapter> subAdapterList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
        initRVLayout();
        initData();
    }

    private void initRVLayout() {
        layoutManager = new VirtualLayoutManager(this);
        rvGroupGoodsList.setLayoutManager(layoutManager);

        //2.设置组件复用回收池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(1, 1);
        recycledViewPool.setMaxRecycledViews(2, 30);
        rvGroupGoodsList.setRecycledViewPool(recycledViewPool);

        initLayoutHelper1();
        initLayoutHelper2();

        //设置适配器
        adapter = new DelegateAdapter(layoutManager, false);
        adapter.setAdapters(subAdapterList);
        rvGroupGoodsList.setAdapter(adapter);
        rvGroupGoodsList.setItemViewCacheSize(30);
    }
    private void initLayoutHelper1() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        ArrayList<HashMap<String,Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
        adapter1 = new GroupGoodsListAdapter(this,singleLayoutHelper,1,data,1);
        subAdapterList.add(adapter1);
    }
    private void initLayoutHelper2() {
        LinearLayoutHelper singleLayoutHelper = new LinearLayoutHelper();
        ArrayList<HashMap<String,Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
        data.add(new HashMap<String, Object>());
        data.add(new HashMap<String, Object>());
        data.add(new HashMap<String, Object>());
        data.add(new HashMap<String, Object>());
        adapter2 = new GroupGoodsListAdapter(this,singleLayoutHelper,data.size(),data,2);
        subAdapterList.add(adapter2);
    }



    private void initData() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_group_goods_list;
    }

    @Override
    public void reloadData() {

    }
}
