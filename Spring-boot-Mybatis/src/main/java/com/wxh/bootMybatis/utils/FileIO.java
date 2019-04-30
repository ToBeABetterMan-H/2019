package com.wxh.bootMybatis.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;

public class FileIO {
	/**
	 * @Author: wxh
	 * 
	 * @Date: 2019-04-08 pm
	 * 
	 * @Description:读取类似于excel格式的数据---几行几列
	 */
	public static Map<Integer, List<String>> readLikeExcel(String str, int colNumber) {
		Map<Integer, List<String>> data = new HashMap<Integer, List<String>>();

		for (int i = 0; i < colNumber; i++) {
			List<String> cols = new ArrayList<String>();
			data.put(i, cols);
		}

		String[] rows = str.split("\r\n");

		for (String row : rows) {
			String[] cell = row.split("	");

			for (int i = 0; i < colNumber; i++) {
				try {
					data.get(i).add(cell[i]);
				} catch (Exception e) {
					data.get(i).add("");
				}
			}
		}
		return data;
	}

	/**
	 * @param filePath 要读取的文件的完整路径
	 * 
	 * @return 以字符串的形式返回所读取的文件内容
	 * 
	 * @Author wxh
	 * 
	 * @Date 2019-04-08 pm
	 * 
	 * @Description 读取目标文件信息
	 */
	public static String readFile(String filePath) {
		String desString;

		byte[] strBuffer = null;

		int fLen = 0;

		File desFile = new File(filePath);

		try {
			@SuppressWarnings("resource")
			InputStream in = new FileInputStream(desFile);
			fLen = (int) desFile.length();
			strBuffer = new byte[fLen];
			in.read(strBuffer, 0, fLen);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 构建String时，可用byte[]类型
		desString = new String(strBuffer);

		return desString;
	}

	/**
	 * If the file dose not exist, create the file. Overwrite if file exist.
	 * 
	 * @author wxh
	 * 
	 * @Date 2019-04-11 am
	 */
	public static void coverFile(String filePath, String fileName, String data) {
		try {
			FileWriter fw = new FileWriter(filePath + "/" + fileName);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * If the file dose not exist, create the file. If the file exists,insert data
	 * from the end of the file.
	 * 
	 * @author wxh
	 * 
	 * @Date 2019-04-13 am
	 */
	public static void insertFile(String filePath, String fileName, String data) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(filePath + "/" + fileName, true);
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the TABLE of word file.
	 * 
	 * @param filePath Full path.
	 * @return String
	 * @author wxh
	 * @Date 2019-04-30 pm
	 */
	public static String readTable(String filePath) {

		return null;
	}

	/**
	 * Print book marks of word file.
	 * 
	 * @param filePath Full path.
	 * @return String
	 * @author wxh
	 * @Date 2019-04-30 pm
	 */
	public static void printInfo(Bookmarks bookmarks) {
		int count = bookmarks.getBookmarksCount();
		System.out.println("书签数量：" + count);
		Bookmark bookmark;
		for (int i = 0; i < count; i++) {
			bookmark = bookmarks.getBookmark(i);
			System.out.println("书签" + (i + 1) + "的名称是：" + bookmark.getName());
			System.out.println("开始位置：" + bookmark.getStart());
			System.out.println("结束位置：" + bookmark.getEnd());
		}
	}
}