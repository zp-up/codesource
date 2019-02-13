package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.userInfo;

/**
 * Created by wuqaing on 2018/12/19.
 */

public class UserBean {
    private int id;
    private String name;
    private String nickName;
    private String img;
    private String userPhone;
    private String password;

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhone() {

        return userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getImg() {
        return img;
    }
}
