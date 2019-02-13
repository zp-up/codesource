/**
  * Copyright 2018 bejson.com 
  */
package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.homeBanner;
import java.util.List;

/**
 * Auto-generated: 2018-12-17 16:47:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BannerRootBean {

    private int code;
    private String msg;
    private int state;
    private List<Data> data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setState(int state) {
         this.state = state;
     }
     public int getState() {
         return state;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}