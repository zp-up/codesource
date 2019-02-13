package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 *
 * 本地存储用户信息
 */
@Table(name = "sm_address_tbl")
public class AppAddress {
    //用户表主键 id
    @Column(name = "id",isId = true,autoGen = true)
    private int id;

    //用户 id
    @Column(name = "address_id",property = "not null")
    private String userId;

    //用户登录后的 token
    @Column(name = "content",property = "not null")
    private String content;

    public AppAddress(){

    }

    public AppAddress(String userId, String content){
        this.userId=userId;
        this.content = content;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", addressId='" + userId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
