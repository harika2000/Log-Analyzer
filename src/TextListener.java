import java.io.File;

/**
 * Interface to provide connection between TextPanel and FormPanel inorder to Save the textArea into a file
 * @author Venkata Harika
 *
 */
public interface TextListener {
	public void saveToFile(File file);

}
