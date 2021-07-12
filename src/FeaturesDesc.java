import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * A class to implement the description of every feature present under the feature drop down.
 * @author Venkata Harika
 *
 */
public class FeaturesDesc extends JPanel{

	private JTextArea featuresDesc; 
	FeaturesDesc(ParentTabbedTextPane frame,boolean modal)
	{
		featuresDesc=new JTextArea();
		Dimension size = new Dimension();
		size.width=250;
		size.height=90;
		featuresDesc.setFont(new Font("Sans Serif", Font.PLAIN, 14));
		featuresDesc.setEditable(false);
		featuresDesc.setPreferredSize(size);
		add(featuresDesc, new BorderLayout().CENTER);
		featuresDesc.setBackground(Color.white);
		if (OsUtils.isWindows()) {
			featuresDesc.setBackground(SystemColor.inactiveCaptionBorder);
			this.setBackground(SystemColor.inactiveCaptionBorder);
		} else if (OsUtils.isMac()){
			featuresDesc.setBackground(SystemColor.info);
			this.setBackground(SystemColor.info);
		}
		
		setLocation(20,130);
		
	}
	public void setText(String description)
	{	
		featuresDesc.setText(description);
		revalidate();
	}
	public String getText()
	{
		return featuresDesc.getText();
	}
}

