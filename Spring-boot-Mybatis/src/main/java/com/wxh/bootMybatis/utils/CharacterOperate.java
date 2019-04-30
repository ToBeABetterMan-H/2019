package com.wxh.bootMybatis.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterOperate {
	/**
	 * 获取List<String>中最长的字符串的长度
	 * 
	 * @author wxh
	 * 
	 * @Date 2019-04-16 pm
	 */
	public static Integer longestLengthOfList(List<String> source) {
		Integer result = source.get(0).length();
		for (int i = 0; i < source.size(); i++) {
			if (source.get(i).length() > result) {
				result = source.get(i).length();
			}
		}

		return result;
	}

	/**
	 * 将String用' '补充到指定长度,返回需要补充的字符串
	 * 
	 * @param src    源字符串
	 * @param length 要补充到的长度
	 * @param target 用来补充的字符（串）
	 * 
	 * @return 返回补充的后的字符串
	 * 
	 * @author: wxh
	 * 
	 * @Date 2019-04-16 pm
	 */
	public static String fillString(String src, Integer length, String target) {
		String result = src;
		while (result.length() < length) {
			result += target;
		}
		return result;
	}

	/**
	 * @param srcString   原字符串
	 * @param startString 删除的开始位置
	 * @param endString   删除的结束位置
	 * @return 删除后的字符串
	 * 
	 * @Author wxh
	 * 
	 * @Date 2019-04-17 am
	 * 
	 * @Description 去除String中两个子串间的所有字符（包括这两个子串）
	 */
	public static String removeRangeString(String srcString, String startString, String endString) {
		String result = srcString;
		for (;;) {
			int start = result.indexOf(startString);
			if (start != -1) {
				int end = result.indexOf(endString);
				if (end != -1) {
					result = result.substring(0, start) + result.substring(end + endString.length(), result.length());
				} else
					return result;
			} else
				return result;
		}
	}

	/**
	 * @param str 原字符串
	 * 
	 * @return 删除后的字符串
	 * 
	 * @Author wxh
	 * 
	 * @Date 2019-04-23 pm
	 * 
	 * @Description 去除String中所用中文字符
	 */
	public static String removeChinese(String str) {
		Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");// 中文正则表达式
		Matcher mat = pat.matcher(str);
		return mat.replaceAll("").toString();
	}
	
	/** 根据CSSMIS接口文档生成对应的请求参数 */
}
