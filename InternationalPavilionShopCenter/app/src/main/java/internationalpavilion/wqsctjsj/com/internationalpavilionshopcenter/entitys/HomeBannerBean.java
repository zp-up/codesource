package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

/**
 * Created by mayikang on 2018/1/10.
 */

public class HomeBannerBean {
    private int id;
    private String imgUrl;//图片地址
    private String targetUrl;//跳转目标地址
    private String remark;
    private int toType;

    public void setToType(int toType) {
        this.toType = toType;
    }

    public int getToType() {

        return toType;
    }

    public HomeBannerBean(int id, String imgUrl, String targetUrl, String remark, int toType) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.targetUrl = targetUrl;
        this.remark = remark;
        this.toType = toType;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {

        return remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
