package edu.emporia.mathtools;

/**
 * Created by Ben on 12/19/2015.
 */
class App2 extends Expr {
  int rator;
  Expr rand0, rand1;

  App2 (int _rator, Expr _rand0, Expr _rand1) {
    rator = _rator; rand0 = _rand0; rand1 = _rand1;
  }
  public double value () {
    double arg0 = rand0.value ();
    double arg1 = rand1.value ();
    switch (rator) {
    case ADD:  return arg0 + arg1;
    case SUB:  return arg0 - arg1;
    case MUL:  return arg0 * arg1;
    case DIV:  return arg0 / arg1;   // check for division by 0?
    case POW:  return Math.pow (arg0, arg1);
    case MOD:  return arg0 % arg1;
    case LTH:  if(arg0 < arg1) return 1; else return 0;
    case LEQ:  if(arg0 <= arg1) return 1; else return 0;
    case GTH:  if(arg0 > arg1) return 1; else return 0;
    case GEQ:  if(arg0 >= arg1) return 1; else return 0;
    case EQU:  if(arg0 == arg1) return 1; else return 0;
    case NEQ:  if(arg0 != arg1) return 1; else return 0;
    case AND:  if((arg0 != 0) && (arg1 != 0)) return 1; else return 0;
    case ORR:  if((arg0 != 0) || (arg1 != 0)) return 1; else return 0;

    default: throw new RuntimeException ("BUG: bad rator");
    }
  }
}
