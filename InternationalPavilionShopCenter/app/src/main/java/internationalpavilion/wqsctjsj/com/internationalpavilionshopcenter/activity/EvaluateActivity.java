package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chrisjason.baseui.ui.BaseAppcompatActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter.CommentAdapter;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.CommentsInfo;

public class EvaluateActivity extends BaseAppcompatActivity implements CommentAdapter.OnStatusListener{
    private int orderId = -1;

    ArrayList<CommentsInfo> list = new ArrayList<>();
    CommentAdapter commentAdapter;
    @BindView(R.id.lv_comments)
    RecyclerView lvComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", -1);
        initAdapter();

    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvComment.setLayoutManager(layoutManager);
        for(int i=0;i<3;i++){
            CommentsInfo commentsInfo = new CommentsInfo();
            List<String> commentImgs = new ArrayList<>();
            commentImgs.add("");
            commentsInfo.setCommentImgs(commentImgs);
            list.add(commentsInfo);
        }
        commentAdapter = new CommentAdapter(list,this);
        commentAdapter.setOnStatusListener(this);
        lvComment.setAdapter(commentAdapter);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
    @Override
    public int initLayout() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void onSetStatusListener(int pos) {

        if(list.get(pos).getCommentImgs().size()<5){
            CommentsInfo commentsInfo = list.get(pos);
            List<String> commentImgs = commentsInfo.getCommentImgs();
            commentImgs.add(0,"http://img.xsmore.com/cjyp/Product/2ef24a07-f3b0-4497-8549-2acc0f7ca4b0.jpg");
            commentsInfo.setCommentImgs(commentImgs);
            list.set(pos,commentsInfo);
            commentAdapter.notifyItemChanged(pos);
        }else if(list.get(pos).getCommentImgs().size()==5){
            CommentsInfo commentsInfo = list.get(pos);
            List<String> commentImgs = commentsInfo.getCommentImgs();
            commentImgs.set(commentImgs.size()-1,"http://img.xsmore.com/cjyp/Product/2ef24a07-f3b0-4497-8549-2acc0f7ca4b0.jpg");
            commentsInfo.setCommentImgs(commentImgs);
            list.set(pos,commentsInfo);
            commentAdapter.notifyItemChanged(pos);
        }
    }

    @Override
    public void onDeleteListener(int pos,int tagPos) {
        CommentsInfo commentsInfo = list.get(pos);
        List<String> commentImgs = commentsInfo.getCommentImgs();
        if(!commentImgs.get(commentImgs.size()-1).equals("")){
            commentImgs.add("");
        }
        commentImgs.remove(tagPos);
        commentsInfo.setCommentImgs(commentImgs);
        list.set(pos,commentsInfo);
        commentAdapter.notifyItemChanged(pos);
    }
}
