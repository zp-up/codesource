package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.LocationAperationInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAreaOrCityOrProvenceDataCallBack;


/**
 * Created by wuqaing on 2019/1/20.
 */

public class LocationOperationImp implements LocationAperationInterface{
    private NetRequestInterface netReques;
    @Override
    public void getAreaOrCityOrProvence(RequestParams params, final OnAreaOrCityOrProvenceDataCallBack callBack) {
        if (netReques == null){
            netReques = new NetRequestImp();
        }
        netReques.doNetRequest(params, new NetCallBack() {
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
                callBack.onAreaInfoCallBack(result);
            }
        });
    }

    @Override
    public void editOrAddLocationInfo(RequestParams params, final OnAreaOrCityOrProvenceDataCallBack callBack) {
        if (netReques == null){
            netReques = new NetRequestImp();
        }
        netReques.doNetRequest(params, new NetCallBack() {
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
                callBack.onAddressInfoAddOrChangedCallBack(result);
            }
        });
    }

    @Override
    public void onDestroyed() {

    }
}
