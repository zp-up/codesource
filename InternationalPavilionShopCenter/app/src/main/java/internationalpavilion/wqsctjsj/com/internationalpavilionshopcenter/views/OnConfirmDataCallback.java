package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/19.
 */

public interface OnConfirmDataCallback {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onOrderInfoLoaded(String result);

    void onWalletDataLoaded(String result);

    void onWalletSetSuccessed(String result);

    void onCheckInfoCallBack(String result);
}
