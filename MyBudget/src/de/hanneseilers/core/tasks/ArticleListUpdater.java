package de.hanneseilers.core.tasks;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import de.hanneseilers.core.Article;

/**
 * Adds a set of articles to a list model
 * @author Hannes Eilers
 *
 */
public class ArticleListUpdater implements Runnable {

	private DefaultListModel<Article> model = null;
	private List<Article> articles = null;
	public String name = null;
	
	/**
	 * List containing all running tasks names
	 */
	public static List<String> runningTasks = new ArrayList<String>();
	public static final long taskWaitTimeout = 30 * 1000;		// 30 sec.
	
	/**
	 * Constructor
	 * @param model List model to update
	 * @param articles Articles to ad to list model
	 * @param name Name of the task
	 */
	public ArticleListUpdater(String name, DefaultListModel<Article> model, List<Article> articles){
		this.model = model;
		this.articles = articles;
		
	}
	
	@Override
	public void run() {
		
		try {
		
			/*
			 * Wait until other tasks with this name are finished
			 * or timeout occured.
			 */
			long t1 = System.currentTimeMillis();
			while( isSameTaskActive(this) && (System.currentTimeMillis()-t1) > taskWaitTimeout );
			
			// add task to task list
			addTask(this);
			
			// clear model
			SwingUtilities.invokeAndWait( new Runnable() {	
				
				@Override
				public void run() {
					model.clear();					
				}
				
			} );
			
			// Add articles to list model using invokeAndWait function of SwingUtilities
			for( Article article : articles ){		
					SwingUtilities.invokeAndWait( new ArticleAdder(model, article) );
			}
			
			// remove task from task list
			removeTask(this);
			
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return True if this task is already running.
	 */
	private static synchronized boolean isSameTaskActive(ArticleListUpdater task){
		synchronized (ArticleListUpdater.class) {
			return runningTasks.contains(task.name);
		}		
	}
	
	/**
	 * Adds this task to the list of running tasks
	 */
	private static synchronized void addTask(ArticleListUpdater task){
		synchronized (ArticleListUpdater.class) {
			runningTasks.add(task.name);
		}
	}
	
	/**
	 * Removes current task from list of running tasks
	 */
	private static synchronized void removeTask(ArticleListUpdater task){
		synchronized (ArticleListUpdater.class) {
			runningTasks.remove(task.name);
		}
	}
	
	
	/**
	 * Class for adding a article to a default list model
	 * @author Hannes Eilers
	 *
	 */
	private class ArticleAdder implements Runnable{

		private DefaultListModel<Article> model = null;
		private Article article = null;
		
		/**
		 * Constructor
		 * @param model List model to update
		 * @param article Article to add
		 */
		public ArticleAdder(DefaultListModel<Article> model, Article article) {
			this.model = model;
			this.article = article;
		}
		
		@Override
		public void run() {
			model.addElement(article);
		}
		
	}

}
