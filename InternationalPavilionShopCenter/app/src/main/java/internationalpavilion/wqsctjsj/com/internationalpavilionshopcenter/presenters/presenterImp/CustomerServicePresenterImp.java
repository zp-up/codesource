package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CustomerServicePresenter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.NetCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

/**
 * Author:chris - jason
 * Date:2019/2/24.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp
 */

public class CustomerServicePresenterImp implements CustomerServicePresenter {

    private NetRequestInterface netRequest;


    @Override
    public void getInitMessage(RequestParams params, final NetCallback callBack) {
        if(netRequest==null){
            netRequest = new NetRequestImp();
        }

        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onError(Throwable error) {
                if(callBack!=null){
                    callBack.onFailed(error);
                }
            }

            @Override
            public void onSuccess(String result) {
                if(callBack!=null){
                    callBack.onSuccess(result);
                }
            }
        });
    }

    @Override
    public void sendMessage(RequestParams params, final NetCallback callback) {
        if(netRequest==null){
            netRequest = new NetRequestImp();
        }

        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onError(Throwable error) {
                if(callback!=null){
                    callback.onFailed(error);
                }
            }

            @Override
            public void onSuccess(String result) {
                if(callback!=null){
                    callback.onSuccess(result);
                }
            }
        });
    }

}
