package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBondedGoodsCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnOrderDealCallBack;

/**
 * Created by wuqaing on 2018/12/26.
 */

public interface OrderDealInterface {
    void buyAgain(RequestParams params, OnOrderDealCallBack callBack);
    void cancelOrder(RequestParams params,OnOrderDealCallBack callBack);
    void backMoneyOnly(RequestParams params,OnOrderDealCallBack callBack);
    void confirmReceived(RequestParams params,OnOrderDealCallBack callBack);
    void onDestroyed();
}
