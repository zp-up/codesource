package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wuqaing on 2018/4/8.
 */

public class ToastUtils {
    private static Toast toast;
    public static void show(Context context,String msg){
        if (toast == null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
            toast.show();
        }else {
            toast.setText(msg);
            toast.show();
        }
    }
}
