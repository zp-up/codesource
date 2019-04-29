package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 字符串处理工具类
 */
public class StringUtil {


    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    public static String listToString(List<String> list) {
        if (list.size() <= 0) {
            return "";
        }
        String[] strArray = list.toArray(new String[list.size()]);
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : strArray) {
            stringBuffer.append(s);
            stringBuffer.append(",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }
}
