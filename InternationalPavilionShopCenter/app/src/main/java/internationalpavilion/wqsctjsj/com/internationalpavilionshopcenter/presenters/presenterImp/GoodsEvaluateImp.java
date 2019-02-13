package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.EvaluateImage;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsEvaluateList.GoodsEvaluateListBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsImp.NetRequestImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.modelsInterface.NetRequestInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.GoodsEvaluateInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnEvaluateListCallback;

/**
 * Created by wuqaing on 2018/12/25.
 */

public class GoodsEvaluateImp implements GoodsEvaluateInterface {
    private NetRequestInterface netRequest;

    @Override
    public void getAllEvaluateList(RequestParams params, final OnEvaluateListCallback callBack) {
        if (netRequest == null) {
            netRequest = new NetRequestImp();
        }
        netRequest.doNetRequest(params, new NetCallBack() {
            @Override
            public void onStart() {
                callBack.onStarted();
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }

            @Override
            public void onError(Throwable error) {
                try {
                    callBack.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    ArrayList<GoodsEvaluateListBean> evaluateListBeans = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        JSONObject state = jsonObject.getJSONObject("state");
                        double rating = state.getDouble("rate");
                        double percent = state.getDouble("percent");
                        if (code == 0 && jsonObject.has("data")) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (data.length() != 0) {
                                for (int i = 0; i < data.length(); i++) {
                                    GoodsEvaluateListBean evaluateBean = new GoodsEvaluateListBean();
                                    int id = data.getJSONObject(i).getInt("id");
                                    String userName = data.getJSONObject(i).getJSONObject("user_user").getString("nickname");
                                    String userHeadUrl = data.getJSONObject(i).getJSONObject("user_user").getString("img");
                                    int rate = data.getJSONObject(i).getInt("rate");
                                    String content = data.getJSONObject(i).getString("name");
                                    long inset_time = (long) data.getJSONObject(i).getDouble("create_time") * 1000;
                                    ArrayList<EvaluateImage> imagesList = new ArrayList<>();
                                    if (!TextUtils.isEmpty(data.getJSONObject(i).getString("img"))) {
                                        JSONArray images = data.getJSONObject(i).getJSONArray("img");
                                        for (int j = 0; j < images.length(); j++) {
                                            String url = images.getString(j);
                                            EvaluateImage ei = new EvaluateImage();
                                            ei.setId(0);
                                            ei.setOriginalUrl(url);
                                            ei.setUrl(url);
                                            imagesList.add(ei);
                                        }
                                    }
                                    evaluateBean.setUserNickName(userName);
                                    evaluateBean.setId(id);
                                    evaluateBean.setUserHeadImgUrl(userHeadUrl);
                                    evaluateBean.setEvaluateContent(content);
                                    evaluateBean.setRating(rate);
                                    evaluateBean.setInsertTime(inset_time);
                                    evaluateBean.setImages(imagesList);
                                    evaluateListBeans.add(evaluateBean);
                                }

                            }
                        }
                        callBack.onGetEvaluateListSuccess(evaluateListBeans,rating,percent);
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
