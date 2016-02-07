package EDU.emporia.mathbeans;
import java.awt.*;
import EDU.emporia.mathtools.*;
import javax.swing.*;


/**
 * This class can be used to construct a MathFunction object defined by a symbolic 
 * formula
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/7/00
 */
public class SymbolicFunction extends EDU.emporia.mathtools.MathFunction
{

    /**
     * The expression object used by the parser
     */
    Expr expression; // expression object to be evaluated

    /**
     * The Variable object corresponding to the value of x that will be used by the parser
     */
    Variable xValue;

	/**
	 * The no-argument constructor
	 */
	public SymbolicFunction()
	{
	    // set the special variables
	    xValue = Variable.make ("x");
        Variable.make ("Pi").set_value (Math.PI);
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
            setFormula("0");
            
        }
        catch(Graphable_error r)
        {
	    }
	}

	/**
	 * Sets the formula used to define the function
	 * 
	 * @param formula The formula used to define the functoin
	 * @exception Graphable_error This will be thrown if the parser is unable to parse the formulas
	 */
	public void setFormula(java.lang.String formula)throws Graphable_error
	{
	    try { 
            expression = Parser.parse (formula); 
            title = "y=" + formula;
        } catch (Syntax_error e) {
		    throw new Graphable_error ("Syntax error in function specification");
        }
		this.formula = formula;
	}


	/**
	 * Gets the formula used to define the function
	 * 
	 * @return The formula used to define the function
	 */
	public java.lang.String getFormula()
	{
		return this.formula;
	}

	/**
	 * Used to determine the value of the function at the specified x-value
	 * 
	 * @param a The x-value
	 * @return The value of the function at the specified x-value.
	 */
	public double functionValue(double a)
	{
	    xValue.set_value(a);
	    if ( !expression.equals(null) )
		    return expression.value();
	    else
	    {
	        return 0.0;
	    }
	}

	/**
	 * The formula used to define the function
	 */
	protected java.lang.String formula = "";

}