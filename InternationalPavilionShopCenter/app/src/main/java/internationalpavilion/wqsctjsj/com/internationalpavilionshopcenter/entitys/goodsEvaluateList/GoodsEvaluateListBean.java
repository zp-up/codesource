package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.goodsEvaluateList;

import java.util.ArrayList;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.EvaluateImage;

/**
 * Created by wuqaing on 2018/12/25.
 */

public class GoodsEvaluateListBean {
    private int id;
    private String userHeadImgUrl;
    private int rating;
    private long insertTime;
    private String userNickName;
    private String evaluateContent;
    private ArrayList<EvaluateImage> images;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserHeadImgUrl(String userHeadImgUrl) {
        this.userHeadImgUrl = userHeadImgUrl;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public void setImages(ArrayList<EvaluateImage> images) {
        this.images = images;
    }

    public int getId() {

        return id;
    }

    public String getUserHeadImgUrl() {
        return userHeadImgUrl;
    }

    public int getRating() {
        return rating;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public ArrayList<EvaluateImage> getImages() {
        return images;
    }
}
