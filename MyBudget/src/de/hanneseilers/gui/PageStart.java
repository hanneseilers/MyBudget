package de.hanneseilers.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Start page
 * @author Hannes Eilers
 *
 */
public class PageStart extends Page implements ActionListener {

	/**
	 * Constructor
	 */
	public PageStart() {
		frmMain.btnStartIncome.addActionListener(this);
		frmMain.btnStartOutgo.addActionListener(this);
		frmMain.btnStartOverview.addActionListener(this);
		frmMain.btnStartSettings.addActionListener(this);
	}

	/**
	 * Called if button clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if( source == frmMain.btnStartIncome )
			frmMain.tabbedPane.setSelectedComponent( frmMain.tabIncome );
		else if( source == frmMain.btnStartOutgo )
			frmMain.tabbedPane.setSelectedComponent( frmMain.tabOutgo );
		else if( source == frmMain.btnStartOverview )
			frmMain.tabbedPane.setSelectedComponent( frmMain.tabOverview );
		else if( source == frmMain.btnStartSettings )
			frmMain.tabbedPane.setSelectedComponent( frmMain.tabSettings );	
		
	}
	
}
