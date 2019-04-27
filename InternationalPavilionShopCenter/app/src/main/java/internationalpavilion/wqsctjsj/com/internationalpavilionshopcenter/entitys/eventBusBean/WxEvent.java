package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean;

/**
 * Created by wuqaing on 2019/2/23.
 */

/**
 * 微信事件：包含支付、授权
 * 编码细节详见：WXEntryActivity.java、WXPayEntryActivity.java
 */
public class WxEvent {
    private int code;// 1 支付成功 2 支付失败 3 用户取消支付 4 授权成功
    private String msg;

    public WxEvent(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {

        return code;
    }

    public String getMsg() {
        return msg;
    }
}
