package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.UpLoadFileInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnUpLoadFileCallback;

/**
 * Created by wuqaing on 2018/12/19.
 */

public class UpLoadFileImp implements UpLoadFileInterface{
    private NetRequestInterface netRequest;
    @Override
    public void upLoadFile(RequestParams params, final OnUpLoadFileCallback callback) {
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
                try {
                    callback.onError(error.toString());
                }catch (Exception ew){
                    ew.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callback.onLoadFileSuccess(result);
            }
        });
    }

    @Override
    public void modifyUserHead(RequestParams params, final OnUpLoadFileCallback callback) {
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
                try {
                    callback.onError(error.toString());
                }catch (Exception ew){
                    ew.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callback.onModifyUserHeadSuccess(result);
            }
        });
    }

    @Override
    public void modifyUserNickName(RequestParams params, final OnUpLoadFileCallback callback) {
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
                try {
                    callback.onError(error.toString());
                }catch (Exception ew){
                    ew.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callback.onModifyUserNickName(result);
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
