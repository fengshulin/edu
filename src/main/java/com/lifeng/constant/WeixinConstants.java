package com.lifeng.constant;

public class WeixinConstants {

	public static final String appid = "wxed0ec3f7d21cc993";
	public static final String appsecret = "53ca2dc0ee2038f67db1aa33c190ee87";
	public static final String token = "lifengedu2018";// token服务器令牌

	/** 网页授权start */
	public static final String sessionOpenIdKey = "sessionOpenIdKey";// session中openidkey

	public static final String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize";// 网页授权地址
	public static final String oauth2AccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";// 网页授权获取access_token地址
	public static final String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo";// 网页授权获取用户信息地址
	public static final String userListInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/get";// 获取用户列表
	public static final String cgiuserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info";// 获取用户基本信息
	public static final String jsApiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	/** 网页授权end */

	/** 微信地址 **/
	public static final String templateMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";// 模板信息发送Url
	public static final String custSendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";// 商户发送信息Url
	public static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

	/** 微信企业付款 **/
	public static final String transfersUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";// 企业付款api
	public static final String payOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 公众号支付
	public static final String queryPayOrderUrl = "https://api.mch.weixin.qq.com/pay/orderquery";// 公众号支付订单查询

	/** 永久二维码 **/
	public static final String qrcodeUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";// 永久二维码

}
