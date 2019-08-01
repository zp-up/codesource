package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;

import java.io.File;
import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.CODE.open_clip;


/**
 * Author:chris - jason
 * Date:2019-07-19.
 * Package:com.iflow.dfs.utils
 */
public class AlbumUtil {

    private AlbumUtil() {

    }

    private static class Holder {
        private static final AlbumUtil instance = new AlbumUtil();
    }

    public static AlbumUtil getInstance() {
        return Holder.instance;
    }

    //打开相机
    public void openCamera(final Context context, final OnCameraListener listener) {
        Album.camera(context) // 相机功能。
                .image() // 拍照。
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, String result) {
                        File file = new File(result);
                        //保存图片后发送广播通知更新数据库
                        Uri uri = Uri.fromFile(file);
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        if (listener != null) {
                            listener.onCamera(result);
                        }
                    }
                })
                .start();
    }

    //打开相册
    public void openPhotoAlbum(Context context,int count,final onPhotoListener listener){
        Album.image(context) // 选择图片。
                .multipleChoice()
                .widget(getWidget(context,"相册"))
                .camera(false)// 是否有拍照功能。
                .columnCount(3)// 相册展示列数，默认是2列。
                .selectCount(count)// 最多选择几张图片。
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, ArrayList<AlbumFile> result) {
                        if (listener != null) {
                            listener.onAlbum(result);
                        }
                    }
                })
                .start();
    }

    //打开裁剪
    public void openClip(Activity activity, ArrayList<String> imagePathList) {
        Durban.with(activity)
                // 裁剪界面的标题。
                .title("头像裁剪")
                .statusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                .toolBarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                .navigationBarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                // 图片路径list或者数组。
                .inputImagePaths(imagePathList)
                // 图片输出文件夹路径。
                .outputDirectory(StorageUtils.getImageDir()
                        .getPath())
                // 裁剪图片输出的最大宽高。
                .maxWidthHeight(800, 800)
                // 裁剪时的宽高比。
                .aspectRatio(1, 1)
                // 图片压缩格式：JPEG、PNG。
                .compressFormat(Durban.COMPRESS_PNG)
                // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                // 30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
                .compressQuality(100)
                // 裁剪时的手势支持：ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_SCALE)
                .controller(Controller.newBuilder()
                        .enable(false) // 是否开启控制面板。
                        .rotation(true) // 是否有旋转按钮。
                        .rotationTitle(true) // 旋转控制按钮上面的标题。
                        .scale(true) // 是否有缩放按钮。
                        .scaleTitle(true) // 缩放控制按钮上面的标题。
                        .build()) // 创建控制面板配置。
                .requestCode(open_clip)
                .start();
    }




    public interface OnCameraListener {
        void onCamera(String result);
    }

    public interface  onPhotoListener{
        void onAlbum(ArrayList<AlbumFile> result);
    }


    public Widget getWidget(Context context,String title) {
        int color      = Color.parseColor("#f9f9f9");
        int checkColor = ContextCompat.getColor(context, R.color.colorPrimary);

        // 他们调用的方法和传的参数都是一致的：
        Widget build = Widget.newLightBuilder(context)
                .title(title) // 标题。
                .statusBarColor(color) // 状态栏颜色。
                .toolBarColor(color) // Toolbar颜色。
                .navigationBarColor(color) // Android5.0+的虚拟导航栏颜色。
                .mediaItemCheckSelector(checkColor, checkColor) // 图片或者视频选择框的选择器。
                .bucketItemCheckSelector(checkColor, checkColor) // 切换文件夹时文件夹选择框的选择器。
                //                .buttonStyle( // 用来配置当没有发现图片/视频时的拍照按钮和录视频按钮的风格。
                //                        Widget.ButtonStyle.newLightBuilder(ActControl.getTop()) // 同Widget的Builder模式。
                //                                .setButtonSelector(nonColor, nonColor) // 按钮的选择器。
                //                                .build())
                .build();
        return build;
    }


}
