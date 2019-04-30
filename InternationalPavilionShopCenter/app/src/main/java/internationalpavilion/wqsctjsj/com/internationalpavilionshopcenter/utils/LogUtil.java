package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;


import android.util.Log;

public final class LogUtil {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数
    static boolean isDebug = true;

    //把字符串转中文输出log.i日志
    public static void d(String tag, String string) {
        if (!isDebug) {
            return;
        }
        String newString = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\\') {
                if ((i < string.length() - 5) && ((string.charAt(i + 1) == 'u') || (string.charAt(i + 1) == 'U')))
                    try {
                        newString += (char) Integer.parseInt(string.substring(i + 2, i + 6), 16);
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        newString += string.charAt(i);
                    }
                else
                    newString += string.charAt(i);
            } else {
                newString += string.charAt(i);
            }
        }
        int num = (newString.length() % 3000 == 0) ? newString.length() / 3000 : (newString.length() / 3000 + 1);
        for (int i = 0; i < num; i++) {
            int idexEnd = newString.length() < 3000 * (i + 1) ? newString.length() : 3000 * (i + 1);
            int idexStart = 3000 * i;

            StackTraceElement[] sElements = new Throwable().getStackTrace();
            className = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
            Log.d(tag, methodName + "(" + className + ":" + lineNumber + ")---" + (i + 1) + "--->" +
                    newString.substring(idexStart, idexEnd));
        }
    }

    //把字符串转中文输出log.e日志
    public static void e(String tag, String string, Exception e) {
//        if (!isDebug) {
//            return;
//        }
        String newString = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\\') {
                if ((i < string.length() - 5) && ((string.charAt(i + 1) == 'u') || (string.charAt(i + 1) == 'U')))
                    try {
                        newString += (char) Integer.parseInt(string.substring(i + 2, i + 6), 16);
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        newString += string.charAt(i);
                    }
                else
                    newString += string.charAt(i);
            } else {
                newString += string.charAt(i);
            }
        }
        int num = (newString.length() % 3000 == 0) ? newString.length() / 3000 : (newString.length() / 3000 + 1);
        for (int i = 0; i < num; i++) {
            int idexEnd = newString.length() < 3000 * (i + 1) ? newString.length() : 3000 * (i + 1);
            int idexStart = 3000 * i;

            StackTraceElement[] sElements = new Throwable().getStackTrace();
            className = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
            Log.e(tag, methodName + "(" + className + ":" + lineNumber + ")" + newString.substring(idexStart, idexEnd), e);
        }
    }

}