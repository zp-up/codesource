package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.customerService;

import java.util.List;

/**
 * Author:chris - jason
 * Date:2019/2/24.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.customerService
 * 客服消息实体
 */

public  class CustomerServiceMessage {

    private int id;

    private int direction;//1:接收的消息 2：发送的消息

    private String title;

    private String text;

    private String img;

    private List<String> questionList;

    public CustomerServiceMessage() {
    }

    public List<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<String> questionList) {
        this.questionList = questionList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
