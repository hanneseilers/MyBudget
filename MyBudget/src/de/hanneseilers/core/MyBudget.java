package de.hanneseilers.core;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.hanneseilers.gui.MainFrame;
import de.hanneseilers.gui.PageIncome;
import de.hanneseilers.gui.PageOutgo;
import de.hanneseilers.gui.PageStart;
import de.hanneseilers.gui.SplashScreen;

/**
 * Main
 * @author Hannes Eilers
 *
 */
public class MyBudget {

	public static MainFrame frmMain = null;
	public static DBController database = null;
	
	private Logger logger;
	
	/**
	 * Constructor
	 */
	public MyBudget(){
		SplashScreen splash = new SplashScreen();
		
		// Init logger
		splash.setStatus("Loading logger...");
		initLogger();
		
		logger.info("Application started.");
		
		// Wait for database creation
		splash.setStatus("Initiating database...");
		database = new DBController();
		while( !database.isDbReady() ){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				logger.warn("Waiting for databse to get ready is interrupted.");
			}
		}
		
		// Create categories
		splash.setStatus("Initiating categories...");
		initCategories();
		
		// Create gui and gui page objects
		splash.setStatus("Loading main frame pages...");
		frmMain = new MainFrame();
		
		new PageStart();
		new PageIncome();
		new PageOutgo();
		
		// show main frame and dispose waiting dialog
		splash.setStatus("Showing main frame...");
		frmMain.setVisible(true);
		splash.setVisible(false);
		splash.dispose();

	}
	
	/**
	 * Initiates log4j logger
	 */
	private void initLogger(){
		
		try{
			
			PropertyConfigurator.configureAndWatch( "log4j.properties", 60*1000 );
			logger = Logger.getLogger(getClass());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Initiates categories
	 */
	private void initCategories(){
		logger.debug("Initiating categories.");
		database.addCategory( new Category("Lebensmittel") );
		database.addCategory( new Category("Getränke") );
		database.addCategory( new Category("Süßigkeiten") );
		database.addCategory( new Category("Haus") );
		database.addCategory( new Category("Auto") );
		database.addCategory( new Category("Versicherungen") );
		database.addCategory( new Category("Lohn") );
		database.addCategory( new Category("Unterhalt") );
		database.addCategory( new Category("Geschenke") );
		database.addCategory( new Category("Feiern") );
		database.addCategory( new Category("Freizeit") );
		database.addCategory( new Category("Urlaub") );
		database.addCategory( new Category("Diverses") );
	}
	
	/**
	 * Main routine
	 * @param args	Command line arguments
	 */
	public static void main(String[] args) {
		new MyBudget();
	}

}
