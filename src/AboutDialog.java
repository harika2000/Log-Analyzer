import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Dialog box that tells what the application is about.
 * @author Venkata Harika
 */
public class AboutDialog extends JDialog{
	private JTextArea aboutDesc; 
	AboutDialog(ParentTabbedTextPane frame,boolean modal)
	{
		super(frame,"About",true);
		aboutDesc=new JTextArea();
		Dimension size = new Dimension();
		size.width=700;
		size.height=300;
		String message="This is an application for analyzing logs. It provides the following functions: " + System.lineSeparator() 
		+ "1. Search for any combination of words in the complete/any part of the log file." + System.lineSeparator() 
		+"2. Remove the unneccessary clutter to make your log file easy to read." + System.lineSeparator() 
		+"3. Get all the logs containing SQL queries without having to search through the entire log file." + System.lineSeparator() 
		+ "4. See all the pacakges used in the selected log file" + System.lineSeparator()  
		+ "5. Open multiple tabs of the same application at once for easy traversal."  + System.lineSeparator()
		+"6. Save the output in any text file of your computer" + System.lineSeparator()
		+ "7. Valid keyboard shortcuts: 'Ctrl+F' , 'Ctrl+X' , 'Ctrl+C' , 'Tab' , 'Enter' ";
		aboutDesc.setText(message);
		aboutDesc.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		aboutDesc.setEditable(false);
		aboutDesc.setPreferredSize(size);
		aboutDesc.setMargin(new Insets(5,5,5,5));
		add(aboutDesc, new BorderLayout().CENTER);
		aboutDesc.setBackground(Color.white);
		if (OsUtils.isWindows()) {
			aboutDesc.setBackground(SystemColor.inactiveCaptionBorder);
		} else if (OsUtils.isMac()){
			aboutDesc.setBackground(SystemColor.info);
		}
		
		pack();
		setResizable(false);
		setLocation(20,130);
	}
	
}
