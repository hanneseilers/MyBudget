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
		
		String appVersion = (String) Loader.config.getProperty( ConfigurationValues.APP_VERSION.getKey() );
		frmMain.lblSettingsApplicationVersion.setText(appVersion);
		
		updateViewSettings();
		updateCategoriesList();
		frmMain.cmbSettingsCategories.setSelectedIndex(0);
	}
	
	/**
	 * Updates view parameters
	 */
	private void updateViewSettings(){
		int articleNameLength = Loader.config.getInt( ConfigurationValues.ARTICLE_NAME_LENGTH.getKey() );
		int categoryNameLength = Loader.config.getInt( ConfigurationValues.CATEGROY_NAME_LENGTH.getKey() );
		String currencySymbol = Loader.config.getString( ConfigurationValues.ARTICLE_CURRENCY_SYMBOL.getKey() );
		int numbersPreDecimalPlaces = Loader.config.getInt( ConfigurationValues.NUMBERS_PRE_DECIMAL_PLACES.getKey() );
		int numbersPostDecimalPlaces = Loader.config.getInt( ConfigurationValues.NUMBERS_POST_DECIMAL_PLACES.getKey() );
		
		frmMain.txtSettingsViewArticleNameLength.setText( Integer.toString(articleNameLength) );
		frmMain.txtSettingsViewCategoryNameLength.setText( Integer.toString(categoryNameLength) );
		frmMain.txtSettingsViewCurrencySymbol.setText( currencySymbol );
		frmMain.txtSettingsViewPreDecimalPlaces.setText( Integer.toString(numbersPreDecimalPlaces) );
		frmMain.txtSettingsViewPostDecimalPlaces.setText( Integer.toString(numbersPostDecimalPlaces) );
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
			Category category = new Category( frmMain.txtSettingsCatergoyName.getText() );
			category.update();
			
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
				Category category = frmMain.cmbSettingsCategories.getItemAt( index );
				category.setName(name);
				category.update();
				
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
