package de.hanneseilers.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Database controller
 * @author Hannes Eilers
 *
 */
public class DBController {
	
	private Connection connection = null;
	private boolean dbReady = false;

	public DBController() {
		
		try {
		
			// Connect to database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:mybudget.db");
			initDB();
			
		} catch (Exception e) {
			e.printStackTrace();
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
			
			Statement st = connection.createStatement();		
			result = st.executeQuery( sql );
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Executes sql update
	 * @param sql
	 */
	private void exeUpdate(String sql){
		try{
			
			Statement st = connection.createStatement();
			st.executeUpdate( sql );
			st.close();
			
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes database connection
	 */
	public void closeDB(){
		if( connection != null ){
			try {
				
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds new category
	 * @param category
	 * @return true if successfully
	 */
	public boolean addCategory(String category){
		try {
			
			// First check if category is already there
			String sql = "SELECT COUNT(*) AS num FROM categories WHERE name='" + category + "';";
			if( exec(sql).getInt("num") == 0 ){	
				
				// Add category
				sql = "INSERT INTO categories (cid, name) VALUES (?, '" + category + "');";
				exeUpdate(sql);
				return true;
						
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	/**
	 * @return List of categories
	 */
	public HashMap<String, Integer> getCategories(){
		HashMap<String, Integer> categories = new HashMap<String, Integer>();
		
		try {
		
			String sql = "SELECT * FROM categories";
			ResultSet result = exec(sql);	
			while( result.next() )
				categories.put( result.getString("name"),  result.getInt("cid") );
			
		} catch( SQLException e ){
			e.printStackTrace();
		}
		
		return categories;
	}
	
	/**
	 * Updates category
	 * @param cid
	 * @param category
	 */
	public void updateCategory(int cid, String category){
		String sql = "UPDATE categories SET name='"
				+ category + "' WHERE cid="
				+ Integer.toString(cid) + ";";
		exeUpdate(sql);		
	}
	

	/**
	 * Recieves list of articles
	 * @param contion	SQL Condition to add to request
	 * @return
	 */
	public List<Article> getArticles(String contion){		
		List<Article> articles = new ArrayList<Article>();
		
		try{
			
			String sql = "SELECT * FROM articles ";
			
			// select conditions
			if( contion.length() > 0 )
				sql += contion;
			sql += ";";
			
			ResultSet result = exec(sql);
			while( result.next() ){
				Article a = new Article(result.getString("article"),
						result.getDouble("price"),
						result.getLong("timestamp"),
						result.getInt("cid"));
				articles.add( a );
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return articles;		
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
