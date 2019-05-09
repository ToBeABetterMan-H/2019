package com.wxh.swing.common;

public enum MyError {
	NoError(10000, "成功"),
	LocalError(10001, "本地错误"),
	RemoteError(10002, "服务器错误"),
	
	PlaceHolderError(999999, "占位符");

	private final int errorCode;
	private  String errorMsg;

	private MyError(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
	    return errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
