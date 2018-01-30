package com.lifeng.intercept;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lifeng.constant.WeixinConstants;
import com.lifeng.service.WxService;
import com.lifeng.util.IPUtils;
import com.lifeng.util.RequestUtil;

/**
 * 微信拦截器获取openId
 * 
 * @author solin
 *
 */
@Repository
public class Oauth2AccessTokenAnnotationInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(Oauth2AccessTokenAnnotationInterceptor.class);

	@Resource
	private WxService wxService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = null;
		try {
			handlerMethod = (HandlerMethod) handler;
		} catch (Exception e) {
			logger.error("Oauth2AccessTokenAnnotationInterceptor 异常信息:{}", e);
		}

		if (handlerMethod == null) { // 如果请求地址错误,则将该错误抛入springmvc处理，返回404的地址错误提示
			return super.preHandle(request, response, handler);
		}

		Method method = handlerMethod.getMethod();
		NeedOpenId annotation = method.getAnnotation(NeedOpenId.class);
		if (annotation == null) {
			return super.preHandle(request, response, handler);
		}
		boolean oauth2Flag = annotation.oauth2Flag();

		if (annotation.needOpenId()) {
			boolean flag = processAuth2OpenId(request, response, oauth2Flag, annotation);
			if (!flag) {
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * @Description 授权获取openId
	 * @param request
	 * @param response
	 * @param oauth2Flag
	 * @param annotation
	 * @return
	 * @throws IOException
	 * @see 需要参考的类或方法
	 */
	private boolean processAuth2OpenId(HttpServletRequest request, HttpServletResponse response, boolean oauth2Flag,
			NeedOpenId annotation) throws IOException {
		// 取session中是否存在openid
		String openId = (String) WebUtils.getSessionAttribute(request, WeixinConstants.sessionOpenIdKey);
		logger.info("缓存中微信openId:{}", openId);
		String requestURL = request.getRequestURL().toString();
		logger.info("授权请求路径:{}", requestURL);
		if (requestURL.contains("lilfengedu.cn") || requestURL.contains("lilfengedu.com")) {
			requestURL = requestURL.replace(requestURL.substring(0, requestURL.indexOf(":")), "https");
		}

		// 首先验证缓存中是否存在openid，如果为空进行授权，反则直接跳过
		if (StringUtils.isBlank(openId)) {
			String code = request.getParameter("code");

			// 获取请求参数，移除code和state
			String requestParammStr = RequestUtil.getRequestOauthParammStr(request);
			String redirectUrl = requestURL.replace(";", ",");
			if (!StringUtils.isEmpty(requestParammStr)) {
				redirectUrl += "?" + requestParammStr;
			}

			// 验证code是否为空（第一步：用户同意授权，获取code）
			if (StringUtils.isNotBlank(code)) {
				long tokenTime = System.currentTimeMillis();
				// 第二步：通过code换取网页授权access_token
				String accessTokenJson = getOauth2AccessToken(code);
				if (accessTokenJson != null) {
					JSONObject obj = JSON.parseObject(accessTokenJson);

					// 获取失败，重新跳转到授权页面
					if (obj.containsKey("errcode")) {
						String errcode = obj.getString("errcode");
						if (!"0".equals(errcode)) {
							String oauthUrl = oauthUrl(redirectUrl, oauth2Flag);
							response.sendRedirect(response.encodeRedirectURL(oauthUrl));
							return false;
						}
					}
					openId = obj.getString("openid");
					WebUtils.setSessionAttribute(request, WeixinConstants.sessionOpenIdKey, openId);
					String accessToken = obj.getString("access_token");

					// 第三步：拉取用户信息(需scope为 snsapi_userinfo)
					if (oauth2Flag) {
						getWeixinInfo(accessToken, openId, request);
					}
				} else {
					logger.info("获取accessToken返回null，服务异常，重新进入授权页面");
					String oauthUrl = oauthUrl(redirectUrl, oauth2Flag);
					response.sendRedirect(response.encodeRedirectURL(oauthUrl));
					return false;
				}

				logger.info("拦截器调用微信授权耗时:{}ms", System.currentTimeMillis() - tokenTime);
			} else {
				// 跳转到授权页面，获取code
				String oauthUrl = oauthUrl(redirectUrl, oauth2Flag);
				response.sendRedirect(response.encodeRedirectURL(oauthUrl));
				return false;
			}
		}
		logger.info("拦截器获取openId:{},设备类型：{},ip:{}", openId, request.getHeader("user-agent"),
				IPUtils.getIpAddr(request));

		return true;
	}

	/**
	 * 用户同意授权，获取code
	 *
	 * @param url
	 * @param appId
	 * @param oauth2Flag
	 * @return
	 */
	private String oauthUrl(String url, boolean oauth2Flag) {
		StringBuffer oathUrl = new StringBuffer();
		String encodeUrl = "";
		if (url != null) {
			try {
				encodeUrl = URLEncoder.encode(url, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("urlEncode异常!.oauthUrl:{}", e);
			}
		}
		Random random = new Random();
		int nextInt = random.nextInt(10000);
		Calendar cld = Calendar.getInstance();
		logger.info("应用授权作用域：{}", oauth2Flag ? "snsapi_userinfo" : "snsapi_base");
		oathUrl.append(WeixinConstants.oauth2Url).append("?").append("appid=").append(WeixinConstants.appid)
				.append("&redirect_uri=").append(encodeUrl).append("&response_type=").append("code").append("&scope=")
				.append(oauth2Flag ? "snsapi_userinfo" : "snsapi_base").append("&state=")
				.append(cld.getTimeInMillis() + "" + nextInt).append("#wechat_redirect");

		logger.info("用户授权地址:{}", oathUrl.toString());
		return oathUrl.toString();
	}

	/**
	 * 通过code换取网页授权access_token
	 *
	 * @param code
	 * @param appId
	 * @param appSecret
	 * @param oauth
	 * @return
	 */
	private String getOauth2AccessToken(String code) {
		String content = wxService.getOauth2AccessToken(WeixinConstants.appid, WeixinConstants.appsecret, code);
		return content == null ? null : content;
	}

	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 *
	 * @param accessToken
	 * @param openId
	 * @param request
	 */
	private void getWeixinInfo(String accessToken, String openId, HttpServletRequest request) {
		String result = wxService.getOauth2UserInfo(accessToken, openId);
		logger.info("请求授权获取微信用户信息返回结果：{}", result);
		if (!StringUtils.isEmpty(result)) {
			JSONObject object = JSON.parseObject(result);
			boolean containsKey = object.containsKey("errcode");
			if (!containsKey) {

			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

}
