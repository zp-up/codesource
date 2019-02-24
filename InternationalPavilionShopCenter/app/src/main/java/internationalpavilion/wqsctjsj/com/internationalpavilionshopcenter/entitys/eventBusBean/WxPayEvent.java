package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean;

/**
 * Created by wuqaing on 2019/2/23.
 */

public class WxPayEvent {
    private int code;
    private String msg;

    public WxPayEvent(int code, String msg) {
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
