package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views;

/**
 * Created by wuqaing on 2019/1/8.
 */

public interface OnAreaOrCityOrProvenceDataCallBack {
    void onStarted();

    void onFinished();

    void onError(String error);

    void onAreaInfoCallBack(String result);

    void onAddressInfoAddOrChangedCallBack(String result);

    void onAddressDlelteCallBack(String result);
}
