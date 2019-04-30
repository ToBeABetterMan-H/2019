package com.wxh.bootMybatis.service;

public interface IFile {
	/**
	 * Compare the English file.
	 * 
	 * @param leftFile  Source File
	 * @param rightFile File destination
	 * @return
	 */
	String compare(String leftFile, String rightFile);

	/**
	 * Comvert file format.
	 * 
	 * @param fileName Source File
	 * @param format   Format type: json xml
	 * @return In the source directory to generate (.format) file.
	 * @throws Exception 
	 */
	String format(String fileName, String format) throws Exception;

}
