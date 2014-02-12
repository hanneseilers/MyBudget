package de.hanneseilers.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Database controller
 * @author Hannes Eilers
 *
 */
public class DBController {
	
	private Connection connection = null;
	private boolean dbReady = false;
	private Logger logger = Logger.getLogger(getClass());

	public DBController() {
		
		try {
		
			// Connect to database
			logger.debug("Connecting to database");
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:mybudget.db");
			initDB();
			
		} catch (Exception e) {
			logger.error("Can not connect to database: " + e.getMessage());
		}
	}
	
	/**
	 * Creates database table
	 * @throws SQLException 
	 */
	private void initDB() throws SQLException{		
		String sql = "CREATE TABLE IF NOT EXISTS categories ("
				+ "cid INTEGER PRIMARY KEY,"
				+ "name TEXT NOT NULL);";
		exeUpdate(sql);
		
		sql = "CREATE TABLE IF NOT EXISTS articles ("
				+ "aid INTEGER PRIMARY KEY,"
				+ "article LONGTEXT NOT NULL,"
				+ "timestamp LONG NOT NULL,"
				+ "price DOUBLE NOT NULL,"
				+ "cid INT NOT NULL,"
				+ "FOREIGN KEY (cid) REFERENCES categories(cid));";
		exeUpdate(sql);
		setDbReady(true);
	}
	
	/**
	 * Executes sql query
	 * @param sql
	 * @return
	 */
	private ResultSet exec(String sql){
		
		ResultSet result = null;
		
		try {
			
			logger.debug("Executing sql statement: " + sql);
			Statement st = connection.createStatement();		
			result = st.executeQuery( sql );
			
		} catch (SQLException e) {
			logger.error("Can not execute sql statement: " + e.getMessage());		
		}
		
		return result;
	}
	
	/**
	 * Executes sql update
	 * @param sql
	 */
	private void exeUpdate(String sql){
		try{
			
			logger.debug("Executing updating sql statement: " + sql);
			Statement st = connection.createStatement();
			st.executeUpdate( sql );
			st.close();
			
		} catch(SQLException e){
			logger.error("Can not execute sql statement: " + e.getMessage());
		}
	}
	
	/**
	 * Closes database connection
	 */
	public void closeDB(){
		if( connection != null ){
			try {
				
				logger.debug("Closing database connection");
				connection.close();
				
			} catch (SQLException e) {
				logger.error("Can not close database connection: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Adds new category
	 * @param category
	 * @return true if successfully
	 */
	public boolean addCategory(Category category){
		try {
			
			// First check if category is already there
			String sql = "SELECT COUNT(*) AS num FROM categories WHERE name='" + category.getName() + "';";
			if( exec(sql).getInt("num") == 0 ){	
				
				// Add category
				sql = "INSERT INTO categories (cid, name) VALUES (?, '" + category.getName() + "');";
				exeUpdate(sql);
				
				// Set new categorys cid
				sql = "SELECT cid FROM categories ORDER BY cid DESC;";
				ResultSet result = exec(sql);
				if( result.getType() == ResultSet.TYPE_FORWARD_ONLY || result.first() ){
					category.setCID( result.getInt("cid") );
					return true;
				}

			}
			else{
				
				// set category vid
				category.setCID( getCategory(category.getName()).getCID() );
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	/**
	 * @param name
	 * @return Category found by name
	 */
	public Category getCategory(String name){
		return getCategory(-1, name);
	}
	
	/**
	 * @param cid
	 * @return Category found by cid
	 */
	public Category getCategory(int cid){
		return getCategory(cid, null);
	}
	
	/**
	 * Gets categor from one of the following parameters.
	 * The other parameter should be null or negative.
	 * @param cid
	 * @param name
	 * @return Category or null if not found
	 */
	private Category getCategory(int cid, String name){
		try{			
			String sql = "";
		
			if( cid != -1 )
				// search by CID
				sql = "SELECT * FROM categories WHERE cid='" + Integer.toString(cid) + "'";
			else if( name != null )
				// search by name
				sql = "SELECT * FROM categories WHERE name='" + name + "'";
			
			if( sql != "" ){
				ResultSet result = exec(sql);
				if( result.getType() == ResultSet.TYPE_FORWARD_ONLY || result.first() ){
					Category category = new Category(false);
					category.setName( result.getString("name") );
					category.setCID( result.getInt("cid") );
					return category;
				}
			}		
		} catch( SQLException e ){
			e.printStackTrace();
		}
		
		// category not found
		return null;
	}
	
	/**
	 * @return List of categories
	 */
	public List<Category> getCategories(){
		List<Category> categories = new ArrayList<Category>();
		
		try {
		
			String sql = "SELECT * FROM categories";
			ResultSet result = exec(sql);	
			while( result.next() ){
				Category category = new Category(false);
				category.setName( result.getString("name") ); 
				category.setCID( result.getInt("cid") );
				category.setDynObj(true);
				categories.add( category );
			}
			
		} catch( SQLException e ){
			e.printStackTrace();
		}
		
		return categories;
	}
	

	/**
	 * Updates category
	 * @param category
	 */
	public void updateCategory(Category category){
		if( !addCategory(category) ){
			String sql = "UPDATE categories SET name='"
					+ category.getName() + "' WHERE cid="
					+ Integer.toString(category.getCID()) + ";";
			exeUpdate(sql);	
		}
	}
	

	/**
	 * Recieves list of articles
	 * @param condition	SQL Condition to add to request
	 * @return
	 */
	public List<Article> getArticles(String condition){		
		List<Article> articles = new ArrayList<Article>();
		
		try{
			
			String sql = "SELECT * FROM articles " + condition + ";";
			
			ResultSet result = exec(sql);
			while( result.next() ){
				Article article = new Article(false);
				article.setArticle( result.getString("article") );
				article.setPrice( result.getDouble("price") );
				article.setDate( new Date(result.getLong("timestamp")) );
				article.setCategory( getCategory(result.getInt("cid")) );
				article.setDynObj(true);
				articles.add( article );
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return articles;		
	}
	
	public boolean addArticle(Article article){
		try {
			
			// First check if article is already there
			String sql = "SELECT COUNT(*) AS num FROM articles WHERE aid='" + Integer.toString(article.getAid()) + "';";
			if( article.getAid() < 0 || exec(sql).getInt("num") == 0 ){	
				
				// Add areticle
				sql = "INSERT INTO articles (aid, article, price, timestamp, cid) VALUES (?,"
				+ "'" + article.getArticle() + "',"
				+ Double.toString(article.getPrice()) + ","
				+ Long.toString(article.getDate().getTime()) + ","
				+ Integer.toString(article.getCategory().getCID())
				+ ");";
				exeUpdate(sql);
				
				// Set new articles aid
				sql = "SELECT aid FROM articles ORDER BY aid DESC;";
				ResultSet result = exec(sql);
				if( result.getType() == ResultSet.TYPE_FORWARD_ONLY || result.first() ){
					article.setAid( result.getInt("aid") );
					return true;
				}
						
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	/**
	 * Updates article and adds it if not already in database
	 * @param article
	 */
	public void updateArticle(Article article){
		if( !addArticle(article) ){
			String sql = "UPDATE articles SET name='"
					+ article.getArticle() + "',"
					+ "price='" + Double.toString(article.getPrice() )+ "'"
					+ "timestamp='" + Long.toString(article.getDate().getTime()) + "'"
					+ "cid='" + Integer.toString(article.getCategory().getCID()) + "'"
					+ " WHERE aid="	+ Integer.toString(article.getAid()) + ";";
			exeUpdate(sql);	
		}
	}

	/**
	 * @return the dbReady
	 */
	public synchronized boolean isDbReady() {
		return dbReady;
	}

	/**
	 * @param dbReady the dbReady to set
	 */
	public synchronized void setDbReady(boolean dbReady) {
		this.dbReady = dbReady;
	}
	
}
