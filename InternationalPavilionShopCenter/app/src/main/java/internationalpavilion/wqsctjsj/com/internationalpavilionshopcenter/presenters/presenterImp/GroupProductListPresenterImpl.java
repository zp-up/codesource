package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.IGroupProductListPresenter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnGroupProductListCallback;

/**
 * Author:chris - jason
 * Date:2019/2/19.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp
 */

public class GroupProductListPresenterImpl implements IGroupProductListPresenter {

    private NetRequestInterface netRequest;

    @Override
    public void pullGroupProductList(RequestParams params, final OnGroupProductListCallback callback) {
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
                    callback.onError(error);
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
