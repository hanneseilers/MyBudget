package de.hanneseilers.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.hanneseilers.core.Article;
import de.hanneseilers.core.Category;

public class PageIncome extends Page implements ActionListener, ChangeListener, MouseListener, KeyListener {

	/**
	 * Constructor
	 */
	public PageIncome() {
		frmMain.tabbedPane.addChangeListener(this);
		frmMain.btnIncomeAdd.addActionListener(this);
		frmMain.btnIncomeDelete.addActionListener(this);
		frmMain.btnIncomeEdit.addActionListener(this);
		frmMain.btnIncomeRefresh.addActionListener(this);
		frmMain.btnIncomeFilter.addActionListener(this);
		frmMain.lstIncome.addMouseListener(this);
		frmMain.lstIncome.addKeyListener(this);
		
		updateArticlesList();
		updateCategoriesList();
	}
	
	/**
	 * Updates filters category list
	 */
	private synchronized void updateCategoriesList(){
		frmMain.cmbIncomeFilterCategory.removeAllItems();
		for( Category c : db.getCategories() ){
			frmMain.cmbIncomeFilterCategory.addItem(c);
		}
	}
	
	/**
	 * Updates article list without using filters
	 */
	private synchronized void updateArticlesList(){
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
			articles = db.getArticles( "WHERE price >= 0.0" );
		}
		
		// update gui list
		frmMain.lstIncomeModel.clear();
		for( Article a : articles ){
			frmMain.lstIncomeModel.addElement(a);
		}		
		frmMain.lstIncome.setSelectedIndex(0);
		
	}
	
	/**
	 * @return SQL Filter statement
	 */
	private String getFilter(){
		if( frmMain.rdbtnIncomeFilterCategory.isSelected() ){
			int index = frmMain.cmbIncomeFilterCategory.getSelectedIndex();
			if( index >= 0 ){
				return "WHERE price >= 0.0 AND cid=" + frmMain.cmbIncomeFilterCategory.getItemAt(index).getCID();
			}
		}
		else{
			return "WHERE price >= 0.0 AND LOWER(article) LIKE '%"
					+ frmMain.txtIncomeFilter.getText().toLowerCase() + "%'";
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
			
		}
		else if( source == frmMain.btnIncomeDelete ){
			
			// Delete article
			deleteSelectedArticle();
			
		}
		else if( source == frmMain.btnIncomeEdit ){
			
			// Edit article
			editSelectedArticle();
			
		}
		else if( source == frmMain.btnIncomeFilter ){
			
			// Filter articles
			updateArticlesList(true);
			return;
			
		}
		
		// update article list
		updateArticlesList();
		
	}
	
	/**
	 * Deletes selected article
	 */
	private void deleteSelectedArticle(){
		if( !frmMain.lstIncome.isSelectionEmpty() ){
			frmMain.lstIncome.getSelectedValue().delete();
		}
	}
	
	/**
	 * Edits selected article
	 */
	private void editSelectedArticle(){
		if( !frmMain.lstIncome.isSelectionEmpty() ){
			ArticleDialog dialog = new ArticleDialog( ArticleDialogType.INCOME_EDIT,
					frmMain.lstIncome.getSelectedValue() );
		dialog.showDialog();
		}
	}

	
	/**
	 * Called if tab changed
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		Component source = frmMain.tabbedPane.getSelectedComponent();
		if( source == frmMain.tabIncome ){
			updateCategoriesList();
			updateArticlesList();
		}		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// check for double klick
		if( e.getClickCount() > 1 ){
			editSelectedArticle();
			
			// update articles list
			updateArticlesList();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_DELETE ){
			deleteSelectedArticle();
		}
		
		// update articles list
		updateArticlesList();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
