package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAfterSaleOrderCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnOrderDealCallBack;

/**
 * Created by wuqaing on 2018/12/26.
 */

public interface OrderAfterSaleDealInterface {

    void requestAfterSale(RequestParams params, OnAfterSaleOrderCallBack callBack);
    void setExpressInfo(RequestParams params, OnAfterSaleOrderCallBack callBack);
    void getRefundGoodsAddressUrl(RequestParams params, OnAfterSaleOrderCallBack callBack); // 获取退货地址
    void onDestroyed();
}
