
package EDU.emporia.mathbeans;
import java.awt.*;
import EDU.emporia.mathtools.*;
import javax.swing.*;
import java.util.*;


/**
 * MathGrapher is capable of displaying graphs of curves and of discrete points
 * 
 * @author Joe Yanik
 * @version 1.4
 * @since 6/01/2002
 */
public class MathGrapher extends EDU.emporia.mathbeans.MathGrid
{
    //***********************************************************
    //*********Global Declarations*******************************
    //************************************************************

    /**
     * The mathematical width of one pixel on the coordinate system
     */
    protected double xPixelWidth = .1;
    
    /**
     * The number of graphs currently being displayed
     */
   
    protected int graphCounter = 0;
    
    
    /**
     * The graph array will contain the graphs to be graphed
     */
    protected Graphable graph[]; 
    
    
    /**
     * The graphPoints arrays will contain the points to be used to plot
     *   the graphs
     */
    protected EDU.emporia.mathtools.Point2D graphPoints[][];
    
    /**
     * point is will reference the Point2D object used by plotPoints to be displayed.
     */
    protected EDU.emporia.mathtools.Point2D.Double point = new Point2D.Double(0,0);

    /**
     * pointPlotted is a boolean used to keep track of the first time that
     * pointPlot is called.
     */
    protected boolean pointPlotted = false;
    
   
    /**
     * This will correspond to the index of the graph to be traced at the moment
     */
    protected int traceGraph;
    
    /**
     * The "home point" for the trace
     */
    protected double tTraceHome;
    
    
    /**
     * yTraceMath is the mathematical y-coordinate of the point currently
     * being displayed in the trace
     */
    /**
     * xTraceMath is the mathematical x-coordinate of the current point displayed 
     * in the trace
     */
    protected double xTraceMath, yTraceMath;
    
    
    
    /**
     * This will be incremented or decremented as the graph is traced.
     */
    protected double traceMultiplier;
    
    /**
     * tDelta keeps track of the distance between sampling points used to display
     * the graph. For functions it will be xPixelWidth
     */
    /**
     * The tMax array keeps track of the current value of tMax for each graph.
     * For functions it will be xMax.
     */
    /**
     * The tMin array keeps track of the current value of tMin for each curve.
     * For functions it will be xMinCurrent
     */
    protected double tMin[], tMax[], tDelta[];
    
   
    
    /**
     * The array of discrete points to be graphed on the MathGrapher
     */
    protected Vector discretePoints = new Vector();
    
    /**
     * An array of Color objects that will determine the color to be used to 
     * display each point.
     */
    protected Vector pointColors = new Vector();
    
    /**
     * The default color to be used when no color is specified for a given point
     */
    protected Color defaultPointColor = new Color(75, 120, 63);
    
    
    
    /**
     * An array of colors to be used to draw the graphs in different colors
     */
    //The colors that will be used to draw the graphs/
    protected Color graphColor[]={Color.blue, Color.red,
        Color.green, Color.yellow, Color.cyan, Color.orange,
        Color.magenta, Color.pink, new Color(0, 64, 0), 
        new Color(64, 0, 64), new Color(128,64,0), new Color(128,128,0),
        new Color(0,0,128), new Color(201,206,9), new Color(195,50,75),
        new Color(143,75,160)};
        
    /**
     * Keeps track of whether or not the trace feature is enabled
     */
    //***************************************************************************
    //*********Properties to appear in the property list***********************
    //*************************************************************************
    
    //Indicates whether or not the trace feature is enabled
    protected boolean traceEnabled;
    
    /**
     * The radius (in pixels) used to draw the discrete points. Must be at least
     * 2.
     */
    //The pixel radius of discrete  points that are plotted
    protected int pointRadius = 2;
    
     /**
     * The maximum number of graphs that can be displayed at one time
     */
    protected int maxNumberOfGraphs = 30;
    
    /**
     * Parameter used to add a graph to the MathGrapher
     */
    //Graphable objects that will be displayed on the graph
    protected EDU.emporia.mathtools.Graphable g;

	/**
	 * Parameter used to add a graph to the MathGrapher
	 */
	protected EDU.emporia.mathtools.Graphable f;
	
	//******************************************************************************
	//*******************End of global declarations*********************************
	//*****************************************************************************

	public MathGrapher()
	{
	    super();
	    traceGraph = 0;
	    traceMultiplier = 0;
	    traceEnabled = true;
	    
	    graph = new Graphable[maxNumberOfGraphs];
	    tMin = new double[maxNumberOfGraphs];
	    tMax = new double[maxNumberOfGraphs];
	    tDelta = new double[maxNumberOfGraphs];
	    graphPoints = new EDU.emporia.mathtools.Point2D[maxNumberOfGraphs][];
	    
	    f = null;
	    g = null;
	    
		//{{INIT_CONTROLS
		setLayout(null);
		setBackground(java.awt.Color.lightGray);
		setFont(new Font("Serif", Font.PLAIN, 10));
		setSize(200,200);
		//}}
	
		//{{REGISTER_LISTENERS
		SymKey aSymKey = new SymKey();
		this.addKeyListener(aSymKey);
		//}}
	}//end of no-argument constructor

	//{{DECLARE_CONTROLS
	//}}
	
	public void paintComponent(Graphics a)
	{
	    super.paintComponent(a);	    
	    
	    //Plot each graph
	    for (int j = 0 ; j < graphCounter ; j++)
	    {
	        //Specify the color for the graph.
	        a.setColor(graphColor[j%16]);
	        
	        // first will keep track of whether it is an initial point
	        // for the graph or a continuation.
	        boolean first = true;
	        
	        //The graph will be a series of line segments each drawn from
	        //(xBegin, yBegin) to (xEnd, yEnd)
	        int xBegin = 0, yBegin = 0, xEnd = 0, yEnd = 0;
	        
	        //Step through the array of points to draw the graph
	        for (int i = 0; i < graphPoints[j].length ; i++)
	        {
	            //x and y will hold the coordinates of the current point
	            double x,y;
	            x = graphPoints[j][i].getX();
	            y = graphPoints[j][i].getY();
	            
	            // Check to see if the coordinates are numbers 
	            if (!((Double.isNaN(x)) || (Double.isInfinite(x)) 
	                || (Double.isNaN(y)) || (Double.isInfinite(y))))
	            {	                
	                if (first)
	                {
	                    first = false;
	                        
	                    xBegin = xMathToPixel(x);
	                    yBegin = yMathToPixel(y);
	                }
	                else
	                {
	                    int xTemp=0;
	                    xEnd = xMathToPixel(x);
	                    yEnd = yMathToPixel(y);
	                    if ( (yBegin >= yMaxPixel) && (yBegin <= yMinPixel) )
	                        if ( yEnd < yMaxPixel )
	                        {
	                            xTemp = xBegin + ((yMaxPixel - yBegin)*(xEnd - xBegin))/(yEnd - yBegin);
	                            a.drawLine(xBegin, yBegin, xTemp, yMaxPixel);
	                        }
	                        else if ( yEnd > yMinPixel)
	                        {
	                            xTemp = xBegin + ((yMinPixel - yBegin)*(xEnd-xBegin))/(yEnd-yBegin);
	                            a.drawLine(xBegin, yBegin, xTemp, yMinPixel);
	                        }
	                        else
	                            a.drawLine(xBegin, yBegin, xEnd, yEnd);
	                    else if( (yEnd >= yMaxPixel) && (yEnd <= yMinPixel) )
	                        if ( yBegin < yMaxPixel )
	                        {
	                            xTemp = xBegin + ((yMaxPixel - yBegin)*(xEnd - xBegin))/(yEnd - yBegin);
	                            a.drawLine( xTemp, yMaxPixel, xEnd, yEnd);
	                        }
	                        else
	                        {
	                            xTemp = xBegin + ((yMinPixel - yBegin)*(xEnd-xBegin))/(yEnd-yBegin);
	                            a.drawLine(xTemp, yMinPixel, xEnd, yEnd);
	                        }
	                    xBegin = xEnd;
	                    yBegin = yEnd;
	                }
	            }
	            //If we get an invalid point we want to start the graph over
	            //again at the next valid point.
	            else first = true;
	        }
	    } 
	    
	    
	    if ( !discretePoints.isEmpty() )
	    {
	        int xCenter, yCenter;
	        Point2D.Double p;
	        
	        for ( int i=0 ; i < discretePoints.size() ; i++)
	        {
	            p = (Point2D.Double)(discretePoints.elementAt(i));
	            a.setColor((Color)(pointColors.elementAt(i)));
	        
	            xCenter = xMathToPixel(p.getX());
	            yCenter = yMathToPixel(p.getY());
	            a.fillOval(xCenter-pointRadius,yCenter-pointRadius,2*pointRadius + 1,2*pointRadius + 1);
	        }
	    }
	   
	    
	    //Plot the point that is being traced
	    if ( (traceGraph < graphCounter) && ( traceEnabled) )
	    {
	        a.setColor(Color.black);
	        a.fillOval(xMathToPixel(xTraceMath) - pointRadius,
	                    yMathToPixel(yTraceMath) - pointRadius,
	                    2*pointRadius + 1, 2*pointRadius + 1);      
	    }                
	}//end of paintComponent method
	
	
	/**
	 * updateParameters readjusts all the parameters of the graph. It will be
	 * called whenever the xRange or yRange have changed
	 */
	protected void updateParameters()
	{
	    super.updateParameters();
	    updateGraph();
	}
	
    /**
     * updateGraph should be called whenever a new graph has been added or when
     * one of the graphs that has already been added has changed
     */
    public void updateGraph()
	{
	    xPixelWidth = xRange/width;
	    
	    //Should never happen, but just to prevent an infinite loop.
	    if ( xPixelWidth <= 0 ) xPixelWidth = .1;
	    
	    //Get the points for the graph
	    for ( int j = 0 ; j < graphCounter ; j++ )
	    {  
	        if ( graph[j] instanceof ParametricCurve )
	        {
	            tMin[j] = ((ParametricCurve)graph[j]).getTMin();
	            tMax[j] = ((ParametricCurve)graph[j]).getTMax();
	            tDelta[j] = ((ParametricCurve)graph[j]).getTDelta();
	            if ( tDelta[j] <= 0 ) tDelta[j] = 0.1;
	        }
	        else
	        {
	            tMin[j] = xMinCurrent;
	            tMax[j] = xMaxCurrent;
	            tDelta[j] = xPixelWidth;
	        }
	        try
	        {
	            graphPoints[j] = graph[j].getPoints(tMin[j], tMax[j], tDelta[j]);
	        }
	        catch(Graphable_error e) {};
	    }
	    
	    //Set the trace point back to the origin after a zoom
	    if ( (justZoomed) && (traceGraph < graphCounter) )
	    {
	        if ( graph[traceGraph] instanceof ParametricCurve)
	            tTraceHome = tMin[traceGraph];
	        else
	            tTraceHome = xZeroMath;
	        traceMultiplier = 0;
	        justZoomed = false;
	        //if (traceEnabled) updateTrace();
	    }
	    
	    if (graphCounter > 0)
	    {
	        setTitle(graph[traceGraph].getTitle());
	        if (traceEnabled) updateTrace();
	    }
	    
	    repaint();
	}//end of updateGraph method
	
	/**
	 * updateTrace adjusts the parameters to display the new trace point
	 */
	protected void updateTrace()
	{
	    Point2D tracePoint;
	    double tTraceMath;
	    
	    tTraceMath = tTraceHome + tDelta[traceGraph]*traceMultiplier;
	    
	    while ( tTraceMath < tMin[traceGraph] )
	        tTraceMath = tTraceHome + tDelta[traceGraph]*(++traceMultiplier);
	    while ( tTraceMath > tMax[traceGraph] )
	        tTraceMath = tTraceHome + tDelta[traceGraph]*(--traceMultiplier);   
	    try
	    {
	        tracePoint = graph[traceGraph].getPoint(tTraceMath);
	        xTraceMath = tracePoint.getX();
	        yTraceMath = tracePoint.getY();
	        String s = "(" + 
		        MathUtility.displayFormat(xTraceMath,8)
		        + ", " + 
		        MathUtility.displayFormat(yTraceMath,8)
		        + ")";
		    if ( graph[traceGraph] instanceof ParametricCurve )
		        s = s + ",t=" + MathUtility.displayFormat(tTraceMath,8);
		    setMessage(s);
	    }
	    catch (Graphable_error e) 
	    {
	        System.err.println(e.getMessage());
	    }
	    
	    
	}//end of updateTrace method

	/**
	 * addGraph adds a new Graph to the MathGrapher
	 * 
	 * @param h h is a Graphable object. It could either represent a function or a
	 * parameteric curve
	 */
	//This will add a new Graph
	public void addGraph( Graphable h)
	{
	    graph[graphCounter] = h;
	    setTitle( h.getTitle());
	    traceGraph = graphCounter;
	    
	    if ( h instanceof ParametricCurve )
	    {
	        tMin[graphCounter] = ((ParametricCurve)h).getTMin();
	        tMax[graphCounter] = ((ParametricCurve)h).getTMax();
	        tDelta[graphCounter] = ((ParametricCurve)h).getTDelta();
	        if ( tDelta[graphCounter] <= 0 ) tDelta[graphCounter] = 0.1;
	    }
	    else
	    {
	        tMin[graphCounter] = xMinCurrent;
	        tMax[graphCounter] = xMaxCurrent;
	        tDelta[graphCounter] = xPixelWidth;
	    }
	    try
	    {
	        graphPoints[graphCounter] = h.getPoints(tMin[graphCounter], tMax[graphCounter], 
	                                        tDelta[graphCounter]);
	    }
	    catch(Graphable_error e) {};
	    
	    graphCounter = Math.min(++graphCounter, maxNumberOfGraphs-1);
	    if (traceEnabled) updateTrace();
	    repaint();
	}//end of addGraph method
	
	/**
	 * Another version of addGraph that allows you to specify the color of the
	 * graph.
	 * 
	 * @param h The Graphable object to be graphed
	 * @param c The color that it will be graphed in.
	 */
	public void addGraph( Graphable h, Color c)
	{
	    graphColor[graphCounter%16] = c;
	    addGraph(h);
	}
	
	/**
	 * removeGraph will remove the most recently displayed graph
	 */
	public void removeGraph()
	{
	    graphCounter = Math.max(--graphCounter,0);
	    if ( (traceGraph >= graphCounter) && (traceGraph > 0) ) traceGraph--;
	    repaint();
	}
	
	/**
	 * Removes the graph at the indicated index
	 * 
	 * @param index The index of the graph to be removed
	 */
	public void removeGraph(int index)
	{
	    if ( (index < graphCounter)&&(index >= 0))
	    {
	        for ( int i = index ; i < (graphCounter-1) ; i++)
	        {
	            graph[i] = graph[i+1];
	            graphPoints[i] = graphPoints[i+1];
	        }
	        graphCounter--;
	        if ( (traceGraph >= graphCounter) && (traceGraph > 0) ) traceGraph--;
	        repaint();
	    }
	}
	
	/**
	 * Removes the specified graph
	 * 
	 * @param h The Graphable object to be removed
	 */
	public void removeGraph (Graphable h)
	{
	    for ( int i = 0 ; i <= graphCounter ; i++)
	    {
	        if ( h.equals(graph[i])) removeGraph(i);
	    }
	}
	
	/**
	 * Adds a new point to be displayed using the default color. Repeated invocation
	 * of addPoint will result in additional points being displayed
	 * 
	 * @param p The point to be displayed
	 */
	public void addPoint( Point2D.Double p)
	{
	    addPoint(p,defaultPointColor);
	}
	
	/**
	 * A version of the addPoint method that allows you to specify the color
	 * used to display the point.
	 * 
	 * @param p The point to be displayed
	 * @param c The color used to display it
	 */
	public void addPoint( Point2D.Double p, Color c)
	{
	    if ( !discretePoints.contains(p))
	    {
	        discretePoints.addElement(p);
	        pointColors.addElement(c);
	        repaint();
	    }
	}
	    
	
	/**
	 * Another version of the addPoint method that accepts the coordinates of
	 * the point as parameters
	 * 
	 * @param x The x-coordinate of the point
	 * @param y The y-coordinate of the point
	 */
	public void addPoint( double x, double y)
	{
	    addPoint(new Point2D.Double(x,y), defaultPointColor);
	}
	
	/**
	 * Still another version of the addPoint method
	 * that specifies the color.
	 * 
	 * @param x The mathematical x-coordinate of the point to be added
	 * @param y The mathematical y-coordinate of the point to be added.
	 * @param c The color used to draw the point
	 */
	public void addPoint( double x, double y, Color c)
	{
	    addPoint(new Point2D.Double(x,y), c);
	}
	/**
	 * Removes the point from the graph. It will remove the last occurrence
	 * of a point whose coordinates equal the coordinates of the point that
	 * is passed
	 * 
	 * @param p The point to be removed from the graph
	 */
	public void removePoint( Point2D.Double p)
	{
	    int loc = discretePoints.lastIndexOf(p);
	    discretePoints.removeElementAt(loc);
	    pointColors.removeElementAt(loc);
	    if ( p.equals(point)) pointPlotted = false;
	    repaint();
	}
	
	/**
	 * Removes all points from the graph
	 */
	public void removeAllPoints()
	{
	    discretePoints.removeAllElements();
	    pointColors.removeAllElements();
	    pointPlotted = false;
	    repaint();
	}
	
	/**
	 * Plots the point with indicated coordinates. Repeated calls of plotPoint
	 * will only result in the latest point being plotted.
	 * 
	 * @param x The x-coordinate of the point to be plotted
	 * @param y The y-coordinate of the point to be plotted
	 */
	public void plotPoint(double x, double y)
	{
	    point.setLocation(x,y);
	    if (pointPlotted)
	        repaint();
	    else
	        addPoint(point);
	}
	
	
	
	/**
	 * Adds all the points to the graph
	 * 
	 * @param pointArray The array of points to be added to the graph
	 */
	public void addPoints( Point2D.Double[] pointArray )
	{
	    for ( int i=0 ; i<pointArray.length ; i++ )
	    {
	        if ( !discretePoints.contains(pointArray[i]))
	        {
	            discretePoints.addElement(pointArray[i]);
	            pointColors.addElement(defaultPointColor);
	        }
	    }
	    repaint();
	}
	
	/**
	 * Sets the radius (in pixels) of the discrete points to be plotted on the 
	 * graph. Since 2 is the smallest radius that can be displayed any value
	 * less than 2 will result in a radius of 2
	 * 
	 * @param radius The radius (in pixels) of the discrete points to be plotted
	 */
	public void setPointRadius ( int radius )
	{
	    // a radius smaller than 1 doesn't show up
	    pointRadius = Math.max( radius, 1);
	}
	
	/**
	 * Returns the radius (in pixels) that will be used to plot the discrete
	 * points.
	 */
	public int getPointRadius()
	{
	    return pointRadius;
	}
	
	public void setMaxNumberOfGraphs(int n)
	{
	    if ( n > 0 )
	    {
	        graphCounter = Math.min(n , graphCounter);
	        maxNumberOfGraphs = n;
	        
	        Graphable[] tempGraph = graph;
	        graph = new Graphable[n];
	        
	        double[] tempTMin = tMin;
	        tMin = new double[n];
	        
	        double[] tempTMax = tMax;
	        tMax = new double[n];
	        
	        double[] tempTDelta = tDelta;
	        tDelta = new double[n];
	        
	        EDU.emporia.mathtools.Point2D[][] tempGraphPoints = graphPoints;
	        graphPoints = new EDU.emporia.mathtools.Point2D[n][];
	        
	        for ( int i=0 ; i < graphCounter ; i++ )
	        {
	            graph[i] = tempGraph[i];
	            tMin[i] = tempTMin[i];
	            tMax[i] = tempTMax[i];
	            tDelta[i] = tempTDelta[i];
	            graphPoints[i] = tempGraphPoints[i];
	        }
	    }
	}
	
	public int getMaxNumberOfGraphs()
	{
	    return maxNumberOfGraphs;
	}
	        
	
	
	/**
	 * Sets whether or not the trace feature is enabled
	 * 
	 * @param traceEnabled When true trace will be enabled
	 */
	public void setTraceEnabled(boolean traceEnabled)
	{
	    this.traceEnabled = traceEnabled;
	    if (!traceEnabled) setMessage("");
	}
	
	/**
	 * Determines whether or not the trace feature is enabled
	 * 
	 * @return True if enabled, false otherwise
	 */
	public boolean isTraceEnabled()
	{
		return this.traceEnabled;
	}

	/**
	 * Sets the values of the Graphable object f which would then be added
	 * to the MathGrapher
	 * 
	 * @param f The Graphable object representing the curve to be graphed
	 */
	public void setF(EDU.emporia.mathtools.Graphable f)
	{
		if (!(this.f == null)) removeGraph(this.f);
		addGraph(f);
		this.f = f;
	}

	/**
	 * Gets the parameter f
	 * 
	 * @return The Graphable object f which is displayed on the Graph
	 */
	public EDU.emporia.mathtools.Graphable getF()
	{
		return this.f;
	}

	/**
	 * Sets the parameter g
	 * 
	 * @param g The Graphable object g
	 */
	public void setG(EDU.emporia.mathtools.Graphable g)
	{
		if (!(this.g == null)) removeGraph(this.g);
		addGraph(g);
		this.g = g;
	}

	/**
	 * Gets the value of the parameter g
	 * 
	 * @return The graphable object g
	 */
	public EDU.emporia.mathtools.Graphable getG()
	{
		return this.g;
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
				setSize(400,300);
				getContentPane().add(new MathGrapher());
			}
		}

		new DriverFrame().show();
	}

	class SymKey extends java.awt.event.KeyAdapter
	{
		public void keyPressed(java.awt.event.KeyEvent event)
		{
			Object object = event.getSource();
			if (object == MathGrapher.this)
				MathGrapher_keyPressed(event);
		}
	}

	void MathGrapher_keyPressed(java.awt.event.KeyEvent event)
	{
		if ( (traceEnabled) && (graphCounter > 0) )
		{
		    switch(event.getKeyCode())
		    {
		        case java.awt.event.KeyEvent.VK_RIGHT:
		            traceMultiplier++;
		            break;
		        case java.awt.event.KeyEvent.VK_LEFT:
		            traceMultiplier--;
		            break;
		        case java.awt.event.KeyEvent.VK_UP:
		            traceGraph = (++traceGraph)%graphCounter;
		            tTraceHome = ( graph[traceGraph] instanceof ParametricCurve ? 
		                tMin[traceGraph] : xZeroMath );
		            setTitle(graph[traceGraph].getTitle());
		            break;
		        case java.awt.event.KeyEvent.VK_DOWN:
		            traceGraph = (--traceGraph + graphCounter)%graphCounter;
		            tTraceHome = ( graph[traceGraph] instanceof ParametricCurve ? 
		                tMin[traceGraph] : xZeroMath );
		            setTitle(graph[traceGraph].getTitle());
		            break;
		        case java.awt.event.KeyEvent.VK_ENTER:
		            String s = JOptionPane.showInputDialog("Enter a value");
		            try {
		                double temp = MathUtility.parseDouble(s);
		            
		                if ( (temp >= tMin[traceGraph]) && (temp <= tMax[traceGraph]) )
		                {
		                    tTraceHome = temp;
		                    traceMultiplier = 0;
		                }
		                else
		                    JOptionPane.showMessageDialog(null, "Value must be between " +
		                        tMin[traceGraph] + " and " + tMax[traceGraph], "Illegal entry",
		                        JOptionPane.ERROR_MESSAGE) ;
		                break;
		            }catch (MathSyntaxError error)
		            {
		                Toolkit.getDefaultToolkit().beep();
	                    System.err.println("getValue: could not parse: " + s);
	                    JOptionPane.showMessageDialog(null, 
	                        new JLabel("Could not parse: " + s),
	                        "Error", JOptionPane.ERROR_MESSAGE);
	                }
		                
		    }
		    updateTrace();
		    repaint();
		    
		}
	}
}