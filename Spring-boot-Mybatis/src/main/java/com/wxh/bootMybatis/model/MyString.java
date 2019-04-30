package com.wxh.bootMybatis.model;

public class MyString {
	/** 逗号 */
	public final static String COMMA = ",";
	/** 制表符 */
	public final static String TABLE = "	";
	/** 换行 */
	public final static String ENTER = "\r\n";
	/** 空白 */
	public final static String BLANK = "";
	/** 空格 */
	public final static String SPACE = " ";
	/** 等号 */
	public final static String EQUALS = "=";
	
	/** 失败 */
	public static final String FAILED = "Failed !!!";
	/** 成功 */
	public static final String SUCCEED = "Succeed !!!";

	/** CSSMIS的IP */
	public static final String IP_CSS = "192.168.1.123";
	/** CSSMIS的PORT */
	public static final String PORT_CSS = "10243";
	/* CSSMIS的URL */
	public static final String URL_BUSS = "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/buss/";
	public static final String URL_WORKFLOW = "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/wf/";
	public static final String URL_DOCIF = "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/docif/";
	/** 请求参数文件路径_BUSS */
	public static final String PATH_REQUEST_BUSS = "F:\\2019\\wxh\\测试用例\\20190430-批量测试\\Request_buss.txt";
	/** 请求参数文件路径_DOCIF */
	public static final String PATH_REQUEST_DOCIF = "F:\\2019\\wxh\\测试用例\\20190430-批量测试\\Request_docif.txt";
	/** 请求参数文件路径_WORKFLOW */
	public static final String PATH_REQUEST_WORKFLOW = "F:\\2019\\wxh\\测试用例\\20190430-批量测试\\Request_workflow.txt";
}
