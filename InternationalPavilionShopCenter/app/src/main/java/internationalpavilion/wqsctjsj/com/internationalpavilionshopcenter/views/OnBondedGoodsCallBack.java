package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/26.
 */

public interface OnBondedGoodsCallBack {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onBondedGoodsLoaded(String result);

    void onBrandDataLoaded(String result);

    void onClassDataLoaded(String result);
}
