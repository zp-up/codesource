package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.orderBeans;

/**
 * Created by wuqaing on 2019/1/27.
 */

public class OrderGoodsBean {
    private int goodsId;
    private String goodsName;
    private String goodsDescription;
    private String imageUrl;
    private int count;
    private double goodsPrice;
    private double bondedPrice;

    public void setBondedPrice(double bondedPrice) {
        this.bondedPrice = bondedPrice;
    }

    public double getBondedPrice() {

        return bondedPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public double getGoodsPrice() {

        return goodsPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {

        return count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public int getGoodsId() {

        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }
}
