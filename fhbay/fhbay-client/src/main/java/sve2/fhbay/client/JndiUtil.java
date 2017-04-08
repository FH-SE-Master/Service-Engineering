package sve2.fhbay.client;

import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class JndiUtil {
  private static Context context;

  public static Context getInitialContext() throws NamingException {
    if (context == null) context = new InitialContext();
    return context;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getRemoteObject(String jndiName) throws NamingException {
    Object ref = getInitialContext().lookup(jndiName);
    // return (T) PortableRemoteObject.narrow(ref, intfClass);
    return (T) ref;
  }

  public static String getProperty(String propName) throws NamingException {
    @SuppressWarnings("unchecked")
    Hashtable<String, String> props =
        (Hashtable<String, String>) getInitialContext().getEnvironment();
    return props.get(propName);
  }
  
	public static void printJndiEntries(String name) {
		try {
			Context ctx = new InitialContext();
			NamingEnumeration<Binding> bindList = ctx.listBindings(name);
			// Go through each item in list
			while (bindList != null && bindList.hasMore()) {
				Binding bd = bindList.next();
				System.out.printf("%s/%s: %s: %s%n", name, bd.getName(), bd.getClassName(),
					bd.getObject());
				printJndiEntries(bd.getName());
			}
		}
		catch (NamingException e) {
			// System.out.printf("NamingException: resolved: %s, not resolved: %s%n", e.getResolvedName(), e.getRemainingName());
		}
	}
}
