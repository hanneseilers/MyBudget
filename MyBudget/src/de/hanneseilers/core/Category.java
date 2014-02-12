package de.hanneseilers.core;

public class Category {

	DBController db = MyBudget.database;
	private int cid = -1;
	private String name = "";
	private boolean dynObj = true;
	
	/**
	 * Constructor
	 */
	public Category(){}
	
	/**
	 * Constructor
	 * @param nondynObj If false the object is
	 * not automatically updated on database
	 */
	public Category(boolean dynObj){
		this.dynObj = dynObj;
	}
	
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
		update();
	}
	
	/**
	 * Updates this article
	 */
	private void update(){
		if( dynObj && db.isDbReady() ){
			db.updateCategory(this);
		}
	}
	
	/**
	 * @return Category name
	 */
	public String toString(){
		return name;
	}

	/**
	 * @return the dynObj
	 */
	public boolean isDynObj() {
		return dynObj;
	}

	/**
	 * @param dynObj the dynObj to set
	 */
	public void setDynObj(boolean dynObj) {
		this.dynObj = dynObj;
	}
	
}
