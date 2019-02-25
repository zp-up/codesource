package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.ConfirmOrderInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnConfirmDataCallback;

/**
 * Created by wuqaing on 2019/2/20.
 */

public class ConfirmOrderImp implements ConfirmOrderInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getOrderInfo(RequestParams params, final OnConfirmDataCallback callback) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();

        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error.toString());
            }

            @Override
            public void onSuccess(String result) {
                callback.onOrderInfoLoaded(result);
            }
        });
    }

    @Override
    public void getWalletData(RequestParams params, final OnConfirmDataCallback callback) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();

        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error.toString());
            }

            @Override
            public void onSuccess(String result) {
                callback.onWalletDataLoaded(result);
            }
        });
    }

    @Override
    public void setWalletMoneyToOrder(RequestParams params, final OnConfirmDataCallback callback) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();

        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error.toString());
            }

            @Override
            public void onSuccess(String result) {
                callback.onWalletSetSuccessed(result);
            }
        });
    }

    @Override
    public void checkPayInfo(RequestParams params, final OnConfirmDataCallback callback) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();

        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error.toString());
            }

            @Override
            public void onSuccess(String result) {
                callback.onCheckInfoCallBack(result);
            }
        });
    }
}
