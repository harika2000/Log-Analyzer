import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * A custom drop down that is used for the features dropdown menu and the keywords dropdown menu.
 * Implements a text prompt for the drop down.
 * @author Venkata Harika
 *
 */
public class ComboBoxRenderer extends JLabel implements ListCellRenderer{
	private String text;
	private String dropDownType;
	
	public ComboBoxRenderer(String text, String dropDownType)
	{
		this.dropDownType=dropDownType;
		if(dropDownType.equals("keywords"))
		this.text=text;
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {		
		if(dropDownType.equals("keywords"))
		{
			if(index==-1 && value==null)
				setText(text);
			else
			{
				setText(value.toString());
				this.setOpaque(true);
				Color background;
		         // check if this cell represents the current DnD drop location
		         JList.DropLocation dropLocation = list.getDropLocation();
		         if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index)
		             background = SystemColor.inactiveCaption;
		         // check if this cell is selected to show tooltip
		         else if (isSelected) 
		         {
		             background = SystemColor.inactiveCaption;
		             if(index==0)
			             this.setToolTipText("All the entered keywords should be present in every line");
		             if(index==1)
			             this.setToolTipText("Any one of the entered keywords should be present in every line");
		             if(index==2)
			             this.setToolTipText("No keywords to filter search.");
		             if(index==3)
			             this.setToolTipText("A part of the entered keywords should be present in every line, while the rest should be excluded");
		         } 
		      // unselected, and not the DnD drop location
		         else 
		             background = Color.WHITE;
		         setBackground(background);     	        
			}
		}
		else if(dropDownType.equals("features"))
		{
			setText(value.toString());
			this.setOpaque(true);
			Color background;
	         // check if this cell represents the current DnD drop location
	         JList.DropLocation dropLocation = list.getDropLocation();
	         if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index)
	             background = SystemColor.inactiveCaption;
	      // check if this cell is selected to show tooltip
	         else if (isSelected) {
	             background = SystemColor.inactiveCaption;
	             if(index==0)
		             this.setToolTipText("The clutter is removed.");
	             if(index==1)
		             this.setToolTipText("A normal search is performed.");
	             if(index==2)
		             this.setToolTipText("All the packages used is displayed.");
	             if(index==3)
		             this.setToolTipText("The lines contatining SQL queries are extracted.");
	         } 
	      // unselected, and not the DnD drop location
	         else 
	             background = Color.WHITE;
	         setBackground(background);     	        			
		}		
		return this;	
 }
}
