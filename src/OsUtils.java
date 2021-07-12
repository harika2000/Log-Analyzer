/**
 * Class for deciding if the OS the program is running in is Windows or Mac.
 * @author Venkata Harika
 *
 */
public class OsUtils {
	private static String OS = null;
	   public static String getOsName()
	   {
	      if(OS == null) { OS = System.getProperty("os.name"); }
	      return OS;
	   }
	   public static boolean isWindows()
	   {
	      return getOsName().startsWith("Windows");
	   }

	   public static boolean isMac() // and so on
	   {
		   return getOsName().startsWith("Mac");
	   }
}
