package edu.emporia.mathbeans;

import java.awt.*;
import edu.emporia.mathtools.*;



/**
 * An object of this class will generate a random piecewise linear function that can 
 * be used in examples.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/7/00
 */
public class RandomPLFunction extends edu.emporia.mathtools.MathFunction
{  
   
    /**
     * The y-coordinate of the point at x=-10.
     */
    protected double yLeftEnd;
    
    /**
     * The x-coordinate of the left hand point used to construct the function. It will
     * be between -6 and -2.
     */
    protected double xLeft; 
    
    /**
     * The y-coordinate of the left hand point used to construct the function. It will
     * be between -10 and 10.
     */
    protected double yLeft; 
     
    /**
     * The x-coordinate of the right hand point used to construct the function. It will
     * be between -6 and -2.
     */
    protected double xRight;
                         
    /**
     * The y-coordinate of the right hand point used to construct the function. It will
     * be between -10 and 10.
     */
    protected double yRight;
                         
    /**
     * The y-coordinate of the point at x=10.
     */
    protected double yRightEnd;
    /**
     * No-argument constructor
     */
    
    public RandomPLFunction()
    {
        randomize();
    
		//{{INIT_CONTROLS
		//}}
	}
    
    /**
     * This method will select random values for the parameters used to construct the
     * random piecewise linear function
     */
    public void randomize()
    {
        yLeftEnd = randomChoice(-10.0,10.0);
        xLeft = randomChoice(-6.0,-2.0);
        yLeft = randomChoice(-10.0,10.0);
        xRight = randomChoice(2.0,6.0);
        yRight = randomChoice(-10.0,10.0);
        yRightEnd = randomChoice(-10.0,10.0);
    }
    
    /**
     * Picks a random number between the given parameters
     * 
     * @param a The lower bound for the interval from which the random number is chosen
     * @param b The upper bound for the interval from which the random number is chosen
     * @return A random number between a and b
     */
    public double randomChoice( double a, double b)
    {
        if ( a < b )
        {
            return (Math.rint(2.0 * Math.random()*(b-a)))/2.0 + a;
        }
        else return a;
    }
    
    /**
     * Returns the function evaluated at the given value
     * 
     * @param u The x-value at which the function will be evaluated
     * @return The value of the function at u.
     */
    public double functionValue(double u)
    {
        double yValue;
        
        if ( u < xLeft )
        {
            yValue = yLeftEnd +
                ((yLeft - yLeftEnd)/(xLeft + 10))*(u+10);
        }
        else if ( u < xRight )
        {
            yValue = yLeft +
                ((yRight - yLeft)/(xRight - xLeft))*(u-xLeft);
        }
        else yValue = yRight +
                ((yRightEnd - yRight)/(10-xRight))*(u-xRight);
                
        return yValue;        
    }

        
	//{{DECLARE_CONTROLS
	//}}
}
