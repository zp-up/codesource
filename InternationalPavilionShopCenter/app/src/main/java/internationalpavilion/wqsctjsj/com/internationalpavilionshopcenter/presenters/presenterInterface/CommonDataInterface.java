package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

/**
 * Created by wuqaing on 2019/1/8.
 */

public interface CommonDataInterface {
    void getCommonGoodsData(RequestParams params, OnCommonGoodsCallBack callBack);
    void onDestroyed();
}
