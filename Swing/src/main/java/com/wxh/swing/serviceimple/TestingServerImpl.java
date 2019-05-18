package com.wxh.swing.serviceimple;

import java.awt.EventQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxh.swing.FirstJFrame;
import com.wxh.swing.dao.TestingMapper;
import com.wxh.swing.service.TestingServer;

@Service("TestingServer")
public class TestingServerImpl implements TestingServer{
	@Autowired
	private TestingMapper testingMapper;
	
	TestingServerImpl(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstJFrame frame = new FirstJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public String getTesting() {
		// TODO Auto-generated method stub
		return testingMapper.getTestingByID(1).toString();
	}

}
