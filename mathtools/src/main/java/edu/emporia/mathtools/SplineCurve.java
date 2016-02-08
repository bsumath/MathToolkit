package edu.emporia.mathtools;
import java.util.Vector;


/**
 * This creates a parameterized curve whose graph is a cubic spline
 * approximation through a given set of points.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 6/12/2001
 */
public class SplineCurve extends ParametricCurve
{

    /**
     * The vector of points that define the curve
     */
    protected Vector points = new Vector();
    
    /**
     * The vector of points that are used to specify the slope of the curve at any point.
     */
    protected Vector slopes = new Vector();
    
    /**
     * A vector that keeps track of whether the slope at any given point should be 
     * calculated automatically or whether the value that is specified should be used.
     */
    protected Vector autoSlope = new Vector();

    
    /**
     * no-argument constructor
     */
    
    public SplineCurve()
    {
        tMin = 0.0;
        tMax = 0.0;
        tDelta = 0.1;
    }
    
    /**
     * Version of the construct that accepts as a parameter an array
     * of points to be used to construct the curve.
     * 
     * @param p The array of points to be added.
     */
    public SplineCurve(edu.emporia.mathtools.Point2D.Double[] p)
    {
        tMin = 0.0;
        tMax = 0.0;
        tDelta = 0.1;
        addPoints(p);
    }
    
    /**
     * Version of the constructor that takes an array of points and 
     * an array of slopes to be used to construct the curve
     * 
     * @param p The array of points. The curve will pass through each point
     * in the array
     * @param m
     */
    public SplineCurve(edu.emporia.mathtools.Point2D.Double[] p,
                        edu.emporia.mathtools.Point2D.Double[] m)
    {
        tMin = 0.0;
        tMax = 0.0;
        tDelta = 0.1;
        addPoints(p,m);
    }
    
    /**
     * Returns the point corresponding to the specified index
     * 
     * @param u The index of the point.
     * @return The point at that index. If the index is outside the bounds of the defined
     * point it returns a point both of whose coordinates are Double.NaN
     */
    public Point2D getPoint(double u)
    {
        //If there are no points return NaN
        if ( (tMax == 0.0) || (u < tMin) ) return new Point2D.Double(Double.NaN, Double.NaN);
        int pointNumber = points.size();
        int index = 0;
        
        while( (index < u) && (index < pointNumber) ) index++;
        
        if ( index >= pointNumber ) return new Point2D.Double(Double.NaN, Double.NaN);
        else if (index == 0) return (Point2D.Double)(points.elementAt(0));
        else 
        {
            edu.emporia.mathtools.Point2D.Double p1 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index - 1));
            edu.emporia.mathtools.Point2D.Double p2 = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(index));
            edu.emporia.mathtools.Point2D.Double tangent1 = (edu.emporia.mathtools.Point2D.Double)(slopes.elementAt(index - 1));
            edu.emporia.mathtools.Point2D.Double tangent2 = (edu.emporia.mathtools.Point2D.Double)(slopes.elementAt(index));
            double x1 = p1.getX();
            double x2 = p2.getX();
            double y1 = p1.getY();
            double y2 = p2.getY();
            double xPrime1 = tangent1.getX();
            double xPrime2 = tangent2.getX();
            double yPrime1 = tangent1.getY();
            double yPrime2 = tangent2.getY();
                
            double xTemp = x1*(index -u)*(index-u)*(index-u) 
                            + 3*(u-index+1)*(index-u)*(index-u)*(x1+xPrime1/3)
                            + 3*(u-index+1)*(u-index+1)*(index-u)*(x2-xPrime2/3)
                            + (u-index+1)*(u-index+1)*(u-index+1)*x2;
                                
            double yTemp = y1*(index -u)*(index-u)*(index-u) 
                            + 3*(u-index+1)*(index-u)*(index-u)*(y1+yPrime1/3)
                            + 3*(u-index+1)*(u-index+1)*(index-u)*(y2-yPrime2/3)
                            + (u-index+1)*(u-index+1)*(u-index+1)*y2;
            return new Point2D.Double(xTemp, yTemp);
        }
        
    }
    
    /**
     * Adds a point to be used to construct the curve. The curve
     * will then pass through this point
     * 
     * @param p The point to be added
     */
    public void addPoint(Point2D.Double p)
    {
        points.addElement(p);
        autoSlope.addElement(new Boolean(true));
        int pointNumber = points.size();
        if ( pointNumber < 2 )
            slopes.addElement(new edu.emporia.mathtools.Point2D.Double(1,1));
        else 
        {
            edu.emporia.mathtools.Point2D.Double pSlope, pTemp;
            tMax++;
            
            //Compute the slope at the new point to be the slope of the line between
            //it and the previous point
            pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(pointNumber-2));                
            pSlope = new edu.emporia.mathtools.Point2D.Double(p.getX()-pTemp.getX(),p.getY()-pTemp.getY());
            slopes.addElement(pSlope);
            
            if ( ((Boolean)autoSlope.elementAt(pointNumber-2)).booleanValue())
            {
                if ( pointNumber > 2)
                {
                    pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(pointNumber-3)); 
                    pSlope = new edu.emporia.mathtools.Point2D.Double(p.getX()-pTemp.getX(),p.getY()-pTemp.getY());
                }
                slopes.setElementAt(pSlope, pointNumber-2);
            }
        }
    }
    
    /**
     * Adds a point to be used to construct the curve.
     * 
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     */
    public void addPoint(double x, double y)
    {
        addPoint( new edu.emporia.mathtools.Point2D.Double(x,y));
    }
    
    /**
     * Adds a point to be used by the curve and a another point to be used as a tangent
     * vector to the curve
     * 
     * @param p The point to be added. The curve will pass through this point
     * @param slope A tangent vector to the curve at the point that is added.
     */
    public void addPoint(edu.emporia.mathtools.Point2D.Double p, edu.emporia.mathtools.Point2D.Double slope) 
    {
        edu.emporia.mathtools.Point2D.Double pSlope, pTemp;
        points.addElement(p);
        autoSlope.addElement(new Boolean(false));
        slopes.addElement(slope);
        int pointNumber = points.size();
        if ( pointNumber > 1)
        {
            tMax++;
            
            //Recalculate the slope of the previous point if it is set to autoSlope = true
            if ( ((Boolean)autoSlope.elementAt(pointNumber-2)).booleanValue())
            {
                if ( pointNumber > 2)
                    pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(pointNumber-3));
                else
                    pTemp = (edu.emporia.mathtools.Point2D.Double)(points.elementAt(pointNumber-3));
                
                pSlope = new edu.emporia.mathtools.Point2D.Double(p.getX()-pTemp.getX(),p.getY()-pTemp.getY());
                
                slopes.setElementAt(pSlope, pointNumber-2);
            }
            
        }
    }
    
    /**
     * Adds a point and specifies a tangent vector at that point.
     * 
     * @param x The x coordinate of the point to be added.
     * @param y The y-coordinate of the point to be added.
     * @param xSlope The value of dx/dt at the point that is added
     * @param ySlope The value of dy/dt at the point that is added.
     */
    public void addPoint(double x, double y, double xSlope, double ySlope)
    {
        addPoint(new edu.emporia.mathtools.Point2D.Double(x,y), 
                new edu.emporia.mathtools.Point2D.Double(xSlope,ySlope)); 
    }
    
    /**
     * Adds an array of points to be used to define the curve
     * 
     * @param p The array of points
     */
    public void addPoints(edu.emporia.mathtools.Point2D.Double[] p)
    {
        for ( int i = 0 ; i < p.length ; i++)
        {
            addPoint(p[i]);
        }
    }
    
    /**
     * Adds an array of points along with an array of slopes. If the
     * arrays aren't the same size it uses the smallest one to 
     * determine the number of points to be added.
     * 
     * @param p The array of points to be added.
     * @param m The array that will determine the slopes at those points.
     */
    public void addPoints(edu.emporia.mathtools.Point2D.Double[] p, edu.emporia.mathtools.Point2D.Double[] m)
    {
        int number = Math.min(p.length, m.length);
        for ( int i = 0 ; i < number ; i++)
        {
            addPoint(p[i], m[i]);
        }
    }
    
    /**
     * Sets the slope of the curve at the point specified.
     * 
     * @param p The point at which the slope is to be set.
     * @param slope The point that will be a tangent vector to the point.
     * @exception IllegalPointError This will be thrown if the point is not one of the data points for the curve.
     */
    public void setSlope(edu.emporia.mathtools.Point2D.Double p, edu.emporia.mathtools.Point2D.Double slope)
        throws IllegalPointError
    {
        int i = points.indexOf(p);
        if ( i >= 0)
        {
            slopes.setElementAt(slope, i);
            autoSlope.setElementAt(new Boolean(false), i);
        }
        else throw new IllegalPointError("Point not in the array");
    }
    
    /**
     * This will set the tangent vector for the curve at the point that is given.
     * 
     * @param x The x coordinate of the point where the slope is to be set.
     * @param y The y coordinate of the point where the slope is to be specified.
     * @param xSlope The value of dx/dt at the point.
     * @param ySlope The value of dy/dt at the point.
     * @exception IllegalPointError
     */
    public void setSlope(double x, double y, double xSlope, double ySlope)
        throws IllegalPointError
    {
        setSlope(new edu.emporia.mathtools.Point2D.Double(x,y), 
            new edu.emporia.mathtools.Point2D.Double(xSlope, ySlope));
    }
    
    /**
     * Removes the point from the list of points to be used
     * 
     * @param p The point to be removed
     */
    public void removePoint(edu.emporia.mathtools.Point2D.Double p)
    {
        int index = points.lastIndexOf(p);
        points.removeElementAt(index);
        slopes.removeElementAt(index);
        autoSlope.removeElementAt(index);
        if ( !points.isEmpty()) tMax--;
    }
    
    /**
     * Removes the last point that was added. If there are no points it does nothing.
     */
    public void removePoint()
    {
        if ( !points.isEmpty() ) 
        {
            int index = points.size()-1;
            points.removeElementAt(index);
            slopes.removeElementAt(index);
            autoSlope.removeElementAt(index);
            if ( !points.isEmpty()) tMax--;
        }
    }
    
    /**
     * Removes the point from the list of points to be used
     * 
     * @param x The x-coordinate of the point to be used
     * @param y The y-coordinate of the point to be used
     */
    public void removePoint(double x, double y)
    {
        removePoint ( new edu.emporia.mathtools.Point2D.Double(x,y) );
    }
    
    /**
     * Clears all the points out to go back to an empty curve.
     */
    public void removeAllPoints()
    {
        points.removeAllElements();
        tMax = 0.0;
    }
    
    /**
     * Clears all points out. (The same as removeAllPoints)
     */
    public void clearPoints()
    {
        points.removeAllElements();
        slopes.removeAllElements();
        autoSlope.removeAllElements();
        tMax = 0.0;
    }
    
    /**
     * Gets the slopes that are used to construct the slopes.
     * 
     * @return An array of points that are used to construct the curve. They will all be tangent vectors.
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
     * Gets the array of points that are being used to define the curve
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