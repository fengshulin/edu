package com.lifeng.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lifeng.constant.WeixinConstants;
import com.lifeng.entity.WxJssdkConfig;
import com.lifeng.util.HttpClientUtil;
import com.lifeng.util.JsonUtil;

@Service
public class WxService {

	private Logger logger = LoggerFactory.getLogger(WxService.class);

	/**
	 * 网页授权获取accessToken
	 */

	public String getOauth2AccessToken(String appid, String appsecret, String code) {
		logger.info("WxService服务getOauth2AccessToken请求参数 appid:{},appsecret:{},code:{}", appid, appsecret, code);
		StringBuilder sb = new StringBuilder();
		sb.append("appid=").append(appid).append("&secret=").append(appsecret).append("&code=").append(code)
				.append("&grant_type=").append("authorization_code");
		String content = HttpClientUtil.sendGet(WeixinConstants.oauth2AccessTokenUrl, sb.toString());
		logger.info("WxService服务wxServiceFacade 返回结果:{}", content);
		return content;
	}

	/**
	 * 网页授权获取用户信息
	 */

	public String getOauth2UserInfo(String accessToken, String openId) {
		logger.info("WxService服务getOauth2UserInfo请求参数 accessToken:{},openId:{}", accessToken, openId);
		StringBuilder sb = new StringBuilder();
		sb.append("access_token=");
		sb.append(accessToken);
		sb.append("&openid=");
		sb.append(openId);
		sb.append("&lang=zh_CN");
		String content = HttpClientUtil.sendGet(WeixinConstants.userInfoUrl, sb.toString());
		logger.info("WxService服务getOauth2UserInfo 返回结果:{}", content);
		return content;
	}

	/**
	 * 获取accessToken
	 */

	public String getAccessToken(String lfappid, String lfappsecret) {
		JSONObject jsonObject = JsonUtil.read("token");
		String accessToken = "";
		if (jsonObject != null) {
			accessToken = jsonObject.getString("token");
			String time = jsonObject.getString("time");
			logger.info("token -------:{}", accessToken);
			if (StringUtils.isNotBlank(time)) {
				long oldTime = Long.valueOf(time);
				if (new Date().getTime() - oldTime > 60 * 60 * 1000) {
					logger.info("WxService服务getAccessToken请求参数 appid:{},appsecret:{}", lfappid, lfappsecret);
					StringBuilder sb = new StringBuilder();
					sb.append("grant_type=").append("client_credential").append("&appid=").append(lfappid)
							.append("&secret=").append(lfappsecret);
					String content = HttpClientUtil.sendGet(WeixinConstants.tokenUrl, sb.toString());
					logger.info("WxService服务getAccessToken 返回结果:{}", content);
					JSONObject obj = JSON.parseObject(content);
					accessToken = (String) obj.get("access_token");
					if (StringUtils.isNotBlank(accessToken)) {
						logger.info("getAccessToken accessToken:{} " + accessToken);
					} else {
						logger.info("getAccessToken accessToken错误");
					}
					JsonUtil.writeStringToFile("token", accessToken, String.valueOf(new Date().getTime()));
				}
			}
		} else {

		}
		return accessToken;
	}

	public WxJssdkConfig getWxJssdkConfig(String url) {
		String accesstoken = getAccessToken(WeixinConstants.appid, WeixinConstants.appsecret);
		logger.info("wxJssdkConfigService getWxJssdkConfig currentUrl:{}", url);
		JSONObject jsonObject = JsonUtil.read("ticket");
		String apiTicket = "";
		if (jsonObject != null) {
			apiTicket = jsonObject.getString("ticket");
			String time = jsonObject.getString("time");
			if (StringUtils.isNotBlank(time)) {
				long oldTime = Long.valueOf(time);
				if (new Date().getTime() - oldTime > 60 * 60 * 1000) {
					String apiTicketJson = getApiTicket(accesstoken);
					logger.info("apiTicketJson -------:{}", apiTicketJson);
					if (!StringUtils.isEmpty(apiTicketJson)) {
						JSONObject parseObject = JSON.parseObject(apiTicketJson);
						if (parseObject.containsKey("errmsg")) {
							String errmsg = (String) parseObject.get("errmsg");
							if (errmsg.equals("ok")) {
								String ticket = (String) parseObject.get("ticket");
								apiTicket = ticket;
							}
						}
						JsonUtil.writeStringToFile("ticket", apiTicket, String.valueOf(new Date().getTime()));
					}
				}
			}
		}

		Map<String, String> wxSign = WxJssdkConfig.sign(apiTicket, url);
		if (wxSign != null && !wxSign.isEmpty()) {
			WxJssdkConfig wxJssdkConfig = new WxJssdkConfig();
			wxJssdkConfig.setNonceStr(wxSign.get("nonceStr"));
			wxJssdkConfig.setTimestamp(wxSign.get("timestamp"));
			wxJssdkConfig.setSignature(wxSign.get("signature"));
			wxJssdkConfig.setUrl(wxSign.get("url"));
			wxJssdkConfig.setAppId(WeixinConstants.appid);
			wxJssdkConfig.setJsapi_ticket(apiTicket);
			return wxJssdkConfig;
		} else {
			return null;
		}
	}

	/**
	 * 获取api_ticket
	 */

	public String getApiTicket(String accessToken) {
		logger.info("WxService服务getApiTicket请求参数 accessToken:{}", accessToken);
		StringBuilder sb = new StringBuilder();
		sb.append("access_token=");
		sb.append(accessToken);
		sb.append("&type=");
		sb.append("jsapi");
		String content = HttpClientUtil.sendGet(WeixinConstants.jsApiTicketUrl, sb.toString());
		logger.info("WxService服务getApiTicket 返回结果:{}", content);
		return content;
	}

	public String getUserInfo(String openId) {
		String accessToken = getAccessToken(WeixinConstants.appid, WeixinConstants.appsecret);
		logger.info("WxService服务getUserInfo请求参数 accessToken:{},openId:{}", accessToken, openId);
		StringBuilder sb = new StringBuilder();
		sb.append("access_token=");
		sb.append(accessToken);
		sb.append("&openid=");
		sb.append(openId);
		sb.append("&lang=zh_CN");
		String content = HttpClientUtil.sendGet(WeixinConstants.cgiuserInfoUrl, sb.toString());
		logger.info("WxService服务getUserInfo 返回结果:{}", content);
		return content;
	}

	/**
	 * @Description 获取公众号永久二维码
	 * @param accessToken
	 * @param content
	 * @return
	 * @see 需要参考的类或方法
	 */

	public String createQrcode(String accessToken, String content) {
		logger.info("WxService服务createQrcode请求参数 accessToken:{},sence:{}", accessToken, content);
		StringBuilder sb = new StringBuilder(WeixinConstants.qrcodeUrl);
		sb.append(accessToken);
		String result = HttpClientUtil.sendPost(sb.toString(), content);
		logger.info("WxService服务createQrcode 返回结果:{}", result);
		return result;
	}

}
