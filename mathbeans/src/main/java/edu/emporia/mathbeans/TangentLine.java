package edu.emporia.mathbeans;
import java.awt.*;
import edu.emporia.mathtools.*;


/**
 * A class to be used to construct a tangent line to a curve that can be displayed on 
 * a MathGrapher
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/6/00
 */
public class TangentLine extends edu.emporia.mathtools.MathFunction
{

    /**
     * If true the slope will be approximated automatically. If false it will be based on 
     * the function fPrime
     */
    protected boolean autoSlope = true;

    /**
     * The slope of the line
     */
    protected double slope = 1;

    /**
     * The y-coordinate of the base point
     */
    protected double yBase = 0;

	public TangentLine()
	{
	    super();
	}

	/**
	 * Sets the x-value for the point on the curve to be used as a base point for the 
	 * tangent line
	 * 
	 * @param xBase The x-value of the base point
	 */
	public void setXBase(double xBase)
	{
	    this.xBase = xBase;
        updateSlope();
	}

	/**
	 * Gets the x-value for the point on the curve to be used as a base point for the 
	 * tangent line
	 * 
	 * @return The x-value of the base point
	 */
	public double getXBase()
	{
		return this.xBase;
	}

	/**
	 * Sets the MathFunction object that represents the curve to which this line will 
	 * be tangent
	 * 
	 * @param f The curve
	 */
	public void setF(edu.emporia.mathtools.MathFunction f)
	{
		this.f = f;
        updateSlope();
	}

	/**
	 * Gets the MathFunction object that represents the curve to which this line will 
	 * be tangent
	 * 
	 * @return The curve
	 */
	public edu.emporia.mathtools.MathFunction getF()
	{
		return this.f;
	}
	/**
	 * Sets the function to be used to compute the derivative of f. If this is not called
	 * the derivative will be approximated
	 * 
	 * @param fPrime The derivative function
	 */

	public void setFPrime(edu.emporia.mathtools.MathFunction fPrime)
	{
		this.fPrime = fPrime;
        autoSlope = false;
        updateSlope();
	}

	/**
	 * Gets the function to be used to compute the derivative of f.
	 * 
	 * @return The derivative that is being used
	 */
	public edu.emporia.mathtools.MathFunction getFPrime()
	{
		return this.fPrime;
	}

	/**
	 * Returns the y-coordinate of the tangent line corresponding to the given x
	 * 
	 * @param a The x-coordinate
	 * @return The y-coordinate
	 */
	public double functionValue(double a)
	{
		return yBase + slope * ( a - xBase);
	}
	
	/**
	 * Gives the slope of the line
	 * 
	 * @return The slope
	 */
	public double getSlope()
    {
        return slope;
    }
    
    /**
     * Gives the y-coordinate of the base point on the curve
     * 
     * @return The y-coordinate of the base point
     */
    public double getYBase()
    {
        return yBase;
    }
    
    /**
     * Returns the base point for the tangent line (The base point is the point on the 
     * curve at which this line is tangent).
     */
    public Point2D.Double getBase()
    {
        return new Point2D.Double(xBase, yBase);
    }
    
    /**
     * Updates the slope. Should be called if the curve or the base point is changed
     */
    public void updateSlope()
    {
        yBase = f.functionValue(xBase);
        if ( autoSlope )
            slope = (f.functionValue(xBase + .001)
                        - f.functionValue(xBase - .001))*500;
        else
            slope = fPrime.functionValue(xBase);
    }


	/**
	 * The MathFunction object to which this line is tangent
	 */
	protected edu.emporia.mathtools.MathFunction f = new MathFunction();

	/**
	 * The x-coordinate of the base point
	 */
	protected double xBase = 0;

	/**
	 * The MathFunction object used to determine the derivative when autoSlope is false.
	 */
	protected edu.emporia.mathtools.MathFunction fPrime = new MathFunction();

}