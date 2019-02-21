package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnGroupProductListCallback;

/**
 * Author:chris - jason
 * Date:2019/2/19.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface
 */
//拼团商品列表 p
public interface IGroupProductListPresenter {

    //查询拼团商品列表
    void pullGroupProductList(RequestParams params, OnGroupProductListCallback callback);

}
