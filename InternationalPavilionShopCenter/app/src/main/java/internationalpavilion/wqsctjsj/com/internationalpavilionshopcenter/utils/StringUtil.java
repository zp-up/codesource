package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;

import java.io.IOException;
import java.io.InputStream;

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
}
