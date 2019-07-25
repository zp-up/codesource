package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnMineDataCallBack;

/**
 * Created by gwx on 2019/07/25.
 */

public interface MineDataInterface {

    void getUserOrderData(RequestParams params, OnMineDataCallBack callBack);

    void onDestroyed();
}
