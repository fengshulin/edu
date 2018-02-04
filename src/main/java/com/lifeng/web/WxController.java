package com.lifeng.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lifeng.constant.WeixinConstants;
import com.lifeng.entity.WxJssdkConfig;
import com.lifeng.service.WxService;
import com.lifeng.util.DigestUtils;
import com.lifeng.web.wx.WeixinRequestDealer;

@Controller
public class WxController {

	private Logger logger = LoggerFactory.getLogger(WxController.class);

	@Resource
	private WxService wxService;
	@Resource
	private WeixinRequestDealer weixinRequestDealer;

	/**
	 * @Description 接收微信普通消息/事件推送-
	 * @param request
	 * @param response
	 * @throws IOException
	 * @see 需要参考的类或方法
	 */
	@RequestMapping(value = "wxNotice", method = RequestMethod.POST)
	public @ResponseBody void receiveMessagePost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 微信消息处理
		String respMessage = weixinRequestDealer.getMessage(request);
		logger.info("微信回复消息:{}", respMessage);

		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

	/**
	 * @Description 微信验证服务器地址的有效性
	 * @param request
	 * @param response
	 * @see 需要参考的类或方法
	 */
	@RequestMapping(value = "wxNotice", method = RequestMethod.GET)
	public @ResponseBody String wxNotice(HttpServletRequest request, HttpServletResponse response) {
		logger.info("微信验证服务器地址的有效性。。。。。。。");
		// 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数
		try {
			String signature = request.getParameter("signature");// 微信加密签名（token、timestamp、nonce。）
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
			logger.info("signatur:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);

			// 将token、timestamp、nonce三个参数进行字典序排序
			String[] params = new String[] { WeixinConstants.token, timestamp, nonce };
			Arrays.sort(params);
			// 将三个参数字符串拼接成一个字符串进行加密
			String clearText = params[0] + params[1] + params[2];
			String sign = DigestUtils.getSha1Str(clearText);

			// 获得加密后的字符串可与signature对比
			if (signature.equals(sign)) {
				return echostr;
			}
		} catch (Exception e) {
			logger.error("微信验证服务器地址的有效性，服务异常:{}", e.getMessage());
			e.printStackTrace();
		}

		return "ERROR";
	}

	/**
	 * @Description 获取jssdk配置
	 * @param request
	 * @return
	 * @see 需要参考的类或方法
	 */
	@RequestMapping(value = "wxJssdkConfig", method = RequestMethod.GET)
	public @ResponseBody String wxJssdkConfig(HttpServletRequest request, HttpServletResponse response) {
		String currentUrl = request.getParameter("currentUrl");
		logger.info("currentUrl:" + currentUrl);
		WxJssdkConfig wxJssdkConfig = wxService.getWxJssdkConfig(currentUrl);
		logger.info("wxJssdkConfig:" + JSON.toJSONString(wxJssdkConfig));
		return JSON.toJSONString(wxJssdkConfig);
	}

}
