import java.util.EventObject;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JButton;

/**
 * A class extending EventObject that stores the data of the form to send it to all other classes.
 * Accessed by the FormListener as an event .
 * @author Venkata Harika
 *
 */
public class FormEvent extends EventObject {

	private SortedMap<Integer,String> logs = new TreeMap<Integer,String>();
	private ProgressBar progressBar ;
	private String path;
	private String keyword;
	private JButton btn;
	private JButton saveBtn;
	private JButton backBtn;
	private JButton nextBtn;
	
	public FormEvent(Object source) {
		super(source);
	}
	public FormEvent(Object source,SortedMap<Integer,String> logs,ProgressBar progressBar,String path,String keyword,JButton btn,JButton saveBtn,JButton backBtn,JButton nextBtn)
	{
		super(source);
		this.logs = logs;
		if(progressBar==null){
			System.out.println("null progressBar 23 ");
			return;
		}
		this.progressBar=progressBar;
		this.path=path;
		this.keyword=keyword;
		this.btn=btn;
		this.saveBtn=saveBtn;
		this.backBtn=backBtn;
		this.nextBtn=nextBtn;
	}
	
	public JButton getSaveBtn() {
		return saveBtn;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	public SortedMap<Integer, String> getLogs() {
		return logs;
	}
	public void setLogs(SortedMap<Integer, String> logs) {
		this.logs = logs;
	}
	public JButton getBtn() {
		return btn;
	}
	public JButton getBackBtn() {
		return backBtn;
	}
	
	public JButton getNextBtn() {
		return nextBtn;
	}
}
