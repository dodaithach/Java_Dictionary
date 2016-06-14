package ddthach.homework02;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;

public class DialogMessage extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public DialogMessage(String message) {
		setTitle("Message");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 500, 75);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblNewLabel.setBounds(10, 16, 474, 14);
			
			if (message != null && message.compareTo("") != 0)
				lblNewLabel.setText(message);
			contentPanel.setLayout(null);
			
			contentPanel.add(lblNewLabel);
		}
	}

}
