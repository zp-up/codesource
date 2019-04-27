package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.TokenEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.UserOptionImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UserOptionInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.CrashHandler;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

/**
 * Created by wuqaing on 2018/12/14.
 */

public class IPSCApplication extends Application implements OnNetCallBack{

    private static final String TAG = "[IPSC][IPSCApplication]";
    public static String accessToken = "";
    public static int id = -1;
    private String token;
    private UserOptionInterface userOptionPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        //统一java层异常捕获
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance(this));
        accessToken = getSPAccessToken();
        x.Ext.init(this);
        getTokenFromNet();

        File ca=new File(Environment.getExternalStorageDirectory().getPath()+"/"+"myImgage");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                // max width, max height，即保存的每个缓存文件的最大宽高
                .memoryCacheExtraOptions(330, 700)
                // 线程池内加载的数量
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(10 * 1024 * 1024)
                .diskCacheSize(40 * 1024 * 1024)
                // 将保存的时候的URI名称用MD5 加密
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheFileCount(30)// 可以缓存的文件数量
                // 自定义缓存路径
                .diskCache(new UnlimitedDiskCache(ca))
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(new BaseImageDownloader(this, 5*1000, 30*1000))
                //.writeDebugLogs()
                .build(); //开始构建

        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
        params.addBodyParameter("name","3453454435");
        params.addBodyParameter("pasw","345345345");
        userOptionPresenter.doLogin(params,this);
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onFinished() {

    }
    public void saveUserInfo(String userJson){
        Log.d(TAG, "saveUserInfo() userJson:" + userJson);
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
        Log.d(TAG, "getUserInfo() userJson:" + userJson.toString());
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
        Log.e(TAG,"error:"+error);
    }

    @Override
    public void onGetCodeSuccess(String result) {

    }

    @Override
    public void onCommonSuccess(String result, int type) {

    }

    public DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        return options;
    }

    @Override
    public void onLoginSuccess(String result) {
        if (result != null){
            Log.e(TAG,"登录结果:"+result);
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
