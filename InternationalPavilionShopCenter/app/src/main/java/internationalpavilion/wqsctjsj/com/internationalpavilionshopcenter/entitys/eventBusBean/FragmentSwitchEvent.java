package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean;

/**
 * Author:chris - jason
 * Date:2019/2/27.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean
 */
//MainActivity下几个Fragment的切换事件
public class FragmentSwitchEvent {
    private int position;//对应位置

    public FragmentSwitchEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
