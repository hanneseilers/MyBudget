package de.hanneseilers.gui;

public enum ArticleDialogType {

	NONE(""),
	INCOME_ADD("Einnahme hinzufügen"),
	INCOME_EDIT("Einnahme bearbeiten"),
	OUTGO_ADD("Ausgabe hinzufügen"),
	OUTGO_EDIT("Ausgabe bearbeiten");
	
	private String title;
	
	private ArticleDialogType(String aTitle){
		title = aTitle;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
}
