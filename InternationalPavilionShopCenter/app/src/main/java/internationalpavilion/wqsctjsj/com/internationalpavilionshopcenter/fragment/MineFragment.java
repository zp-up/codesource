package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.CollectionActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ContactCustomerServiceActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.CouponActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LoginByPasswordActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.MyBalanceActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.MyGroupActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.MyIntegralActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.RealNameAuthenticationListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ReceivedAddressListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.SettingActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.TokenEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.MineDataImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.MineDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnMineDataCallBack;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class MineFragment extends Fragment implements OnMineDataCallBack {

    private static final String TAG = "[IPSC][MineFragment]";
    private Unbinder unbinder;

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.civ_head)
    ImageView civHead;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_wait_pay_order)
    TextView tvWaitPayOrder;
    @BindView(R.id.tv_wait_delivery_order)
    TextView tvWaitDeliveryOrder;
    @BindView(R.id.tv_wait_received_order)
    TextView tvWaitReceivedOrder;
    @BindView(R.id.tv_after_sale_order)
    TextView tvAfterSaleOrder;
    @BindView(R.id.tv_group_order)
    TextView tvGroupOrder;

    private MineDataInterface minePresenter;
    private ArrayList<HashMap<String, Object>> userOrderData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        minePresenter = new MineDataImp();

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (((IPSCApplication) getActivity().getApplicationContext()).getUserInfo() != null) {
                    getUserInfo();
                    getUserOrder();
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((IPSCApplication) getActivity().getApplicationContext()).getUserInfo() != null) {
            getUserInfo();
            getUserOrder();
        }
    }

    private void getUserOrder() {
        try {
            if (TextUtils.isEmpty(IPSCApplication.accessToken)) {
                ToastUtils.show(getActivity(), "程序出错，请重启应用。");
                return;
            }
            RequestParams params = new RequestParams(MainUrls.getUserOrderUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            UserBean userInfo = ((IPSCApplication) getActivity().getApplicationContext()).getUserInfo();
            if (userInfo != null) {
                params.addBodyParameter("user", userInfo.getId() + "");
            }
            minePresenter.getUserOrderData(params, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubEventReceived(TokenEvent e) {
        if (e != null && e.code == 1) {
            getUserInfo();
        }else if (e != null && e.code == 2){
            initUserInfo();
        }
    }

    private void getUserInfo() {
        try {
            UserBean userBean = ((IPSCApplication) getActivity().getApplicationContext()).getUserInfo();
            if (userBean != null && userBean.getNickName() != null) {
                tvNickName.setText(userBean.getNickName());
            }
            if (userBean != null && userBean.getUserPhone() != null) {
                String phone = userBean.getUserPhone().toString();
                tvAccount.setText(phone.isEmpty() ? "没有数据" : phone);
            } else {
                tvAccount.setText("没有数据");
            }
            if (userBean != null && userBean.getImg() != null) {
                Log.e("TAG","头像地址:"+userBean.getImg());
                RequestOptions options = new RequestOptions();
                options.circleCrop();
                options.placeholder(R.mipmap.icon_mine_defaul_head);
                options.error(R.mipmap.icon_mine_defaul_head);
                Glide.with(getActivity()).load(userBean.getImg()).apply(options).into(civHead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.ll_account, R.id.rl_to_wait_pay_order, R.id.rl_to_wait_delivery_order,
            R.id.rl_to_wait_received_order, R.id.rl_to_after_sale_order, R.id.rl_to_group_order, R.id.ll_to_my_balance, R.id.ll_to_integral,
            R.id.ll_to_collection, R.id.ll_to_friends, R.id.ll_to_coupon, R.id.ll_to_address_manage, R.id.ll_to_real_name_auther, R.id.ll_to_custom_service,
            R.id.ll_to_setting,R.id.ll_to_wait_all_order
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_account:
                if (((IPSCApplication) getActivity().getApplicationContext()).getUserInfo() == null) {
                    Intent intent = new Intent(getActivity(), LoginByPasswordActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_to_wait_all_order:
                if(isLogin()){
                    Intent intentToWaitPayOrder = new Intent(getActivity(), OrderActivity.class);
                    intentToWaitPayOrder.putExtra("index", 0);
                    startActivity(intentToWaitPayOrder);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.rl_to_wait_pay_order:
                if(isLogin()){
                    Intent intentToWaitPayOrder = new Intent(getActivity(), OrderActivity.class);
                    intentToWaitPayOrder.putExtra("index", 1);
                    startActivity(intentToWaitPayOrder);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.rl_to_wait_delivery_order:
                if(isLogin()){
                    Intent intentToWaitDeliveryOrder = new Intent(getActivity(), OrderActivity.class);
                    intentToWaitDeliveryOrder.putExtra("index", 2);
                    startActivity(intentToWaitDeliveryOrder);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.rl_to_wait_received_order:
                if(isLogin()){
                    Intent intentToWaitReceivedOrder = new Intent(getActivity(), OrderActivity.class);
                    intentToWaitReceivedOrder.putExtra("index", 3);
                    startActivity(intentToWaitReceivedOrder);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.rl_to_after_sale_order:
                if(isLogin()){
                    Intent intentToAfterSaleOrder = new Intent(getActivity(), OrderActivity.class);
                    intentToAfterSaleOrder.putExtra("index", 4);
                    startActivity(intentToAfterSaleOrder);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.rl_to_group_order:
                if(isLogin()){
                    Intent intentToGroupOrder = new Intent(getActivity(), MyGroupActivity.class);
                    startActivity(intentToGroupOrder);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_my_balance:
                if(isLogin()){
                    Intent intentToMyBalance = new Intent(getActivity(), MyBalanceActivity.class);
                    startActivity(intentToMyBalance);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_integral:
                if(isLogin()){
                    Intent intentToIntegral = new Intent(getActivity(), MyIntegralActivity.class);
                    startActivity(intentToIntegral);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_collection:
                if(isLogin()){
                    Intent intentToCollection = new Intent(getActivity(), CollectionActivity.class);
                    startActivity(intentToCollection);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_friends:
                if(isLogin()){
//                    Intent intentToFriends = new Intent(getActivity(), FriendsActivity.class);
//                    startActivity(intentToFriends);
                    ToastUtils.show(getContext(), "暂未开通！");
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_coupon:
                if(isLogin()){
                    Intent intentToCoupon = new Intent(getActivity(), CouponActivity.class);
                    startActivity(intentToCoupon);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_address_manage:
                if(isLogin()){
                    Intent intentToAddressManage = new Intent(getActivity(), ReceivedAddressListActivity.class);
                    startActivity(intentToAddressManage);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_real_name_auther:
                if(isLogin()){
                    Intent intentToRealNameAuthor = new Intent(getActivity(), RealNameAuthenticationListActivity.class);
                    startActivity(intentToRealNameAuthor);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_custom_service:
                if(isLogin()){
                    Intent intentToCustomService = new Intent(getActivity(), ContactCustomerServiceActivity.class);
                    startActivity(intentToCustomService);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
            case R.id.ll_to_setting:
                if(isLogin()){
                    Intent intentToSetting = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intentToSetting);
                }else {
                    if(getActivity()!=null){
                        getActivity().startActivity(new Intent(getActivity(),LoginByPasswordActivity.class));
                    }
                }

                break;
        }
    }

    private void initUserInfo() {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.icon_mine_defaul_head);
        options.error(R.mipmap.icon_mine_defaul_head);
        Glide.with(getActivity()).load(R.mipmap.icon_mine_defaul_head).apply(options).into(civHead);
        tvNickName.setText("请先登录");
        tvAccount.setText("登录后查看");
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    //判断是否登录
    private boolean isLogin() {
        if (getActivity() != null && isAdded()) {
            if (((IPSCApplication) getActivity().getApplication()).getUserInfo() != null) {
                return true;
            }
        }
        return false;
    }

    //请求开始
    @Override
    public void onStarted() {
        ((BaseAppcompatActivity)getActivity()).showLoading(false,"获取数据中...");
    }

    //请求结束
    @Override
    public void onFinished() {
        ((BaseAppcompatActivity)getActivity()).dismissLoading();
    }

    //请求错误
    @Override
    public void onError(String error) {
        Log.e(TAG, "error:" + error);
    }

    //请求成功
    @Override
    public void onGetUserOrderData(String result) {
        LogUtil.d(TAG, "getUserOrder result:" + result);
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data != null && data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {

                            int id = data.getJSONObject(i).getInt("id");
                            int count =data.getJSONObject(i).getInt("count");

                            HashMap<String, Object> dataMap = new HashMap<>();
                            dataMap.put("id",id);
                            dataMap.put("name", data.getJSONObject(i).getString("name"));
                            dataMap.put("count", count);

                            userOrderData.add(dataMap);


                            switch (id) {
                                case 1:// 待付款
                                    tvWaitPayOrder.setText(count + "");
                                    tvWaitPayOrder.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                                    break;
                                case 2:// 拼团中
                                    tvGroupOrder.setText(count + "");
                                    tvGroupOrder.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                                    break;
                                case 3:// 待发货
                                    tvWaitDeliveryOrder.setText(count + "");
                                    tvWaitDeliveryOrder.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                                    break;
                                case 4:// 待收货
                                    tvWaitReceivedOrder.setText(count + "");
                                    tvWaitReceivedOrder.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                                    break;
                                case 5:// 售后单
                                    tvAfterSaleOrder.setText(count + "");
                                    tvAfterSaleOrder.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                                    break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.e(TAG, " parse user order occur exception!", e);
            }
        }
    }
}
