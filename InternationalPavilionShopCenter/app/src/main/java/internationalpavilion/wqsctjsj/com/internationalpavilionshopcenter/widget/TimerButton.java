package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by wuqaing on 2018/9/13.
 */

public class TimerButton extends CountDownTimer {
    public static final int TIME_COUNT = 121000;//时间防止从119s开始显示（以倒计时120s为例子）
    private TextView btn;
    private String endStrRid;
    private Context context;
    private String send_finish_txt_color, sending_txt_color, send_finish_bg_color, sending_bg_color,send_finish_border_color,sending_border_color;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimerButton(long millisInFuture, long countDownInterval, TextView tv, String text) {
        super(millisInFuture, countDownInterval);
        btn = tv;
        //发送完成字体色
        send_finish_txt_color = "#1E67AC";
        //发送中字体色
        sending_txt_color = "#aaaaaa";


    }
    //倒计时过程中
    @Override
    public void onTick(long millisUntilFinished) {
            btn.setText(millisUntilFinished / 1000 + "s后重新获取");
            btn.setTextColor(Color.parseColor(sending_txt_color));
            btn.setEnabled(false);
    }
    //计时结束
    @Override
    public void onFinish() {
        btn.setText("重新发送");
        btn.setTextColor(Color.parseColor(send_finish_txt_color));
        btn.setEnabled(true);
    }
}
