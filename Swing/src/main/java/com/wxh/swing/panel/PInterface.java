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

	private JTextPane tpIP;
	private JTextPane tpPort;

	private JTextArea showRequest;
	private JTextArea showResult;

	private String filePath;
	private int type;

	private SendHttpImpl hClient = new SendHttpImpl();

	/**
	 * @param filePath Full file path which including the requests.
	 * @param type     1:buss 2:docif 3.workflow.
	 */
	public PInterface(String filePath, int type) {
		this.filePath = filePath;
		this.type = type;
		this.setEnabled(true);
		this.setVisible(true);
		setLayout(null);

		JLabel lblIP = new JLabel("IP:");
		lblIP.setBounds(50, 5, 25, 23);
		add(lblIP);
		tpIP = new JTextPane();
		tpIP.setText(MyString.IP_CSS);
		tpIP.setBounds(75, 5, 100, 23);
		add(tpIP);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(220, 5, 45, 23);
		add(lblPort);
		tpPort = new JTextPane();
		tpPort.setText(MyString.PORT_CSS);
		tpPort.setBounds(265, 5, 100, 23);
		add(tpPort);

//		autoCreateButtons();
		autoCreateButtons2();

		JButton btnChange = new JButton("Change");
		btnChange.setBounds(370, 5, 80, 23);
		btnChange.addActionListener(this);
		btnChange.setActionCommand(btnChange.getText());
		add(btnChange);

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

		showResult = new JTextArea();
		JScrollPane jsp = new JScrollPane(showResult);
		// 设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
		jsp.setBounds(170, 580, 1200, 200);
		// 默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jsp);
	}

	/** model 1 */
	@SuppressWarnings("unused")
	private void autoCreateButtons() {
		showRequest = new JTextArea();
		showRequest.setBounds(60, 505, 1320, 60);
		add(showRequest);
		
		String data = FileIO.readFile(filePath);
		String[] requests = data.split(MyString.ENTER + MyString.ENTER);
		requests = CharaccterOperate.sortMerge(requests);

		JPanel pMethod = new JPanel();
		JScrollPane jsp = new JScrollPane(pMethod);
		this.add(jsp);
		jsp.setBounds(50, 33, 1340, 467);
		/** setLayout 只有当 rows == 0 时，列的定义才生效 */
		pMethod.setLayout(new GridLayout(0, 5, 5, 5));

		for (String request : requests) {
			final String funcName = request.split(MyString.ENTER)[0];
			String btnName = funcName;
			if (!request.split(MyString.ENTER)[1].contains(MyString.EQUALS)) {
				btnName = request.split(MyString.ENTER)[1];
			}
			JButton button = new JButton(btnName);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					showRequest.setText(funcName + ":" + hClient.getRequest(filePath).get(funcName).toString());
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
	
	/** model 2 */
	private void autoCreateButtons2() {
		showRequest = new JTextArea();
		JScrollPane jspRequest = new JScrollPane(showRequest);
		jspRequest.setBounds(1100, 33, 300, 527);
		add(jspRequest);
		
		String data = FileIO.readFile(filePath);
		String[] requests = data.split(MyString.ENTER + MyString.ENTER);
		requests = CharaccterOperate.sortMerge(requests);

		JPanel pMethod = new JPanel();
		JScrollPane jsp = new JScrollPane(pMethod);
		this.add(jsp);
		jsp.setBounds(50, 33, 1045, 527);
		/** setLayout 只有当 rows == 0 时，列的定义才生效 */
		pMethod.setLayout(new GridLayout(0, 4, 5, 5));

		for (String request : requests) {
			final String funcName = request.split(MyString.ENTER)[0];
			String btnName = funcName;
			if (!request.split(MyString.ENTER)[1].contains(MyString.EQUALS)) {
				btnName = request.split(MyString.ENTER)[1];
			}
			JButton button = new JButton(btnName);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					String request = hClient.getRequest(filePath).get(funcName).toString();
					showRequest.setText(Format.json(request));
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
		} else if (command.equals("Change")) {
			MyString.IP_CSS = tpIP.getText();
			MyString.PORT_CSS = tpPort.getText();
		}
	}
}
