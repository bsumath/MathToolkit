package EDU.emporia.mathbeans;
import java.awt.*;
import EDU.emporia.mathtools.*;


/**
 * This can be used to generate a parametric curve defined by formulas expressed in terms
 * of t.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/7/00
 */
public class SymbolicParametricCurve extends EDU.emporia.mathtools.ParametricCurve
{

    /**
     * The expression object used by the parser to evaluate the x-value
     */
    protected Expr xExpression;
    
    /**
     * The expression object used by the parser to find the y-value
     */
    protected Expr yExpression; 

    /**
     * The Variable object used by the parser to represent the current value of t
     */
    protected Variable tValue;

	/**
	 * The no-argument constructor
	 */
	public SymbolicParametricCurve()
	{
	     // set the special variables
	    tValue = Variable.make("t");
        Variable.make ("PI").set_value (Math.PI);
        Variable.make("Pi").set_value(Math.PI);
        Variable.make ("e").set_value (Math.E);
        Variable.make ("NaN").set_value (Double.NaN);
        Variable.make ("infinity").set_value (Double.POSITIVE_INFINITY);
        Variable.make ("negativeInfinity").set_value (Double.NEGATIVE_INFINITY);
        Variable.make ("true").set_value (1);
        Variable.make ("false").set_value (0);
        Variable.make ("maxValue").set_value (Double.MAX_VALUE);
        Variable.make ("minValue").set_value (Double.MIN_VALUE);
        Variable.make ("randomValue").set_value (java.lang.Math.random());
        
        try{
            setXFormula("0");
            setYFormula("0");
        }catch(Graphable_error r){}
	}
	/**
	 * Method to set the formula to be used to determine the x-parameter. (Should be 
	 * in terms of t).
	 * 
	 * @param xFormula The String expression in terms of t that represents the x-parameter
	 * @exception Graphable_error Will be thrown if the formula cannot be parsed
	 */

	public void setXFormula(java.lang.String xFormula) throws Graphable_error
	{
	    try { 
            xExpression = Parser.parse(xFormula); 
            this.xFormula = xFormula;
            setTitle("x(t)=" + xFormula + ", y(t)=" + yFormula);
        } catch (Syntax_error e) {
		    throw new Graphable_error ("Syntax error in xFormula specification");
        }
	}

	/**
	 * Gets the formula to be used to determine the x-parameter. (Should be 
	 * in terms of t).
	 * 
	 * @return The String expression in terms of t that represents the x-parameter
	 */
	public java.lang.String getXFormula()
	{
		return this.xFormula;
	}

	/**
	 * Method to set the formula to be used to determine the y-parameter. (Should be 
	 * in terms of t).
	 * 
	 * @param yFormula The String expression in terms of t that represents the y-parameter
	 * @exception Graphable_error Will be thrown if the formula cannot be parsed
	 */
	public void setYFormula(java.lang.String yFormula) throws Graphable_error
	{
		try { 
            yExpression = Parser.parse (yFormula); 
            this.yFormula = yFormula;
            setTitle("x(t)=" + xFormula + ", y(t)=" + yFormula);
        } catch (Syntax_error e) {
		    throw new Graphable_error ("Syntax error in yFormula specification");
        }
		
	}

	/**
	 * Gets the formula to be used to determine the y-parameter. (Should be 
	 * in terms of t).
	 * 
	 * @return The String expression in terms of t that represents the y-parameter
	 */
	public java.lang.String getYFormula()
	{
		return this.yFormula;
	}

	/**
	 * Gets the point on the curve corresponding to the given parameter
	 * 
	 * @param a The t-value that determines the point
	 * @return The point on the curve at the parameter
	 */
	public EDU.emporia.mathtools.Point2D getPoint(double a)
	{
	    tValue.set_value(a);
		return new Point2D.Double(xExpression.value(), yExpression.value());
	}
	/**
	 * The title to be displayed that describes the curve
	 * 
	 * @return The title
	 */

	public java.lang.String getTitle()
	{
		return title;
	}

	/**
	 * Describes the curve
	 * 
	 * @return String description of the curve
	 */
	public java.lang.String toString()
	{
		return "Parametric curve defined by " + getTitle();
	}


	/**
	 * The String representing the formula for the x-parameter. (Should be in terms of t)
	 */
	protected java.lang.String xFormula = "";

	/**
	 * The String representing the formula for the y-parameter. (Should be in terms of t)
	 */
	protected java.lang.String yFormula = "";

}