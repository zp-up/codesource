package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCartOperationCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

/**
 * Created by wuqaing on 2019/1/8.
 */

public interface CartOperationInterface {
    void cartAdd(RequestParams params, OnCartOperationCallBack callBack);

    void cartSub(RequestParams params, OnCartOperationCallBack callBack);

    void cartGoodsChanged(RequestParams params, OnCartOperationCallBack callBack);

    void cartStoreChanged(RequestParams params, OnCartOperationCallBack callBack);

    void cartAllSelected(RequestParams params, OnCartOperationCallBack callBack);

    void cartDelete(RequestParams params, OnCartOperationCallBack callBack);

    void cartAllChecked(RequestParams params, OnCartOperationCallBack callBack);

    void cartSubmit(RequestParams params,OnCartOperationCallBack callBack);

    void onDestroyed();
}
