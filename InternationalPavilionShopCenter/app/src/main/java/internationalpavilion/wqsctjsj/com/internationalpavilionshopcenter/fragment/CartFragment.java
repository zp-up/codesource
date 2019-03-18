package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ConfirmOrderActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.LoginByPasswordActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.CartAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CartGood;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CartRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CartOperationImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CommonGoodsImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CartOperationInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CommonDataInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCartOperationCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnCommonGoodsCallBack;

/**
 * Created by wuqaing on 2018/11/29.
 */

public class CartFragment extends Fragment implements OnCommonGoodsCallBack, CartAdapter.OnShoppingCartOperationCallBack, OnCartOperationCallBack {
    private Unbinder unbinder;
    @BindView(R.id.rv_cart_content)
    RecyclerView rvCartContent;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.tv_total_pay)
    TextView tvTotalPay;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_submit_cart)
    TextView tvSubmitCart;
    @BindView(R.id.iv_all_selected)
    ImageView ivAllSelected;

    private ArrayList<CartRootBean> carts = new ArrayList<>();
    private CartAdapter cartAdapter;

    private CommonDataInterface commonPresenter;
    private CartOperationInterface cartOperationPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        commonPresenter = new CommonGoodsImp();
        cartOperationPresenter = new CartOperationImp();
        initView();
        return view;
    }

    private void initView() {
        srlContent.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
        ivAllSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAllChecked = isAllChecked(carts);
                RequestParams params = new RequestParams(MainUrls.selectedAllGoodsUrl);
                params.addBodyParameter("access_token", IPSCApplication.accessToken);
                params.addBodyParameter("cart_state", isAllChecked ? 1 + "" : 0 + "");
                if(((IPSCApplication) getActivity().getApplication()).getUserInfo()!=null){
                    params.addBodyParameter("user", ((IPSCApplication) getActivity().getApplication()).getUserInfo().getId() + "");
                }
                cartOperationPresenter.cartAllChecked(params, CartFragment.this);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (cartAdapter == null) {
            cartAdapter = new CartAdapter(getActivity(), carts);
            cartAdapter.setListener(this);
        }
        rvCartContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCartContent.setAdapter(cartAdapter);
        initData();
    }

    private void initData() {
        if (isLogin()) {
            RequestParams params = new RequestParams(MainUrls.getGoodsCartUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("page", "1");
            params.addBodyParameter("limit", "10000");
            params.addBodyParameter("user", ((IPSCApplication) getActivity().getApplication()).getUserInfo().getId() + "");
            commonPresenter.getCommonGoodsData(params, this);
        }

    }

    @OnClick({R.id.tv_submit_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击结算
            case R.id.tv_submit_cart:
                if (isLogin()) {
                    if (carts != null && carts.size() > 0) {
                        submitCart();
                    } else {
                        if (getActivity() != null && isAdded()) {
                            Toast.makeText(getActivity(), "当前购物车为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (getActivity() != null) {
                        getActivity().startActivity(new Intent(getActivity(), LoginByPasswordActivity.class));
                    }
                }

                break;
        }
    }

    private void submitCart() {
        RequestParams params = new RequestParams(MainUrls.cartSubmitUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        if(((IPSCApplication) getContext().getApplicationContext()).getUserInfo()!=null){
            params.addBodyParameter("user", ((IPSCApplication) getContext().getApplicationContext()).getUserInfo().getId() + "");
        }
        cartOperationPresenter.cartSubmit(params, this);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onStarted() {
        ((BaseAppcompatActivity) getActivity()).showLoading(false, "加载数据中...");
    }

    @Override
    public void onFinished() {
        srlContent.finishRefresh();
        srlContent.finishLoadmore();
        ((BaseAppcompatActivity) getActivity()).dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("CartFragment", "网络请求出错:" + error);
    }

    @Override
    public void onCommonDealCallBack(String result) {
        try {
            Log.e("TAG", "用户操作结果:" + result);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除购物车商品
    @Override
    public void onDeleteCallBack(String result) {
        initData();
    }

    @Override
    public void onSubmitCallBack(String result) {
        Log.e("TAG", "提交购物车返回:" + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            String msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "提交购物车失败";
            if (code == 0 && state == 0) {
                if (jsonObject.has("data")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    String orderId = data.has("0") ? data.getString("0") : "-1";
                    if (!orderId.equals("-1")) {
                        Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                        intent.putExtra("id", orderId);
                        startActivity(intent);
                    }
                }
            } else {
                if (msg.contains("无需要生成的订单")) {
                    ToastUtils.show(getContext(), "请选择要购买的的商品。");
                } else {
                    ToastUtils.show(getContext(), msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCommonGoodsCallBack(String result) {
        if (result != null) {
            Log.e("TAG", "cartInfo:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int state = jsonObject.getInt("state");
                int code = jsonObject.getInt("code");
                if (state == 0 && code == 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    double totalPrice = data.has("total") ? data.getDouble("total") : 0.00;
                    int totalNumber = data.has("number") ? data.getInt("number") : 0;
                    tvSubmitCart.setText("结算" + (totalNumber == 0 ? "" : "(" + totalNumber + ")"));
                    tvTotalPay.setText("合计:￥" + totalPrice);
                    tvDiscount.setText("优惠:-￥0.00");
                    if (carts.size() != 0) {
                        carts.clear();
                        cartAdapter.notifyDataSetChanged();
                    }
                    JSONArray storeList = data.getJSONArray("list");
                    if (storeList != null && storeList.length() != 0) {
                        for (int i = 0; i < storeList.length(); i++) {
                            CartRootBean cartRootBean = new CartRootBean();
                            JSONObject carRoot = storeList.getJSONObject(i);
                            cartRootBean.setCartGoodsName(carRoot.getJSONObject("store_store").getString("name"));
                            cartRootBean.setStoreType(carRoot.getJSONObject("store_store").getString("type"));
                            cartRootBean.setId(carRoot.getJSONObject("store_store").getInt("id"));
                            cartRootBean.setTotalPrice(carRoot.getDouble("total"));
                            JSONArray goodsList = carRoot.getJSONArray("list");
                            ArrayList<CartGood> cartGoodsList = new ArrayList<>();
                            if (goodsList != null && goodsList.length() != 0) {
                                for (int j = 0; j < goodsList.length(); j++) {
                                    JSONObject goods = goodsList.getJSONObject(j);
                                    CartGood cartGood = new CartGood();
                                    cartGood.setId(goods.getInt("id"));
                                    cartGood.setChecked(goods.getInt("cart_state") == 1 ? true : false);
                                    cartGood.setImagePath(goods.getJSONObject("store_price").getJSONObject("goods_goods")
                                            .getJSONArray("img").getString(0));
                                    cartGood.setName(goods.getJSONObject("store_price").getJSONObject("goods_goods")
                                            .getString("name"));
                                    cartGood.setNum(goods.getInt("number"));
                                    cartGood.setPrice(goods.getDouble("price"));
                                    cartGood.setOriginalPrice(goods.getDouble("price"));
                                    cartGood.setTaxation(goods.getDouble("tax"));
                                    cartGood.setInfo(goods.getJSONObject("store_price").getJSONObject("goods_goods")
                                            .getString("spec"));
                                    cartGoodsList.add(cartGood);
                                }
                                cartRootBean.setmCartGood(cartGoodsList);
                            }
                            carts.add(cartRootBean);
                            if (cartAdapter != null) {
                                cartAdapter.notifyDataSetChanged();
                            }
                            ivAllSelected.setImageResource(isAllChecked(carts) ? R.mipmap.icon_cart_box_selected : R.mipmap.icon_cart_box_unselected);
                        }
                    } else {
                        ivAllSelected.setImageResource(R.mipmap.icon_cart_box_unselected);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAllChecked(ArrayList<CartRootBean> rootBean) {
        for (int i = 0; i < rootBean.size(); i++) {
            if (rootBean.get(i).getmCartGood() != null && rootBean.get(i).getmCartGood().size() != 0) {
                for (int j = 0; j < rootBean.get(i).getmCartGood().size(); j++) {
                    if (!rootBean.get(i).getmCartGood().get(j).isChecked()) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onGoodsChanged(int goodsCartId) {
        RequestParams params = new RequestParams(MainUrls.cartGoodsChangedUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", goodsCartId + "");
        params.addBodyParameter("user", ((IPSCApplication) getActivity().getApplication()).getUserInfo().getId() + "");
        cartOperationPresenter.cartAdd(params, this);
    }

    @Override
    public void onStoreChanged(int storeId) {
        RequestParams params = new RequestParams(MainUrls.cartStoreChangedUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("store", storeId + "");
        if(getActivity()!=null && isAdded()){
            if(((IPSCApplication) getActivity().getApplication()).getUserInfo()!=null){
                params.addBodyParameter("user", ((IPSCApplication) getActivity().getApplication()).getUserInfo().getId() + "");
            }
        }

        cartOperationPresenter.cartAdd(params, this);
    }

    @Override
    public void onNumAdd(int goodsCartId, int num) {
        int number = num;
        number += 1;
        RequestParams params = new RequestParams(MainUrls.modifyCartGoodsNumber);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", goodsCartId + "");
        params.addBodyParameter("number", number + "");
        cartOperationPresenter.cartAdd(params, this);
    }

    @Override
    public void onNumSub(int goodsCartId, int num) {
        int number = num;
        if (number <= 1) {
            number = 1;
        } else {
            number -= 1;
        }
        RequestParams params = new RequestParams(MainUrls.modifyCartGoodsNumber);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", goodsCartId + "");
        params.addBodyParameter("number", number + "");
        cartOperationPresenter.cartAdd(params, this);
    }

    @Override
    public void onDeleteGoodsCart(int goodsCartId) {
        RequestParams params = new RequestParams(MainUrls.deleteCartGoodsUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", goodsCartId + "");
        cartOperationPresenter.cartAdd(params, this);
    }

    //判断是否登录
    private boolean isLogin() {
        if (getActivity() != null && isAdded()) {
            if (((IPSCApplication) getActivity().getApplication()).getUserInfo() != null) {
                return true;
            }
        }
        return false;
    }

}
