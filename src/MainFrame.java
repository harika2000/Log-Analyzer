import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.SortedMap;
import javax.swing.UIManager;

/**
 * The controller of the application.Provides a platform for all the panels to send and receive data
 * @author Venkata Harika
 *
 */
public class MainFrame extends JPanel implements FormListener{
	
	private TextTabbedPane textTabbedPane;
	private FormPanel formPanel;	
	private ArrayList<Details> details;
	private int count;
	private int presentCount;
	boolean backToDisplay;
	ParentTabbedTextPane p;
 
	MainFrame(ParentTabbedTextPane p){
		
		this.p=p;
		presentCount=-1;
		count=-1;
		backToDisplay=false;
				
		textTabbedPane = new TextTabbedPane(p,this);
		textTabbedPane.setBackground(UIManager.getColor("activeCaption"));	
		formPanel= new FormPanel(p,this);
		formPanel.setBackground(UIManager.getColor("activeCaption"));
		details=new ArrayList<Details>();
		
		setLayout(new BorderLayout());
		setBackground(UIManager.getColor("activeCaption"));
		add(textTabbedPane,BorderLayout.CENTER);
		add(formPanel,BorderLayout.NORTH);
		
		allListeners();	
		
	}

	//for all listeners
	public void allListeners()
	{
		formPanel.setFormListener(this);
		
		//search button listener
		formPanel.getBtn().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				textTabbedPane.removeAllTabs();				
			}	
		});
		
		//listener for saving file
		formPanel.setTextListener(new TextListener(){
			public void saveToFile(File file) {
			 BufferedWriter outFile = null;		     
		         try {
					outFile = new BufferedWriter(new FileWriter(file));
				} catch (IOException e) {
					System.out.println("setTextListener exception" + e);					
				}
		         textTabbedPane.saveFile(outFile);				
			}	
		});
		
		//listener for expand button
		textTabbedPane.getExpandBtn().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(formPanel.isVisible())
				{
					formPanel.setVisible(false);
					 URL url = App.class.getResource("restore.png");
					 ImageIcon icon = new ImageIcon(url);
					Image img = ((ImageIcon) icon).getImage() ;  
					Image newimg = img.getScaledInstance( 15, 15,  java.awt.Image.SCALE_SMOOTH ) ;  
			  	    icon = new ImageIcon( newimg );
					textTabbedPane.getExpandBtn().setIcon(icon);;
					textTabbedPane.getExpandBtn().setToolTipText("Minimize");
					JTextArea findTextArea= textTabbedPane.getFindTextArea();
					if(findTextArea==null) return;
					findTextArea.requestFocusInWindow();
					findTextArea.setFocusable(true);
					findTextArea.setCaretPosition(findTextArea.getCaretPosition());
				}
				else
				{
					formPanel.setVisible(true);
					 URL url = App.class.getResource("square.png");
					 ImageIcon icon = new ImageIcon(url);
					Image img = ((ImageIcon) icon).getImage() ;  
					Image newimg = img.getScaledInstance( 15, 15,  java.awt.Image.SCALE_SMOOTH ) ;  
			  	    icon = new ImageIcon( newimg );
					textTabbedPane.getExpandBtn().setIcon(icon);
					textTabbedPane.getExpandBtn().setToolTipText("Expand");
					JTextArea findTextArea= textTabbedPane.getFindTextArea();
					if(findTextArea==null) return;
					findTextArea.requestFocusInWindow();
					findTextArea.setFocusable(true);
					findTextArea.setCaretPosition(findTextArea.getCaretPosition());
				}				
				
			}			
		});	
		
		//listener for back button
		formPanel.getBackBtn().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backToDisplay=true;
				presentCount--;
				Details backDetails= details.get(presentCount);
				formPanel.getDropDownFeatures().setSelectedIndex(backDetails.featuresIndex);
				formPanel.getDropDownKeywords().setSelectedIndex(backDetails.keywordsIndex);
				formPanel.getFilePathField().setText(backDetails.filePaths);
				formPanel.getKeywordField().setText(backDetails.keywordField);
				formPanel.getKeywordPresentField().setText(backDetails.containsField);
				formPanel.getKeywordAbsentField().setText(backDetails.absentField);
				formPanel.getStartLineField().setText(backDetails.startLine);				
				formPanel.getEndLineField().setText(backDetails.endLine);
				formPanel.getBtn().doClick();
				backToDisplay=false;
			}			
		});
		
		//listener for next button
		formPanel.getNextBtn().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backToDisplay=true;
				presentCount++;
				Details backDetails= details.get(presentCount);
				formPanel.getDropDownFeatures().setSelectedIndex(backDetails.featuresIndex);
				formPanel.getDropDownKeywords().setSelectedIndex(backDetails.keywordsIndex);
				formPanel.getFilePathField().setText(backDetails.filePaths);
				formPanel.getKeywordField().setText(backDetails.keywordField);
				formPanel.getKeywordPresentField().setText(backDetails.containsField);
				formPanel.getKeywordAbsentField().setText(backDetails.absentField);
				formPanel.getStartLineField().setText(backDetails.startLine);				
				formPanel.getEndLineField().setText(backDetails.endLine);
				formPanel.getBtn().doClick();
				backToDisplay=false;
			}			
		});
	}
	

	// Function to receive data from FormPanel and send to textPanel for displaying
	@Override
	public void formEventOccured(FormEvent e) {
	   SortedMap<Integer,String> logs=e.getLogs();
	   if(e.getProgressBar()==null){
			System.out.println("null progressBar 7");
			return;
		}
	   ProgressBar progressBar=e.getProgressBar();
	   String path=e.getPath();
	   String keyword=e.getKeyword();
	   JButton btn=e.getBtn();
	   JButton saveBtn=e.getSaveBtn();
	   JButton backBtn=e.getBackBtn();
	   JButton nextBtn = e.getNextBtn();
	   textTabbedPane.addNewTextPanel(logs,progressBar,path,keyword,btn,saveBtn,backBtn,nextBtn);
		
	}

	//to store the entered details in class
	public void setFeatures(int featuresIndex,int keywordsIndex, String filePaths, String keywordField,String containsField, String absentField, String startLine, String endLine)
	{
		if(count>presentCount){
			int i;
			for(i=details.size()-1;i>=presentCount+1;i--) 
			{
				details.remove(i);
			}
		}
		
		presentCount+=1;
		count=presentCount; 		
		if(details!=null)
		details.add(new Details(featuresIndex,keywordsIndex,filePaths,keywordField,containsField, absentField,startLine,endLine,count));
	 
	}

	//getters
	public boolean isBackToDisplay() {
		return backToDisplay;
	}
	
	public int getPresentCount() {
		return presentCount;
	}
	
	public ArrayList<Details> getDetails() {
		return details;
	}
	
	public FormPanel getFormPanel()
	{
		return formPanel;
	}
	
	public TextTabbedPane getTextTabbedPane() {
		return textTabbedPane;
	}	
	
}