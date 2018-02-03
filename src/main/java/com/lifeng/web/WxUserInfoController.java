package com.lifeng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.lifeng.constant.WeixinConstants;
import com.lifeng.intercept.NeedOpenId;
import com.lifeng.service.WxService;
import com.lifeng.util.ReturnContent;
import com.lifeng.util.XmlUtil;

@Controller
public class WxUserInfoController {
	private Logger logger = LoggerFactory.getLogger(WxUserInfoController.class);

	@Resource
	private WxService wxService;

	/**
	 * 跳转到抽签首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/home")
	@NeedOpenId(needOpenId = true, oauth2Flag = true)
	public String user(HttpServletRequest request) {
		String openId = (String) WebUtils.getSessionAttribute(request, WeixinConstants.sessionOpenIdKey);
		logger.info("home/openId:{}", openId);
		String redirectUrl = "http://www.lifengedu.com/wx/html/index.html?openId=" + openId;
		return "redirect:" + redirectUrl;
	}

	/**
	 * 获取微信用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("info")
	public @ResponseBody String info(HttpServletRequest request) {
		ReturnContent<Map<String, Object>> content = null;
		Map<String, Object> returnMap = new HashMap<>();
		String openId = request.getParameter("openId");
		try {
			if (StringUtils.isBlank(openId)) {
				content = new ReturnContent<Map<String, Object>>("99", "openId不能为空");
				return JSON.toJSONString(content);
			}

			String nickname = (String) WebUtils.getSessionAttribute(request, openId + "_nickname");
			String img = (String) WebUtils.getSessionAttribute(request, openId + "_img");
			if (StringUtils.isBlank(nickname) && StringUtils.isBlank(img)) {
				content = new ReturnContent<Map<String, Object>>("99", "用户信息不存在", returnMap);
			} else {
				returnMap.put("img", img);
				returnMap.put("nickname", nickname);
				content = new ReturnContent<Map<String, Object>>("00", "success", returnMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			content = new ReturnContent<Map<String, Object>>("99", "用户信息不存在", returnMap);
		}
		return JSON.toJSONString(content);
	}

	/**
	 * 检查是否抽签，true：已抽签，false：未抽签
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("join/check")
	public @ResponseBody String check(HttpServletRequest request) {
		ReturnContent<Map<String, Object>> content = null;
		String openId = request.getParameter("openId");
		try {
			if (StringUtils.isBlank(openId)) {
				content = new ReturnContent<Map<String, Object>>("99", "openId不能为空");
			} else {
				boolean result = XmlUtil.getById(openId);
				logger.info("检查是否存在:{},用户:{}", result, openId);
				content = new ReturnContent<Map<String, Object>>("00", String.valueOf(XmlUtil.getById(openId)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			content = new ReturnContent<Map<String, Object>>("99", "系统异常");
		}
		return JSON.toJSONString(content);
	}

	/**
	 * 增加抽签记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("join/add")
	public @ResponseBody String add(HttpServletRequest request) {
		ReturnContent<Map<String, Object>> content = null;
		Map<String, Object> returnMap = new HashMap<>();
		try {
			String name = request.getParameter("name");
			String openId = request.getParameter("openId");
			if (StringUtils.isBlank(openId)) {
				content = new ReturnContent<Map<String, Object>>("99", "openId不能为空");
				return JSON.toJSONString(content);
			}
			if (StringUtils.isBlank(name)) {
				content = new ReturnContent<Map<String, Object>>("99", "运势参数不能为空");
				return JSON.toJSONString(content);
			}
			boolean result = XmlUtil.getById(openId);
			if (!result) {
				logger.info("首次创建:{}", openId);
				XmlUtil.createUser(name, openId);
			} else {
				logger.info("已经存在:{}", openId);
			}
			content = new ReturnContent<Map<String, Object>>("00", "创建成功");
		} catch (Exception e) {
			e.printStackTrace();
			content = new ReturnContent<Map<String, Object>>("99", "系统异常", returnMap);
		}
		return JSON.toJSONString(content);
	}

	/**
	 * 删除抽签记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("join/delete")
	public @ResponseBody String delete(HttpServletRequest request) {
		ReturnContent<Map<String, Object>> content = null;
		Map<String, Object> returnMap = new HashMap<>();
		try {
			String openId = request.getParameter("openId");
			if (StringUtils.isBlank(openId)) {
				content = new ReturnContent<Map<String, Object>>("99", "openId不能为空");
				return JSON.toJSONString(content);
			}
			XmlUtil.deleteUser(openId);
			content = new ReturnContent<Map<String, Object>>("00", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			content = new ReturnContent<Map<String, Object>>("99", "系统异常", returnMap);
		}
		return JSON.toJSONString(content);
	}

	@RequestMapping("join/list")
	public @ResponseBody String list(HttpServletRequest request) {
		List<Map<String, String>> list = null;
		try {
			list = XmlUtil.getAllMemebers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(list);
	}
}
