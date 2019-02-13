package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface;

/**
 * Created by wuqaing on 2018/11/20.
 */

public interface NetCallBack {
    void onStart();
    void onFinished();
    void onError(Throwable error);
    void onSuccess(String result);
}
