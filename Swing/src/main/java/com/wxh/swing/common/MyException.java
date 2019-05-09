package com.wxh.swing.common;

public class MyException extends RuntimeException {

	private static final long serialVersionUID = -2286680814245759744L;
	@SuppressWarnings("unused")
	private MyError error;

	public MyException() {

	}

	/**
	 * 构造方法
	 * 
	 * @param code 返回码
	 */
	public MyException(MyError error) {
		this.error = error;
	}

	/**
	 * 构造方法
	 * 
	 * @param result 返回值
	 * @param cause  异常堆栈
	 */
	public MyException(MyError error, Throwable cause) {
		super(error.getErrorMsg(), cause);
		this.error = error;
	}

	/**
	 * 构造方法
	 * 
	 * @param code  返回码
	 * @param msg   返回消息
	 * @param cause 异常堆栈
	 */
	public MyException(MyError error, String msg) {
		super(msg);
		this.error = error;
	}

}
