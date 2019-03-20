package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.eventBusBean;

//MainActivity 切换事件
public class MainSwitchEvent {


    private int position;

    public MainSwitchEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
