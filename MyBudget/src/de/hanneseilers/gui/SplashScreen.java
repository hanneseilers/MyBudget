package de.hanneseilers.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class SplashScreen extends JFrame {

	private JPanel contentPane;
	private JLabel lblLogo;
	private JLabel lblTitle;
	private JLabel lblStatus;

	/**
	 * Create the frame.
	 */
	public SplashScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		lblTitle = new JLabel("MyBudget");
		lblTitle.setForeground(new Color(51, 51, 51));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblTitle, "2, 2, fill, top");
		
		lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(SplashScreen.class.getResource("/de/hanneseilers/gui/icon/MyBudget_64.png")));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLogo, "2, 4, fill, fill");
		
		lblStatus = new JLabel("Loading...");
		lblStatus.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 16));
		contentPane.add(lblStatus, "2, 6, fill, top");
		
		// set position to center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation( dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2 );
		
		setUndecorated(true);
		setVisible(true);
	}
	
	public void setStatus(String msg){
		lblStatus.setText(msg);
	}

}
