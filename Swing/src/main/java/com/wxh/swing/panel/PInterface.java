package com.wxh.swing.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.wxh.swing.FileIO;
import com.wxh.swing.MyString;
import com.wxh.swing.SendHttpImpl;
import com.wxh.swing.utils.CharaccterOperate;
import com.wxh.swing.utils.Format;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PInterface extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextPane showRequest;
	private JTextArea showResult;

	private String filePath;
	private int type;

	private SendHttpImpl hClient = new SendHttpImpl();

	/**
	 * @param filePath   Full file path which including the requests.
	 * @param type       1:buss 2:docif 3.workflow.
	 */
	public PInterface(String filePath, int type) {
		this.filePath = filePath;
		this.type = type;
		this.setEnabled(true);
		this.setVisible(true);
		setLayout(null);

		autoCreateButtons();

		JButton btnAll = new JButton("All");
		btnAll.setBounds(1150, 780, 100, 23);
		btnAll.addActionListener(this);
		btnAll.setActionCommand(btnAll.getText());
		add(btnAll);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(1020, 780, 100, 23);
		btnClear.addActionListener(this);
		btnClear.setActionCommand(btnClear.getText());
		add(btnClear);

		JLabel lblResult = new JLabel("Result: ");
		lblResult.setBounds(60, 580, 100, 23);
		add(lblResult);

		showRequest = new JTextPane();
		showRequest.setBounds(60, 505, 1320, 60);
		add(showRequest);

		showResult = new JTextArea();
		JScrollPane jsp = new JScrollPane(showResult);
		// 设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
		jsp.setBounds(170, 580, 1200, 200);
		// 默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jsp);
	}

	private void autoCreateButtons() {
		String data = FileIO.readFile(filePath);
		String[] requests = data.split(MyString.ENTER + MyString.ENTER);
		requests = CharaccterOperate.sortMerge(requests);

		JPanel pMethod = new JPanel();
		JScrollPane jsp = new JScrollPane(pMethod);
		this.add(jsp);
		jsp.setBounds(50, 5, 1340, 495);
		/** setLayout 只有当 rows == 0 时，列的定义才生效 */
		pMethod.setLayout(new GridLayout(0, 5, 5, 5));

		for (String request : requests) {
			final String funcName = request.substring(0, request.indexOf(MyString.ENTER));
			JButton button = new JButton(funcName);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					showRequest.setText(hClient.getRequest(filePath).get(funcName).toString());
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					String result = hClient.cssmis(null, type, funcName).toString();
					showResult.setText(Format.json(result));
				}
			});

			pMethod.add(button);
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();

		if (command.equals("All")) {
			SendHttpImpl hClient = new SendHttpImpl();
			String result = hClient.cssmis(null, type, null).toString();
			showResult.setText(Format.json(result));
		} else if (command.equals("Clear")) {
			showResult.setText(null);
		}
	}
}
