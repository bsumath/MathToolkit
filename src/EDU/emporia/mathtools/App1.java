package EDU.emporia.mathtools;

/**
 * Mathematical expressions.
 * Copyright 1996 by Darius Bacon; see the file COPYING.
 *
 * 14May96: added constant folding
 *
 * @author Darius Bacon
 * @author Chuck Pheatt
 * @version 1.0
 * @since 7/7/99
 */
class App1 extends Expr {
  int rator;
  Expr rand;

  App1 (int _rator, Expr _rand) { rator = _rator; rand = _rand; }

  public double value () {
    double arg = rand.value ();
    switch (rator) {
    case ABS:   return Math.abs (arg);
    case ACOS:  return Math.acos (arg);
    case ASIN:  return Math.asin (arg);
    case ATAN:  return Math.atan (arg);
    case CEIL:  return Math.ceil (arg);
    case COS:   return Math.cos (arg);
    case EXP:   return Math.exp (arg);
    case FACT:  return MiscFunctions.fact (arg);
    case FLOOR: return Math.floor (arg);
    case LOG:   return Math.log (arg);
    case LOG10: return Math.log (arg) * 0.434294481903251827651128918916;
    case NEG:   return -arg;
    case ROUND: return Math.round (arg);
    case SIN:   return Math.sin (arg);
    case SQRT:  return Math.sqrt (arg);
    case TAN:   return Math.tan (arg);

    default: throw new RuntimeException ("BUG: bad rator");
    }
  }
}
