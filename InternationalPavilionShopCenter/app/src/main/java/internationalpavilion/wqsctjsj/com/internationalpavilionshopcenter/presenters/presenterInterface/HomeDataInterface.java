package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnHomeDataCallBack;

/**
 * Created by wuqaing on 2018/12/17.
 */

public interface HomeDataInterface {
    void getBannerData(RequestParams params, OnHomeDataCallBack callBack);

    void getLimitTimeGoodsList(RequestParams params, OnHomeDataCallBack callBack);

    void getHotSaleGoodsList(RequestParams params, OnHomeDataCallBack callBack);

    void getBondedGoodsList(RequestParams params, OnHomeDataCallBack callBack);

    void getOverseasGoodsList(RequestParams params, OnHomeDataCallBack callBack);

    void getWishGoodsList(RequestParams params, OnHomeDataCallBack callBack);

    void getPopularityGoodsList(RequestParams params, OnHomeDataCallBack callBack);

    void onDestroyed();
}
