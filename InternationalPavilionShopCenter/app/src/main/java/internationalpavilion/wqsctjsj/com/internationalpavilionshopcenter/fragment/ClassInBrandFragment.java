package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;

import org.xutils.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.ClassBrandAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.ClassDataImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.ClassDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnClassCallback;

/**
 * Created by wuqaing on 2018/11/30.
 */

public class ClassInBrandFragment extends Fragment implements OnClassCallback{
    private Unbinder unbinder;
    private ClassBrandAdapter classBrandAdapter;
    private ArrayList<RightClassBean> data = new ArrayList<>();

    @BindView(R.id.rv_brand)
    RecyclerView rvBrand;

    private ClassDataInterface classDataPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_in_brand,container,false);
        unbinder = ButterKnife.bind(this,view);
        classDataPresenter = new ClassDataImp();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initBrandData();
    }

    private void initBrandData() {
        if (classBrandAdapter == null) {
            classBrandAdapter = new ClassBrandAdapter(getActivity(), data);
            rvBrand.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvBrand.setAdapter(classBrandAdapter);
        }
        if (data.size() == 0) {
            RequestParams params = new RequestParams(MainUrls.getAllClassBrandUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("page","1");
            params.addBodyParameter("limit","100");
            classDataPresenter.getBrandClass(params,this);
//            for (int i = 0; i < 3; i++) {
//                ArrayList<RightClassInChildBean> list = new ArrayList<>();
//                RightClassBean rightClassBean = new RightClassBean();
//                rightClassBean.setMainClassName("面部护理");
//
//                RightClassInChildBean childBean1 = new RightClassInChildBean();
//                childBean1.setClassName("洁面/卸妆");
//                childBean1.setImgUrl("");
//                list.add(childBean1);
//                RightClassInChildBean childBean2 = new RightClassInChildBean();
//                childBean2.setClassName("面膜");
//                childBean2.setImgUrl("");
//                list.add(childBean2);
//                RightClassInChildBean childBean3 = new RightClassInChildBean();
//                childBean3.setClassName("乳液面霜");
//                childBean3.setImgUrl("");
//                list.add(childBean3);
//                RightClassInChildBean childBean4 = new RightClassInChildBean();
//                childBean4.setClassName("精华");
//                childBean4.setImgUrl("");
//                list.add(childBean4);
//                RightClassInChildBean childBean5 = new RightClassInChildBean();
//                childBean5.setClassName("套装");
//                childBean5.setImgUrl("");
//                list.add(childBean5);
//                rightClassBean.setChildBeans(list);
//                data.add(rightClassBean);
//            }
//        } else {
//            data.clear();
//            for (int i = 0; i < 3; i++) {
//                ArrayList<RightClassInChildBean> list = new ArrayList<>();
//                RightClassBean rightClassBean = new RightClassBean();
//                rightClassBean.setMainClassName("面部护理");
//
//                RightClassInChildBean childBean1 = new RightClassInChildBean();
//                childBean1.setClassName("洁面/卸妆");
//                childBean1.setImgUrl("");
//                list.add(childBean1);
//                RightClassInChildBean childBean2 = new RightClassInChildBean();
//                childBean2.setClassName("面膜");
//                childBean2.setImgUrl("");
//                list.add(childBean2);
//                RightClassInChildBean childBean3 = new RightClassInChildBean();
//                childBean3.setClassName("乳液面霜");
//                childBean3.setImgUrl("");
//                list.add(childBean3);
//                RightClassInChildBean childBean4 = new RightClassInChildBean();
//                childBean4.setClassName("精华");
//                childBean4.setImgUrl("");
//                list.add(childBean4);
//                RightClassInChildBean childBean5 = new RightClassInChildBean();
//                childBean5.setClassName("套装");
//                childBean5.setImgUrl("");
//                list.add(childBean5);
//                rightClassBean.setChildBeans(list);
//                data.add(rightClassBean);
//            }
        }


    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onStarted() {
        ((BaseAppcompatActivity)getActivity()).showLoading(false,"获取数据中...");
    }

    @Override
    public void onFinished() {
        ((BaseAppcompatActivity)getActivity()).dismissLoading();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onLeftClassSuccess(String result) {

    }

    @Override
    public void onRightClassSuccess(String result) {

    }
    //品牌分类
    @Override
    public void onBrandClassSuccess(ArrayList<RightClassBean> result) {
        if (result.size() != 0){
            data.addAll(result);
            classBrandAdapter.notifyDataSetChanged();
        }
    }
}
