package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

/**
 * Created by wuqaing on 2019/1/14.
 */

public class PriceData {
    private int id ;
    private String priceSection;
    private boolean isChecked;

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {

        return isChecked;
    }

    public void setPriceSection(String priceSection) {
        this.priceSection = priceSection;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public String getPriceSection() {
        return priceSection;
    }
}
