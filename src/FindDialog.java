import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Implemets the Ctrl+F dialog box containing a textfield to enter the word, two buttons to determine the direction of 
 * search and a checkbox to determine if the search should be case insensitive.
 * @author Venkata Harika
 *
 */
public class FindDialog extends JDialog{
	
	private JButton downBtn, upBtn;
	private JCheckBox matchCase;
    private JTextField findField;
    private JLabel findLabel;
    private int index=0;
    private String find="";
    private int findLength=find.length();
    Action searchAction;
	Action upButtonAction;
	private JTabbedPane parentTabbedPane;
	private ParentTabbedTextPane frame;
	
	public FindDialog(ParentTabbedTextPane frame, String text, boolean modal,JTabbedPane parentTabbedPane)
	{
		//setting up components
		super(frame,text,modal);
		this.frame=frame;
		this.parentTabbedPane=parentTabbedPane;		
		downBtn = new JButton("down");
		upBtn=new JButton("up");
		upBtn.setEnabled(false);
        downBtn.setEnabled(false);
		matchCase= new JCheckBox("Match Case");
		matchCase.setSelected(false);
		findLabel= new JLabel("<html><b style='color:red'>No results</b><br>");
		findLabel.setEnabled(false);
		findField = new JTextField(10);
		matchCase.setEnabled(false);
		searchAction =new SearchAction();
		upButtonAction = new UpButtonAction();
		//setting tooltips
		downBtn.setToolTipText("Enter");
		upBtn.setToolTipText("Shift+Enter");
		setLocation(1390,110);
		setResizable(false);
		setLayout(new GridBagLayout());
		GridBagConstraints gc1=new GridBagConstraints();
		
		//on pressing enter, down button is clicked
		getRootPane().setDefaultButton(downBtn);
		
		addKeyBindings();
		settingLayout();		
		allListeners();
		
	}
	//sets layout of the dialog
	public void settingLayout()
	{
		GridBagConstraints gc1=new GridBagConstraints();
		gc1.fill=GridBagConstraints.NONE;
		gc1.insets=new Insets(5,5,5,5);
		gc1.anchor =GridBagConstraints.LINE_START;
	
		gc1.gridx=0;gc1.gridy=0;
	    add(findLabel,gc1);    
		gc1.gridx=0;gc1.gridy=1;	
		add(findField,gc1);
		gc1.gridx=1;gc1.gridy=1;
	    add(downBtn,gc1);
	    gc1.gridx=1;gc1.gridy=2;
	    add(upBtn,gc1);
	    gc1.gridx=0;gc1.gridy=2;
	    add(matchCase,gc1);
	    pack();
	}

	//implements listeners
	public void allListeners()
	{
		//Listener for find textField
		findField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				if(findField.getText().length()==0)
				{
					upBtn.setEnabled(false);
			        downBtn.setEnabled(false);
			        findLabel.setVisible(true);
			        findLabel.setEnabled(false);
			        matchCase.setEnabled(false);
			        validate();
			        repaint();
				}
				else
				{
					upBtn.setEnabled(true);
			        downBtn.setEnabled(true);
			        findLabel.setVisible(false);
			        findLabel.setEnabled(true);
			        matchCase.setEnabled(true);
			        validate();
			        repaint();
				}
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changedUpdate(arg0);
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changedUpdate(arg0);
			}			
			
		});
		
		//listener for down direction button
		downBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	if(parentTabbedPane==null) {
        			System.out.println("downBtnnull");
        			return;
        		}
            	JTextArea textArea= frame.getFindTextArea();
            	if(textArea==null) 
            	{
            		findLabel.setVisible(true);		
			        findLabel.setEnabled(true);
			        return;
            	}
        		index=textArea.getCaretPosition();
                find = findField.getText();
                findLength = find.length();
                textArea.requestFocusInWindow();   
        	    textArea.setFocusable(true);     
        	    
                Document document = textArea.getDocument();                    
                try {
                    boolean found = false;     
                    
                    if (index + findLength >= document.getLength()) 
                    	index = 0;
                    String match="";
                    while (index >=0 && index + findLength < document.getLength()) 
                    {
                    	if(matchCase.isSelected())
                    		 match = document.getText(index, findLength);
                    	 else
                    	 {
                    		 match = document.getText(index, findLength).toLowerCase();
                    		 find=find.toLowerCase();
                    	 }                      		             
                        if (match.equals(find)) {
                            found = true;break;
                        }
                        index++;
                    }                       
                    if(found) //if word is found
                    {
                    	findLabel.setVisible(false);
                    	textArea.select(index, index +find.length());
                        selectAndPossiblyCenter(textArea,index,index+findLength);
                    }
                    
                    if(!found) //if word not found, loop around
                    {
                    	index = 0;
                    	if(matchCase.isSelected())
                    		index=document.getText(0, document.getLength()).indexOf(find,index);
                    	else
                    	index=document.getText(0, document.getLength()).toLowerCase().indexOf(find.toLowerCase(),index);
                    	
                    	if(index==-1) 
                    	{
                    		findLabel.setVisible(true);		
        			        findLabel.setEnabled(true);
                    	}
                    	else selectAndPossiblyCenter(textArea,index,index+findLength);                   	
                    }

                } catch (Exception exp) {
                	System.out.println("find dialog error" + exp);
                }               
            }
        });

		//listener for up direction button
		upBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(parentTabbedPane==null) {
        			System.out.println("downBtnnull");
        			return;
        		}
            	JTextArea textArea= frame.getFindTextArea();
            	if(textArea==null) 
            	{
            		findLabel.setVisible(true);		
			        findLabel.setEnabled(true);
			        return;
            	}
				if(index+findLength!=textArea.getCaretPosition())
            	{
            		index=textArea.getCaretPosition();
            	}			
                find = findField.getText();
                findLength = find.length();
               String text=textArea.getText();                   
                boolean found=false;                   
                    if (index >=0 ) { 	
                    	String str=text.substring(0, index);
                        if(matchCase.isSelected())
                        	index= str.lastIndexOf(find);
                        else
                        	index= str.toLowerCase().lastIndexOf(find.toLowerCase());
                        if(index!=-1)
                        {
                        	found=true;
                        	findLabel.setVisible(false);
                        	textArea.select(index, index +find.length());
                        	selectAndPossiblyCenter(textArea,index,index+findLength);
                        }                                                                      
                    }
                    if(!found) // if word not found, loop around
                    {
                    	String str=text;
                    	index=text.length()-1;
                    	if(matchCase.isSelected())
                        	index= str.lastIndexOf(find);
                        else
                        	index= str.toLowerCase().lastIndexOf(find.toLowerCase());
                    	if(index==-1) 
                    	{
                    		findLabel.setVisible(true);
        			        findLabel.setEnabled(true);      			        
                    	}
                    	else
                    	selectAndPossiblyCenter(textArea,index,index+findLength);                   	
                    }
                    
			      }  	
               });
	}

	public JButton getDownBtn() {
		return downBtn;
	}
	public JButton getUpBtn() {
		return upBtn;
	}
	//function for scrolling to selected text
	private static void selectAndPossiblyCenter(JTextArea textArea, int start, int end) {

		if(textArea==null) {
			System.out.println("selectAndPossiblyCenternull");
			return;
		}
	    textArea.setSelectionStart(start);
	    textArea.setSelectionEnd(end);

	    Rectangle r = null;
	    try {
	      r = textArea.modelToView(start);
	      if (r == null) { // Not yet visible
	        return;
	      }
	      
	      if (end != start) {
	        r = r.union(textArea.modelToView(end));
	      }
	    } catch (BadLocationException ble) { 
	      System.out.println("center exception" +ble);
	    	ble.printStackTrace();
	      textArea.setSelectionStart(start);
	      textArea.setSelectionEnd(end);
	      return;
	    }
	    
	    
	    Rectangle visible = textArea.getVisibleRect();

	    // If the new selection is already in the view, don't scroll,
	    if (visible.contains(r)) {
	      textArea.setSelectionStart(start);
	      textArea.setSelectionEnd(end);
	      return;
	    }
	    
		//trying to make in center
	    visible.x = r.x - (visible.width - r.width) / 2;
	    visible.y = r.y - (visible.height - r.height) / 2;

	    Rectangle bounds = textArea.getBounds();
	    Insets i = textArea.getInsets();
	    bounds.x = i.left;
	    bounds.y = i.top;
	    bounds.width -= i.left + i.right;
	    bounds.height -= i.top + i.bottom;

	    if (visible.x < bounds.x) {
	      visible.x = bounds.x;
	    }

	    if (visible.x + visible.width > bounds.x + bounds.width) {
	      visible.x = bounds.x + bounds.width - visible.width;
	    }

	    if (visible.y < bounds.y) {
	      visible.y = bounds.y;
	    }

	    if (visible.y + visible.height > bounds.y + bounds.height) {
	      visible.y = bounds.y + bounds.height - visible.height;
	    }

	    textArea.scrollRectToVisible(visible);
	  }
	
	//function to allow application to register ctrl+f as keyboard shortcuts
	public void addKeyBindings()
	{
		if(parentTabbedPane==null) {
			System.out.println("addKeyBindings()null");
			return;
		}
//		InputMap im_textArea = parentTabbedPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//		KeyStroke key_textArea=KeyStroke.getKeyStroke(KeyEvent.VK_F,Event.CTRL_MASK);		
//		im_textArea.put(key_textArea, "search action");
//		parentTabbedPane.getActionMap().put("search action", searchAction);
		
		InputMap im_textArea = parentTabbedPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		KeyStroke key_textArea=KeyStroke.getKeyStroke(KeyEvent.VK_F,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());		
		im_textArea.put(key_textArea, "search action");
		parentTabbedPane.getActionMap().put("search action", searchAction);
		
		//KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		
		InputMap im_findDialog = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke key_findDialog=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.SHIFT_DOWN_MASK);	
		im_findDialog.put(key_findDialog, "up button action");
		getRootPane().getActionMap().put("up button action", upButtonAction);
        
	}
	//class for registering ctrl+find and begin search
	public class SearchAction extends AbstractAction{
		@Override
		public void actionPerformed(ActionEvent e) {
			index=0;
			if(parentTabbedPane==null) {
				System.out.println("SearchActionnull");
				return;
			}
            setVisible(true);        
            findLength=0;
            findField.setText("");		   	
		}		
	}
	
	//class for registering shift+enter as up button
	public class UpButtonAction extends AbstractAction{
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			upBtn.doClick();
		}	
	}
}
