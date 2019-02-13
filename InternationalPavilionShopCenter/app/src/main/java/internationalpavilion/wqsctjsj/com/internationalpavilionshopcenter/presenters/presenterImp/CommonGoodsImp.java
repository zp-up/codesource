package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

/**
 * Created by wuqaing on 2019/1/8.
 */

public class CommonGoodsImp implements CommonDataInterface {
    private NetRequestInterface netRequest;


    @Override
    public void getCommonGoodsData(RequestParams params, final OnCommonGoodsCallBack callBack) {
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
                callBack.onCommonGoodsCallBack(result);
            }
        });
    }

    @Override
    public void onDestroyed() {
        if (netRequest != null){
            netRequest = null;
            System.gc();
        }
    }
}
