package de.hanneseilers.core;

import java.util.Date;

import org.apache.log4j.Logger;
import de.hanneseilers.gui.MainFrame;
import de.hanneseilers.gui.PageIncome;
import de.hanneseilers.gui.PageOutgo;
import de.hanneseilers.gui.PageOverview;
import de.hanneseilers.gui.PageSettings;
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
	public MyBudget(SplashScreen splash){
		// Set logger
		logger = Logger.getLogger(getClass());
		
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
		
		// Execute patches
		splash.setStatus("Patching application...");
		patch( Loader.config.getInt(ConfigurationValues.APP_LAST_PATCH.getKey(), (int) ConfigurationValues.APP_LAST_PATCH.getDefaultValue()) );
		
		// Create gui and gui page objects
		splash.setStatus("Loading main frame pages...");
		frmMain = new MainFrame();
		
		new PageStart();
		new PageIncome();
		new PageOutgo();
		new PageOverview();
		new PageSettings();
		
		// show main frame and dispose waiting dialog
		splash.setStatus("Showing main frame...");
		frmMain.setVisible(true);
		splash.setVisible(false);
		splash.dispose();

	}
	
	/**
	 * Initiates categories
	 */
	private void initCategories(){
		if( database.isCategoriesEmpty() ){
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
	}
	
	/**
	 * Patches application.
	 * @param patch	{@link Integer} of last executed patch.
	 */
	private void patch(int patch){
		
		// Correcting wrong database article timestamps.
		if( patch < 1 ){
			logger.info( "Executing patch #1" );
			for( Article vArticle : database.getArticles(null, 0) ){
				Date vDate = vArticle.getDate();
				vDate.setTime( vDate.getTime()
						+ Loader.config.getLong(ConfigurationValues.ARTICLE_TIMESTAMP_DAYS.getKey()) );
				vArticle.setDate( vDate );
				database.updateArticle(vArticle);
				logger.info( "Updated " + vArticle );
			}
			Loader.config.setProperty( ConfigurationValues.APP_LAST_PATCH.getKey(), 1 );
		}
		
	}

}
