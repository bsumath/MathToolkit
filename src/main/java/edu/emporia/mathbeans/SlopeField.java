package EDU.emporia.mathbeans;
import java.awt.*;
import java.awt.Color;
import EDU.emporia.mathtools.*;
import java.util.*;


/**
 * This is a class that will construct a slope field 
 * corresponding to a first order differential equation.
 * It is also capable of graphing an approximation to 
 * a solution using Euler's method.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 6/1/2001
 */
public class SlopeField extends EDU.emporia.mathbeans.MathGrapher
{

    /**
     * This determines the number of iterations to be used when
     * graphing an approximation according to Euler's method.
     * The actual number of points is actually twice this number
     * since it graphs the curve in both the positive and negative
     * direction. The default is 200.
     */
    protected int eulerPointNumber = 200;

	/**
	 * When this is true the user will be able to add a data
	 * point by clicking on the slope field with the mouse.
	 * This will result in a curve being drawn that approximates
	 * a solution to the differential equation.
	 */
	protected boolean eulerMouseEnabled = true;

	/**
	 * This determines the length in pixels of the line 
	 * segments used to show the slope field. It must be
	 * a positive integer.
	 */
	protected int slopeRadius = 2;

	/**
	 * This is the formula that is used for the derivative of
	 * y in the differential equation. It should be a expressed
	 * in terms of x and y. The default value is x.
	 */
	protected java.lang.String yDerivative = "";

	/**
	 * This is the color to be used to draw the slope field
	 * The default color is black.
	 */
	protected java.awt.Color fieldColor = Color.black;

	/**
	 * This is the delta that is used in Euler's approximation
	 * to get the next point. Smaller values will result in 
	 * more accuracy. The default is 0.1
	 */
	protected double eulerDelta = 0.1;

	/**
	 * This is the formula that is used for the derivative of
	 * x. If it is intended to represent a single differential
	 * equation of the form y'=f(x,y), then this should have value
	 * 1. The default is 1.
	 */
	protected java.lang.String xDerivative = "";

	/**
	 * This determines the color that will be used to draw the
	 * graph that approximates the solution. The default is a
	 * navy blue.
	 */
	protected java.awt.Color eulerGraphColor = new Color(102,102,153);

	/**
	 * This is a Vector of points. Each point will be used as
	 * a starting point for an Euler approximation to a solution.
	 */
	protected Vector dataPoints = new Vector();
	
	// The expression objects used by the parser.
     
    private Expr xExpression, yExpression; 

    /*
     The Variable objects corresponding to the values of x
      and y that will be used by the parser.
     */
    private Variable xValue, yValue;

	public SlopeField()
	{
		//{{INIT_CONTROLS
		setLayout(null);
		setSize(200,200);
		//}}
		xValue = Variable.make ("x");
        yValue = Variable.make("y");
        Variable.make ("Pi").set_value (Math.PI);
        Variable.make ("e").set_value (Math.E);
        Variable.make ("NaN").set_value (Double.NaN);
        Variable.make ("infinity").set_value (Double.POSITIVE_INFINITY);
        Variable.make ("negativeInfinity").set_value (Double.NEGATIVE_INFINITY);
        Variable.make ("maxValue").set_value (Double.MAX_VALUE);
        Variable.make ("minValue").set_value (Double.MIN_VALUE);
        Variable.make ("randomValue").set_value (java.lang.Math.random());
        try{
            setXDerivative("1");
            setYDerivative("x");
        }catch(Graphable_error e){System.out.println(e.toString());}
	}

	//{{DECLARE_CONTROLS
	//}}
	
	public void paintComponent(java.awt.Graphics g)
	{
		super.paintComponent(g);
        
        double xTempMath, yTempMath;
        
        g.setColor(fieldColor);
              
        if (xMaxCurrent > 0)
        {
            xTempMath = xZeroMath;
            for (double j=0 ; (xTempMath < xMaxCurrent) && (j < 50);
                                xTempMath = xZeroMath + (++j)*xMark)
            {
                if (yMaxCurrent > 0)
                {
                    yTempMath = yZeroMath;
                    for (double k=0 ; (yTempMath < yMaxCurrent) && (k < 50);
                                        yTempMath = yZeroMath + (++k)*yMark)
                    {
                        drawSlopeLine(g,xTempMath,yTempMath);
                    }
                }
                if (yMinCurrent < 0)
                {                    
                    double k = (yMaxCurrent > 0 ? 1 : 0);
                    for(yTempMath = yZeroMath - k*yMark ;(yTempMath > yMinCurrent) && (k < 50);
                        yTempMath = yZeroMath - (++k)*yMark )
                    {
                        drawSlopeLine(g,xTempMath,yTempMath);
                    }
                }            
            }
        }
        
        if (xMinCurrent < 0)
        {
            xTempMath = xZeroMath;
            for (double j=0 ; (xTempMath > xMinCurrent) && (j < 50);
                                xTempMath = xZeroMath - (++j)*xMark)
            {
                if (yMaxCurrent > 0)
                {
                    yTempMath = yZeroMath;
                    for (double k=0 ; (yTempMath < yMaxCurrent) && (k < 50);
                                        yTempMath = yZeroMath + (++k)*yMark)
                    {
                        drawSlopeLine(g,xTempMath,yTempMath);
                    }
                }
                if (yMinCurrent < 0)
                {
                    double k = (yMaxCurrent > 0 ? 1 : 0);
                    
                    for (yTempMath = yZeroMath - k*yMark; 
                        (yTempMath > yMinCurrent) && (k < 50);
                        yTempMath = yZeroMath - (++k)*yMark)
                    {
                        drawSlopeLine(g,xTempMath,yTempMath);
                    }
                }            
            }
        }
        
         if ( !dataPoints.isEmpty() )
	    {
	        int xCenter, yCenter;
	        EDU.emporia.mathtools.Point2D.Double p;
	        g.setColor(eulerGraphColor);
	        
	        for ( int i=0 ; i < dataPoints.size() ; i++)
	        {
	            p = (EDU.emporia.mathtools.Point2D.Double)(dataPoints.elementAt(i));
	            double xData = p.getX(), yData = p.getY();
	            
	            
	            	        
	            xCenter = xMathToPixel(xData);
	            yCenter = yMathToPixel(yData);
	            g.fillOval(xCenter-2,yCenter-2,4,4);
	            
	            // first will keep track of whether it is an initial point
	            // for the graph or a continuation.
	            boolean first = true;
	        
	            //The graph will be a series of line segments each drawn from
	            //(xBegin, yBegin) to (xEnd, yEnd)
	            int xBegin = 0, yBegin = 0, xEnd = 0, yEnd = 0;
	            
	            double xNew = xData, yNew = yData;
	        
	            //Plot the curve in the positive direction
	            for (int n = 0; n < eulerPointNumber ; n++)
	            {
	                // Check to see if the coordinates are valid numbers 
	                if (!((Double.isNaN(xNew)) || (Double.isInfinite(xNew)) 
	                    || (Double.isNaN(yNew)) || (Double.isInfinite(yNew))))
	                {	                
	                    if (first)
	                    {
	                        first = false;
	                        
	                        xBegin = xMathToPixel(xNew);
	                        yBegin = yMathToPixel(yNew);
	                    }
	                    else
	                    {
	                        int xTemp=0;
	                        xEnd = xMathToPixel(xNew);
	                        yEnd = yMathToPixel(yNew);
	                        if ( (yBegin >= yMaxPixel) && (yBegin <= yMinPixel) )
	                            if ( yEnd < yMaxPixel )
	                            {
	                                xTemp = xBegin + ((yMaxPixel - yBegin)*(xEnd - xBegin))/(yEnd - yBegin);
	                                g.drawLine(xBegin, yBegin, xTemp, yMaxPixel);
	                            }
	                            else if ( yEnd > yMinPixel)
	                            {
	                                xTemp = xBegin + ((yMinPixel - yBegin)*(xEnd-xBegin))/(yEnd-yBegin);
	                                g.drawLine(xBegin, yBegin, xTemp, yMinPixel);
	                            }
	                            else
	                                g.drawLine(xBegin, yBegin, xEnd, yEnd);
	                        else if( (yEnd >= yMaxPixel) && (yEnd <= yMinPixel) )
	                            if ( yBegin < yMaxPixel )
	                            {
	                                xTemp = xBegin + ((yMaxPixel - yBegin)*(xEnd - xBegin))/(yEnd - yBegin);
	                                g.drawLine( xTemp, yMaxPixel, xEnd, yEnd);
	                            }
	                            else
	                            {
	                                xTemp = xBegin + ((yMinPixel - yBegin)*(xEnd-xBegin))/(yEnd-yBegin);
	                                g.drawLine(xTemp, yMinPixel, xEnd, yEnd);
	                            }
	                        xBegin = xEnd;
	                        yBegin = yEnd;
	                        xValue.set_value(xNew);
	                        yValue.set_value(yNew);
	                        
	                        xNew = xNew + xExpression.value()*eulerDelta;
	                        yNew = yNew + yExpression.value()*eulerDelta;
	                  
	                    }
	                }
	                //If we get an invalid point we stop
	                else break;
	            }
	            //Plot the curve in the negative direction
	            
	            xNew = xData;
	            yNew = yData;
	            first = true;
	            
	            for (int n = 0; n < eulerPointNumber ; n++)
	            {
	                // Check to see if the coordinates are valid numbers 
	                if (!((Double.isNaN(xNew)) || (Double.isInfinite(xNew)) 
	                    || (Double.isNaN(yNew)) || (Double.isInfinite(yNew))))
	                {	                
	                    if (first)
	                    {
	                        first = false;
	                        
	                        xBegin = xMathToPixel(xNew);
	                        yBegin = yMathToPixel(yNew);
	                    }
	                    else
	                    {
	                        int xTemp=0;
	                        xEnd = xMathToPixel(xNew);
	                        yEnd = yMathToPixel(yNew);
	                        if ( (yBegin >= yMaxPixel) && (yBegin <= yMinPixel) )
	                            if ( yEnd < yMaxPixel )
	                            {
	                                xTemp = xBegin + ((yMaxPixel - yBegin)*(xEnd - xBegin))/(yEnd - yBegin);
	                                g.drawLine(xBegin, yBegin, xTemp, yMaxPixel);
	                            }
	                            else if ( yEnd > yMinPixel)
	                            {
	                                xTemp = xBegin + ((yMinPixel - yBegin)*(xEnd-xBegin))/(yEnd-yBegin);
	                                g.drawLine(xBegin, yBegin, xTemp, yMinPixel);
	                            }
	                            else
	                                g.drawLine(xBegin, yBegin, xEnd, yEnd);
	                        else if( (yEnd >= yMaxPixel) && (yEnd <= yMinPixel) )
	                            if ( yBegin < yMaxPixel )
	                            {
	                                xTemp = xBegin + ((yMaxPixel - yBegin)*(xEnd - xBegin))/(yEnd - yBegin);
	                                g.drawLine( xTemp, yMaxPixel, xEnd, yEnd);
	                            }
	                            else
	                            {
	                                xTemp = xBegin + ((yMinPixel - yBegin)*(xEnd-xBegin))/(yEnd-yBegin);
	                                g.drawLine(xTemp, yMinPixel, xEnd, yEnd);
	                            }
	                        xBegin = xEnd;
	                        yBegin = yEnd;
	                        xValue.set_value(xNew);
	                        yValue.set_value(yNew);
	                        
	                        xNew = xNew - xExpression.value()*eulerDelta;
	                        yNew = yNew - yExpression.value()*eulerDelta;
	                  
	                    }
	                }
	                //If we get an invalid point we stop
	                else break;
	            }
	                   
	        }
	    }
	}
	
	private void drawSlopeLine( Graphics g, double x, double y)
    {
        double xDelta, yDelta, slope;
        int xBegin, yBegin, xEnd, yEnd;
        xValue.set_value(x);
        yValue.set_value(y);
        xDelta = xExpression.value();
        yDelta = yExpression.value();
                        
        if ( (xDelta != 0)||(yDelta != 0) )
        {
            if ( Math.abs(xDelta/xRange) > Math.abs(yDelta/yRange))
            {
                xBegin = xMathToPixel(x - slopeRadius * xPixelWidth);
                xEnd = xMathToPixel(x + slopeRadius * xPixelWidth);
                yBegin = yMathToPixel(y - slopeRadius * (yDelta/xDelta) * xPixelWidth);
                yEnd = yMathToPixel(y + slopeRadius * (yDelta/xDelta) * xPixelWidth);
            }
            else
            {
                double yPixelWidth = yRange/height;
                yBegin = yMathToPixel(y - slopeRadius * yPixelWidth);
                yEnd = yMathToPixel(y + slopeRadius * yPixelWidth);
                xBegin = xMathToPixel(x - slopeRadius * (xDelta/yDelta) * yPixelWidth);
                xEnd = xMathToPixel(x + slopeRadius * (xDelta/yDelta) * yPixelWidth);
            }
                            
            g.drawLine(xBegin, yBegin, xEnd, yEnd);
         }
        
    }

	/**
	 * Sets the formula for g(x,y) to be used in the 
	 * differential equation x'=g(x,y).
	 * 
	 * @param xDerivative
	 * @exception Graphable_error
	 */
	public void setXDerivative(java.lang.String xDerivative)throws Graphable_error 
	{
        try { 
            xExpression = Parser.parse (xDerivative); 
            this.xDerivative = xDerivative;
            repaint();
            
        } catch (Syntax_error e) {
		    throw new Graphable_error ("Syntax error in xDerivative");
        }
    }

	/**
	 * Gets the formula g(x,y) to be used in the differential
	 * equation y'=g(x,y)
	 * 
	 * @return The formula for g(x,y)
	 */
	public java.lang.String getXDerivative()
	{
		return this.xDerivative;
	}

	/**
	 * Sets the formula for f(x,y) to be used in the differential
	 * equation y'=f(x,y).
	 * 
	 * @param yDerivative
	 * @exception Graphable_error
	 */
	public void setYDerivative(java.lang.String yDerivative)throws Graphable_error
	{
		try { 
            yExpression = Parser.parse (yDerivative); 
            this.yDerivative = yDerivative;
            repaint();
        } catch (Syntax_error e) {
		    throw new Graphable_error ("Syntax error in yDerivative");
        }
	}

	/**
	 * Gets the formula to be used in the differential equatio
	 * y'=f(x,y).
	 * 
	 * @return The formula f(x,y)
	 */
	public java.lang.String getYDerivative()
	{
		return this.yDerivative;
	}

	/**
	 * Sets the radius in pixels to be used in drawing the 
	 * line segments for the slope field. Will not allow
	 * a value that is not positive.
	 * 
	 * @param slopeRadius
	 */
	public void setSlopeRadius(int slopeRadius)
	{
		if ( slopeRadius > 0)
        {
            this.slopeRadius = slopeRadius;
            repaint();
        }
	}

	/**
	 * Gets the radius (in pixels) to be used in drawing the 
	 * line segments for the slope fields.
	 * 
	 * @return Half the width (in pixels) of the square that will
	 * contain the line segment.
	 */
	public int getSlopeRadius()
	{
		return this.slopeRadius;
	}

	/**
	 * Sets the increment that will be used in Euler's method
	 * when drawing the graph of an approximate solution.
	 * 
	 * @param eulerDelta
	 */
	public void setEulerDelta(double eulerDelta)
	{
		this.eulerDelta = eulerDelta;
		repaint();
	}

	/**
	 * Gets the increment that will be used in drawing the
	 * graph of an approximate solution using Euler's method.
	 */
	public double getEulerDelta()
	{
		return this.eulerDelta;
	}

	/**
	 * Sets the number of iterations that will be used in 
	 * drawing a graph using Euler's method. Since the graph
	 * is drawn in both the positive and the negative direction
	 * there are actually twice this many points used.
	 * 
	 * @param eulerPointNumber
	 */
	public void setEulerPointNumber(int eulerPointNumber)
	{
		this.eulerPointNumber = eulerPointNumber;
		repaint();
	}

	/**
	 * Gets the number of iterations to be used in drawing
	 * an approximate graph using Euler's method.
	 */
	public int getEulerPointNumber()
	{
		return this.eulerPointNumber;
	}

	/**
	 * Sets the color used for the approximate solutions.
	 * 
	 * @param eulerGraphColor
	 */
	public void setEulerGraphColor(java.awt.Color eulerGraphColor)
	{
		this.eulerGraphColor = eulerGraphColor;
		repaint();
	}

	/**
	 * Gets the color used for drawing the graph of the 
	 * approximate solutions.
	 */
	public java.awt.Color getEulerGraphColor()
	{
		return this.eulerGraphColor;
	}

	/**
	 * Sets the color to be used for the slope field.
	 * 
	 * @param fieldColor
	 */
	public void setFieldColor(java.awt.Color fieldColor)
	{
		this.fieldColor = fieldColor;
		repaint();
	}

	/**
	 * Gets the color to be used for the slope field.
	 */
	public java.awt.Color getFieldColor()
	{
		return this.fieldColor;
	}

	/**
	 * Sets whether or not the user will be able to click on
	 * the slope field with a mouse and have an approximation
	 * drawn through that point.
	 * 
	 * @param eulerMouseEnabled
	 */
	public void setEulerMouseEnabled(boolean eulerMouseEnabled)
	{
		this.eulerMouseEnabled = eulerMouseEnabled;
	}

	/**
	 * Gets the value of the variable eulerMouseEnabled.
	 * 
	 * @return True if the user can generate graphs with a mouse click
	 * and false otherwise.
	 */
	public boolean isEulerMouseEnabled()
	{
		return this.eulerMouseEnabled;
	}

	
	public java.lang.String toString()
	{
		return "SlopeField";
	}
	
	/**
	 * Adds a dataPoint to be used to generate an Euler 
	 * approximation.
	 * 
	 * @param p A Point2D object
	 */
	public void addDataPoint( EDU.emporia.mathtools.Point2D.Double p)
	{
	    if ( !dataPoints.contains(p))
	    {
	        dataPoints.addElement(p);
	        repaint();
	    }
	}
    
    /**
     * Another version of the addDataPoint method that accepts
     * the coordinates of the point to be added.
     * 
     * @param x The first coordinate of the point to be added.
     * @param y The second coordinate of the point to be added.
     */
    public void addDataPoint( double x, double y)
	{
	    addDataPoint(new EDU.emporia.mathtools.Point2D.Double(x,y));
	}
	
	/**
	 * Removes a point from the Vector.
	 * 
	 * @param p
	 */
	public void removeDataPoint( EDU.emporia.mathtools.Point2D.Double p)
	{
	    dataPoints.removeElement(p);
	    repaint();
	}
	
	/**
	 * Clears all points from the Slope Field.
	 */
	public void removeAllDataPoints()
	{
	    dataPoints.removeAllElements();
	    repaint();
	}

	public static void main(String argv[])
	{
		class DriverFrame extends javax.swing.JFrame
		{
			public DriverFrame()
			{
				addWindowListener(new java.awt.event.WindowAdapter()
				{
					public void windowClosing(java.awt.event.WindowEvent event)
					{
						dispose();	  // free the system resources
						System.exit(0); // close the application
					}
				});
				getContentPane().setLayout(null);
				setSize(200,200);
				getContentPane().add(new SlopeField());
			}
		}

		new DriverFrame().show();
	}
	
	void MathGrid_mousePressed(java.awt.event.MouseEvent event)
	{
	    requestFocus();
		if ( event.isControlDown())
		{
		    
	        if ( event.isShiftDown())
	        {
	            xMinCurrent = xMin;
	            xMaxCurrent = xMax;
	            yMinCurrent = yMin;
	            yMaxCurrent = yMax;
	            justZoomed = true;
	            updateParameters();
	        }
	        else
	        {
		        double x,y;
	            double halfWidth, halfHeight;
		        switch (zoomMode)
		        {
		            case (ZOOMIN):
		                x = xPixelToMath(event.getX());
		                y = yPixelToMath(event.getY());
		                halfWidth = xRange / (2.0 * zoomFactor);
		                halfHeight = yRange / (2.0 * zoomFactor);
		                
		                xMinCurrent = x - halfWidth;
		                xMaxCurrent = x + halfWidth;
		                yMinCurrent = y -halfHeight;
		                yMaxCurrent = y + halfHeight;
		                justZoomed = true;
		                updateParameters();
		                break;
		    
		            case (ZOOMOUT):
		                x = xPixelToMath(event.getX());
		                y = yPixelToMath(event.getY());
		                halfWidth = xRange * zoomFactor / 2.0 ;
		                halfHeight = yRange * zoomFactor / 2.0;
		                
		                xMinCurrent = x - halfWidth;
		                xMaxCurrent = x + halfWidth;
		                yMinCurrent = y -halfHeight;
		                yMaxCurrent = y + halfHeight;
		                justZoomed = true;
		                updateParameters();
		                break;  
		    
		            case (ZOOMBOX):
		                xValue1 = event.getX();
		                yValue1 = event.getY();
		                break;
		    
		            case (SHOWPOINT):
		                String s = "(" + 
		                    MathUtility.displayFormat(xPixelToMath(event.getX()),8)
		                    + "," + 
		                    MathUtility.displayFormat(yPixelToMath(event.getY()),8)
		                    + ")";
		                setTitle(s);
		                break;
		        }
		    }
		}
		else if(eulerMouseEnabled)
		{
		    int x = event.getX();
		    int y = event.getY();
		    String s = "(" + 
		                MathUtility.displayFormat(xPixelToMath(x),8)
		                + "," + 
		                MathUtility.displayFormat(yPixelToMath(x),8)
		                + ")";
		    setMessage(s);
		    addDataPoint(xPixelToMath(x), yPixelToMath(y));
		}
		
		repaint();
	}

	void MathGrid_mouseReleased(java.awt.event.MouseEvent event)
	{
	    if (event.isControlDown())
	    {
		    if ((zoomMode == ZOOMBOX) && ( !(event.isShiftDown())))
	        {
		        dragging = false;
		        xValue2 = event.getX();
		        yValue2 = event.getY();
		
		        if ( (xValue2 != xValue1) && (yValue2 != yValue1))
		        {
		            double xValue1Math = xPixelToMath(xValue1);
		            double xValue2Math = xPixelToMath(xValue2);
		            double yValue1Math = yPixelToMath(yValue1);
		            double yValue2Math = yPixelToMath(yValue2);
		    
		            xMinCurrent = Math.min(xValue1Math, xValue2Math);
		            xMaxCurrent = Math.max(xValue1Math, xValue2Math);
		            yMinCurrent = Math.min(yValue1Math, yValue2Math);
		            yMaxCurrent = Math.max(yValue1Math, yValue2Math);
		        }
		        justZoomed = true;
		        updateParameters();
		        repaint();
            }
        }
	}
	
	void MathGrid_mouseDragged(java.awt.event.MouseEvent event)
	{
		if ((zoomMode == ZOOMBOX) && ( !(event.isShiftDown())) && ((event.isControlDown())))
	    {
		    dragging = true;
		    xValue2 = event.getX();
		    yValue2 = event.getY();
		    repaint();
		}
	}


	

}