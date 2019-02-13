package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnEvaluateListCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

/**
 * Created by wuqaing on 2018/12/14.
 */

public interface GoodsEvaluateInterface {
    void getAllEvaluateList(RequestParams params, OnEvaluateListCallback callBack);
    void onDestroyed();
}
