package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2019/1/8.
 */

public interface OnCartOperationCallBack {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onCommonDealCallBack(String result);

    void onDeleteCallBack(String result);

    void onSubmitCallBack(String result);
}
