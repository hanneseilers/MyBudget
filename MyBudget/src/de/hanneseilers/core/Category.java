package de.hanneseilers.core;

public class Category {

	private int cid = -1;
	private String name = "";
	
	public Category(int aCID, String aName){
		cid = aCID;
		name = aName;
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
	 * @return Category name
	 */
	public String toString(){
		return name;
	}
	
}
