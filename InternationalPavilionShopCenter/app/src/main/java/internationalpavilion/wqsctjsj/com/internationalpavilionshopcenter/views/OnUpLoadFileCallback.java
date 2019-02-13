package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/19.
 */

public interface OnUpLoadFileCallback {
    void onStarted();
    void onFinished();
    void onError(String error);
    void onLoadFileSuccess(String result);
    void onModifyUserHeadSuccess(String result);
    void onModifyUserNickName(String result);
}
