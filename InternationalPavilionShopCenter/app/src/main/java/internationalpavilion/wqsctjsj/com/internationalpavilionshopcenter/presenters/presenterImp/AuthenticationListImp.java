package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAuthenticationListCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAuthenticationListInterface;

/**
 * Created by wangm on 2019/4/17.
 */

public class AuthenticationListImp implements OnAuthenticationListInterface {
    private NetRequestInterface netRequest;
    @Override
    public void getAuthenticationList(RequestParams params, final OnAuthenticationListCallback callback) {
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
                callback.onSuccess(result);
            }
        });
    }
}
