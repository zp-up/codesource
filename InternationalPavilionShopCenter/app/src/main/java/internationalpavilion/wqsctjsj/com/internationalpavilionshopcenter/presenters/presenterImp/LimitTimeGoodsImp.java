package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.LimitTimeGoodsInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnLimitTimeGoodsCallback;

/**
 * Created by wuqaing on 2018/12/20.
 */

public class LimitTimeGoodsImp implements LimitTimeGoodsInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getLimitTimeGoodsList(RequestParams params, final OnLimitTimeGoodsCallback callBack) {
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
                callBack.onLimitTimeGoodsLoaded(result);
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
