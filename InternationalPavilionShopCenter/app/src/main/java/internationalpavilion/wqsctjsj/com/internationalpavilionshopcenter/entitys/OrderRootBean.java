package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import java.util.List;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.orderBeans.OrderGoodsBean;

/**
 * Created by wuqaing on 2018/12/4.
 */

public class OrderRootBean {
    private int id;//订单id
    private String orderNumber;//订单编号
    private String status;//交易状态
    private int state;
    private int count;
    private int type;
    private String afterSaleState;//售后状态
    private String refund_goods;//退货状态
    private long refund_goods_time;//退货时间
    private String refund_state;//退款状态
    private long refund_time;//退款时间
    private String storeType;//仓库类型
    private String storeName;//仓库名
    private String create_time;//订单创建时间
    private double pay_total;//支付总额
    private List<OrderGoodsBean> goodsBeans;
    private String post;//配送方式
    private double total_tax;//收税金额
    private double total_goods;//商品金额
    private double total_total;//应付金额
    private AddressBean addressBean;
    private double postPrice;//运费
    private double weight;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {

        return weight;
    }

    public void setPostPrice(double postPrice) {
        this.postPrice = postPrice;
    }

    public double getPostPrice() {

        return postPrice;
    }

    public void setAddressBean(AddressBean addressBean) {
        this.addressBean = addressBean;
    }

    public AddressBean getAddressBean() {

        return addressBean;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public void setTotal_goods(double total_goods) {
        this.total_goods = total_goods;
    }

    public void setTotal_total(double total_total) {
        this.total_total = total_total;
    }

    public String getPost() {

        return post;
    }

    public double getTotal_tax() {
        return total_tax;
    }

    public double getTotal_goods() {
        return total_goods;
    }

    public double getTotal_total() {
        return total_total;
    }

    public void setGoodsBeans(List<OrderGoodsBean> goodsBeans) {
        this.goodsBeans = goodsBeans;
    }

    public List<OrderGoodsBean> getGoodsBeans() {

        return goodsBeans;
    }

    public OrderRootBean() {
    }

    public int getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getAfterSaleState() {
        return afterSaleState;
    }

    public String getRefund_goods() {
        return refund_goods;
    }

    public long getRefund_goods_time() {
        return refund_goods_time;
    }

    public String getRefund_state() {
        return refund_state;
    }

    public long getRefund_time() {
        return refund_time;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getCreate_time() {
        return create_time;
    }

    public double getPay_total() {
        return pay_total;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAfterSaleState(String afterSaleState) {
        this.afterSaleState = afterSaleState;
    }

    public void setRefund_goods(String refund_goods) {
        this.refund_goods = refund_goods;
    }

    public void setRefund_goods_time(long refund_goods_time) {
        this.refund_goods_time = refund_goods_time;
    }

    public void setRefund_state(String refund_state) {
        this.refund_state = refund_state;
    }

    public void setRefund_time(long refund_time) {
        this.refund_time = refund_time;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setPay_total(double pay_total) {
        this.pay_total = pay_total;
    }

    public OrderRootBean(int state, int count, int type) {
        this.state = state;
        this.count = count;
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {

        return type;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getState() {

        return state;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "OrderRootBean{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", status='" + status + '\'' +
                ", state=" + state +
                ", count=" + count +
                ", type=" + type +
                ", afterSaleState='" + afterSaleState + '\'' +
                ", refund_goods='" + refund_goods + '\'' +
                ", refund_goods_time=" + refund_goods_time +
                ", refund_state='" + refund_state + '\'' +
                ", refund_time=" + refund_time +
                ", storeType='" + storeType + '\'' +
                ", create_time='" + create_time + '\'' +
                ", pay_total=" + pay_total +
                ", goodsBeans=" + goodsBeans +
                ", post='" + post + '\'' +
                ", total_tax=" + total_tax +
                ", total_goods=" + total_goods +
                ", total_total=" + total_total +
                ", addressBean=" + addressBean +
                ", postPrice=" + postPrice +
                ", weight=" + weight +
                '}';
    }
}
