package EDU.emporia.mathtools;

/**
 * A base class for all mathematical functions. It includes the capablility
 * of combining MathFunction objects to create new ones using composition,
 * addition, and multiplication
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/7/00
 */
public class MathFunction implements Graphable
{
    /**
     * The second MathFunction object to be used in when combining two
     */

    /**
     * T
     */
    /**
     * The first MathFunction object to be used in when combining two
     */
    MathFunction firstFunction, secondFunction;

    /**
     * Parameter used to keep track of the method of combining functions
     */
    protected int combineMode;

    /**
     * The title used to describe the function
     */
    protected String title = "";
    
    /**
     * Static constant corresponding to the default combine mode. Results
     * in the identity function
     */
    public static final int NORMAL = 0;

    /**
     * Static constant corresponding to the additive combine mode.
     */
    public static final int PLUS = 1;

    /**
     * Static constant corresponding to the subtraction combine mode.
     */
    public static final int MINUS = 2;

    /**
     * Static constant corresponding to the multiplicative combine mode.
     */
    public static final int TIMES = 3;

    /**
     * Static constant corresponding to the division combine mode.
     */
    public static final int DIVIDEDBY = 4;

    /**
     * Static constant corresponding to the composition combine mode.
     */
    public static final int COMPOSEDWITH = 5;

    /**
     * Static constant corresponding to a constant function when used
     * for the combineMode
     */
    public static final int CONSTANT = 6;
    
    /**
     * The value used for the constant when in CONSTANT combineMode.
     */
    protected double constantValue = 0;
    
    /**
     * Static constant corresponding to the indentity function
     */
    public static final MathFunction IDENTITY = new MathFunction();
    
    
    /**
     * no-argument constructor. Correponds to the identity function
     */
    public MathFunction()
    {
        combineMode = NORMAL;
    }
    
    /**
     * A version of the constructor that will create a new MathFunction object from 
     * two specified MathFunction objects according to the specified combineMode
     * 
     * @param f The first MathFunction object
     * @param combineMode A parameter that will determine the way in which they are combined
     * @param g The second MathFunction object
     */
    public MathFunction(MathFunction f, int combineMode, MathFunction g)
    {
        this.combineMode=combineMode;
        firstFunction = f;
        secondFunction = g;        
    }
    
    /**
     * A version of the constructor that will create a constant function based on the
     * specified constant.
     * 
     * @param u The constant to be used to define the constant function
     */
    public MathFunction(double u)
    {
        constantValue = u;
        combineMode = CONSTANT;
    }
        
    /**
     * Sets the title to be displayed to describe the function.
     * 
     * @param title The title to be displayed to describe the function.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    
    /**
     * Gets the title to be displayed to describe the function.
     * 
     * @return The title to be displayed to describe the function.
     */
    public String getTitle ( )
    {
        return title;
    }
    
    /**
     * Gets the point on the graph of the function at the specified x-coordinate
     * 
     * @param u The x-coordinate
     * @return The point on the graph
     */
    public Point2D getPoint(double u)
    {
        return new Point2D.Double(u, functionValue(u));   
    }
    
    /**
     * Gets an array of points between the two indicated x-values
     * 
     * @param uMin The smallest x-value
     * @param uMax The largest x-value
     * @param numberOfPoints The number of points in the array
     * @return An array of points between the two specified x-values
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
	 * Gets an array of points on the graph of the function between two specified 
	 * x-values with a distance of delta between subsequent x-values
	 * 
	 * @param uMin The smallest x-value
	 * @param uMax The largest x-value
	 * @param delta The distance between subsequent x-values
	 * @return An array of points on the graph of the function.
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
	 * Gets the value of the function at the specified x-value
	 * 
	 * @param u The x-value
	 * @return The function value at the given x-val;ue
	 */
	public double functionValue(double u)
	{
	    double yValue;
        switch ( combineMode )
        {
            case NORMAL:
                yValue = u; 
                break; 
            case PLUS:
                yValue = firstFunction.functionValue(u)
                    + secondFunction.functionValue(u);
                break;
            case MINUS:
                yValue = firstFunction.functionValue(u)
                    - secondFunction.functionValue(u);
                break;
            case TIMES:
                yValue = firstFunction.functionValue(u)
                    * secondFunction.functionValue(u);
                break;        
            case DIVIDEDBY:
                yValue = firstFunction.functionValue(u)
                    / secondFunction.functionValue(u);
                break; 
            case COMPOSEDWITH:
                yValue = firstFunction.functionValue(secondFunction.functionValue(u)); 
                break; 
            case CONSTANT:
                yValue = constantValue; 
                break; 
            default:
                yValue = u;
        }
        return yValue;
    }
    
    /**
     * Picks a random number between the specifiend numbers
     * 
     * @param a The lower bound of the interval
     * @param b The upper bound of the interval
     * @return A random number in the specified interval
     */
    public double pickRandom( double a, double b)
    {
        if ( a < b )
        {
            return  (Math.random()*(b-a) + a);
        }
        else return a;
    }
    
    /**
     * By sampling over the interval from -10 to 10 this method determines
     * whether or not the MathFunction appears to be equal to the 
     * specified MathFunction object
     * 
     * @param f The function to compare to.
     * @return True if they are probably equal. False otherwise
     */
    public boolean probablyEquals(MathFunction f)
    {
        return probablyEquals(f,-10,10);
    }
    
    /**
     * Version of the probablyEquals method that allows the sampling
     * interval to be specified
     * 
     * @param f The function to compare to
     * @param xMin The lower bound of the sampling interval
     * @param xMax The upper bound of the sampling interval
     * @return True if the function appear to be equal. False otherwise
     */
    public boolean probablyEquals(MathFunction f, double xMin, double xMax)
    {
        boolean equals = true;
        double delta = (xMax - xMin)/20;
        
        for ( double t = 0 ; t < 20 ; t++)
        {
            double u = pickRandom(xMin + t*delta, xMin + (t+1)*delta);
            if ( Math.abs(functionValue(u)-f.functionValue(u)) > .00000000001)
                equals = false;  
        }
        
        return equals;
    }
    
    /**
     * Creates a new MathFunction object that correponds to the composition
     * of this one with the specified MathFunction
     * 
     * @param f The MathFunction object to be composed with.
     * @return The composition of the two
     */
    public MathFunction composedWith(MathFunction f)
    {
        return new MathFunction(this, COMPOSEDWITH, f);
    }
    /**
     * Creates a new MathFunction object that is the sum of this one with
     * the one specified
     * 
     * @param f The function to be added
     * @return The sum of the two functions.
     */
    
    public MathFunction plus(MathFunction f)
    {
        return new MathFunction(this, PLUS, f);
    }
    
    /**
     * Creates a new MathFunction object that is the sum of this one with
     * the constant specified
     * 
     * @param c The constant to be added
     * @return The function plus the specified constant
     */
    public MathFunction plus(double c)
    {
        return new MathFunction(this, PLUS, new MathFunction(c));
    }
    
    /**
     * Creates a new MathFunction object that is the difference of this 
     * one with the one specified
     * 
     * @param f The function to be subtracted
     * @return The difference of the two functions.
     */
    public MathFunction minus(MathFunction f)
    {
        return new MathFunction(this, MINUS, f);
    }
    
    /**
     * Creates a new MathFunction object that is the difference of this 
     * one with the constant specified
     * 
     * @param c The constant to be subtracted
     * @return The function minus the specified constant
     */
    public MathFunction minus(double c)
    {
        return new MathFunction(this, MINUS, new MathFunction(c));
    }
    
    /**
     * Creates a new MathFunction object that is the product of this one with
     * the one specified
     * 
     * @param f The function to be multiplied
     * @return The product of the function
     */
    public MathFunction times(MathFunction f)
    {
        return new MathFunction(this, TIMES, f);
    }
    
    /**
     * Creates a new MathFunction object that is the product of this one with
     * the constant specified
     * 
     * @param c The constant to be multiplied
     * @return The product of the function and the specified constant
     */
    public MathFunction times(double c)
    {
        return new MathFunction(this, TIMES, new MathFunction(c));
    }
    
    /**
     * Creates a new MathFunction object that is the quotient of this one with
     * the one specified
     * 
     * @param f The function to appear in the denominator of the quotient
     * @return The quotient of the functions
     */
    public MathFunction dividedBy(MathFunction f)
    {
        return new MathFunction(this, DIVIDEDBY, f);
    }
}