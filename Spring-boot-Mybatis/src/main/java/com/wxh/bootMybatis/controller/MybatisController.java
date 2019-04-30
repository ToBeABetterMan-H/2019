package com.wxh.bootMybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wxh.bootMybatis.model.MyString;
import com.wxh.bootMybatis.service.IFile;
import com.wxh.bootMybatis.service.ISendHttp;
import com.wxh.bootMybatis.service.UserService;

@RestController
@RequestMapping("/")
public class MybatisController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("fileService")
	private IFile fileService;

	@Autowired
	@Qualifier("httpService")
	private ISendHttp httpService;

	@RequestMapping(value = "mybatisTest", params = { "userID" })
	public Object mybatisTest(@RequestParam("userID") Integer userID) {
		String result = "Controller Success \r\n";
		if (userService == null)
			return "Controller init failed !!!";
		result += userService.getUserInfoByUserID(userID);
		return result;
	}

	@RequestMapping(value = "compareFile", params = { "leftFile", "rightFile" })
	public Object compareFile(@RequestParam String leftFile, @RequestParam String rightFile) {
		String result = MyString.BLANK;
		result += fileService.compare(leftFile, rightFile);
		return result;
	}

	@RequestMapping(value = "formatFile", params = { "fileName", "format" })
	public Object formatFile(@RequestParam String fileName, @RequestParam String format) throws Exception {
		String result = MyString.BLANK;
		result += fileService.format(fileName, format);
		return result;
	}

	@RequestMapping(value = "cssmis", params = { "type" })
	public Object allMethods(@RequestParam String filePath, @RequestParam Integer type, @RequestParam String methodName)
			throws Exception {
		return httpService.cssmis(filePath, type, methodName);
	}
}
