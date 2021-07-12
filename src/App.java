import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * A class that runs the whole application in EDT by calling the parentTabbedTextPane class in EDT.
 * @author Venkata Harika
 *
 */
public class App
{
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				ParentTabbedTextPane parentTabbed= new ParentTabbedTextPane();
			}	
		});	
	}
}