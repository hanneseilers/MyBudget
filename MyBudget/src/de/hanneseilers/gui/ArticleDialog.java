package de.hanneseilers.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.toedter.calendar.JDateChooser;

import de.hanneseilers.core.Article;
import de.hanneseilers.core.Category;
import de.hanneseilers.core.ConfigurationValues;
import de.hanneseilers.core.DBController;
import de.hanneseilers.core.Loader;
import de.hanneseilers.core.MyBudget;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

import org.apache.log4j.Logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JCheckBox;

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
	public JCheckBox chkSaveLastDate;
	public JCheckBox chkSaveLastCategory;

	private DBController db = MyBudget.database;
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Data of dialog
	 */
	private boolean isSaved = false;
	private Article article = null;
	public final ButtonGroup rdbgrpIncomeOutgo = new ButtonGroup();

	/**
	 * Create a dialog
	 * 
	 * @param aType
	 * @param aArticle
	 */
	public ArticleDialog(ArticleDialogType aType, Article aArticle) {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				dialogCanceled();
			}
		});

		// set dialog title
		setTitle(aType.getTitle());

		String articleName = "";
		String articlePrice = "0.00";
		Date articleDate;
		if (Loader.config.getBoolean(ConfigurationValues.ARTICLE_SAVE_LAST_DATE.getKey())) {
			articleDate = new Date(Loader.config.getLong(ConfigurationValues.ARTICLE_LAST_DATE.getKey()));
		} else {
			articleDate = new Date(System.currentTimeMillis());
		}

		System.out.println("loaded " + articleDate.getTime());

		// set article
		if (aArticle != null) {
			article = aArticle;
			articleName = article.getArticle();
			articlePrice = String.format("%." + Article.numbersPostDecimalPlaces + "f", (article.getPrice()));
			articleDate = article.getDate();
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 312, 283);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC }));

		JLabel lblArticle = new JLabel("Artikel:");
		contentPanel.add(lblArticle, "2, 2, right, default");

		txtArticle = new JTextField();
		txtArticle.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtArticle.selectAll();
			}
		});
		contentPanel.add(txtArticle, "4, 2, 3, 1, fill, default");
		txtArticle.setColumns(10);
		txtArticle.setText(articleName);

		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setVerticalAlignment(SwingConstants.TOP);
		lblDatum.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblDatum, "2, 4");

		dateChooser = new JDateChooser();
		((JTextField) dateChooser.getDateEditor().getUiComponent()).addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				((JTextField) e.getSource()).selectAll();
			}
		});
		dateChooser.setDate(articleDate);
		contentPanel.add(dateChooser, "4, 4, 3, 1");

		JLabel lblPrice = new JLabel("Preis:");
		contentPanel.add(lblPrice, "2, 6, right, default");

		txtPrice = new JTextField();
		txtPrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						txtPrice.selectAll();
					}
				});
			}
		});
		txtPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(txtPrice, "4, 6, fill, default");
		txtPrice.setColumns(10);
		txtPrice.setText(articlePrice);

		JLabel lblEUR = new JLabel("EUR");
		contentPanel.add(lblEUR, "6, 6");

		JPanel panArticleDialog = new JPanel();
		contentPanel.add(panArticleDialog, "2, 8, 5, 1, fill, fill");
		panArticleDialog.setLayout(new GridLayout(1, 0, 0, 0));

		rdbtnOutgo = new JRadioButton("Ausgabe");
		rdbgrpIncomeOutgo.add(rdbtnOutgo);
		panArticleDialog.add(rdbtnOutgo);

		rdbtnIncome = new JRadioButton("Einnahme");
		rdbgrpIncomeOutgo.add(rdbtnIncome);
		panArticleDialog.add(rdbtnIncome);
		rdbtnIncome.setSelected(true);

		JLabel lblCategory = new JLabel("Kategorie:");
		contentPanel.add(lblCategory, "2, 10, right, default");

		chkSaveLastDate = new JCheckBox("Datum merken");
		chkSaveLastDate.setSelected(true);
		contentPanel.add(chkSaveLastDate, "2, 12, 5, 1");

		chkSaveLastCategory = new JCheckBox("Kategorie merken");
		chkSaveLastCategory.setSelected(true);
		contentPanel.add(chkSaveLastCategory, "2, 14, 5, 1");

		// add categories
		cmbCategory = new JComboBox<Category>();
		contentPanel.add(cmbCategory, "4, 10, 3, 1, fill, default");

		for (Category c : db.getCategories()) {
			cmbCategory.addItem(c);
		}

		// select category of article in JComboBox
		if (article != null && article.getCategory() != null && article.getCategory().getCID() > 0) {
			selectCategroy(article.getCategory().getName());
		} else if (Loader.config.getBoolean(ConfigurationValues.ARTICLE_SAVE_LAST_CATEGORY.getKey())) {
			selectCategroy(Loader.config.getString(ConfigurationValues.ARTICLE_LAST_CATEGORY.getKey()));
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnSave = new JButton("Speichern");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogSaved();
			}
		});
		btnSave.setActionCommand("OK");
		buttonPane.add(btnSave);
		getRootPane().setDefaultButton(btnSave);

		btnCancel = new JButton("Abbrechen");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dialogCanceled();
			}
		});
		btnCancel.setActionCommand("Cancel");
		buttonPane.add(btnCancel);

		// set income/outgo radio button selection
		if (aType == ArticleDialogType.OUTGO_ADD || aType == ArticleDialogType.OUTGO_EDIT) {
			rdbtnOutgo.setSelected(true);
		}

	}

	/**
	 * Selects a catergory from drop down list.
	 * 
	 * @param categoryName
	 *            {@link String} of category name.
	 */
	private void selectCategroy(String categoryName) {
		for (int i = 0; i < cmbCategory.getItemCount(); i++) {
			if (cmbCategory.getItemAt(i).getName().equals(categoryName)) {
				cmbCategory.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * Call if dialog should be saved
	 */
	private void dialogSaved() {

		// Get article data
		String articleName = txtArticle.getText();
		Double articlePrice = 0.0;
		Date articleDate = dateChooser.getDate();
		long curDay = (System.currentTimeMillis() / Article.timestampDay) * Article.timestampDay;

		// check datedate
		if (articleDate == null) {
			articleDate = new Date(curDay);
		}

		// try to convert price to double
		try {
			articlePrice = Double.parseDouble(txtPrice.getText().replace(',', '.'));
		} catch (NumberFormatException e) {
			articlePrice = 0.0;
		}

		Category articleCategory = cmbCategory.getItemAt(cmbCategory.getSelectedIndex());

		// check for income or outgo
		if (rdbtnOutgo.isSelected() && articlePrice >= 0) {
			articlePrice *= -1.0;
		} else if (rdbtnIncome.isSelected() && articlePrice < 0) {
			articlePrice *= -1.0;
		}

		// check if to update or to add article
		if (article == null) {
			// add new article
			article = new Article(articleName, articlePrice, articleDate, articleCategory);
			article.update();
			logger.debug("Added new article: " + article.toString());
		} else {
			// update article
			article.setArticle(articleName);
			article.setDate(articleDate);
			article.setPrice(articlePrice);
			article.setCategory(articleCategory);
			article.update();
			logger.debug("Updated article: " + article.toString());
		}

		// Check if to save last date
		Loader.config.setProperty(ConfigurationValues.ARTICLE_SAVE_LAST_DATE.getKey(), chkSaveLastDate.isSelected());
		Loader.config.setProperty(ConfigurationValues.ARTICLE_LAST_DATE.getKey(), articleDate.getTime());

		// Check if to save last category
		Loader.config.setProperty(ConfigurationValues.ARTICLE_SAVE_LAST_CATEGORY.getKey(),
				chkSaveLastCategory.isSelected());
		Loader.config.setProperty(ConfigurationValues.ARTICLE_LAST_CATEGORY.getKey(),
				cmbCategory.getItemAt(cmbCategory.getSelectedIndex()).getName());

		logger.debug("Hiding dialog window: " + getTitle());
		setVisible(false);

	}

	/**
	 * Call if dialog should be canceled
	 */
	private void dialogCanceled() {
		logger.debug("Dialog window aborted.");
		article = null;
		setVisible(false);
	}

	/**
	 * Shows dialog
	 * 
	 * @param Returns
	 *            edited article object or null if dialog closed
	 */
	public Article showDialog() {
		logger.debug("Openening dialog: " + getTitle());
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
