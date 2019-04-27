package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wangm on 2019/4/17.
 */

public interface OnAuthenticationListCallback {
    void onStarted();
    void onFinished();
    void onError(String error);
    void onSuccess(String result);
}
