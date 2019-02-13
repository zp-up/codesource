package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import java.util.ArrayList;

/**
 * Created by wuqaing on 2018/12/3.
 */

public class CartRootBean {
    private String cartGoodsName;
    private int id;
    private String storeType;
    private double totalPrice;


    private ArrayList<CartGood> mCartGood;

    public CartRootBean(String cartGoodsName, ArrayList<CartGood> mCartGood){
        this.cartGoodsName = cartGoodsName;
        this.mCartGood = mCartGood;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {

        return totalPrice;
    }

    public CartRootBean() {
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public int getId() {

        return id;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getCartGoodsName() {
        return cartGoodsName;
    }

    public void setCartGoodsName(String cartGoodsName) {
        this.cartGoodsName = cartGoodsName;
    }

    public ArrayList<CartGood> getmCartGood() {
        return mCartGood;
    }

    public void setmCartGood(ArrayList<CartGood> mCartGood) {
        this.mCartGood = mCartGood;
    }

}
