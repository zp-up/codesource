package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.LeftClassAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.RightClassAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.LeftClassBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassInChildBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.ClassDataImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.ClassDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnClassCallback;

/**
 * Created by wuqaing on 2018/11/30.
 */

public class ClassInClassFragment extends Fragment implements OnClassCallback {
    private Unbinder unbinder;

    @BindView(R.id.rv_class)
    RecyclerView rvClassLeft;
    @BindView(R.id.rv_class_right)
    RecyclerView rvClassRight;
    private ArrayList<LeftClassBean> leftClassData = new ArrayList<>();
    private ArrayList<RightClassBean> rightClassData = new ArrayList<>();
    private LeftClassAdapter leftClassAdapter;
    private RightClassAdapter rightClassAdapter;
    private ClassDataInterface classDataPresenter;
    private String currentImgUrl = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_of_class_in_class, container, false);
        unbinder = ButterKnife.bind(this, view);
        classDataPresenter = new ClassDataImp();
        initLeftData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setHeaderImg(String imgUrl) {
        currentImgUrl = imgUrl;
    }

    private void initRightData(int pid) {
        RequestParams params = new RequestParams(MainUrls.getLeftClassUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("pid", pid + "");
        classDataPresenter.getRightClass(params, this);
        if (rightClassAdapter == null) {
            rightClassAdapter = new RightClassAdapter(getActivity(), rightClassData);
            rvClassRight.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvClassRight.setAdapter(rightClassAdapter);
        }
    }

    private void initLeftData() {
        RequestParams params = new RequestParams(MainUrls.getLeftClassUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        classDataPresenter.getLeftClass(params, this);
        if (leftClassAdapter == null) {
            leftClassAdapter = new LeftClassAdapter(leftClassData, getActivity());
            leftClassAdapter.setListener(new LeftClassAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    for (int i = 0; i < leftClassData.size(); i++) {
                        leftClassData.get(i).setChecked(false);
                    }
                    leftClassData.get(position).setChecked(true);
                    leftClassAdapter.notifyDataSetChanged();
                    setHeaderImg(leftClassData.get(position).getImgUrl());
                    initRightData(leftClassData.get(position).getId());
                }
            });
            rvClassLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvClassLeft.setAdapter(leftClassAdapter);
        }

    }

    @Override
    public void onStarted() {
        ((BaseAppcompatActivity) getActivity()).showLoading(false, "获取数据中...");
    }

    @Override
    public void onFinished() {
        ((BaseAppcompatActivity) getActivity()).dismissLoading();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onLeftClassSuccess(String result) {
        if (result != null) {
            Log.e("TAG", "左边分类:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data != null && data.length() != 0) {
                            leftClassData.clear();
                            leftClassAdapter.notifyDataSetChanged();
                            for (int i = 0; i < data.length(); i++) {
                                LeftClassBean bean = new LeftClassBean();
                                int id = data.getJSONObject(i).getInt("id");
                                String name = data.getJSONObject(i).getString("name");
                                bean.setId(id);
                                bean.setClassName(name);
                                if (!TextUtils.isEmpty(data.getJSONObject(i).getString("img"))) {
                                    bean.setImgUrl(data.getJSONObject(i).getJSONArray("img").getString(0));
                                }
                                if (i == 0) {
                                    bean.setChecked(true);
                                    initRightData(id);
                                    setHeaderImg(bean.getImgUrl());
                                } else {
                                    bean.setChecked(false);
                                }
                                leftClassData.add(bean);
                            }
                            leftClassAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRightClassSuccess(String result) {
        if (result != null) {
            Log.e("TAG", "右边分类:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                if (code == 0 && state == 0) {
                    if (jsonObject.has("data")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data != null && data.length() != 0) {
                            rightClassData.clear();
                            rightClassAdapter.notifyDataSetChanged();
                            for (int i = 0; i < data.length(); i++) {
                                RightClassBean bean = new RightClassBean();
                                int id = data.getJSONObject(i).getInt("id");
                                String name = data.getJSONObject(i).getString("name");
                                bean.setMainClassName(name);
                                bean.setId(id);
                                bean.setCurrentUrl(currentImgUrl);

                                if (data.getJSONObject(i).has("cate") && data.getJSONObject(i).getJSONArray("cate") != null) {
                                    ArrayList<RightClassInChildBean> childBeans = new ArrayList<>();
                                    JSONArray children = data.getJSONObject(i).getJSONArray("cate");
                                    if (children.length() != 0) {
                                        for (int j = 0; j < children.length(); j++) {
                                            RightClassInChildBean child = new RightClassInChildBean();
                                            int childId = children.getJSONObject(j).getInt("id");
                                            String childName = children.getJSONObject(j).getString("name");
                                            if (children.getJSONObject(j).has("goods")) {
                                                JSONObject goods = children.getJSONObject(j).getJSONObject("goods");
                                                if(goods!=null){
                                                    Object o = goods.get("img");
                                                    String img = "";
                                                    if(o instanceof String){
                                                        img = goods.getString("img");
                                                    }else if(o instanceof JSONArray){
                                                        JSONArray imgAry = goods.getJSONArray("img");
                                                        if(imgAry!=null && imgAry.length()>0){
                                                            img = imgAry.getString(0);
                                                        }

                                                    }
                                                    child.setImgUrl(img);
                                                    child.setId(childId);
                                                    child.setClassName(childName);
                                                    childBeans.add(child);
                                                }

                                            }

                                        }
                                        bean.setChildBeans(childBeans);
                                    }
                                }
                                rightClassData.add(bean);
                            }
                            rightClassAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //无用
    @Override
    public void onBrandClassSuccess(ArrayList<RightClassBean> result) {

    }
}
