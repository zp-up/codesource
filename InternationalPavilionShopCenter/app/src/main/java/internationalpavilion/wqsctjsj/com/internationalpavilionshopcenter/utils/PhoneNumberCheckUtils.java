package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Pattern;


/**
 * Created by wuqaing on 2018/12/12.
 */

public class PhoneNumberCheckUtils {

    public static boolean isRealPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        if (phoneNumber.length() != 11) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$";
        return phoneNumber.matches(regex);
    }
}
