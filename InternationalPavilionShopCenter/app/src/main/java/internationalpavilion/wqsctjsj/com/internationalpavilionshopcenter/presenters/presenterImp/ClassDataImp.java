package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassInChildBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.ClassDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnClassCallback;

/**
 * Created by wuqaing on 2018/12/20.
 */

public class ClassDataImp implements ClassDataInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getLeftClass(RequestParams params, final OnClassCallback callback) {
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
                callback.onLeftClassSuccess(result);
            }
        });
    }

    @Override
    public void getRightClass(RequestParams params, final OnClassCallback callback) {
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
                callback.onRightClassSuccess(result);
            }
        });
    }

    @Override
    public void getBrandClass(RequestParams params, final OnClassCallback callback) {
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
                ArrayList<RightClassBean> branList = new ArrayList<>();
                if (result != null) {
                    Log.e("TAG", "品牌分类:" + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        int state = jsonObject.getInt("state");
                        if (code == 0 && state == 0) {
                            if (jsonObject.has("data")) {
                                JSONArray data = jsonObject.getJSONArray("data");
                                if (data != null && data.length() != 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        RightClassBean rightClassBean = new RightClassBean();
                                        int id = data.getJSONObject(i).getInt("id");
                                        String name = data.getJSONObject(i).getString("name");

                                        rightClassBean.setId(id);
                                        rightClassBean.setMainClassName(name);

                                        if (data.getJSONObject(i).has("list") && !TextUtils.isEmpty(data.getJSONObject(i).getString("list"))) {
                                            JSONArray list = data.getJSONObject(i).getJSONArray("list");
                                            if (list != null && list.length() != 0) {
                                                ArrayList<RightClassInChildBean> childBeans = new ArrayList<>();
                                                for (int j = 0; j < list.length(); j++) {
                                                    RightClassInChildBean childBean = new RightClassInChildBean();
                                                    int childId = list.getJSONObject(j).getInt("id");
                                                    String childName = list.getJSONObject(j).getString("name");
                                                    String childLogo = list.getJSONObject(j).getString("logo");
                                                    childBean.setClassName(childName);
                                                    childBean.setId(childId);
                                                    childBean.setImgUrl(childLogo);
                                                    childBeans.add(childBean);
                                                }
                                                rightClassBean.setChildBeans(childBeans);
                                            }
                                        }
                                        branList.add(rightClassBean);
                                    }
                                    callback.onBrandClassSuccess(branList);
                                }
                            }
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
