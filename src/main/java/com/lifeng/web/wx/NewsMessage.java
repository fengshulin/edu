package com.lifeng.web.wx;


import java.util.Date;
import java.util.List;

import com.lifeng.util.MessageUtil;

public class NewsMessage extends BaseMessage {
	// 图文消息个数，限制为10条以内
	private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> Articles;

	public NewsMessage() {

	}

	public NewsMessage(String fromUserName, String toUserName, int articleCount, List<Article> articleList) {
		this.setFromUserName(fromUserName);
		this.setToUserName(toUserName);
		this.setCreateTime(new Date().getTime());
		this.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		this.ArticleCount = articleList.size();
		this.Articles = articleList;
	}

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}