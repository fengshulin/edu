package com.lifeng.util;

import java.io.Serializable;

/**
 * @Description: 返回结果
 * @see: ReturnContent 此处填写需要参考的类
 * @version 2016年10月25日 下午6:27:08
 * @author shulin.feng
 */
public class ReturnContent<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 返回码
	 */
	private String code;
	/**
	 * 返回消息
	 */
	private String msg;
	/**
	 * 操作对象
	 */
	private T data;

	/** 无参构造方法 */
	public ReturnContent() {
		super();
	}

	/**
	 * 构造方法
	 *
	 * @param returnCode
	 *            返回码
	 * @param errorMsg
	 *            返回消息
	 */
	public ReturnContent(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 构造方法
	 *
	 * @param returnCode
	 *            返回码
	 * @param errorMsg
	 *            返回消息
	 * @param content
	 *            操作对象
	 */
	public ReturnContent(String code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReturnContent [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
