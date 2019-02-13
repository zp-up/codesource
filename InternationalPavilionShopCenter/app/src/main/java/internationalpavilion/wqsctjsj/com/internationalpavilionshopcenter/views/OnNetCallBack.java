package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/14.
 */

public interface OnNetCallBack {
    void onStarted();
    void onFinished();
    void onError(String error);
    void onGetCodeSuccess(String result);
    void onLoginSuccess(String result);
    void onRegisterSuccess(String result);
}
