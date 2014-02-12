package de.hanneseilers.gui;

import de.hanneseilers.core.DBController;
import de.hanneseilers.core.MyBudget;

/**
 * Abstract class type for gui pages
 * @author Hannes Eilers
 *
 */
public abstract class Page {
	
	protected MainFrame frmMain = MyBudget.frmMain;
	protected DBController db = MyBudget.database;
	
}
