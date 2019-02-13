package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2019/2/12.
 */

public interface OnSearchCallback {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onHistorySearchLoaded(String result);

    void onHotSearchLoaded(String result);
}
