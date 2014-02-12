package de.hanneseilers.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {

	private DBController db = MyBudget.database;
	private int aid = -1;
	
	private String article = "";
	private Date date = new Date();
	private double price = 0.0;
	private Category category = new Category();
	
	/**
	 * Constructor (sets nothing)
	 */
	public Article() {}
	
	/**
	 * Constructor (sets current date)
	 * @param article
	 * @param price
	 */
	public Article(String article, double price){
		setArticle(article);
		setPrice(price);
		setDate( new Date(System.currentTimeMillis()) );
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
	public void setArticle(String article) {
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
		this.price = price;
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
	 */
	private void update(){
		if( db.isDbReady() ){
			db.updateArticle(this);
		}
	}
	
	/**
	 * @return String representation of article
	 */
	public String toString(){
		return (new SimpleDateFormat("dd.MM.yyyy")).format(date) + " - " + article + "\t" + Double.toString(price);
	}
	
}
