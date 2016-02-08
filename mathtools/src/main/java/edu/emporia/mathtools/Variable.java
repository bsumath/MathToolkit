// Variables associate values with names.
// Copyright 1996 by Darius Bacon; see the file COPYING.

// 01Jun96: made `make' synchronized.

// 07JUL1999 - version 1.0 - C. Pheatt

package edu.emporia.mathtools;
import java.util.Hashtable;

/**
 * Variables associate values with names.
 */
public class Variable {
  static Hashtable variables = new Hashtable ();

  /**
   * Return <EM>the</EM> variable named `_name'.  
   * make (s1) == make (s2) iff s1.equals (s2).
   */
  static public synchronized Variable make (String _name) {
    Variable result = (Variable) variables.get (_name);
    if (result == null)
      variables.put (_name, result = new Variable (_name));
    return result;
  }

  String name;
  double val;

  public Variable (String _name) { name = _name; val = 0; }

  public String toString () { return name; }
  public double value () { return val; }
  public void set_value (double _val) { val = _val; }
}
