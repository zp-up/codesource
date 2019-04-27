package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/14.
 */

public interface OnNetCallBack {
    void onStarted();
    void onFinished();
    void onError(String error);
    void onGetCodeSuccess(String result);
    void onCommonSuccess(String result, int type);// 通用接口
    void onLoginSuccess(String result);
    void onRegisterSuccess(String result);
}
