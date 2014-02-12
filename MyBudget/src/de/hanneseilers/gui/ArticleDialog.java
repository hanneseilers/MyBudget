package de.hanneseilers.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.toedter.calendar.JDateChooser;

import de.hanneseilers.core.Article;
import de.hanneseilers.core.Category;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

@SuppressWarnings("serial")
public class ArticleDialog extends JDialog {

	private JButton btnSave;
	private JButton btnCancel;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtArticle;
	private JTextField txtPrice;
	private JComboBox<Category> cmbCategory;
	private JRadioButton rdbtnIncome;
	private JRadioButton rdbtnOutgo;
	private JDateChooser dateChooser;
	
	/**
	 * Data of dialog
	 */
	private boolean isSaved = false;
	private Article article = null;
	
	/**
	 * Create a dialog
	 * @param aType
	 * @param aArticle
	 */
	public ArticleDialog(ArticleDialogType aType, Article aArticle) {
		// set dialog title
		setTitle( aType.getTitle() );
		
		// set article
		if( aArticle != null ){
			article = aArticle;
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		setBounds(100, 100, 312, 264);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblArticle = new JLabel("Artikel:");
		contentPanel.add(lblArticle, "2, 2, right, default");

		txtArticle = new JTextField();
		contentPanel.add(txtArticle, "4, 2, 3, 1, fill, default");
		txtArticle.setColumns(10);

		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setVerticalAlignment(SwingConstants.TOP);
		lblDatum.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblDatum, "2, 4");
		
		dateChooser = new JDateChooser();
		if( article != null ){
			dateChooser.setDate( article.getDate() );
		}
		else{
			dateChooser.setDate( new Date(System.currentTimeMillis()) );
		}
		contentPanel.add( dateChooser, "4, 4, 3, 1" );

		JLabel lblPrice = new JLabel("Preis:");
		contentPanel.add(lblPrice, "2, 6, right, default");

		txtPrice = new JTextField();
		txtPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPrice.setText("0,00");
		contentPanel.add(txtPrice, "4, 6, fill, default");
		txtPrice.setColumns(10);

		JLabel lblEUR = new JLabel("EUR");
		contentPanel.add(lblEUR, "6, 6");

		JPanel panArticleDialog = new JPanel();
		contentPanel.add(panArticleDialog, "2, 8, 5, 1, fill, fill");
		panArticleDialog.setLayout(new GridLayout(1, 0, 0, 0));

		rdbtnOutgo = new JRadioButton("Ausgabe");
		panArticleDialog.add(rdbtnOutgo);

		rdbtnIncome = new JRadioButton("Einnahme");
		panArticleDialog.add(rdbtnIncome);
		rdbtnIncome.setSelected(true);

		JLabel lblCategory = new JLabel("Kategorie:");
		contentPanel.add(lblCategory, "2, 10, right, default");

		cmbCategory = new JComboBox<Category>();
		contentPanel.add(cmbCategory, "4, 10, 3, 1, fill, default");

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btnSave = new JButton("Speichern");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				// Get article data
				String articleName = txtArticle.getText();
				Double articlePrice = 0.0;
				Date articleDate = dateChooser.getDate();
				try{
					articlePrice = Double.parseDouble( txtPrice.getText().replace(',', '.') );
				}
				catch(NumberFormatException e){
					articlePrice = 0.0;
				}
				Category articleCategory = (Category) cmbCategory.getSelectedItem();
				
				if( article == null ){
					// add new article
					article = new Article( articleName, articlePrice, articleDate, articleCategory );
				}
				else{
					// update article
					article.setArticle(articleName);
					article.setDate(articleDate);
					article.setPrice(articlePrice);
					article.setCategory(articleCategory);
				}
					
			}
		});
		this.
		btnSave.setActionCommand("OK");
		buttonPane.add(btnSave);
		getRootPane().setDefaultButton(btnSave);

		btnCancel = new JButton("Abbrechen");
		btnCancel.setActionCommand("Cancel");
		buttonPane.add(btnCancel);
			
	}
	
	/**
	 * Shows dialog
	 */
	public Article showDialog(){
		setModal(true);
		setVisible(true);
		return article;
	}

	/**
	 * @return True if saved button is clicked
	 */
	public boolean isSaved() {
		return isSaved;
	}

	/**
	 * @return Edited article
	 */
	public Article getArticle() {
		return article;
	}

}
