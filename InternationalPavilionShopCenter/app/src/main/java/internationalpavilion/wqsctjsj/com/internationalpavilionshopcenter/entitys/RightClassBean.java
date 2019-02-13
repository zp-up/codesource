package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import java.util.ArrayList;

/**
 * Created by wuqaing on 2018/12/1.
 */

public class RightClassBean {
    private String mainClassName;
    private int id;
    private String currentUrl;
    private ArrayList<RightClassInChildBean> childBeans;

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getCurrentUrl() {

        return currentUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public void setChildBeans(ArrayList<RightClassInChildBean> childBeans) {
        this.childBeans = childBeans;
    }

    public String getMainClassName() {

        return mainClassName;
    }

    public ArrayList<RightClassInChildBean> getChildBeans() {
        return childBeans;
    }
}
