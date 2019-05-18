package com.wxh.swing;

public class MyString {
	/** 逗号 */
	public final static String COMMA = ",";
	/** 制表符 */
	public final static String TABLE = "	";
	/** 4个空格 */
	public final static String BLANK_FOUR = "    ";
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
	public static String IP_CSS = "192.168.1.123";
	/** CSSMIS的PORT */
	public static String PORT_CSS = "10243";
	/* CSSMIS的URL */
	public static String URL_BUSS = "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/buss/";
	public static String URL_WORKFLOW = "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/wf/";
	public static String URL_DOCIF = "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/docif/";
	/** 请求参数文件路径_BUSS */
	public static final String PATH_REQUEST_BUSS = "src/main/resources/testCase/Request_buss.txt";
	/** 请求参数文件路径_DOCIF */
	public static final String PATH_REQUEST_DOCIF = "src/main/resources/testCase/Request_docif.txt";
	/** 请求参数文件路径_WORKFLOW */
	public static final String PATH_REQUEST_WORKFLOW = "src/main/resources/testCase/Request_workflow.txt";
	public static String getURL_BUSS() {
		return "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/buss/";
	}
	public static String getURL_WORKFLOW() {
		return "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/wf/";
	}
	public static String getURL_DOCIF() {
		return "http://" + IP_CSS + ":" + PORT_CSS + "/CSSMIS/api/docif/";
	}	
	
}
