package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CartOperationInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCartOperationCallBack;

/**
 * Created by wuqaing on 2019/2/9.
 */

public class CartOperationImp implements CartOperationInterface{
    private NetRequestInterface netRequest;

    @Override
    public void cartAdd(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onCommonDealCallBack(result);
                }
            }
        });
    }

    @Override
    public void cartSub(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onCommonDealCallBack(result);
                }
            }
        });
    }

    @Override
    public void cartGoodsChanged(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onCommonDealCallBack(result);
                }
            }
        });
    }

    @Override
    public void cartStoreChanged(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onCommonDealCallBack(result);
                }
            }
        });
    }

    @Override
    public void cartAllSelected(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onCommonDealCallBack(result);
                }
            }
        });
    }

    @Override
    public void cartDelete(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onDeleteCallBack(result);
                }
            }
        });
    }

    @Override
    public void cartAllChecked(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onCommonDealCallBack(result);
                }
            }
        });
    }

    @Override
    public void cartSubmit(RequestParams params, final OnCartOperationCallBack callBack) {
        if (netRequest == null){
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
                if (result != null){
                    callBack.onSubmitCallBack(result);
                }
            }
        });
    }

    @Override
    public void onDestroyed() {
        if (netRequest != null){
            netRequest = null;
        }
    }
}
