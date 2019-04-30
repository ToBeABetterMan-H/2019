package com.wxh.bootMybatis.serviceimpl;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import com.wxh.bootMybatis.model.MyString;
import com.wxh.bootMybatis.service.IFile;
import com.wxh.bootMybatis.utils.CharacterOperate;
import com.wxh.bootMybatis.utils.FileIO;
import com.wxh.bootMybatis.utils.Format;
import com.wxh.bootMybatis.utils.Reflect;

@Service("fileService")
public class FileImpl implements IFile {

	/** @date 2019-04-28 am */
	@Override
	public String compare(String leftFile, String rightFile) {
		// TODO Auto-generated method stub
		try {
			String[] left = FileIO.readFile(leftFile).split(MyString.SPACE);
			String[] right = FileIO.readFile(rightFile).split(MyString.SPACE);
			String date = MyString.BLANK;

			for (int i = 0; i < left.length && i < right.length; i++) {
				if (!left[i].equals(right[i])) {
					right[i] += "(" + left[i] + ")";
				}
			}
			for (String word : right) {
				date += CharacterOperate.fillString(word, word.length() + 1, MyString.SPACE);
			}
			FileIO.coverFile(MyString.BLANK, rightFile + ".equal", date);

			InputStream is = new FileInputStream("F:\\2019\\doc\\设计文档\\建筑工程施工现场监管信息系统接口设计.docx");
			XWPFDocument xdoc = new XWPFDocument(is);
			XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
			System.out.println(extractor.getText());
			// 输出书签信息
//			FileIO.printInfo(xdoc.getBodyElements());

			return "GoodJob";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	public String format(String fileName, String format) throws Exception {
		// TODO Auto-generated method stub

		Format obj = new Format();

		Object[] args = new Object[] { FileIO.readFile(fileName) };

		FileIO.coverFile(MyString.BLANK, fileName + ".format", Reflect.invokeMethod(obj, format, args).toString());

		return "GoodJob";
	}

}
