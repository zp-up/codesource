package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.classBrandBean;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.RightClassInChildBean;

/**
 * Created by wuqaing on 2018/12/20.
 */

public class ClassBrandBean {
    private int id;
    private String name;
    private ArrayList<RightClassInChildBean> children;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildren(ArrayList<RightClassInChildBean> children) {
        this.children = children;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<RightClassInChildBean> getChildren() {
        return children;
    }
}
