package com.lifeng.web.wx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.lifeng.util.HttpClientUtil;

/**
 * 菜单管理
 * 
 * @author solin
 *
 */
public class PersonMenu {
	public static final String accessToken = "6_dGU0ENs3ffI-YLrWmbK53zYcWptU5OyxA2LStsJd_mxRi-z6MRFFI8BDOhFq9jQUip4C_dZMFI6ucg7GSXEIq_D0L7bfGKOnhQtz44TO2-vYUhEHBn_DekeSnH-3G0vfNxjmJKjw3-2hTmu3FUWiAGARGI";
	public static String deleteUrl = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN";
	public static final String menuAddconditionalUrl = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";

	public static void main(String[] args) {
		// menuCreate();
		delete();
	}

	/**
	 * 菜单创建
	 */
	public static void menuCreate() {
		Map<String, Object> map = new HashMap<>();

		List<Object> buttonList = new ArrayList<>();

		// // 底部一级
		// Map<String, Object> map1 = new HashMap<String, Object>();
		// List<Object> map1List = new ArrayList<>();
		// Map<String, Object> map11 = new HashMap<String, Object>();
		// map11.put("type", "view");
		// map11.put("name", "交易·打款查询");
		// map11.put("url",
		// "https://wx.lefu8.com/wxcustfront/customer/toTradeMainList");
		// Map<String, Object> map12 = new HashMap<String, Object>();
		// map12.put("type", "view");
		// map12.put("name", "我的信息");
		// map12.put("url", "https://wx.lefu8.com/wxcustfront/wx/menu/XXE9vB2a");
		// Map<String, Object> map13 = new HashMap<String, Object>();
		// map13.put("type", "view");
		// map13.put("name", "￥利息宝天天赚");
		// map13.put("url", "https://wx.lefu8.com/wxcustfront/fund/index");
		// Map<String, Object> map14 = new HashMap<String, Object>();
		// map14.put("type", "view");
		// map14.put("name", "账户设置");
		// map14.put("url", "https://wx.lefu8.com/wxcustfront/wx/menu/ZHCjRTNQ");
		// map1List.add(map11);
		// map1List.add(map12);
		// // map1List.add(map13);
		// map1List.add(map14);
		// map1.put("name", "查询");
		// map1.put("sub_button", map1List);
		// buttonList.add(map1);
		//
		// // 底部二级
		// Map<String, Object> map2 = new HashMap<String, Object>();
		// List<Object> map2List = new ArrayList<>();
		// Map<String, Object> map23 = new HashMap<String, Object>();
		// map23.put("type", "view");
		// map23.put("name", "机具维修");
		// map23.put("url", "https://wx.lefu8.com/wxcustfront/wx/menu/JJ1vVXaT");
		// Map<String, Object> map21 = new HashMap<String, Object>();
		// map21.put("type", "view");
		// map21.put("name", "乐富轻松购");
		// map21.put("url", "https://wx.lefu8.com/wxcustfront/wx/menu/SH9pF3Ci");
		// Map<String, Object> map22 = new HashMap<String, Object>();
		// map22.put("type", "click");
		// map22.put("name", "在线客服");
		// map22.put("key", "ONLINE");
		// Map<String, Object> map24 = new HashMap<String, Object>();
		// map24.put("type", "view");
		// map24.put("name", "增值服务");
		// map24.put("url", "https://wx.lefu8.com/wxcustfront/wx/menu/ZZuw3Bjz");
		// Map<String, Object> map25 = new HashMap<String, Object>();
		// map25.put("type", "view");
		// map25.put("name", "常见问题");
		// map25.put("url", "http://wxcache.lefu8.com/WxResource/html/faq/faq.html");
		// map2List.add(map23);
		// map2List.add(map21);
		// map2List.add(map22);
		// map2List.add(map24);
		// map2List.add(map25);
		// map2.put("name", "服务");
		// map2.put("sub_button", map2List);
		// buttonList.add(map2);

		// 底部三级
		Map<String, Object> map3 = new HashMap<String, Object>();
		List<Object> map3List = new ArrayList<>();
		Map<String, Object> map33 = new HashMap<String, Object>();
		map33.put("type", "media_id");
		map33.put("name", "设计大赛");
		map33.put("media_id", "pYZnrZeO9y8PoJQBbTxl4N06prnVtCo3Sx5OWo6xS0w");
		Map<String, Object> map31 = new HashMap<String, Object>();
		map31.put("type", "media_id");
		map31.put("name", "体验报名");
		map31.put("media_id", "pYZnrZeO9y8PoJQBbTxl4JLVtyqT0owPfImJE-GjJIY");
		Map<String, Object> map32 = new HashMap<String, Object>();
		map32.put("type", "view");
		map32.put("name", "新年抽签");
		map32.put("url", "http://www.lifengedu.com/wx/home");
		map3List.add(map33);
		map3List.add(map31);
		map3List.add(map32);
		// map3.put("name", "活动");
		// map3.put("sub_button", map3List);
		// buttonList.add(map3List);

		map.put("button", map3List);
		Map<String, String> mapmatch = new HashMap<>();
		mapmatch.put("group_id", "100");
		map.put("matchrule", mapmatch);

		System.out.println(JSON.toJSONString(map));
		//
		String result = HttpClientUtil.sendPost(menuAddconditionalUrl.replace("ACCESS_TOKEN", accessToken),
				JSON.toJSONString(map));
		System.out.println(result);

	}

	public static void delete() {
		Map<String, String> map = new HashMap<>();
		map.put("menuid", "480826551");
		String result = HttpClientUtil.sendPost(deleteUrl.replace("ACCESS_TOKEN", accessToken), JSON.toJSONString(map));
		System.out.println(result);
	}
}
