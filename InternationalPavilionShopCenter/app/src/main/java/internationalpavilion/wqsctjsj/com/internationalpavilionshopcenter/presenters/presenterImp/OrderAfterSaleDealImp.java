package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderAfterSaleDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAfterSaleOrderCallBack;

/**
 * Created by wuqaing on 2018/12/26.
 */

public class OrderAfterSaleDealImp implements OrderAfterSaleDealInterface {
    private NetRequestInterface netRequest;


    @Override
    public void requestAfterSale(RequestParams params, final OnAfterSaleOrderCallBack callBack) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callBack.onStarted();
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                callBack.onError(error.toString());
            }

            @Override
            public void onSuccess(String result) {
                callBack.onRequestAfterSale(result);
            }
        });
    }

    @Override
    public void setExpressInfo(RequestParams params, final OnAfterSaleOrderCallBack callBack) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callBack.onStarted();
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                callBack.onError(error.toString());
            }

            @Override
            public void onSuccess(String result) {
                callBack.onSetExpressInfo(result);
            }
        });
    }

    @Override
    public void getRefundGoodsAddressUrl(RequestParams params, final OnAfterSaleOrderCallBack callBack) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callBack.onStarted();
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                callBack.onError(error.toString());
            }

            @Override
            public void onSuccess(String result) {
                callBack.onGetRefundGoodsAddressUrl(result);
            }
        });
    }

    @Override
    public void onDestroyed() {

    }
}
