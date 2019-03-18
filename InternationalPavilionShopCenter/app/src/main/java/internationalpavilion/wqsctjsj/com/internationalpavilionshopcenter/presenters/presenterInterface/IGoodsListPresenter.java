package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.NetCallback;

/**
 * Author:chris - jason
 * Date:2019/2/27.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface
 */

public interface IGoodsListPresenter {

    //查询新品列表
    void getNewlyGoodsList(RequestParams params , NetCallback callBack);

    //查询折扣商品列表
    void getDiscountGoodsList(RequestParams params, NetCallback callback);
}