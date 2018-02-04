package com.lifeng.web.wx;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import com.lifeng.util.MessageUtil;

@Service("eventRequestDealer")
public class EventRequestDealer {

	private static Logger logger = LoggerFactory.getLogger(EventRequestDealer.class);

	public String receiveEvent(Map<String, String> requestMap) throws UnsupportedEncodingException {
		String responseText = "";
		// 事件类型
		String eventType = requestMap.get("Event");
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		String eventKey = requestMap.get("EventKey");
		// 订阅
		if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE) || MessageUtil.EVENT_TYPE_SCAN.equals(eventType)) {
			if ("qrscene_trilead_first_qrcode".equals(eventKey) || "trilead_first_qrcode".equals(eventKey)) {
				logger.info("新用户关注，openId:{}", fromUserName);
				List<Article> articleList = new ArrayList<>();
				Article article = new Article();
				article.setDescription("");
				article.setPicUrl("http://www.lifengedu.com/wx/html/chouqian/image/tuisongtu.png");
				article.setTitle("祝福要传递，好运需分享");
				article.setUrl("http://www.lifengedu.com/wx/home");
				articleList.add(article);
				return MessageUtil
						.newsMessageToXml(new NewsMessage(toUserName, fromUserName, articleList.size(), articleList));
			}
		}
		// 取消订阅
		else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
		}
		// 推送模板事件
		else if (eventType.equals(MessageUtil.TEMPLATE_SEND_JOB_FINISH)) {
			String status = requestMap.get("Status");
			String msgId = requestMap.get("MsgID");
			if ("success".equals(status)) {
			} else {
				logger.info("微信模板推送失败openId:{},status:{},msgId:{}", fromUserName, status, msgId);
			}
		} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
		}
		// 推送模板事件
		else if (eventType.equals(MessageUtil.TEMPLATE_SEND_JOB_FINISH)) {
			String status = requestMap.get("Status");
			String msgId = requestMap.get("MsgID");
			if ("success".equals(status)) {
			} else {
				logger.info("微信模板推送失败openId:{},status:{},msgId:{}", fromUserName, status, msgId);
			}
		}
		// 自定义菜单事件-点击菜单拉取消息时的事件推送
		else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
			logger.info("自定义菜单点击事件 eventKey:{}", eventKey);
		}
		if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
		}
		return "success";
	}

}
