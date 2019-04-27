package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.WxEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;

/**
 * Created by wuqaing on 2018/8/28.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    // 微信事件编码
    /** 微信支付成功 */
    public static final int WX_EVENT_ID_PAY_SUCCESS = 0x1;
    /** 微信支付失败 */
    public static final int WX_EVENT_ID_PAY_FAILED = 0x2;
    /** 用户取消支付 */
    public static final int WX_EVENT_ID_PAY_CANCEL = 0x3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx936ef706f9fb1fe7", false);
        api.registerApp("wx936ef706f9fb1fe7");
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //支付结果判定
            switch (resp.errCode) {
                //成功
                case 0:
                    ToastUtils.show(WXPayEntryActivity.this, "支付成功");
                    Log.e("TAG", resp.errCode + "");
                    EventBus.getDefault().post(new WxEvent(WX_EVENT_ID_PAY_SUCCESS, "支付成功"));
                    finish();
                    break;
                //错误
                case -1:
                    Log.e("TAG", resp.errCode + "");
                    ToastUtils.show(WXPayEntryActivity.this, "支付失败");
                    EventBus.getDefault().post(new WxEvent(WX_EVENT_ID_PAY_FAILED, "支付失败"));
                    finish();
                    break;
                //取消
                case -2:
                    ToastUtils.show(WXPayEntryActivity.this, "用户取消支付");
                    Log.e("TAG", resp.errCode + "");
                    EventBus.getDefault().post(new WxEvent(WX_EVENT_ID_PAY_CANCEL, "用户取消支付"));
                    finish();
                    break;
            }

        }
    }
}
