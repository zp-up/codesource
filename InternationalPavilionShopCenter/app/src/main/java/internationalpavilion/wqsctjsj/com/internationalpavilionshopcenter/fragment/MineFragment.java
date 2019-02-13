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

    @OnClick({R.id.tv_account, R.id.ll_to_wait_pay_order, R.id.ll_to_wait_delivery_order,
            R.id.ll_to_wait_received_order, R.id.ll_to_after_sale_order, R.id.ll_to_group_order, R.id.ll_to_my_balance, R.id.ll_to_integral,
            R.id.ll_to_collection, R.id.ll_to_friends, R.id.ll_to_coupon, R.id.ll_to_address_manage, R.id.ll_to_real_name_auther, R.id.ll_to_custom_service,
            R.id.ll_to_setting
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account:
                if (((IPSCApplication) getActivity().getApplicationContext()).getUserInfo() == null) {
                    Intent intent = new Intent(getActivity(), LoginByPasswordActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_to_wait_pay_order:
                Intent intentToWaitPayOrder = new Intent(getActivity(), OrderActivity.class);
                intentToWaitPayOrder.putExtra("index", 1);
                startActivity(intentToWaitPayOrder);
                break;
            case R.id.ll_to_wait_delivery_order:
                Intent intentToWaitDeliveryOrder = new Intent(getActivity(), OrderActivity.class);
                intentToWaitDeliveryOrder.putExtra("index", 2);
                startActivity(intentToWaitDeliveryOrder);
                break;
            case R.id.ll_to_wait_received_order:
                Intent intentToWaitReceivedOrder = new Intent(getActivity(), OrderActivity.class);
                intentToWaitReceivedOrder.putExtra("index", 3);
                startActivity(intentToWaitReceivedOrder);
                break;
            case R.id.ll_to_after_sale_order:
                Intent intentToAfterSaleOrder = new Intent(getActivity(), OrderActivity.class);
                intentToAfterSaleOrder.putExtra("index", 4);
                startActivity(intentToAfterSaleOrder);
                break;
            case R.id.ll_to_group_order:
                Intent intentToGroupOrder = new Intent(getActivity(), MyGroupActivity.class);
                startActivity(intentToGroupOrder);
                break;
            case R.id.ll_to_my_balance:
                Intent intentToMyBalance = new Intent(getActivity(), MyBalanceActivity.class);
                startActivity(intentToMyBalance);
                break;
            case R.id.ll_to_integral:
                Intent intentToIntegral = new Intent(getActivity(), MyIntegralActivity.class);
                startActivity(intentToIntegral);
                break;
            case R.id.ll_to_collection:
                Intent intentToCollection = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intentToCollection);
                break;
            case R.id.ll_to_friends:
                Intent intentToFriends = new Intent(getActivity(), FriendsActivity.class);
                startActivity(intentToFriends);
                break;
            case R.id.ll_to_coupon:
                Intent intentToCoupon = new Intent(getActivity(), CouponActivity.class);
                startActivity(intentToCoupon);
                break;
            case R.id.ll_to_address_manage:
                Intent intentToAddressManage = new Intent(getActivity(), ReceivedAddressListActivity.class);
                startActivity(intentToAddressManage);
                break;
            case R.id.ll_to_real_name_auther:
                Intent intentToRealNameAuthor = new Intent(getActivity(), RealNameAuthenticationActivity.class);
                startActivity(intentToRealNameAuthor);
                break;
            case R.id.ll_to_custom_service:
                Intent intentToCustomService = new Intent(getActivity(), ContactCustomerServiceActivity.class);
                startActivity(intentToCustomService);
                break;
            case R.id.ll_to_setting:
                Intent intentToSetting = new Intent(getActivity(), SettingActivity.class);
                startActivity(intentToSetting);
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
}
