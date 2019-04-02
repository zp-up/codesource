package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.CollectionActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ContactCustomerServiceActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.CouponActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.FriendsActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LoginByPasswordActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.MyBalanceActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.MyGroupActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.MyIntegralActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.OrderActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.RealNameAuthenticationActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ReceivedAddressListActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.SettingActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.TokenEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class MineFragment extends Fragment {
    private Unbinder unbinder;

    @BindView(R.id.civ_head)
    ImageView civHead;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((IPSCApplication) getActivity().getApplicationContext()).getUserInfo() != null) {
            getUserInfo();
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
            if (userBean != null && userBean.getName() != null) {
                tvAccount.setText(userBean.getName());
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

    @OnClick({R.id.ll_account, R.id.ll_to_wait_pay_order, R.id.ll_to_wait_delivery_order,
            R.id.ll_to_wait_received_order, R.id.ll_to_after_sale_order, R.id.ll_to_group_order, R.id.ll_to_my_balance, R.id.ll_to_integral,
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
            case R.id.ll_to_wait_pay_order:
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
            case R.id.ll_to_wait_delivery_order:
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
            case R.id.ll_to_wait_received_order:
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
            case R.id.ll_to_after_sale_order:
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
            case R.id.ll_to_group_order:
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
                    Intent intentToFriends = new Intent(getActivity(), FriendsActivity.class);
                    startActivity(intentToFriends);
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
                    Intent intentToRealNameAuthor = new Intent(getActivity(), RealNameAuthenticationActivity.class);
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
}
