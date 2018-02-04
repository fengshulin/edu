package com.lifeng.web.wx;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("messageRequestDealer")
public class MessageRequestDealer {
	private static Logger logger = LoggerFactory.getLogger(MessageRequestDealer.class);

	public String receiveMessage(Map<String, String> requestMap) {
		String responseText = "";
		// 输入内容
		String message = requestMap.get("Content");
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		logger.info("messageRequestDealer 用户输入内容：{}", message);

		return "success";
	}

}
