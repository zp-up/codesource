package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

/**
 * Created by Administrator on 2019/1/9/009.
 */

public class CartGood {
    private boolean isChecked;
    private String imagePath;
    private String name;
    private String info;
    private double price;//现价
    private double originalPrice;//原价
    private double taxation;
    private int num;
    private int id;

    public CartGood(boolean isChecked,String imagePath, String name, String info, double price, double originalPrice, double taxation, int num) {
        this.isChecked = isChecked;
        this.imagePath = imagePath;
        this.name = name;
        this.info = info;
        this.price = price;
        this.originalPrice = originalPrice;
        this.taxation = taxation;
        this.num = num;
    }

    public CartGood() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getTaxation() {
        return taxation;
    }

    public void setTaxation(double taxation) {
        this.taxation = taxation;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
