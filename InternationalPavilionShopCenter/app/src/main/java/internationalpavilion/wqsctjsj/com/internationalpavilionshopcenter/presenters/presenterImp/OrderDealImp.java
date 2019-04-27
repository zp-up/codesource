package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.OrderDealInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBondedGoodsCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnOrderDealCallBack;

/**
 * Created by wuqaing on 2018/12/26.
 */

public class OrderDealImp implements OrderDealInterface {
    private NetRequestInterface netRequest;

    @Override
    public void buyAgain(RequestParams params, final OnOrderDealCallBack callBack) {
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
                callBack.onBuyAgainCallBack(result);
            }
        });
    }

    @Override
    public void cancelOrder(RequestParams params, final OnOrderDealCallBack callBack) {
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
                callBack.onCancelOrderCallBack(result);
            }
        });
    }

    @Override
    public void backMoneyOnly(RequestParams params, final OnOrderDealCallBack callBack) {
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
                callBack.onBackMoneyOnlyCallBack(result);
            }
        });
    }

    @Override
    public void confirmReceived(RequestParams params, final OnOrderDealCallBack callBack) {
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
                callBack.onConfirmOrderCallBack(result);
            }
        });
    }

    @Override
    public void onDestroyed() {

    }
}
