package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AddressBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.OrderRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.orderBeans.OrderGoodsBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

public class PayResultActivity extends BaseAppcompatActivity implements OnCommonGoodsCallBack {

    private String orderId;
    private CommonDataInterface commonPresenter;



    @BindView(R.id.tv_receive_name)
    TextView tv_receive_name;
    @BindView(R.id.tv_receive_phone)
    TextView tv_receive_phone;
    @BindView(R.id.tv_receive_address)
    TextView tv_receive_address;
    @BindView(R.id.tv_pay)
    TextView tv_pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        commonPresenter = new CommonGoodsImp();

        if(getIntent()!=null){
            orderId= getIntent().getStringExtra("orderId");
        }

        getOrderInfo();

    }

    @Override
    public int initLayout() {
        return R.layout.activity_pay_result;
    }

    @OnClick({R.id.iv_back,R.id.tv_check_order,R.id.tv_continue})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_check_order:
                Intent intent = new Intent(PayResultActivity.this,OrderDetailActivity.class);
                intent.putExtra("orderId",Integer.valueOf(orderId));
                startActivity(intent);
                finish();
                break;
            case R.id.tv_continue:
                Intent intent1 = new Intent(PayResultActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    @Override
    public void reloadData() {

    }

    private void getOrderInfo(){
        RequestParams params = new RequestParams(MainUrls.getOrderDetailByIdUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", orderId);
        commonPresenter.getCommonGoodsData(params,this);

    }


    @Override
    public void onStarted() {

    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onError(String error) {

    }


    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    JSONObject data = jsonObject.getJSONObject("data");

                    if(data!=null){

                        JSONObject addressObj= data.getJSONObject("address");
                        String name = "",country="",province="",city="",area="",address="",telphone="";
                        if(addressObj!=null){
                            name = addressObj.getString("name");
                            country = addressObj.getString("country");
                            province = addressObj.getString("province");
                            telphone = addressObj.getString("telphone");
                            city = addressObj.getString("city");
                            area = addressObj.getString("area");
                            address = addressObj.getString("address");
                        }

                        tv_receive_name.setText(name);
                        tv_receive_phone.setText(telphone);
                        tv_receive_address.setText(province+city+area+address);
                        tv_pay.setText("￥"+data.getDouble("pay_total"));

                    }

                }

            } catch (Exception e) {

            }
        }
    }


}
