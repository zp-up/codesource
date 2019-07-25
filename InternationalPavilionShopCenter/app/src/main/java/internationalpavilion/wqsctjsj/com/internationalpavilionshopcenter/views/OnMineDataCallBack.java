package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by gwx on 2019/07/25.
 */

public interface OnMineDataCallBack {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onGetUserOrderData(String result);

}
