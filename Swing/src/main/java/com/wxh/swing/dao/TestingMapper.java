package com.wxh.swing.dao;

import org.springframework.stereotype.Component;

import com.wxh.swing.model.Testing;

@Component
public interface TestingMapper {
	Testing getTestingByID(Integer id);
}
