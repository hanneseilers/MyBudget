package de.hanneseilers.core.tasks;

import java.awt.Font;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

/**
 * Class for printing plain text
 * @author Hannes Eilers
 *
 */
public class Printer {

	private JTextArea component = null;
	private MessageFormat header = null;
	private MessageFormat footer = null;
	public static int fontSize = 9;
	
	/**
	 * Constructor using a header and a footer text
	 * @param content
	 * @param header
	 * @param footer
	 */
	public Printer(String content, String header, String footer) {
		component = new JTextArea(content);
		component.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
		
		if( header != null )
			this.header = new MessageFormat(header);
		if( footer != null )
			this.footer = new MessageFormat(footer);
	}
	
	/**
	 * Constructor using header text (no footer)
	 * @param content
	 * @param header
	 */
	public Printer(String content, String header){
		this(content, header, null);
	}
	
	/**
	 * Constructor using content only (no heade ror footer)
	 * @param content
	 */
	public Printer(String content){
		this(content, null, null);
	}
	
	/**
	 * Prints data
	 * @return True if data send to printer
	 */
	public boolean print(){
		try{
			
			// print
			if( header == null && footer == null )
				return component.print();
			else if( header == null && footer != null )
				header = new MessageFormat("");
			else if( footer == null && header != null )
				footer = new MessageFormat("");
			
			return component.print(header, footer);
			
		} catch( PrinterException e ){
			Logger.getLogger(getClass()).error("Could not print data.");
		}
		
		return false;
	}
	
}
