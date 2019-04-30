package com.wxh.bootMybatis.utils;

public class Format {
	/**
	 * 将一串字符串整理成json格式    待优化
	 * 
	 * @author wxh
	 * 
	 * @Date 2019-04-11 am
	 */
	public static String json(String str) {
		str = str.replace("{", "{\r\n	");
		str = str.replace(",", ",\r\n	");
		str = str.replace("}", "\r\n}");
		str = str.replace(":", ": ");

		return str;
	}
}
