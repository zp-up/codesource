package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

/**
 * Created by wuqaing on 2018/12/14.
 */

public interface UserOptionInterface {
    void doLogin(RequestParams params, OnNetCallBack callBack);
    void getVerifyCode(RequestParams params, OnNetCallBack callBack);
    void doRegister(RequestParams params,OnNetCallBack callBack);
    void onDestroyed();
}
