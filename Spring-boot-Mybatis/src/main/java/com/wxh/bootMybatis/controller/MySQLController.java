package com.wxh.bootMybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wxh.bootMybatis.model.MyString;
import com.wxh.bootMybatis.service.IMySQL;

@RestController
@RequestMapping("/MySQL")
public class MySQLController {
	@Autowired
	@Qualifier("mysqlServic")
	private IMySQL mysqlServic;

	@RequestMapping(value = "cRecordDependOnDocument", params = { "tableName", "fileName", "count" })
	public Object cRecordDependOnDocument(@RequestParam String tableName, @RequestParam String fileName,
			@RequestParam Integer count) {
		String result = MyString.BLANK;
		result += mysqlServic.cRecordDependOnDocument(tableName, fileName, count);
		return result;
	}
}
