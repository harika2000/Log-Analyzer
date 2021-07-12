import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Class implementing text prompt for each text field.
 * @author Venkata Harika
 *
 */
public class TextPrompt extends JLabel implements FocusListener, DocumentListener
{
	private JTextField textField;
	private Document document;
	private boolean showPromptOnce;
	private int focusLost;
	
	//will show always,except when user types
	public TextPrompt(String text, JTextField textField)
	{
		this.textField = textField;		
		document = textField.getDocument();

		setText(text);
		setFont(new Font("Sans Serif", Font.PLAIN, 15));
		setForeground(Color.LIGHT_GRAY);
		
		textField.addFocusListener( this );
		document.addDocumentListener( this );

		textField.setLayout( new BorderLayout() );
		textField.add( this );
		checkForPrompt();
	}

	//Check whether the prompt should be visible or not. The visibility will change on updates to the Document and on focus changes.
	private void checkForPrompt()
	{
		//  Text has been entered, remove the prompt
		if (document.getLength() > 0)
		{
			setVisible( false );
			return;
		}

		//  Check the Show property and component focus to determine if the
		//  prompt should be displayed.
		//if no focus or focus should be displayed
        if (textField.hasFocus())
        {
        	setVisible( true );
        }
        else
        {
        	setVisible( true );
        }
	}

//  Implement FocusListener
	public void focusGained(FocusEvent e)
	{
		checkForPrompt();
	}

	public void focusLost(FocusEvent e)
	{
		focusLost++;
		checkForPrompt();
	}

//  Implement DocumentListener
	public void insertUpdate(DocumentEvent e)
	{
		checkForPrompt();
	}

	public void removeUpdate(DocumentEvent e)
	{
		checkForPrompt();
	}

	public void changedUpdate(DocumentEvent e) {}
}