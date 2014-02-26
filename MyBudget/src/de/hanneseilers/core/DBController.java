package de.hanneseilers.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private HashMap<String, List<Article>> articlesMap = new HashMap<String, List<Article>>();
	private List<Category> categoriesList = new ArrayList<Category>();
	private HashMap<Integer, Category> categoriesListCID = new HashMap<Integer, Category>();
	private HashMap<String, Category> categoriesListName = new HashMap<String, Category>();

	public DBController() {
		
		try {
		
			// Connect to database
			logger.debug("Connecting to database");
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:mybudget.db");
			initDB();
			
		} catch (Exception e) {
			logger.fatal("Can not connect to database: " + e.getMessage());
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
	 * @return true if successfull
	 */
	private boolean exeUpdate(String sql){
		try{
			
			logger.debug("Executing updating sql statement: " + sql);
			Statement st = connection.createStatement();
			int ret = st.executeUpdate( sql );
			st.close();
			
			if( ret != 0 ){
				return true;
			}
			
		} catch(SQLException e){
			logger.error("Can not execute sql statement: " + e.getMessage());
		}
		return false;
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
	 * Clears all buffer lists
	 */
	private void clearAllBuffers(){
		clearArticleBuffer();
		clearCategoryBuffer();
	}
	
	/**
	 * Clears category buffer lists
	 */
	private void clearCategoryBuffer(){
		categoriesList.clear();
		categoriesListCID.clear();
		categoriesListName.clear();
	}
	
	/**
	 * Clears article buffer lists
	 */
	private void clearArticleBuffer(){
		articlesMap.clear();
	}
	
	/**
	 * Adds new category
	 * @param category
	 * @return true if successfully
	 */
	public boolean addCategory(Category category){
		try {
			
			// First check if category is already there (name of cid)
			String sql = "SELECT COUNT(*) AS num FROM categories WHERE name='" + category.getName() +
					"' OR cid=" + category.getCID() + ";";
			if( exec(sql).getInt("num") == 0 ){	
				
				// Add category
				sql = "INSERT INTO categories (cid, name) VALUES (?, '" + category.getName() + "');";
				if( !exeUpdate(sql) ){
					throw new SQLException("Unable to insert data into categories table.");
				}
				
				// Set new categorys cid
				sql = "SELECT cid FROM categories ORDER BY cid DESC;";
				ResultSet result = exec(sql);
				if( result.getType() == ResultSet.TYPE_FORWARD_ONLY || result.first() ){
					category.setCID( result.getInt("cid") );
					clearCategoryBuffer();
					return true;
				}

			}
			
		} catch (SQLException e) {
			logger.error("Can not add category " + category.getName() + ":" + e.getMessage());
		}		
		return false;
	}
	
	/**
	 * Deletes category from database
	 * @param category
	 * @return True if successfull
	 */
	public boolean deleteCategory(Category category){		
		String sql = "DELETE FROM categories WHERE cid=" + Integer.toString(category.getCID())
				+ " AND name='" + category.getName() + "';";
		clearCategoryBuffer();
		return exeUpdate(sql);		
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
		
			if( cid != -1 ){
				// search by CID
				if( categoriesListCID.containsKey(cid) ){
					return categoriesListCID.get(cid);
				}
				sql = "SELECT * FROM categories WHERE cid='" + Integer.toString(cid) + "'";
			}
			else if( name != null ){
				// search by name
				if( categoriesListName.containsKey(name) ){
					return categoriesListName.get(name);
				}
				sql = "SELECT * FROM categories WHERE name='" + name + "'";
			}
			
			if( sql != "" ){
				ResultSet result = exec(sql);
				if( result.getType() == ResultSet.TYPE_FORWARD_ONLY || result.first() ){
					Category category = new Category();
					category.setName( result.getString("name") );
					category.setCID( result.getInt("cid") );
					
					// add category to buffer lists
					categoriesListCID.put(category.getCID(), category);
					categoriesListName.put(category.getName(), category);
					
					return category;
				}
			}		
		} catch( SQLException e ){
			logger.error("Can not get category information: " + e.getMessage());
		}
		
		// category not found
		return null;
	}
	
	/**
	 * @return True if there are no categories in database
	 */
	public boolean isCategoriesEmpty(){		
		try {
			
			String sql = "SELECT COUNT(*) AS num FROM categories";
			if( (exec(sql).getInt("num") == 0) ){
				categoriesList.clear();
			}
			
		} catch (SQLException e) {
			logger.warn("Can execute sql statement: " + e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * @return List of categories
	 */
	public List<Category> getCategories(){		
		if( categoriesList.size() == 0 ){
		
			try {
			
				String sql = "SELECT * FROM categories ORDER BY name ASC";
				ResultSet result = exec(sql);	
				while( result.next() ){
					Category category = new Category();
					category.setName( result.getString("name") ); 
					category.setCID( result.getInt("cid") );
					categoriesList.add( category );
				}
				
			} catch( SQLException e ){
				logger.error("Can not get list of categories: " + e.getMessage());
			}
			
		}
		
		return categoriesList;
	}
	

	/**
	 * Updates category
	 * @param category
	 * @return True if successfull
	 */
	public boolean updateCategory(Category category){
		if( !addCategory(category) ){
			String sql = "UPDATE categories SET name='"
					+ category.getName() + "' WHERE cid="
					+ Integer.toString(category.getCID()) + ";";
			 clearAllBuffers();
			return exeUpdate(sql);	
		}
		return false;
	}
	

	/**
	 * Recieves list of articles
	 * @param condition	SQL Condition to add to request
	 * @return
	 */
	public List<Article> getArticles(String condition){
		if( condition == null ){
			condition = "";
		}
		
		if( !articlesMap.containsKey(condition) ){
			
			List<Article> articlesList = new ArrayList<Article>();
			
			try{
				
				String sql = "SELECT * FROM articles " + condition + " ORDER BY timestamp DESC, aid DESC;";
				
				// ad articles form database to articlellist
				ResultSet result = exec(sql);
				while( result.next() ){
					Article article = new Article();
					article.setAid( result.getInt("aid") );
					article.setArticle( result.getString("article") );
					article.setPrice( result.getDouble("price") );
					article.setDate( new Date(result.getLong("timestamp")) );
					article.setCategory( getCategory(result.getInt("cid")) );
					articlesList.add( article );
				}
				
				// add article list to hash map
				articlesMap.put(condition, articlesList);
				
			} catch(SQLException e){
				logger.error("Can not get list of articles: " + e.getMessage());
			}
			
		}
		
		return articlesMap.get(condition);		
	}
	
	/**
	 * Adds article to database
	 * @param article
	 * @return True if successfull, false if article already in database or error occured while adding.
	 */
	public boolean addArticle(Article article){
		try {
			
			// First check if article is already there
			String sql = "SELECT COUNT(*) AS num FROM articles WHERE aid='" + article.getAid() + "';";
			if( article.getAid() < 0 || exec(sql).getInt("num") == 0 ){	
				
				// Add areticle
				sql = "INSERT INTO articles (aid, article, price, timestamp, cid) VALUES (?,"
				+ "'" + article.getArticle() + "',"
				+ Double.toString(article.getPrice()) + ","
				+ Long.toString(article.getDate().getTime()) + ","
				+ Integer.toString(article.getCategory().getCID())
				+ ");";
				if( !exeUpdate(sql) ){
					throw new SQLException("Unable to inster data into articles table.");
				}
				
				// Set new articles aid
				sql = "SELECT aid FROM articles ORDER BY aid DESC;";
				ResultSet result = exec(sql);
				if( result.getType() == ResultSet.TYPE_FORWARD_ONLY || result.first() ){
					article.setAid( result.getInt("aid") );
					articlesMap.clear();
					return true;
				}
						
			}
			
		} catch (SQLException e) {
			logger.error("Can not add article " + article.getArticle() + ": " + e.getMessage());
		}		
		return false;
	}
	
	/**
	 * Deletes a article from database
	 * @param article
	 * @return true if successfull
	 */
	public boolean deleteArticle(Article article){		
		// Send delete sql statement to database
		String sql = "DELETE FROM articles WHERE aid=" + Integer.toString(article.getAid())
				+ " AND article='" + article.getArticle() + "';";
		if( exeUpdate(sql) ){
			logger.debug("Deleted " + article.getArticle() + " from database.");
			articlesMap.clear();
			return true;
		}
		
		logger.debug("Could not delete article " + article.getArticle() + " from database.");
		return false;
	}
	
	/**
	 * Updates article and adds it if not already in database
	 * @param article
	 * @return True if successfull
	 */
	public boolean updateArticle(Article article){
		if( !addArticle(article) ){
			String sql = "UPDATE articles SET article='"
					+ article.getArticle() + "',"
					+ "price=" + Double.toString(article.getPrice() )+ ","
					+ "timestamp=" + Long.toString(article.getDate().getTime()) + ","
					+ "cid=" + Integer.toString(article.getCategory().getCID()) + ""
					+ " WHERE aid="	+ Integer.toString(article.getAid()) + ";";
			articlesMap.clear();
			return exeUpdate(sql);	
		}
		
		return false;
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
