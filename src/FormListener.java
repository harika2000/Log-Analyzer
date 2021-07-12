import java.util.EventListener;

/**
 * An interface extending EventListener to send data from the form to other components.
 * @author Venkata Harika
 *
 */
public interface FormListener extends EventListener{
	
	public void formEventOccured(FormEvent e);

}

