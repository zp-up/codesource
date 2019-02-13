/**
  * Copyright 2018 bejson.com 
  */
package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsDetailBean;

/**
 * Auto-generated: 2018-12-21 9:48:29
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Storelist {

    private boolean index;
    private int id;
    private int storeid;
    private String store;
    private boolean isChecked;

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {

        return isChecked;
    }

    public void setIndex(boolean index) {
         this.index = index;
     }
     public boolean getIndex() {
         return index;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setStoreid(int storeid) {
         this.storeid = storeid;
     }
     public int getStoreid() {
         return storeid;
     }

    public void setStore(String store) {
         this.store = store;
     }
     public String getStore() {
         return store;
     }

}