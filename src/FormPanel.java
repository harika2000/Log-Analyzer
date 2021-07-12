import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;

import javax.swing.JFileChooser;

/**
 * Maintains all the components of the form.
 * @author Venkata Harika
 *
 */
public class FormPanel extends JPanel implements ActionListener, MouseListener{
	
	private String fileNames;
	private JLabel filePathLabel;
	private JLabel keywordLabel;
	private JTextField filePathField;
	private JTextField keywordField;
	private JButton btn;
	private FormListener formListener;
	private JFileChooser fileChooser;
	private JLabel startLineLabel ,endLineLabel,typeLabel;
	private JTextField startLineField ,endLineField;
	private ProgressBar progressBar;
	private JButton saveBtn;
	private TextListener textListener;
	private JComboBox<String> dropDownFeatures;
	private JLabel dropDownLabel;
	private ParentTabbedTextPane p;
	private JTextField keywordPresentField, keywordAbsentField;
	private JLabel keywordPresentLabel, keywordAbsentLabel;
	private JComboBox<String> dropDownKeywords;
	private JButton resetBtn;
	private MainFrame m;
	private JButton backBtn;
	private JButton nextBtn;
	private JButton helpBtn;
	private AboutDialog aboutDesc;
	private FeaturesDesc featuresDesc;
/**
 * Initializes and sets the formats of all the components of the form	
 */
	FormPanel(ParentTabbedTextPane p,MainFrame m){
		
		this.p=p;
		this.m=m;
		filePathLabel = new JLabel("Select file path(s):");
		keywordLabel = new JLabel("Enter keyword(s):");
		filePathField = new JTextField(15);
		keywordField= new JTextField(10);
		startLineField= new JTextField(10);
		endLineField =new JTextField(10);
		startLineLabel= new JLabel("Start line:");
		endLineLabel= new JLabel("End line:");
		btn = new JButton("Search");
		fileChooser=new JFileChooser();
		typeLabel=new JLabel("Select file type: ");
		saveBtn=new JButton("Save");
		progressBar = new ProgressBar();
		dropDownFeatures= new JComboBox<String>();
		dropDownLabel = new JLabel("Select a feature:");
		dropDownKeywords=new JComboBox<String>();
		keywordPresentField= new JTextField(5);
		keywordAbsentField= new JTextField(5);
		keywordPresentLabel= new JLabel();
		keywordAbsentLabel= new JLabel();
		resetBtn= new JButton("Reset");
		URL url = App.class.getResource("left-arrow.png");
		 ImageIcon icon = new ImageIcon(url);
		Image img = ((ImageIcon) icon).getImage() ;
		Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;  
 	    icon = new ImageIcon(newimg );
		backBtn= new JButton(icon);
		url = App.class.getResource("right-arrow.png");
		icon = new ImageIcon(url);
		img = ((ImageIcon) icon).getImage() ;  
		newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;  
 	    icon = new ImageIcon( newimg );
		nextBtn= new JButton(icon);
		helpBtn = new JButton("About");
		aboutDesc = new AboutDialog(p,false);
		featuresDesc = new FeaturesDesc(p, false);
		featuresDesc.setVisible(true);
		
		//setting font
		filePathLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		filePathField.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		keywordLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		keywordField.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		startLineLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		startLineField.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		endLineLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		endLineField.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		dropDownFeatures.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		dropDownLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		typeLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		btn.setFont(new Font("Sans Serif", Font.BOLD, 15));
		saveBtn.setFont(new Font("Sans Serif", Font.BOLD, 15));
		dropDownKeywords.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		keywordPresentLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		keywordAbsentLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
		keywordPresentField.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		keywordAbsentField.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		resetBtn.setFont(new Font("Sans Serif", Font.BOLD, 15));
		backBtn.setFont(new Font("Sans Serif", Font.BOLD, 15));
		nextBtn.setFont(new Font("Sans Serif", Font.BOLD, 15));
		helpBtn.setFont(new Font("Sans Serif", Font.BOLD, 15));
		
		
		//setting up featuresDesc
		String message="A normal search is performed " + System.lineSeparator()+"on the whole log file"  + System.lineSeparator()+"based on the filters selected.";
		featuresDesc.setText(message);
		
		//text prompt
		TextPrompt textPromptKeyword = new TextPrompt("Eg: from,select", keywordField);
		TextPrompt textPromptstart = new TextPrompt("Eg: 50", startLineField);
		TextPrompt textPromptend = new TextPrompt("Eg: 100", endLineField);
		TextPrompt textPromptPresent = new TextPrompt("Contains", keywordPresentField);
		TextPrompt textPromptAbsent = new TextPrompt("Exclude", keywordAbsentField);
		
		//setting up Features Combo box
		
		DefaultComboBoxModel<String> dropDownFeaturesModel = new DefaultComboBoxModel<String>();
		dropDownFeatures.setBackground(Color.white);
		dropDownFeaturesModel.addElement("Remove Clutter");
		dropDownFeaturesModel.addElement("Search");	
		dropDownFeaturesModel.addElement("Show Packages");	
		dropDownFeaturesModel.addElement("SQL Queries");
		dropDownFeatures.setModel(dropDownFeaturesModel);
		dropDownFeatures.setSelectedIndex(1);		
		dropDownFeatures.setRenderer(new ComboBoxRenderer("","features"));
		
		//setting up keywords combo box
		DefaultComboBoxModel<String> dropDownKeywordsModel = new DefaultComboBoxModel<String>();		
		dropDownKeywords.setBackground(Color.white);
		dropDownKeywordsModel.addElement("All keyword(s) present");	
		dropDownKeywordsModel.addElement("Either one present");
		dropDownKeywordsModel.addElement("No Keywords");
		dropDownKeywordsModel.addElement("Contains, exclude");
		dropDownKeywords.setModel(dropDownKeywordsModel);
		dropDownKeywords.setSelectedIndex(-1);
		dropDownKeywords.setRenderer(new ComboBoxRenderer("Select search type","keywords"));
			
		//set up textfields size and dropdown size
		Dimension d= dropDownKeywords.getPreferredSize();
		keywordField.setPreferredSize(new Dimension(keywordField.getWidth(),d.height));
		filePathField.setPreferredSize(keywordField.getPreferredSize());
		startLineField.setPreferredSize(new Dimension(startLineField.getWidth(),d.height));
		endLineField.setPreferredSize(startLineField.getPreferredSize());
		keywordPresentField.setPreferredSize(new Dimension(keywordPresentField.getWidth(),d.height));
		keywordAbsentField.setPreferredSize(keywordPresentField.getPreferredSize());
		
		//disabling
		typeLabel.setEnabled(false);
		keywordPresentField.setVisible(false);
		keywordAbsentField.setVisible(false); 
		keywordField.setVisible(false);
		keywordLabel.setVisible(false);
		backBtn.setEnabled(false);
		nextBtn.setEnabled(false);
		saveBtn.setEnabled(false);
		
		//set up btn
		btn.setBorder(BorderFactory.createEtchedBorder(0));
		Dimension dim = new Dimension();
		dim.width=100;
		dim.height=30;
		btn.setPreferredSize(dim);
				
		//set up save btn
		saveBtn.setBackground(SystemColor.textHighlight);
		saveBtn.setBorder(BorderFactory.createEtchedBorder(getForeground(), getBackground()));
		dim = new Dimension();
		dim.width=70;
		dim.height=30;
		saveBtn.setPreferredSize(dim);
		
		//set up resetBtn
		resetBtn.setBorder(BorderFactory.createEtchedBorder(0));
		dim = new Dimension();
		dim.width=100;
		dim.height=30;
		resetBtn.setPreferredSize(dim);
		
		//set up dropDownFeatures
		dim = new Dimension();
		dim.width=130;
		dim.height=27;
		dropDownFeatures.setPreferredSize(dim);
			
		
		//Tooltip for all
		filePathField.setToolTipText("Click on the text field to browse for a file");
		keywordField.setToolTipText("Enter keywords separated by a comma or leave it blank if no specification");
		startLineField.setToolTipText("Enter the starting line in the range or leave it blank if no specification");
		endLineField.setToolTipText("Enter the ending line in the range or leave it blank if no specification");
		saveBtn.setToolTipText("Save displayed output");
		btn.setToolTipText("Display output");
		dropDownKeywords.setToolTipText("Select a search type");
		keywordPresentField.setToolTipText("Keywords that should be present. Enter keywords separated by a comma or leave it blank if no specification");
		keywordAbsentField.setToolTipText("Keywords that should not be present. Enter keywords separated by a comma or leave it blank if no specification");
		resetBtn.setToolTipText("Reset to default values");
		backBtn.setToolTipText("Go back to previously entered details");
		nextBtn.setToolTipText("Go forward to next details entered");
		helpBtn.setToolTipText("About the application");	
		
		//defining border of entire FormPanel
		Border innerBorder=BorderFactory.createEtchedBorder();
		Border outerBorder=BorderFactory.createEmptyBorder(10,10,0,10);	
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));	
		setLayout(new GridBagLayout());
		
		settingLayout();
		allListeners();
		
	}
	
	public void settingLayout()
	{
		/* FIRST ROW */
		GridBagConstraints gc1=new GridBagConstraints();
		gc1.fill=GridBagConstraints.NONE;
		gc1.weightx=1;
		gc1.weighty=1;
		
		gc1.gridx=0;
		gc1.gridy=0;
		gc1.insets=new Insets(5,5,5,0);
		gc1.anchor =GridBagConstraints.LINE_START;
		add(dropDownLabel,gc1);	
		
		gc1.gridx=0;
		gc1.gridy=0;
		gc1.insets=new Insets(5,130,5,0);
		gc1.anchor =GridBagConstraints.LINE_START;
		add(dropDownFeatures,gc1);
		
		gc1.weightx=1;
		gc1.weighty=1;
		gc1.gridx=0;
		gc1.gridy=1;
		gc1.gridheight=2;
		gc1.insets=new Insets(5,5,5,0);
		gc1.anchor =GridBagConstraints.LINE_START;
		add(featuresDesc,gc1);
		
		gc1.gridheight=1;
		gc1.insets=new Insets(5,0,0,0);
		gc1.gridx=1;
		gc1.gridy=0;
		gc1.anchor =GridBagConstraints.LINE_START;
		add(filePathLabel,gc1);
		
		gc1.gridx=1;
		gc1.gridy=0;
		gc1.insets=new Insets(5,140,0,0);
		gc1.anchor =GridBagConstraints.LINE_START;
		add(filePathField,gc1);
		
		gc1.gridx=2;
		gc1.gridy=0;
		gc1.insets=new Insets(5,0,0,5);
		gc1.anchor= GridBagConstraints.FIRST_LINE_END;
		add(helpBtn,gc1);
		
		gc1.gridx=2;
		gc1.gridy=0;
		gc1.insets=new Insets(5,0,0,90);
		gc1.anchor= GridBagConstraints.FIRST_LINE_END;
		add(nextBtn,gc1);
		
		gc1.gridx=2;
		gc1.gridy=0;
		gc1.insets=new Insets(5,0,0,150);
		gc1.anchor= GridBagConstraints.FIRST_LINE_END;
		add(backBtn,gc1);
		
			
		/* SECOND ROW */
		GridBagConstraints gc3=new GridBagConstraints();
		gc3.fill=GridBagConstraints.NONE;
		gc3.weightx=1.4;
		gc3.weighty=1.4;
		gc3.gridheight=1;
		gc3.gridx=1;
		gc3.gridy=1;
		gc3.insets= new Insets(5,0,0,0);
		gc3.anchor =GridBagConstraints.LINE_START;
		add(keywordLabel,gc3);
		
		gc3.gridx=1;
		gc3.gridy=1;
		gc3.insets=new Insets(0,140,0,0);
		gc3.anchor =GridBagConstraints.LINE_START;//place it in corner where the first line would start 
		add(dropDownKeywords,gc3);		
				
		gc3.gridx=1;
		gc3.gridy=1;
		gc3.insets=new Insets(0,340,0,0);		
		gc3.anchor =GridBagConstraints.LINE_START;//place it in corner where the first line would start 
		add(keywordField,gc3);		
		
		gc3.gridx=1;
		gc3.gridy=1;
		gc3.insets=new Insets(0,340,0,0);
		gc3.anchor =GridBagConstraints.LINE_START;//place it in corner where the first line would start 
		add(keywordPresentField,gc3);
		
		gc3.gridx=1;
		gc3.gridy=1;
		gc3.insets=new Insets(0,425,0,0);
		gc3.anchor =GridBagConstraints.LINE_START;//place it in corner where the first line would start 
		add(keywordAbsentField,gc3);
				
		/* THIRD ROW */
		GridBagConstraints gc4=new GridBagConstraints();
		gc4.fill=GridBagConstraints.NONE;
		gc4.weightx=1;
		gc4.weighty=1;
		
		gc4.gridx=1;
		gc4.gridy=2;
		gc4.insets= new Insets(0,0,0,0);
		gc4.anchor =GridBagConstraints.LINE_START;
		add(startLineLabel,gc4);
		
		gc4.gridx=1;
		gc4.gridy=2;
		gc4.insets=new Insets(0,140,0,0);
		gc4.anchor = GridBagConstraints.LINE_START;
		add(startLineField,gc4);	
		
		/* FOURTH ROW */		
		GridBagConstraints gc5=new GridBagConstraints();
		gc5.fill=GridBagConstraints.NONE;
		gc5.weightx=1;
		gc5.weighty=1;
		
		gc5.gridx=1;
		gc5.gridy=3;
		gc5.insets= new Insets(0,0,5,0);
		gc5.anchor =GridBagConstraints.LINE_START;
		add(endLineLabel,gc5);
		
		gc5.gridx=1;
		gc5.gridy=3;
		gc5.insets=new Insets(0,140,5,0);
		gc5.anchor = GridBagConstraints.LINE_START;
		add(endLineField,gc5);	
		
		GridBagConstraints gc6=new GridBagConstraints();
		gc6.fill=GridBagConstraints.NONE;
		
		gc6.gridx=2;
		gc6.gridy=3;
		gc6.anchor = GridBagConstraints.LINE_END;
		gc6.insets=new Insets(0,0,5,5);
		add(saveBtn,gc6);
		
		gc6.weightx=1;
		gc6.weighty=1.0;
		gc6.insets=new Insets(0,0,5,90);		
		gc6.gridx=2;
		gc6.gridy=3;
		gc6.anchor = GridBagConstraints.LINE_END;
		add(btn,gc6);
		
		gc6.weightx=1;
		gc6.weighty=1.0;
		gc6.insets=new Insets(0,0,5,200);		
		gc6.gridx=2;
		gc6.gridy=3;
		gc6.anchor = GridBagConstraints.LINE_END;
		add(resetBtn,gc6);
		
		gc6.gridx=2;
		gc6.gridy=3;
		gc6.insets=new Insets(0,0,5,400);
		gc6.anchor = GridBagConstraints.LINE_END;
		add(progressBar,gc6);
		
	}
	
	public void allListeners()
	{
		//listener for help button to open about
		helpBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDesc.setLocationRelativeTo(p);
				aboutDesc.setVisible(true);
			}			
		});
		
		//Adding mouse listener for entering the path and action listener for responding to button
		filePathField.addMouseListener(this);
		btn.addActionListener(this);
		
		//listener for setting display according to feature selected
		dropDownFeatures.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String feature=(String) dropDownFeatures.getSelectedItem();
				if(feature.equals("Search"))
				{
					typeLabel.setEnabled(false);
					keywordLabel.setEnabled(true);
					keywordField.setEnabled(true);
					dropDownKeywords.setEnabled(true);
					startLineField.setEnabled(true);
					startLineLabel.setEnabled(true);
					endLineField.setEnabled(true);
					endLineLabel.setEnabled(true);
					keywordPresentField.setEnabled(true);
					keywordAbsentField.setEnabled(true);
				}
				if(feature.equals("Remove Clutter"))
				{
					typeLabel.setEnabled(true);
					keywordLabel.setEnabled(true);
					keywordField.setEnabled(true);
					dropDownKeywords.setEnabled(true);
					startLineField.setEnabled(true);
					startLineLabel.setEnabled(true);
					endLineField.setEnabled(true);
					endLineLabel.setEnabled(true);
					keywordPresentField.setEnabled(true);
					keywordAbsentField.setEnabled(true);
				}
				if(feature.equals("SQL Queries"))
				{
					typeLabel.setEnabled(false);
					keywordLabel.setEnabled(true);
					keywordField.setEnabled(true);
					dropDownKeywords.setEnabled(true);
					startLineField.setEnabled(true);
					startLineLabel.setEnabled(true);
					endLineField.setEnabled(true);
					endLineLabel.setEnabled(true);
					keywordPresentField.setEnabled(true);
					keywordAbsentField.setEnabled(true);
				}
				if(feature.equals("Show Packages"))
				{
					typeLabel.setEnabled(false);
					keywordLabel.setEnabled(false);
					keywordField.setEnabled(false);
					dropDownKeywords.setEnabled(false);
					startLineField.setEnabled(false);
					startLineLabel.setEnabled(false);
					endLineField.setEnabled(false);
					endLineLabel.setEnabled(false);
					keywordPresentField.setEnabled(false);
					keywordAbsentField.setEnabled(false);
				}				
			}
		});
		
		//adding anon classes to respond to save button.
		//selects the file to be chosen and calls textListenenr to write the textArea into the chosen file
		saveBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text","log");
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle("Specify a file to save");
				int userSelection = fileChooser.showSaveDialog(null);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File file = fileChooser.getSelectedFile();
				         file = new File(file.getAbsolutePath());
				    if(textListener!=null)
				    textListener.saveToFile(file);
				}
				
			}	
		});
			
		//action listener for reset button to rest to default values
		resetBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dropDownFeatures.setSelectedIndex(1);
				filePathField.setText("");
				dropDownKeywords.setSelectedIndex(-1);
				keywordField.setText("");
				keywordField.setVisible(false);
				keywordAbsentField.setText("");
				keywordAbsentField.setVisible(false);
				keywordPresentField.setText("");
				keywordPresentField.setVisible(false);
				startLineField.setText("");
				endLineField.setText("");
				
			}			
		});		
		
		//to change colour of Search button when clicked
		btn.addMouseListener(new MouseListener () {
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btn.setBackground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btn.setBackground(new ColorUIResource(238,238,238));
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}	
		});	

		//action listener for setting display according to search type selectede
		dropDownKeywords.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(dropDownKeywords.getSelectedIndex()==-1) return;
					String type=(String) dropDownKeywords.getSelectedItem();
					if(type.equals("No Keywords"))
					{
						keywordField.setVisible(false);
						keywordPresentField.setVisible(false);
						keywordAbsentField.setVisible(false);
						keywordLabel.setVisible(false);
						validate();
					}
					else if(type.equals("Contains, exclude"))
					{
						keywordField.setVisible(false);
						keywordPresentField.setVisible(true);
						keywordAbsentField.setVisible(true);
						keywordLabel.setVisible(true);
						validate();
					}
					else //either present, all present
					{
						keywordPresentField.setVisible(false);
						keywordAbsentField.setVisible(false);
						keywordField.setVisible(true);
						keywordLabel.setVisible(true);
						validate();
					}
				}
				catch(Exception e) {
					System.out.println("drop down keywords exception"+e);
				}
									
			}			
		});
		
		dropDownFeatures.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String feature=(String) dropDownFeatures.getSelectedItem();
//					if(m.getFormPanel().isVisible()) featuresDesc.setVisible(true);
//					else if(!m.getFormPanel().isVisible()) featuresDesc.setVisible(false);
					
					if(feature.equals("Search"))
					{
						String message="A normal search is performed " + System.lineSeparator()+"on the whole log file"  + System.lineSeparator()+"based on the filters selected.";
						featuresDesc.setText(message);	
						
					}
					if(feature.equals("Remove Clutter"))
					{
						String message= "The clutter is removed from the " + System.lineSeparator()+ "output of the log file, " + System.lineSeparator()+ "and then search is performed based " + System.lineSeparator()+ "on filters selected.";
						featuresDesc.setText(message);
					}
					if(feature.equals("SQL Queries"))
					{
						String message= "The lines contatining SQL queries " + System.lineSeparator()+ "are extracted from the log file, " + System.lineSeparator()+ "and then search is performed " + System.lineSeparator()+ "based on filters selected.";
						featuresDesc.setText(message);
					}
					if(feature.equals("Show Packages"))
					{
						String message= "All the packages used in the selected " + System.lineSeparator()+ "log file is displayed.";
						featuresDesc.setText(message);
					}				
				}
			});
		
	}
	//Listener functions to initialize the interface object
	public void setFormListener(FormListener formListener)
	{
		this.formListener=formListener;
	}
	
	public void setTextListener(TextListener textListener)
	{
		this.textListener=textListener;
	}
	  
	//common function that is called to put lines into map according to the search type selected
 	public boolean keywordsSearch(String [] keywords,String [] presentKeywords, String[] absentKeywords, String fullData,String searchType)
 	{
 		if(searchType.equals("All keyword(s) present"))
 		{
 			for(String word:keywords)
			{									
 				if(fullData.toLowerCase().contains(word.toLowerCase()))
					;
				else return false;
			}

 			return true;			
 		}
 		else if(searchType.equals("Either one present"))
 		{
 			for(String word:keywords)
			{									
				if(fullData.toLowerCase().contains(word.toLowerCase()))
					return true;
			}			
 		}
 		else if(searchType.equals("Contains, exclude"))
 		{
 			for(String word:presentKeywords)
			{					
				if(fullData.toLowerCase().contains(word.toLowerCase()))
					;
				else return false;
			}
 			
			for(String word:absentKeywords)
			{								
				if(word.length()==0 || !fullData.toLowerCase().contains(word.toLowerCase()))
				;
				else return false;
			}
			return true; 			
 		}
 		else if(searchType.equals("No Keywords"))
 		{
 			return true;
 		}
 		return false;	
 	}
	
 	//to club the lines based on timestamp
	public boolean isTimeStamp(String input)
	{
		SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		SimpleDateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSX");
		try {
            format1.parse(input);
            return true;
        } catch (ParseException e1) {
        	 try{
        		 format2.parse(input);
        		 return true;
        	 }
        	 catch (ParseException e2){
        		 System.out.println("time stamp error" + e2);
        		 return false;
        	 }
        }
	}
	
	//function to display SQL queries present in log file
	public void findSQLQueries(String startLine,String endLine,String path,String keyword,String presentKeyword,String absentKeyword,ActionEvent e,SortedMap<Integer,String> logs,String searchType)
	{
		if(progressBar==null)
		{
			System.out.println("null progress bar 6");
			return;
		}
		progressBar.setVisible(true);
		progressBar.setIndeterminateBar();
		progressBar.paint();
		progressBar.setStringValue("Started");
		
		SwingWorker<Void,Integer> worker=new SwingWorker<Void,Integer>(){
			@Override
			protected Void doInBackground() throws Exception {
				try 
				{
					logs.clear();
					int start,end;
					try{
						start=Integer.parseInt(startLine);
					}
					catch(Exception e){
						start=0;
					}
					try{
						end = Integer.parseInt(endLine);
					}
					catch(Exception e){
						end=Integer.MAX_VALUE;
					}
					int mapLine=0;
					BufferedReader br;   
					FileReader myObj = new FileReader(path);
					br = new BufferedReader(myObj);
					String data="";
					String fullData="";
					String [] keywords=keyword.split(",");
					String [] presentKeywords=presentKeyword.split(",");
					String [] absentKeywords=absentKeyword.split(",");
					String timeStamp="";
					boolean isTime=true;
					while ((data=br.readLine())!=null)
					 {
						if(data.isEmpty())
							continue;
						if(data.charAt(0)=='[')
						{
							timeStamp=data.split("]")[0];
							if(timeStamp.length()>1)
								timeStamp=timeStamp.substring(1,timeStamp.length());
							
							isTime=isTimeStamp(timeStamp);								
						}						
						else
							isTime=false;
						
						if(mapLine< start) continue;
						if(mapLine> end) break;					
						if(isTime)
						{
							if(fullData.toLowerCase().contains("select ") || fullData.toLowerCase().contains("select\n") || fullData.toLowerCase().contains("select[["))
								if(fullData.toLowerCase().contains("from "))
									if(mapLine!=0 && keywordsSearch(keywords,presentKeywords,absentKeywords,fullData,searchType)) 
										logs.put(mapLine, fullData);									
							fullData="";
							fullData=data;
							mapLine++;
						}
						else 
						{
							fullData+=data;
						}					
						
					  }
					
					if(mapLine>=start && mapLine<=end)
					{
						if(fullData.toLowerCase().contains("select ") || fullData.toLowerCase().contains("select\n") || fullData.toLowerCase().contains("select[["))
							if(fullData.toLowerCase().contains("from "))
								if(mapLine!=0 && keywordsSearch(keywords,presentKeywords,absentKeywords,fullData,searchType)) 
									logs.put(mapLine, fullData);			
						}
					br.close();
					
				} 
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(p,"Enter a valid file", "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println(e);
				}
				
				return null;			
			}
			@Override
			protected void done() {	
				FormEvent ev;
				if(progressBar==null){
					System.out.println("null progressBar 5");
					return;
				}
				if(searchType.equals("Contains, exclude"))				
					ev=new FormEvent(e,logs,progressBar,path,presentKeyword,btn,saveBtn,backBtn,nextBtn);
				else 			
					ev=new FormEvent(e,logs,progressBar,path,keyword,btn,saveBtn,backBtn,nextBtn);
				
				if(formListener!=null)
				{
					formListener.formEventOccured(ev);
				}		
			}
			@Override
			protected void process(List<Integer> chunks) {
				int value=chunks.get(chunks.size()-1);
			}		
		};	
		worker.execute();			

	}

	//Function to search through logs using swingWorker and store in a map
	 //  the map is then passed to FormEvent to give access to the textPanel to display on textArea
	public void findLogs(String startLine,String endLine,String path,String keyword,String presentKeyword,String absentKeyword,ActionEvent e,SortedMap<Integer,String> logs,String clutter, String searchType)
	{
		//if clutter=no,full search
		//if clutter=yes, ADF or PL/SQL logs
		//if clutter = package , display all packages
		if(progressBar==null)
		{
			System.out.println("null progress bar 4");
			return;
		}
		progressBar.setVisible(true);
		progressBar.setIndeterminateBar();
		progressBar.paint();
		progressBar.setStringValue("Started");
		
		SwingWorker<Void,Integer> worker=new SwingWorker<Void,Integer>(){
			@Override
			protected Void doInBackground() throws Exception {
				try 
				{
					logs.clear();
					int start,end;
					try{
						start=Integer.parseInt(startLine);
					}
					catch(Exception e){
						start=1;
					}
					try{
						end = Integer.parseInt(endLine);
					}
					catch(Exception e){
						end=Integer.MAX_VALUE;
					}
					int mapLine=0;
					BufferedReader br;   
					FileReader myObj = new FileReader(path);
					br = new BufferedReader(myObj);
					String data="";
					String fullData="";
					String [] keywords=keyword.split(",");
					String [] presentKeywords=presentKeyword.split(",");
					String [] absentKeywords=absentKeyword.split(",");
					
					int packageCount=0;
					int testCount=0;
					while ((data=br.readLine())!=null)
					 {
						if(data.isEmpty())
							continue;
						if(clutter.equals("yes"))
						{
							int i= data.lastIndexOf("APPS_SOURCE")-1;
							if(i>=0) 
								data=data.substring(i);
						}
						else if(clutter.equals("package"))
						{ 
							packageCount++;
							int i=data.lastIndexOf("APPS_SOURCE")-1;
							int j=data.indexOf("]",i)+1;
							if(i>=0 && j>0)
							{	data=data.substring(i,j);
								if(!logs.containsValue(data))
								{
									logs.put(-1*packageCount, data);
									testCount++;
								}
								else testCount++;
							}
							publish(logs.size());	
							continue;
						}
						mapLine++;
						
						if(mapLine< start) continue;
						if(mapLine> end) break;
						fullData+=data;	
						
						if(keywordsSearch(keywords,presentKeywords,absentKeywords,fullData,searchType)) 
							logs.put(mapLine, fullData);
						
						fullData="";
						publish(mapLine);		
					  }	
					br.close();
					
				} 
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(p,"Enter a valid file", "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println(e);
				}
				
				return null;			
			}
			@Override
			protected void done() {	
				FormEvent ev;
				if(progressBar==null){
					System.out.println("null progressBar 3");
					return;
				}
				if(searchType.equals("Contains, exclude"))				
					ev=new FormEvent(e,logs,progressBar,path,presentKeyword,btn,saveBtn,backBtn,nextBtn);
				else 			
					ev=new FormEvent(e,logs,progressBar,path,keyword,btn,saveBtn,backBtn,nextBtn);
				if(formListener!=null)
				{
					formListener.formEventOccured(ev);
				}			
			}
			@Override
			protected void process(List<Integer> chunks) {
				int value=chunks.get(chunks.size()-1);         
			}		
		};	
		worker.execute();		
	}
	
	//called when user clicks the DISPLAY button
	//@Override	
	public void actionPerformed(ActionEvent e) {
		if(progressBar==null) {
			System.out.println("Progress bar null 2");
			return;
		}
		String allPaths=filePathField.getText();
		String keyword="";
		String presentKeyword="";
		String absentKeyword="";
		
		//keyword can be empty for no keyword->not visible
		//->visible and empty
		//absent present can be empty with visible and not visible
		if(keywordField.isVisible())
		{
			keyword=keywordField.getText();
		}
		else if(keywordPresentField.isVisible() && keywordAbsentField.isVisible())
		{
			presentKeyword=keywordPresentField.getText();
			absentKeyword=keywordAbsentField.getText();
		}
		String startLine=startLineField.getText();
		String endLine=endLineField.getText();
		int keywordsDropDown = dropDownKeywords.getSelectedIndex();
		String message="";
		if(allPaths.length()==0)
			message+="<html>Please select one or more files.<br />";
		if(startLine.length()!=0 && !startLine.matches("[0-9]*"))
			message+="<html>Please enter a valid number for start line.<br />";
		if(endLine.length()!=0 && !endLine.matches("[0-9]*"))
			message+="<html>Please enter a valid number for end line.<br />";
		if(dropDownKeywords.isEnabled()  && keywordsDropDown==-1)
			message+="<html>Please select a search type.<br />";
		if(startLine.length()!=0 && endLine.length()!=0)
		{
			if(Integer.parseInt(startLine) > Integer.parseInt(endLine))
				message+="<html>Please enter a valid combination of start line and end line.</html>";
		}
			
		if(message.length()!=0)		
		{
			JOptionPane.showMessageDialog(p,message, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		btn.setEnabled(false);
		saveBtn.setEnabled(false);	
		backBtn.setEnabled(false);
		nextBtn.setEnabled(false);
		if(progressBar==null){
			System.out.println("null progressBar 1");
			return;
		}
		progressBar.setVisible(true);
		String [] pathArray=allPaths.split(",");
		progressBar.removePaint();
		for(String path:pathArray)
		{
			SortedMap<Integer,String> logs = new TreeMap<Integer,String>(); 
			String feature=(String) dropDownFeatures.getSelectedItem();
			String searchType="";
			if(dropDownKeywords.isEnabled())
			searchType=(String) dropDownKeywords.getSelectedItem();
			if(feature.equals("Search"))
			{				
				findLogs(startLine,endLine,path,keyword,presentKeyword,absentKeyword,e,logs,"no",searchType);
			}
			else if(feature.equals("Remove Clutter"))
			{	
				findLogs(startLine,endLine,path,keyword,presentKeyword,absentKeyword,e,logs,"yes",searchType);
				
			}
			else if(feature.equals("SQL Queries"))
			{
				logs.clear();
				findSQLQueries(startLine,endLine,path,keyword,presentKeyword,absentKeyword,e,logs,searchType);	
			}
			else if(feature.equals("Show Packages"))
			{
				findLogs(startLine,endLine,path,keyword,presentKeyword,absentKeyword,e,logs,"package",searchType);
			}
		
		}
		int featuresDropDown = dropDownFeatures.getSelectedIndex();
		//to store the features for next button and back button
		if(m!=null && m.isBackToDisplay()==false)
		m.setFeatures(featuresDropDown,keywordsDropDown,allPaths,keywordField.getText(),keywordPresentField.getText(),keywordAbsentField.getText(),startLine,endLine);
	}

	
	//gets the file names entered and processes them
	public void listf(String directoryName) {
	    File directory = new File(directoryName);
	    File[] files = directory.listFiles(new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	            return name.toLowerCase().endsWith(".txt") || name.toLowerCase().endsWith(".log") || name.toLowerCase().endsWith(".text");
	        }
	    });
	   
	    
	    if(files != null)
	        for (File file : files) {      
	            	fileNames+=file.toString();
	 	           fileNames+=",";    	            
	        }
	 }
	

	//called when user clicks on filePathField
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//selects files and directories
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES","txt", "text","log");
		fileChooser.setFileFilter(filter);
		//shows the dialog
		int result=fileChooser.showOpenDialog(null);
		if(result==JFileChooser.APPROVE_OPTION)
		{
			File[] files= fileChooser.getSelectedFiles();
			
			fileNames = "";
	        for(File file: files){
	        	if(file.isDirectory())
	        	{	
	        		listf(file.toString());
	        		continue;
	        	}
	           fileNames+=file.toString();
	           fileNames+=",";             
	        }
			filePathField.setText(fileNames);	
		}	
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
	public JButton getBtn() {
		return btn;
	}
	public void setBtn(JButton btn) {
		this.btn = btn;
	}
	public JButton getBackBtn() {
		return backBtn;
	}
	public JButton getNextBtn() {
		return nextBtn;
	}
	public JTextField getFilePathField() {
		return filePathField;
	}
	public JTextField getKeywordField() {
		return keywordField;
	}
	public FormListener getFormListener() {
		return formListener;
	}
	public JTextField getStartLineField() {
		return startLineField;
	}
	public JTextField getEndLineField() {
		return endLineField;
	}
	public TextListener getTextListener() {
		return textListener;
	}
	public JComboBox<String> getDropDownFeatures() {
		return dropDownFeatures;
	}
	public JTextField getKeywordPresentField() {
		return keywordPresentField;
	}
	public JTextField getKeywordAbsentField() {
		return keywordAbsentField;
	}
	public JComboBox<String> getDropDownKeywords() {
		return dropDownKeywords;
	}
	public AboutDialog getAboutDesc() {
		return aboutDesc;
	}
	
	
}