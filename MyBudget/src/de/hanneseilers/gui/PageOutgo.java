package de.hanneseilers.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import de.hanneseilers.core.Article;
import de.hanneseilers.core.Category;

public class PageOutgo extends Page implements ActionListener {

	/**
	 * Constructor
	 */
	public PageOutgo() {
		frmMain.btnOutgoAdd.addActionListener(this);
		frmMain.btnOutgoDelete.addActionListener(this);
		frmMain.btnOutgoEdit.addActionListener(this);
		frmMain.btnOutgoFilter.addActionListener(this);
		
		updateArticlesList();
		updateCategoryList();
	}
	
	/**
	 * Updates filters category list
	 */
	private void updateCategoryList(){
		frmMain.cmbOutgoFilterCategory.removeAllItems();
		for( Category c : db.getCategories() ){
			frmMain.cmbOutgoFilterCategory.addItem(c);
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
			articles = db.getArticles( "WHERE price < 0.0 ORDER BY timestamp DESC" );
		}
		
		// update gui list
		frmMain.lstOutgoModel.clear();
		for( Article a : articles ){
			frmMain.lstOutgoModel.addElement(a);
		}		
		
	}
	
	/**
	 * @return SQL Filter statement
	 */
	private String getFilter(){
		if( frmMain.rdbtnOutgoFilterCategory.isSelected() ){
			int index = frmMain.cmbOutgoFilterCategory.getSelectedIndex();
			if( index >= 0 ){
				return "WHERE price < 0.0 AND cid=" + frmMain.cmbOutgoFilterCategory.getItemAt(index).getCID()
						+ " ORDER BY timestamp DESC";
			}
		}
		else{
			return "WHERE price < 0.0 AND article LIKE '%" + frmMain.txtOutgoFilter.getText() + "%'"
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
		if( source == frmMain.btnOutgoAdd ){
			
			// Add new article
			ArticleDialog dialog = new ArticleDialog( ArticleDialogType.OUTGO_ADD, null );			
			dialog.showDialog();
			updateArticlesList();
			
		}
		else if( source == frmMain.btnOutgoDelete ){
			
			// Delete article
			db.deleteArticle( frmMain.lstOutgo.getSelectedValue() );
			updateArticlesList();
			
		}
		else if( source == frmMain.btnOutgoEdit ){
			
			// Edit article
			ArticleDialog dialog = new ArticleDialog( ArticleDialogType.OUTGO_EDIT,
					frmMain.lstOutgo.getSelectedValue() );
			dialog.showDialog();
			updateArticlesList();
			
		}
		else if( source == frmMain.btnOutgoFilter ){
			
			// Filter articles
			updateArticlesList(true);
			
		}
		
	}

}