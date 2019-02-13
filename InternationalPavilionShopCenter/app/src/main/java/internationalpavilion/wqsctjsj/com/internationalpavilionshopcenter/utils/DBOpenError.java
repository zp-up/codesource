package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;

/**
 * Created by wuqaing on 2018/7/7.
 */

public class DBOpenError extends RuntimeException{
    private String msg;
    public DBOpenError(String msg){
        this.msg = msg;
    }
}
