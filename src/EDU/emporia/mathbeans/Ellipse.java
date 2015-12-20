package EDU.emporia.mathbeans;
import java.awt.*;
import EDU.emporia.mathtools.*;


/**
 * A paramerized ellipse
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/3/00
 */
public class Ellipse extends EDU.emporia.mathtools.ParametricCurve
{

	public Ellipse()
	{
	    super();
        tMin = 0;
        tMax = 6.2832;
	}

	/**
	 * Sets the x-coordinate of the center of the ellipse
	 * 
	 * @param xCenter The x-coordinate of the center
	 */
	public void setXCenter(double xCenter)
	{
		this.xCenter = xCenter;
	}

	/**
	 * Gets the x-coordinate of the center of the ellipse
	 * 
	 * @return The x-coordinate of the center
	 */
	public double getXCenter()
	{
		return this.xCenter;
	}

	/**
	 * Sets the y-coordinate of the center of the ellipse
	 * 
	 * @param yCenter The y-coordinate of the center
	 */
	public void setYCenter(double yCenter)
	{
		this.yCenter = yCenter;
	}

	/**
	 * Gets the y-coordinate of the center of the ellipse
	 * 
	 * @return The y-coordinate of the center
	 */
	public double getYCenter()
	{
		return this.yCenter;
	}

	/**
	 * Sets the radius of the axis of the ellipse that is parallel to the x-axis
	 * 
	 * @param xRadius The radius of the axis of the ellipse that is parallel to the x-axis
	 */
	public void setXRadius(double xRadius)
	{
		this.xRadius = xRadius;
	}

	/**
	 * Gets the radius of the axis of the ellipse that is parallel to the x-axis
	 * 
	 * @return The radius of the axis of the ellipse that is parallel to the x-axis
	 */
	public double getXRadius()
	{
		return this.xRadius;
	}

	/**
	 * Sets the radius of the axis of the ellipse that is parallel to the y-axis
	 * 
	 * @param yRadius The radius of the axis of the ellipse that is parallel to the y-axis
	 */
	public void setYRadius(double yRadius)
	{
		this.yRadius = yRadius;
	}

	/**
	 * Gets the radius of the axis of the ellipse that is parallel to the y-axis
	 * 
	 * @return The radius of the axis of the ellipse that is parallel to the y-axis
	 */
	public double getYRadius()
	{
		return this.yRadius;
	}

	/**
	 * The point on the ellipse at the indicated parameter
	 * 
	 * @param u The parameter
	 * @return The point
	 */
	public EDU.emporia.mathtools.Point2D getPoint(double u)
	{
		double xTemp = xCenter + xRadius * Math.cos(u);
        double yTemp = yCenter + yRadius * Math.sin(u);
        return new Point2D.Double(xTemp, yTemp);
	}


	/**
	 * The radius in the y-direction
	 */
	protected double yRadius = 1;

	/**
	 * The radius in the x-direction
	 */
	protected double xRadius = 1;

	/**
	 * The y-coordinate of the center
	 */
	protected double yCenter = 0;

	/**
	 * The x-coordinate of the center
	 */
	protected double xCenter = 0;

}