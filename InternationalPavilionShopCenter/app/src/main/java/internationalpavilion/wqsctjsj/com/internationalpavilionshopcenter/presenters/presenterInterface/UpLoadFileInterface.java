package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnUpLoadFileCallback;

/**
 * Created by wuqaing on 2018/12/19.
 */

public interface UpLoadFileInterface {
    void upLoadFile(RequestParams params, OnUpLoadFileCallback callback);
    void modifyUserHead(RequestParams params,OnUpLoadFileCallback callback);
    void modifyUserNickName(RequestParams params,OnUpLoadFileCallback callback);
    void onDestroyed();
}
