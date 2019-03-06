package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.QuestionListAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.customerService.CustomerServiceMessage;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo.UserBean;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.Items;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.MultiTypeViewBinder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.multitype.ViewHolder;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterImp.CustomerServicePresenterImp;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.CustomerServicePresenter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.presenters.presenterInterface.NetCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls.MainUrls;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.GlideRoundTransform;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ListViewUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ScreenUtil;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;

public class ContactCustomerServiceActivity extends BaseAppcompatActivity {
    @BindView(R.id.et_im_input)
    EditText etImInput;

    @BindView(R.id.rv)
    RecyclerView mRV;
    private LinearLayoutManager layoutManager;
    private MultiTypeAdapter adapter;
    private Items mItems;
    private CustomerServicePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initView();
        presenter = new CustomerServicePresenterImp();
        initIM();
    }

    private void initView() {

        initRV();

        //监听输入框

        etImInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (TextUtils.isEmpty(etImInput.getText().toString())) {
                    ToastUtils.show(ContactCustomerServiceActivity.this, "请先输入问题");
                    return true;
                }

                if (i == EditorInfo.IME_ACTION_SEND) {
                    sendMsg(etImInput.getText().toString());
                }


                return true;
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.rl_send_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ContactCustomerServiceActivity.this.finish();
                break;

            //发送消息
            case R.id.rl_send_msg:
                sendMsg(etImInput.getText().toString());
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_contact_customer_service;
    }

    @Override
    public void reloadData() {

    }

    private void initRV() {

        mItems = new Items();
        adapter = new MultiTypeAdapter(mItems);


        MultiTypeViewBinder<CustomerServiceMessage> binder = new MultiTypeViewBinder<CustomerServiceMessage>(this, R.layout.item_im_message_layout) {
            @Override
            protected void convert(ViewHolder holder, CustomerServiceMessage message, int position) {

                View root = holder.getConvertView();
                if (root != null) {
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) root.getLayoutParams();
                    if (lp != null) {
                        if (position == 0) {
                            lp.topMargin = ScreenUtil.dip2px(ContactCustomerServiceActivity.this, 24f);
                        } else {
                            lp.topMargin = 0;
                        }
                        root.setLayoutParams(lp);
                    }
                }

                //接收消息框
                LinearLayout mLLReceivedMessageBox = holder.getView(R.id.ll_received_message_box);
                CircleImageView civ_received_portrait = holder.getView(R.id.civ_received_message_portrait);
                TextView tv_received_title = holder.getView(R.id.tv_received_message_title);
                ListView lv_question = holder.getView(R.id.lv_question);
                ImageView iv_received_big_pic = holder.getView(R.id.iv_received_big_pic);

                //发送消息框
                RelativeLayout mRLSentMessageBox = holder.getView(R.id.rl_sent_message_box);
                TextView tv_sent_text = holder.getView(R.id.tv_sent_text);
                CircleImageView civ_sent_user_portrait = holder.getView(R.id.civ_sent_user_portrait);

                //接收消息
                if (1 == message.getDirection()) {
                    mLLReceivedMessageBox.setVisibility(View.VISIBLE);
                    mRLSentMessageBox.setVisibility(View.GONE);

                    //title
                    if (!TextUtils.isEmpty(message.getTitle())) {
                        tv_received_title.setVisibility(View.VISIBLE);
                        tv_received_title.setText(message.getTitle());
                    } else {
                        tv_received_title.setVisibility(View.GONE);
                    }
                    //问题列表
                    if (message.getQuestionList() != null) {
                        lv_question.setVisibility(View.VISIBLE);
                        QuestionListAdapter lvAdapter = new QuestionListAdapter(message.getQuestionList(), ContactCustomerServiceActivity.this);
                        lv_question.setAdapter(lvAdapter);
                        ListViewUtil.setHeightBasedOnAllItems(lv_question);
                    } else if (!TextUtils.isEmpty(message.getText())) {
                        lv_question.setVisibility(View.VISIBLE);
                        List<String> list = new ArrayList<>();
                        list.add(message.getText());
                        QuestionListAdapter lvAdapter = new QuestionListAdapter(list, ContactCustomerServiceActivity.this);
                        lv_question.setAdapter(lvAdapter);
                        ListViewUtil.setHeightBasedOnItemCount(lv_question, 1);
                    } else {
                        lv_question.setVisibility(View.GONE);
                    }

                    //pic
                    if (TextUtils.isEmpty(message.getImg())) {
                        iv_received_big_pic.setVisibility(View.GONE);
                    } else {
                        iv_received_big_pic.setVisibility(View.VISIBLE);
                        RequestOptions rp = new RequestOptions();
                        rp.error(R.mipmap.ic_launcher).
                                placeholder(R.drawable.ic_launcher_background).
                                bitmapTransform(new GlideRoundTransform(ContactCustomerServiceActivity.this, ScreenUtil.dip2px(ContactCustomerServiceActivity.this, 8.0f)));
                        Glide.with(ContactCustomerServiceActivity.this).
                                load(message.getImg()).
                                apply(rp).
                                into(iv_received_big_pic);
                    }
                }

                if (2 == message.getDirection()) {
                    mLLReceivedMessageBox.setVisibility(View.GONE);
                    mRLSentMessageBox.setVisibility(View.VISIBLE);
                    tv_sent_text.setText(message.getText());


                        UserBean userBean = ((IPSCApplication)getApplicationContext()).getUserInfo();

                        if (userBean != null && userBean.getImg() != null) {
                            RequestOptions options = new RequestOptions();
                            options.circleCrop();
                            options.placeholder(R.mipmap.icon_mine_defaul_head);
                            options.error(R.mipmap.icon_mine_defaul_head);
                            Glide.with(ContactCustomerServiceActivity.this).load(userBean.getImg()).apply(options).into(civ_sent_user_portrait);
                        }

                }

            }
        };

        adapter.register(CustomerServiceMessage.class, binder);
        mRV.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        mRV.setAdapter(adapter);

    }

    private void initIM() {
        RequestParams params = new RequestParams(MainUrls.getInitIMMessage);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        presenter.getInitMessage(params, new NetCallback() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    try {
                        JSONObject root = new JSONObject(result);
                        if (root != null) {

                            int code = root.getInt("code");
                            int state = root.getInt("state");
                            if (0 == code && 0 == state) {

                                JSONArray dataArray = root.getJSONArray("data");
                                if (dataArray != null && dataArray.length() > 0) {
                                    //解析数据

                                    CustomerServiceMessage message = new CustomerServiceMessage();
                                    message.setDirection(1);
                                    List<String> questionList = new ArrayList<>();

                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject obj = dataArray.getJSONObject(i);
                                        String title = obj.getString("title");
                                        questionList.add(title);
                                    }

                                    message.setQuestionList(questionList);

                                    //插入列表
                                    if (mItems.size() > 0) {
                                        mItems.clear();
                                    }
                                    mItems.add(message);
                                    adapter.notifyItemInserted(0);
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

            }
        });
    }

    public void sendMsg(String msg) {

        if (TextUtils.isEmpty(msg)) {
            ToastUtils.show(this, "请先输入问题");
            return;
        }


        CustomerServiceMessage message = new CustomerServiceMessage();
        message.setDirection(2);
        message.setText(msg);
        mItems.add(message);
        adapter.notifyItemInserted(mItems.size()-1);
        layoutManager.scrollToPositionWithOffset(mItems.size(), 0);

        etImInput.setText("");
        hideSoftInput(this);

        invokeSend(msg);
    }

    //关闭键盘
    public void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void invokeSend(String msg) {
        RequestParams params = new RequestParams(MainUrls.sendMessage);
        params.addBodyParameter("access_token", IPSCApplication.accessToken);
        params.addBodyParameter("text", msg);
        presenter.sendMessage(params, new NetCallback() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    try {
                        JSONObject root = new JSONObject(result);
                        if (root != null) {
                            int state = root.getInt("state");
                            int code = root.getInt("code");
                            if (0 == state && 0 == code) {
                                JSONArray arrayData = root.getJSONArray("data");

                                if (arrayData != null && arrayData.length() > 0) {

                                    for (int i = 0; i < arrayData.length(); i++) {
                                        CustomerServiceMessage message = new CustomerServiceMessage();
                                        JSONObject obj = arrayData.getJSONObject(i);
                                        int id = obj.getInt("id");
                                        String title = obj.getString("title");
                                        String text = obj.getString("text");
                                        String img = obj.getString("img");

                                        message.setId(id);
                                        message.setDirection(1);
                                        message.setTitle(title);
                                        message.setText(text);
                                        message.setImg(img);
                                        message.setQuestionList(null);
                                        mItems.add(message);
                                    }

                                    //插入列表
                                    adapter.notifyItemInserted(mItems.size()-1);

                                    layoutManager.scrollToPositionWithOffset(mItems.size()-1, 0);

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

            }
        });
    }

}
