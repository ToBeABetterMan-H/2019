package com.wxh.swing.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wxh.swing.FileIO;
import com.wxh.swing.MyString;
import com.wxh.swing.utils.CharaccterOperate;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PExcel extends JPanel {
	private static Logger logger = LogManager.getLogger();
	
	private static final long serialVersionUID = 1L;
	private JTextPane tf_filePath;
	private JPanel panel;

	private List<String> srcList = new ArrayList<String>();
	private List<String> desList = new ArrayList<String>();

	/**
	 * Create the panel.
	 */
	public PExcel() {
		setLayout(null);

		JLabel label = new JLabel("文件：");
		label.setBounds(10, 47, 36, 24);
		add(label);

		tf_filePath = new JTextPane();
		tf_filePath.setText("E:\\CMMI\\CM\\CMMI\\组织级文档\\01管理域\\06配置管理");
		tf_filePath.setBounds(56, 50, 513, 21);
		add(tf_filePath);

		JButton btn_OpenFile = new JButton("打开文件");
		btn_OpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(tf_filePath.getText());
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file == null) {
					return;
				}
				tf_filePath.setText(file.getAbsolutePath());
			}
		});
		btn_OpenFile.setBounds(579, 49, 93, 23);
		add(btn_OpenFile);

		panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 82, 1045, 251);
		add(scrollPane);
		panel.setLayout(new GridLayout(0, 5, 5, 5));

		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addConditionToUI(null, null);
			}
		});
		btnAdd.setBounds(210, 363, 93, 23);
		add(btnAdd);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refresh();
				panel.updateUI();
			}
		});
		btnRefresh.setBounds(450, 363, 93, 23);
		add(btnRefresh);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					CharaccterOperate.replaceExcel(tf_filePath.getText(), srcList, desList);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(313, 363, 93, 23);
		add(btnUpdate);
		
		logger.info(this.getBounds());
		logger.info(this.getHeight());
	}

	/**
	 * 在界面上添加条件栏
	 * 
	 * @param find   需要替换的目标值
	 * @param update 替换后的值
	 */
	private void addConditionToUI(String find, String update) {
		final JLabel lbl_find = new JLabel("查找：");
		panel.add(lbl_find);
		final JTextPane tp_find = new JTextPane();
		tp_find.setText(find);
		panel.add(tp_find);
		final JLabel lbl_update = new JLabel("替换：");
		panel.add(lbl_update);
		final JTextPane tp_update = new JTextPane();
		tp_update.setText(update);
		panel.add(tp_update);
		final JButton btn_del = new JButton("del");
		panel.add(btn_del);
		btn_del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel.remove(lbl_find);
				panel.remove(tp_find);
				panel.remove(lbl_update);
				panel.remove(tp_update);
				panel.remove(btn_del);
				panel.updateUI();
			}
		});
		panel.updateUI();
	}
	
	/** 刷新条件 */
	private void refresh() {
		panel.removeAll();
		srcList.clear();
		desList.clear();
		String data = FileIO.readFile("F:\\2019\\wxh\\MysqlScripts\\CMMI.txt");
		String[] rows = data.split(MyString.ENTER);
		for (String row : rows) {
			srcList.add(row.split(MyString.TABLE)[0]);
			desList.add(row.split(MyString.TABLE)[1]);
		}
		int count = srcList.size();
		for (int i = 0; i < count; i++) {
			addConditionToUI(srcList.get(i), desList.get(i));
		}
	}
}
