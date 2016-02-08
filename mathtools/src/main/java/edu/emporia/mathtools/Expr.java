

package edu.emporia.mathtools;

/**
 * A mathematical expression, built out of literal numbers, variables,
 * arithmetic operators, and elementary functions.  The operator names
 * are from java.lang.Math.
 * 
 * @author Darius Bacon
 * @author Chuck Pheatt
 * @version 1.0
 * @since 7/7/99
 */
public abstract class Expr {

  /** @return the value given the current variable values */
  public abstract double value ();

  /** Binary operator. */  public static final int ADD = 0;  
  /** Binary operator. */  public static final int SUB = 1;
  /** Binary operator. */  public static final int MUL = 2;
  /** Binary operator. */  public static final int DIV = 3;
  /** Binary operator. */  public static final int POW = 4;
  /** Binary operator. */  public static final int MOD = 5;
  /** Binary operator. */  public static final int LTH = 6;
  /** Binary operator. */  public static final int LEQ = 7;
  /** Binary operator. */  public static final int GTH = 8;
  /** Binary operator. */  public static final int GEQ = 9;
  /** Binary operator. */  public static final int EQU = 10;
  /** Binary operator. */  public static final int NEQ = 11;
  /** Binary operator. */  public static final int AND = 12;
  /** Binary operator. */  public static final int ORR = 13;

  /** Unary operator. */        public static final int ABS   = 100;
  /** Unary operator. */        public static final int ACOS  = 101;
  /** Unary operator. */        public static final int ASIN  = 102;
  /** Unary operator. */        public static final int ATAN  = 103;
  /** Unary operator. */        public static final int CEIL  = 104;
  /** Unary operator. */        public static final int COS   = 105;
  /** Unary operator. */        public static final int EXP   = 106;
  /** Unary operator. */        public static final int FLOOR = 107;
  /** Unary operator. */        public static final int LOG   = 108;
  /** Unary minus operator. */  public static final int NEG   = 109;
  /** Unary operator. */        public static final int ROUND = 110;
  /** Unary operator. */        public static final int SIN   = 111;
  /** Unary operator. */        public static final int SQRT  = 112;
  /** Unary operator. */        public static final int TAN   = 113;
  /** Unary operator. */        public static final int LOG10 = 114;
  /** Unary operator. */        public static final int FACT  = 115;
  
  public static Expr make_literal (double v) { 
    return new Literal (v); 
  }
  public static Expr make_var_ref (Variable var) {
    return new Var_ref (var);
  }
  /** 
   * @param rator unary operator
   * @param rand operand
   */
  public static Expr make_app1 (int rator, Expr rand) {
    Expr app = new App1 (rator, rand);
    return rand instanceof Literal ? new Literal (app.value ()) : app;
  }
  /** 
   * @param rator binary operator
   * @param rand0 left operand
   * @param rand1 right operand
   */
  public static Expr make_app2 (int rator, Expr rand0, Expr rand1) {
    Expr app = new App2 (rator, rand0, rand1);
    return rand0 instanceof Literal && rand1 instanceof Literal
	     ? new Literal (app.value ()) 
	     : app;
  }
}

// These classes are all private to this module so that I can get rid
// of them later.  For applets you want to use as few classes as
// possible to avoid http connections at load time; it'd be profitable
// to replace all these subtypes with bytecodes for a stack machine,
// or perhaps a type that's the union of all of them (see class Node
// in java/demo/SpreadSheet/SpreadSheet.java).

