package ddthach.homework02;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class DialogResult extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public DialogResult(AppControl appControl, String key, String value) {
		setTitle("Result");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPanel.add(splitPane);
		
		//
		// KEY
		//
		JTextPane keyTextPane = new JTextPane();
		keyTextPane.setEditable(false);
		splitPane.setLeftComponent(keyTextPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		JTextPane valueTextPane = new JTextPane();
		valueTextPane.setEditable(false);
		scrollPane.setViewportView(valueTextPane);
		
		if (key != null && key.compareTo("") != 0) {
			keyTextPane.setText(key);
			
			appControl.addWordToStatistic(key);
		}
		if (value != null && value.compareTo("") != 0) {
			valueTextPane.setText(value);
			valueTextPane.setCaretPosition(0);
		}
		
		//
		// BUTTONS
		//
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			//
			// ADD TO FAVORITE
			//
			{
				JButton addToFavButton = new JButton("Add to favorite");
				addToFavButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						MyNode item = new MyNode(key, value);
						
						if (appControl.addItemToFav(item)) {
							DialogMessage dialog = new DialogMessage("This word has been added");
							dialog.setVisible(true);
						}
						else {
							DialogMessage dialog = new DialogMessage("Adding failed. May be this word has been added yet");
							dialog.setVisible(true);
						}
					}
				});
				addToFavButton.setActionCommand("OK");
				buttonPane.add(addToFavButton);
				getRootPane().setDefaultButton(addToFavButton);
			}
			
			//
			// CANCEL
			//
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
