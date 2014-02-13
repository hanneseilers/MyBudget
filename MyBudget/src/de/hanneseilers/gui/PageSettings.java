package de.hanneseilers.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.hanneseilers.core.Category;
import de.hanneseilers.core.ConfigurationValues;
import de.hanneseilers.core.Loader;

public class PageSettings extends Page implements ActionListener, ChangeListener {

	/**
	 * Constructor
	 */
	public PageSettings() {
		frmMain.tabbedPane.addChangeListener(this);
		frmMain.btnSettingsCategoryAdd.addActionListener(this);
		frmMain.btnSettingsCategoryRename.addActionListener(this);
		frmMain.btnSettingsRemoveCategory.addActionListener(this);
		
		String appVersion = (String) Loader.config.getProperty( ConfigurationValues.APPLICATION_VERSION.getKey() );
		frmMain.lblSettingsApplicationVersion.setText(appVersion);
		
		updateCategoriesList();
		frmMain.cmbSettingsCategories.setSelectedIndex(0);
	}
	
	/**
	 * Updates list of categories
	 */
	private synchronized void updateCategoriesList(){
		// remove listener and items from combo box
		frmMain.cmbSettingsCategories.removeActionListener(this);
		frmMain.cmbSettingsCategories.removeAllItems();
		
		// add categories from database
		for( Category c : db.getCategories() ){
			frmMain.cmbSettingsCategories.addItem(c);
		}
		
		// add listener again
		frmMain.cmbSettingsCategories.addActionListener(this);
	}

	/**
	 * Called if button clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if( source == frmMain.btnSettingsCategoryAdd ){
			
			// add new category
			new Category( frmMain.txtSettingsCatergoyName.getText() );
			
		}
		else if( source == frmMain.btnSettingsRemoveCategory ){
			
			// Delete category
			int index = frmMain.cmbSettingsCategories.getSelectedIndex();
			if( index >= 0 ){
				frmMain.cmbSettingsCategories.getItemAt( index ).delete();				
			}
			
		}
		else if( source == frmMain.btnSettingsCategoryRename ){
			
			// Rename category
			int index = frmMain.cmbSettingsCategories.getSelectedIndex();
			String name = frmMain.txtSettingsCatergoyName.getText();
			if( index >= 0 ){
				frmMain.cmbSettingsCategories.getItemAt( index ).setName(name);				
			}			
			
		}
		else if( source == frmMain.cmbSettingsCategories ){
			
			// Category selection changed
			int index = frmMain.cmbSettingsCategories.getSelectedIndex();
			if( index >= 0 ){
				String name = frmMain.cmbSettingsCategories.getItemAt( index ).getName();
				frmMain.txtSettingsCatergoyName.setText(name);
				return;
			}			
			
		}
		
		// update categtory list
		updateCategoriesList();
		
	}

	/**
	 * Called if tab changed
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Component source = frmMain.tabbedPane.getSelectedComponent();
		if( source == frmMain.tabSettings ){
			updateCategoriesList();
		}	
	}
	
}
