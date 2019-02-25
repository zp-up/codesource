package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.R;


/**
 * WalletDialog
 * Created by Vidge.Gang
 * Time:2019/1/21
 * Email:Vidge.Gang@harman.com
 * the class is show alert of warning
 * Dialog 提示窗口类，主要是设置提示dialog的样式、属性，以及点击事件回调
 */
public class WalletDialog extends Dialog implements View.OnClickListener {
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * dialog提示框上button的点击回调
     */
    private OnButtonClickListener mClickListener;
    /**
     * 当前dialog对象
     */
    private Dialog dialog = null;

    public WalletDialog(Context context) {
        super(context, R.style.warning_dialog_theme);
        this.context = context;
    }

    /**
     * Show  the dialog
     *
     * @param clickListener 点击回调方法
     */
    public void showDialog(OnButtonClickListener clickListener, int canUse) {
        this.mClickListener = clickListener;
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_wallet_layout, null);
        TextView tvNegative = contentView.findViewById(R.id.tv_negative_button);
        TextView tvCanUseMoney = contentView.findViewById(R.id.tv_can_use_money);
        tvCanUseMoney.setText("可用：￥" + canUse + " 元");
        final TextView tvUsedMoney = contentView.findViewById(R.id.tv_used_money);
        SeekBar seekBar = contentView.findViewById(R.id.seek_bar);
        seekBar.setMax(canUse);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvUsedMoney.setText("已选：￥" + progress + " 元");
                if (mClickListener != null) {
                    mClickListener.onProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tvNegative.setOnClickListener(this);
        setContentView(contentView);
        dialog = this;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; //设置宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_negative_button:
                if (dialog != null) {
                    dialog.dismiss();
                }
                mClickListener.onNegativeClick();
                break;
        }
    }

    /**
     * 定义点击事件回调接口
     */
    public interface OnButtonClickListener {

        /**
         * 取消按钮点击回调方法
         */
        void onNegativeClick();

        void onProgress(int progress);
    }
}
