package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.SearchOperationInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnSearchCallback;

/**
 * Created by wuqaing on 2019/2/12.
 */

public class SearchOperationImp implements SearchOperationInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getHistorySearch(RequestParams params, final OnSearchCallback callback) {
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
                callback.onHistorySearchLoaded(result);
            }
        });
    }

    @Override
    public void getHotSearch(RequestParams params, final OnSearchCallback callback) {
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
                callback.onHotSearchLoaded(result);
            }
        });
    }
}
