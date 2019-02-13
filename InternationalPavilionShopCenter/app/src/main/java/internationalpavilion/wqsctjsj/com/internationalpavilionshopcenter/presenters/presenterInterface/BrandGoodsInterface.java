package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface;

import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnBrandGoodsLoaded;

/**
 * Created by wuqaing on 2019/1/16.
 */

public interface BrandGoodsInterface {
    void getBrandGoodsList(RequestParams params, OnBrandGoodsLoaded callback);
    void getBrandInformation(RequestParams params, OnBrandGoodsLoaded callback);

}
