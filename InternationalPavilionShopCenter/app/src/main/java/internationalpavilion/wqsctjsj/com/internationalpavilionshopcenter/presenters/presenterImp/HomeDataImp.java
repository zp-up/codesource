package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.HomeDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnHomeDataCallBack;

/**
 * Created by wuqaing on 2018/12/17.
 */

public class HomeDataImp implements HomeDataInterface{
    private NetRequestInterface netRequest;
    @Override
    public void getBannerData(RequestParams params, final OnHomeDataCallBack callBack) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null){
                    callBack.onGetBannerData(result);
                }
            }
        });
    }

    @Override
    public void getLimitTimeGoodsList(RequestParams params, final OnHomeDataCallBack callBack) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null){
                    callBack.onLimitTimeGoodsLoaded(result);
                }
            }
        });
    }

    @Override
    public void getHotSaleGoodsList(RequestParams params, final OnHomeDataCallBack callBack) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null){
                    callBack.onHotSaleGoodsLoaded(result);
                }
            }
        });
    }

    @Override
    public void getBondedGoodsList(RequestParams params, final OnHomeDataCallBack callBack) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null){
                    callBack.onBondedGoodsLoaded(result);
                }
            }
        });
    }

    @Override
    public void getOverseasGoodsList(RequestParams params, final OnHomeDataCallBack callBack) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null){
                    callBack.onOverseasGoodsLoaded(result);
                }
            }
        });
    }

    @Override
    public void getWishGoodsList(RequestParams params, final OnHomeDataCallBack callBack) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null){
                    callBack.onWishGoodsLoaded(result);
                }
            }
        });
    }

    @Override
    public void getPopularityGoodsList(RequestParams params, final OnHomeDataCallBack callBack) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null){
                    callBack.onPopularityGoodsLoaded(result);
                }
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
