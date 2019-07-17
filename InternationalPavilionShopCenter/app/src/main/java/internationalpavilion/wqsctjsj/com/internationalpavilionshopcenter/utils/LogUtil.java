package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;


import android.util.Log;

/**
 * 日志工具类
 */
public final class LogUtil {

    /**
     * 类名
     */
    static String className;
    /**
     * 方法名
     */
    static String methodName;
    /**
     * 行数
     */
    static int lineNumber;
    /**
     * 调试模式,控制是否输出内容细节
     */
    static boolean isDebug = false;
    static final int LEVEL_V = 0x1;
    static final int LEVEL_D = 0x2;
    static final int LEVEL_I = 0x3;
    static final int LEVEL_W = 0x4;
    static final int LEVEL_E = 0x5;
    private static boolean IS_SHOW_LOG = false;  // all control
    private static boolean IS_SHOW_V = true;    //verbose level
    private static boolean IS_SHOW_D = true;    //debug level
    private static boolean IS_SHOW_I = true;    //info level
    private static boolean IS_SHOW_W = true;    //warning level
    private static boolean IS_SHOW_E = true;    //error level

    public static void v(String tag, String msg) {
        if (IS_SHOW_LOG && IS_SHOW_V) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (IS_SHOW_LOG && IS_SHOW_I) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_SHOW_LOG && IS_SHOW_W) {
            Log.w(tag, msg);
        }
    }


    public static void d(String tag, String string) {
        if (IS_SHOW_LOG && IS_SHOW_D) {
            if (!isDebug) {
                Log.d(tag, string);
            } else {
                trackDetail(LEVEL_D, tag, string, null);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (IS_SHOW_LOG && IS_SHOW_E) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Exception e) {
        trackDetail(LEVEL_E, tag, msg, e);
    }

    /**
     * 输出详细内容：类名、方法名、行数，方便直接定位
     *
     * @param level
     * @param tag
     * @param string
     * @param e
     */
    private static void trackDetail(int level, String tag, String string, Exception e) {
        String newString = "";
        // 输出全部内容
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
            int indexEnd = newString.length() < 3000 * (i + 1) ? newString.length() : 3000 * (i + 1);
            int indexStart = 3000 * i;

            StackTraceElement[] sElements = new Throwable().getStackTrace();
            className = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
            switch (level) {
                case LEVEL_V:// v
                    Log.v(tag, string, e);
                    break;
                case LEVEL_D:// d
                    Log.d(tag, methodName + "(" + className + ":" + lineNumber + ")---" + (i + 1) + "--->" +
                            newString.substring(indexStart, indexEnd));
                    break;
                case LEVEL_I:// i
                    Log.i(tag, string, e);
                    break;
                case LEVEL_W:// w
                    Log.w(tag, string, e);
                    break;
                case LEVEL_E:// e
                    Log.e(tag, methodName + "(" + className + ":" + lineNumber + ")" + newString.substring(indexStart, indexEnd), e);
                    break;
            }

        }
    }
}