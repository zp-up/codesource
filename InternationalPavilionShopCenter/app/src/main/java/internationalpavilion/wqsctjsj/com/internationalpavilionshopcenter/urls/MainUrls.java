package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.urls;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.BuildConfig;

/**
 * Created by wuqaing on 2018/12/14.
 */

public class MainUrls {
    public static final String areaUrl = "https://app.gjigbuy.com";

    public static final String ServerIp = BuildConfig.DEBUG ? "https://www.gjigbuy.com/app/index/api" : "https://app.gjigbuy.com/app/index/api";

    public static final String getAccessTokenUrl = BuildConfig.DEBUG ? "https://www.gjigbuy.com/app/index/login" : "https://app.gjigbuy.com/app/index/login";

    // 基础系统-》配置管理-》发送注册验证码
    public static final String getVerifyCodeUrl = ServerIp + "?api=data.config.sendregsms";

    public static final String phoneRegisterUrl = ServerIp + "?api=user.user.telphoneadd";

    public static final String getBannerDataUrl = ServerIp + "?api=app.action.carousel";

    public static final String userLoginUrl = ServerIp + "?api=user.user.login";

    // 会员系统-》会员管理-》第三方登录
    public static final String userThirdLoginUrl = ServerIp + "?api=user.user.thirdlogin";

    // 会员系统-》会员管理-》第三方code登录
    public static final String userThirdCodeLoginUrl = ServerIp + "?api=user.user.thirdcodelogin";

    // 应用系统-》应用管理-》第三方登录列表
    public static final String userThirdLoginListUrl = ServerIp + "?api=app.app.loginlist";

    // 会员系统-》实名管理-》用户验证码登录
    public static final String userCodeLogin = ServerIp + "?api=user.user.codelogin";

    // 会员系统-》实名管理-》检验手机号是否注册
    public static final String isRegisteredMobile = ServerIp + "?api=user.user.ismobile";

    // 附件管理 -》 文件流上传
    public static final String upLoadImageUrl = ServerIp + "?api=base.file.uploadfile";


    // 基础系统-》配置管理-》发送邮件
    public static final String sendEmailUrl = ServerIp + "?api=\tdata.config.sendemail";

    // 基础系统-》配置管理-》发送短信验证码
    public static final String sendSMSCodeUrl = ServerIp + "?api=data.config.sendsmscode";

    // 基础系统-》配置管理-》发送邮件注册验证码
    public static final String sendRegEmailUrl = ServerIp + "?api=data.config.sendregemail";

    public static final String modifyUserInfoUrl = ServerIp + "?api=user.user.setinfo";

    public static final String getLimitTimeGoodsUrl = ServerIp + "?api=store.price.timescreen";

    public static final String getHotSaleGoodsUrl = ServerIp + "?api=store.price.rm";

    public static final String getBondedGoodsUrl = ServerIp + "?api=store.price.bonded";

    public static final String getOverseasGoodsUrl = ServerIp + "?api=store.price.dmail";

    public static final String getWishGoodsUrl = ServerIp + "?api=store.price.WishGoods";

    public static final String getPopularityGoodsUrl = ServerIp + "?api=store.price.PopGoods";

    public static final String getLeftClassUrl = ServerIp + "?api=goods.cate.cate";

    // 商品系统-》品牌管理-》获取全部分类品牌
    public static final String getAllClassBrandUrl = ServerIp + "?api=goods.brand.getallcatebrand";

    // 商品系统-》品牌管理-》按分类获取品牌
    public static final String getClassBrandUrl = ServerIp + "?api=goods.brand.getcatebrand";

    // 商品系统-》品牌管理-》指定品牌列表
    public static final String getGoodsDataWithBrandUrl = ServerIp + "?api=goods.brand.getdata";

    public static final String getGoodsDetailUrl = ServerIp + "?api=store.price.goods";

    public static final String getGoodsEvaluateUrl = ServerIp + "?api=goods.comment.commentlist";

    public static final String addGoodsToCartUrl = ServerIp + "?api=order.orderlist.addcart";

    // 商品系统-》收藏管理-》用户收藏或取消
    public static final String addToCollectionUrl = ServerIp + "?api=goods.colle.add";

    public static final String getClassGoodsListUrl = ServerIp + "?api=goods.cate.data";

    // 订单系统-》订单明细-》用户购物车
    public static final String getGoodsCartUrl = ServerIp + "?api=order.orderlist.cartlist";

    public static final String getUserInfoUrl = ServerIp + "?api=user.user.info";

    public static final String getExperienceListUrl = ServerIp + "?api=user.exp.explist";

    public static final String getAllThreeLevelClassUrl = ServerIp + "?api=goods.cate.cata3";

    public static final String getBrandGoodsListUrl = ServerIp + "?api=goods.brand.getdata";

    public static final String getBrandInformationUrl = ServerIp + "?api=goods.brand.brand";

    public static final String getCityAreaProvienceUrl = "http://www.everynew.cn/test/log$ajax.htm";

    public static final String addAddressInfoUrl = ServerIp + "?api=user.address.addsh";

    public static final String editAddressInfoUrl = ServerIp + "?api=user.address.addusersh";

    public static final String getAddressListUrl = ServerIp + "?api=user.address.saddress";

    public static final String getOrderListUlr = ServerIp + "?api=order.order.orderlist";

    public static final String getOrderDetailByIdUrl = ServerIp + "?api=order.order.order";

    public static final String buyAgainUrl = ServerIp + "?api=order.order.againorder";

    public static final String cancelOrderUrl = ServerIp + "?api=order.order.closeorder";

    public static final String backMoneyOnlyUrl = ServerIp + "?api=order.order.cancelorder";

    public static final String comfirmOrderUrl = ServerIp + "?api=order.order.signpost";

    public static final String logisticsByOrderIdUrl = ServerIp + "?api=order.order.postquery";

    // 商品系统-》收藏管理-》用户收藏列表
    public static final String collectionGoodsListUrl = ServerIp + "?api=goods.colle.collelist";

    // 第三方接口-》腾讯 AI-》身份证识别
    public static final String verifyIdCardUrl = ServerIp + "?api=third.qq.idcard";

    // 会员系统-》实名管理-》实名添加手动
    public static final String verifyIdCardByInputUrl = ServerIp + "?api=user.auth.useradd";

    // 订单系统-》订单明细-》商品数量更改（待付款订单）
//    public static final String modifyCartGoodsNumber = ServerIp + "?api=order.orderlist.uplist";// 进入待付款后才能用
    public static final String modifyCartGoodsNumber = ServerIp + "?api=order.orderlist.addcart";

    // 订单系统-》订单明细-》删除购物车商品
    public static final String deleteCartGoodsUrl = ServerIp + "?api=order.orderlist.del";

    // 订单系统-》订单明细-》购物车仓库
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
    // 查询支付结果
    public static final String getPayResultUrl = ServerIp + "?api=order.order.payquery";


    public static final String deleteAddressUrl = ServerIp + "?api=user.address.del";

    //查询新品列表
    public static final String getNewlyGoodsListUrl =ServerIp +"?api=store.price.newgoods";
    //查询折扣商品列表
    public static final String getDiscountGoodsListUrl = ServerIp +"?api=store.price.agio";

    // 实名认证
    public static final String authenticationListUrl = ServerIp + "?api=user.auth.datalist";
    // 会员系统-》实名管理-》实名添加
    public static final String authenticationAddUrl = ServerIp + "?api=user.auth.authadd";
    // 用户申请售后
    public static final String requestAfterSaleUrl = ServerIp + "?api=order.order.AfterSale";
    // 填写快递/退货信息 的接口
    public static final String setExpressInfoUrl = ServerIp + "?api=order.order.refundgoods";
    // 售后退款
    public static final String afterSaleRefundUrl = ServerIp + "?api=order.order.AsseRefund";
    // 退货地址
    public static final String getRefundGoodsAddressUrl = ServerIp + "?api=order.order.refundgoodsaddress";

}
