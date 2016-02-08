package edu.emporia.mathtools;
import java.util.*;


/**
 * A class that constructs a cubic spline passing through a 
 * specified set of points. The slope at each point can be
 * specified or generated automatically.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 6/6/2001
 */
public class SplineFunction extends edu.emporia.mathtools.MathFunction
{
    
    /**
     * The Vector that will contain the points used to define the function. They will
     * always be listed in increasing order of x-value.
     */
    protected Vector points = new Vector();
    
    /**
     * The Vector that will be used to define the slopes that will be
     * used to define the curve. The slope at a particular index will
     * correspond to the point at that same index in the points Vector.
     */
    protected Vector slopes = new Vector();
    
    /**
     * An Vector of boolean values that keeps track of whether the slope
     * at each point should stay fixed or should be automatically 
     * adjusted as new points are added.
     */
    protected Vector autoSlope = new Vector();
    
    
    public SplineFunction() 
    {
        super();
    }
    
    public SplineFunction(edu.emporia.mathtools.Point2D.Double[] p)
    {
        super();
        try{
            addPoints(p);
        }catch(IllegalPointError e){}
    }
        
    
    /**
     * Overrides the functionValue method of the MathFunction class. 
     * This method returns the value of the function at the 
     * specified x-coordinate
     * 
     * @param u The x-coordinate
     * @return f(x)
     */
    public double functionValue(double u)
    {
        int index = 0;
        double yValue;
        double x1=0, y1=0, x2=0, y2=0, m1=0, m2=0;
        int pointNumber = points.size();
        edu.emporia.mathtools.Point2D.Double p1 = new edu.emporia.mathtools.Point2D.Double(),
            p2 = new edu.emporia.mathtools.Point2D.Double();
        
        if ( pointNumber == 0) yValue = 0;
        else
        {
            p2 = (edu.emporia.mathtools.Point2D.Double)points.elementAt(0);
            x2 = p2.getX();
            
            // Make the curve a straight line to the left of the first point.
            if ( (u <= x2) || (pointNumber == 1) )
            {
                m2 = ((Double)(slopes.elementAt(0))).doubleValue();
                yValue = p2.getY() + m2*(u-x2);
            }
            else
            {   
                //find the first point whose x-coordinate is to the right of u
                while ( ( ++index < pointNumber ) && ( u > x2 ) )
                {
                    p1 = p2;
                    x1 = x2;
                    p2 = (edu.emporia.mathtools.Point2D.Double)points.elementAt(index);
                    x2 = p2.getX();
                }
            
                //If u is to the right of all x-values make the graph a straight line. 
                if ( u >= x2 )
                {
                    m2 = ((Double)(slopes.elementAt(index-1))).doubleValue();
                    yValue = p2.getY() + m2*(u-x2);
                }
                else
                {
                   y1 = p1.getY();
                   
                   m1 = ((Double)(slopes.elementAt(index-2))).doubleValue();
                   m2 = ((Double)(slopes.elementAt(index-1))).doubleValue();
                   y2 = p2.getY();
                   
                   double a,b;
                   
                   a = (m2 + m1)/((x2-x1)*(x2-x1)) - 2*(y2-y1)/((x2-x1)*(x2-x1)*(x2-x1));
                   b = 3*(y2-y1)/((x2-x1)*(x2-x1)) - (2*m1+m2)/(x2-x1);
                   
                   //Cubic spline formula
                   yValue = y1 + m1*(u-x1) + b*(u-x1)*(u-x1)+ a*(u-x1)*(u-x1)*(u-x1);
                }
            }
        }

        return yValue;
    }
    /**
     * Adds the indicated point to the list of points used to construct the function. 
     * The graph will then pass through the specified point. It will throw an exception 
     * if there is already a point with that x-coordinate. The slope
     * at the point will generated automatically.
     * 
     * @param p The point to be added.
     * @exception IllegalPointError
     */
    
    public void addPoint(edu.emporia.mathtools.Point2D.Double p) throws IllegalPointError
    {
        if (points.isEmpty())
        {
            points.addElement(p);
            slopes.addElement(new Double(0));
            autoSlope.addElement(new Boolean(true));
        }
        else 
        {
            edu.emporia.mathtools.Point2D.Double p1, p2;
            int pointNumber = points.size();
            int index = 0;
            double u = p.getX();
            double xTemp = 0;
            double m = 0;
           
            do 
            { 
                xTemp = ((Point2D.Double)(points.elementAt(index))).getX();
            }while( (u > xTemp)&& (++index < pointNumber));
            
            if ( xTemp == u ) throw new IllegalPointError("Duplicate x-value");
            else
            {
                points.insertElementAt(p,index);
                autoSlope.insertElementAt(new Boolean(true), index);
                if ( index == 0)
                {
                    p1 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(1));
                    m = (p1.getY()-p.getY())/(p1.getX()-p.getX());
                    slopes.insertElementAt(new Double(m),index);
                    if(((Boolean)(autoSlope.elementAt(1))).booleanValue())
                    {
                        if( pointNumber > 1) 
                        {
                            p2 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(2));
                            m = (p2.getY()-p.getY())/(p2.getX()-p.getX());                        
                        }             
                        slopes.setElementAt(new Double(m),1); 
                    }
                }
                else if ( index == pointNumber)
                {
                    p1 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index -1));
                    m = (p1.getY()-p.getY())/(p1.getX()-p.getX());
                    slopes.insertElementAt(new Double(m),index);
                    if((((Boolean)(autoSlope.elementAt(index-1))).booleanValue()))
                    {
                        if( pointNumber > 1) 
                        {
                            p2 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index -2));
                            m = (p2.getY()-p.getY())/(p2.getX()-p.getX());                        
                        }
                        slopes.setElementAt(new Double(m),index -1);   
                    }
                }
                else
                {
                    p1 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index-1));
                    p2 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index+1));
                    m = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
                    slopes.insertElementAt(new Double(m),index);
                    if((((Boolean)(autoSlope.elementAt(index -1))).booleanValue()))
                    {
                        if( index > 1) 
                            p1 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index-2));
                        m = (p1.getY()-p.getY())/(p1.getX()-p.getX()); 
                        slopes.setElementAt(new Double(m),index -1);   
                    }
                    if((((Boolean)(autoSlope.elementAt(index+1))).booleanValue()))
                    {
                        if( index < (pointNumber - 1) ) 
                            p2 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index+2));
                        m = (p2.getY()-p.getY())/(p2.getX()-p.getX()); 
                        slopes.setElementAt(new Double(m),index+1);   
                    }
                }
            } 
        }
    }
    
      /**
     * Adds the indicated points to the list of points and specifies
     * the slope of the curve at that point. The graph of the function
     * will then pass through the added point with the slope specified.
     * 
     * @param p The point to be added.
     * @param slope The slope of the curve at that point.
     * @exception IllegalPointError This will be thrown when there is already a point with the
     * indicated x-value.
     */
    public void addPoint(edu.emporia.mathtools.Point2D.Double p, double slope) 
        throws IllegalPointError
    {
        if (points.isEmpty())
        {
            points.addElement(p);
            slopes.addElement(new Double(slope));
            autoSlope.addElement(new Boolean(false));
        }
        else 
        {
            edu.emporia.mathtools.Point2D.Double pTemp;
            int pointNumber = points.size();
            int index = 0;
            double u = p.getX();
            double xTemp = 0;
            double m = 0;
           
            do 
            { 
                xTemp = ((Point2D.Double)(points.elementAt(index))).getX();
            }while( (u > xTemp)&& (++index < pointNumber));
            
            if ( xTemp == u ) throw new IllegalPointError("Duplicate x-value");
            else
            {
                points.insertElementAt(p,index);
                autoSlope.insertElementAt(new Boolean(false), index);
                slopes.insertElementAt(new Double(slope),index);
                if ( index == 0)
                {
                    if(((Boolean)(autoSlope.elementAt(1))).booleanValue())
                    {
                        if( pointNumber > 1) 
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(2));
                        else
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(1));
                            
                        m = (pTemp.getY()-p.getY())/(pTemp.getX()-p.getX());                        
                        slopes.setElementAt(new Double(m),1); 
                    }
                }
                else if ( index == pointNumber)
                {
                   if((((Boolean)(autoSlope.elementAt(index-1))).booleanValue()))
                    {
                        
                        if( pointNumber > 1) 
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index-2));
                        else
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index-1));
                        
                        m = (pTemp.getY()-p.getY())/(pTemp.getX()-p.getX());
                        slopes.setElementAt(new Double(m),index-1);   
                    }
                }
                else
                {
                    if((((Boolean)(autoSlope.elementAt(index-1))).booleanValue()))
                    {
                        if( index > 1) 
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index-2));
                        else
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index-1));
                        m = (pTemp.getY()-p.getY())/(pTemp.getX()-p.getX()); 
                        slopes.setElementAt(new Double(m),index-1);   
                    }
                    if((((Boolean)(autoSlope.elementAt(index+1))).booleanValue()))
                    {
                        if( index < (pointNumber - 1) ) 
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index+2));
                        else
                            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index+1));
                        m = (pTemp.getY()-p.getY())/(pTemp.getX()-p.getX()); 
                        slopes.setElementAt(new Double(m),index+1);   
                    }
                }
            } 
        }
    }
    
    /**
     * Adds a point with the given x and y coordinates and specifies the
     * slope at that point.
     * 
     * @param x The x coordinate of the point to be added.
     * @param y The y coordinate of the point to be added.
     * @param slope The slope of the curve at the added point.
     * @exception IllegalPointError Thrown when there is already a point with the given 
     * x coordinate.
     */
    public void addPoint(double x, double y, double slope) 
        throws IllegalPointError
    {
        addPoint(new edu.emporia.mathtools.Point2D.Double(x,y),slope);
    }
    
    /**
     * Adds the point with the specified x and y coordinate. It throws an IllegalPointError
     * if there is already a point with the given x-coordinate. The
     * slope is generated automatically.
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
     * it will throw and IllegalPointError. The slopes will be 
     * generated automatically.
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
        int index = points.lastIndexOf(p);
        points.removeElementAt(index);
        slopes.removeElementAt(index);
        autoSlope.removeElementAt(index);
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
        if ( !points.isEmpty() ) 
        {
            int index = points.size()-1;
            points.removeElementAt(index);
            slopes.removeElementAt(index);
            autoSlope.removeElementAt(index);
        }
    }
    
    /**
     * Removes all points from the list of points that are used to define the function.
     */
    public void removeAllPoints()
    {
        points.removeAllElements();
        slopes.removeAllElements();
        autoSlope.removeAllElements();
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
    
    /**
     * Gets the array of slopes that are being used to define the curve.
     * 
     * @return The array of slopes.
     */
    public double[] getSlopes()
    {
        int arraySize = slopes.size();
        double[] slopesArray = new double[arraySize];
        
        for ( int j=0 ; j < arraySize ; j++ )
        {
            slopesArray[j] = ((Double)(slopes.elementAt(j))).doubleValue();
        }
        return slopesArray;
    }
    
    /**
     * Sets the slope of the curve at the given point
     * 
     * @param p The point at which the slope is to be set.
     * @param m The slope at the indicated point.
     * @exception IllegalPointError
     */
    public void setSlope(edu.emporia.mathtools.Point2D.Double p, double m)
        throws IllegalPointError
    {
        int i = points.indexOf(p);
        if ( i >= 0)
        {
            slopes.setElementAt(new Double(m), i);
            autoSlope.setElementAt(new Boolean(false), i);
        }
        else throw new IllegalPointError("Point not in the array");
    }
        
}