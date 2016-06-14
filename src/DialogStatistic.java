package ddthach.homework02;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class DialogStatistic extends JDialog {

	/**
	 * Create the dialog.
	 */
	public DialogStatistic(AppControl appControl) {
		setTitle("Statistic");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 600, 400);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBorder(null);
		splitPane.setLeftComponent(splitPane_1);
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane_1.setLeftComponent(lblWeek);
		
		JLabel lblMonth = new JLabel("Month");
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane_1.setRightComponent(lblMonth);
		splitPane_1.setDividerLocation(295);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setBorder(null);
		splitPane.setRightComponent(splitPane_2);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane_2.setLeftComponent(scrollPane);
		
		JTextPane weekTextPane = new JTextPane();
		weekTextPane.setEditable(false);
		scrollPane.setViewportView(weekTextPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_2.setRightComponent(scrollPane_1);
		
		JTextPane monthTextPane = new JTextPane();
		monthTextPane.setEditable(false);
		scrollPane_1.setViewportView(monthTextPane);
		splitPane_2.setDividerLocation(295);
		
		// Init data
		MyStatistic statistic = appControl.getStatistic();
		if (statistic != null) {
			String data = "";
			
			// Get week data
			ArrayList<String> words = statistic.getWordsInWeek();
			ArrayList<Integer> times = statistic.getTimesInWeek();
			if (words != null && words.size() != 0 && times != null && times.size() != 0) {
				for (int i = 0; i < words.size(); i++) {
					data = data + words.get(i) + "\t" + times.get(i).toString() + " times" + "\n";
				}
				
				weekTextPane.setText(data);
			}			
			
			data = "";
			
			// Get month data
			words = statistic.getWordsInMonth();
			times = statistic.getTimesInMonth();
			if (words != null && words.size() != 0 && times != null && times.size() != 0) {
				for (int i = 0; i < words.size(); i++) {
					data = data + words.get(i) + "\t" + times.get(i).toString() + " times" + "\n";
				}
				
				monthTextPane.setText(data);
			}
		}
	}
}
