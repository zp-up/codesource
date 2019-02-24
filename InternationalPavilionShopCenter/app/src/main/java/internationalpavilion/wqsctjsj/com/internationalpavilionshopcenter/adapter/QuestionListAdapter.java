package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.activity.ContactCustomerServiceActivity;

/**
 * Author:chris - jason
 * Date:2019/2/24.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.adapter
 */

public class QuestionListAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;

    public QuestionListAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question_list_layout,null);
            holder = new Holder();
            holder.tvQuestion = convertView.findViewById(R.id.tv_question);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        holder.tvQuestion.setText(data.get(position));
        holder.tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof ContactCustomerServiceActivity){
                    ((ContactCustomerServiceActivity)context).sendMsg(data.get(position));
                }
            }
        });
        return convertView;
    }

    class Holder{
        TextView tvQuestion;
    }



}
