
/**
 * Stores the entered details in the textFields,frop downs and radio button in order to implement the next and back button.
 * @author Venkata Harika
 *
 */
public class Details {
	int featuresIndex;
	String filePaths;
	int keywordsIndex;
	String keywordField;
	String containsField;
	String absentField;
	String startLine;
	String endLine;
	int count=0;
	
	Details()
	{
	    featuresIndex=-1;
	    keywordsIndex=-1;
		filePaths="";
		keywordField="";
		containsField="";
		absentField="";
		startLine="";
		endLine="";
	}

	public Details(int featuresIndex,int keywordsIndex, String filePaths, String keywordField,String containsField, String absentField, String startLine, String endLine,int count) {
		
		this.featuresIndex = featuresIndex;
		this.filePaths = filePaths;
		this.keywordsIndex = keywordsIndex;
		this.keywordField = keywordField;
		this.containsField = containsField;
		this.absentField = absentField;
		this.startLine = startLine;
		this.endLine = endLine;
		this.count=count;
	
	}
}
