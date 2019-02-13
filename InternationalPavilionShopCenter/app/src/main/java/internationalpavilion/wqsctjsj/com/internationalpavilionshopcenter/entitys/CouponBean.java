package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class CouponBean {
    private int type;
    private boolean isOpen;

    public CouponBean(int type, boolean isOpen) {
        this.type = type;
        this.isOpen = isOpen;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getType() {

        return type;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
