package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeWishGoodsBean;

/**
 * Created by wuqaing on 2018/12/20.
 */

public class HomeWishGoodsBean {
    private int goodsId;
    private String goodsName;
    private double goodsPrice;
    private String goodsPic;

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
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

    public String getGoodsPic() {
        return goodsPic;
    }
}
