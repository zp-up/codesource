package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

/**
 * Author:chris - jason
 * Date:2019/2/22.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface
 */

public interface NetCallback {
    void onSuccess(String result);
    void onFailed(Throwable e);
}
