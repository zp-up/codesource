package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.GroupProductEntity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.Items;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeViewBinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.ViewHolder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.SearchOperationImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.NetCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.SearchOperationInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PasswordCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ScreenUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.SpannableUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnSearchCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.LinearLayout;

public class SearchActivity extends BaseAppcompatActivity implements OnSearchCallback {
    @BindView(R.id.ll_history_record)
    LinearLayout llHistoryRecord;
    @BindView(R.id.ll_hot_keyword)
    LinearLayout llHotKeyword;
    @BindView(R.id.et_search_input)
    EditText etSearchInput;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.tv_no_record)
    TextView tvNoRecord;

    @BindView(R.id.rv_result)
    RecyclerView mRV;

    @BindView(R.id.ll_search_box)
    android.widget.LinearLayout mLLSearchBox;

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private MultiTypeAdapter adapter;
    private Items items;
    private int pageIndex = 1;

    private SearchOperationInterface searchOperationPresenter;
    private List<String> hotSearch = new ArrayList<>();
    private List<String> historySearch = new ArrayList<>();
    private String rmb = Html.fromHtml("&yen").toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchOperationPresenter = new SearchOperationImp();
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initData();
        initViews();
    }

    private void initViews() {

        initRV();

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                doSearch();
            }
        });

        mRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                doSearch();
            }
        });

        etSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && !TextUtils.isEmpty(s)) {
                    tvOperation.setText("搜索");
                    etSearchInput.setSelection(s.length());
                } else {
                    tvOperation.setText("取消");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvOperation.getText().toString().equals("取消")) {
                    finish();
                } else {
                    //发起搜索
                    doSearch();
                }
            }
        });
    }

    private void doSearch() {
        if (searchOperationPresenter != null) {
            RequestParams params = new RequestParams(MainUrls.searchProduct);

            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("page", String.valueOf(pageIndex));

            if( ((IPSCApplication) getApplication()).getUserInfo()!=null){
                params.addBodyParameter("user", String.valueOf(((IPSCApplication) getApplication()).getUserInfo().getId()));
            }

            params.addBodyParameter("search", etSearchInput.getText().toString());
            searchOperationPresenter.doSearch(params, new NetCallback() {
                @Override
                public void onSuccess(String result) {
                    stopRefreshAndLoadMore();
                    if (result != null) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            if (obj != null) {

                                int code = obj.getInt("code");
                                int state = obj.getInt("state");
                                if (0 == code && 0 == state) {

                                    JSONArray data_array = obj.getJSONArray("data");
                                    if (data_array != null && data_array.length() > 0) {
                                        List<GroupProductEntity> data = new ArrayList<>();

                                        for (int i = 0; i < data_array.length(); i++) {
                                            JSONObject obj_i = data_array.getJSONObject(i);
                                            GroupProductEntity gp = new GroupProductEntity();
                                            gp.setId(obj_i.getInt("id"));

                                            GroupProductEntity.GoodsGoodsBean goodsGoodsBean = new GroupProductEntity.GoodsGoodsBean();
                                            GroupProductEntity.StoreStoreBean storeStoreBean = new GroupProductEntity.StoreStoreBean();


                                            JSONObject goods_goods = obj_i.getJSONObject("goods_goods");

                                            goodsGoodsBean.setId(goods_goods.getInt("id"));
                                            //商品图片
                                            List<String> imgs = new ArrayList<>();
                                            if (!TextUtils.isEmpty(goods_goods.getString("img"))) {
                                                JSONArray array = goods_goods.getJSONArray("img");
                                                if (array != null) {
                                                    for (int p = 0;p < array.length();p++){
                                                        imgs.add(array.getString(p));
                                                    }
                                                    //imgs = array.toJavaList(String.class);
                                                }
                                            }
                                            goodsGoodsBean.setImg(imgs);

                                            //商品名字
                                            goodsGoodsBean.setName(goods_goods.getString("name"));
                                            //商品描述
                                            goodsGoodsBean.setDescribe(goods_goods.getString("describe"));

                                            //拼团人数
                                            gp.setGroup_num_p(obj_i.getInt("group_num_p"));
                                            //商品单价
                                            gp.setPrice_m(obj_i.getString("price_m"));
                                            //商品团购价
                                            gp.setGroup_price(obj_i.getString("group_price"));
                                            //团购截止时间
                                            gp.setPron_end_time(obj_i.getString("pron_end_time"));

                                            gp.setGoods_goods(goodsGoodsBean);
                                            gp.setStore_store(storeStoreBean);
                                            data.add(gp);

                                        }


                                        //刷新或第一次加载
                                        if (pageIndex == 1) {
                                            items.clear();
                                            if (data != null && data.size() > 0) {
                                                //showSuccessLayout();
                                                items.addAll(data);

                                                //已经加载完所有数据
                                                if (data.size() < 10) {
                                                    ToastUtils.show(SearchActivity.this, "已加载完所有数据");
                                                    mRefresh.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    mRefresh.setEnableLoadmore(true);
                                                }

                                            } else {
                                                //刷新或首次加载失败
                                                ToastUtils.show(SearchActivity.this, "数据加载失败");
                                                //showEmptyLayout();
                                            }

                                        } else if (pageIndex > 1) {
                                            //上拉加载时
                                            if (data != null && data.size() > 0) {

                                                items.addAll(data);

                                                if (data.size() < 10) {
//                                if (mItems.contains(bottomEntity)) {
//                                    mItems.remove(bottomEntity);
//                                    mItems.add(bottomEntity);
//                                } else {
//                                    mItems.add(bottomEntity);
//                                }
                                                    ToastUtils.show(SearchActivity.this, "已加载完所有数据");
                                                    //上拉加载完所有数据，禁止上拉事件
                                                    mRefresh.setEnableLoadmore(false);
                                                } else {
                                                    pageIndex++;
                                                    mRefresh.setEnableLoadmore(true);
                                                }
                                            } else {
                                                //上拉加载完了所有数据
//                            if (mItems.contains(bottomEntity)) {
//                                mItems.remove(bottomEntity);
//                                mItems.add(bottomEntity);
//                            } else {
//                                mItems.add(bottomEntity);
//                            }
                                                ToastUtils.show(SearchActivity.this, "已加载完所有数据");
                                                mRefresh.setEnableLoadmore(false);
                                            }

                                        }

                                        adapter.notifyDataSetChanged();

                                        if(items.size()>0){
                                            mLLSearchBox.setVisibility(View.GONE);
                                            mRefresh.setVisibility(View.VISIBLE);
                                        }else {
                                            mLLSearchBox.setVisibility(View.VISIBLE);
                                            mRefresh.setVisibility(View.GONE);
                                        }


                                    }

                                }

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailed(Throwable e) {
                    stopRefreshAndLoadMore();
                }
            });
        }
    }

    private void stopRefreshAndLoadMore() {
        if (mRefresh != null) {
            mRefresh.finishLoadmore();
            mRefresh.finishRefresh(true);
        }
    }

    private void initRV() {
        items = new Items();

        adapter = new MultiTypeAdapter(items);
        MultiTypeViewBinder<GroupProductEntity> binder = new MultiTypeViewBinder<GroupProductEntity>(this, R.layout.item_product_search_result) {
            @Override
            protected void convert(ViewHolder holder, final GroupProductEntity productEntity, int position) {
                android.widget.LinearLayout llBox= holder.getView(R.id.ll_box);
                ImageView ivProduct = holder.getView(R.id.iv_product);
                TextView tvProductName = holder.getView(R.id.tv_name);
                TextView tvDescribe= holder.getView(R.id.tv_describe);
                TextView tvSinglePrice = holder.getView(R.id.tv_price);

                //商品名字
                tvProductName.setText(productEntity.getGoods_goods().getName());

                //商品描述
                tvDescribe.setText(productEntity.getGoods_goods().getDescribe());
                //商品单价
                tvSinglePrice.setText(SpannableUtil.setSizeSpan(rmb+productEntity.getPrice_m(),0,1,0.8f));

                //商品图片
                if(productEntity.getGoods_goods().getImg()!=null && productEntity.getGoods_goods().getImg().size()>0){
                    Glide.with(SearchActivity.this).load(productEntity.getGoods_goods().getImg().get(0)).
                            apply(new RequestOptions().override(300, 300).
                                    placeholder(R.mipmap.ic_launcher).
                                    error(R.mipmap.ic_launcher)).
                            into(ivProduct);
                }

                llBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(SearchActivity.this,GoodsDetailActivity.class);
                        intent.putExtra("goodsId",productEntity.getGoods_goods().getId());
                        startActivity(intent);
                    }
                });


            }
        };
        adapter.register(GroupProductEntity.class, binder);
        mRV.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildPosition(view);
                if (position % 2 == 0) {
                    outRect.left = ScreenUtil.dip2px(SearchActivity.this, 15f);
                    outRect.right = ScreenUtil.dip2px(SearchActivity.this, 7.5f);
                } else {
                    outRect.right = ScreenUtil.dip2px(SearchActivity.this, 15f);
                    outRect.left = ScreenUtil.dip2px(SearchActivity.this, 7.5f);
                }
            }
        });
        mRV.setLayoutManager(new GridLayoutManager(this, 2));
        mRV.setAdapter(adapter);
    }

    private void initData() {
        if (((IPSCApplication) getApplication()).getUserInfo() != null) {
            RequestParams params = new RequestParams(MainUrls.getHistorySearchUrl);
            params.addBodyParameter("access_token", IPSCApplication.accessToken);
            params.addBodyParameter("user", "" + ((IPSCApplication) getApplication()).getUserInfo().getId());
            params.addBodyParameter("page", "1");
            params.addBodyParameter("limit", "10");
            searchOperationPresenter.getHistorySearch(params, this);
        }
        RequestParams params = new RequestParams(MainUrls.getHotSearchUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("limit", "10");
        searchOperationPresenter.getHotSearch(params, this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onStarted() {
        showLoading(false, "获取数据中...");
    }

    @Override
    public void onFinished() {
        dismissLoading();
    }

    @Override
    public void onError(String error) {
        Log.e("SearchActivity", "出错:" + error);
    }

    @Override
    public void onHistorySearchLoaded(String result) {
        Log.e("TAG", "历史收索:" + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            if (code == 0 && state == 0) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data != null && data.length() != 0) {
                    for (int i = 0; i < data.length(); i++) {
                        if (PasswordCheckUtils.whatIsInputNumber(data.getString(i)) != 1) {
                            historySearch.add(data.getString(i));
                        }
                    }
                    initHistoryTag();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHistoryTag() {
        if (historySearch.size() != 0) {
            llHistoryRecord.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            llHistoryRecord.removeAllViews();
            for (int i = 0; i < historySearch.size(); i++) {
                View classView1 = LayoutInflater.from(this).inflate(R.layout.text_tag_view, null);
                TextView class1 = classView1.findViewById(R.id.tv_tag_name);
                final int index = i;
                class1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etSearchInput.setText(historySearch.get(index));
                    }
                });
                class1.setText(historySearch.get(i));
                llHistoryRecord.addView(classView1);
            }
        } else {
            tvNoRecord.setVisibility(View.VISIBLE);
            llHistoryRecord.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHotSearchLoaded(String result) {
        Log.e("TAG", "热门搜索:" + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            if (code == 0 && state == 0) {
                JSONArray data = jsonObject.getJSONArray("data");
                if (data != null && data.length() != 0) {
                    for (int i = 0; i < data.length(); i++) {
                        if (PasswordCheckUtils.whatIsInputNumber(data.getString(i)) != 1) {
                            hotSearch.add(data.getString(i));
                        }
                    }
                    initHatTag();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHatTag() {
        if (hotSearch.size() != 0) {
            //添加分类
            llHotKeyword.removeAllViews();
            for (int i = 0; i < hotSearch.size(); i++) {
                View classView1 = LayoutInflater.from(this).inflate(R.layout.text_tag_view, null);
                TextView class1 = classView1.findViewById(R.id.tv_tag_name);
                final int index = i;
                class1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etSearchInput.setText(hotSearch.get(index));
                    }
                });
                class1.setText(hotSearch.get(i));
                llHotKeyword.addView(classView1);
            }
        }
    }


}
