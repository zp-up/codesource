package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.BuildConfig;

/**
 * Created by wuqaing on 2018/12/14.
 */

public class MainUrls {
    public static final String areaUrl = "https://app.gjigbuy.com";

    public static final String ServerIp = BuildConfig.DEBUG ? "https://api.gjigbuy.com/app/index/api" : "https://app.gjigbuy.com/app/index/api";

    public static final String getAccessTokenUrl = BuildConfig.DEBUG ? "https://api.gjigbuy.com/app/index/login" : "https://app.gjigbuy.com/app/index/login";

    public static final String getVerifyCodeUrl = ServerIp + "?api=data.config.sendregsms";

    public static final String phoneRegisterUrl = ServerIp + "?api=user.user.telphoneadd";

    public static final String getBannerDataUrl = ServerIp + "?api=app.action.carousel";

    public static final String userLoginUrl = ServerIp + "?api=user.user.login";

    public static final String upLoadImageUrl = ServerIp + "?api=base.file.uploadfile";

    public static final String modifyUserInfoUrl = ServerIp + "?api=user.user.setinfo";

    public static final String getLimitTimeGoodsUrl = ServerIp + "?api=store.price.timescreen";

    public static final String getHotSaleGoodsUrl = ServerIp + "?api=store.price.rm";

    public static final String getBondedGoodsUrl = ServerIp + "?api=store.price.bonded";

    public static final String getOverseasGoodsUrl = ServerIp + "?api=store.price.dmail";

    public static final String getWishGoodsUrl = ServerIp + "?api=store.price.WishGoods";

    public static final String getPopularityGoodsUrl = ServerIp + "?api=store.price.PopGoods";

    public static final String getLeftClassUrl = ServerIp + "?api=goods.cate.cate";

    public static final String getClassBrandUrl = ServerIp + "?api=goods.brand.getallcatebrand";

    public static final String getGoodsDetailUrl = ServerIp + "?api=store.price.goods";

    public static final String getGoodsEvaluateUrl = ServerIp + "?api=goods.comment.commentlist";

    public static final String addGoodsToCartUrl = ServerIp + "?api=order.orderlist.addcart";

    public static final String addToCollectionUrl = ServerIp + "?api=goods.colle.add";

    public static final String getClassGoodsListUrl = ServerIp + "?api=goods.cate.data";

    public static final String getGoodsCartUrl = ServerIp + "?api=order.orderlist.cartlist";

    public static final String getUserInfoUrl = ServerIp + "?api=user.user.info";

    public static final String getExperienceListUrl = ServerIp + "?api=user.exp.explist";

    public static final String getAllThreeLevelClassUrl = ServerIp + "?api=goods.cate.cata3";

    public static final String getBrandGoodsListUrl = ServerIp + "?api=goods.brand.getdata";

    public static final String getBrandInformationUrl = ServerIp + "?api=goods.brand.brand";

    public static final String getCityAreaProvienceUrl = "http://www.everynew.cn/test/log$ajax.htm";

    public static final String addAddressInfoUrl = ServerIp + "?api=user.address.addsh";

    public static final String editAddressInfoUrl = ServerIp + "?api=user.address.upedit";

    public static final String getAddressListUrl = ServerIp + "?api=user.address.saddress";

    public static final String getOrderListUlr = ServerIp + "?api=order.order.orderlist";

    public static final String getOrderDetailByIdUrl = ServerIp + "?api=order.order.order";

    public static final String buyAgainUrl = ServerIp + "?api=order.order.againorder";

    public static final String cancelOrderUrl = ServerIp + "?api=order.order.closeorder";

    public static final String backMoneyOnlyUrl = ServerIp + "?api=order.order.cancelorder";

    public static final String comfirmOrderUrl = ServerIp + "?api=order.order.signpost";

    public static final String logisticsByOrderIdUrl = ServerIp + "?api=order.order.postquery";

    public static final String collectionGoodsListUrl = ServerIp + "?api=goods.colle.collelist";

    public static final String verifyIdCardUrl = ServerIp + "?api=third.qq.idcard";

    public static final String modifyCartGoodsNumber = ServerIp + "?api=order.orderlist.uplist";

    public static final String deleteCartGoodsUrl = ServerIp + "?api=order.orderlist.del";

    public static final String cartGoodsChangedUrl = ServerIp + "?api=order.orderlist.CartStatus";

    public static final String cartStoreChangedUrl = ServerIp + "?api=order.orderlist.CartStoreStatus";

    public static final String selectedAllGoodsUrl = ServerIp + "?api=order.orderlist.AllCartState";

    public static final String cartSubmitUrl = ServerIp + "?api=order.orderlist.checkout";

    public static final String getHistorySearchUrl = ServerIp + "?api=goods.search.userlog";

    public static final String getHotSearchUrl = ServerIp + "?api=goods.search.hot";

    //获取拼团商品列表数据
    public static final String getGroupProductListUrl = ServerIp + "/api?api=store.price.getgroupgoodslist";

    //商品搜索
    public static final String searchProduct =ServerIp+"/api?api=goods.search.goods";
    //初始化联系客服
    public static final String getInitIMMessage = ServerIp +"/api?api=msg.qmsg.getallcatebrand";
    //发送客服消息
    public static final String sendMessage = ServerIp +"/api?api=msg.qmsg.getdata";

    public static final String getUserWalletMoneyUrl = ServerIp + "?api=order.order.getmoney";

    public static final String setAddressDefaultUrl = ServerIp + "?api=user.address.setdefault";

    public static final String setWalletMoneyToOrderUrl = ServerIp + "?api=order.order.setmoney";

    public static final String getPayInfoUrl = ServerIp + "?api=order.order.payinfo";

    public static final String setPayWayUrl = ServerIp + "?api=order.order.setfinpay";

    public static final String toPayUrl = ServerIp + "?api=order.order.pay";
}
