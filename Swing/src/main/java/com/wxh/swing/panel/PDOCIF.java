//package com.wxh.swing.panel;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextPane;
//
//import com.wxh.swing.FileIO;
//import com.wxh.swing.MyString;
//import com.wxh.swing.SendHttpImpl;
//import com.wxh.swing.utils.CharaccterOperate;
//import com.wxh.swing.utils.Format;
//import java.awt.GridLayout;
//
//public class PDOCIF extends JPanel {
//	private static final long serialVersionUID = 509368446356931923L;
//	private JTextArea showResult;
//	private JTextPane showRequest;
//
//	private SendHttpImpl hClient = new SendHttpImpl();
//
//	/**
//	 * Create the panel.
//	 */
//	public PDOCIF() {
//		this.setEnabled(true);
//		this.setVisible(true);
//		setLayout(null);
//
//		autoCreateButtons();
//
//		JButton btnAll = new JButton("All");
//		btnAll.setBounds(1150, 780, 100, 23);
//		btnAll.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				String result = hClient.cssmis(null, 2, null).toString();
//				showResult.setText(Format.json(result));
//			}
//		});
//		add(btnAll);
//
//		JButton btnClear = new JButton("Clear");
//		btnClear.setBounds(1020, 780, 100, 23);
//		btnClear.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				showResult.setText(null);
//			}
//		});
//		add(btnClear);
//
//		JLabel lblResult = new JLabel("Result: ");
//		lblResult.setBounds(60, 580, 100, 23);
//		add(lblResult);
//
//		showRequest = new JTextPane();
//		showRequest.setBounds(60, 505, 1320, 60);
//		add(showRequest);
//
//		showResult = new JTextArea();
//		JScrollPane jsp = new JScrollPane(showResult);
//		// 设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
//		jsp.setBounds(170, 580, 1210, 200);
//		// 默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
//		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		add(jsp);
//	}
//
//	private void autoCreateButtons() {
//		String data = FileIO.readFile(MyString.PATH_REQUEST_DOCIF);
//		String[] requests = data.split(MyString.ENTER + MyString.ENTER);
//		requests = CharaccterOperate.sortMerge(requests);
//		
//		JPanel pMethod = new JPanel();		
//		JScrollPane jsp = new JScrollPane(pMethod);
//		jsp.setBounds(50, 5, 1340, 495);
//		pMethod.setLayout(new GridLayout(0, 5, 5, 5));
////		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		add(jsp);
//		
//		for (String request : requests) {
//			final String funcName = request.substring(0, request.indexOf(MyString.ENTER));
//			JButton button = new JButton(funcName);
//
//			button.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseEntered(MouseEvent e) {
//					showRequest.setText(hClient.getRequest(MyString.PATH_REQUEST_DOCIF).get(funcName).toString());
//				}
//
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					String result = hClient.cssmis(null, 2, funcName).toString();
//					showResult.setText(Format.json(result));
//				}
//			});
//
//			pMethod.add(button);
//		}
//	}
//
//}
