package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homePopularityGoods;

/**
 * Created by wuqaing on 2018/12/20.
 */

public class HomePopularityGoodsBean {
    private int goodsId;
    private String goodsName;
    private double goodsPrice;
    private String goodsPic;
    private String storeType;

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreType() {

        return storeType;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsPic() {

        return goodsPic;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsId() {

        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }
}
