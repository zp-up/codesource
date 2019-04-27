package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

import org.xutils.http.RequestParams;

/**
 * Created by wangm on 2019/4/17.
 */

public interface OnAuthenticationListInterface {
    void getAuthenticationList(RequestParams params, OnAuthenticationListCallback callback);
}
