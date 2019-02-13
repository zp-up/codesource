package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * TODO<json数据源>
 *
 * @author: 小嵩
 * @date: 2017/3/16 15:36
 */

public class JsonBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String areaname;
    private List<CityBean> childsArea;

    public String getName() {
        return areaname;
    }

    public void setName(String name) {
        this.areaname = name;
    }

    public List<CityBean> getCityList() {
        return childsArea;
    }

    public void setCityList(List<CityBean> city) {
        this.childsArea = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.areaname;
    }



    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        private String areaname;
        private List<AreaInfo> childsArea;

        public String getName() {
            return areaname;
        }

        public void setName(String name) {
            this.areaname = name;
        }

        public List<AreaInfo> getArea() {
            return childsArea;
        }

        public void setArea(List<AreaInfo> area) {
            this.childsArea = area;
        }
    }
    public static class AreaInfo{
        private String areaname;


        public void setName(String areaname) {
            this.areaname = areaname;
        }

        public String getName() {

            return areaname;
        }
    }
}
