package com.wxh.swing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wxh.swing.MyString;

public class CharaccterOperate {
	public static String[] srcString = {MyString.SPROJECTNAME   ,
			"P3-PENA-莲融",
			MyString.SSUOXIE        ,
			MyString.SDATE_JIHUA    ,
			MyString.SDATE_XUQIU    ,
			MyString.SDATE_SHEJI    ,
			MyString.SDATE_BIANMA   ,
			MyString.SDATE_ZOUCHA   ,
			MyString.SDATE_DANYUAN  ,
			MyString.SDATE_JICHENG  ,
			MyString.SDATE_XITONG   ,
			MyString.SDATE_YANZHENG ,
			MyString.SDATE_FABU     ,
			MyString.SPERSON_1      ,
			MyString.SPERSON_2      ,
			MyString.SPERSON_3      ,
			MyString.SPERSON_4      ,
			MyString.SPERSON_5      ,
			MyString.SPERSON_6      ,
			MyString.SPERSON_7 ,
			"2018/11/9"};
	public static String[] desString = {MyString.DPROJECTNAME   ,
			MyString.DPROJECTNAME,
			MyString.DSUOXIE        ,
			MyString.DDATE_XUQIU    ,
			MyString.DDATE_JIHUA    ,
			MyString.DDATE_SHEJI    ,
			MyString.DDATE_BIANMA   ,
			MyString.DDATE_ZOUCHA   ,
			MyString.DDATE_DANYUAN  ,
			MyString.DDATE_JICHENG  ,
			MyString.DDATE_XITONG   ,
			MyString.DDATE_YANZHENG ,
			MyString.DDATE_FABU     ,
			MyString.DPERSON_1      ,
			MyString.DPERSON_2      ,
			MyString.DPERSON_3      ,
			MyString.DPERSON_4      ,
			MyString.DPERSON_5      ,
			MyString.DPERSON_6      ,
			MyString.DPERSON_7 ,
			"2018/05/09"};
	
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
	 * .xlsx文件的替换
	 * 
	 * @throws IOException
	 */
	public static void updXLSX() throws IOException {
		String directPath = "E:\\CMMI\\CM\\CMMI\\02项目库\\PON+EOC网管系统软件\\02管理域\\07配置管理\\TEST";
		File ff = new File(directPath);
		if (!ff.isDirectory()) {
			System.out.println("非目录");
			return;
		}
		String[] filelist = ff.list();
		for (String filePath : filelist) {
			System.out.println("fileName = " + filePath);
			File f = new File(directPath + "\\" + filePath);
			InputStream inputStream = new FileInputStream(f);
			if(filePath.contains("xlsx"))
			{
				xlsxFile(inputStream,f);	
			}else {
				xlsFile(inputStream,f);
			}
			
			
		}
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void xlsxFile(InputStream inputStream,File f) throws IOException
	{
		@SuppressWarnings("resource")
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0); // 如果是.xlsx文件使用这个
		for (Row row : xssfSheet) {
			for (Cell hssfCell : row) {
				if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
				} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
				} else if (hssfCell.getCellType() == Cell.CELL_TYPE_STRING) {
					String str = hssfCell.getStringCellValue();
					System.out.println(str);
					for(int i =0;i<srcString.length;i++) {
						if(str.contains(srcString[i])) {
							hssfCell.setCellValue(new XSSFRichTextString(str.replace(srcString[i], desString[i])));
						}
					}
//					if (str.equals(MyString.SPROJECTNAME)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPROJECTNAME));
//					} else if (str.equals(MyString.SSUOXIE)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DSUOXIE));
//					} else if (str.equals(MyString.SDATE_JIHUA)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_JIHUA));
//					} else if (str.equals(MyString.SDATE_XUQIU)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_XUQIU));
//					} else if (str.equals(MyString.SDATE_SHEJI)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_SHEJI));
//					} else if (str.equals(MyString.SDATE_BIANMA)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_BIANMA));
//					} else if (str.equals(MyString.SDATE_ZOUCHA)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_ZOUCHA));
//					} else if (str.equals(MyString.SDATE_DANYUAN)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_DANYUAN));
//					} else if (str.equals(MyString.SDATE_JICHENG)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_JICHENG));
//					} else if (str.equals(MyString.SDATE_XITONG)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_XITONG));
//					} else if (str.equals(MyString.SDATE_YANZHENG)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_YANZHENG));
//					} else if (str.equals(MyString.SDATE_FABU)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DDATE_FABU));
//					} else if (str.equals(MyString.SPERSON_1)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPERSON_1));
//					} else if (str.equals(MyString.SPERSON_2)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPERSON_2));
//					} else if (str.equals(MyString.SPERSON_3)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPERSON_3));
//					} else if (str.equals(MyString.SPERSON_4)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPERSON_4));
//					} else if (str.equals(MyString.SPERSON_5)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPERSON_5));
//					} else if (str.equals(MyString.SPERSON_6)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPERSON_6));
//					} else if (str.equals(MyString.SPERSON_7)) {
//						hssfCell.setCellValue(new XSSFRichTextString(MyString.DPERSON_7));
//					}

				}
				
			}
		}
		FileOutputStream fileOut = new FileOutputStream(f);
		xssfWorkbook.write(fileOut);
		fileOut.close();
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void xlsFile(InputStream inputStream,File f) throws IOException
	{
		@SuppressWarnings("resource")
		HSSFWorkbook xssfWorkbook = new HSSFWorkbook(inputStream);
		HSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0); // 如果是.xlsx文件使用这个
		for (Row row : xssfSheet) {
			for (Cell hssfCell : row) {
				if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
				} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
					System.out.println(hssfCell.getNumericCellValue());
					if(hssfCell.getNumericCellValue() == 43444.0) {
						hssfCell.setCellValue(43594.0);
					}
				}else if (hssfCell.getCellType() == Cell.CELL_TYPE_STRING) {
					String str = hssfCell.getStringCellValue();
					for(int i =0;i<srcString.length;i++) {
						if(str.contains(srcString[i])) {
							hssfCell.setCellValue(new HSSFRichTextString(str.replace(srcString[i], desString[i])));
						}
					}
//					if (str.equals(MyString.SPROJECTNAME)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPROJECTNAME));
//					} else if (str.equals(MyString.SSUOXIE)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DSUOXIE));
//					} else if (str.equals(MyString.SDATE_JIHUA)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_JIHUA));
//					} else if (str.equals(MyString.SDATE_XUQIU)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_XUQIU));
//					} else if (str.equals(MyString.SDATE_SHEJI)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_SHEJI));
//					} else if (str.equals(MyString.SDATE_BIANMA)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_BIANMA));
//					} else if (str.equals(MyString.SDATE_ZOUCHA)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_ZOUCHA));
//					} else if (str.equals(MyString.SDATE_DANYUAN)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_DANYUAN));
//					} else if (str.equals(MyString.SDATE_JICHENG)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_JICHENG));
//					} else if (str.equals(MyString.SDATE_XITONG)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_XITONG));
//					} else if (str.equals(MyString.SDATE_YANZHENG)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_YANZHENG));
//					} else if (str.equals(MyString.SDATE_FABU)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DDATE_FABU));
//					} else if (str.equals(MyString.SPERSON_1)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPERSON_1));
//					} else if (str.equals(MyString.SPERSON_2)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPERSON_2));
//					} else if (str.equals(MyString.SPERSON_3)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPERSON_3));
//					} else if (str.equals(MyString.SPERSON_4)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPERSON_4));
//					} else if (str.equals(MyString.SPERSON_5)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPERSON_5));
//					} else if (str.equals(MyString.SPERSON_6)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPERSON_6));
//					} else if (str.equals(MyString.SPERSON_7)) {
//						hssfCell.setCellValue(new HSSFRichTextString(MyString.DPERSON_7));
//					}

				}
			}
		}
		FileOutputStream fileOut = new FileOutputStream(f);
		xssfWorkbook.write(fileOut);
		fileOut.close();
	}
}
