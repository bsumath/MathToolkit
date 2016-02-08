package edu.emporia.mathtools;
import java.util.*;


/**
 * A class that constructs a piecewise linear function passing through a specified set of 
 * points.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 6/4/2001
 */
public class PiecewiseLinearFunction extends edu.emporia.mathtools.MathFunction
{
    
    /**
     * The Vector that will contain the points used to define the function. They will
     * always be listed in increasing order of x-value.
     */
    protected Vector points = new Vector();
    
    
    public PiecewiseLinearFunction() 
    {
        super();
    }
    
    public PiecewiseLinearFunction(edu.emporia.mathtools.Point2D.Double[] p)
    {
        super();
        try{
            addPoints(p);
        }catch(IllegalPointError e){}
    }
        
    
    /**
     * Overrides the functionValue method of the MathFunction class. This method returns
     * the value of the function at the specified x-coordinate
     * 
     * @param u The x-coordinate
     * @return f(x)
     */
    public double functionValue(double u)
    {
        int index = 0;
        double yValue;
        double x1=0, y1=0, x2=0, y2=0;
        int pointNumber = points.size();
        edu.emporia.mathtools.Point2D.Double p1 = new edu.emporia.mathtools.Point2D.Double(),
            p2 = new edu.emporia.mathtools.Point2D.Double();
        
        if ( pointNumber == 0) yValue = 0;
        else
        {
            p2 = (edu.emporia.mathtools.Point2D.Double)points.elementAt(0);
            x2 = p2.getX();
            if ( u < x2) yValue = p2.getY();
            else
            {   
                while ( ( u > x2 ) && ( index < pointNumber) )
                {
                    p1 = p2;
                    x1 = x2;
                    p2 = (edu.emporia.mathtools.Point2D.Double)points.elementAt(index);
                    x2 = p2.getX();
                    index++;
                }
            
                if ( u > x2 ) yValue = p2.getY();
                else
                {
                   y1 = p1.getY();
                   y2 = p2.getY();
                   yValue = y1 + (u - x1)*(y2 - y1)/(x2 - x1);
                }
            }
        }

        return yValue;
    }
    /**
     * Adds the indicated point to the list of points used to construct the function. The
     * function will then pass through the specified point. It will through an exception 
     * if there is already a point with that x-coordinate.
     * 
     * @param p The point to be added.
     * @exception IllegalPointError
     */
    
    public void addPoint(edu.emporia.mathtools.Point2D.Double p) throws IllegalPointError
    {
        if (points.isEmpty())
        {
            points.addElement(p);
        }
        else 
        {
            int pointNumber = points.size();
            int index = 0;
            double u = p.getX();
            double xTemp = 0;
           
            do 
            { 
                xTemp = ((Point2D.Double)(points.elementAt(index))).getX();
            }while( (u > xTemp)&& (++index < pointNumber));
            
            if ( xTemp == u ) throw new IllegalPointError("Duplicate x-value");
            else
                points.insertElementAt(p,index);
            
        }
    }
    
    /**
     * Adds the point with the specified x and y coordinate. It throws an IllegalPointError
     * if there is already a point with the given x-coordinate.
     * 
     * @param x The x-coordinate of the point to be added
     * @param y The y-coordinate of the point to be added.
     * @exception IllegalPointError This will be thrown is there is an attempt to add a point with the same x coordinate
     * as one that is already being used.
     */
    public void addPoint(double x, double y) throws IllegalPointError
    {
        addPoint( new edu.emporia.mathtools.Point2D.Double(x,y));
    }
    
    /**
     * Adds the indicate array of points to the ones that are being used to define the
     * function. If any of the points contains an x-value that is already being used
     * it will throw and IllegalPointError.
     * 
     * @param p The array of points to be added.
     * @exception IllegalPointError Thrown is there is a duplicate x-value.
     */
    public void addPoints(edu.emporia.mathtools.Point2D.Double[] p) throws IllegalPointError
    {
        for ( int i = 0 ; i < p.length ; i++)
        {
            addPoint(p[i]);
        }
    }
    
    /**
     * Removes the indicated point from the list of points that are being used to 
     * define the function
     * 
     * @param p The point to be removed.
     */
    public void removePoint(edu.emporia.mathtools.Point2D.Double p)
    {
        points.removeElement(p);
    }
    
    /**
     * Removes the point with the indicated x and y coordinate from the list of points
     * that are used to define the functoin
     * 
     * @param x The x-coordinate of the point to be removed.
     * @param y The y-coordinate of the point to be removed.
     */
    public void removePoint(double x, double y)
    {
        removePoint ( new edu.emporia.mathtools.Point2D.Double(x,y) );
    }
    
    /**
     * Removes the point with the largest x coordinate
     */
    public void removePoint()
    {
        if ( !points.isEmpty() ) points.removeElementAt(points.size()-1);
    }
    
    /**
     * Removes all points from the list of points that are used to define the function.
     */
    public void removeAllPoints()
    {
        points.removeAllElements();
    }
    
    /**
     * Gets the array of points that are being used to define the function.
     * 
     * @return The array of points
     */
    public edu.emporia.mathtools.Point2D.Double[] getPoints()
    {
        int arraySize = points.size();
        edu.emporia.mathtools.Point2D.Double[] pointsArray = new edu.emporia.mathtools.Point2D.Double[arraySize];
        
        for ( int j=0 ; j < arraySize ; j++ )
        {
            pointsArray[j] = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(j));
        }
        return pointsArray;
    }
        
}