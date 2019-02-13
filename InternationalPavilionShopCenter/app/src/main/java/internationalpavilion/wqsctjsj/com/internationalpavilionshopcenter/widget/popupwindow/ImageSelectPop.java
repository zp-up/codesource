package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;


public class ImageSelectPop implements View.OnClickListener {

    private int count = 1;
    private PopupWindow pop;
    private ItemClick click;
    boolean cancelAble = true;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout llBackAll;
    private LinearLayout llChangeGoods;
    private LinearLayout llBackMoney;
    private TextView tvCancel;


    public ImageSelectPop(Context c) {

        context = c;
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        View v = inflater.inflate(R.layout.pop_image_select_layout, null);
        pop = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(cancelAble);
        pop.setAnimationStyle(R.style.dialog_bottom_animal);
        pop.setFocusable(true);

        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        initView(v);

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

    public void show(View root, ItemClick click) {
        if (pop == null) {
            return;
        }
        pop.showAtLocation(root, Gravity.BOTTOM, 0, 0);

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
                if (pop != null){
                    pop.dismiss();
                }
                break;
            case R.id.ll_see_big_pic:
                if (click != null && pop != null){
                    click.onClick(1,0.0,0.0);
                    pop.dismiss();
                }
                break;
            case R.id.ll_take_photo:
                if (click != null && pop != null){
                    click.onClick(2,0.0,0.0);
                    pop.dismiss();

                }
                break;
            case R.id.ll_open_album:
                if (click != null && pop != null){
                    click.onClick(3,0.0,0.0);
                    pop.dismiss();
                }
                break;
            case R.id.tv_cancel:
                if (click != null && pop != null){
                    click.onClick(-1,0.0,0.0);
                    pop.dismiss();
                }
                break;
        }
    }


    void initView(View v) {
        llBackAll = v.findViewById(R.id.ll_see_big_pic);
        llChangeGoods = v.findViewById(R.id.ll_take_photo);
        llBackMoney = v.findViewById(R.id.ll_open_album);
        tvCancel = v.findViewById(R.id.tv_cancel);
        llBackAll.setOnClickListener(this);
        llChangeGoods.setOnClickListener(this);
        llBackMoney.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }





    public interface ItemClick {
        void onClick(int type, double rentPrice, double buyPrice);
    }

}
