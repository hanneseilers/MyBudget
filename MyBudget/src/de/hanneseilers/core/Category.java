package de.hanneseilers.core;

public class Category {

	DBController db = MyBudget.database;
	private int cid = -1;
	private String name = "";
	
	/**
	 * Constructor
	 */
	public Category(){}
	
	/**
	 * Constructor
	 * @param aName
	 */
	public Category(String name){
		setName(name);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param cid
	 */
	public Category(String name, int cid){
		setCID(cid);
		setName(name);
	}
	
	/**
	 * @return the cid
	 */
	public int getCID() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCID(int cid) {
		this.cid = cid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Updates this article
	 */
	public void update(){
		if( db.isDbReady() ){
			db.updateCategory(this);
		}
	}
	
	/**
	 * Deletes this category from database
	 * @return True if successfull
	 */
	public boolean delete(){
		return db.deleteCategory(this);
	}
	
	/**
	 * @return Category name
	 */
	public String toString(){
		return name;
	}
	
}
