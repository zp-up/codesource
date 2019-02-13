package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.classBrandBean.ClassBrandBean;

/**
 * Created by wuqaing on 2018/12/19.
 */

public interface OnClassCallback {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onLeftClassSuccess(String result);

    void onRightClassSuccess(String result);

    void onBrandClassSuccess(ArrayList<RightClassBean> result);
}
