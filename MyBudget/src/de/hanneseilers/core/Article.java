package de.hanneseilers.core;

public class Article {

	private int aid = -1;
	private int cid = -1;
	private String article = "";
	private long timestamp = 0;
	private double price = 0.0;
	
	/**
	 * Constructor (sets nothing)
	 */
	public Article() {}
	
	/**
	 * Constructor (sets current timestamp)
	 * @param article
	 * @param price
	 */
	public Article(String article, double price){
		this.article = article;
		this.price = price;
		this.timestamp = System.currentTimeMillis();
	}
	
	/**
	 * Constructor
	 * @param article
	 * @param price
	 * @param timestamp
	 */
	public Article(String article, double price, long timestamp){
		this(article, price);
		this.timestamp = timestamp;
	}
	
	/**
	 * Constructor (sets current timestamp)
	 * @param article
	 * @param price
	 * @param cid
	 */
	public Article(String article, double price, int cid){
		this(article, price);
		this.cid = cid;
	}
	
	/**
	 * Constructor
	 * @param article
	 * @param price
	 * @param timestamp
	 * @param cid
	 */
	public Article(String article, double price, long timestamp, int cid){
		this(article, price, timestamp);
		this.cid = cid;
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
	 * @return the cid
	 */
	public int getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(int cid) {
		this.cid = cid;
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
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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
	}
	
}
