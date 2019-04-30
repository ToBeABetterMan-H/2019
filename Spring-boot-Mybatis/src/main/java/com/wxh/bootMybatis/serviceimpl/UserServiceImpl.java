package com.wxh.bootMybatis.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxh.bootMybatis.dao.UserMapper;
import com.wxh.bootMybatis.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Object getUserInfoByUserID(Object request) {
		// TODO Auto-generated method stub
		if (userMapper == null) {
			return "Autowired UserMapper Failed!!!";
		}
		return userMapper.selectByPrimaryKey((Integer) request);
	}

}
