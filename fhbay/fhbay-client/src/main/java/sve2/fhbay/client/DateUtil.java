package sve2.fhbay.client;

import java.util.Date;
import java.util.Calendar;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class DateUtil {
  public static Date getDate(int year, int month, int day) {
    return getTime(year, month, day, 0, 0, 0);
  }

  public static Date getTime(int year, int month, int day, int hour, int minute) {
    return getTime(year, month, day, hour, minute, 0);
  }

  public static Date getTime(int year, int month, int day, int hour,
      int minute, int second) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month - 1, day, hour, minute, second);
    cal.set(Calendar.MILLISECOND, 0);

    return cal.getTime();
  }

  public static Date getTime(int hour, int minute) {
    return getTime(hour, minute, 0);
  }

  public static Date getTime(int hour, int minute, int second) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.MINUTE, minute);
    cal.set(Calendar.SECOND, second);

    return cal.getTime();
  }

  public static Date now() {
    return new Date();
  }
  
  public static Date addSeconds(Date date, int secs) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.SECOND, secs);
    
    return cal.getTime();
  }
  
  public static void dumpBindings(Context ctx) {
    dumpBindings(ctx, "");
  }

  public static void dumpBindings(Context ctx, String bindingPath) {
    try {
      System.out.println("====== List ob bindings for "
          + (bindingPath == "" ? "root context" : bindingPath) + " ======");

      Object boundObj = ctx.lookup(bindingPath);
      if (boundObj instanceof Context) dumpBindings((Context) boundObj, 0);

      System.out.println("====== Binding list end ======");
    }
    catch (NamingException e) {
      System.out.println(e);
    }
  }

  private static void dumpBindings(Context ctx, int indent) {
    try {
      NamingEnumeration<Binding> bindings = ctx.listBindings("");
      while (bindings.hasMore()) {
        Binding binding = bindings.next();

        StringBuffer blanks = new StringBuffer();
        for (int i = 0; i < indent; i++)
          blanks.append(" ");
        System.out.println(String.format("%s%s (%s)", blanks,
            binding.getName(), binding.getClassName()));

        Object boundObj = binding.getObject();
        if (boundObj instanceof Context)
          dumpBindings((Context) boundObj, indent + 3);
      }
    }
    catch (NamingException e) {
      System.out.println(e);
    }
  }
}
