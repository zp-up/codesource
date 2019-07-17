package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.UpLoadFileImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UpLoadFileInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.LogUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnUpLoadFileCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.popupwindow.ImageSelectPop;

public class RealNameAuthenticationActivity extends BaseAppcompatActivity implements TakePhoto.TakeResultListener, InvokeListener, OnUpLoadFileCallback {

    public static final String TAG = "[IPSC][RealNameAuthenticationActivity]";
    @BindView(R.id.ll_parent)
    LinearLayout llParent;


    private TakePhoto takePhoto;
    private File file;
    private InvokeParam invokeParam;
    private Bitmap bitmap;
    private String url = "";
    private UpLoadFileInterface upLoadFilePresenter;
    private int currentPicPosition = 1;
    @BindView(R.id.img_front)
    ImageView imgFront;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_realNname)
    TextView tvName;
    @BindView(R.id.tv_idNo)
    TextView tvID;
    @BindView(R.id.switch_mode)
    Switch switch_mode;

    private  int mAuthMode = AUTH_MODE_AUTO;
    private static final int AUTH_MODE_AUTO = 0x01;
    private static final int AUTH_MODE_MANUAL = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
        upLoadFilePresenter = new UpLoadFileImp();
        switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged() isChecked:" + isChecked);
                mAuthMode = isChecked ? AUTH_MODE_AUTO : AUTH_MODE_MANUAL;
                changeMode();
            }
        });
        changeMode();
//        switch_mode.setChecked(true);
    }
    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                RealNameAuthenticationActivity.this.finish();
                break;
        }
    }
    @OnClick({R.id.rl_add_front,R.id.rl_add_back})
    public void click(View view){
        /******************************************************/
        switch (mAuthMode){
            case AUTH_MODE_AUTO:
                break;
            case AUTH_MODE_MANUAL:
                verifyIdCardByInput();
                return;
        }
        /******************************************************/
        switch (view.getId()){
            case R.id.rl_add_front:
                currentPicPosition = 1;
                takePhoto();
                break;
            case R.id.rl_add_back:
                currentPicPosition = 2;
                takePhoto();
                break;
        }
    }
    @Override
    public int initLayout() {
        return R.layout.activity_real_name_authentication;
    }

    @Override
    public void reloadData() {

    }
    /**
     * 图片保存路径
     *
     * @return
     */
    private Uri getUri() {

        file = new File(Environment.getExternalStorageDirectory(), "/shoes/images/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
//            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
            TakePhotoImpl  takephotoImpl = new TakePhotoImpl(this, this);
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
            takephotoImpl.onEnableCompress(compressConfig, false);
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(takephotoImpl);
        }
        return takePhoto;
    }
    @Override
    public void takeSuccess(TResult result) {
        bitmap = BitmapFactory.decodeFile(result.getImage().getPath());
        if(currentPicPosition == 1){
            imgFront.setImageBitmap(bitmap);
        }else{
            imgBack.setImageBitmap(bitmap);
        }
        if (null != file) {
            UpUserImg();
        }
    }

    //上传图片
    private void UpUserImg() {
        // 注意图片资源必须在2M以内，否则会失败
        RequestParams params = new RequestParams(MainUrls.upLoadImageUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("file", file);
        params.setMultipart(true);
        upLoadFilePresenter.upLoadFile(params, this);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //处理运行时权限
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 页面跳转回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult() requestCode:" + requestCode + ",resultCode:" + resultCode + ",intent:" + data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStarted() {
        showLoading(false, "后台处理中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "出错:" + error);
        showFailDialog();
    }

    /**
     * 上传图片失败dialog
     */
    private void showFailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RealNameAuthenticationActivity.this);
        View view = LayoutInflater.from(RealNameAuthenticationActivity.this).inflate(R.layout.dialog_upload_fail,null);
        final AlertDialog alertDialog = builder.setView(view).create();
        alertDialog.setCancelable(false);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_re_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != file){
                    UpUserImg();
                }else{
                    Log.d("TAG","file = " + file);
                }
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }

    //文件上传成功
    @Override
    public void onLoadFileSuccess(String result) {

        if (result != null) {
            Log.e("TAG", "上传图片结果:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    url = data.getString("url");
                    String sha256 = data.has("sha256")?data.getString("sha256"):"";
                    ToastUtils.show(RealNameAuthenticationActivity.this, "上传图片成功");
                    verifyIdCard(sha256);

                } else {
                    ToastUtils.show(RealNameAuthenticationActivity.this, msg);
                }
            } catch (Exception e) {
                Log.e(TAG, "", e);
            }
        }
    }

    @Override
    public void onModifyUserHeadSuccess(String result) {
        if (result != null) {
            Log.e("TAG","图片识别结果:"+result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    if ("false".equals(jsonObject.getString("data"))) {
                        ToastUtils.show(RealNameAuthenticationActivity.this, "图片识别失败！");
                    } else if ("true".equals(jsonObject.getString("data"))) {
                        ToastUtils.show(RealNameAuthenticationActivity.this, "图片识别成功！");
                        finish();
                    }
                } else {
                    ToastUtils.show(RealNameAuthenticationActivity.this, msg);
                }
            } catch (Exception e) {
                Log.e(TAG, "onModifyUserHeadSuccess() ", e);
            }
        }

    }

    @Override
    public void onModifyUserNickName(String result) {
        if (result != null) {
            Log.e("TAG","图片识别结果:"+result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //TODO 获取 data.getString("返回字段") 设置到 TextView 上面
                } else {
                    ToastUtils.show(RealNameAuthenticationActivity.this, msg);
                }
            } catch (Exception e) {
                Log.e(TAG, "onModifyUserNickName() ", e);
            }
        }
    }

    @Override
    public void onVerifyIdCardByInput(String result) {
        if (result != null) {
            Log.e("TAG","身份证认证结果:"+result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                if (code == 0 && state == 0) {
                    if ("false".equals(jsonObject.getString("data"))) {
                        ToastUtils.show(RealNameAuthenticationActivity.this, "身份证认证失败！");
                    } else if ("true".equals(jsonObject.getString("data")) || jsonObject.getString("data").toString().isEmpty()) {
                        ToastUtils.show(RealNameAuthenticationActivity.this, "身份证认证成功！");
                        finish();
                    }
                } else {
                    ToastUtils.show(RealNameAuthenticationActivity.this, msg);
                }
            } catch (Exception e) {
                Log.e(TAG, "onVerifyIdCardByInput() ", e);
            }
        }
    }

    /**
     *
     * @param imageSHA256 图片资源的SHA256
     */
    private void verifyIdCard(String imageSHA256) {
        RequestParams params = new RequestParams(MainUrls.verifyIdCardUrl);
        if (currentPicPosition == 1) {
            params.addBodyParameter("type", "front");
        } else {
            params.addBodyParameter("type", "back");
        }
        params.addBodyParameter("sha256", imageSHA256);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        LogUtil.d(TAG, "verifyIdCard() params:" + params.toString());
        upLoadFilePresenter.modifyUserHead(params, this);
    }

    private void verifyIdCardByInput() {
        if (tvName.getText().toString().isEmpty() || tvID.getText().toString().isEmpty()) {
            Toast.makeText(this, "请填好数据！", Toast.LENGTH_LONG).show();
            return;
        }
        RequestParams params = new RequestParams(MainUrls.verifyIdCardByInputUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("status", "0");// 是否默认:0否,1是
        params.addBodyParameter("auto", "手动");// 是否手动:自动,手动
//        params.addBodyParameter("name", "朱觉烽");// 姓名
//        params.addBodyParameter("card", "450923199310173774");// 身份证号
        params.addBodyParameter("name", tvName.getText().toString());// 姓名
        params.addBodyParameter("card", tvID.getText().toString());// 身份证号
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            params.addBodyParameter("user", ((IPSCApplication) this.getApplication()).getUserInfo().getId() + "");
        }
        Log.d(TAG, "verifyIdCardByInput() params:" + params.toString());
        upLoadFilePresenter.verifyIdCardByInput(params, this);
    }

    private void changeMode() {
        switch (mAuthMode) {
            case AUTH_MODE_AUTO: // 自动识别
                tvName.setFocusable(false);
                tvName.setFocusableInTouchMode(false);
                tvName.setHint("由系统自动识别");
                tvID.setFocusable(false);
                tvID.setFocusableInTouchMode(false);
                tvID.setHint("由系统自动识别");
                break;
            case AUTH_MODE_MANUAL:// 可以输入
                tvName.setFocusable(true);
                tvName.setFocusableInTouchMode(true);
                tvName.setHint("请输入姓名");
                tvID.setFocusable(true);
                tvID.setFocusableInTouchMode(true);
                tvID.setHint("请输入证件号码");
                break;
        }
    }

    private void takePhoto() {
        //打开相机、裁剪参数
        CropOptions cropOptions = new CropOptions.Builder().
                setWithOwnCrop(false).setOutputX(800).setOutputY(800).create();
        getTakePhoto().onPickFromCaptureWithCrop(getUri(), cropOptions);
        /*                ImageSelectPop pop1 = new ImageSelectPop(this);
                pop1.setCancelAble(false);
                pop1.show(llParent, new ImageSelectPop.ItemClick() {
                    @Override
                    public void onClick(int type, double rentPrice, double buyPrice) {
                        switch (type) {
                            case 1:

                                break;
                            case 2:
                                //裁剪参数
                                CropOptions cropOptions = new CropOptions.Builder().
                                        setWithOwnCrop(false).setOutputX(800).setOutputY(800).create();
                                getTakePhoto().onPickFromCaptureWithCrop(getUri(), cropOptions);
                                break;
                            case 3:
                                //裁剪参数
                                CropOptions cropOptions1 = new CropOptions.Builder()
                                        .setWithOwnCrop(false).setOutputX(800).setOutputY(800).create();
                                getTakePhoto().onPickFromGalleryWithCrop(getUri(), cropOptions1);
                                break;
                        }
                    }
                });*/
    }
}
