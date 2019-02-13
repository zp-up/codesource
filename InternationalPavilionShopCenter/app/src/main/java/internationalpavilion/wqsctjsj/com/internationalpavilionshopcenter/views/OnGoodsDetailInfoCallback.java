package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2018/12/21.
 */

public interface OnGoodsDetailInfoCallback {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onGoodsInfoLoaded(String result);

    void onGoodsEvaluateLoaded(String result);

    void onGoodsAddedToCart(String result);

    void onGoodsAddedToCollection(int type, String msg);
}
