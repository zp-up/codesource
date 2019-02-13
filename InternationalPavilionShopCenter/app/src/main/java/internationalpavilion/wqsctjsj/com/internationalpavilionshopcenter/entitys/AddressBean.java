package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import java.io.Serializable;

/**
 * Created by wuqaing on 2018/12/5.
 */

public class AddressBean implements Serializable{
    private int id;
    private String province;
    private String area;
    private String city;
    private String detailPlace;
    private boolean isChecked;
    private String receiveName;
    private String receivePhone;

    public void setId(int id) {
        this.id = id;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public int getId() {

        return id;
    }

    public String getProvince() {
        return province;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getDetailPlace() {
        return detailPlace;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }
}
