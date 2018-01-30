package com.lifeng.util;

import java.util.UUID;

/**
 * UUID生成工具类 Created by shulin.feng on 13-12-18.
 */
public class UUIDUtils {

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	/**
	 * 随机生成32位长度的UUID，全小写字母与数字的组合
	 *
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 随机生成8位长度的UUID,大小写数字混合 经测试千万次不重复
	 *
	 * @return
	 */
	public static String getShortUUID() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 获得定长随机字符串 格式如：yyyyMMddHHmmss+5位定长随机数
	 *
	 * @return
	 */
	public static Long getSerialID() {
		String cur = DateFormatUtil.getCurrentDate("yyyyMMddHHmmss");
		Double as = Math.random() * 90000 + 10000;
		cur = cur + as.intValue();
		return Long.valueOf(cur);
	}

	/**
	 * 获得随机定长6位数字
	 *
	 * @return
	 */
	public static String getNumberBySix() {
		Double as = Math.random() * 900000 + 100000;
		String sixNumber = as.intValue() + "";
		return sixNumber;
	}

	public static void main(String[] args) {
		System.out.println(UUIDUtils.getUUID());
	}
}
