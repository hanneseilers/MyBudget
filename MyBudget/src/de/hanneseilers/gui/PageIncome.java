package de.hanneseilers.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import de.hanneseilers.core.Article;
import de.hanneseilers.core.Category;

public class PageIncome extends Page implements ActionListener {

	/**
	 * Constructor
	 */
	public PageIncome() {
		frmMain.btnIncomeAdd.addActionListener(this);
		frmMain.btnIncomeDelete.addActionListener(this);
		frmMain.btnIncomeEdit.addActionListener(this);
		frmMain.btnIncomeFilter.addActionListener(this);
		
		updateArticlesList();
		updateCategoryList();
	}
	
	/**
	 * Updates filters category list
	 */
	private void updateCategoryList(){
		frmMain.cmbIncomeFilterCategory.removeAllItems();
		for( Category c : db.getCategories() ){
			frmMain.cmbIncomeFilterCategory.addItem(c);
		}
	}
	
	/**
	 * Updates article list without using filters
	 */
	private void updateArticlesList(){
		updateArticlesList(false);
	}
	
	/**
	 * Updates article list
	 * @param useFilter Set true to use filter
	 */
	private void updateArticlesList(boolean useFilter){
		
		// get list of articles
		List<Article> articles;
		if( useFilter ){
			articles = db.getArticles( getFilter() );
		}
		else{
			articles = db.getArticles( "WHERE price >= 0.0 ORDER BY timestamp DESC" );
		}
		
		// update gui list
		frmMain.lstIncomeModel.clear();
		for( Article a : articles ){
			frmMain.lstIncomeModel.addElement(a);
		}		
		
	}
	
	/**
	 * @return SQL Filter statement
	 */
	private String getFilter(){
		if( frmMain.rdbtnIncomeFilterCategory.isSelected() ){
			int index = frmMain.cmbIncomeFilterCategory.getSelectedIndex();
			if( index >= 0 ){
				return "WHERE price >= 0.0 AND cid=" + frmMain.cmbIncomeFilterCategory.getItemAt(index).getCID()
						+ " ORDER BY timestamp DESC";
			}
		}
		else{
			return "WHERE price >= 0.0 AND article LIKE '%" + frmMain.txtIncomeFilter.getText() + "%'"
					+ " ORDER BY timestamp DESC";
		}
		
		return "";
	}
	
	/**
	 * Called if button clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if( source == frmMain.btnIncomeAdd ){
			
			// Add new article
			ArticleDialog dialog = new ArticleDialog( ArticleDialogType.INCOME_ADD, null );			
			dialog.showDialog();
			updateArticlesList();
			
		}
		else if( source == frmMain.btnIncomeDelete ){
			
			// Delete article
			db.deleteArticle( frmMain.lstIncome.getSelectedValue() );
			updateArticlesList();
			
		}
		else if( source == frmMain.btnIncomeEdit ){
			
			// Edit article
			ArticleDialog dialog = new ArticleDialog( ArticleDialogType.INCOME_EDIT,
					frmMain.lstIncome.getSelectedValue() );
			dialog.showDialog();
			updateArticlesList();
			
		}
		else if( source == frmMain.btnIncomeFilter ){
			
			// Filter articles
			updateArticlesList(true);
			
		}
		
	}

}
