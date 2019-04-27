package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2019/1/8.
 */

public interface OnOrderDealCallBack /*extends OnAfterSaleOrderCallBack*/ {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onBuyAgainCallBack(String result);

    void onCancelOrderCallBack(String result);

    void onBackMoneyOnlyCallBack(String result);
    void onConfirmOrderCallBack(String result);
//    void onRequestAfterSale(String result);// 申请售后
//    void onSetExpressInfo(String result);// 填写退货快递信息
}
