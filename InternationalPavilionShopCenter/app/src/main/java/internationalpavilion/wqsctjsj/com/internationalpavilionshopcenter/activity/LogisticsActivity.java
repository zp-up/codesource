package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.LogisticsAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.LogisticsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class LogisticsActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {
    @BindView(R.id.rv_logistic_list)
    RecyclerView rvLogisticsList;

    private LogisticsAdapter adapter;
    private ArrayList<LogisticsBean> data = new ArrayList<>();
    private int orderId = -1;

    private CommonDataInterface commonPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        commonPresenter = new CommonGoodsImp();
        orderId = getIntent().getIntExtra("orderId", -1);
        initData();
    }

    private void initData() {
//        if (data.size() == 0) {
//            LogisticsBean bean6 = new LogisticsBean();
//            bean6.acceptStation = "四川省成都市武侯区四公司 已收入";
//            bean6.acceptTime = "2018-12-12 20:39";
//            data.add(bean6);
//            LogisticsBean bean5 = new LogisticsBean();
//            bean5.acceptStation = "成都转运中心 已收入";
//            bean5.acceptTime = "2018-12-12 18:09";
//            data.add(bean5);
//            LogisticsBean bean4 = new LogisticsBean();
//            bean4.acceptStation = "广东省深圳市坂田公司 已发出，下一站 成都转运中心";
//            bean4.acceptTime = "2018-12-11 18:49";
//            data.add(bean4);
//            LogisticsBean bean3 = new LogisticsBean();
//            bean3.acceptStation = "广东省深圳市坂田公司 已收件";
//            bean3.acceptTime = "2018-12-10 17:00";
//            data.add(bean3);
//            LogisticsBean bean2 = new LogisticsBean();
//            bean2.acceptStation = "包裹正在等待揽收";
//            bean2.acceptTime = "2018-12-10 15:40";
//            data.add(bean2);
//            LogisticsBean bean1 = new LogisticsBean();
//            bean1.acceptStation = "您的订单开始处理";
//            bean1.acceptTime = "2018-12-10 10:09";
//            data.add(bean1);
//
//        }
        RequestParams params = new RequestParams(MainUrls.logisticsByOrderIdUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id",orderId+"");
        commonPresenter.getCommonGoodsData(params,this);
        if (adapter == null) {
            adapter = new LogisticsAdapter(this, data);
            rvLogisticsList.setLayoutManager(new LinearLayoutManager(this));
            rvLogisticsList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取数据中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "物流信息获取出错:" + error);
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null){
            Log.e("TAG","物流信息："+result);
            try {
                JSONObject jsonObject = new JSONObject(result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
