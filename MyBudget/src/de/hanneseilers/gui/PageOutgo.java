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

public class PageOutgo extends Page implements ActionListener, ChangeListener, MouseListener, KeyListener {

	/**
	 * Constructor
	 */
	public PageOutgo() {
		frmMain.tabbedPane.addChangeListener(this);
		frmMain.btnOutgoAdd.addActionListener(this);
		frmMain.btnOutgoDelete.addActionListener(this);
		frmMain.btnOutgoEdit.addActionListener(this);
		frmMain.btnOutgoRefresh.addActionListener(this);
		frmMain.btnOutgoFilter.addActionListener(this);
		frmMain.lstOutgo.addMouseListener(this);
		frmMain.lstOutgo.addKeyListener(this);
		
		updateArticlesList();
		updateCategoriesList();
	}
	
	/**
	 * Updates filters category list
	 */
	private synchronized void updateCategoriesList(){
		frmMain.cmbOutgoFilterCategory.removeAllItems();
		for( Category c : db.getCategories() ){
			frmMain.cmbOutgoFilterCategory.addItem(c);
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
			articles = db.getArticles( "WHERE price < 0.0" );
		}
		
		// update gui list
		frmMain.lstOutgoModel.clear();
		for( Article a : articles ){
			frmMain.lstOutgoModel.addElement(a);
		}
		frmMain.lstOutgo.setSelectedIndex(0);
		
	}
	
	/**
	 * @return SQL Filter statement
	 */
	private String getFilter(){
		if( frmMain.rdbtnOutgoFilterCategory.isSelected() ){
			int index = frmMain.cmbOutgoFilterCategory.getSelectedIndex();
			if( index >= 0 ){
				return "WHERE price < 0.0 AND cid=" + frmMain.cmbOutgoFilterCategory.getItemAt(index).getCID();
			}
		}
		else{
			return "WHERE price < 0.0 AND LOWER(article) LIKE '%"
					+ frmMain.txtOutgoFilter.getText().toLowerCase() + "%'";
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
			
		}
		else if( source == frmMain.btnOutgoDelete ){
			
			// Delete article
			deleteSelectedArticle();
			
		}
		else if( source == frmMain.btnOutgoEdit ){
			
			// Edit article
			editSelectedArticle();
			
		}
		else if( source == frmMain.btnOutgoFilter ){
			
			// Filter articles
			updateArticlesList(true);
			return;
			
		}
		
		// update articles list
		updateArticlesList();
		
	}
	
	/**
	 * Deletes selected article
	 */
	private void deleteSelectedArticle(){
		if( !frmMain.lstOutgo.isSelectionEmpty() ){
			frmMain.lstOutgo.getSelectedValue().delete();
		}
	}
	
	/**
	 * Edits selected article
	 */
	private void editSelectedArticle(){
		if( !frmMain.lstOutgo.isSelectionEmpty() ){
			ArticleDialog dialog = new ArticleDialog( ArticleDialogType.OUTGO_EDIT,
					frmMain.lstOutgo.getSelectedValue() );
			dialog.showDialog();
		}
	}

	/**
	 * Called if tab changed
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Component source = frmMain.tabbedPane.getSelectedComponent();
		if( source == frmMain.tabOutgo ){
			updateCategoriesList();
			updateArticlesList();
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// check for double klick
		if( e.getClickCount() > 1 ){
			editSelectedArticle();
		}
		
		// update articles list
		updateArticlesList();
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