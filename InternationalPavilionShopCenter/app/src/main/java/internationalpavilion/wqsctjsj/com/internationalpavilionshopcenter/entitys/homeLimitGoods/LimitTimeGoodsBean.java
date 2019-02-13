package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeLimitGoods;

/**
 * Created by wuqaing on 2018/12/19.
 */

public class LimitTimeGoodsBean {
    private int id;
    private String goodsName;
    private double price;
    private long startTime;
    private long endTime;

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndHour() {

        return endHour;
    }

    private int endHour;

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsImg() {

        return goodsImg;
    }

    private String goodsImg;

    public void setId(int id) {
        this.id = id;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getId() {

        return id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public double getPrice() {
        return price;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
