package ddthach.homework02;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm {	
	//
	// My variables
	//
	private AppControl m_appControl;

	//
	// List model
	//
	private DefaultListModel<String> m_listModel;
	
	//
	// UI variables
	//
	private JFrame frmHomework;
	private JMenuBar menuBar;
	private JSplitPane splitPane;
	private JSplitPane splitPane_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JList list;
	private JTextPane textPane;
	private JMenu mnEngViet;
	private JMenu mnVietEng;
	private JSplitPane splitPane_2;
	private JTextField textField;
	private JButton btnSearch;
	private JMenu mnAddToFavorite;
	private JMenu mnFavoriteList;
	private JMenu mnStatistic;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmHomework.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		// 1. Set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception ex) {
			System.err.print(ex.getMessage());
		}
		
		// 2. Init AppControl
		m_appControl = new AppControl();
		
		// 3. Init UI
		this.initialize();
		
		// 4. Init list model
		m_listModel = new DefaultListModel<>();
		list.setModel(m_listModel);
		
		// 5. Update list
		this.updateList();
	}
	
	private void updateList() {
		if (m_listModel == null)
			m_listModel = new DefaultListModel<>();
		else
			m_listModel.clear();
		
		if (m_appControl == null)
			return;
		
		ArrayList<MyNode> listNode = m_appControl.getDict();
		
		if (listNode == null || listNode.size() == 0) {
			DialogMessage dialog = new DialogMessage("Fail to load dictionary");
			dialog.setVisible(true);
			return;
		}
		
		for (MyNode node : listNode) {
			m_listModel.addElement(node.getKey());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHomework = new JFrame();
		frmHomework.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				m_appControl.save();
			}
		});
		frmHomework.setTitle("Homework02 - Dictionary");
		frmHomework.setResizable(false);
		frmHomework.setBounds(100, 100, 600, 400);
		frmHomework.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHomework.getContentPane().setLayout(null);
		
		splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setBounds(0, 0, 594, 350);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmHomework.getContentPane().add(splitPane);
		
		splitPane_1 = new JSplitPane();
		splitPane_1.setBorder(null);
		splitPane.setRightComponent(splitPane_1);
		
		scrollPane = new JScrollPane();
		splitPane_1.setLeftComponent(scrollPane);
		
		list = new JList();
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int idx = list.getSelectedIndex();
				
				m_appControl.setCurrentItem(idx);
				
				String meaning = m_appControl.getMeaning(idx);
				
				textPane.setText(meaning);
				textPane.setCaretPosition(0);
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		splitPane_1.setRightComponent(scrollPane_1);
		
		textPane = new JTextPane();
		textPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPane.setEditable(false);
		scrollPane_1.setViewportView(textPane);
		splitPane_1.setDividerLocation(250);
		splitPane.setDividerLocation(25);
		
		menuBar = new JMenuBar();
		frmHomework.setJMenuBar(menuBar);
		
		mnEngViet = new JMenu("Eng - Viet");
		mnEngViet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				m_appControl.setMode(AppControl.c_eng_vi_mode);
				
				updateList();
			}
		});
		menuBar.add(mnEngViet);
		
		mnVietEng = new JMenu("Viet - Eng");
		mnVietEng.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				m_appControl.setMode(AppControl.c_vi_eng_mode);
				
				updateList();
			}
		});
		menuBar.add(mnVietEng);
		
		mnAddToFavorite = new JMenu("Add to favorite");
		mnAddToFavorite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (m_appControl.addCurrentItemToFav()) {
					DialogMessage dialog = new DialogMessage("This word has been added");
					dialog.setVisible(true);
				}
				else {
					DialogMessage dialog = new DialogMessage("Adding failed. May be this word has been added yet");
					dialog.setVisible(true);
				}
			}
		});
		menuBar.add(mnAddToFavorite);
		
		mnFavoriteList = new JMenu("Favorite list");
		mnFavoriteList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogFavorite dialog = new DialogFavorite(m_appControl);
				dialog.setVisible(true);
			}
		});
		menuBar.add(mnFavoriteList);
		
		mnStatistic = new JMenu("Statistic");
		mnStatistic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogStatistic dialog = new DialogStatistic(m_appControl);
				dialog.setVisible(true);
			}
		});
		menuBar.add(mnStatistic);
		
		splitPane_2 = new JSplitPane();
		splitPane_2.setBorder(null);
		splitPane.setLeftComponent(splitPane_2);
		
		textField = new JTextField();
		textField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		splitPane_2.setLeftComponent(textField);
		textField.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String key = textField.getText();
				if (key == null || key.compareTo("") == 0) {
					DialogMessage dialog = new DialogMessage("Please input word");
					dialog.setVisible(true);
					
					return;
				}
				
				String value = m_appControl.getMeaning(key);
				
				if (value == null) {
					DialogMessage dialog = new DialogMessage("There is no item matching this word or dictionary has not been loaded");
					dialog.setVisible(true);
					
					return;
				}
				
				DialogResult dialog = new DialogResult(m_appControl, key, value);
				dialog.setVisible(true);
			}
		});
		btnSearch.setBorder(null);
		splitPane_2.setRightComponent(btnSearch);
		splitPane_2.setDividerLocation(500);
	}

}
