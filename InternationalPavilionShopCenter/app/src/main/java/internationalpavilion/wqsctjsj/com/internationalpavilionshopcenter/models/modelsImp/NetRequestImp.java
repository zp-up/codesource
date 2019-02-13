package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.net.NormalResponse;

/**
 * Created by wuqaing on 2018/12/14.
 */

public class NetRequestImp implements NetRequestInterface{
    @Override
    public void doNetRequest(RequestParams params, final NetCallBack callBack) {
        params.setUseCookie(true);
        x.http().post(params, new Callback.ProgressCallback<NormalResponse>() {

            @Override
            public void onSuccess(NormalResponse result) {
                if (result != null){
                    callBack.onSuccess(result.getResult());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                callBack.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });
    }
}
