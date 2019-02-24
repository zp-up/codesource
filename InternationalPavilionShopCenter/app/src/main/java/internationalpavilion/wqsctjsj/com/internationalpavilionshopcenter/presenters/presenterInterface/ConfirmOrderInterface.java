package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnConfirmDataCallback;

/**
 * Created by wuqaing on 2019/2/20.
 */

public interface ConfirmOrderInterface {
    void getOrderInfo(RequestParams params, OnConfirmDataCallback callback);

    void getWalletData(RequestParams params, OnConfirmDataCallback callback);

    void setWalletMoneyToOrder(RequestParams params, OnConfirmDataCallback callback);

    void checkPayInfo(RequestParams params, OnConfirmDataCallback callback);
}
