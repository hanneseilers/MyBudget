package de.hanneseilers.core;

import de.hanneseilers.gui.MainFrame;
import de.hanneseilers.gui.PageStart;

/**
 * Main
 * @author Hannes Eilers
 *
 */
public class MyBudget {

	public static final MainFrame frmMain = new MainFrame();
	public static final DBController database = new DBController();
	
	/**
	 * Initiates categories
	 */
	public static void initCategories(){
		database.addCategory("Lebenmittel");
		database.addCategory("Getränke");
		database.addCategory("Süßigkeiten");
		database.addCategory("Haus");
		database.addCategory("Auto");
		database.addCategory("Versicherungen");
		database.addCategory("Lohn");
		database.addCategory("Unterhalt");
		database.addCategory("Geschenke");
		database.addCategory("Feiern");
		database.addCategory("Freizeit");
		database.addCategory("Urlaub");
		database.addCategory("Diverses");
	}
	
	/**
	 * Main routine
	 * @param args	Command line arguments
	 */
	public static void main(String[] args) {
		// Wait for database creation
		while( !database.isDbReady() ){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
		
		// Create categories
		initCategories();
		
		// Create gui page objects
		new PageStart();
	}

}
