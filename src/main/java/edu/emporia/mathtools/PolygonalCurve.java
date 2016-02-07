package EDU.emporia.mathtools;
import java.util.Vector;


/**
 * This creates a parameterized polygonal curve whose graph is a series of 
 * line segments between specified points
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/9/00
 */
public class PolygonalCurve extends ParametricCurve
{

    /**
     * The vector of points that define the polygonal curve
     */
    public Vector points = new Vector();

    /**
     * Keeps track of whether or not the next point to be added is the
     * first point since the first point added is treated differently
     * from the others
     */
    boolean first = true;
    /**
     * no-argument constructor
     */
    
    public PolygonalCurve()
    {
        tMin = 0.0;
        tMax = 0.0;
        tDelta = 1;
    }
    
    /**
     * Returns the point correponding to the specified index
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
            Point2D.Double p1 = (Point2D.Double)(points.elementAt(index - 1));
            Point2D.Double p2 = (Point2D.Double)(points.elementAt(index));
            double x1 = p1.getX();
            double x2 = p2.getX();
            double y1 = p1.getY();
            double y2 = p2.getY();
            double xTemp = x2*(u - index + 1) + x1*(index - u);
            double yTemp = y2*(u - index + 1) + y1*(index - u);
            return new Point2D.Double(xTemp, yTemp);
        }
        
    }
    
    /**
     * Adds a point to be used to construct the polygon
     * 
     * @param p The point to be added
     */
    public void addPoint(Point2D.Double p)
    {
        points.addElement(p);
        if ( first ) first = false;
        else tMax++;
    }
    
    /**
     * Adds a point to be used to construct the polygon
     * 
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     */
    public void addPoint(double x, double y)
    {
        addPoint( new Point2D.Double(x,y));
    }
    
    /**
     * Adds an array of points to be used to define the polygon
     * 
     * @param p The array of points
     */
    public void addPoints(Point2D.Double[] p)
    {
        for ( int i = 0 ; i < p.length ; i++)
        {
            addPoint(p[i]);
        }
    }
    
    /**
     * Removes the point from the list of points to be used
     * 
     * @param p The point to be removed
     */
    public void removePoint(Point2D.Double p)
    {
        points.removeElement(p);
        if ( points.isEmpty()) first = true;
        else tMax--;
    }
    
    /**
     * Removes the point from the list of points to be used
     * 
     * @param x The x-coordinate of the point to be used
     * @param y The y-coordinate of the point to be used
     */
    public void removePoint(double x, double y)
    {
        removePoint ( new Point2D.Double(x,y) );
    }
    
    /**
     * Clears all the points out to go back to an empty curve.
     */
    public void removeAllPoints()
    {
        points.removeAllElements();
        first = true;
        tMax = 0.0;
    }
    
    public void clearPoints()
    {
        points.removeAllElements();
        first = true;
        tMax = 0.0;
    }
    
    /**
     * Gets the array of points that are being used to define the curve
     */
    public Point2D.Double[] getPoints()
    {
        int arraySize = points.size();
        Point2D.Double[] pointsArray = new Point2D.Double[arraySize];
        
        for ( int j=0 ; j < arraySize ; j++ )
        {
            pointsArray[j] = (Point2D.Double)(points.elementAt(j));
        }
        return pointsArray;
    }
}