package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

/**
 * Created by wuqaing on 2018/12/1.
 */

public class RightClassInChildBean {
    private String className;
    private String imgUrl;
    private int id;
    private boolean isChecked;

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {

        return isChecked;
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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getClassName() {

        return className;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
