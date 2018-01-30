package com.lifeng.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class XmlUtil {
	public static String path = "/Users/solin/Documents/workspace/edu/target/";

	public static void createXml(String token, String time) {

		Element root2 = DocumentHelper.createElement("token");
		Document document2 = DocumentHelper.createDocument(root2);
		// 添加子节点:add之后就返回这个元素
		Element helloElement = root2.addElement("accessToken");
		Element worldElement = root2.addElement("time");

		helloElement.setText(token);
		worldElement.setText(time);

		OutputFormat format = new OutputFormat("    ", true);// 设置缩进为4个空格，并且另起一行为true
		XMLWriter xmlWriter2;
		try {
			xmlWriter2 = new XMLWriter(new FileWriter(new File(path + "token.xml")), format);
			xmlWriter2.write(document2);
			xmlWriter2.flush();
			xmlWriter2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public static JSONObject readXml() {
		System.out.println("==========");
		JSONObject jsonObject = new JSONObject();
		try {
			File f = new File(path + "token.xml");
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document document = saxReader.read(f);
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements("token");
			System.out.println("xxxxx:" + JSON.toJSONString(elements));
			jsonObject.put("token", elements.get(0).elementText("accessToken"));
			jsonObject.put("time", elements.get(0).elementText("time"));
		} catch (DocumentException e) {
			System.out.println();
			e.printStackTrace();
		}
		System.out.println("==========" + JSON.toJSONString(jsonObject));
		return jsonObject;
	}

	public static void main(String[] args) throws Exception {
		// createXml("22222", "1234");
		readXml();
	}

}
