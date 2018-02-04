package com.lifeng.web.wx;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lifeng.util.MessageUtil;

@Service("weixinRequestDealer")
public class WeixinRequestDealer {
	Logger logger = LoggerFactory.getLogger(WeixinRequestDealer.class);

	@Resource
	private EventRequestDealer eventRequestDealer;
	@Resource
	private MessageRequestDealer messageRequestDealer;

	public String getMessage(HttpServletRequest request) {
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			logger.info("请求参数：{}", JSON.toJSONString(requestMap));

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");

			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				return messageRequestDealer.receiveMessage(requestMap);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {

			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				return eventRequestDealer.receiveEvent(requestMap);
			}
		} catch (Exception e) {
			logger.error("微信回复消息异常：{}", e.getMessage());
			return "";
		}

		return "success";
	}

}
