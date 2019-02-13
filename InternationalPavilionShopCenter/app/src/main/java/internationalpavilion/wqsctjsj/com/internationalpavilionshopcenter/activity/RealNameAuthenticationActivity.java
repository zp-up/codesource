package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
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
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnUpLoadFileCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.popupwindow.ImageSelectPop;

public class RealNameAuthenticationActivity extends BaseAppcompatActivity implements TakePhoto.TakeResultListener, InvokeListener, OnUpLoadFileCallback {
    @BindView(R.id.ll_parent)
    LinearLayout llParent;


    private TakePhoto takePhoto;
    private File file;
    private InvokeParam invokeParam;
    private Bitmap bitmap;
    private String url = "";
    private UpLoadFileInterface upLoadFilePresenter;
    private int currentPicPosition = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
        upLoadFilePresenter = new UpLoadFileImp();
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
        switch (view.getId()){
            case R.id.rl_add_front:
                currentPicPosition = 1;
                ImageSelectPop pop = new ImageSelectPop(this);
                pop.setCancelAble(false);
                pop.show(llParent, new ImageSelectPop.ItemClick() {
                    @Override
                    public void onClick(int type, double rentPrice, double buyPrice) {
                        switch (type) {
                            case 1:

                                break;
                            case 2:
                                //裁剪参数
                                CropOptions cropOptions = new CropOptions.Builder().
                                        setWithOwnCrop(false).create();
                                getTakePhoto().onPickFromCaptureWithCrop(getUri(), cropOptions);
                                break;
                            case 3:
                                //裁剪参数
                                CropOptions cropOptions1 = new CropOptions.Builder()
                                        .setWithOwnCrop(false).create();
                                getTakePhoto().onPickFromGalleryWithCrop(getUri(), cropOptions1);
                                break;
                        }
                    }
                });
                break;
            case R.id.rl_add_back:
                currentPicPosition = 2;
                ImageSelectPop pop1 = new ImageSelectPop(this);
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
                                        setWithOwnCrop(false).create();
                                getTakePhoto().onPickFromCaptureWithCrop(getUri(), cropOptions);
                                break;
                            case 3:
                                //裁剪参数
                                CropOptions cropOptions1 = new CropOptions.Builder()
                                        .setWithOwnCrop(false).create();
                                getTakePhoto().onPickFromGalleryWithCrop(getUri(), cropOptions1);
                                break;
                        }
                    }
                });
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
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
    @Override
    public void takeSuccess(TResult result) {
        bitmap = BitmapFactory.decodeFile(result.getImage().getPath());
        if (null != file) {
            UpUserImg();
        }
    }

    //上传图片
    private void UpUserImg() {
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
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStarted() {
        showLoading(false, "上传图片中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("TAG", "出错:" + error);
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
                    RequestParams params = new RequestParams(MainUrls.verifyIdCardUrl);
                    if (currentPicPosition == 1){
                        params.addBodyParameter("type","front");
                    }else {
                        params.addBodyParameter("type","back");
                    }
                    params.addBodyParameter("sha256",sha256);
                    params.addBodyParameter("access_token",IPSCApplication.accessToken);
                    upLoadFilePresenter.modifyUserHead(params,this);
                } else {
                    ToastUtils.show(RealNameAuthenticationActivity.this, msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onModifyUserHeadSuccess(String result) {
        Log.e("TAG","图片识别结果:"+result);

    }

    @Override
    public void onModifyUserNickName(String result) {

    }
}
