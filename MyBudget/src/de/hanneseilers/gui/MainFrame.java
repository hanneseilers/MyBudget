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
import com.toedter.calendar.JDateChooser;

import de.hanneseilers.core.Article;
import de.hanneseilers.core.Category;
import de.hanneseilers.core.ConfigurationValues;
import de.hanneseilers.core.Loader;

import javax.swing.JButton;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.Date;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	public JPanel panStartButtons;
	public JButton btnStartIncome;
	public JButton btnStartOverview;
	public JButton btnStartOutgo;
	public JButton btnStartSettings;
	
	public DefaultListModel<Article> lstIncomeModel;
	public DefaultListModel<Article> lstOutgoModel;
	public DefaultListModel<Article> lstOverviewModel;
	
	public JPanel tabOutgo;
	public JComboBox<Category> cmbOutgoCategory;
	public JComboBox<Category> cmbOverviewCategory;
	public JLabel lblOverview1;
	public JLabel lblOverviewIncomeTotal;
	public JLabel lblOverview2;
	public JLabel lblOverviewOutgoTotal;
	public JLabel lblOverview3;
	public JLabel lblOverviewTotal;
	public JLabel lblOverview4;
	public JLabel lblStichwort;
	public JTextField txtOverviewSearch;
	public JButton btnOverviewFilter;
	public JList<Article> lstOverviewArticles;
	public JButton btnSettingsRemoveCategory;
	public JLabel lblSettingsCatergory;
	public JComboBox<Category> cmbSettingsCategories;
	public JTextField txtSettingsCatergoyName;
	public JButton btnSettingsCategoryRename;
	public JButton btnSettingsCategoryAdd;
	public JLabel lblStartTitle;
	public JLabel lblStartFooter;
	public JLabel lblStartIcon;
	public JLabel lblSettingsCategoryName;
	public JPanel panIncomeButtons;
	public JList<Article> lstIncome;
	public JButton btnIncomeAdd;
	public JButton btnIncomeDelete;
	public JButton btnIncomeEdit;
	public JPanel panIncomeFilter;
	public JRadioButton rdbtnIncomeFilterCategory;
	public JButton btnIncomeFilter;
	public JTextField txtIncomeFilter;
	public JRadioButton rdbtnIncomeFilterWord;
	public JComboBox<Category> cmbIncomeFilterCategory;
	public final ButtonGroup btngrpIncomeFilter = new ButtonGroup();
	public JList<Article> lstOutgo;
	public JPanel panOutgoButtons;
	public JPanel panOutgoFilter;
	public JButton btnOutgoAdd;
	public JButton btnOutgoDelete;
	public JButton btnOutgoEdit;
	public JRadioButton rdbtnOutgoFilterCategory;
	public JButton btnOutgoFilter;
	public JRadioButton rdbtnOutgoFilterWord;
	public JTextField txtOutgoFilter;
	public JComboBox<Category> cmbOutgoFilterCategory;
	public final ButtonGroup btngrpOutgoFilter = new ButtonGroup();
	public JPanel panSettingsButtons;
	public JLabel lblOverview5;
	public JLabel lblOverviewTrend;
	public JLabel lblOverview7;
	public JTextField txtOverviewDays;
	public JLabel lblIncomeHeader;
	public JLabel lbOutgoHeader;
	public JButton btnIncomeRefresh;
	public JButton btnOutgoRefresh;
	public JLabel lblSettings1;
	public JLabel lblSettingsApplicationVersion;
	public JRadioButton rdbOverviewTimePeriod;
	public JRadioButton rdbOverviewTimeDays;
	public final ButtonGroup rdbgpOverviewTime = new ButtonGroup();
	public JLabel lblOverview6;
	public JDateChooser dateChooserOverviewTimePeriodFrom;
	public JDateChooser dateChooserOverviewTimePeriodTill;
	public JPanel panSettingsCategories;
	public JPanel panSettingsViewOptions;
	public JPanel panSettingsAppInfo;
	public JLabel lblSettings2;
	public JLabel lblSettingsAppLastUpdate;
	public JButton btnSettingsAppUpdate;
	public JLabel lblSettingsAppUpdateStatus;
	public JLabel lblSettingsView1;
	public JLabel lblSettingsView2;
	public JTextField txtSettingsViewArticleNameLength;
	public JTextField txtSettingsViewCategoryNameLength;
	public JLabel lblSettingsView3;
	public JTextField txtSettingsViewCurrencySymbol;
	public JLabel lblSettingsView4;
	public JTextField txtSettingsViewPreDecimalPlaces;
	public JLabel lblSettingsView5;
	public JTextField txtSettingsViewPostDecimalPlaces;
	public JLabel lblSettingsView6;
	public JTextField txtSettingsViewArticleMaxRows;
	private JButton btnOverviewTimeDaysReset;

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		frmMain.setVisible(false);
	}
	
	/**
	 * Sets visibility of frame
	 * @param b
	 */
	public void setVisible(boolean b){
		frmMain.setVisible(b);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/de/hanneseilers/gui/icon/MyBudget_64.png")));
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
		panStartButtons = new JPanel();
		tabStart.add(panStartButtons, "3, 6, center, center");
		panStartButtons.setLayout(new GridLayout(0, 1, 0, 10));
		btnStartIncome = new JButton("Einnahmen");
		btnStartIncome.setMnemonic('E');
		panStartButtons.add(btnStartIncome);
		btnStartOutgo = new JButton("Ausgaben");
		btnStartOutgo.setMnemonic('A');
		panStartButtons.add(btnStartOutgo);
		btnStartOverview = new JButton("\u00DCbersicht");
		btnStartOverview.setMnemonic('Ü');
		panStartButtons.add(btnStartOverview);
		btnStartSettings = new JButton("Einstellungen");
		btnStartSettings.setMnemonic('s');
		panStartButtons.add(btnStartSettings);
		lblStartFooter = new JLabel("by hannes eilers (C) 2013 - www.hanneseilers.de | Icon from Visual Pharm http://icons8.com/ (CC BY-ND 3.0)");
		lblStartFooter.setHorizontalAlignment(SwingConstants.LEFT);
		tabStart.add(lblStartFooter, "2, 8, 3, 1");
		
		tabIncome = new JPanel();
		tabbedPane.addTab("Einnahmen", null, tabIncome, null);
		tabIncome.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
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
		
		lblIncomeHeader = new JLabel("Einnahmen");
		lblIncomeHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblIncomeHeader.setFont(new Font("Dialog", Font.BOLD, 14));
		tabIncome.add(lblIncomeHeader, "2, 2");
		
		lstIncomeModel = new DefaultListModel<Article>();
		lstIncome = new JList<Article>(lstIncomeModel);
		lstIncome.setFont(new Font("Monospaced", Font.BOLD, 12));
		lstIncome.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabIncome.add( new JScrollPane(lstIncome), "2, 4, fill, fill");
		
		panIncomeButtons = new JPanel();
		tabIncome.add(panIncomeButtons, "4, 4, center, top");
		panIncomeButtons.setLayout(new GridLayout(0, 1, 0, 2));
		
		btnIncomeAdd = new JButton("Artikel hinzufügen");
		btnIncomeAdd.setMnemonic('h');
		panIncomeButtons.add(btnIncomeAdd);
		
		btnIncomeDelete = new JButton("Artikel löschen");
		btnIncomeDelete.setMnemonic('l');
		panIncomeButtons.add(btnIncomeDelete);
		
		btnIncomeEdit = new JButton("Artikel bearbeiten");
		btnIncomeEdit.setMnemonic('b');
		panIncomeButtons.add(btnIncomeEdit);
		
		btnIncomeRefresh = new JButton("Liste aktualisieren");
		btnIncomeRefresh.setMnemonic('a');
		panIncomeButtons.add(btnIncomeRefresh);
		
		panIncomeFilter = new JPanel();
		tabIncome.add(panIncomeFilter, "2, 6, 3, 1, fill, fill");
		panIncomeFilter.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		rdbtnIncomeFilterCategory = new JRadioButton("Filtern nach Kategorie:");
		btngrpIncomeFilter.add(rdbtnIncomeFilterCategory);
		panIncomeFilter.add(rdbtnIncomeFilterCategory, "2, 1");
		
		cmbIncomeFilterCategory = new JComboBox<Category>();
		panIncomeFilter.add(cmbIncomeFilterCategory, "4, 1, fill, default");
		
		rdbtnIncomeFilterWord = new JRadioButton("Filtern nach Suchwort:");
		btngrpIncomeFilter.add(rdbtnIncomeFilterWord);
		rdbtnIncomeFilterWord.setSelected(true);
		panIncomeFilter.add(rdbtnIncomeFilterWord, "6, 1");
		
		txtIncomeFilter = new JTextField();
		txtIncomeFilter.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtIncomeFilter.selectAll();
			}
		});
		panIncomeFilter.add(txtIncomeFilter, "8, 1, fill, default");
		txtIncomeFilter.setColumns(10);
		
		btnIncomeFilter = new JButton("Filtern");
		btnIncomeFilter.setMnemonic('F');
		panIncomeFilter.add(btnIncomeFilter, "10, 1");
		
		tabOutgo = new JPanel();
		tabbedPane.addTab("Ausgaben", null, tabOutgo, null);
		tabOutgo.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
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
		
		lbOutgoHeader = new JLabel("Ausgaben");
		lbOutgoHeader.setFont(new Font("Dialog", Font.BOLD, 14));
		lbOutgoHeader.setHorizontalAlignment(SwingConstants.CENTER);
		tabOutgo.add(lbOutgoHeader, "2, 2");
		
		lstOutgoModel = new DefaultListModel<Article>();
		lstOutgo = new JList<Article>(lstOutgoModel);
		lstOutgo.setFont(new Font("Monospaced", Font.BOLD, 12));
		lstOutgo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabOutgo.add(new JScrollPane(lstOutgo), "2, 4, fill, fill");
		
		panOutgoButtons = new JPanel();
		tabOutgo.add(panOutgoButtons, "4, 4, center, top");
		panOutgoButtons.setLayout(new GridLayout(0, 1, 0, 2));
		
		btnOutgoAdd = new JButton("Artikel hinzufügen");
		btnOutgoAdd.setMnemonic('h');
		panOutgoButtons.add(btnOutgoAdd);
		
		btnOutgoDelete = new JButton("Artikel löschen");
		btnOutgoDelete.setMnemonic('l');
		panOutgoButtons.add(btnOutgoDelete);
		
		btnOutgoEdit = new JButton("Artikel bearbeiten");
		btnOutgoEdit.setMnemonic('b');
		panOutgoButtons.add(btnOutgoEdit);
		
		btnOutgoRefresh = new JButton("Liste aktualisieren");
		btnOutgoRefresh.setMnemonic('a');
		panOutgoButtons.add(btnOutgoRefresh);
		
		panOutgoFilter = new JPanel();
		tabOutgo.add(panOutgoFilter, "2, 6, 3, 1, fill, fill");
		panOutgoFilter.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		rdbtnOutgoFilterCategory = new JRadioButton("Filtern nach Kategorie:");
		btngrpOutgoFilter.add(rdbtnOutgoFilterCategory);
		panOutgoFilter.add(rdbtnOutgoFilterCategory, "2, 1");
		
		cmbOutgoFilterCategory = new JComboBox<Category>();
		panOutgoFilter.add(cmbOutgoFilterCategory, "4, 1, fill, default");
		
		rdbtnOutgoFilterWord = new JRadioButton("Filtern nach Suchwort:");
		btngrpOutgoFilter.add(rdbtnOutgoFilterWord);
		rdbtnOutgoFilterWord.setSelected(true);
		panOutgoFilter.add(rdbtnOutgoFilterWord, "6, 1");
		
		txtOutgoFilter = new JTextField();
		txtOutgoFilter.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtOutgoFilter.selectAll();
			}
		});
		panOutgoFilter.add(txtOutgoFilter, "8, 1, fill, default");
		txtOutgoFilter.setColumns(10);
		
		btnOutgoFilter = new JButton("Filtern");
		btnOutgoFilter.setMnemonic('F');
		panOutgoFilter.add(btnOutgoFilter, "10, 1");
		
		
		tabOverview = new JPanel();
		tabbedPane.addTab("\u00DCbersicht", null, tabOverview, null);
		tabOverview.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(73dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(40dlu;min)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(30dlu;default)"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		lblOverview4 = new JLabel("Kategorie:");
		lblOverview4.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverview4, "2, 2, right, default");
		cmbOverviewCategory = new JComboBox<Category>();
		tabOverview.add(cmbOverviewCategory, "4, 2, 7, 1, fill, default");
		lblStichwort = new JLabel("Suchwort:");
		lblStichwort.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblStichwort, "2, 4, right, default");
		txtOverviewSearch = new JTextField();
		txtOverviewSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtOverviewSearch.selectAll();
			}
		});
		tabOverview.add(txtOverviewSearch, "4, 4, 7, 1, fill, default");
		txtOverviewSearch.setColumns(10);
		
		rdbOverviewTimePeriod = new JRadioButton("Zeitraum:");
		rdbgpOverviewTime.add(rdbOverviewTimePeriod);
		tabOverview.add(rdbOverviewTimePeriod, "2, 6");
		
		lblOverview6 = new JLabel("bis");
		lblOverview6.setHorizontalAlignment(SwingConstants.CENTER);
		tabOverview.add(lblOverview6, "6, 6");
		
		
		rdbOverviewTimeDays = new JRadioButton("letzten");
		rdbOverviewTimeDays.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				if( rdbOverviewTimeDays.isSelected() ){
					long curDay = (System.currentTimeMillis()/Article.timestampDay) * Article.timestampDay;
					if( dateChooserOverviewTimePeriodFrom != null
							&& dateChooserOverviewTimePeriodTill != null){
						dateChooserOverviewTimePeriodFrom.setDate( new Date(curDay) );
						dateChooserOverviewTimePeriodTill.setDate( new Date(curDay) );
						
					}
				}
				else{
					txtOverviewDays.setText("-1");
				}
				
			}
		});
		rdbOverviewTimeDays.setSelected(true);
		rdbgpOverviewTime.add(rdbOverviewTimeDays);
		tabOverview.add(rdbOverviewTimeDays, "2, 8");
		
		txtOverviewDays = new JTextField();
		txtOverviewDays.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtOverviewDays.selectAll();
			}
		});
		txtOverviewDays.setHorizontalAlignment(SwingConstants.CENTER);
		txtOverviewDays.setText("-1");
		tabOverview.add(txtOverviewDays, "4, 8, fill, center");
		txtOverviewDays.setColumns(10);
		
		lblOverview7 = new JLabel("Tage");
		tabOverview.add(lblOverview7, "6, 8");
		
		btnOverviewTimeDaysReset = new JButton("alle Tage");
		btnOverviewTimeDaysReset.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnOverviewTimeDaysReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtOverviewDays.setText("-1");
			}
		});
		btnOverviewTimeDaysReset.setMnemonic('a');
		tabOverview.add(btnOverviewTimeDaysReset, "8, 8, 3, 1");
		btnOverviewFilter = new JButton("Filtern");
		btnOverviewFilter.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnOverviewFilter.setMnemonic('F');
		tabOverview.add(btnOverviewFilter, "8, 10, 3, 1");
		lblOverview1 = new JLabel("Einnahmen:");
		lblOverview1.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverview1, "2, 12, 3, 1");
		lblOverviewIncomeTotal = new JLabel("0,00 EUR");
		lblOverviewIncomeTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverviewIncomeTotal, "6, 12, 3, 1");
		lblOverview2 = new JLabel("Ausgaben:");
		lblOverview2.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverview2, "4, 13");
		lblOverviewOutgoTotal = new JLabel("0,00 EUR");
		lblOverviewOutgoTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverviewOutgoTotal, "6, 13, 3, 1");
		lblOverview3 = new JLabel("Bilanz:");
		lblOverview3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOverview3.setFont(new Font("Tahoma", Font.BOLD, 11));
		tabOverview.add(lblOverview3, "4, 15");
		lblOverviewTotal = new JLabel("0,00 EUR");
		lblOverviewTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOverviewTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tabOverview.add(lblOverviewTotal, "6, 15, 3, 1");
		
		lblOverview5 = new JLabel("Trend:");
		tabOverview.add(lblOverview5, "10, 15, right, default");
		
		lstOverviewModel = new DefaultListModel<Article>();
		
		lblOverviewTrend = new JLabel("Nicht genug Daten");
		tabOverview.add(lblOverviewTrend, "12, 15, left, default");
		lstOverviewArticles = new JList<Article>(lstOverviewModel);
		lstOverviewArticles.setEnabled(false);
		lstOverviewArticles.setFont(new Font("Monospaced", Font.BOLD, 12));
		lstOverviewArticles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabOverview.add(new JScrollPane( lstOverviewArticles ), "2, 17, 11, 1, fill, fill");
		
		long curDay = (System.currentTimeMillis()/Article.timestampDay) * Article.timestampDay;
		dateChooserOverviewTimePeriodFrom = new JDateChooser( new Date(curDay) );
		dateChooserOverviewTimePeriodFrom.getDateEditor().getUiComponent().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				((JTextField) e.getSource()).selectAll();
			}
		});
		dateChooserOverviewTimePeriodTill = new JDateChooser( new Date(curDay) );		
		dateChooserOverviewTimePeriodTill.getDateEditor().getUiComponent().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				((JTextField) e.getSource()).selectAll();
			}
		});
		tabOverview.add(dateChooserOverviewTimePeriodFrom, "4, 6");
		tabOverview.add(dateChooserOverviewTimePeriodTill, "8, 6, 3, 1");
		
		tabSettings = new JPanel();
		tabbedPane.addTab("Einstellungen", null, tabSettings, null);
		tabSettings.setLayout(new FormLayout(new ColumnSpec[] {
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
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		panSettingsCategories = new JPanel();
		panSettingsCategories.setBorder(new TitledBorder(null, "Kategorien", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabSettings.add(panSettingsCategories, "2, 2, fill, fill");
		panSettingsCategories.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		lblSettingsCatergory = new JLabel("Kategorien:");
		panSettingsCategories.add(lblSettingsCatergory, "2, 2");
		lblSettingsCatergory.setHorizontalAlignment(SwingConstants.RIGHT);
		cmbSettingsCategories = new JComboBox<Category>();
		panSettingsCategories.add(cmbSettingsCategories, "4, 2");
		
		lblSettingsCategoryName = new JLabel("Kategoriename:");
		panSettingsCategories.add(lblSettingsCategoryName, "2, 4");
		txtSettingsCatergoyName = new JTextField();
		panSettingsCategories.add(txtSettingsCatergoyName, "4, 4");
		txtSettingsCatergoyName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtSettingsCatergoyName.selectAll();
			}
		});
		txtSettingsCatergoyName.setColumns(10);
		
		panSettingsButtons = new JPanel();
		panSettingsCategories.add(panSettingsButtons, "4, 6");
		btnSettingsCategoryAdd = new JButton("Kategorie hinzuf\u00FCgen");
		btnSettingsCategoryAdd.setMnemonic('h');
		panSettingsButtons.add(btnSettingsCategoryAdd);
		btnSettingsRemoveCategory = new JButton("Kategorie l\u00F6schen");
		btnSettingsRemoveCategory.setMnemonic('l');
		panSettingsButtons.add(btnSettingsRemoveCategory);
		btnSettingsCategoryRename = new JButton("Kategorie umbennen");
		btnSettingsCategoryRename.setMnemonic('u');
		panSettingsButtons.add(btnSettingsCategoryRename);
		
		panSettingsViewOptions = new JPanel();
		panSettingsViewOptions.setBorder(new TitledBorder(null, "Ansichtsoptionen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabSettings.add(panSettingsViewOptions, "2, 4, fill, fill");
		panSettingsViewOptions.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("25dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("25dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblSettingsView1 = new JLabel("Länge Artikelname:");
		lblSettingsView1.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsViewOptions.add(lblSettingsView1, "2, 2, right, default");
		
		txtSettingsViewArticleNameLength = new JTextField();
		txtSettingsViewArticleNameLength.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					int value = Integer.parseInt(txtSettingsViewArticleNameLength.getText());
					Loader.config.setProperty( ConfigurationValues.ARTICLE_NAME_LENGTH.getKey(), value );
					Article.updateFormatterString();
				} catch( NumberFormatException err ){}
			}
		});
		panSettingsViewOptions.add(txtSettingsViewArticleNameLength, "4, 2, fill, default");
		txtSettingsViewArticleNameLength.setColumns(10);
		
		lblSettingsView4 = new JLabel("Vorkammstellen:");
		lblSettingsView4.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsViewOptions.add(lblSettingsView4, "8, 2, right, default");
		
		txtSettingsViewPreDecimalPlaces = new JTextField();
		txtSettingsViewPreDecimalPlaces.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try{
					int value = Integer.parseInt(txtSettingsViewPreDecimalPlaces.getText());
					Loader.config.setProperty( ConfigurationValues.NUMBERS_PRE_DECIMAL_PLACES.getKey(), value );
					Article.updateFormatterString();
				} catch( NumberFormatException err ){}
			}
		});
		panSettingsViewOptions.add(txtSettingsViewPreDecimalPlaces, "10, 2, fill, default");
		txtSettingsViewPreDecimalPlaces.setColumns(10);
		
		lblSettingsView2 = new JLabel("Länge Kategoriename:");
		lblSettingsView2.setVerticalAlignment(SwingConstants.CENTER);
		lblSettingsView2.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsViewOptions.add(lblSettingsView2, "2, 4, right, default");
		
		txtSettingsViewCategoryNameLength = new JTextField();
		txtSettingsViewCategoryNameLength.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					int value = Integer.parseInt(txtSettingsViewCategoryNameLength.getText());
					Loader.config.setProperty( ConfigurationValues.CATEGROY_NAME_LENGTH.getKey(), value );
					Article.updateFormatterString();
				} catch( NumberFormatException err ){}
			}
		});
		panSettingsViewOptions.add(txtSettingsViewCategoryNameLength, "4, 4, fill, default");
		txtSettingsViewCategoryNameLength.setColumns(10);
		
		lblSettingsView5 = new JLabel("Nachkommastellen:");
		lblSettingsView5.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsViewOptions.add(lblSettingsView5, "8, 4, right, default");
		
		txtSettingsViewPostDecimalPlaces = new JTextField();
		txtSettingsViewPostDecimalPlaces.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try{
					int value = Integer.parseInt(txtSettingsViewPostDecimalPlaces.getText());
					Loader.config.setProperty( ConfigurationValues.NUMBERS_POST_DECIMAL_PLACES.getKey(), value );
					Article.updateFormatterString();
				} catch( NumberFormatException err ){}
			}
		});
		panSettingsViewOptions.add(txtSettingsViewPostDecimalPlaces, "10, 4, fill, default");
		txtSettingsViewPostDecimalPlaces.setColumns(10);
		
		lblSettingsView3 = new JLabel("Währung:");
		lblSettingsView3.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsViewOptions.add(lblSettingsView3, "2, 6, right, default");
		
		txtSettingsViewCurrencySymbol = new JTextField();
		txtSettingsViewCurrencySymbol.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String value = txtSettingsViewCurrencySymbol.getText();
				Loader.config.setProperty( ConfigurationValues.ARTICLE_CURRENCY_SYMBOL.getKey(), value );
				Article.updateFormatterString();
			}
		});
		panSettingsViewOptions.add(txtSettingsViewCurrencySymbol, "4, 6, fill, default");
		txtSettingsViewCurrencySymbol.setColumns(10);
		
		lblSettingsView6 = new JLabel("Max. angezeigte Artikel:");
		lblSettingsView6.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsViewOptions.add(lblSettingsView6, "8, 6, right, default");
		
		txtSettingsViewArticleMaxRows = new JTextField();
		txtSettingsViewArticleMaxRows.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try{
					int value = Integer.parseInt(txtSettingsViewArticleMaxRows.getText());
					Loader.config.setProperty( ConfigurationValues.ARTICLE_MAX_ROWS.getKey(), value );
					Article.updateFormatterString();
				} catch( NumberFormatException err ){}
			}
		});
		panSettingsViewOptions.add(txtSettingsViewArticleMaxRows, "10, 6, fill, default");
		txtSettingsViewArticleMaxRows.setColumns(10);
		
		panSettingsAppInfo = new JPanel();
		panSettingsAppInfo.setBorder(new TitledBorder(null, "Programminformationen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabSettings.add(panSettingsAppInfo, "2, 6, fill, fill");
		panSettingsAppInfo.setLayout(new FormLayout(new ColumnSpec[] {
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
		
		lblSettings1 = new JLabel("Programmversion:");
		panSettingsAppInfo.add(lblSettings1, "2, 2");
		lblSettings1.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblSettingsApplicationVersion = new JLabel("0");
		panSettingsAppInfo.add(lblSettingsApplicationVersion, "4, 2, fill, default");
		
		btnSettingsAppUpdate = new JButton("Nach Updates suchen");
		btnSettingsAppUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String buttonText = btnSettingsAppUpdate.getText();
				// check if to update or download
				if( buttonText.contains("suchen") ){
					
					// check for update
					btnSettingsAppUpdate.setEnabled(false);
					if( Loader.checkForUpdate().size() > 0 ){
						// Update button
						btnSettingsAppUpdate.setText( "Updates herunterladen" );
						btnSettingsAppUpdate.setIcon(new ImageIcon(MainFrame.class.getResource("/de/hanneseilers/gui/icon/download_16.png")));
					} else{
						lblSettingsAppUpdateStatus.setText("Keine Updates verfügbar.");
					}
					btnSettingsAppUpdate.setEnabled(true);
					
				} else{
					
					// Download update
					if( !Loader.restartApplication() ){
						btnSettingsAppUpdate.setEnabled(false);
						lblSettingsAppUpdateStatus.setText("Manueller Programmneustart erforderlich!");
					}
					
				}
			}
		});
		btnSettingsAppUpdate.setIcon(new ImageIcon(MainFrame.class.getResource("/de/hanneseilers/gui/icon/update_16.png")));
		panSettingsAppInfo.add(btnSettingsAppUpdate, "6, 2, 1, 3");
		
		lblSettings2 = new JLabel("Letztes Update:");
		lblSettings2.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsAppInfo.add(lblSettings2, "2, 4");
		
		lblSettingsAppLastUpdate = new JLabel("no update");
		panSettingsAppInfo.add(lblSettingsAppLastUpdate, "4, 4, fill, default");
		
		lblSettingsAppUpdateStatus = new JLabel("");
		lblSettingsAppUpdateStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		panSettingsAppInfo.add(lblSettingsAppUpdateStatus, "2, 6, 5, 1");
	}
	
}
