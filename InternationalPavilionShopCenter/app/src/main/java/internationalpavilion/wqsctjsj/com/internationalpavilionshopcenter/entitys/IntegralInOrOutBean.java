package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class IntegralInOrOutBean {
    private double price;
    private int inOrOut;
    private String content;

    public IntegralInOrOutBean(double price, int inOrOut, String content) {
        this.price = price;
        this.inOrOut = inOrOut;
        this.content = content;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInOrOut(int inOrOut) {
        this.inOrOut = inOrOut;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {

        return price;
    }

    public int getInOrOut() {
        return inOrOut;
    }

    public String getContent() {
        return content;
    }
}
