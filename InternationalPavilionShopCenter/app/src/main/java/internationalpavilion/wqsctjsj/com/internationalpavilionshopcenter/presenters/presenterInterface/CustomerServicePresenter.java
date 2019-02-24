package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnNetCallBack;

/**
 * Author:chris - jason
 * Date:2019/2/24.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface
 */

public interface CustomerServicePresenter {

    //获取初始化机器人消息
    void getInitMessage(RequestParams params, NetCallback callBack);
    //用户发送消息
    void sendMessage(RequestParams params,NetCallback callback);

}
