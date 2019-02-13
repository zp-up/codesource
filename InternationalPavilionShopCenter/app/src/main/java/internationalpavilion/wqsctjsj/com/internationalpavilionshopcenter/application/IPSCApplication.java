package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.TokenEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.UserOptionImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UserOptionInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

/**
 * Created by wuqaing on 2018/12/14.
 */

public class IPSCApplication extends Application implements OnNetCallBack{
    public static String accessToken = "";
    public static int id = -1;
    private String token;
    private UserOptionInterface userOptionPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        accessToken = getSPAccessToken();
        x.Ext.init(this);
        getTokenFromNet();
    }

    private String getSPAccessToken() {
        SharedPreferences sp = getSharedPreferences("Token", MODE_PRIVATE);
        String accessToken = sp.getString("token", "");
        return accessToken;
    }

    private void saveSPAccessToken(String accessToken) {
        if (accessToken == null) {
            return;
        }
        SharedPreferences sp = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", accessToken);
        editor.commit();
    }

    public void setAccessToken(String accessToken) {
        IPSCApplication.accessToken = accessToken;
        IPSCApplication.this.saveSPAccessToken(accessToken);
    }
    private void getTokenFromNet(){
        if (userOptionPresenter == null){
            userOptionPresenter = new UserOptionImp();
        }
        RequestParams params = new RequestParams(MainUrls.getAccessTokenUrl);
        params.addBodyParameter("type","app");
        params.addBodyParameter("name","gjg16756533");
        params.addBodyParameter("pasw","12348787987987");
        userOptionPresenter.doLogin(params,this);
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onFinished() {

    }
    public void saveUserInfo(String userJson){
        if (userJson == null){
            return;
        }
        SharedPreferences sharedPreferences  = getSharedPreferences("User",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userInfo",userJson);
        editor.commit();
    }
    public UserBean getUserInfo(){
        SharedPreferences sharedPreferences  = getSharedPreferences("User",MODE_PRIVATE);
        String userJson = sharedPreferences.getString("userInfo","");
        UserBean userBean = null;
        try {
            userBean = new Gson().fromJson(userJson,UserBean.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userBean;
    }
    public void removeUserInfo(){
        SharedPreferences sharedPreferences  = getSharedPreferences("User",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    @Override
    public void onError(String error) {
        Log.e("TAG","error:"+error);
    }

    @Override
    public void onGetCodeSuccess(String result) {

    }

    @Override
    public void onLoginSuccess(String result) {
        if (result != null){
            Log.e("TAG","登录结果:"+result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0 && jsonObject.has("data") && jsonObject.getJSONObject("data").has("token")){
                    String token = jsonObject.getJSONObject("data").getString("token");
                    int id = jsonObject.getJSONObject("data").getInt("userid");
                    IPSCApplication.this.setAccessToken(token);
                    IPSCApplication.id = id;
                    TokenEvent tokenEvent = new TokenEvent();
                    tokenEvent.code = 0;
                    EventBus.getDefault().post(tokenEvent);
                }else {
                    getTokenFromNet();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRegisterSuccess(String result) {

    }
}
