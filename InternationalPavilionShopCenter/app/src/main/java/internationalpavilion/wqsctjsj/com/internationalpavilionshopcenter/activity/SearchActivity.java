package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.SearchOperationImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.SearchOperationInterface;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PasswordCheckUtils;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.PhoneNumberCheckUtils;
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

    private SearchOperationInterface searchOperationPresenter;
    private List<String> hotSearch = new ArrayList<>();
    private List<String> historySearch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchOperationPresenter = new SearchOperationImp();
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initData();
        initViews();
    }

    private void initViews() {
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
                    ToastUtils.show(SearchActivity.this, "搜素：" + etSearchInput.getText().toString());
                }
            }
        });
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
