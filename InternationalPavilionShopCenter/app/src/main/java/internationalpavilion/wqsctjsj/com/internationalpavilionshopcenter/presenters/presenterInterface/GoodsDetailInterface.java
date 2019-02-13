package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnGoodsDetailInfoCallback;

/**
 * Created by wuqaing on 2018/12/21.
 */

public interface GoodsDetailInterface {
    void getGoodsBaseInfo(RequestParams params, OnGoodsDetailInfoCallback callback);
    void getGoodsEvaluate(RequestParams params, OnGoodsDetailInfoCallback callback);
    void addGoodsToCart(RequestParams params,OnGoodsDetailInfoCallback callback);
    void addGoodsToCollection(RequestParams params,OnGoodsDetailInfoCallback callback,int type);
    void onDestroyed();
}
