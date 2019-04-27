package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import java.util.List;

/**
 * Created by wangm on 2019/4/18.
 */

public class AuthenticationListBean {


    /**
     * msg : []
     * code : 0
     * data : [{"front_state":1,"user_user":0,"address":"","create_time":"1555407069.914","back":"","type":"身份证","error":"","update_time":"1555407069.914","name":"*端","back_state":1,"id":354,"front":"","card":"5101***********512","status":0},{"front_state":1,"user_user":0,"address":"","create_time":"1555407075.092","back":"","type":"身份证","error":"","update_time":"1555407075.092","name":"*端","back_state":1,"id":355,"front":"","card":"5101***********512","status":0},{"front_state":1,"user_user":0,"address":"","create_time":"1555407195.555","back":"","type":"身份证","error":"","update_time":"1555407195.555","name":"*端","back_state":1,"id":356,"front":"","card":"5101***********512","status":0}]
     * count : 3
     * limit : 10
     * state : 0
     * page : 1
     */
    private String msg;
    private int code;
    private List<DataEntity> data;
    private int count;
    private String limit;
    private int state;
    private int page;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public String getLimit() {
        return limit;
    }

    public int getState() {
        return state;
    }

    public int getPage() {
        return page;
    }

    public class DataEntity {
        /**
         * front_state : 1
         * user_user : 0
         * address :
         * create_time : 1555407069.914
         * back :
         * type : 身份证
         * error :
         * update_time : 1555407069.914
         * name : *端
         * back_state : 1
         * id : 354
         * front :
         * card : 5101***********512
         * status : 0
         */
        private int front_state;
        private int user_user;
        private String address;
        private String create_time;
        private String back;
        private String type;
        private String error;
        private String update_time;
        private String name;
        private int back_state;
        private int id;
        private String front;
        private String card;
        private int status;

        public void setFront_state(int front_state) {
            this.front_state = front_state;
        }

        public void setUser_user(int user_user) {
            this.user_user = user_user;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setError(String error) {
            this.error = error;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBack_state(int back_state) {
            this.back_state = back_state;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setFront(String front) {
            this.front = front;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getFront_state() {
            return front_state;
        }

        public int getUser_user() {
            return user_user;
        }

        public String getAddress() {
            return address;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getBack() {
            return back;
        }

        public String getType() {
            return type;
        }

        public String getError() {
            return error;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getName() {
            return name;
        }

        public int getBack_state() {
            return back_state;
        }

        public int getId() {
            return id;
        }

        public String getFront() {
            return front;
        }

        public String getCard() {
            return card;
        }

        public int getStatus() {
            return status;
        }
    }
}
