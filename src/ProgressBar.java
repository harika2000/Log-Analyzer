import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * Class that manages the changes done to the progressBar
 * @author Venkata Harika
 *
 */
public class ProgressBar extends JPanel {
	
	private JProgressBar progressBar;
	static JPanel panelg;
	
	ProgressBar()
	{
		super();
		progressBar=new JProgressBar();
		
		Dimension size = new Dimension();
		size.width=310;
		size.height=21;
		if(progressBar==null){
			System.out.println("null progressBar 11");
			return;
		}
		progressBar.setPreferredSize(size);	
		progressBar.setBorderPainted(false);
		progressBar.setBackground(UIManager.getColor("activeCaption"));
		add(progressBar, new BorderLayout().CENTER);		
		setBackground(UIManager.getColor("activeCaption"));
		super.setVisible(true);
	}
	//when value=valueMax,loading over
	public void setMaximumValue(int valueMax)
	{
		if(progressBar==null){
			System.out.println("null progressBar 11");
			return;
		}
		progressBar.setMaximum(valueMax);
		
	}
	//setting value of progress bar
	public void setProgressValue(int value)
	{
		if(progressBar==null){
			System.out.println("null progressBar 12");
			return;
		}
		progressBar.setValue(value);
	}

	//getting maximum value of progress bar
	public int getMaximumValue()
	{
		if(progressBar==null){
			System.out.println("null progressBar 13");
			return 1;
		}
		return progressBar.getMaximum();
	}
	
	//allowing to paint
	public void paint()
	{
		if(progressBar==null){
			System.out.println("null progressBar 14");
			return;
		}
		progressBar.setStringPainted(true);
	}
	
	//removing paint
	public void removePaint()
	{
		if(progressBar==null){
			System.out.println("null progressBar 15");
			return;
		}
		progressBar.setStringPainted(false);
	}
	
	//making bar indeterminate
	public void setIndeterminateBar()
	{
		if(progressBar==null){
			System.out.println("null progressBar 16");
			return;
		}
		progressBar.setIndeterminate(true);
	}
	//removing indeterminate
	public void removeIndeterminateBar()
	{
		if(progressBar==null){
			System.out.println("null progressBar 17");
			return;
		}
		progressBar.setIndeterminate(false);
	}
	//setting value of string
	public void setStringValue(String value)
	{
		if(progressBar==null){
			System.out.println("null progressBar 18");
			return;
		}
		progressBar.setString(value);
	}
	//getting value of string
	public String getStringValue()
	{
		if(progressBar==null){
			System.out.println("null progressBar 19");
			return "";
		}
		return progressBar.getString();
	}
	//find if painted
	public boolean getIsPainted()
	{
		if(progressBar==null){
			System.out.println("null progressBar 20");
			return true;
		}
		return progressBar.isStringPainted();
	}
	
	//to set visibility
	@Override
	public void setVisible(boolean visible) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(progressBar==null){
					System.out.println("null progressBar 21");
					return;
				}
				if(getStringValue().equals("Done"))
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if(progressBar==null){
					System.out.println("null progressBar 22");
					return;
				}
				if(visible==true)
				{
					progressBar.setBorderPainted(true);
					progressBar.setBackground(SystemColor.inactiveCaptionBorder);
					progressBar.setForeground( new ColorUIResource(163,184,204));
					progressBar.setStringPainted(true);
					setBackground(UIManager.getColor("activeCaption"));
				}
				else if(visible==false)
				{
					progressBar.setBorderPainted(false);
					progressBar.setBackground(UIManager.getColor("activeCaption"));
					progressBar.setForeground(UIManager.getColor("activeCaption"));
					progressBar.setStringPainted(false);
					setBackground(UIManager.getColor("activeCaption"));
					validate();
					
				}
			}
			
		});
	}
}
