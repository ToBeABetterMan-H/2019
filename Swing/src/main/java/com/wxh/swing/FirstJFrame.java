package com.wxh.swing;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.wxh.swing.panel.PExcel;
import com.wxh.swing.panel.PInterface;

@SuppressWarnings("serial")
public class FirstJFrame extends JFrame {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger();

	private PInterface pbuss;
	private PInterface pdocif;
	private PExcel pexcel;
	
	private PInterface pworkflow;
	private CardLayout cardLayout;
	private JPanel mainPanel;

	{
		pbuss = new PInterface(MyString.PATH_REQUEST_BUSS, 1);
		pdocif = new PInterface(MyString.PATH_REQUEST_DOCIF, 2);
		pworkflow = new PInterface(MyString.PATH_REQUEST_WORKFLOW, 3);
		pexcel = new PExcel();

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		mainPanel.add(pexcel, "Excel");
		mainPanel.add(pbuss, "buss");
		mainPanel.add(pdocif, "docif");
		mainPanel.add(pworkflow, "workflow");
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		
//	}

	/**
	 * Create the frame.
	 */
	public FirstJFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); // maximize window
		setBounds(450, 200, 400, 300);
		this.setContentPane(mainPanel);
		this.setTitle("CSSMIS测试");
		addMenu();
	}

	/** add Menus */
	public void addMenu() {
		// 1.create a menu bar
		final JMenuBar menuBar = new JMenuBar();

		// 2.create menus
		JMenu fileMenu = new JMenu("File");
		JMenu interfaceMenu = new JMenu("Interface");

		// 3.create menu items
		JMenuItem newMenuItem = new JMenuItem("new");
		newMenuItem.setMnemonic(KeyEvent.VK_N); // shortcut key
//		newMenuItem.setActionCommand("New");

		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setActionCommand("Open");

		JMenuItem bussMenuItem = new JMenuItem("buss");
		bussMenuItem.setActionCommand("buss");
		JMenuItem docifMenuItem = new JMenuItem("docif");
		docifMenuItem.setActionCommand("docif");
		JMenuItem workflowMenuItem = new JMenuItem("workflow");
		workflowMenuItem.setActionCommand("workflow");
		JMenuItem excelMenuItem = new JMenuItem("Excel");
		excelMenuItem.setActionCommand("Excel");

		MenuItemListener menuItemListener = new MenuItemListener();

//		newMenuItem.addActionListener(menuItemListener);
		openMenuItem.addActionListener(menuItemListener);
		bussMenuItem.addActionListener(menuItemListener);
		docifMenuItem.addActionListener(menuItemListener);
		workflowMenuItem.addActionListener(menuItemListener);
		excelMenuItem.addActionListener(menuItemListener);

		// 4.add menu items to menus
//		fileMenu.add(newMenuItem);
//		fileMenu.addSeparator();
		fileMenu.add(openMenuItem);

		interfaceMenu.add(bussMenuItem);
		interfaceMenu.addSeparator();
		interfaceMenu.add(docifMenuItem);
		interfaceMenu.addSeparator();
		interfaceMenu.add(workflowMenuItem);
		interfaceMenu.addSeparator();
		interfaceMenu.add(excelMenuItem);

		// 5.add menu to menuBar
		menuBar.add(fileMenu);
		menuBar.add(interfaceMenu);

		// 6.add menuBar to the frame
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}

	// Listener
	class MenuItemListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			if (command.equals("Open")) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if(file == null) {
					return;
				}
				if (file.isDirectory()) {
					System.out.println("文件夹:" + file.getAbsolutePath());
				} else if (file.isFile()) {
					System.out.println("文件:" + file.getAbsolutePath());
				}
				System.out.println(jfc.getSelectedFile().getName());
			} else
				cardLayout.show(mainPanel, e.getActionCommand());
		}
	}

}
