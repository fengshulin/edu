package com.lifeng.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {
	private static String xmlPath = "/usr/local/lifeng/webapps/wxfile/join.xml";

	public static void getAllMemebers() {
		/*
		 * 创建文件工厂实例
		 */
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// 如果创建的解析器在解析XML文档时必须删除元素内容中的空格，则为true，否则为false
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			/*
			 * 创建文件对象
			 */
			DocumentBuilder db = dbf.newDocumentBuilder();// 创建解析器，解析XML文档
			Document doc = db.parse(xmlPath); // 使用dom解析xml文件

			/*
			 * 历遍列表，进行XML文件的数据提取
			 */
			// 根据节点名称来获取所有相关的节点
			NodeList sonlist = doc.getElementsByTagName("user");
			for (int i = 0; i < sonlist.getLength(); i++) // 循环处理对象
			{
				// 节点属性的处理
				Element son = (Element) sonlist.item(i);
				// 循环节点son内的所有子节点
				for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()) {
					// 判断是否为元素节点
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						String name = node.getNodeName();
						String value = node.getFirstChild().getNodeValue();
						System.out.println(name + " : " + value);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean getById(String id) {
		// 创建文件工厂实例
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			// 从XML文档中获取DOM文档实例
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 获取Document对象
			Document xmldoc = db.parse(xmlPath);

			// 获取根节点
			Element root = xmldoc.getDocumentElement();
			Element per = (Element) selectSingleNode("/result/user[@id='" + id + "']", root);
			if (per == null) {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	// 修改
	public static void modifyUser() {
		// 创建文件工厂实例
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			// 从XML文档中获取DOM文档实例
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 获取Document对象
			Document xmldoc = db.parse(xmlPath);

			// 获取根节点
			Element root = xmldoc.getDocumentElement();
			// 定位id为001的节点
			Element per = (Element) selectSingleNode("/result/user[@id='001']", root);
			// 将age节点的内容更改为28
			per.getElementsByTagName("age").item(0).setTextContent("30");
			// 保存
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// 获取目标节点，进行删除，最后保存
	public static void discardUser() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmldoc = db.parse(xmlPath);
			// 获取根节点
			Element root = xmldoc.getDocumentElement();
			// 定位根节点中的id=002的节点
			Element son = (Element) selectSingleNode("/result/user[@id='002']", root);
			// 删除该节点
			root.removeChild(son);
			// 保存
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// 新增节点
	public static void createUser(String result, String openId) {
		// 创建文件工厂实例
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(false);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 创建Document对象
			Document xmldoc = db.parse(xmlPath);
			// 获取根节点
			Element root = xmldoc.getDocumentElement();
			// 创建节点son，设置对应的id为004
			Element son = xmldoc.createElement("user");
			son.setAttribute("id", openId);
			// 创建节点name
			Element name = xmldoc.createElement("name");
			name.setTextContent(result);
			son.appendChild(name);
			// 创建节点age
			Element age = xmldoc.createElement("openId");
			age.setTextContent(openId);
			son.appendChild(age);
			// 把son添加到根节点中
			root.appendChild(son);
			// 保存
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// 修改节点信息
	public static Node selectSingleNode(String express, Element source) {
		Node result = null;
		// 创建XPath工厂
		XPathFactory xpathFactory = XPathFactory.newInstance();
		// 创建XPath对象
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	// 打印
	public static void main(String[] args) {
		String openId = "1234";
		boolean result = getById(openId);
		if (!result) {
			createUser("一等奖", openId);
		}
		// System.out.println(result);
		// modifySon();
		// System.out.println("修改数据");
		// discardSon();
		// System.out.println("删除数据");
		// createSon();
		// System.out.println("添加数据");
	}

}
