import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.SortedMap;
import java.lang.reflect.Field;

/**
 * Class that contains tabs for each file showing the output in text area.
 * @author Venkata Harika
 *
 */
public class TextTabbedPane extends JPanel{
	
	private JTabbedPane tabbedPane;
	private ArrayList<TextPanel> textPanelArray;
	private ParentTabbedTextPane p;
	private JButton expandBtn;
	private Icon icon;
	private MainFrame m;
	
	public TextTabbedPane(ParentTabbedTextPane p, MainFrame m) {
		this.p=p;
		this.m=m;
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		textPanelArray = new ArrayList<TextPanel>();
		 URL url = App.class.getResource("square.png");
		 ImageIcon icon = new ImageIcon(url);
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 15, 15,  java.awt.Image.SCALE_SMOOTH ) ;  
  	    icon = new ImageIcon( newimg );
		expandBtn = new JButton(icon);
		
		Border innerBorder=BorderFactory.createEtchedBorder();
		Border outerBorder=BorderFactory.createEmptyBorder(10,10,10,10);	
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		setLayout(new GridBagLayout());
		GridBagConstraints gc1=new GridBagConstraints();
		
		//set up expand button
		expandBtn.setBorder(null);
		expandBtn.setToolTipText("Expand");
		expandBtn.setFont(new Font("Sans Serif", Font.BOLD, 15));
		expandBtn.setForeground(SystemColor.textHighlight);
		expandBtn.setContentAreaFilled(false);	
		expandBtn.setPreferredSize(new Dimension(20,20));
		
		gc1.weightx=1;
		gc1.weighty=1;
		gc1.fill=GridBagConstraints.BOTH;
		gc1.gridx=0;
		gc1.gridy=0;
		add(tabbedPane,gc1);
		
	
		//listener when tabs are changed
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(tabbedPane.getSelectedComponent()==null) return;
				TextPanel textPanel =(TextPanel) tabbedPane.getSelectedComponent();
				JTextArea findTextArea= textPanel.getTextArea();
				textPanel.getTextArea().requestFocusInWindow();
				textPanel.getTextArea().setFocusable(true);
				textPanel.getTextArea().setCaretPosition(textPanel.getTextArea().getCaretPosition());
				
			}
	    });		
				
	}
	
	//function for adding new panel for each tab
	public void addNewTextPanel(SortedMap<Integer,String> logs,ProgressBar progressBar,String path,String keyword,JButton btn,JButton saveBtn,JButton backBtn,JButton nextBtn)
	{
		if(tabbedPane.getTabCount()==0)
		{
			tabbedPane.addTab("", null, null);
			tabbedPane.setTabComponentAt(0, expandBtn);
		}
		
		textPanelArray.add(new TextPanel(p,m));
		tabbedPane.addTab(path,null, textPanelArray.get(textPanelArray.size()-1),path);
		if(tabbedPane.getTabCount()==2)
			tabbedPane.setSelectedIndex(1);
		int index=tabbedPane.indexOfTab(path);
		
		JButton crossBtn;
		JPanel tabPanel;
		JLabel tabLabel;
		crossBtn = new JButton("x");
		crossBtn.setForeground(Color.BLACK);	
		tabPanel=new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tabPanel.setOpaque(false);

		if (OsUtils.isWindows()) {
			tabLabel=new JLabel(path.substring(path.lastIndexOf("\\")+1));
		} else if(OsUtils.isMac()){
			tabLabel= new JLabel(path.substring(path.lastIndexOf("/")+1));
		}
		else
		{
			tabLabel= new JLabel(path);
		}

		tabPanel.add(tabLabel);
		tabPanel.add(crossBtn);
		
		tabLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		tabPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		crossBtn.setUI(new BasicButtonUI());
		crossBtn.setBackground(UIManager.getColor("activeCaption"));
		crossBtn.setPreferredSize(new Dimension(15, 15));
		crossBtn.setToolTipText("close this tab");
		crossBtn.setBorder(BorderFactory.createEtchedBorder());
		
		tabbedPane.setTabComponentAt(index, tabPanel);
		
		//listener for changing colour of cross button
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
		
		//listener for closing tab on cross button
		crossBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i= tabbedPane.indexOfTabComponent(tabPanel);
				int confirm =JOptionPane.showConfirmDialog(p, "Click ok if you want to close the tab","CLOSE TAB",JOptionPane.OK_CANCEL_OPTION);
				if(confirm==0)
				if(i!=-1) {
					tabbedPane.remove(i);
					textPanelArray.remove(i-1);
				}
			}	
		});
		//calling text panel from text tabbed pane
		textPanelArray.get(textPanelArray.size()-1).appendTextArea(logs, progressBar,keyword,btn,saveBtn,backBtn,nextBtn);
	}
	
	//function to remove all tabs
	public void removeAllTabs()
	{
		textPanelArray.clear();
		tabbedPane.removeAll();
	}
	
	//function to save file
	public void saveFile(BufferedWriter outFile)
	{
	   TextPanel textPanel =(TextPanel) tabbedPane.getSelectedComponent();
	   textPanel. writeToFile(outFile);
		
	}
	
	public JTextArea getFindTextArea()
	{
		TextPanel textPanel =(TextPanel) tabbedPane.getSelectedComponent();
		if(textPanel==null) return null;
		JTextArea findTextArea= textPanel.getTextArea();
		return findTextArea;
	}
	
	//function to set cursor position in text area
	public void setCaretPosition()
	{
		for(TextPanel textPanel:textPanelArray)
		{
			textPanel.getTextArea().setCaretPosition(0);;
		}
	}

	//getter
	public JButton getExpandBtn() {
		return expandBtn;
	}

	
	
}