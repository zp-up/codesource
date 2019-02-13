package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAreaOrCityOrProvenceDataCallBack;


/**
 * Created by wuqaing on 2019/1/8.
 */

public interface LocationAperationInterface {
    void getAreaOrCityOrProvence(RequestParams params, OnAreaOrCityOrProvenceDataCallBack callBack);

    void editOrAddLocationInfo(RequestParams params, OnAreaOrCityOrProvenceDataCallBack callBack);

    void onDestroyed();
}
