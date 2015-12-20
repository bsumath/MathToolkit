// Operator-precedence parser.
// Copyright 1996 by Darius Bacon; see the file COPYING.

// 14May96: bugfix. 
//	StreamTokenizer treated '-<number>' as a numeric token, not a minus
//	operator followed by a number.  Fix: make '-' an ordinaryChar.

// 12May97: Changed the precedence of unary minus to be lower than 
//      multiplication, so -y^2 is like -(y^2), not (-y)^2.

// 07JUL1999 - version 1.0 - C. Pheatt

package EDU.emporia.mathtools;
import java.io.*;

/** 
  Parses strings representing mathematical formulas with variables.
  The following operators, in descending order of precedence, are
  defined:

  <UL>
  <LI>^ (raise to a power)
  <LI>* /
  <LI>Unary minus (-x)
  <LI>+ -
  </UL>

  ^ associates right-to-left; other operators associate left-to-right.

  <P>These functions are defined: 
    abs, acos, asin, atan, 
    ceil, cos, exp, floor, 
    log, round, sin, sqrt, 
    tan.  Each requires one argument enclosed in parentheses.

  <P>Whitespace outside identifiers is ignored.

  <P>The syntax-error messages aren't very informative, unfortunately.
  IWBNI it indicated where in the input string the parse failed, but 
  that'd be kind of a pain since our scanner is a StreamTokenizer.  A
  hook for that info should've been built into StreamTokenizer.

  <P>Examples:
  <UL>
  <LI>42
  <LI>2-3
  <LI>cos(x^2) + sin(x^2)
  <UL>
 */
public class Parser {
  static StreamTokenizer tokens;

  public static Expr parse (String input) throws Syntax_error {
    tokens = new StreamTokenizer (new StringReader (input));
    tokens.ordinaryChar ('/');
    tokens.ordinaryChar ('-');
    tokens.ordinaryChar ('%');
    tokens.ordinaryChar ('>');
    tokens.ordinaryChar ('<');
    tokens.ordinaryChar ('=');
    tokens.ordinaryChar ('!');
    tokens.ordinaryChar ('&');
    tokens.ordinaryChar ('|');
    next ();
    Expr expr = parse_expr (0);
    if (tokens.ttype != StreamTokenizer.TT_EOF)
      throw new Syntax_error ("Incomplete expression: " + input);
    return expr;
  }

  static void next () {
    try { tokens.nextToken (); }
    catch (IOException e) { throw new RuntimeException ("I/O error: " + e); }
  }

  static void push () {
     tokens.pushBack(); 
  }

  static void expect (int ttype) throws Syntax_error {
    if (tokens.ttype != ttype)
      throw new Syntax_error ("'" + (char) ttype + "' expected");
    next ();
  }

  static Expr parse_expr (int precedence) throws Syntax_error {
    Expr expr = parse_factor ();
  loop: for (;;) {
      int l, r, rator;   

      // The operator precedence table.
      // l = left precedence, r = right precedence, rator = operator.
      // Higher precedence values mean tighter binding of arguments.
      // To associate left-to-right, let r = l+1;
      // to associate right-to-left, let r = l.

      switch (tokens.ttype) {

      case '|': 
        l = 2; r = 3; rator = Expr.ORR;
        next();
        if(tokens.ttype != '|') push();
        break;

      case '&': 
        l = 4; r = 5; rator = Expr.AND; 
        next();
        if(tokens.ttype != '&') push();
        break;

      case '=': 
        l = 6; r = 7; rator = Expr.EQU; 
        next();
        if(tokens.ttype != '=') push();
        break;
        
      case '!': 
        l = 6; r = 7; rator = Expr.NEQ; 
        next();
        if(tokens.ttype != '=') push();
        break;
      
      case '<':
        l = 8; r = 9;
        next();
        if(tokens.ttype != '=')  {
            push();
            rator = Expr.LTH; 
        }
        else {
            rator = Expr.LEQ; 
        }    
        break;
        
      case '>':
        l = 8; r = 9; 
        next();
        if(tokens.ttype != '=')  {
            push();
            rator = Expr.GTH; 
        }
        else {
            rator = Expr.GEQ; 
        }    
        break;
      
      case '+': l = 10; r = 11; rator = Expr.ADD; break;
      case '-': l = 10; r = 11; rator = Expr.SUB; break;
	
      case '%': l = 20; r = 21; rator = Expr.MOD; break;
      case '*': l = 20; r = 21; rator = Expr.MUL; break;
      case '/': l = 20; r = 21; rator = Expr.DIV; break;
	
      case '^': l = 30; r = 30; rator = Expr.POW; break; 
	
      default: break loop;
      }

      if (l < precedence)
	break loop;

      next ();
      expr = Expr.make_app2 (rator, expr, parse_expr (r));
    }
    return expr;
  }

  static String[] procs = {
    "abs", "acos","arccos", "asin",
    "arcsin", "atan", "arctan",
    "ceil", "cos", "exp", "floor", 
    "log", "round", "sin", "sqrt", 
    "tan", "log10", "fact"
  };
  static int[] rators = {
    Expr.ABS, Expr.ACOS, Expr.ACOS, Expr.ASIN,
    Expr.ASIN, Expr.ATAN, Expr.ATAN, 
    Expr.CEIL, Expr.COS, Expr.EXP, Expr.FLOOR,
    Expr.LOG, Expr.ROUND, Expr.SIN, Expr.SQRT, 
    Expr.TAN , Expr.LOG10, Expr.FACT
  };
	
  static Expr parse_factor () throws Syntax_error {
    switch (tokens.ttype) {
    case StreamTokenizer.TT_NUMBER: {
      Expr lit = Expr.make_literal (tokens.nval);
      next ();
      return lit;
    }
    case StreamTokenizer.TT_WORD: {
      for (int i = 0; i < procs.length; ++i)
	if (procs [i].equals (tokens.sval)) {
	  next ();
	  expect ('(');
	  Expr rand = parse_expr (0);
	  expect (')');
	  return Expr.make_app1 (rators [i], rand);
	}

      Expr var = Expr.make_var_ref (Variable.make (tokens.sval));
      next ();
      return var;
    }
    case '(': {
      next ();
      Expr enclosed = parse_expr (0);
      expect (')');
      return enclosed;
    }
    case '-': 
      next ();
      return Expr.make_app1 (Expr.NEG, parse_expr (15));
    default:
      throw new Syntax_error ("Expected a factor");
    }
  }
}
