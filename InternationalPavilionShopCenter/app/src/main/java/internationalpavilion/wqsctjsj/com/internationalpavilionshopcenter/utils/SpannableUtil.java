package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

/**
 * Author:chris - jason
 * Date:2019/2/22.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils
 */

public class SpannableUtil {

    public static SpannableString setSizeSpan(String source, int start, int end, float proportion){

        SpannableString spannableString = new SpannableString(source);

        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(proportion);
        spannableString.setSpan(sizeSpan01, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;

    }
}
