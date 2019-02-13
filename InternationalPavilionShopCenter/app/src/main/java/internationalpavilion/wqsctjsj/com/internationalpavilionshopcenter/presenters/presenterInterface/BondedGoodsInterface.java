package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBondedGoodsCallBack;

/**
 * Created by wuqaing on 2018/12/26.
 */

public interface BondedGoodsInterface {
    void getBondedGoods(RequestParams params, OnBondedGoodsCallBack callBack);
    void getBrandData(RequestParams params,OnBondedGoodsCallBack callBack);
    void getClassData(RequestParams params,OnBondedGoodsCallBack callBack);
    void onDestroyed();
}
