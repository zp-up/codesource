package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBondedGoodsCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnOverseasGoodsCallBack;

/**
 * Created by wuqaing on 2018/12/26.
 */

public interface OverseasGoodsInterface {
    void getOverseasGoods(RequestParams params, OnOverseasGoodsCallBack callBack);
    void getBrandData(RequestParams params,OnOverseasGoodsCallBack callBack);
    void getClassData(RequestParams params,OnOverseasGoodsCallBack callBack);
    void onDestroyed();
}
