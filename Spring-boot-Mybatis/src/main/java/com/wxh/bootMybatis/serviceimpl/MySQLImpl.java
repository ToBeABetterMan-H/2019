package com.wxh.bootMybatis.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wxh.bootMybatis.model.MyString;
import com.wxh.bootMybatis.service.IMySQL;
import com.wxh.bootMybatis.utils.FileIO;

@Service("mysqlServic")
public class MySQLImpl implements IMySQL {

	/** @Date 2019-04-27 pm */
	@Override
	public String cRecordDependOnDocument(String tableName, String fileName, Integer count) {
		// TODO Auto-generated method stub
		String data = MyString.BLANK;

		if (count == null || count < 1) {
			count = 1;
		}

		while (count > 0) {
			data += "INSERT INTO " + tableName + "(";
			Map<Integer, List<String>> source = FileIO.readLikeExcel(FileIO.readFile(fileName), 5);

			for (int i = 0; i < source.get(0).size(); i++) {
				data += source.get(0).get(i) + MyString.COMMA;
			}
			data = data.substring(0, data.length() - 1);
			data += ") VALUES(";

			for (int i = 0; i < source.get(0).size(); i++) {
				if (source.get(1).get(i).equalsIgnoreCase("TINYINT")) {
					data += count + MyString.COMMA;
				} else if (source.get(1).get(i).equalsIgnoreCase("DOUBLE")) {
					data += (1.234 + count) + MyString.COMMA;
				} else if (source.get(1).get(i).equalsIgnoreCase("DATETIME")) {
//				设置日期格式
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					data += "\"" + df.format(new Date()) + "\",";
				} else {
					if (i == 0) {
						data += "\"" + UUID.randomUUID().toString().replaceAll("-", "") + "\",";
					} else {
						try {
							data += "\"" + source.get(4).get(i) + count + "\",";
						} catch (Exception e) {
							data += "\"" + source.get(3).get(i) + count + "\",";
						}
					}
				}
			}
			// remove the trailing comma.
			data = data.substring(0, data.length() - 1);

			data += ");" + MyString.ENTER;

			count--;
		}
		return data;
	}

}
