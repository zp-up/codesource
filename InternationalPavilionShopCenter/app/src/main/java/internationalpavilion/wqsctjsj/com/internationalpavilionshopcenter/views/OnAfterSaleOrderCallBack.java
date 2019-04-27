package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

public interface OnAfterSaleOrderCallBack {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onRequestAfterSale(String result);// 申请售后

    void onSetExpressInfo(String result);// 填写退货快递信息

    void onGetRefundGoodsAddressUrl(String result);// 获取退货地址

}
