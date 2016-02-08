//Version 1.3 revised 5/31/2001

package edu.emporia.mathbeans;
import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import edu.emporia.mathtools.*;


public class MathGrid extends javax.swing.JPanel
{
    //*************************************************************
    //**********************Global Declarations*******************
    //*****************************************************************
    

    /**
     * FontMetrics object for the current font
     */
    protected FontMetrics fm;
    
	/**
	 * fontHeight will contain the height of the font. This will be used
	 * to adjust the bottom border so that there is room 
	 * to display the labels.
	 */
	protected int fontHeight;
	
	
	/**
	 * gridBorder will provide a border for the Coordinate Grid
	 * It will be a TitledBorder so that it can contain a title.
	 */
	protected javax.swing.border.TitledBorder gridBorder;
	
	
	/**
	 * gridInsets will tell the amount of inset on each side 
	 * for the border.
	 */
	protected Insets gridInsets;
	
	 
    /**
     * xHashMark will contain the mathematical distance between the hash marks
     * on the x axis
     */
    protected double xHashMark;
    
     /**
     * yHashMarks will contain the mathematical distance between the hash marks
     * on the y axis
     */
    protected double yHashMark;
    
    /**
     * xHashMarkPixel will contain the pixel distance between the hash marks
     * on the x axis
     */
    protected int xHashMarkPixel;
    
    /**
     * yHashMarkPixel will contain the pixel distance between the hash marks
     * on the y axis
     */
    protected int yHashMarkPixel;
	
	
	/**
	 * height will contain the height of the coordinate
	 * system in pixels.
	 */
	protected int height;
	
	/**
	 * width will contain the width of the coordinate
	 * system in pixels.
	 */
	protected int width;
	
	
	/**
	 * xBorder will contain the number of pixels to be designated
	 * for the border in the x direction that will be added, when necessary, 
	 * to leave room for the labels.
	 */
	protected int xBorder;
	
	/**
	 * yBorder will contain the number of pixels to be designated
	 * for the border in the y direction that will be added, when necessary, 
	 * to leave room for the labels.
	 */
	protected int yBorder;
	
	
	/**
	 * xRange will contain the mathematical width of the coordinate system. 
	 * (as opposed to the pixel width.)
	 */
	protected double xRange;
	
	/**
	 * yRange will contain the mathematical height of the coordinate system. 
	 * (as opposed to the pixel height.)
	 */
	protected double yRange;
	
	
	/**
	 * xMark will be the mathematical distance between gridlines on the x-axis.
	 */
	protected double xMark;
	
	/**
	 * yMark will be the mathematical distance between gridlines on the y-axis.
	 */
	protected double yMark;
	
	
	/**
	 * xMarkPixel is be the pixel version of xMark
	 */
	protected int xMarkPixel;
	
	
	/**
	 * yMarkPixel is be the pixel version of yMark
	 */
	protected int yMarkPixel;
	
	
	/**
	 * xZeroPixel is the pixel version of the x-coordinate of the "origin". 
	 * This will correspond to x=0, if possible, but, in general, 
	 * it will be where the axes cross.
	 */
	protected int xZeroPixel;
	
	/**
	 * yZeroPixel is the pixel version of the y-coordinate of the "origin". 
	 * This will correspond to y=0, if possible, but, in general, 
	 * it will be where the axes cross.
	 */
	protected int yZeroPixel;
	
	
	/**
	 * xZeroMath is the math version of the x-coordinate of the "origin". 
	 * This will correspond to x=0, if possible, but, in general, 
	 * it will be where the axes cross.
	 */
	protected double xZeroMath;
	
	/**
	 * yZeroMath is the math version of the y-coordinate of the "origin". 
	 * This will correspond to y=0, if possible, but, in general, 
	 * it will be where the axes cross.
	 */
	protected double yZeroMath;
	
	
	/**
	 * yFontWidth contains the width of the largest
	 * string to be displayed along the y axis.
	 */
	protected int yFontWidth;
	
	/**
	 * xFontWidth contains the width of the largest
	 * string to be displayed along the x axis.
	 */
	protected int xFontWidth;
	
	/**
	 * Static integer used for value of zoomMode that 
	 * corresponds to the zoom off mode.
	 */
	public static final int ZOOMOFF = 0;

	/**
	 * Static integer used for value of zoomMode that
	 *  corresponds to the zoom in mode.
	 */
	public static final int ZOOMIN = 1;

	/**
	 * Static integer used for value of zoomMode that
	 *  corresponds to the zoom out mode.
	 */
	public static final int ZOOMOUT = 2;

	/**
	 * Static integer used for value of zoomMode that
	 *  corresponds to the zoom box mode.
	 */
	public static final int ZOOMBOX = 3;

	/**
	 * Static integer used for value of zoomMode that
	 *  corresponds to the show point mode.
	 */
	public static final int SHOWPOINT = 4;
	
	/**
	 * Static integer used for value of gridLines that
	 *  corresponds to no gridlines.
	 */
	public static final int GRIDOFF = 0;

	/**
	 * Static constant used for value of gridLines 
	 * that correponds to a coarse grid.
	 */
	public static final int GRIDCOARSE = 10;

	/**
	 * Static constant used for value of gridLines 
	 * that correponds to the default grid.
	 */
	public static final int GRIDNORMAL = 20;

	/**
	 * Static constant used for value of gridLines 
	 * that correponds to a fine grid.
	 */
	public static final int GRIDFINE = 30;
	
	/**
	 * The current font used for drawing.
	 */
	protected Font theFont;
	
	/**
	 * Used to hold the coordinates of the rectange used when dragging the
	 * mouse in zoom box mode
	 */
	//These will be used to hold the coordinates of the rectangle
	// to be drawn when dragging the mouse in zoomBox mode.
	protected int xValue1;
	
	/**
	 * Used to hold the coordinates of the rectange used when dragging the
	 * mouse in zoom box mode
	 */
	protected int xValue2;
	
	/**
	 * Used to hold the coordinates of the rectange used when dragging the
	 * mouse in zoom box mode
	 */
	protected int yValue1;
	/**
	 * Used to hold the coordinates of the rectange used when dragging the
	 * mouse in zoom box mode
	 */
	
	protected int yValue2;
	
	
	/**
	 * This will be true when the mouse is dragging in zoomBox mode
	 */
	protected boolean dragging;
	
	
	/**
	 * This will be true immediately after a zoom
	 */
	protected boolean justZoomed = false;
	
	
	/**
	 * This will hold the current parameter for the grid that will be 
	 * used for drawing. When in zoomIn or zoomOut mode it may differ from
	 * xMin which will be used to preserve the default values.
	 */
	protected double xMinCurrent;
	
	/**
	 * This will hold the current parameter for the grid that will be 
	 * used for drawing. When in zoomIn or zoomOut mode it may differ from
	 * xMax which will be used to preserve the default values.
	 */
	protected double xMaxCurrent;
	
	/**
	 * This will hold the current parameter for the grid that will be 
	 * used for drawing. When in zoomIn or zoomOut mode it may differ from
	 * yMin which will be used to preserve the default values.
	 */
	protected double yMinCurrent;
	
	/**
	 * This will hold the current parameter for the grid that will be 
	 * used for drawing. When in zoomIn or zoomOut mode it may differ from
	 * yMax which will be used to preserve the default values.
	 */
	protected double yMaxCurrent;
	
	
	/**
	 * The inner border that will be used to display the upper title
	 */
	protected TitledBorder innerBorder;
	
	/**
	 * The outer border that will be used to display the lower title
	 */
	protected TitledBorder outerBorder;
	
	
    /**
     * The pixel version of xMinCurrent
     */
    protected int xMinPixel;
    
    /**
     * The pixel version of xMaxCurrent
     */
    protected int xMaxPixel;
    
    /**
     * The pixel version of yMinCurrent
     */
    protected int yMinPixel;
    
    /**
     * The pixel version of yMaxCurrent
     */
    protected int yMaxPixel;
    
   	
//**********************************************************************
// *************PROPERTIES TO APPEAR IN THE PROPERTY LIST*********
//***********************************************************************

	
    /**
    * xMin will contain the default numerical value of the smallest 
    * x-coordinate displayed
    */
    protected double xMin;
    
    /**
     * xMax will contain the default numerical value of the largest
     * x-coordinate displayed
     */
    protected double xMax;
    
    /**
     * yMax will contain the default numerical value of the largest 
     * y-coordinate displayed
     */
	protected double yMax;
	
	/**
	 * yMin will contain the default numerical value of the smallest 
	 * y-coordinate displayed
	 */
	protected double yMin;
	
	
	/**
	 * axesColor will designate the color in which the axes will be drawn
	 * 	The default will be black.
	 */
	protected java.awt.Color axesColor;
	
	
	/**
	 * gridColor will designate the color in which the grid lines will be
	 * 	drawn. The default will be white.
	 */
	protected java.awt.Color gridColor;
	
	
	/**
	 * gridLines will designate the approximate number of gridLines that
	 * 	will be drawn in each direction. It may not be precise due to 
	 * 	rounding.
	 */
	protected int gridLines;
	
   
	/**
	 * xLabel will contain the label to be displayed for the x-axis
	 */
	protected java.lang.String xLabel = "";
	
	
	/**
	 * yLabel will contain the label to be displayed for the y-axis
	 */
	protected java.lang.String yLabel = "";
	
	
	/**
	 * the value of zoomMode will determine how the Grid will respond to 
	 * 	a mouse-click. It will either do nothing, zoom in, zoom out,
	 * 	zoom in on a box, or display the coordinates of the point 
	 * 	depending on the mode.
	 */
	protected int zoomMode;
	
    
	/**
	 * title will provide a title for the graph
	 */
	protected java.lang.String title = "";
	
	/**
	 * The font to be used for the title that is displayed
	 * at the bottom of the coordinate system
	 */
	protected Font titleFont = new Font("Serif", Font.PLAIN, 10);
	
	/**
	 * The font that is used for the message that is 
	 * displayed at the top of the coordinate system.
	 * This message is normally used to display the 
	 * coordinates during a trace.
	 */
	protected Font traceFont = new Font("Serif", Font.PLAIN, 10);
	
	
	/**
	 * This will determine the factor by which you zoom in or out
	 */
	protected int zoomFactor;
	
	/**
	 * This will be the message that will be displayed at the top of the 
	 *  graph
	 */
	protected java.lang.String message = "";
	
	/**
	 * Determines whether or not the title at the bottom
	 * of the coordinate system is to be displayed.
	 */
	protected boolean titleEnabled = true;
	
	//**************************************************************
	//********** End of Global Declarations************************
	//************************************************************        

	public MathGrid()
	{
	    Border emptyBorder, loweredBevelBorder, compoundBorder;
	    
	    xMin = xMinCurrent = -10.0;
	    xMax = xMaxCurrent = 10.0;
	    yMin = yMinCurrent = -10.0;
	    yMax = yMaxCurrent = 10.0;
	    
	    xLabel = "x";
		yLabel = "y";
		
		zoomFactor = 10;
		
		axesColor = java.awt.Color.black;
		gridColor =java.awt.Color.white;
		
		gridLines = 20;
		
		//{{INIT_CONTROLS
		setLayout(null);
		setBackground(java.awt.Color.lightGray);
		setFont(new Font("Serif", Font.PLAIN, 10));
		setSize(250,250);
		//}}
		
		loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
		emptyBorder = BorderFactory.createEmptyBorder();
		
		innerBorder = new TitledBorder(emptyBorder,
		                                message, 
		                                TitledBorder.LEFT,
		                                TitledBorder.ABOVE_TOP,
		                                traceFont);
		outerBorder = new TitledBorder(loweredBevelBorder,
		                                title, 
		                                TitledBorder.LEFT,
		                                TitledBorder.BELOW_BOTTOM,
		                                titleFont);
        innerBorder.setTitleColor(Color.black);
        outerBorder.setTitleColor(Color.black);
		compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
		
		setBorder(compoundBorder);
		
		dragging = false;
	
		//{{REGISTER_LISTENERS
		SymMouse aSymMouse = new SymMouse();
		this.addMouseListener(aSymMouse);
		SymMouseMotion aSymMouseMotion = new SymMouseMotion();
		this.addMouseMotionListener(aSymMouseMotion);
		//}}
	}//end of no-argument constructor

	//{{DECLARE_CONTROLS
	//}}
	
   
    /**
     * This method will update the parameters to reflect any changes made in 
     *  xMin, xMax, yMin, yMax, or the overall size of the MathGrid.
     */
    protected void updateParameters()
	{
	    //*******************************************************
	    //********Local Declarations for this method************
	    //**********************************************************
	    
	    //delta will be used to measure how much we are willing to 
	    // to adjust values so that they will occur at "nice"
	    // numbers.
	    double delta;
	    
	    //*******************************************************
	    //*********End of local declarations********************
	    //*******************************************************
	    
	    theFont = getFont();
		fm = getFontMetrics(theFont);
		
	    xRange = xMaxCurrent - xMinCurrent;
	    yRange = yMaxCurrent - yMinCurrent;
	    
	    
	    //In order to make the gridlines appear at "nice" numbers
	    // we will arrange that the distance between them has only
	    // one significant digit. 
	    if ( gridLines != 0)
	    {
	        xMark = MathUtility.roundDecimal(xRange/gridLines,1);
	        yMark = MathUtility.roundDecimal(yRange/gridLines,1);
	        xHashMark = xMark * Math.max(gridLines/4, 1);
	        yHashMark = yMark * Math.max(gridLines/4, 1);
	    }
	    else 
	    {
	        xMark = xRange + 1;
	        yMark = yRange + 1;
	        xHashMark = MathUtility.roundDecimal(xRange/6,1);
	        yHashMark = MathUtility.roundDecimal(yRange/6,1);
	    }
	    

	    // delta is how far we are willing to go to find a "nice"
	    // number at which to draw the y-axis
	    delta = Math.min(xMark, xRange/20);
	    
	    // We need to decide where to draw the y-axis
	    if ( xMaxCurrent < 0)
	    {
	        // Round off a bit so that the 
	        // y-axis and all hashmarks will be at a nicer number
	        int digits = (int)(Math.ceil(MathUtility.log10(xMaxCurrent/delta)));
	        xZeroMath = MathUtility.roundDecimal(xMaxCurrent - delta/2, digits);
	    }
	    else if ( xMinCurrent > 0)
	    {
	        // Round again in this case
	       int digits = (int)(Math.ceil(MathUtility.log10(xMinCurrent/delta)));
	       xZeroMath = MathUtility.roundDecimal(xMinCurrent + delta/2, digits);
	    }
	    else xZeroMath = 0;
	    
	    // Now delta is the wiggle room we are willing to use to 
	    // draw the x-axis at a nice place
	    delta = Math.min(yMark, yRange/20);
	    
	    if ( yMaxCurrent < 0 )
	    {
	         // Round off a bit so that the 
	        // x-axis and all hashmarks will be at a nicer number
	        int digits = (int)(Math.ceil(MathUtility.log10(yMaxCurrent/delta)));
	        yZeroMath = MathUtility.roundDecimal(yMaxCurrent - delta/2, digits);
	    }
	    else if ( yMinCurrent > 0 )
	    {
	        // Round again in this case
	       int digits = (int)(Math.ceil(MathUtility.log10(yMinCurrent/delta)));
	       yZeroMath = MathUtility.roundDecimal(yMinCurrent + delta/2, digits);
	    }
	    else yZeroMath = 0;
	         
	    fontHeight = fm.getAscent();
	    
	    //Measure the width of the labels to be placed along the y-axis.
	    yFontWidth = 0;
	    
	    for ( double y = yZeroMath, j = 0 ; (y < yMaxCurrent) && (j<15) ; j++ )
	    {
	        y = yZeroMath + j*yHashMark;
	        yFontWidth = Math.max(yFontWidth, fm.stringWidth(MathUtility.displayFormat(y, 8)));
	    }
	    
	    for ( double y = yZeroMath, j = 1 ; (y > yMinCurrent) && (j<15) ; j++ )
	    {
	        y = yZeroMath - j*yHashMark;
	        yFontWidth = Math.max(yFontWidth, fm.stringWidth(MathUtility.displayFormat(y, 8)));
	    }
	    
	    adjustBorders();
	    
	}//end of updateParameters method
	
	/**
	 * This method will adjust the borders to make sure that there is room
	 * to display the labels
	 */
	protected void adjustBorders()
	{
	    //dim will contain information about the current width and height
	    //of the coordinateSystem object.
	    Dimension dim = getSize();
	    
	    gridInsets = getInsets();
	    
	    height = dim.height - gridInsets.top - gridInsets.bottom;
	    width = dim.width - gridInsets.left - gridInsets.right;
	   
	    xBorder = 0;
	    
	    xZeroPixel = xMathToPixel(xZeroMath);
	    
	    //Add some padding to the left hand side, if necessary
	    // in order to have room to label the y-axis
	    if ( xZeroPixel < gridInsets.left + yFontWidth + 5)
	    {
	        xBorder = gridInsets.left + yFontWidth + 5 - xZeroPixel;
	        xZeroPixel = xMathToPixel(xZeroMath);
	    }
	    
	    
	    yBorder = 0; 
	    yZeroPixel = yMathToPixel(yZeroMath);
	    
	    // Add some padding at the bottom, if necessary, in order
	    // to have room to label the x-axis
	    if ( yZeroPixel + fontHeight + 2 > height)
	    {
	        yBorder = yZeroPixel + fontHeight + 2 - height;
	        yZeroPixel = yMathToPixel(yZeroMath);
	    } 
	    
	    
	}
	
	/**
	 * Paints the Grid
	 * 
	 * @param a The Graphics context used to paint the grid
	 */
	public void paintComponent(java.awt.Graphics a)
	{
	    //*****************************************************
	    //*********Local Declarations for this method**********
	    //******************************************************
	    
	    //tempPixel will temporarily hold a pixel coordinate
		// for drawing purposes.
	    int tempPixel;
	    
	   	String lastString, tempString;
		int lastPixel;
		
		String s;
		
		double tempMath;
		
		//************************************************************
		//***********End of Local declarations************************
		//**********************************************************
	    
	    super.paintComponent(a);
	    
	    a.setFont(theFont);
	    
		xMinPixel = xMathToPixel(xMinCurrent);
		xMaxPixel = xMathToPixel(xMaxCurrent);
		yMinPixel = yMathToPixel(yMinCurrent);
		yMaxPixel = yMathToPixel(yMaxCurrent);
		
		
		// ***********************************************
		//*******Draw in the grid lines******************
		//***************************************************
		a.setColor(gridColor);
		
		if ( xMaxCurrent > 0 )
		{
		    tempMath = xZeroMath + xMark;
		    for ( double j = 1 ;(tempMath < xMaxCurrent)&&(j<50);
		        tempMath = xZeroMath + (++j)*xMark)
		    {
		        tempPixel = xMathToPixel(tempMath);
		        a.drawLine(tempPixel, yMinPixel, tempPixel, yMaxPixel);
		    }
		}
		
		
		if ( xMinCurrent < 0 )
		{
		    tempMath = xZeroMath - xMark;
		    for ( double j = 1;(tempMath > xMinCurrent)&&(j<50);
		        tempMath = xZeroMath - (++j)*xMark)
		    {
		        tempPixel = xMathToPixel(tempMath);
		        a.drawLine(tempPixel, yMinPixel, tempPixel, yMaxPixel);
		    }
		}
		
		
		if ( yMaxCurrent > 0 )
		{
		    tempMath = yZeroMath + yMark;
		    for ( double j = 1 ;(tempMath < yMaxCurrent) && (j<50) ;
		        tempMath = yZeroMath + (++j)*yMark)
		    {
		        tempPixel = yMathToPixel(tempMath);
		        a.drawLine(xMinPixel, tempPixel, xMaxPixel, tempPixel);
		    }
		}
		
		if ( yMinCurrent < 0)
		{
		    tempMath = yZeroMath - yMark;
		    for ( double j = 1 ; (tempMath > yMinCurrent) && (j<50) ;
		        tempMath = yZeroMath - (++j)*yMark)
		    {
		        tempPixel = yMathToPixel(tempMath);
		        a.drawLine(xMinPixel, tempPixel, xMaxPixel, tempPixel);
		    }
		}
		//***********************************************************
		//**********end of drawing in grid lines*********************
		//*********************************************************
		
		//*********draw in the coordinate axes******************
		
		a.setColor(axesColor);
		a.drawLine(xZeroPixel, yMinPixel, xZeroPixel, yMaxPixel);
		a.drawLine(xMinPixel, yZeroPixel, xMaxPixel, yZeroPixel);
		
		//*****************************************************
		
		// *****************************************************
		//**********draw in arrows if there is enough room*******
		//********************************************************
		
		if ( xMaxPixel - xZeroPixel > 10 )
		{
		    int[] xCoordinates = {xMaxPixel, xMaxPixel - 8, xMaxPixel -4,
		        xMaxPixel - 8, xMaxPixel};
		    int[] yCoordinates = {yZeroPixel, yZeroPixel - 4, yZeroPixel,
		        yZeroPixel  + 4, yZeroPixel};
		    a.fillPolygon(xCoordinates, yCoordinates, 5);
		}
		
		if ( (yZeroPixel - yMaxPixel) > 10)
		{
		    int[] xCoordinates = {xZeroPixel, xZeroPixel + 4, xZeroPixel,
		        xZeroPixel - 4, xZeroPixel};
		    int[] yCoordinates = {yMaxPixel, yMaxPixel + 8, yMaxPixel + 4,
		        yMaxPixel + 8, yMaxPixel};
		    a.fillPolygon(xCoordinates, yCoordinates, 5);
		}
		
		//*******************************************************
		//***********Draw and label the hashmarks****************
		//********************************************************
		
		lastString = tempString = "";
		lastPixel = xZeroPixel;
		
		if (( xMaxCurrent < 0) || (xMinCurrent > 0))
		{
		    lastString = MathUtility.displayFormat(xZeroMath, 8);
		    if ( fm.stringWidth(lastString) + xZeroPixel < width)
		    {
		        a.drawString (lastString, xZeroPixel + 1, 
		            yZeroPixel + fontHeight + 2);
		    }
		}
		
		if ( xMaxCurrent > 0 )
		{
		    for (double j = 1 ; j<20 ;j++)
		    {
		        tempMath = xZeroMath + xHashMark * j;
		        tempPixel = xMathToPixel(tempMath);
		        tempString = MathUtility.displayFormat(tempMath,8);
		        if (tempPixel < xMaxPixel - 10)
		        {
		             a.drawLine(tempPixel, yZeroPixel - 2, 
		                tempPixel, yZeroPixel + 2);
		             
		            //Only label hashmark if there is enough room
		            if ( ((tempPixel - lastPixel) > fm.stringWidth(lastString))
		            && ((tempPixel + fm.stringWidth(tempString))<(xMaxPixel-10)))
		            {
		                a.drawString(tempString, tempPixel + 1, 
		                    yZeroPixel + fontHeight + 2);
		                lastString = tempString;
		                lastPixel = tempPixel;
		            }
		        }
		        else j = 20;
		    }
		}
		
		if ( xMinCurrent < 0 )
		{
		    lastPixel = xZeroPixel;
		    for ( double j = 1 ; j<20 ;j++)
		    {
		        tempMath = xZeroMath - xHashMark * j;
		        tempPixel = xMathToPixel(tempMath);
		        if ( tempPixel > xMinPixel)
		        {
		            tempString = MathUtility.displayFormat(tempMath,8);
		            a.drawLine(tempPixel, yZeroPixel - 2,
		                tempPixel, yZeroPixel + 2);
		            if ( (lastPixel - tempPixel) > fm.stringWidth(tempString) )
		            {
		                a.drawString(tempString, tempPixel + 1, 
		                    yZeroPixel + fontHeight + 2);
		                lastPixel = tempPixel;
		            }
		        }
		       else j = 20;
		    }
		}
		
		if ( yMaxCurrent > 0 )
		{
		    for ( double j = 1 ; j < 20 ; j++)
		    {
		        tempMath = yZeroMath + yHashMark * j;
		        tempPixel = yMathToPixel(tempMath);
		        
		        if ( tempPixel > (yMaxPixel + 10))
		        {
		            a.drawLine(xZeroPixel - 2, tempPixel, 
		                xZeroPixel + 2, tempPixel);
		            tempString = MathUtility.displayFormat(tempMath,8);
		        
		            a.drawString(tempString, 
		                Math.max(xZeroPixel - fm.stringWidth(tempString)-3,1), 
		                tempPixel + fontHeight/2);
		        }
		        else j = 20;
		    }
		}
		
		if ( yMinCurrent < 0 )
		{
		    for ( double j = 1 ; j < 20 ; j++)
		    {
		        tempMath = yZeroMath - yHashMark * j;
		        tempPixel = yMathToPixel(tempMath);
		        
		        if ( tempPixel < yMinPixel)
		        {
		            a.drawLine(xZeroPixel - 2, tempPixel, 
		                xZeroPixel + 2, tempPixel);
		            tempString = MathUtility.displayFormat(tempMath,8);
		        
		            a.drawString(tempString, 
		                Math.max(xZeroPixel - fm.stringWidth(tempString)-3,1), 
		                tempPixel + fontHeight/2);
		        }
		        else j = 20;
		    }
		}
		//********************************************************
		//*********End of draw and label hashmarks***************
		//*******************************************************
		
		if ( (yZeroPixel - fontHeight - 5) > yMaxPixel)
		{
		    a.drawString(xLabel, 
		        xMaxPixel - fm.stringWidth(xLabel)-2, yZeroPixel -5);
		}
		
		if ( (fm.stringWidth(yLabel) + xZeroPixel + 5) < xMaxPixel)
		{
		    a.drawString(yLabel, xZeroPixel + 5, fontHeight + yMaxPixel);
		}
		
		//****************************************************
		//********draw a box if dragging in zoomBox mode*****
		//****************************************************
		if (dragging)
		{
		    a.setColor(getBackground().darker());
		    a.drawRect(Math.min(xValue1, xValue2),
		                Math.min(yValue1, yValue2),
		                Math.abs(xValue2-xValue1), 
		                Math.abs(yValue2-yValue1)); 
		    
		}
	}//end of paintComponent method
	/**
	 * Converts a math x coordinate into its corresponding 
	 * coordinate in pixels.
	 * 
	 * @param x
	 */


	/**
	 * Converts a math x coordinate into its corresponding 
	 * coordinate in pixels.
	 * 
	 * @param x The x-coordinate to be converted.
	 * @return The distance in pixels from the left side of the MathGrid
	 * of the point with this x-coordinate
	 */
	public int xMathToPixel( double x)
	{
	    return( (int) (   ( (x - xMinCurrent)/xRange ) * (width - xBorder)) )
	        + xBorder + gridInsets.left ;
	}
	
	/**
	 * Converts a math y coordinate into its corresponding 
	 * coordinate in pixels.
	 * 
	 * @param y The y-coordinate to be converted.
	 * @return The distance in pixels from the top of the MathGrid
	 * of the point with this y-coordinate
	 */
	public int yMathToPixel( double y)
	{
	    return ( height - yBorder + gridInsets.top
	        - (int) (  ( (y - yMinCurrent)/yRange ) * (height - yBorder)) );
	}
	
	/**
	 * Converts the pixel version of an x-coordinate
	 * into its corresponding math coordinate
	 * 
	 * @param p The distance in pixels from the left hand side of
	 * the MathGrid
	 * @return The double precsion floating point value that 
	 * corresponds to this x-coordinate of this in the
	 * plane.
	 */
	//Converts a pixel x coordinate into its corresponding coordinate in math
	public double xPixelToMath (int p)
	{
	    return ( (xRange * (p - xBorder - gridInsets.left))/(width - xBorder)
	        + xMinCurrent );
	}
	
	/**
	 * Converts the pixel version of the y-coordinate into
	 * the mathematical one
	 * 
	 * @param p The distance in pixels from the top of the MathGrid
	 * of a point.
	 * @return The double-precision floating point number that 
	 * gives the y-coordinate in the plane of a point
	 * at that location.
	 */
	//Converts a pixel y coordinate into its corresponding coordinate in math
	public double yPixelToMath (int p)
	{
	    return ( yRange * ( height - yBorder - p + gridInsets.top)
	        /( height - yBorder) + yMinCurrent );
	}
	
	//****************************************************************
	// ***************End of utility methods**********************
	//****************************************************************
	
	//****************************************************************
    //*****************Get and Set methods****************************
    //************************************************************
	
	
	/**
	 * Sets the value of xMin
	 * 
	 * @param xMin The smallest value represented on the x-axis.
	 */
	public void setXMin(double xMin)
	{
		if ((this.xMin != xMin)&&(xMin < xMaxCurrent))
		{
			xMinCurrent = this.xMin = xMin;
			updateParameters();
			repaint();
		}
	}

	
	/**
	 * Gets the value of xMin
	 * 
	 * @return The smallest value on the x-axis
	 */
	public double getXMin()
	{
		return this.xMin;
	}

	/**
	 * Sets the value of xMax
	 * 
	 * @param xMax The largest value on the x-axis
	 */
	public void setXMax(double xMax)
	{
		if ((this.xMax != xMax) && (xMax > xMinCurrent))
		{
			xMaxCurrent = this.xMax = xMax;
			updateParameters();
			repaint();
		}
	}

	/**
	 * Gets the value of xMax
	 * 
	 * @return The largest x-value on the x-axis.
	 */
	public double getXMax()
	{
		return this.xMax;
	}

	/**
	 * Sets the value of yMin
	 * 
	 * @param yMin The smallest value on the y-axis
	 */
	public void setYMin(double yMin)
	{
		if ((this.yMin != yMin)&&(yMin < yMaxCurrent))
		{
			yMinCurrent = this.yMin = yMin;
			updateParameters();
			repaint();
		}
	}

	/**
	 * Gets the value of YMin
	 * 
	 * @return The smallest y-value on the y-axis
	 */
	public double getYMin()
	{
		return this.yMin;
	}

	/**
	 * Sets the value of yMax
	 * 
	 * @param yMax The largest y-value on the y-axis.
	 */
	public void setYMax(double yMax)
	{
		if ((this.yMax != yMax) && (yMax > yMinCurrent))
		{
			yMaxCurrent = this.yMax = yMax;
			updateParameters();
			repaint();
		}
	}

	/**
	 * Gets the value of yMax
	 * 
	 * @return The largest y-value on the y-axis
	 */
	public double getYMax()
	{
		return this.yMax;
	}
	
	public synchronized void updateRanges(double xMin, double xMax, double yMin, double yMax)
	{
	    xMinCurrent = this.xMin = xMin;
	    xMaxCurrent = this.xMax = xMax;
	    yMinCurrent = this.yMin = yMin;
	    yMaxCurrent = this.yMax = yMax;
	    updateParameters();
		repaint();
	}
	    

	/**
	 * Sets the color that will be used to draw the axes.
	 * 
	 * @param axesColor The color for the axes.
	 */
	public void setAxesColor(java.awt.Color axesColor)
	{
		this.axesColor = axesColor;
		repaint();
	}

	/**
	 * Gets the color that will be used to draw the axes.
	 * The default is black
	 * 
	 * @return The color of the axes
	 */
	public java.awt.Color getAxesColor()
	{
		return this.axesColor;
	}

	/**
	 * Sets the color to be used for the gridlines. The
	 * default is white.
	 * 
	 * @param gridColor The color of the gridlines.
	 */
	public void setGridColor(java.awt.Color gridColor)
	{
		this.gridColor = gridColor;
		repaint();
	}

	/**
	 * Gets the color to be used for the gridlines
	 * 
	 * @return The color of the gridlines.
	 */
	public java.awt.Color getGridColor()
	{
		return this.gridColor;
	}

	/**
	 * Sets the approximate number of gridlines to be drawn.
	 * Static variables GRIDOFF, GRIDCOARSE, GRIDNORMAL, 
	 * and GRIDFINE, correspond to approximately 
	 * 0, 10, 20, and 30 lines, respectively.
	 * 
	 * @param gridLines The approximate number of gridlines to be drawn. 
	 * (It may be adjusted to make the gridlines appear
	 * at nicer numbers)
	 */
	public void setGridLines(int gridLines)
	{
			this.gridLines = gridLines;
			updateParameters();
			repaint();
	}

	/**
	 * Gets the approximate number of gridlines to be drawn.
	 * 
	 * @return The value of gridLines, which is used to determine
	 * the approximate number of gridlines to be drawn.
	 */
	public int getGridLines()
	{
		return this.gridLines;
	}

	/**
	 * Sets the label to be used for the x-axis.
	 * 
	 * @param xLabel The label for the x-axis. The default is "x".
	 */
	public void setXLabel(java.lang.String xLabel)
	{
			this.xLabel = xLabel;
			repaint();
	}

	/**
	 * Gets the label that will be used for the x-axis.
	 * 
	 * @return The label for the x-axis.
	 */
	public java.lang.String getXLabel()
	{
		return this.xLabel;
	}

	/**
	 * Sets the label to be used for the y-axis.
	 * 
	 * @param yLabel The label for the y-axis. The default is "y".
	 */
	public void setYLabel(java.lang.String yLabel)
	{
			this.yLabel = yLabel;
			repaint();
	}

	/**
	 * Gets the Label to be used for the y-axis
	 * 
	 * @return The label for the y-axis.
	 */
	public java.lang.String getYLabel()
	{
		return this.yLabel;
	}
    
    /**
     * This sets the zoom mode by assigning a value
     * to the parameter zoomMode. Depending on the value
     * of zoomMode the MathGrid will zoom in, zoom out
     * show the coordinates of a point, redraw in 
     * the coordinates of a box drawn by clicking and
     * dragging, or disable zooming.
     * 
     * @param zoomMode The integer that will determine the zoom mode. Static
     * constants ZOOMIN, ZOOMOUT, ZOOMBOX, SHOWPOINT, ZOOMOFF
     * are provided.
     * @see ZOOMOUT
     * @see ZOOMIN
     * @see ZOOMBOX
     * @see SHOWPOINT
     * @see ZOOMOFF
     */
	public void setZoomMode(int zoomMode)
	{
		this.zoomMode = zoomMode;
		if ( zoomMode != SHOWPOINT)
		{
		    innerBorder.setTitle(message);
	        repaint();
		}
	}

	/**
	 * Gets the value of zoomMode
	 * 
	 * @return The value of zoomMode which determines the 
	 * zoom mode for the MathGrid.
	 */
	public int getZoomMode()
	{
		return this.zoomMode;
	}
	
	/**
	 * Sets the titleEnabled property which determines 
	 * whether or not the title at the bottom of the 
	 * coordinate system will be displayed.
	 * 
	 * @param enabled Will be true if the title is to be displayed.
	 */
	public void setTitleEnabled(boolean enabled)
	{
	    if( !enabled ) setTitle("");
	    titleEnabled = enabled;
	}
	/**
	 * Determines whether or not the title at the bottom
	 * of the coordinate system is enabled.
	 * 
	 * @return True if it is to be displayed/
	 */
	
	public boolean isTitleEnabled()
	{
	    return titleEnabled;
	}
	
	/**
	 * Sets the font that will be used for the title at
	 * the bottom of the coordinate system
	 * 
	 * @param f The font to be used.
	 */
	public void setTitleFont(Font f)
	{
	    titleFont = f;
	    outerBorder.setTitleFont(f);
	    adjustBorders();
	    repaint();
	}
	
	/**
	 * Gets the font that will be used for the title at
	 * the bottom of the coordinate system
	 */
	public Font getTitleFont()
	{
	    return titleFont;
	}

	/**
	 * Sets the title that will be displayed at the
	 * bottom of the graph.
	 * 
	 * @param title The title that will be displayed at the bottom of
	 * the grid.
	 */
	public void setTitle(java.lang.String title)
	{
		this.title = title;
		if ( titleEnabled )
		{
		    outerBorder.setTitle(title);
		    adjustBorders();
		    repaint();
		}
	}

	/**
	 * Gets the title that is displayed at the bottom
	 * of the grid.
	 * 
	 * @return The title that is displayed at the bottom of the 
	 * grid.
	 */
	public java.lang.String getTitle()
	{
		return this.title;
	}
	
	/**
	 * Sets the value of the traceFont which is used for 
	 * the message that is displayed at the top of the
	 * coordinate system. This message is generally used to
	 * display the coordinates for a trace.
	 * 
	 * @param f The font to be used.
	 */
	public void setTraceFont(Font f)
	{
	    traceFont = f;
	    innerBorder.setTitleFont(f);
	    adjustBorders();
	    repaint();
	}
	
	/**
	 * Gets the font that is used for the message at the
	 * top of the coordinate system.
	 * 
	 * @return The font to be used.
	 */
	public Font getTraceFont()
	{
	    return traceFont;
	}

	/**
	 * Sets the message that will be displayed at the top
	 * of the grid.
	 * 
	 * @param message Gets the message that is displayed at the bottom
	 * of the grid.
	 */
	public void setMessage(java.lang.String message)
	{
	    this.message = message;
	    innerBorder.setTitle(message);
	    adjustBorders();
	    repaint();
	}

	/**
	 * Gets the message that will appear at the top of
	 * the grid.
	 * 
	 * @return The message that will appear at the top of the grid.
	 */
	public java.lang.String getMessage()
	{
		return this.message;
	}

	/**
	 * Sets the factor that will be used for zooming in
	 * Zoom In, or Zoom Out mode. In Zoom In mode it
	 * will divide by this factor and in Zoom Out mode
	 * it will multiply by it to determine the new range
	 * of x and y values.
	 * 
	 * @param zoomFactor The factor that will be used in zooming. Any number
	 * smaller than one will be ignored.
	 */
	public void setZoomFactor(int zoomFactor)
	{
	    if( zoomFactor >= 1)
	    {
			this.zoomFactor = zoomFactor;
		}
	}

	/**
	 * Gets the factor that will be used in  Zoom In or
	 * Zoom Out mode.
	 * 
	 * @return The factor that will be used in Zoom In or Zoom Out
	 * mode.
	 */
	public int getZoomFactor()
	{
		return this.zoomFactor;
	}
	
	//****************************************************************
    //************End of Get and Set methods***************************
    //***************************************************************
	
	/**
	 * Gives a String description of the coordinate system
	 * 
	  * @return A string description of the coordinate system.
	*/	
    public java.lang.String toString()
	{
		return "[" + xMin + "," + xMax + "]" + " by [" + yMin + ","
		    + yMax + "] coordinate grid";
	}

	/**
	 * Overrides the setSize method so that it will update
	 * the parmeters before redrawing.
	 * 
	 * @param d The dimension object that determines the new size.
	 */
	public void setSize(Dimension d)
	{
	    super.setSize(d);
	    updateParameters();
	}
	
	/**
	 * Overrides the setSize method so that it will 
	 * update the parameters before redrawing.
	 * 
	 * @param w The width in pixels of the new size.
	 * @param h The height in pixels of the new size
	 */
	public void setSize ( int w, int h)
	{
	    super.setSize(w,h);
	    updateParameters();
	}
	
	/**
	 * overrides the setBounds method so that it will
	 * update the parameters before redrawing.
	 * 
	 * @param x The x-coordinate of the upper left hand corner
	 * of the MathGrid in its new location in the container.
	 * @param y The y-coordinate of the upper left hand
	 * corner of the MathGrid in its new location 
	 * in its container.
	 * @param width The width in pixels.
	 * @param height The height in pixels
	 */
	public void setBounds(int x, int y, int width, int height)
	{
	    super.setBounds(x,y,width,height);
	    updateParameters();
	}
	
	/**
	 * overrides the setBounds method so that it will
	 * update the parameters before redrawing.
	 * 
	 * @param r The rectangle correponding to the new bounds
	 */
	public void setBounds(Rectangle r)
	{
	    super.setBounds(r);
	    updateParameters();
	}
	
	/**
	 * This method has been deprecated and should be removed
	 * when possible. It was put in because the VC environment
	 * appeared to use it
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param width Width
	 * @param height Height
	 */
	//deprecated
	public void reshape(int x, int y, int width, int height)
	{
	    super.reshape(x,y,width,height);
	    updateParameters();
	}
	/**
	 * This method has been deprecated and should be removed
	 * when possible. It was put in because the VC environment
	 * appeared to use it
	 * 
	 * @param width The width of the region
	 * @param height The height of the region
	 */
	
	//This method has been deprecated and should be removed
	//when possible. It was put in because the VC environment
	//appeared to use it
	public void resize(int width, int height)
	{
	    super.resize(width, height);
	    updateParameters();
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
				this.setSize(400,300);
				getContentPane().add(new MathGrid());
			}
		}

		new DriverFrame().show();
	}


	class SymMouse extends java.awt.event.MouseAdapter
	{
		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == MathGrid.this)
				MathGrid_mouseReleased(event);
		}

		public void mousePressed(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == MathGrid.this)
				MathGrid_mousePressed(event);
		}
	}

	void MathGrid_mousePressed(java.awt.event.MouseEvent event)
	{
		requestFocus();
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
		
		repaint();
	}

	void MathGrid_mouseReleased(java.awt.event.MouseEvent event)
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

	class SymMouseMotion extends java.awt.event.MouseMotionAdapter
	{
		public void mouseDragged(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == MathGrid.this)
				MathGrid_mouseDragged(event);
		}
	}

	/**
	 * 
	 * @param event
	 */
	void MathGrid_mouseDragged(java.awt.event.MouseEvent event)
	{
		if ((zoomMode == ZOOMBOX) && ( !(event.isShiftDown())))
	    {
		    dragging = true;
		    xValue2 = event.getX();
		    yValue2 = event.getY();
		    repaint();
		}
	}
}