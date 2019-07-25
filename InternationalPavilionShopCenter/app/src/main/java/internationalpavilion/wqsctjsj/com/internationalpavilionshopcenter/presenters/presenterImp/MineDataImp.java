package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.MineDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnMineDataCallBack;

/**
 * Created by gwx on 2019/07/25.
 */

public class MineDataImp implements MineDataInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getUserOrderData(RequestParams params, final OnMineDataCallBack callBack) {
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
                try {
                    callBack.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    callBack.onGetUserOrderData(result);
                }
            }
        });
    }

    @Override
    public void onDestroyed() {
        if (netRequest != null) {
            netRequest = null;
            System.gc();
        }
    }
}
