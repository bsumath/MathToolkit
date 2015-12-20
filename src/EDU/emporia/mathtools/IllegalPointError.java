package EDU.emporia.mathtools;

/**
 * Exception used to indicate that an illegal point has been
 * added to a function--for example because there is a duplicate
 * x-value
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 6/5/2001
 */
public class IllegalPointError extends Exception {
  public IllegalPointError (String complaint) { super (complaint); }
}