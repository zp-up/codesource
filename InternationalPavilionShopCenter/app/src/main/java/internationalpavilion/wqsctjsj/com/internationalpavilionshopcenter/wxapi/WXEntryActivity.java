package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean.WxEvent;


/**
 * Created by wuqaing on 2018/8/28.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "[IPSC][WXEntryActivity]";
    private IWXAPI api;
    // WX_APP_ID 替换为你的应用从官方网站申请到的合法appID
    public static final String WX_APP_ID = "wx936ef706f9fb1fe7";
    // 微信事件编码
    /** 微信授权成功 */
    public static final int WX_EVENT_ID_AUTH_SUCCESS = 0x4;
    /** 微信授权失败 */
    public static final int WX_EVENT_ID_AUTH_FAILED = 0x5;
    /** 用户取消授权 */
    public static final int WX_EVENT_ID_AUTH_CANCEL = 0x6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq() type:" + baseReq.getType());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "onResp() errCode:" + baseResp.errCode
                + ",code:" + ((SendAuth.Resp) baseResp).code
                + ",openId:" + baseResp.openId
                + ",type:" + baseResp.getType());

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                EventBus.getDefault().post(new WxEvent(WX_EVENT_ID_AUTH_SUCCESS, ((SendAuth.Resp) baseResp).code));
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
        }
        finish();
    }
}
