/**
 * Copyright 2018 bejson.com
 */
package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean;

import java.util.List;

/**
 * Auto-generated: 2018-12-21 9:48:29
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Goods_goods {

    private int id;
    private String name;
    private Goods_temp goods_temp;
    private Goods_brand goods_brand;
//    private Base_area base_area;
    private Goods_cate goods_cate;
    private String spec;
    private List<String> img;
    private Base_unit base_unit;
    private String barcode;
    private String keyword;
    //private Customs_hs customs_hs;
    //private String goods_attr;
    private String describe;
    private String detail;
    private int quality;
    private int num_p;
    private double weight;
    private int volume;
    private int num = 1;
    private String instructions;
    private String goods_attr_html;

    public void setGoods_attr_html(String goods_attr_html) {
        this.goods_attr_html = goods_attr_html;
    }

    public String getGoods_attr_html() {

        return goods_attr_html;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getInstructions() {

        return instructions;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {

        return num;
    }

    private List<Integer> speclist;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGoods_temp(Goods_temp goods_temp) {
        this.goods_temp = goods_temp;
    }

    public Goods_temp getGoods_temp() {
        return goods_temp;
    }

    public void setGoods_brand(Goods_brand goods_brand) {
        this.goods_brand = goods_brand;
    }

    public Goods_brand getGoods_brand() {
        return goods_brand;
    }

//    public void setBase_area(Base_area base_area) {
//        this.base_area = base_area;
//    }
//
//    public Base_area getBase_area() {
//        return base_area;
//    }

    public void setGoods_cate(Goods_cate goods_cate) {
        this.goods_cate = goods_cate;
    }

    public Goods_cate getGoods_cate() {
        return goods_cate;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSpec() {
        return spec;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public List<String> getImg() {
        return img;
    }

    public void setBase_unit(Base_unit base_unit) {
        this.base_unit = base_unit;
    }

    public Base_unit getBase_unit() {
        return base_unit;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

//    public void setCustoms_hs(Customs_hs customs_hs) {
//        this.customs_hs = customs_hs;
//    }
//
//    public Customs_hs getCustoms_hs() {
//        return customs_hs;
//    }

//    public void setGoods_attr(String goods_attr) {
//        this.goods_attr = goods_attr;
//    }
//
//    public String getGoods_attr() {
//        return goods_attr;
//    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }

    public void setNum_p(int num_p) {
        this.num_p = num_p;
    }

    public int getNum_p() {
        return num_p;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setSpeclist(List<Integer> speclist) {
        this.speclist = speclist;
    }

    public List<Integer> getSpeclist() {
        return speclist;
    }

}