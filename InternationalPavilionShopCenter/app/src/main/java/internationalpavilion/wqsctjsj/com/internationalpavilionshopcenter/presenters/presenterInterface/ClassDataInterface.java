package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnClassCallback;

/**
 * Created by wuqaing on 2018/12/20.
 */

public interface ClassDataInterface {
    void getLeftClass(RequestParams params, OnClassCallback callback);

    void getRightClass(RequestParams params, OnClassCallback callback);

    void getBrandClass(RequestParams params, OnClassCallback callback);

    void onDestroyed();
}
