package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean;

/**
 * Author:chris - jason
 * Date:2019/2/21.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean
 */

public class AddressUpdateEvent {

    private int op;//1,新增地址 2，修改地址
    private boolean res;//操作结果

    public AddressUpdateEvent() {

    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }
}
