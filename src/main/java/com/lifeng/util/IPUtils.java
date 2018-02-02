package com.lifeng.util;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
	/**
	 * 获取客户端ip
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取本机机器名
	 */
	public static String getLocalHostName() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString();// 获得本机IP
			System.out.println(ip);
			String hostName = addr.getHostName().toString();// 获得本机名称
			System.out.println(hostName);
			return hostName;
		} catch (Exception e) {
			System.out.println("Bad IP Address!" + e);
			return "";
		}
	}

	public static void main(String[] args) {
		getLocalHostName();
	}

}
