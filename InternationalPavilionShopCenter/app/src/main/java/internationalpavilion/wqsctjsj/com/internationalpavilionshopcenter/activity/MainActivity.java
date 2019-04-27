package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.MainSwitchEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.TokenEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.CartFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.ClassFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.HomeFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment.MineFragment;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.UserOptionImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UserOptionInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

public class MainActivity extends BaseAppcompatActivity {
    private FragmentManager mFragmentManager;
    private Fragment homeFragment, classFragment, cartFragment, mineFragment;

    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_class)
    RadioButton rbClass;
    @BindView(R.id.rb_shop_cart)
    RadioButton rbShopCart;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.frame_container)
    FrameLayout fContainer;
    @BindView(R.id.rg_bottom)
    RadioGroup rgHome;

    private int index = -1;

    private boolean isExit = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
            }
        }
    };
    private UserOptionInterface userOptionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        userOptionPresenter = new UserOptionImp();
        mFragmentManager = getSupportFragmentManager();
        addFragment(1);
        initView();
        EventBus.getDefault().register(this);
    }

    private void hideAllFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (classFragment != null) {
            transaction.hide(classFragment);
        }
        if (cartFragment != null) {
            transaction.hide(cartFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
        //提交
        transaction.commit();
    }

    private void addFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index) {
            case 1:
                hideAllFragment();
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.frame_container, homeFragment);
                    transaction.show(homeFragment).commit();
                } else {
                    transaction.show(homeFragment).commit();
                }
                break;
            case 2:
                hideAllFragment();
                if (classFragment == null) {
                    classFragment = new ClassFragment();
                    transaction.add(R.id.frame_container, classFragment);
                    transaction.show(classFragment).commit();
                } else {
                    transaction.show(classFragment).commit();
                }
                break;
            case 3:
                hideAllFragment();
                if (cartFragment == null) {
                    cartFragment = new CartFragment();
                    transaction.add(R.id.frame_container, cartFragment);
                    transaction.show(cartFragment).commit();
                } else {
                    transaction.show(cartFragment).commit();
                }
                break;
            case 4:
                hideAllFragment();
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.frame_container, mineFragment);
                    transaction.show(mineFragment).commit();
                } else {
                    transaction.show(mineFragment).commit();
                }
                break;
        }
    }

    private void initView() {
        //设置homeTab为选择状态
        setDrawable(rbHome, R.mipmap.icon_home_tab_selected);
        setDrawable(rbClass, R.mipmap.icon_class_tab_unselected);
        setDrawable(rbShopCart, R.mipmap.icon_shop_cart_tab_unselected);
        setDrawable(rbMine, R.mipmap.icon_mine_tab_unselected);

    }

    private void setDrawable(RadioButton mRadioButton, int id) {
        Drawable mDrawable = getResources().getDrawable(id);
        //设置图标大小
        mDrawable.setBounds(0, 0, 55, 60);
        mRadioButton.setCompoundDrawables(null, mDrawable, null, null);
    }

    //设置点击事件
    @OnClick({R.id.rb_home,R.id.rb_class,R.id.rb_shop_cart,R.id.rb_mine})
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.rb_home://首页
                selectedView(1);
                break;
            case R.id.rb_class://分类
                selectedView(2);
                break;
            case R.id.rb_shop_cart://购物车
                selectedView(3);
                break;
            case R.id.rb_mine://我的
                selectedView(4);
                break;
        }


    }

    private void selectedView(int i) {
        switch (i) {
            case 1:
                setDrawable(rbHome, R.mipmap.icon_home_tab_selected);
                setDrawable(rbClass, R.mipmap.icon_class_tab_unselected);
                setDrawable(rbShopCart, R.mipmap.icon_shop_cart_tab_unselected);
                setDrawable(rbMine, R.mipmap.icon_mine_tab_unselected);
                addFragment(1);
                break;
            case 2:
                setDrawable(rbHome, R.mipmap.icon_home_tab_unselected);
                setDrawable(rbClass, R.mipmap.icon_class_tab_selected);
                setDrawable(rbShopCart, R.mipmap.icon_shop_cart_tab_unselected);
                setDrawable(rbMine, R.mipmap.icon_mine_tab_unselected);
                addFragment(2);
                break;
            case 3:
                setDrawable(rbHome, R.mipmap.icon_home_tab_unselected);
                setDrawable(rbClass, R.mipmap.icon_class_tab_unselected);
                setDrawable(rbShopCart, R.mipmap.icon_shop_cart_tab_selected);
                setDrawable(rbMine, R.mipmap.icon_mine_tab_unselected);
                addFragment(3);
                break;
            case 4:
                setDrawable(rbHome, R.mipmap.icon_home_tab_unselected);
                setDrawable(rbClass, R.mipmap.icon_class_tab_unselected);
                setDrawable(rbShopCart, R.mipmap.icon_shop_cart_tab_unselected);
                setDrawable(rbMine, R.mipmap.icon_mine_tab_selected);
                addFragment(4);
                break;
        }
    }

    //setContentView
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void reloadData() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                System.exit(0);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceived(TokenEvent event) {
        if (event != null && event.code == 0) {
            initData();
        }
    }

    private void initData() {
        if (homeFragment != null) {
            ((HomeFragment) homeFragment).initData();
        }
        oneKeyLogin();
    }

    private void oneKeyLogin() {
        final UserBean userBean = ((IPSCApplication) getApplication()).getUserInfo();
        if (userBean == null) {
            return;
        }
        if (userBean != null && userBean.getPassword() != null && userBean.getUserPhone() != null) {
            RequestParams params = new RequestParams(MainUrls.userLoginUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("name", userBean.getUserPhone());
            params.addBodyParameter("password", userBean.getPassword());
            userOptionPresenter.doLogin(params, new OnNetCallBack() {
                @Override
                public void onStarted() {
                    showLoading(false, "网络请求中,请稍候");
                }

                @Override
                public void onFinished() {
                    dismissLoading();
                }

                @Override
                public void onError(String error) {
                    oneKeyLogin();
                }

                @Override
                public void onGetCodeSuccess(String result) {

                }

                @Override
                public void onCommonSuccess(String result, int type) {

                }

                @Override
                public void onLoginSuccess(String result) {
                    try {
                        Log.e("TAG", "用户登录:" + result);
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        int state = jsonObject.getInt("state");
                        String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                        if (code == 0 && state == 0) {
                            if (jsonObject.has("data") && jsonObject.getString("data") != null && !jsonObject.getString("data").equals("null")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                UserBean userBeanNew = new UserBean();
                                userBeanNew.setId(data.getInt("id"));
                                userBeanNew.setName(data.getString("name"));
                                userBeanNew.setUserPhone(userBean.getUserPhone());
                                userBeanNew.setPassword(userBean.getPassword());
                                userBeanNew.setNickName(data.has("nickname") ? data.getString("nickname") : "");
                                userBeanNew.setImg(data.has("img") ? data.getString("img") : "");
                                ((IPSCApplication) getApplication()).saveUserInfo(new Gson().toJson(userBeanNew));
                            } else {
                                ((IPSCApplication) getApplication()).removeUserInfo();
                            }

                        } else {
                            ((IPSCApplication) getApplication()).removeUserInfo();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onRegisterSuccess(String result) {

                }
            });
        }
    }


    @Subscribe
    public void onEvent(final MainSwitchEvent event){

        if(event!=null){
           index = event.getPosition();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(index!=-1){
            selectedView(index);
            index=-1;
        }
    }
}
