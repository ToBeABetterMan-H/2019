package com.wxh.swing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CharaccterOperate {

	/** 归并排序 从字符串的第四个字符开始排序 */
	public static String[] sortMerge(String[] array) {
		if (array.length < 2)
			return array;
		int mid = array.length / 2;
		String[] left = Arrays.copyOfRange(array, 0, mid);
		String[] right = Arrays.copyOfRange(array, mid, array.length);
		return merge(sortMerge(left), sortMerge(right));
	}

	public static String[] merge(String[] left, String[] right) {
		String[] result = new String[left.length + right.length];
		for (int index = 0, i = 0, j = 0; index < result.length; index++) {
			if (i >= left.length) {
				result[index] = right[j++];
			} else if (j >= right.length) {
				result[index] = left[i++];
			} else if (left[i].substring(3).compareTo(right[j].substring(3)) <= 0) {
				result[index] = left[i++];
			} else {
				result[index] = right[j++];
			}
		}
		return result;
	}

	/**
	 * 对Excel表进行替换
	 * 
	 * @param path full path. 若为目录，则对该目录下的所有excel文件进行替换操作
	 * @param srcList 需要替换的字段列表
	 * @param desList 替换后的字段列表
	 * @throws IOException
	 */
	public static void replaceExcel(String path, List<String> srcList, List<String> desList) throws IOException {
		File ff = new File(path);
		if (!ff.isDirectory()) {
			/* 操作单个文件 */
			if (path.contains(".xls")) {
				excelFile(path, srcList, desList);
			}
			return;
		}
		String[] filelist = ff.list();
		for (String fileName : filelist) {
			String filePath = path + "\\" + fileName;
			if (fileName.contains(".xls")) {
				excelFile(filePath, srcList, desList);
			}
		}
	}

	@SuppressWarnings({ "deprecation", "static-access", "resource" })
	private static void excelFile(String filePath, List<String> srcList, List<String> desList) throws IOException {
		File f = new File(filePath);
		InputStream inputStream = new FileInputStream(f);
		List<Row> rows = new ArrayList<Row>();

		Workbook workbook = null;
		if (filePath.contains(".xlsx")) {
			/* .xlsx */
			workbook = new XSSFWorkbook(inputStream);
		} else if (filePath.contains(".xls")) {
			/* .xls */
			workbook = new HSSFWorkbook(inputStream);
		} else {
			return;
		}
		int count = workbook.getNumberOfSheets();

		/* 遍历sheet */
		for (int j = 0; j < count; j++) {
			for(Row row:workbook.getSheetAt(j)) {
				rows.add(row);
			}

			for (Row row : rows) {
				/* 遍历row */
				for (Cell cell : row) {
					/* 遍历cell */
					if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
						/* 处理数字型（读取Excel日期型时为double型，值为1900-01-01至目标日期间的天数，包括头尾两天） */
						for (int i = 0; i < srcList.size(); i++) {
							/* 遍历要替换列表 */
							try {
								Double excelData = cell.getNumericCellValue();/* 从Excel中读取到的数据 */
								Double inputData = Double.parseDouble(srcList.get(i));/* 输入的替换目标 */
								if (excelData.equals(inputData)) {
									Double outputData = Double.parseDouble(desList.get(i));/* 替换后的值 */
									cell.setCellValue(outputData);
								}
							} catch (NumberFormatException e) {
								continue;
							}
						}
					} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						/* 处理字符型 */
						String str = cell.getStringCellValue();
						for (int i = 0; i < srcList.size(); i++) {
							if (str.contains(srcList.get(i))) {
								str = str.replace(srcList.get(i), desList.get(i));
							}
						}
						cell.setCellValue(str);
					}
					/* 其他类型暂不做处理 */
				}
			}
			/* 写入源文件 */
			FileOutputStream fileOut = new FileOutputStream(f);
			workbook.write(fileOut);
			fileOut.close();
		}
		inputStream.close();
	}
}
