package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

/**
 * Created by wuqaing on 2018/11/30.
 */

public class LeftClassBean {
    private String className;
    private boolean isChecked;
    private int id;
    private String imgUrl;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {

        return imgUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getClassName() {
        return className;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
