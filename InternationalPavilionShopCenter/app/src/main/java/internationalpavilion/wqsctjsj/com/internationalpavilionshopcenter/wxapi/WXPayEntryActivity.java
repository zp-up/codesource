package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.wxapi;

import android.app.Activity;
import android.content.Intent;
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

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.WxPayEvent;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils.ToastUtils;


/**
 * Created by wuqaing on 2018/8/28.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxc9468a3a1bf25156", false);
        api.registerApp("wxc9468a3a1bf25156");
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
                    EventBus.getDefault().post(new WxPayEvent(1, "支付成功"));
                    WXPayEntryActivity.this.finish();
                    break;
                //错误
                case -1:
                    Log.e("TAG", resp.errCode + "");
                    ToastUtils.show(WXPayEntryActivity.this, "支付失败");
                    EventBus.getDefault().post(new WxPayEvent(-1, "支付失败"));
                    WXPayEntryActivity.this.finish();
                    break;
                //取消
                case -2:
                    ToastUtils.show(WXPayEntryActivity.this, "用户取消支付");
                    Log.e("TAG", resp.errCode + "");
                    EventBus.getDefault().post(new WxPayEvent(-2, "用户取消支付"));
                    WXPayEntryActivity.this.finish();
                    break;
            }

        }
    }
}
