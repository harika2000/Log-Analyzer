 import javax.swing.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import java.awt.Font;
/**
 * The Panel that has the textArea to display the logs according to the users input from the FormPanel
 * @author Venkata Harika
 *
 */
public class TextPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private List<Color> colors;
    private HighlightPainter painter;
    String text="";
    int index=0;
	Highlighter highlighter;
	Document doc;
    String [] keywords;
    MainFrame m;
    private class Highlight {
    	String word;
    	HighlightPainter painter;
    	int index;
    	Highlight(String word,HighlightPainter painter,int index)
    	{
    		this.word=word;
    		this.painter=painter;
    		this.index=index;		
    	}
    	
    };
	
	TextPanel(ParentTabbedTextPane p, MainFrame m)
	{
		this.m=m;
		textArea= new JTextArea();
		textArea.setFont(new Font("Sans Serif", Font.PLAIN, 16));
		textArea.setOpaque(true);
		colors= new ArrayList<Color>();

	    highlighter = textArea.getHighlighter(); 
	    doc =  textArea.getDocument();
	  	
		//set up colors list
		colors.add(Color.YELLOW);
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.LIGHT_GRAY);
		colors.add(Color.MAGENTA);
		colors.add(Color.ORANGE);
		colors.add(Color.CYAN);
		colors.add(Color.GRAY);	
		colors.add(Color.PINK);
		
		Border innerBorder=BorderFactory.createEtchedBorder();
		Border outerBorder=BorderFactory.createEmptyBorder(5,5,5,5);
		
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		setLayout(new GridBagLayout());

		//setting layout
		GridBagConstraints gc4=new GridBagConstraints();
		gc4.weightx=1;
		gc4.weighty=1;
		gc4.fill=GridBagConstraints.BOTH;
		gc4.gridx=0;
		gc4.gridy=0;
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane,gc4);
		
	}
	
	//function for highlighting text
	public void highlightText(String keyword)
	{
		SwingWorker<Void,Highlight> worker=new SwingWorker<Void,Highlight>(){
			@Override
			protected Void doInBackground() throws Exception {
				int index=0;
			      keywords=keyword.split(",");
			      int i=0;
			      try {
						text= doc.getText(0, doc.getLength());
						for(String word:keywords)
						{
							index=0;
							painter =  new DefaultHighlighter.DefaultHighlightPainter(colors.get(i));
							while((index=text.toUpperCase().indexOf(word.toUpperCase(),index))>=0)
							{
								publish(new Highlight(word,painter,index));
								index+=word.length();
							}
							if(i==colors.size()-1)
								i=0;
							else
								i++;
						}
						    
					} catch (Exception e) {
						System.out.println("Highlight Exception" + e);
					}
			
				return null;
			}

			@Override
			protected void done() {
				super.done();
			}

			@Override
			protected void process(List<Highlight> chunks) {
				for(Highlight highlight:chunks)
				{
					try {
						highlighter.addHighlight(highlight.index,highlight.index+highlight.word.length(), highlight.painter);
					} catch (BadLocationException e) {
						System.out.println("highlightprocess Exception" +e);
					}
				}
				
				super.process(chunks);
			}	
		};	
	     
		worker.execute();
	}
	
	
	//Writes the logs from the map into the textArea 
	public void appendTextArea(SortedMap<Integer,String> logs,ProgressBar progressBar,String keyword, JButton btn,JButton saveBtn,JButton backBtn,JButton nextBtn)
	{
		
		SwingWorker<Void,Integer> worker=new SwingWorker<Void,Integer>(){
			@Override
			protected Void doInBackground() throws Exception {
				if(progressBar==null){
					System.out.println("null progressBar ");
					return null;
				}
				if(progressBar!=null && logs!=null)
				{
					textArea.setText("");
					progressBar.setVisible(true);
					progressBar.paint();
					progressBar.setProgressValue(0);
					progressBar.setMaximumValue(logs.size());
					if(logs.size()==0)
					{
						progressBar.setMaximumValue(1);
					}
					progressBar.removeIndeterminateBar();
					StringBuilder str=new StringBuilder();
					int count=0;
					if(logs.size()==0){
						publish(0);
					}
					int overallCount=0;
					for( SortedMap.Entry<Integer, String> e : logs.entrySet()) {
						overallCount++;
						publish(overallCount);				     
						if(count<10000)
						{
							if(e.getKey()>=0)
							{
								str.append(" " + e.getKey() + ". " + e.getValue()+"\n\n");
							}
							else
							{
								str.append(" " + e.getValue()+"\n\n");
							}
							
							count++;
						}
						else if(count==10000 || logs.size()<10000)
						{
							textArea.append(str.toString());
							str.delete(0, str.length());
							count=0;	
						}					
					  }
					
					textArea.append(str.toString());
					if(logs.size()==0){
						publish(1);
					}
				}
								
				return null;
			}

			@Override
			protected void done() {
				if(keyword.length()!=0)
				highlightText(keyword);	
				if(progressBar==null){
					System.out.println("null progressBar 8");
					return;
				}
				progressBar.setStringValue("Done");							
				validate();
				
				btn.setEnabled(true);
				saveBtn.setEnabled(true);
				if(m.getPresentCount()>0) backBtn.setEnabled(true);
				else backBtn.setEnabled(false);	
				
				if(m.getPresentCount()==m.getDetails().size()-1) nextBtn.setEnabled(false);
				else {
					nextBtn.setEnabled(true);
					
				}
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(progressBar==null){
							System.out.println("null progressBar 9");
							return;
						}
						if(progressBar.getStringValue().equals("Done"))
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}						
					}					
				});	
				progressBar.setVisible(false);
				textArea.requestFocusInWindow();
    			textArea.setFocusable(true);
				textArea.setCaretPosition(0);
				textArea.repaint();		
			}
			@Override
			protected void process(List<Integer> chunks) {
				
				if(progressBar==null){
					System.out.println("null progressBar 10");
					return;
				}
				int value=chunks.get(chunks.size()-1);
				progressBar.setProgressValue(value);
				int ValueMax=progressBar.getMaximumValue();
				
				if(value<(0.7*ValueMax))
				{
					progressBar.setStringValue("Busy");
				}
				if(value>=(0.7*ValueMax) && value < ValueMax)
				{
					progressBar.setStringValue("Almost done!");
				}
				
			}
			
			
		};
		worker.execute();			
		
	}
	
	//for saving file into specified file
	public void writeToFile(BufferedWriter outFile)
	{
		
		try {
			textArea.write(outFile);
		} catch (IOException e) {
			System.out.println("write to file exception" + e);
		}
		
	}

	//getters
	public JTextArea getTextArea() {
		return textArea;
	}
	
	
}
