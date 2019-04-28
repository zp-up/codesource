/**
  * Copyright 2018 bejson.com 
  */
package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean;
import java.util.List;

/**
 *
 * @author wuqaing
 *
 */
public class Goods_temp {

    private int id;
    private String name;
    private List<String> img;
    private String video;
    private String detail;
    private String update_time;
    private String create_time;
    private String error;
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

    public void setImg(List<String> img) {
         this.img = img;
     }
     public List<String> getImg() {
         return img;
     }

    public void setVideo(String video) {
         this.video = video;
     }
     public String getVideo() {
         return video;
     }

    public void setDetail(String detail) {
         this.detail = detail;
     }
     public String getDetail() {
         return detail;
     }

    public void setUpdate_time(String update_time) {
         this.update_time = update_time;
     }
     public String getUpdate_time() {
         return update_time;
     }

    public void setCreate_time(String create_time) {
         this.create_time = create_time;
     }
     public String getCreate_time() {
         return create_time;
     }

    public void setError(String error) {
         this.error = error;
     }
     public String getError() {
         return error;
     }

}