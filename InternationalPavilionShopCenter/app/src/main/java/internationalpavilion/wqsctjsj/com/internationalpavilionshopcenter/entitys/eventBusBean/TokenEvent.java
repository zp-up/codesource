package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean;

import java.io.Serializable;

/**
 * Created by wuqaing on 2018/12/19.
 */

public class TokenEvent implements Serializable{
    public int code;//code == 0(获取token) code == 1(获取缓存的用户信息) code == 2(获取用户信息)
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
