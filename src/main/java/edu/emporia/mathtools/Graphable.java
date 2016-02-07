package EDU.emporia.mathtools;

/**
 * This Interface should be used by any class to allow it to be graphed
 * on the MathGrapher.
 * 
 * @author Joe Yanik
 * @author Chuck Pheatt
 * @version 1.1
 * @since 3/1/00
 * @see MathFunction
 */

public interface Graphable
{

	/**
	 * Provides a title for the Graphable object that will be
	 * displayed on the coordinate system when it is selected.
	 * 
	 * @return The title for the Graphable object.
	 */
	public java.lang.String getTitle();
	
	/**
	 * Returns the point that corresponds to the parameter
	 * 
	 * @param u The parameter
	 * @return The point occurring at the parameter
	 * @exception EDU.emporia.mathtools.Graphable_error
	 */
	public Point2D getPoint(double u) throws Graphable_error;                           

	/**
	 * Returns an array of points that can be used in
	 * constructing a graph.
	 * 
	 * @param uMin The lowest parameter for constructing the array.
	 * @param uMax The highest parameter for constructing the array
	 * @param numberOfPoints The number of points in the array
	 * @return An array of points on the graph corresponding to
	 * equally spaced parameters
	 * @exception EDU.emporia.mathtools.Graphable_error
	 */
	public Point2D[] getPoints(double uMin, 
	                             double uMax, 
	                             int numberOfPoints) throws Graphable_error;                             
	
	/**
	 * Returns and array of points that can be used in
	 * graphing the curve
	 * 
	 * @param uMin The lowest parameter used to construct the array
	 * @param uMax The highest parameter used to construct the array
	 * @param delta
	 * @return An array of points constructing using parameters
	 * between uMin and uMax spaced delta units apart
	 * @exception EDU.emporia.mathtools.Graphable_error
	 */
	public Point2D[] getPoints(double uMin, 
	                             double uMax, 
	                             double delta) throws Graphable_error;  
	                                 
}