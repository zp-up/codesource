package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Author:chris - jason
 * Date:2019/2/19.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views
 */
//拼团商品列表网络层回调接口
public interface OnGroupProductListCallback {

    void onError(Throwable error);

    void onSuccess(String result);

}
