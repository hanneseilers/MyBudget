package de.hanneseilers.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Toolkit;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * Main GUI frame
 * @author Hannes Eilers
 *
 */
public class MainFrame {

	private JFrame frmMain;
	public JTabbedPane tabbedPane;
	public JPanel tabStart;
	public JPanel tabIncome;
	public JPanel tabOverview;
	public JPanel tabSettings;
	public JPanel panel;
	public JButton btnStartIncome;
	public JButton btnStartOverview;
	public JButton btnStartOutgo;
	public JButton btnStartSettings;
	
	public JButton btnIncomeSave;
	public JLabel lblIncome3;
	public JTextField txtIncomePrice;
	public JTextField txtIncomeArticle;
	public JComboBox<String> cmbIncomeCategory;
	public JTextField txtIncomeYear;
	public JLabel lblIncome2;
	public JTextField txtIncomeMonth;
	public JLabel lblIncome1;
	public JTextField txtIncomeDay;
	public JLabel lblIncomeDayname;
	public JButton btnIncomeNewArticle;
	public JButton btnIncomeRemoveArticle;
	public JList lstIncome;
	
	public JPanel tabOutgo;
	
	public JButton btnOutgoSave;
	public JLabel lblOutgo3;
	public JTextField txtOutgoPrice;
	public JTextField txtOutgoArticle;
	public JComboBox<String> cmbOutgoCategory;
	public JTextField txtOutgoYear;
	public JLabel lblOutgo2;
	public JTextField txtOutgoMonth;
	public JLabel lblOutgo1;
	public JTextField txtOutgoDay;
	public JLabel lblOutgoDayname;
	public JButton btnOutgoNewArticle;
	public JButton btnOutgoRemoveArticle;
	public JList lstOutgo;
	public JComboBox<String> cmbOverviewCategory;
	public JLabel lblOverview1;
	public JLabel lblOverviewIncomeTotal;
	public JLabel lblOverview2;
	public JLabel lblOverviewOutgoTotal;
	public JLabel lblOverview3;
	public JLabel lblOverviewTotal;
	public JLabel lblOverview4;
	public JLabel lblStichwort;
	public JTextField txtOverviewSearch;
	public JButton btnOverviewSearch;
	public JList lstOverviewArticles;
	public JButton btnSettingsRemoveCategory;
	public JLabel lblSettings1;
	public JComboBox<String> cmbSettingsCategories;
	public JTextField txtSettingsCatergoyName;
	public JButton btnSettingsRenameCategory;
	public JButton btnSettingsAddCategory;
	public JLabel lblStartTitle;
	public JLabel lblStartFooter;
	public JLabel lblStartIcon;

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		frmMain.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/de/hanneseilers/gui/icon/MyBudget_16.png")));
		frmMain.setTitle("MyBudget (C) 2013");
		frmMain.setBounds(100, 100, 800, 600);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmMain.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabStart = new JPanel();
		tabbedPane.addTab("Start", null, tabStart, null);
		tabStart.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("4dlu:grow"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("4dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		lblStartTitle = new JLabel("MyBudget");
		lblStartTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStartTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		tabStart.add(lblStartTitle, "2, 2, 3, 1");
		lblStartIcon = new JLabel("");
		tabStart.add(lblStartIcon, "3, 4");
		lblStartIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblStartIcon.setIcon(new ImageIcon(MainFrame.class.getResource("/de/hanneseilers/gui/icon/MyBudget_64.png")));
		panel = new JPanel();
		tabStart.add(panel, "3, 6, center, center");
		panel.setLayout(new GridLayout(0, 1, 0, 10));
		btnStartIncome = new JButton("Einnahmen");
		panel.add(btnStartIncome);
		btnStartOutgo = new JButton("Ausgaben");
		panel.add(btnStartOutgo);
		btnStartOverview = new JButton("\u00DCbersicht");
		panel.add(btnStartOverview);
		btnStartSettings = new JButton("Einstellungen");
		panel.add(btnStartSettings);
		lblStartFooter = new JLabel("by hannes eilers (C) 2013 - www.hanneseilers.de");
		lblStartFooter.setHorizontalAlignment(SwingConstants.LEFT);
		tabStart.add(lblStartFooter, "2, 8, 3, 1");
		
		tabIncome = new JPanel();
		tabbedPane.addTab("Einnahmen", null, tabIncome, null);
		tabIncome.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("30dlu"),
				ColumnSpec.decode("20dlu"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("35dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		lblIncomeDayname = new JLabel("Montag");
		tabIncome.add(lblIncomeDayname, "2, 2, right, default");
		txtIncomeDay = new JTextField();
		txtIncomeDay.setHorizontalAlignment(SwingConstants.CENTER);
		txtIncomeDay.setText("12");
		tabIncome.add(txtIncomeDay, "4, 2, fill, default");
		txtIncomeDay.setColumns(10);
		lblIncome1 = new JLabel(".");
		tabIncome.add(lblIncome1, "6, 2, right, default");
		txtIncomeMonth = new JTextField();
		txtIncomeMonth.setHorizontalAlignment(SwingConstants.CENTER);
		txtIncomeMonth.setText("10");
		tabIncome.add(txtIncomeMonth, "8, 2, fill, default");
		txtIncomeMonth.setColumns(10);
		lblIncome2 = new JLabel(".");
		tabIncome.add(lblIncome2, "10, 2, right, default");
		txtIncomeYear = new JTextField();
		txtIncomeYear.setHorizontalAlignment(SwingConstants.CENTER);
		txtIncomeYear.setText("2013");
		tabIncome.add(txtIncomeYear, "12, 2, fill, default");
		txtIncomeYear.setColumns(10);
		cmbIncomeCategory = new JComboBox();
		tabIncome.add(cmbIncomeCategory, "14, 2, fill, default");
		txtIncomeArticle = new JTextField();
		txtIncomeArticle.setText("Artikel");
		tabIncome.add(txtIncomeArticle, "16, 2, fill, default");
		txtIncomeArticle.setColumns(10);
		txtIncomePrice = new JTextField();
		txtIncomePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		txtIncomePrice.setText("1205,55");
		tabIncome.add(txtIncomePrice, "18, 2, fill, default");
		txtIncomePrice.setColumns(10);
		lblIncome3 = new JLabel("EUR");
		tabIncome.add(lblIncome3, "20, 2");
		btnIncomeSave = new JButton("Speichern");
		tabIncome.add(btnIncomeSave, "22, 2");
		lstIncome = new JList();
		tabIncome.add(lstIncome, "2, 4, 19, 3, fill, fill");
		btnIncomeNewArticle = new JButton("Neuer Artikel");
		tabIncome.add(btnIncomeNewArticle, "22, 4, default, top");
		btnIncomeRemoveArticle = new JButton("Artikel entfernen");
		tabIncome.add(btnIncomeRemoveArticle, "22, 6");
		
		tabOutgo = new JPanel();
		tabbedPane.addTab("Ausgaben", null, tabOutgo, null);
		tabOutgo.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("30dlu"),
				ColumnSpec.decode("20dlu"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("35dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		lblOutgoDayname = new JLabel("Montag");
		tabOutgo.add(lblOutgoDayname, "2, 2, right, default");
		txtOutgoDay = new JTextField();
		txtOutgoDay.setHorizontalAlignment(SwingConstants.CENTER);
		txtOutgoDay.setText("12");
		tabOutgo.add(txtOutgoDay, "4, 2, fill, default");
		txtOutgoDay.setColumns(10);
		lblOutgo1 = new JLabel(".");
		tabOutgo.add(lblOutgo1, "6, 2, right, default");
		txtOutgoMonth = new JTextField();
		txtOutgoMonth.setHorizontalAlignment(SwingConstants.CENTER);
		txtOutgoMonth.setText("10");
		tabOutgo.add(txtOutgoMonth, "8, 2, fill, default");
		txtOutgoMonth.setColumns(10);
		lblOutgo2 = new JLabel(".");
		tabOutgo.add(lblOutgo2, "10, 2, right, default");
		txtOutgoYear = new JTextField();
		txtOutgoYear.setHorizontalAlignment(SwingConstants.CENTER);
		txtOutgoYear.setText("2013");
		tabOutgo.add(txtOutgoYear, "12, 2, fill, default");
		txtOutgoYear.setColumns(10);
		cmbOutgoCategory = new JComboBox();
		tabOutgo.add(cmbOutgoCategory, "14, 2, fill, default");
		cmbOutgoCategory = new JComboBox();
		tabOutgo.add(cmbOutgoCategory, "14, 2, fill, default");
		txtOutgoArticle = new JTextField();
		txtOutgoArticle.setText("Artikel");
		tabOutgo.add(txtOutgoArticle, "16, 2, fill, default");
		txtOutgoArticle.setColumns(10);
		txtOutgoPrice = new JTextField();
		txtOutgoPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOutgoPrice.setText("1205,55");
		tabOutgo.add(txtOutgoPrice, "18, 2, fill, default");
		txtOutgoPrice.setColumns(10);
		lblOutgo3 = new JLabel("EUR");
		tabOutgo.add(lblOutgo3, "20, 2");
		btnOutgoSave = new JButton("Speichern");
		tabOutgo.add(btnOutgoSave, "22, 2");
		lstOutgo = new JList();
		tabOutgo.add(lstOutgo, "2, 4, 19, 3, fill, fill");
		btnOutgoNewArticle = new JButton("Neuer Artikel");
		tabOutgo.add(btnOutgoNewArticle, "22, 4, default, top");
		btnOutgoRemoveArticle = new JButton("Artikel entfernen");
		tabOutgo.add(btnOutgoRemoveArticle, "22, 6");
		
		
		tabOverview = new JPanel();
		tabbedPane.addTab("\u00DCbersicht", null, tabOverview, null);
		tabOverview.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;pref)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu"),
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
				RowSpec.decode("20dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("20dlu"),
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		lblOverview4 = new JLabel("Kategorie:");
		lblOverview4.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverview4, "2, 2, right, default");
		cmbOverviewCategory = new JComboBox();
		tabOverview.add(cmbOverviewCategory, "4, 2, 5, 1, fill, default");
		lblStichwort = new JLabel("Stichwort:");
		lblStichwort.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblStichwort, "2, 4, right, default");
		txtOverviewSearch = new JTextField();
		tabOverview.add(txtOverviewSearch, "4, 4, 3, 1, fill, default");
		txtOverviewSearch.setColumns(10);
		btnOverviewSearch = new JButton("Suche");
		tabOverview.add(btnOverviewSearch, "8, 4");
		lblOverview1 = new JLabel("Einnahmen:");
		lblOverview1.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverview1, "2, 6, 3, 1");
		lblOverviewIncomeTotal = new JLabel("0,00 EUR");
		lblOverviewIncomeTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverviewIncomeTotal, "6, 6");
		lblOverview2 = new JLabel("Ausgaben:");
		lblOverview2.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverview2, "2, 8, 3, 1");
		lblOverviewOutgoTotal = new JLabel("0,00 EUR");
		lblOverviewOutgoTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverviewOutgoTotal, "6, 8");
		lblOverview3 = new JLabel("Bilanz:");
		lblOverview3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOverview3.setFont(new Font("Tahoma", Font.BOLD, 11));
		tabOverview.add(lblOverview3, "2, 10, 3, 1");
		lblOverviewTotal = new JLabel("0,00 EUR");
		lblOverviewTotal.setForeground(new Color(0, 128, 0));
		lblOverviewTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOverviewTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverviewTotal, "6, 10");
		lstOverviewArticles = new JList();
		tabOverview.add(lstOverviewArticles, "2, 12, 9, 1, fill, fill");
		
		tabSettings = new JPanel();
		tabbedPane.addTab("Einstellungen", null, tabSettings, null);
		tabSettings.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		lblSettings1 = new JLabel("Kategorien:");
		lblSettings1.setHorizontalAlignment(SwingConstants.RIGHT);
		tabSettings.add(lblSettings1, "2, 2, right, default");
		cmbSettingsCategories = new JComboBox();
		tabSettings.add(cmbSettingsCategories, "4, 2, fill, default");
		btnSettingsRemoveCategory = new JButton("Kategorie l\u00F6schen");
		tabSettings.add(btnSettingsRemoveCategory, "6, 2");
		txtSettingsCatergoyName = new JTextField();
		tabSettings.add(txtSettingsCatergoyName, "4, 4, fill, default");
		txtSettingsCatergoyName.setColumns(10);
		btnSettingsRenameCategory = new JButton("Kategorie umbennen");
		tabSettings.add(btnSettingsRenameCategory, "6, 4");
		btnSettingsAddCategory = new JButton("Kategorie hinzuf\u00FCgen");
		tabSettings.add(btnSettingsAddCategory, "6, 6");
	}
	
}
