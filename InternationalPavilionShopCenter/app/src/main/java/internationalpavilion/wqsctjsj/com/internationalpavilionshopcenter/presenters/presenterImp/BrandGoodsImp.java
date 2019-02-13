package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.BrandGoodsInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBrandGoodsLoaded;

/**
 * Created by wuqaing on 2019/1/16.
 */

public class BrandGoodsImp implements BrandGoodsInterface{
    private NetRequestInterface netRequest;
    @Override
    public void getBrandGoodsList(RequestParams params, final OnBrandGoodsLoaded callback) {
        if (netRequest == null){
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
                callback.onBrandGoodsLoaded(result);
            }
        });
    }

    @Override
    public void getBrandInformation(RequestParams params, final OnBrandGoodsLoaded callback) {
        if (netRequest == null){
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
                callback.onBrandInfoLoaded(result);
            }
        });
    }
}
