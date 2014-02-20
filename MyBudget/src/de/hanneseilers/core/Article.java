package de.hanneseilers.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Article {

	private DBController db = MyBudget.database;
	private int aid = -1;
	
	private String article = "";
	private Date date = new Date();
	private double price = 0.0;
	private Category category = new Category(false);
	private boolean synchronizing = true;
	
	public static final int articleNameLength = Loader.config.getInt( ConfigurationValues.ARTICLE_NAME_LENGTH.getKey() );
	public static final int categoryNameLength = Loader.config.getInt( ConfigurationValues.CATEGROY_NAME_LENGTH.getKey() );
	public static final int timestampDay = Loader.config.getInt( ConfigurationValues.ARTICLE_TIMESTAMP_DAYS.getKey() );
	
	/**
	 * Constructor
	 */
	public Article(){
		setDate( new Date(convertTimestamp(System.currentTimeMillis())) );
	}
	
	/**
	 * Constructor
	 * @param nondynObj If false the object is
	 * not automatically updated on database
	 */
	public Article(boolean dynObj) {
		this.synchronizing = dynObj;
	}
	
	/**
	 * Constructor (sets current date)
	 * @param article
	 * @param price
	 */
	public Article(String article, double price){
		this();
		setArticle(article);
		setPrice(price);
	}
	
	/**
	 * Constructor
	 * @param article
	 * @param price
	 * @param date
	 */
	public Article(String article, double price, Date date){
		this(article, price);
		setDate(date);
	}
	
	/**
	 * Constructor (sets current date)
	 * @param article
	 * @param price
	 * @param category
	 */
	public Article(String article, double price, Category category){
		this(article, price);
		setCategory(category);
	}
	
	/**
	 * Constructor
	 * @param article
	 * @param price
	 * @param date
	 * @param category
	 */
	public Article(String article, double price, Date date, Category category){
		this(article, price, date);
		setCategory(category);
	}
	
	/**
	 * Constructor
	 * @param article
	 * @param price
	 * @param date
	 * @param category
	 * @param aid
	 */
	public Article(String article, double price, Date date, Category category, int aid){
		setAid(aid);
		setArticle(article);
		setPrice(price);
		setDate(date);
		setCategory(category);
	}

	/**
	 * @return the aid
	 */
	public int getAid() {
		return aid;
	}
	
	/**
	 * @param aid the aid to set
	 */
	public void setAid(int aid) {
		this.aid = aid;
	}

	/**
	 * @return the article
	 */
	public String getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(String article){
		article = article.trim();
		
		// check for empty article name
		if( article.length() == 0 ){
			article = ">NO NAME<";
		}
		
		// check article names length
		if( article.length() > articleNameLength ){
			article = article.substring(0, articleNameLength);
		}

		this.article = article;
		update();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		// set correct time
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		
		date.setTime( convertTimestamp(date.getTime()) );
		this.date = date;
		update();
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = Math.round( (price)*100 )/100.0;
		update();
	}
	
	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
		update();
	}
	
	
	/**
	 * Updates this article
	 * @return True if successfull
	 */
	public boolean update(){
		if( synchronizing && db.isDbReady() ){
			return db.updateArticle(this);
		}
		return false;
	}
	
	/**
	 * Deletes this article from database
	 * @return True if successfull
	 */
	public boolean delete(){
		return db.deleteArticle(this);
	}
	
	/**
	 * @return String representation of article
	 */
	public String toString(){
		String categoryName = getCategory().getName();
		String ret = String.format( "%s %-" + Integer.toString(articleNameLength)
				+ "s [%-" + Integer.toString(categoryNameLength) 
				+ "s]  %9.2f EUR",
				
				(new SimpleDateFormat("dd.MM.yyyy")).format(date),
				getArticle(),
				(categoryName.length() <= categoryNameLength) ? categoryName : categoryName.substring(0, categoryNameLength-1)+".",
				getPrice() );
		
		return  ret;
	}

	/**
	 * @return the synchronizing
	 */
	public boolean isSynchronizing() {
		return synchronizing;
	}

	/**
	 * @param synchronizing the synchronizing to set
	 */
	public void setSynchronizing(boolean dynObj) {
		this.synchronizing = dynObj;
	}
	
	/**
	 * Converts timestamp to day only
	 * @param timestamp
	 * @return
	 */
	private long convertTimestamp(long timestamp){
		return timestamp - (timestamp%timestampDay);
	}
	
}
