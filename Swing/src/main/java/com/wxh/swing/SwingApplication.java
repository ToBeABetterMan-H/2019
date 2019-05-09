package com.wxh.swing;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingApplication {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;

	public SwingApplication() {
        prepareGUI();
    }

	public static void main(String[] args) {
		SwingApplication swingControlDemo = new SwingApplication();
		swingControlDemo.showEventDemo();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Java SWING监听处理示例");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350, 100);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}

	private void showEventDemo() {
		headerLabel.setText("Control in action: Button");

		JButton okButton = new JButton("确定");
		JButton submitButton = new JButton("提交");
		JButton cancelButton = new JButton("取消");

		okButton.setActionCommand("OK");
		submitButton.setActionCommand("Submit");
		cancelButton.setActionCommand("Cancel");

		okButton.addActionListener(new ButtonClickListener());
		submitButton.addActionListener(new ButtonClickListener());
		cancelButton.addActionListener(new ButtonClickListener());

		controlPanel.add(okButton);
		controlPanel.add(submitButton);
		controlPanel.add(cancelButton);

		mainFrame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			if (command.equals("OK")) {
				SendHttpImpl hClient = new SendHttpImpl();
				String result = hClient.cssmis(null, 1, "Login").toString();
				statusLabel.setText(result);
			} else if (command.equals("Submit")) {
				statusLabel.setText("点击了'提交'按钮");
			} else {
				statusLabel.setText("点击了'取消'按钮");
			}
		}
	}
}
