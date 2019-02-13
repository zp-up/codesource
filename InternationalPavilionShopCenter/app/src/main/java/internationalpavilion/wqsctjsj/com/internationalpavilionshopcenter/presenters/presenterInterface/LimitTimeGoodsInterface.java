package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnLimitTimeGoodsCallback;

/**
 * Created by wuqaing on 2018/12/20.
 */

public interface LimitTimeGoodsInterface {
    void getLimitTimeGoodsList(RequestParams params, OnLimitTimeGoodsCallback callBack);
    void onDestroyed();
}
