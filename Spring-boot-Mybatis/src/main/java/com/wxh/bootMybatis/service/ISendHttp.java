package com.wxh.bootMybatis.service;

public interface ISendHttp {
	/**
	 * @param type       1:buss 2:docif 3.workflow
	 * @param methodName Method Name
	 * @param filePath   Full path
	 * 
	 * @return
	 */
	Object cssmis(String filePath, Integer type, String methodName);

}
