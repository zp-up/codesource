package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.FriendsAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.FriendsBean;

public class FriendsActivity extends BaseAppcompatActivity {
    private ArrayList<FriendsBean> data = new ArrayList<>();
    private FriendsAdapter adapter;

    @BindView(R.id.rv_friends)
    RecyclerView rvFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (data.size() == 0){
            data.add(new FriendsBean());
            data.add(new FriendsBean());
            data.add(new FriendsBean());
            data.add(new FriendsBean());
            data.add(new FriendsBean());
        }
        if (adapter == null){
            adapter = new FriendsAdapter(this,data);
            rvFriends.setLayoutManager(new LinearLayoutManager(this));
            rvFriends.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }
    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
    @Override
    public int initLayout() {
        return R.layout.activity_friends;
    }

    @Override
    public void reloadData() {

    }
}
