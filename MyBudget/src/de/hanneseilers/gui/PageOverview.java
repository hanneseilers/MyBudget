package de.hanneseilers.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.hanneseilers.core.Article;
import de.hanneseilers.core.Category;
import de.hanneseilers.core.ConfigurationValues;
import de.hanneseilers.core.Loader;
import de.hanneseilers.core.tasks.ArticleListUpdater;

public class PageOverview extends Page implements ActionListener, ChangeListener {

	private double income = 0;
	private double outgo = 0;
	
	/**
	 * Constructor
	 */
	public PageOverview() {
		frmMain.tabbedPane.addChangeListener(this);
		frmMain.btnOverviewFilter.addActionListener(this);
		
		updateCategoriesList();	
	}
	
	/**
	 * Updates list of categories
	 */
	private synchronized void updateCategoriesList(){
		frmMain.cmbOverviewCategory.removeAllItems();
		
		// add all category
		Category category = new Category();
		category.setCID(-2);
		category.setName("> ALLE <");		
		frmMain.cmbOverviewCategory.addItem( category );
		
		// add categories from database
		for( Category c : db.getCategories() ){
			frmMain.cmbOverviewCategory.addItem(c);
		}
	}
	
	/**
	 * Updates list of articles
	 */
	private synchronized void updateArticlesList(){		
		// reset list and income, outgo counter
		frmMain.lstOverviewModel.clear();
		income = 0;
		outgo = 0;
		
		// get row limit
		List<Article> articles = db.getArticles( getFilter(), -1 );
		
		// calculate income and outgo
		for( Article article : articles ){
		
			// calculate income and outgo
			double price = article.getPrice();
			if( price < 0 ){
				outgo += price;
			}
			else{
				income += price;
			}
			
		}
		
		// update gui list
		List<Component> components = new ArrayList<Component>();
		components.add(frmMain.btnOverviewPrint);
		ArticleListUpdater update = new ArticleListUpdater( "overview", frmMain.lstOverviewModel, articles, components );
		(new Thread( update )).start();
		
		// show income, outgo and balance
		double total = income + outgo;
		String currenyFormat = "%" + (Article.numbersPreDecimalPlaces+Article.numbersPostDecimalPlaces+1)
				+ "."	+ Article.numbersPostDecimalPlaces + "f " + Article.currencySymbol;
		frmMain.lblOverviewTrend.setText( getTrend() );
		frmMain.lblOverviewIncomeTotal.setText( String.format(currenyFormat, income) );
		frmMain.lblOverviewOutgoTotal.setText(String.format(currenyFormat, outgo) );
		frmMain.lblOverviewTotal.setText( String.format(currenyFormat, total) );		
		if( total < 0){
			frmMain.lblOverviewTotal.setForeground( new Color(128, 0, 0) );
		}
		else if( total > 0 ){
			frmMain.lblOverviewTotal.setForeground( new Color(0, 128, 0) );
		}
		else{
			frmMain.lblOverviewTotal.setForeground( new Color(0, 0, 0) );
		}
	}
	
	/**
	 * @return SQL filter statement
	 */
	private String getFilter(){
		return getFilter(true);
	}
	
	/**
	 * @param useDays Set true to use days in filter statement
	 * @return SQL filter statement
	 */
	private String getFilter(boolean useDays){
		String filter = "WHERE";
		boolean filterAppended = false;
		
		// collect filter data
		Category category = null;
		int index = frmMain.cmbOverviewCategory.getSelectedIndex();
		
		if( index >= 0 ){
			category = frmMain.cmbOverviewCategory.getItemAt(index);
		}
		
		long days;
		try{
			days= Long.parseLong( frmMain.txtOverviewDays.getText() ) * Article.timestampDay;
		} catch(NumberFormatException e){
			days = -1;
		}
		String searchWord = frmMain.txtOverviewSearch.getText().trim();
		
		// edit sql filter statement
		if( category != null && category.getCID() >= 0 ){
			filter += " cid=" + category.getCID();
			filterAppended = true;
		}
		
		if( searchWord.length() > 0 ){
			if( filterAppended )
				filter += " AND";
			filter += " LOWER(article) LIKE '%" + searchWord.toLowerCase() + "%'";
			filterAppended = true;
		}
		
		if( useDays ){			
			if(  frmMain.rdbOverviewTimeDays.isSelected() && days > 0 ){
				
				// select time period by last days
				if( filterAppended )
					filter += " AND";
				long curDay = (System.currentTimeMillis()/Article.timestampDay) * Article.timestampDay;
				filter += " timestamp>=" + Long.toString(curDay - days);
				filterAppended = true;
				
			}
			else if( frmMain.rdbOverviewTimePeriod.isSelected()
					&& frmMain.dateChooserOverviewTimePeriodFrom.getDate() != null 
					&& frmMain.dateChooserOverviewTimePeriodTill.getDate() != null ){
				
				// select time period between two dates
				if( filterAppended )
					filter += " AND";
				long periodFrom = frmMain.dateChooserOverviewTimePeriodFrom.getDate().getTime();
				long periodTill = frmMain.dateChooserOverviewTimePeriodTill.getDate().getTime();
				filter += " timestamp>=" + Long.toString( periodFrom );
				filter += " AND timestamp<=" + Long.toString( periodTill );
				filterAppended = true;
				
			}
		}
		
		if( !filterAppended )
			filter = null;
		
		return filter;
	}
	
	/**
	 * @return Trend string of data
	 */
	private String getTrend(){
		String trend = "Kein Zeitraum verfübar!";
		long days;
		try{ 
			days = Long.parseLong( frmMain.txtOverviewDays.getText() ) * Article.timestampDay;
		} catch(NumberFormatException e){
			days = -1;
		}
		
		if( days > 0  ){
			// calculate timezone
			long lastDay1 = (System.currentTimeMillis()/Article.timestampDay) * Article.timestampDay - days;
			long lastDay2 = lastDay1 - days;
			
			// get sql filter statement
			String filter = getFilter(false);
			if( filter == null ){
				filter = " WHERE";
			}
			else{
				filter += " AND";
			}
			filter += " timestamp>=" + Long.toString(lastDay2)
					+ " AND timestamp<" + Long.toString(lastDay1);
			
			// get row limit
			int limit = Loader.config.getInt( ConfigurationValues.ARTICLE_MAX_ROWS.getKey() );
			
			// get articles and reference articles
			List<Article> articles = db.getArticles( getFilter(), limit );
			List<Article> articlesRef = db.getArticles( filter, limit );
			if( articles.size() > 0 ){				
				if( articlesRef.size() > 0 ){
					
					// calculate total prices
					double total = income + outgo;
					double totalRef = 0;
					for( Article aRef : articlesRef ){
						totalRef += aRef.getPrice();
					}
					
					// calculate trend
					double balance = total - totalRef;
					double percent = Math.round( Math.abs( ((balance/totalRef)/100)*100 ) );
					if( total > totalRef ){
						trend = String.format("Bilanzsteigerung um %.2f EUR (+%.2f%%).", balance, percent );
					}
					else if( total < totalRef ){
						trend = String.format("Bilanzminderung um %.2f EUR (-%.2f%%).", balance, percent );
					}
					else{
						trend = "Bilanz unverändert.";
					}
					
				}
				else{
					trend = "Keine Daten im Referenzzeitraum!";
				}
			}
			else{
				trend = "Keine Daten im Zeitraum!";
			}
			
		}
		
		return trend;
	}

	/**
	 * Called if button clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if( source == frmMain.btnOverviewFilter ){
			updateArticlesList();			
		}
		
	}

	/**
	 * Called if tab changed
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Component source = frmMain.tabbedPane.getSelectedComponent();
		if( source == frmMain.tabOverview ){
			updateCategoriesList();
		}
	}
	
}
