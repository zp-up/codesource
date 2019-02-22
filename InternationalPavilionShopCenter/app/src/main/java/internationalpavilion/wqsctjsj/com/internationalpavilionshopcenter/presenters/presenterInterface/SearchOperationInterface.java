package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnSearchCallback;

/**
 * Created by wuqaing on 2019/2/12.
 */

public interface SearchOperationInterface {
    void getHistorySearch(RequestParams params, OnSearchCallback callback);

    void getHotSearch(RequestParams params, OnSearchCallback callback);
    //发起搜索
    void doSearch(RequestParams params,NetCallback callback);
}
