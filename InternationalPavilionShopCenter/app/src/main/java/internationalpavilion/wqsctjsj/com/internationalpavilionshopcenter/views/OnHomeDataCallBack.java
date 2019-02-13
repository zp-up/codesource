package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/14.
 */

public interface OnHomeDataCallBack {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onGetBannerData(String result);

    void onLimitTimeGoodsLoaded(String result);

    void onHotSaleGoodsLoaded(String result);

    void onBondedGoodsLoaded(String result);

    void onOverseasGoodsLoaded(String result);

    void onWishGoodsLoaded(String result);

    void onPopularityGoodsLoaded(String result);
}
