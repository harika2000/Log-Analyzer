import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * The main frame that is divided into tabs with each tab having its own MainFrame.
 * @author Venkata Harika
 *
 */
public class ParentTabbedTextPane extends JFrame{
	private JTabbedPane parentTabbedPane;
	private JButton addBtn;
	private ArrayList<MainFrame> parentPanelArray;
	private MainFrame MFObject;
	private List<String> allTabNames ;
	private FindDialog findDialog;
	Action displayAction;
	ParentTabbedTextPane()
	{
		super("Log Analyzer");		
		this.setBackground(Color.white);
		
		MFObject= new MainFrame(this);
		parentTabbedPane= new JTabbedPane();
		addBtn=new JButton("+");
		parentPanelArray=new ArrayList<MainFrame>();
		allTabNames = new ArrayList<String>();
		findDialog= new FindDialog(this,"Find",false,parentTabbedPane);
		if(parentTabbedPane==null) System.out.println("yes");
		displayAction =new DisplayAction();
		 
		//setResizable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setMinimumSize(new Dimension(1536 , 600));
		setLayout(new GridBagLayout());
		GridBagConstraints gc1=new GridBagConstraints();
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gc1.weightx=1;
		gc1.weighty=1;
		gc1.fill=GridBagConstraints.BOTH;
		gc1.gridx=0;
		gc1.gridy=0;
		add(parentTabbedPane,gc1);
		
		//set up add btn
		addBtn.setBorder(null);
		addBtn.setFocusPainted(false);			
		addBtn.setPreferredSize(new Dimension(20,20 ));
		addBtn.setToolTipText("Click to add more tabs");
		addBtn.setFont(new Font("Sans Serif", Font.BOLD, 17));
		addBtn.setForeground(SystemColor.textHighlight);
		addBtn.setContentAreaFilled(false);	
		parentTabbedPane.addTab("", null, null, "Click to add more tabs");
		parentTabbedPane.setTabComponentAt(0, addBtn);
				
		setTab("Tab 1");
		addKeyBindings();
		allListeners();	
		
	}
	
	public void allListeners()
	{
		//listener for renaming tab
		parentTabbedPane.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2 && parentTabbedPane.getUI().tabForCoordinate(parentTabbedPane, e.getX(),e.getY())!=-1)
				{
					int index=parentTabbedPane.getSelectedIndex();
					//storing
					String prevTabName=allTabNames.get(index-1);
					allTabNames.remove(index-1);
					String message="Rename the tab or leave blank for default";
					String tabName="";
				
					do {
						tabName =JOptionPane.showInputDialog(ParentTabbedTextPane.this,message,"Rename tab",JOptionPane.INFORMATION_MESSAGE);
				        message ="<html><b style='color:red'>Tab Name already exists.</b><br>"
				                + "Rename the tab or leave blank for default";    
				    } while(allTabNames.contains(tabName));
								
					allTabNames.add(index-1,prevTabName);
					if(tabName!=null)
					{
						if(tabName.length()==0)
						{
							tabName="Tab "+ Integer.toString(index);
							allTabNames.set(index-1,prevTabName);
						}
						
						JPanel newPanel = (JPanel) parentTabbedPane.getTabComponentAt(index);
						JLabel newLabel = (JLabel)newPanel.getComponent(0);
						newPanel.remove(0);
						newLabel.setText(tabName);
						newPanel.add(newLabel, 0);
						parentTabbedPane.setTabComponentAt(index, newPanel);
						allTabNames.set(index-1,tabName);						
					}					
				}				
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
		});
		
		//listener for add button
		addBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				String message="Enter a unique tab name or leave blank for default";
				String tabName="";
				do {
					tabName =JOptionPane.showInputDialog(ParentTabbedTextPane.this,message,"");
			        message ="<html><b style='color:red'>Tab Name already exists.</b><br>"
			                + "Enter a unique tab name";    
			    } while(allTabNames.contains(tabName));
				
				if(tabName!=null) 
				{
					if(tabName.length()==0) {
						tabName="Tab "+ Integer.toString(parentTabbedPane.getTabCount());
					}
					setTab(tabName);
					
				}							
			}		
		});
		
		//listener for setting caret position when changing tab
		parentTabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(parentTabbedPane.getSelectedComponent()==null) return;
				MainFrame mainFrame = (MainFrame) parentTabbedPane.getSelectedComponent();
				TextTabbedPane textTabbedPane =(TextTabbedPane) mainFrame.getTextTabbedPane();
				if(textTabbedPane==null) return;
				JTextArea findTextArea= textTabbedPane.getFindTextArea();
				if(findTextArea==null) return;
				findTextArea.requestFocusInWindow();
				findTextArea.setFocusable(true);				
				findTextArea.setCaretPosition(findTextArea.getCaretPosition());			
			}
	    });

	}
	
	//setting panel for each tab
 public void setTab(String tabName)
 {
	   MainFrame m = new MainFrame(this);
	   
	    parentPanelArray.add(m);	   
		parentTabbedPane.addTab(tabName,null, parentPanelArray.get(parentPanelArray.size()-1),"Double click to rename");
		parentTabbedPane.setSelectedComponent(parentPanelArray.get(parentPanelArray.size()-1));
		allTabNames.add(tabName);
		
		int index=parentTabbedPane.indexOfTab(tabName);
		JButton crossBtn;
		JPanel tabPanel;
		JLabel tabLabel;
		crossBtn = new JButton("x");
		crossBtn.setForeground(Color.BLACK);	
		tabPanel=new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tabPanel.setOpaque(false);
		tabLabel=new JLabel(tabName);
		
		tabPanel.add(tabLabel);
		tabPanel.add(crossBtn);
		
		tabLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		tabPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		crossBtn.setUI(new BasicButtonUI());
		crossBtn.setBackground(UIManager.getColor("activeCaption"));
		crossBtn.setPreferredSize(new Dimension(15, 15));
		crossBtn.setToolTipText("close this tab");
		crossBtn.setBorder(BorderFactory.createEtchedBorder());
		
		//changing colour of crossbutton
		parentTabbedPane.setTabComponentAt(index, tabPanel);
		crossBtn.addMouseListener(new MouseListener () {
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				crossBtn.setForeground(Color.RED);	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				crossBtn.setForeground(Color.BLACK);
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}	
		});
		
		//closing tab on clicking cross button
		crossBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i= parentTabbedPane.indexOfTabComponent(tabPanel);
				int confirm =JOptionPane.showConfirmDialog(ParentTabbedTextPane.this, "Click ok if you want to close the tab","CLOSE TAB",JOptionPane.OK_CANCEL_OPTION);
				if(confirm==0)
				if(i>=1) {
					parentTabbedPane.remove(i);
					parentPanelArray.remove(i-1);
					allTabNames.remove(i-1);
				}
			}	
		});
				
 }
 
 //for search button enter
 public void addKeyBindings()
	{
		if(this.getRootPane()==null) {
			System.out.println("addKeyBindings()searchnull");
			return;
		}
		InputMap im_displayAction = this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke key_displayAction=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);	
		im_displayAction.put(key_displayAction, "display action");
		this.getRootPane().getActionMap().put("display action", displayAction);
	}
  
	public class DisplayAction extends AbstractAction{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(parentTabbedPane.getSelectedComponent()==null) System.out.println("null mainframe");
			MainFrame mainFrame = (MainFrame) parentTabbedPane.getSelectedComponent();
			if(mainFrame.getFormPanel()==null) System.out.println("null formPanel");
			FormPanel formPanel =(FormPanel) mainFrame.getFormPanel();
			formPanel.getBtn().doClick();
		}	
	}
 
	//getting textarea
 public JTextArea getFindTextArea()
 {
	 MainFrame mainFrame = (MainFrame) parentTabbedPane.getSelectedComponent();
		JTextArea findTextArea= mainFrame.getTextTabbedPane().getFindTextArea();
	 return findTextArea;
 }
 
 //getters
 public JTabbedPane getParentTabbedPane()
 {
	 return parentTabbedPane;
 }

 
 

}