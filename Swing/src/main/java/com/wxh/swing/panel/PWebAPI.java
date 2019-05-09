//package com.wxh.swing.panel;
//
//import javax.swing.JPanel;
//
//import com.wxh.swing.FirstJFrame;
//
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//
//public class PWebAPI extends JPanel implements ActionListener{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	private JButton btnBuss;
//	private JButton btnWorkflow;
//	private JButton btnDocif;
//	private FirstJFrame mainFrame;
//
//	/**
//	 * Create the panel.
//	 */
//	public PWebAPI(FirstJFrame frame) {
//		setLayout(new GridLayout(1, 0, 0, 0));
//		
//		mainFrame = frame;
//		
//		btnBuss = new JButton("BUSS");
//		btnBuss.addActionListener(this);
//		btnBuss.setActionCommand(btnBuss.getText());
//		add(btnBuss);
//		
//		btnWorkflow = new JButton("WORKFLOW");
//		btnWorkflow.addActionListener(this);
//		btnWorkflow.setActionCommand(btnWorkflow.getText());
//		add(btnWorkflow);
//		
//		btnDocif = new JButton("DOCIF");
//		btnDocif.addActionListener(this);
//		btnDocif.setActionCommand(btnDocif.getText());
//		add(btnDocif);
//
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		if(e.getActionCommand().equals(btnBuss.getActionCommand())) {
//			PBUSS pbuss = new PBUSS(mainFrame);
//			this.setEnabled(false);
//			this.setVisible(false);
//			pbuss.setEnabled(true);
//			pbuss.setVisible(true);
//			mainFrame.setContentPane(pbuss);
//			mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		}else {
//			System.out.println("Coming soon ~");
//		}
//	}
//
//}
