package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import android.util.Log;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.IGoodsListPresenter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.NetCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnOverseasGoodsCallBack;

/**
 * Author:chris - jason
 * Date:2019/2/27.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp
 */

public class GoodsListPresenterImp implements IGoodsListPresenter {
    private NetRequestInterface netRequest;

    //查询新品列表
    @Override
    public void getNewlyGoodsList(RequestParams params, final NetCallback callBack) {
        if(netRequest==null){
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callBack.onStart();
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
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

    //查询折扣商品列表
    @Override
    public void getDiscountGoodsList(RequestParams params, final NetCallback callback) {
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
                try {
                    if (callback != null) {
                        callback.onFailed(error);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(String result) {
                Log.d("IPSC", "getDiscountGoodsList() onSuccess() result:" + result);
                if(callback!=null){
                    callback.onSuccess(result);
                }
            }
        });
    }

    @Override
    public void getBrandData(RequestParams params, final NetCallback callBack) {
        if (netRequest == null) {
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
                try {
                    callBack.onFailed(error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }
        });
    }
}