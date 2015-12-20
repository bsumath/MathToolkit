package EDU.emporia.mathbeans;
import java.awt.*;
import EDU.emporia.mathtools.*;


/**
 * Generates a MathFunction corresponding to a secant line between two points of the 
 * specified curve.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/7/00
 */
public class SecantLine extends EDU.emporia.mathtools.MathFunction
{

    /**
     * The slope of the line
     */
    protected double slope = 1;

    /**
     * The y-coordinate of the first point on the curve used to determine the line
     */
    protected double yOne = 0;

    /**
     * The y-coordinate of the second point on the curve used to determine the line
     */
    protected double yTwo = 1;
	/**
	 * No-argument constructor
	 */
    

	public SecantLine()
	{
	    super();
	}

	/**
	 * Sets the x-coordinate of the first point used to construct the secant line
	 * 
	 * @param xOne The x-coordinate of the first point used to construct the secant line
	 */
	public void setXOne(double xOne)
	{
		if ( xOne != xTwo )
        {
            this.xOne = xOne;
            updateSlope();
        }
	}

	/**
	 * Sets the x-coordinate of the first point used to construct the secant line
	 * 
	 * @return The x-coordinate of the first point used to construct the secant line
	 */
	public double getXOne()
	{
		return this.xOne;
	}
	/**
	 * Sets the x-coordinate of the second point used to construct the secant line
	 * 
	 * @param xTwo The x-coordinate of the second point used to construct the secant line
	 */

	public void setXTwo(double xTwo)
	{
		if ( xTwo != xOne )
        {
            this.xTwo = xTwo;
            updateSlope();
        }
	}

	/**
	 * Gets the x-coordinate of the second point used to construct the secant line
	 * 
	 * @return The x-coordinate of the second point used to construct the secant line
	 */
	public double getXTwo()
	{
		return this.xTwo;
	}

	/**
	 * Sets the function used to determine the secant line.
	 * 
	 * @param f The function used to determine the secant line.
	 */
	public void setF(EDU.emporia.mathtools.MathFunction f)
	{
		this.f = f;
		updateSlope();
	}

	/**
	 * Gets the function used to determine the secant line.
	 * 
	 * @return The function used to determine the secant line.
	 */
	public EDU.emporia.mathtools.MathFunction getF()
	{
		return this.f;
	}

	/**
	 * Gives the y-coordinate of the point on the secant line with the indicated x-coordinate.
	 * 
	 * @param a The indicated x-coordinate
	 * @return The y-coordinate of the point on the secant line with the indicated x-coordinate.
	 */
	public double functionValue(double a)
	{
		return yOne + slope * ( a - xOne) ;
	}
	
	/**
	 * Updates the value of the slope to reflect any changes made in the points or in the
	 * definition of f.
	 */
	public void updateSlope()
    {
        yOne = f.functionValue(xOne);
        yTwo = f.functionValue(xTwo);
        slope = (yTwo - yOne)/(xTwo - xOne);
    }
    
    /**
     * Gets the value of the slope of the secant line
     * 
     * @return The slope of the secant line
     */
    public double getSlope()
    {
        return slope;
    }


	/**
	 * The x-coordinate of the first point on the curve used to determine the line
	 */
	protected double xOne = 0;

	/**
	 * The x-coordinate of the second point on the curve used to determine the line
	 */
	protected double xTwo = 1;

	/**
	 * The function to which the line is secant.
	 */
	protected EDU.emporia.mathtools.MathFunction f = new MathFunction();

}