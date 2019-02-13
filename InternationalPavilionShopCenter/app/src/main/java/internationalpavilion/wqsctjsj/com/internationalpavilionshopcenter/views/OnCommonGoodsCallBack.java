package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2019/1/8.
 */

public interface OnCommonGoodsCallBack {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onCommonGoodsCallBack(String result);
}
