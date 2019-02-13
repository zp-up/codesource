package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.BondedGoodsInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBondedGoodsCallBack;

/**
 * Created by wuqaing on 2018/12/26.
 */

public class BondedGoodsImp implements BondedGoodsInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getBondedGoods(RequestParams params, final OnBondedGoodsCallBack callBack) {
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
                callBack.onBondedGoodsLoaded(result);
            }
        });
    }

    @Override
    public void getBrandData(RequestParams params, final OnBondedGoodsCallBack callBack) {
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
                try {
                    callBack.onError(error.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callBack.onBrandDataLoaded(result);
            }
        });
    }

    @Override
    public void getClassData(RequestParams params, final OnBondedGoodsCallBack callBack) {
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
                try {
                    callBack.onError(error.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callBack.onClassDataLoaded(result);
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
