package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.GoodsDetailInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnGoodsDetailInfoCallback;

/**
 * Created by wuqaing on 2018/12/21.
 */

public class GoodsDetailImp implements GoodsDetailInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getGoodsBaseInfo(RequestParams params, final OnGoodsDetailInfoCallback callback) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                try {
                    callback.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callback.onGoodsInfoLoaded(result);
            }
        });
    }

    @Override
    public void getGoodsEvaluate(RequestParams params, final OnGoodsDetailInfoCallback callback) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                try {
                    callback.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callback.onGoodsEvaluateLoaded(result);
            }
        });
    }

    @Override
    public void addGoodsToCart(RequestParams params, final OnGoodsDetailInfoCallback callback) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                try {
                    callback.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                callback.onGoodsAddedToCart(result);
            }
        });
    }

    @Override
    public void addGoodsToCollection(RequestParams params, final OnGoodsDetailInfoCallback callback, final int type) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callback.onStarted();
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                try {
                    callback.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        Log.d("[IPSC][]", "collection success! result:" + result);
                        int code = jsonObject.getInt("code");
                        int state = jsonObject.getInt("state");
                        String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
                        if (code == 0 && state == 0) {
                            String resultMsg = "";
                            if (type == 1) {
                                resultMsg = "收藏成功";
                            } else {
                                resultMsg = "取消收藏成功";
                            }
                            callback.onGoodsAddedToCollection(type, resultMsg);
                        } else {
                            callback.onGoodsAddedToCollection(type, msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyed() {
        if (netRequest != null){
            netRequest = null;
            System.gc();
        }
    }
}
