package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.GoodsDetailActivity;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.GoodsDetailRootBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.Speclist;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean.Storelist;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;


public class GoodsPropsSelectPop implements View.OnClickListener {

    private final View v;
    private int count = 1;
    private PopupWindow pop;
    private ShoppingCartClick click;
    boolean cancelAble = true;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout llTagsContainer;
    private ImageView ivClosePop;
    private GoodsDetailRootBean goodsBean;
    private internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.LinearLayout llStorageContainer;
    private TextView tvAdd, tvSub, tvNum;

//    private TextView tvAddGoodsToCart;

    private int currentSpecId = -1;
    private int currentStoreType = -1;

    //商品信息
    private ImageView ivGoodsPic;
    private TextView tvGoodsName;
    private TextView tvGoodsPrice;
    private TextView tvSelectedGoodsSpec;
    private ArrayList<Speclist> specLists = new ArrayList<>();
    private ArrayList<Storelist> storeLists = new ArrayList<>();


    public GoodsPropsSelectPop(Context c, GoodsDetailRootBean goodsBean, ArrayList<Speclist> specLists, ArrayList<Storelist> storeLists) {

        context = c;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        this.goodsBean = goodsBean;
        this.specLists = specLists;
        this.storeLists = storeLists;
        getTagBeans(goodsBean);
        v = inflater.inflate(R.layout.pop_goods_detail_view, null);
        pop = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(cancelAble);
        pop.setAnimationStyle(R.style.dialog_bottom_animal);
        pop.setFocusable(true);

        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        initView(v, 1);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });

    }

    public void setCancelAble(boolean cancelAble) {
        this.cancelAble = cancelAble;
    }

    public void show(View root, ShoppingCartClick click) {
        if (pop == null) {
            return;
        }
        pop.showAtLocation(root, Gravity.CENTER, 0, 0);

        this.click = click;
    }

    public void dismiss() {
        if (pop == null) {
            return;
        }
        this.click = null;
        pop.dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_close_select:
                if (pop != null) {
                    pop.dismiss();
                }
                break;
        }
    }

    public void setGoodsBean(GoodsDetailRootBean goodsBean) {
        this.goodsBean = goodsBean;
//        getTagBeans(goodsBean);
        initSelectedInfo();
        Log.e("TAG", "当前规格:" + currentSpecId + " 当前店铺类型:" + currentStoreType);
        initView(v, 1);
    }

    private void initSelectedInfo() {
        if (currentSpecId != -1) {
            for (int i = 0; i < specLists.size(); i++) {
                Log.e("TAG", "specListsId:" + specLists.get(i).getId() + "---position:" + i);
                if (specLists.get(i).getId() == currentSpecId) {
                    specLists.get(i).setChecked(true);
                } else {
                    specLists.get(i).setChecked(false);
                }
            }
        }
        if (currentStoreType != -1) {
            for (int i = 0; i < storeLists.size(); i++) {
                Log.e("TAG", "storeListsId:" + storeLists.get(i).getId() + "---position:" + i);
                if (storeLists.get(i).getId() == currentStoreType) {
                    storeLists.get(i).setChecked(true);
                } else {
                    storeLists.get(i).setChecked(false);
                }
            }
        }
    }

    public void initView(View v, int index) {
        //商品信息
        ivGoodsPic = v.findViewById(R.id.iv_goods_pic);
        tvGoodsName = v.findViewById(R.id.tv_goods_name);
        tvGoodsPrice = v.findViewById(R.id.tv_price);
        tvSelectedGoodsSpec = v.findViewById(R.id.tv_selected);
        llStorageContainer = v.findViewById(R.id.ll_storage_type_container);
//        tvAddGoodsToCart = v.findViewById(R.id.tv_add_goods);
        tvAdd = v.findViewById(R.id.tv_add);
        tvNum = v.findViewById(R.id.tv_num);
        tvSub = v.findViewById(R.id.tv_sub);
        String num="";
        if(goodsBean!=null){
            if(goodsBean.getData()!=null){
                if(goodsBean.getData().getGoods_goods()!=null){
                    num = goodsBean.getData().getGoods_goods().getNum()+"";
                }
            }
        }

        tvNum.setText(num);

        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //减
                if (goodsBean.getData().getGoods_goods().getNum() > 1) {
                    goodsBean.getData().getGoods_goods().setNum(goodsBean.getData().getGoods_goods().getNum() - 1);
                }
                tvNum.setText("" + goodsBean.getData().getGoods_goods().getNum());
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //加
                if (goodsBean.getData().getGoods_goods().getNum() < goodsBean.getData().getUsenumber()) {
                    goodsBean.getData().getGoods_goods().setNum(goodsBean.getData().getGoods_goods().getNum() + 1);
                }
                tvNum.setText("" + goodsBean.getData().getGoods_goods().getNum());
            }
        });

//        tvAddGoodsToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Integer.valueOf(tvNum.getText().toString()) > goodsBean.getData().getUsenumber()) {
//                    ToastUtils.show(context, "库存不足");
//                } else {
//                    ((GoodsDetailActivity) context).addToCart(currentSpecId, currentStoreType, Integer.valueOf(tvNum.getText().toString()));
//                }
//            }
//        });

        // bottom bar click event
        v.findViewById(R.id.rl_contact_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GoodsDetailActivity) context).contactService();
            }
        });
        v.findViewById(R.id.rl_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GoodsDetailActivity) context).openShoppingCar();
            }
        });
        v.findViewById(R.id.rl_add_to_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GoodsDetailActivity) context).addToCollection();
            }
        });
        v.findViewById(R.id.tv_buy_immediately).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TOD 立即购买
//                ((GoodsDetailActivity) context).buyImmediately();
                if (Integer.valueOf(tvNum.getText().toString()) > goodsBean.getData().getUsenumber()) {
                    ToastUtils.show(context, "库存不足");
                } else {
                    ((GoodsDetailActivity) context).buyImmediately(currentSpecId, currentStoreType, Integer.valueOf(tvNum.getText().toString()));
//                    ((GoodsDetailActivity) context).addToCart(currentSpecId, currentStoreType, Integer.valueOf(tvNum.getText().toString()));
                }
            }
        });
        v.findViewById(R.id.tv_add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(tvNum.getText().toString()) > goodsBean.getData().getUsenumber()) {
                    ToastUtils.show(context, "库存不足");
                } else {
                    ((GoodsDetailActivity) context).addToCart(currentSpecId, currentStoreType, Integer.valueOf(tvNum.getText().toString()));
                }
            }
        });

        initGoodsInfo(goodsBean);
        llTagsContainer = v.findViewById(R.id.ll_tags_container);
        ivClosePop = v.findViewById(R.id.iv_close_select);
        ivClosePop.setOnClickListener(this);
        if (index != 0) {
            Log.e("TAG", "执行");
            initTags();
            initStorageTypes();
        }
    }

    private void initStorageTypes() {
        llStorageContainer.removeAllViews();
        for (int i = 0; i < storeLists.size(); i++) {
            final int index = i;
            View childView = inflater.inflate(R.layout.text_tag_view, null);
            TextView tvChildTagName = childView.findViewById(R.id.tv_tag_name);
            tvChildTagName.setText(storeLists.get(i).getStore());
            if (storeLists.get(i).isChecked()) {
                tvChildTagName.setTextColor(Color.parseColor("#ff0000"));
                tvChildTagName.setBackgroundResource(R.drawable.shape_of_goods_spe_selected);
            } else {
                tvChildTagName.setTextColor(Color.parseColor("#aaaaaa"));
                tvChildTagName.setBackgroundResource(R.drawable.shape_of_verify_code_btn);
            }
            tvChildTagName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < storeLists.size(); i++) {
                        storeLists.get(i).setChecked(false);
                    }
                    storeLists.get(index).setChecked(true);
                    currentStoreType = storeLists.get(index).getId();
                    initStorageTypes();
                }
            });
            llStorageContainer.addView(childView);
        }
    }

    private boolean hasChecked(ArrayList<Speclist> specLists) {
        for (int i = 0; i < specLists.size(); i++) {
            if (specLists.get(i).isChecked()) {
                currentSpecId = specLists.get(i).getId();
                return true;
            }
        }
        return false;
    }

    private boolean hasChecked_1(ArrayList<Storelist> storeLists) {
        for (int i = 0; i < storeLists.size(); i++) {
            if (storeLists.get(i).isChecked()) {
                currentStoreType = storeLists.get(i).getId();
                return true;
            }
        }
        return false;
    }

    private void getTagBeans(GoodsDetailRootBean goodsBean) {
        if (!hasChecked(specLists)) {
            if (specLists.size() != 0) {
                specLists.get(0).setChecked(true);
                currentSpecId = specLists.get(0).getId();
            }
        }
        if (!hasChecked_1(storeLists)) {
            if (storeLists.size() != 0) {
                storeLists.get(0).setChecked(true);
                currentStoreType = storeLists.get(0).getId();
            }
        }
    }

    private void initGoodsInfo(GoodsDetailRootBean goodsBean) {
        try {
            Glide.with(context).load(goodsBean.getData().getGoods_goods().getImg().get(0)).apply(new RequestOptions().placeholder(R.drawable.bg_home_lay10_1).error(R.drawable.bg_home_lay10_1).override(200, 200)).into(ivGoodsPic);
            tvGoodsName.setText(goodsBean.getData().getGoods_goods().getName());
            tvGoodsPrice.setText("" + goodsBean.getData().getPrice());
            tvSelectedGoodsSpec.setText("已选:" + goodsBean.getData().getGoods_goods().getSpec());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTags() {
        llTagsContainer.removeAllViews();
        for (int j = 0; j < 1; j++) {
            View view = inflater.inflate(R.layout.pop_goods_spe_item, null);
            internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.LinearLayout llTags = view.findViewById(R.id.ll_tag_container);
            llTags.removeAllViews();
            for (int i = 0; i < specLists.size(); i++) {
                final int index = i;
                final View childView = inflater.inflate(R.layout.text_tag_view, null);
                TextView tvChildTagName = childView.findViewById(R.id.tv_tag_name);
                tvChildTagName.setText(specLists.get(i).getSpec());
                if (specLists.get(i).isChecked()) {
                    tvChildTagName.setTextColor(Color.parseColor("#ff0000"));
                    tvChildTagName.setBackgroundResource(R.drawable.shape_of_goods_spe_selected);
                } else {
                    tvChildTagName.setTextColor(Color.parseColor("#aaaaaa"));
                    tvChildTagName.setBackgroundResource(R.drawable.shape_of_verify_code_btn);
                }
                tvChildTagName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < specLists.size(); i++) {
                            specLists.get(i).setChecked(false);
                        }
                        specLists.get(index).setChecked(true);
                        currentSpecId = specLists.get(index).getId();
                        initTags();
                        ((GoodsDetailActivity) context).initData(currentSpecId);
                    }
                });
                llTags.addView(childView);
            }
            llTagsContainer.addView(view);
        }
    }


    public interface ShoppingCartClick {
        void onClick(int type, double rentPrice, double buyPrice);
    }

}
