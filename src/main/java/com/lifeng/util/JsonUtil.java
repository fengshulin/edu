package com.lifeng.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	public static String path = "/usr/local/lifeng/webapps/wxfile/";

	// 字符串、json 写入文件
	public static void writeStringToFile(String pathName, String token, String time) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(pathName, token);
		jsonObject.put("time", time);
		String json = JSON.toJSONString(jsonObject);
		File txt = new File(path + pathName + ".json");
		if (!txt.exists()) {
			try {
				txt.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		byte[] bytes = json.getBytes(); // 新加的
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(txt);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject read(String pathName) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(path + pathName + ".json");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		JSONObject jsonObject = JSON.parseObject(laststr);
		return jsonObject;
	}

}
