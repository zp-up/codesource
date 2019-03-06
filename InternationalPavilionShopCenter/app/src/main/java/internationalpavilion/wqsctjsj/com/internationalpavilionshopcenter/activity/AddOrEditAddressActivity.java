package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AddressBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AppAddress;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.JsonBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.AddressUpdateEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.LocationOperationImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.LocationAperationInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.AddressDaoConfig;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.views.OnAreaOrCityOrProvenceDataCallBack;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog;

import static internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog.SweetAlertDialog.WARNING_TYPE;


public class AddOrEditAddressActivity extends BaseAppcompatActivity implements OnAreaOrCityOrProvenceDataCallBack {
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.tv_select_city)
    TextView tvSelectCity;
    @BindView(R.id.et_receive_name)
    EditText etReceiveName;
    @BindView(R.id.et_receive_phone)
    EditText etReceivePhone;
    @BindView(R.id.et_detail_place)
    EditText etDetailPlace;
    @BindView(R.id.iv_checked)
    ImageView ivChecked;
    @BindView(R.id.tv_phone_number_notice)
    TextView tvPhoneNumberNotice;

    @BindView(R.id.tv_submit)
    TextView tvSubmit;


    private LocationAperationInterface locationOperationPresenter;
    private AddressDaoConfig addressDaoConfig;
    private List<AppAddress> appAddresses;

    private String provence = "";
    private String city = "";
    private String area = "";
    private String areaString = "";

    private boolean isChecked = false;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private AddressBean addressBean;

    private boolean isEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("isEdit", false)) {
            isEdit = true;
            addressBean = (AddressBean) getIntent().getSerializableExtra("addressInfo");
            bindDataToViews();
        }
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initViews();
        addressDaoConfig = AddressDaoConfig.getInstance();
        locationOperationPresenter = new LocationOperationImp();
        try {
            appAddresses = addressDaoConfig.getData();
            if (appAddresses != null && appAddresses.size() != 0) {
                String addressData = appAddresses.get(0).getContent();
                initJsonData(addressData);
            } else {
                initData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindDataToViews() {
        etDetailPlace.setText(addressBean.getDetailPlace());
        etReceivePhone.setText(addressBean.getReceivePhone());
        etReceiveName.setText(addressBean.getReceiveName());
        if (addressBean.isChecked()) {
            ivChecked.setImageResource(R.mipmap.icon_cart_box_selected);
            isChecked = true;
        } else {
            ivChecked.setImageResource(R.drawable.icon_unchecked_bar);
            isChecked = false;
        }
    }

    private void initViews() {
        etReceivePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 11) {
                    if (s.toString().trim().length() == 0) {
                        tvPhoneNumberNotice.setVisibility(View.GONE);
                    } else {
                        tvPhoneNumberNotice.setVisibility(View.VISIBLE);
                        tvPhoneNumberNotice.setText("* 手机号长度有误");
                    }
                } else {
                    if (PhoneNumberCheckUtils.isRealPhoneNumber(etReceivePhone.getText().toString().trim())) {
                        tvPhoneNumberNotice.setVisibility(View.GONE);
                    } else {
                        tvPhoneNumberNotice.setVisibility(View.VISIBLE);
                        tvPhoneNumberNotice.setText("* 手机号格式有误");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void hidSoftware() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(llParent.getWindowToken(), 0);
    }

    private void initData() {
        RequestParams params = new RequestParams(MainUrls.getCityAreaProvienceUrl);
        params.addBodyParameter("jf", "childsArea");
        locationOperationPresenter.getAreaOrCityOrProvence(params, this);
    }

    @OnClick({R.id.iv_back, R.id.tv_select_city, R.id.rl_check, R.id.iv_checked, R.id.tv_submit,R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_select_city:
                hidSoftware();
                OptionsPickerView pvOptions = null;

                OptionsPickerView.Builder builder = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置,通过三个级别的 list 去获取对应数据

                        tvSelectCity.setText(options1Items.get(options1).getPickerViewText() + "-"
                                + options2Items.get(options1).get(options2) + "-"
                                + options3Items.get(options1).get(options2).get(options3));
                        provence = options1Items.get(options1).getPickerViewText();
                        city = options2Items.get(options1).get(options2);
                        area = options3Items.get(options1).get(options2).get(options3);
                        areaString = options1Items.get(options1).getPickerViewText() + "-"
                                + options2Items.get(options1).get(options2) + "-"
                                + options3Items.get(options1).get(options2).get(options3);
                    }
                })

                        .setTitleText("选择地址")
                        .setDividerColor(Color.parseColor("#E5E6E7"))
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(20);
                pvOptions = builder.build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
                break;
            case R.id.rl_check:
                if (isChecked) {
                    ivChecked.setImageResource(R.drawable.icon_unchecked_bar);
                    isChecked = false;
                } else {
                    ivChecked.setImageResource(R.mipmap.icon_cart_box_selected);
                    isChecked = true;
                }
                break;
            case R.id.iv_checked:
                if (isChecked) {
                    ivChecked.setImageResource(R.drawable.icon_unchecked_bar);
                    isChecked = false;
                } else {
                    ivChecked.setImageResource(R.mipmap.icon_cart_box_selected);
                    isChecked = true;
                }
                break;
            case R.id.tv_submit:
                if (isEdit) {
                    doModify();
                } else {
                    doSubmit();
                }
                break;
            case R.id.tv_delete:
                final SweetAlertDialog dialog = new SweetAlertDialog(AddOrEditAddressActivity.this,WARNING_TYPE);
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismissWithAnimation();
                        deleteAddress();
                    }
                });
                dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismissWithAnimation();
                    }
                });
                dialog.showCancelButton(true);
                dialog.setCancelText("取消");
                dialog.setConfirmText("确定");
                dialog.setTitleText("注意");
                dialog.setContentText("是否要删除收货地址?");
                dialog.setCancelable(false);
                dialog.show();
                break;
        }
    }

    private void deleteAddress() {
        RequestParams params = new RequestParams(MainUrls.deleteAddressUrl);
        params.addBodyParameter("access_token",IPSCApplication.accessToken);
        params.addBodyParameter("id",addressBean == null?"-1":String.valueOf(addressBean.getId()));
        locationOperationPresenter.deleteAddressOperation(params,this);
    }

    private void doModify() {
        if (tvSelectCity.getText().toString().equals("请选择省市区")) {
            ToastUtils.show(AddOrEditAddressActivity.this, "请选择省市区");
            return;
        }
        if (etDetailPlace.getText().toString().length() < 4) {
            ToastUtils.show(AddOrEditAddressActivity.this, "详细地址长度不能少于4");
            return;
        }
        if (etReceiveName.getText().toString().trim().length() == 0) {
            ToastUtils.show(AddOrEditAddressActivity.this, "请输入收货人姓名");
            return;
        }
        if (etReceivePhone.getText().toString().trim().length() != 11 || !PhoneNumberCheckUtils.isRealPhoneNumber(etReceivePhone.getText().toString().trim())) {
            ToastUtils.show(AddOrEditAddressActivity.this, "请输入正确的收货人手机号");
            return;
        }
        RequestParams params = new RequestParams(MainUrls.editAddressInfoUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("id", addressBean.getId() + "");
        params.addBodyParameter("name", etReceiveName.getText().toString().trim());
        params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        params.addBodyParameter("telphone", etReceivePhone.getText().toString().trim());
        params.addBodyParameter("country", "中国");
        params.addBodyParameter("province", provence);
        params.addBodyParameter("city", city);
        params.addBodyParameter("area", area);
        params.addBodyParameter("address", etDetailPlace.getText().toString());
        params.addBodyParameter("status", isChecked ? "0" : "1");
        locationOperationPresenter.editOrAddLocationInfo(params, this);

    }

    private void doSubmit() {
        if (tvSelectCity.getText().toString().equals("请选择省市区")) {
            ToastUtils.show(AddOrEditAddressActivity.this, "请选择省市区");
            return;
        }
        if (etDetailPlace.getText().toString().length() < 4) {
            ToastUtils.show(AddOrEditAddressActivity.this, "详细地址长度不能少于4");
            return;
        }
        if (etReceiveName.getText().toString().trim().length() == 0) {
            ToastUtils.show(AddOrEditAddressActivity.this, "请输入收货人姓名");
            return;
        }
        if (etReceivePhone.getText().toString().trim().length() != 11 || !PhoneNumberCheckUtils.isRealPhoneNumber(etReceivePhone.getText().toString().trim())) {
            ToastUtils.show(AddOrEditAddressActivity.this, "请输入正确的收货人手机号");
            return;
        }
        RequestParams params = new RequestParams(MainUrls.addAddressInfoUrl);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("name", etReceiveName.getText().toString().trim());
        params.addBodyParameter("user", ((IPSCApplication) getApplication()).getUserInfo().getId() + "");
        params.addBodyParameter("telphone", etReceivePhone.getText().toString().trim());
        params.addBodyParameter("country", "中国");
        params.addBodyParameter("province", provence);
        params.addBodyParameter("city", city);
        params.addBodyParameter("area", area);
        params.addBodyParameter("address", etDetailPlace.getText().toString());
        params.addBodyParameter("status", isChecked ? "0" : "1");
        locationOperationPresenter.editOrAddLocationInfo(params, this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_add_or_edit_address;
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
        Log.e("TAG", "获取地址信息失败");
    }

    @Override
    public void onAreaInfoCallBack(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String jsonData = jsonObject.getString("resultList");
                initJsonData(jsonData);
                if (addressDaoConfig != null) {
                    addressDaoConfig.CreateOrUpdateAddressTable(jsonData, "1");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAddressInfoAddOrChangedCallBack(String result) {
        if (result != null) {
            Log.e("TAG", "修改/添加地址信息:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                int state = jsonObject.getInt("state");
                String msg = "添加收货地址失败";
                if (jsonObject.has("data")) {
                    msg = jsonObject.getJSONObject("data").getString("msg");
                }
                if (code == 0 && state == 0) {
                    AddressUpdateEvent event =new AddressUpdateEvent();
                    event.setRes(true);
                    if (isEdit){
                        event.setOp(2);
                        ToastUtils.show(AddOrEditAddressActivity.this, "修改收货地址成功");
                    }else {
                        event.setOp(1);
                        ToastUtils.show(AddOrEditAddressActivity.this, "添加收货地址成功");
                    }
                    finish();
                    //发送事件通知
                    EventBus.getDefault().post(event);
                } else {
                    ToastUtils.show(AddOrEditAddressActivity.this, msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAddressDlelteCallBack(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            int state = jsonObject.getInt("state");
            String msg = jsonObject.getString("msg");
            if (code == 0 && state == 0){
                ToastUtils.show(AddOrEditAddressActivity.this,"操作成功");
                AddressUpdateEvent event = new AddressUpdateEvent();
                event.setRes(true);
                EventBus.getDefault().post(event);
                finish();
            }else {
                ToastUtils.show(AddOrEditAddressActivity.this,msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initJsonData(String json) {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */

        ArrayList<JsonBean> jsonBean = parseData(json);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d).getName();

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

    }

    public ArrayList<JsonBean> parseData(String result) {
        //Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
