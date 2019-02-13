package com.chrisjason.baseui.ui;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.chrisjason.baseui.R;
import com.chrisjason.baseui.receiver.NetworkChangeReceiver;
import com.chrisjason.baseui.util.NetUtil;
import com.chrisjason.baseui.widget.dialog.LoadingDialog;
import com.chrisjason.baseui.widget.view.DefaultRemindView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by mayikang on 17/2/6.
 */

/**
 * 封装的  AppcompatActivity 基类
 */
//extends AppCompatActivity 会抛出异常，找不到 TaskStackBuilder

public abstract class BaseAppcompatActivity extends AppCompatActivity {
    private Unbinder unbinder;
    /**页面根布局**/
    private View rootView;
    private ViewGroup root;

    /**无网络提示控件**/
    private DefaultRemindView defaultRemindView;

    /**无网络时弹出的 dialog**/
    private AlertDialog noNetworkRemindDialog;
    private AlertDialog.Builder noNetworkRemindBuilder;

    //网络状态发生变化时，是否需要提示重新加载页面
    private boolean isNeedNetRemind =true;

    /** 日志输出标志 **/


    /**判断 activity 是否处于激活状态**/
    private boolean isReady=false;

    /**加载进度对话框**/
    private LoadingDialog dialog;

    private  View decorView;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(initLayout());
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //注册网络变化监听
        NetworkChangeReceiver.setOnNetworkChangedListener(new NetworkChangeReceiver.OnNetworkChangedListener() {
            @Override
            public void onNetworkChanged(boolean isConnected, int type) {
                //无网络
                if(!isConnected){
                    showDefaultPage(1);
                } else {
                    dismissDefaultPage();
                }
            }
        });

        if(!NetUtil.isConnected(this)){
            showDefaultPage(1);
        }



    }

    //设置华为手机虚拟按键的问题
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            //|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            // |View.SYSTEM_UI_FLAG_FULLSCREEN;
            //|View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //|View.SYSTEM_UI_FLAG_IMMERSIVE
            //|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //隐藏导航栏
         //hideBottomUIMenu();
         super.setContentView(layoutResID);
         //decorView = getWindow().getDecorView();
         //显示导航栏
         //setViewListener();
        unbinder= ButterKnife.bind(this);
    }

    private void setViewListener() {
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.e("ChangeListener",visibility+"");
                if(visibility==2){
                    View mv = getWindow().getDecorView();
                    int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                    mv.setSystemUiVisibility(uiOptions);
                }
            }
        });
    }


    public abstract @LayoutRes
    int initLayout();

    @Override
    protected void onStart() {
        super.onStart();

        isReady=true;
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(NetUtil.isConnected(this)){
            dismissDefaultPage();

        }else {
            showDefaultPage(1);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {

        super.onStop();
        isReady=false;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        //取消注册 EventBus
        //EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    /**
     * 判断 activity 是否处于激活状态
     * @return
     */
    public boolean isActReady(){
        return isReady;
    }

    /**
     * 设置是否需要网络变化提示
     * @param b
     */
    public void setNeedNetRemind(boolean b){
        this.isNeedNetRemind =b;
    }

    /**
     * 显示缺省页面
     * 1：无网络 2：有网络时，加载数据失败
     * @param type
     */
    public void showDefaultPage(int type){

        if(isNeedNetRemind){
            //获取页面根布局
            rootView=  getWindow().getDecorView().findViewById(android.R.id.content);
            root= (ViewGroup) rootView;

            if(defaultRemindView==null){
                defaultRemindView=new DefaultRemindView(this);
                //设置点击重新加载数据的监听
                defaultRemindView.setOnReloadClickListerer(new DefaultRemindView.ReloadClickListener() {
                    @Override
                    public void reload() {
                        if (NetUtil.isConnected(BaseAppcompatActivity.this)){
                            rootView=  getWindow().getDecorView().findViewById(android.R.id.content);
                            root= (ViewGroup) rootView;

                            //循环遍历根布局下的所有子布局，隐藏其他布局，显示无网络提示
                            for (int i=0;i<root.getChildCount();i++){
                                if(root.getChildAt(i) instanceof DefaultRemindView){
                                    root.getChildAt(i).setVisibility(View.GONE);
                                }else {
                                    root.getChildAt(i).setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            Toast.makeText(BaseAppcompatActivity.this, "请打开网络后操作。", Toast.LENGTH_SHORT).show();
                        }

                        reloadData();
                    }

                    @Override
                    public void setNetwork() {
                        goToSetNetwork();
                    }
                });

                root.addView(defaultRemindView);
            }

            //重新获取一次根布局
            rootView=  getWindow().getDecorView().findViewById(android.R.id.content);
            root= (ViewGroup) rootView;

            //循环遍历根布局下的所有子布局，隐藏其他布局，显示无网络提示
            for (int i=0;i<root.getChildCount();i++){
                if(root.getChildAt(i) instanceof DefaultRemindView){
                    root.getChildAt(i).setVisibility(View.VISIBLE);
                }else {
                    root.getChildAt(i).setVisibility(View.GONE);
                }
            }

        }

    }

    /**
     * 关闭无网络提示
     */
    public void dismissDefaultPage(){

        if(isNeedNetRemind){
            //获取页面根布局
            rootView=  getWindow().getDecorView().findViewById(android.R.id.content);
            root= (ViewGroup) rootView;

            //循环遍历根布局下的所有子布局，显示其他布局，隐藏无网络提示
            for (int i=0;i<root.getChildCount();i++){
                if(root.getChildAt(i) instanceof DefaultRemindView){
                    root.getChildAt(i).setVisibility(View. GONE);
                }else {
                    root.getChildAt(i).setVisibility(View.VISIBLE);
                }
            }
        }


    }



    /**
     * 重新加载数据的方法必须重写
     */
    public  abstract void reloadData();

    /**
     * 去设置网络
     */
    public void goToSetNetwork(){
        NetUtil.openSetting(this);
    }

    /**
     * 显示加载dialog
     * @param str
     */
    public void showLoading(boolean b,String str){
        if(dialog==null){
            dialog=new LoadingDialog(this);
        }
        dialog.setCancelable(b);
        dialog.setLoadingStr(str);
        if(!dialog.isShowing() && isReady){
            dialog.show();
        }

    }

    /**
     * 关闭加载 dialog
     */
    public void dismissLoading(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }



}
