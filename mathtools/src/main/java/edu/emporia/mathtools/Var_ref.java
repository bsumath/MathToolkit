package edu.emporia.mathtools;

/**
 * Created by Ben on 12/19/2015.
 */
class Var_ref extends Expr {
  Variable var;
  Var_ref (Variable _var) { var = _var; }
  public double value () { return var.value (); }
}
