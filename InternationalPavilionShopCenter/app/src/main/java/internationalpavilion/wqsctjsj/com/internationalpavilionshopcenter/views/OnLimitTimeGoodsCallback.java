package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/20.
 */

public interface OnLimitTimeGoodsCallback {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onLimitTimeGoodsLoaded(String result);
}
