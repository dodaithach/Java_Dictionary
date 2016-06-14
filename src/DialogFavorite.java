package ddthach.homework02;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class DialogFavorite extends JDialog {

	/**
	 * Create the dialog.
	 */
	public DialogFavorite(AppControl appControl) {
		setTitle("Favorite words");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 500, 300);
		getContentPane().setLayout(new BorderLayout());
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(6);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);
		
		JLabel lblEngViet = new JLabel("Eng - Viet");
		lblEngViet.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane_1.setLeftComponent(lblEngViet);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane_1.setRightComponent(scrollPane);
		
		JList engViList = new JList();
		scrollPane.setViewportView(engViList);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_2);
		
		JLabel lblVietEng = new JLabel("Viet - Eng");
		lblVietEng.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane_2.setLeftComponent(lblVietEng);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_2.setRightComponent(scrollPane_1);
		
		JList viEngList = new JList();
		scrollPane_1.setViewportView(viEngList);
		splitPane.setDividerLocation(241);
		
		// Init list model
		DefaultListModel<String> engViModel = new DefaultListModel<>();
		DefaultListModel<String> viEngModel = new DefaultListModel<>();
	
		engViList.setModel(engViModel);
		viEngList.setModel(viEngModel);
		
		if (appControl != null) {
			ArrayList<MyNode> engViFavList = appControl.getEngViFavList();
			if (engViFavList != null && engViFavList.size() != 0) {
				for (MyNode node : engViFavList) {
					engViModel.addElement(node.getKey());
				}
			}
			
			ArrayList<MyNode> viEngFavList = appControl.getViEngFavList();
			if (viEngFavList != null && viEngFavList.size() != 0) {
				for (MyNode node : viEngFavList) {
					viEngModel.addElement(node.getKey());
				}
			}
		}
	}

}
