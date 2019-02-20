package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Author:chris - jason
 * Date:2019/2/19.
 * Package:internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys
 */

public class GroupProductEntity implements Serializable {


    /**
     * id : 6735
     * store_store : {"name":"宿迁总仓","type":"国内仓库","id":2}
     * goods_goods : {"id":164,"name":" Lg 悬挂卡片型芳香剂 绿巨人 6g/个","goods_temp":{"id":58,"title":"漫威漫威时尚复仇者联盟2 悬挂卡片型芳香剂 ","name":"悬挂卡片型芳香剂 ","img":["https://file.gjigbuy.com/9e02ef11f4db51134ee53b9516484dd8582f7880fa7c2469aa7571b7522cd308.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/250338221d600916dd3cb23054a3d2c55e78e4fea78eea5ba54658869419002b.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/6ad7cf5ed6772d79c68b19e061ab2f74c3bf9211c9695ecbc430aa1146ad832f.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/3231e74dd17c0befb372810dc8696f1a506d0dff2056814a86af3d77d5047207.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/81c32ce42cc8440b73af82932e2786a9f42d1ea90a1214835e9c4080ccaa98f8.png?x-oss-process=style/thumb","https://file.gjigbuy.com/4fe95fdf9802045ef70a37458e19508732bcbe9356fa3de3e2e52dfe3bef4ef3.png?x-oss-process=style/thumb","https://file.gjigbuy.com/788f9ce1c88c53a4066ede7b4b7539247aa076f691d8909530e5679782a8f8f0.png?x-oss-process=style/thumb","https://file.gjigbuy.com/68a83169042b53b5dfcfbda5bfeb9a44b7a07ecb88d8130326babea99b421fb3.png?x-oss-process=style/thumb"],"video":"","detail":"","create_time":"1534757965.847","error":""},"goods_brand":{"name":"","en":"Lg","id":57,"logo":["https://file.gjigbuy.com/69c90fcf72e41981882fcb4e77e8da73d800766460e6de6c0683c3a89b3d1567.jpg?x-oss-process=style/thumb"]},"base_area":{"name":"韩国","id":9,"img":["https://file.gjigbuy.com/5b5c9cac237ae1fc0dc8483a198cdcac6141a04d129d9cd165d16a140a7fbffa.png?x-oss-process=style/thumb"]},"goods_cate":{"name":"其他清洁用品","id":268},"spec":"6g/个","img":["https://file.gjigbuy.com/57845e5a356dba3147f2caca8f2d82e56b45d8c39cfcfd086c3846d74beaa161.jpeg?x-oss-process=style/thumb"],"base_unit":{"name":"个","id":1},"barcode":"8801051330410","keyword":"绿巨人","customs_hs":0,"goods_attr":"0","describe":"","detail":"","quality":0,"num_p":0,"weight":"0.040","volume":"0.000","speclist":[160,163,164,166]}
     * price : 5.00
     * price_m : 5.00
     * price_l : 2.84
     * number : 99
     * usenumber : 96
     * admin_admin : 0
     * is_full : 0
     * full_max_num : 0
     * full_num : 0
     * full_price : 0.00
     * store_group : 0
     * is_book : 已结束
     * book_price : 19.85
     * book_add_time : 0.000
     * book_end_time : 0.000
     * is_pron : 已结束
     * pron_price : 19.85
     * pron_add_time : 0.000
     * pron_interval : 0
     * pron_end_time : 0.000
     * agio : 0.00
     * is_group : 1
     * group_price : 5.00
     * group_num_p : 2
     * group_num_x : 2
     * is_post : 0
     * is_btax : 0
     * price_tax : 0.00
     * is_receipt : 0
     * cost : 17.26
     * profit_rate : -245.20
     * money_max : 0.00
     * money_min : 100.00
     * money_rate : 0.00
     * is_wish : 0
     * is_new : 0
     * price_ratio : 15.00
     * sn :
     * num_x : 0
     * num_l : 8
     * num_r : 8
     * num_s : 0
     * status : 上架
     * update_time : 1550629833.168
     * create_time : 1543897353.604
     * error : 由系统添加
     * order_group_num : 3
     */

    private int id;
    public StoreStoreBean store_store;
    public GoodsGoodsBean goods_goods;
    private String price;
    private String price_m;
    private String price_l;
    private int number;
    private int usenumber;
    private int admin_admin;
    private int is_full;
    private int full_max_num;
    private int full_num;
    private String full_price;
    private int store_group;
    private String is_book;
    private String book_price;
    private String book_add_time;
    private String book_end_time;
    private String is_pron;
    private String pron_price;
    private String pron_add_time;
    private int pron_interval;
    private String pron_end_time;
    private String agio;
    private int is_group;
    private String group_price;
    private int group_num_p;
    private int group_num_x;
    private int is_post;
    private int is_btax;
    private String price_tax;
    private int is_receipt;
    private String cost;
    private String profit_rate;
    private String money_max;
    private String money_min;
    private String money_rate;
    private int is_wish;
    private int is_new;
    private String price_ratio;
    private String sn;
    private int num_x;
    private int num_l;
    private int num_r;
    private int num_s;
    private String status;
    private String update_time;
    private String create_time;
    private String error;
    private int order_group_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StoreStoreBean getStore_store() {
        return store_store;
    }

    public void setStore_store(StoreStoreBean store_store) {
        this.store_store = store_store;
    }

    public GoodsGoodsBean getGoods_goods() {
        return goods_goods;
    }

    public void setGoods_goods(GoodsGoodsBean goods_goods) {
        this.goods_goods = goods_goods;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_m() {
        return price_m;
    }

    public void setPrice_m(String price_m) {
        this.price_m = price_m;
    }

    public String getPrice_l() {
        return price_l;
    }

    public void setPrice_l(String price_l) {
        this.price_l = price_l;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getUsenumber() {
        return usenumber;
    }

    public void setUsenumber(int usenumber) {
        this.usenumber = usenumber;
    }

    public int getAdmin_admin() {
        return admin_admin;
    }

    public void setAdmin_admin(int admin_admin) {
        this.admin_admin = admin_admin;
    }

    public int getIs_full() {
        return is_full;
    }

    public void setIs_full(int is_full) {
        this.is_full = is_full;
    }

    public int getFull_max_num() {
        return full_max_num;
    }

    public void setFull_max_num(int full_max_num) {
        this.full_max_num = full_max_num;
    }

    public int getFull_num() {
        return full_num;
    }

    public void setFull_num(int full_num) {
        this.full_num = full_num;
    }

    public String getFull_price() {
        return full_price;
    }

    public void setFull_price(String full_price) {
        this.full_price = full_price;
    }

    public int getStore_group() {
        return store_group;
    }

    public void setStore_group(int store_group) {
        this.store_group = store_group;
    }

    public String getIs_book() {
        return is_book;
    }

    public void setIs_book(String is_book) {
        this.is_book = is_book;
    }

    public String getBook_price() {
        return book_price;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }

    public String getBook_add_time() {
        return book_add_time;
    }

    public void setBook_add_time(String book_add_time) {
        this.book_add_time = book_add_time;
    }

    public String getBook_end_time() {
        return book_end_time;
    }

    public void setBook_end_time(String book_end_time) {
        this.book_end_time = book_end_time;
    }

    public String getIs_pron() {
        return is_pron;
    }

    public void setIs_pron(String is_pron) {
        this.is_pron = is_pron;
    }

    public String getPron_price() {
        return pron_price;
    }

    public void setPron_price(String pron_price) {
        this.pron_price = pron_price;
    }

    public String getPron_add_time() {
        return pron_add_time;
    }

    public void setPron_add_time(String pron_add_time) {
        this.pron_add_time = pron_add_time;
    }

    public int getPron_interval() {
        return pron_interval;
    }

    public void setPron_interval(int pron_interval) {
        this.pron_interval = pron_interval;
    }

    public String getPron_end_time() {
        return pron_end_time;
    }

    public void setPron_end_time(String pron_end_time) {
        this.pron_end_time = pron_end_time;
    }

    public String getAgio() {
        return agio;
    }

    public void setAgio(String agio) {
        this.agio = agio;
    }

    public int getIs_group() {
        return is_group;
    }

    public void setIs_group(int is_group) {
        this.is_group = is_group;
    }

    public String getGroup_price() {
        return group_price;
    }

    public void setGroup_price(String group_price) {
        this.group_price = group_price;
    }

    public int getGroup_num_p() {
        return group_num_p;
    }

    public void setGroup_num_p(int group_num_p) {
        this.group_num_p = group_num_p;
    }

    public int getGroup_num_x() {
        return group_num_x;
    }

    public void setGroup_num_x(int group_num_x) {
        this.group_num_x = group_num_x;
    }

    public int getIs_post() {
        return is_post;
    }

    public void setIs_post(int is_post) {
        this.is_post = is_post;
    }

    public int getIs_btax() {
        return is_btax;
    }

    public void setIs_btax(int is_btax) {
        this.is_btax = is_btax;
    }

    public String getPrice_tax() {
        return price_tax;
    }

    public void setPrice_tax(String price_tax) {
        this.price_tax = price_tax;
    }

    public int getIs_receipt() {
        return is_receipt;
    }

    public void setIs_receipt(int is_receipt) {
        this.is_receipt = is_receipt;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getProfit_rate() {
        return profit_rate;
    }

    public void setProfit_rate(String profit_rate) {
        this.profit_rate = profit_rate;
    }

    public String getMoney_max() {
        return money_max;
    }

    public void setMoney_max(String money_max) {
        this.money_max = money_max;
    }

    public String getMoney_min() {
        return money_min;
    }

    public void setMoney_min(String money_min) {
        this.money_min = money_min;
    }

    public String getMoney_rate() {
        return money_rate;
    }

    public void setMoney_rate(String money_rate) {
        this.money_rate = money_rate;
    }

    public int getIs_wish() {
        return is_wish;
    }

    public void setIs_wish(int is_wish) {
        this.is_wish = is_wish;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    public String getPrice_ratio() {
        return price_ratio;
    }

    public void setPrice_ratio(String price_ratio) {
        this.price_ratio = price_ratio;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getNum_x() {
        return num_x;
    }

    public void setNum_x(int num_x) {
        this.num_x = num_x;
    }

    public int getNum_l() {
        return num_l;
    }

    public void setNum_l(int num_l) {
        this.num_l = num_l;
    }

    public int getNum_r() {
        return num_r;
    }

    public void setNum_r(int num_r) {
        this.num_r = num_r;
    }

    public int getNum_s() {
        return num_s;
    }

    public void setNum_s(int num_s) {
        this.num_s = num_s;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getOrder_group_num() {
        return order_group_num;
    }

    public void setOrder_group_num(int order_group_num) {
        this.order_group_num = order_group_num;
    }

    public static class StoreStoreBean {
        /**
         * name : 宿迁总仓
         * type : 国内仓库
         * id : 2
         */

        private String name;
        private String type;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class GoodsGoodsBean implements Serializable{
        /**
         * id : 164
         * name :  Lg 悬挂卡片型芳香剂 绿巨人 6g/个
         * goods_temp : {"id":58,"title":"漫威漫威时尚复仇者联盟2 悬挂卡片型芳香剂 ","name":"悬挂卡片型芳香剂 ","img":["https://file.gjigbuy.com/9e02ef11f4db51134ee53b9516484dd8582f7880fa7c2469aa7571b7522cd308.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/250338221d600916dd3cb23054a3d2c55e78e4fea78eea5ba54658869419002b.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/6ad7cf5ed6772d79c68b19e061ab2f74c3bf9211c9695ecbc430aa1146ad832f.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/3231e74dd17c0befb372810dc8696f1a506d0dff2056814a86af3d77d5047207.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/81c32ce42cc8440b73af82932e2786a9f42d1ea90a1214835e9c4080ccaa98f8.png?x-oss-process=style/thumb","https://file.gjigbuy.com/4fe95fdf9802045ef70a37458e19508732bcbe9356fa3de3e2e52dfe3bef4ef3.png?x-oss-process=style/thumb","https://file.gjigbuy.com/788f9ce1c88c53a4066ede7b4b7539247aa076f691d8909530e5679782a8f8f0.png?x-oss-process=style/thumb","https://file.gjigbuy.com/68a83169042b53b5dfcfbda5bfeb9a44b7a07ecb88d8130326babea99b421fb3.png?x-oss-process=style/thumb"],"video":"","detail":"","create_time":"1534757965.847","error":""}
         * goods_brand : {"name":"","en":"Lg","id":57,"logo":["https://file.gjigbuy.com/69c90fcf72e41981882fcb4e77e8da73d800766460e6de6c0683c3a89b3d1567.jpg?x-oss-process=style/thumb"]}
         * base_area : {"name":"韩国","id":9,"img":["https://file.gjigbuy.com/5b5c9cac237ae1fc0dc8483a198cdcac6141a04d129d9cd165d16a140a7fbffa.png?x-oss-process=style/thumb"]}
         * goods_cate : {"name":"其他清洁用品","id":268}
         * spec : 6g/个
         * img : ["https://file.gjigbuy.com/57845e5a356dba3147f2caca8f2d82e56b45d8c39cfcfd086c3846d74beaa161.jpeg?x-oss-process=style/thumb"]
         * base_unit : {"name":"个","id":1}
         * barcode : 8801051330410
         * keyword : 绿巨人
         * customs_hs : 0
         * goods_attr : 0
         * describe :
         * detail :
         * quality : 0
         * num_p : 0
         * weight : 0.040
         * volume : 0.000
         * speclist : [160,163,164,166]
         */

        private int id;
        private String name;
        public GoodsTempBean goods_temp;
        public GoodsBrandBean goods_brand;
        //有数据返回jsonobject没数据返回数字0
        public BaseAreaBean base_area;

        public GoodsCateBean goods_cate;
        private String spec;
        public BaseUnitBean base_unit;
        private String barcode;
        private String keyword;
        private int customs_hs;
        private String goods_attr;
        private String describe;
        private String detail;
        private int quality;
        private int num_p;
        private String weight;
        private String volume;
        private List<String> img;
        private List<Integer> speclist;

        public GoodsGoodsBean(){

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public GoodsTempBean getGoods_temp() {
            return goods_temp;
        }

        public void setGoods_temp(GoodsTempBean goods_temp) {
            this.goods_temp = goods_temp;
        }

        public GoodsBrandBean getGoods_brand() {
            return goods_brand;
        }

        public void setGoods_brand(GoodsBrandBean goods_brand) {
            this.goods_brand = goods_brand;
        }

        public BaseAreaBean getBase_area() {
            return base_area;
        }

        public void setBase_area(BaseAreaBean base_area) {
            this.base_area = base_area;
        }

        public GoodsCateBean getGoods_cate() {
            return goods_cate;
        }

        public void setGoods_cate(GoodsCateBean goods_cate) {
            this.goods_cate = goods_cate;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public BaseUnitBean getBase_unit() {
            return base_unit;
        }

        public void setBase_unit(BaseUnitBean base_unit) {
            this.base_unit = base_unit;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getCustoms_hs() {
            return customs_hs;
        }

        public void setCustoms_hs(int customs_hs) {
            this.customs_hs = customs_hs;
        }

        public String getGoods_attr() {
            return goods_attr;
        }

        public void setGoods_attr(String goods_attr) {
            this.goods_attr = goods_attr;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public int getNum_p() {
            return num_p;
        }

        public void setNum_p(int num_p) {
            this.num_p = num_p;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public List<Integer> getSpeclist() {
            return speclist;
        }

        public void setSpeclist(List<Integer> speclist) {
            this.speclist = speclist;
        }

        public static class GoodsTempBean {
            /**
             * id : 58
             * title : 漫威漫威时尚复仇者联盟2 悬挂卡片型芳香剂
             * name : 悬挂卡片型芳香剂
             * img : ["https://file.gjigbuy.com/9e02ef11f4db51134ee53b9516484dd8582f7880fa7c2469aa7571b7522cd308.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/250338221d600916dd3cb23054a3d2c55e78e4fea78eea5ba54658869419002b.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/6ad7cf5ed6772d79c68b19e061ab2f74c3bf9211c9695ecbc430aa1146ad832f.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/3231e74dd17c0befb372810dc8696f1a506d0dff2056814a86af3d77d5047207.jpeg?x-oss-process=style/thumb","https://file.gjigbuy.com/81c32ce42cc8440b73af82932e2786a9f42d1ea90a1214835e9c4080ccaa98f8.png?x-oss-process=style/thumb","https://file.gjigbuy.com/4fe95fdf9802045ef70a37458e19508732bcbe9356fa3de3e2e52dfe3bef4ef3.png?x-oss-process=style/thumb","https://file.gjigbuy.com/788f9ce1c88c53a4066ede7b4b7539247aa076f691d8909530e5679782a8f8f0.png?x-oss-process=style/thumb","https://file.gjigbuy.com/68a83169042b53b5dfcfbda5bfeb9a44b7a07ecb88d8130326babea99b421fb3.png?x-oss-process=style/thumb"]
             * video :
             * detail :
             * create_time : 1534757965.847
             * error :
             */

            private int id;
            private String title;
            private String name;
            private String video;
            private String detail;
            private String create_time;
            private String error;

            //有数据返回JSonArray，没数据返回String
            private transient List<String> img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }
        }

        public static class GoodsBrandBean {
            /**
             * name :
             * en : Lg
             * id : 57
             * logo : ["https://file.gjigbuy.com/69c90fcf72e41981882fcb4e77e8da73d800766460e6de6c0683c3a89b3d1567.jpg?x-oss-process=style/thumb"]
             */

            private String name;
            private String en;
            private int id;
            private List<String> logo;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEn() {
                return en;
            }

            public void setEn(String en) {
                this.en = en;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<String> getLogo() {
                return logo;
            }

            public void setLogo(List<String> logo) {
                this.logo = logo;
            }
        }

        public static class BaseAreaBean implements Serializable {
            /**
             * name : 韩国
             * id : 9
             * img : ["https://file.gjigbuy.com/5b5c9cac237ae1fc0dc8483a198cdcac6141a04d129d9cd165d16a140a7fbffa.png?x-oss-process=style/thumb"]
             */

            private String name;
            private int id;
            private List<String> img;

            public BaseAreaBean(){

            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }
        }

        public static class GoodsCateBean {
            /**
             * name : 其他清洁用品
             * id : 268
             */

            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class BaseUnitBean {
            /**
             * name : 个
             * id : 1
             */

            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
