package EDU.emporia.mathtools;

/**
 * Created by Ben on 12/19/2015.
 */
class Literal extends Expr {
  double v;
  Literal (double _v) { v = _v; }
  public double value () { return v; }
}
