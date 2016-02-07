package EDU.emporia.mathtools;


/**
 * This is the base class for all parametric curves
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/7/00
 */
public class ParametricCurve implements EDU.emporia.mathtools.Graphable
{

    /**
     * The title describing the curve
     */
    protected String title = "";

    /**
     * The function describing the y-parameter for the curve
     */
    /**
     * The function describing the x-parameter for the curve
     */
    protected MathFunction x,y;

    /**
     * The suggested distance between t-values to be used in sketching the curve
     */
    /**
     * The maximum suggested t-value to be used in sketching the curve
     */
    /**
     * The minimum suggested t-value to be used in sketching the curve
     */
    protected double tMin, tMax, tDelta;
    /**
     * no-argument constructor
     */
    
    public ParametricCurve()
    {
        this(new MathFunction(), new MathFunction());
    }
    
    /**
     * Version of the constructor that allows the curve to be described by two MathFunction
     * objects
     * 
     * @param x The function defining the x-parameter
     * @param y The function defining the y-parameter
     */
    public ParametricCurve(MathFunction x, MathFunction y)
    {
        this.x = x;
        this.y = y;
        tMin = -10;
        tMax = 10;
        tDelta = .1;
    }
    
    /**
     * Sets the title used to describe the curve.
     * 
     * @param title The title used to describe the curve.
     */
    public void setTitle( String title )
    {
        this.title = title;
    }
    
    /**
     * Gets the title used to describe the curve.
     * 
     * @return The title used to describe the curve.
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     * Gets the point on the curve at the specified parameter.
     * 
     * @param u The parameter for the point
     * @return A point on the curve.
     */
    public Point2D getPoint(double u)
    {
        return new Point2D.Double(x.functionValue(u),
                                    y.functionValue(u));  
    }
    
    /**
     * Gets an array of points on the curve between the two specified t-values
     * 
     * @param uMin The minimum t-value determining the array
     * @param uMax The maximum t-value determining the array
     * @param numberOfPoints The number of points in the array
     * @return An array of points on the curve
     */
    public Point2D[] getPoints(double uMin, 
	                             double uMax, 
	                             int numberOfPoints)
	                             
	{
	    double delta;
	    
	    if ( numberOfPoints > 1)
	        delta = (uMax - uMin)/(numberOfPoints - 1);
	    else
	        delta = uMax - uMin;
	    
	    Point2D pointArray[] = new Point2D[numberOfPoints];
	    
	    for ( int i = 0; i < numberOfPoints ; i++)
	    {
	        double u = uMin + i * delta;
	        pointArray[i] = getPoint(u);
	    }
	    
	    return pointArray;
	}
	
	/**
	 * Gets an array of points on the curve between the two specified t-values
	 * 
	 * @param uMin The minimum t-value determining the array
	 * @param uMax The maximum t-value determining the array.
	 * @param delta The distance between subsequent t-values used in creating the array.
	 * @return An array of points on the curve
	 */
	public Point2D[] getPoints(double uMin, 
	                             double uMax, 
	                             double delta)
	{
	    int numberOfPoints = 0;
	    for (double temp = uMin; temp < uMax ; 
	            temp = uMin + ++numberOfPoints * delta);
	    numberOfPoints++;
	    return getPoints(uMin, uMax, numberOfPoints);
	}
	
	/**
	 * Sets the minimum suggested value of t that to be used in parameterizing the curve
	 * 
	 * @param tMin The minimum suggested value of t that will be used in parameterizing the curve
	 */
	public void setTMin( double tMin)
	{
	    this.tMin = tMin;
	}
	
	/**
	 * Gets the minimum suggested value of t that to be used in parameterizing the curve
	 * 
	 * @return The minimum suggested value of t that to be used in parameterizing the curve
	 */
	public double getTMin()
	{
	    return tMin;
	}
	
	/**
	 * Sets the maximum suggested value of t that to be used in parameterizing the curve
	 * 
	 * @param tMax The maximum suggested value of t that to be used in parameterizing the curve
	 */
	public void setTMax(double tMax)
	{
	    this.tMax = tMax;
	}
	
	/**
	 * Gets the maximum suggested value of t that to be used in parameterizing the curve
	 * 
	 * @return The maximum suggested value of t that to be used in parameterizing the curve
	 */
	public double getTMax()
	{
	    return tMax;
	}
	
	/**
	 * Sets the suggested distance between subsequent t-values in sketching the curve
	 * 
	 * @param tDelta The suggested distance between subsequent t-values in sketching the curve
	 */
	public void setTDelta(double tDelta)
	{
	    this.tDelta = tDelta;
	}
	
	/**
	 * Gets the suggested distance between subsequent t-values in sketching the curve
	 * 
	 * @return The suggested distance between subsequent t-values in sketching the curve
	 */
	public double getTDelta()
	{
	    return tDelta;
	}
    
}