package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2019/1/16.
 */

public interface OnBrandGoodsLoaded {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onBrandGoodsLoaded(String result);

    void onBrandInfoLoaded(String result);
}
