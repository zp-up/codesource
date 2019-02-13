package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsEvaluateList.GoodsEvaluateListBean;

/**
 * Created by wuqaing on 2018/12/19.
 */

public interface OnEvaluateListCallback {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onGetEvaluateListSuccess(ArrayList<GoodsEvaluateListBean> result,double rating,double percent);

}
