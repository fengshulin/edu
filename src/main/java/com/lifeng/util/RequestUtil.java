package com.lifeng.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestUtil {

	/**
	 * 获取当前请求链接
	 * 
	 * @param request
	 * @return
	 */
	public static String getCurrentUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String requestParammStr = getRequestParammStr(request);
		if (!StringUtils.isEmpty(requestParammStr)) {
			url += "?" + requestParammStr;
		}
		return url;
	}

	/**
	 * 获取request参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestParammStr(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Map requestParams = request.getParameterMap();
		StringBuffer paramStr = new StringBuffer();
		for (@SuppressWarnings("rawtypes")
		Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			if (paramStr.toString() != null && !paramStr.toString().equals("")) {
				paramStr.append("&");
			}
			// 乱码解决，出现乱码时使用
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			try {
				valueStr = URLEncoder.encode(valueStr, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			paramStr.append(name).append("=").append(valueStr);

		}
		return paramStr.toString();
	}

	/**
	 * @Description 获取微信授权参数，移除参数:r_u/code/state
	 * @param request
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static String getRequestAuthParam(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Map requestParams = request.getParameterMap();
		StringBuffer paramStr = new StringBuffer();
		for (@SuppressWarnings("rawtypes")
		Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			if ("r_u".equals(name) || "code".equals(name) || "state".equals(name) || "redirectUrl".equals(name)) {
				continue;
			}
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			if (paramStr.toString() != null && !paramStr.toString().equals("")) {
				paramStr.append("&");
			}
			try {
				valueStr = URLEncoder.encode(valueStr, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			paramStr.append(name).append("=").append(valueStr);

		}
		return paramStr.toString();
	}

	/**
	 * @Description 获取微信授权参数，移除参数：code和state
	 * @param request
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static String getRequestOauthParammStr(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Map requestParams = request.getParameterMap();
		StringBuffer paramStr = new StringBuffer();
		for (@SuppressWarnings("rawtypes")
		Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			if ("code".equals(name) || "state".equals(name)) {
				continue;
			}
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			if (paramStr.toString() != null && !paramStr.toString().equals("")) {
				paramStr.append("&");
			}
			try {
				valueStr = URLEncoder.encode(valueStr, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			paramStr.append(name).append("=").append(valueStr);

		}
		return paramStr.toString();
	}

	public static Map<String, String> getRequestParamm(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Map requestParams = request.getParameterMap();
		StringBuffer paramStr = new StringBuffer();
		for (@SuppressWarnings("rawtypes")
		Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			if (valueStr.indexOf("?") > 0) {
				String names = valueStr.split("\\?")[0];
				String param = valueStr.split("\\?")[1];
				String keyName = param.split("=")[0];
				String keyValue = param.split("=")[1];
				map.put(keyName, keyValue);
				valueStr = names + "&" + keyName + "=" + keyValue;
			}
			if (paramStr.toString() != null && !paramStr.toString().equals("")) {
				if (paramStr.toString().indexOf("?") > 0) {
					paramStr.append("&");
				} else {
					paramStr.append("?");
				}
			}
			paramStr.append(name).append("=").append(valueStr);
			map.put(name, valueStr);
		}
		map.put("url", paramStr.toString());
		return map;
	}

	/**
	 * 组装request参数为map，移除sign参数
	 *
	 * @Description 一句话描述方法用法
	 * @param request
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static Map<String, Object> getRequestMapNoSign(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();

		for (Enumeration<String> e = paramNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			if ("sign".equals(thisName)) {
				continue;
			} else {
				paramMap.put(thisName, thisValue);
			}
		}
		return paramMap;
	}

	/**
	 * @Description map转换为key=value&key=value格式，并且自然排序
	 * @param requestParam
	 * @return
	 * @throws UnsupportedEncodingException
	 * @see 需要参考的类或方法
	 */
	public static String MapToStrSort(Map<String, String> requestParam) throws UnsupportedEncodingException {
		StringBuffer strbuff = new StringBuffer();
		List<String> list = new ArrayList<String>(requestParam.keySet());
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			String paramName = list.get(i);
			String paramValue = requestParam.get(paramName);
			strbuff.append(paramName).append("=").append(paramValue);
			if (i != list.size() - 1) {
				strbuff.append("&");
			}
		}
		return strbuff.toString();
	}

	/**
	 * 参数URLEncoder encode
	 * 
	 * @param paramMap
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> paramToURLEncoder(Map<String, String> paramMap, String encode)
			throws UnsupportedEncodingException {
		for (String key : paramMap.keySet()) {
			paramMap.put(key, URLEncoder.encode(paramMap.get(key), encode));
		}
		return paramMap;
	}

	/**
	 * url参数拼接
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String paramToUrl(String url, Map<String, String> paramMap) {
		url += "?";
		for (String key : paramMap.keySet()) {
			url += "&" + key + "=" + paramMap.get(key);
		}
		return url;
	}

}
