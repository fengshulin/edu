package com.lifeng.web;

import com.alibaba.fastjson.JSONObject;
import com.lifeng.util.HttpClientUtil;

public class Test {
	public static void main(String[] args) {
		String url = "http://www.lifengedu.com/wx/join/check";
		String joinurl = "http://www.lifengedu.com/wx/join/add";
		for (int i = 0; i < 1000; i++) {
			String result = HttpClientUtil.sendGet(url, "openId=openId_" + i);
			JSONObject jObject = JSONObject.parseObject(result);
			System.out.println(jObject.get("msg"));
			if ("false".equals(jObject.get("msg"))) {
				String result1 = HttpClientUtil.sendGet(joinurl, "openId=openId_" + i + "&name=" + i);
				System.out.println(result1);
			}

		}
	}
}
