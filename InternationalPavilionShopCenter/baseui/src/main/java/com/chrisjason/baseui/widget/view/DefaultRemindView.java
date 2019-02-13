package com.chrisjason.baseui.widget.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.chrisjason.baseui.R;


/**
 * Created by mayikang on 17/2/6.
 */

public class DefaultRemindView extends RelativeLayout implements View.OnClickListener{
    private ReloadClickListener reloadClickListener;

    public DefaultRemindView(Context context) {
        super(context);
        initView(context);
    }


    private void initView(Context context){
        RelativeLayout mRL= (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.no_network_remind_layout,this);

        RelativeLayout mRLReload= (RelativeLayout) mRL.findViewById(R.id.no_network_view_rl_reload);
        RelativeLayout mRLSetNet= (RelativeLayout) mRL.findViewById(R.id.no_network_view_rl_set_net);

        mRLReload.setOnClickListener(this);
        mRLSetNet.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.no_network_view_rl_reload) {

            if (reloadClickListener != null) {
                reloadClickListener.reload();
            }
        }

        if(view.getId()==R.id.no_network_view_rl_set_net){

            if(reloadClickListener!=null){
                reloadClickListener.setNetwork();
            }

        }
    }



    public  void setOnReloadClickListerer(ReloadClickListener reloadClickListener){
        this.reloadClickListener=reloadClickListener;
    }

    public interface ReloadClickListener{
        public void reload();
        public void setNetwork();

    }


}
