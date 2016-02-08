package edu.emporia.mathbeans;

import javax.swing.*;
import java.awt.*;

/**
 * Class to create objects to be placed on a
 * ScrollControlPanel. Depending on the properties
 * a DirectionPanel object will allow scrolling of
 * a MathGrapher object in one of 8 directions or 
 * recentering
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since May 30, 2002
 * @see ScrollControlPanel
 */
public class ZoomPanel extends JPanel
{
    
    /**
     * The MathGrapher object to be zoomed.
     */
    protected edu.emporia.mathbeans.MathGrapher grapher = new MathGrapher();
    /**
     * A Thread to control the zooming
     */
    
    protected ZoomThread zooming = new ZoomThread();
    /**
     * Static constants determining whether it will zoom in or out.
     */
    
    public static final int ZOOMIN = 1, ZOOMOUT = -1;
    
    /**
     * Records the current state of this value for the 
     * MathGrapher object.
     */
    protected double xMin=-10, xMax=10, yMin=-10, yMax=10;
    
    
    /**
     * The delay in milliseconds between zoom steps.
     */
    protected int delay = 100;
    
    /**
     * Determines whether it zooms in or zooms out.
     */
    protected int zoomMode = 1;
    
    /**
     * Boolean to keep track of the first time that the mouse
     * is pressed so that it can start the thread.
     */
    protected boolean first = true;
    
    /**
     * boolean to record when the thread is suspended.
     */
    private volatile boolean suspended = false;

    
    public ZoomPanel()
    {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEtchedBorder());
    
		//{{REGISTER_LISTENERS
		SymMouse aSymMouse = new SymMouse();
		this.addMouseListener(aSymMouse);
		//}}
	}
    
    public ZoomPanel(edu.emporia.mathbeans.MathGrapher g, int zoomMode  )
    {
        setZoomMode(zoomMode);
        setDelay(100);
        setGrapher(g);
        setOpaque(false);
        setBorder(BorderFactory.createEtchedBorder());
        
        SymMouse aSymMouse = new SymMouse();
		this.addMouseListener(aSymMouse);
    }
    
    
    public void paintComponent( Graphics g)
    {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int delta = Math.min(width/3,height/3);
        int edge = delta/2;
        int xCenter = width/2, yCenter = height/2;
        int x[], y[];
        
        g.setColor(getForeground());
        g.fillOval(xCenter - delta, yCenter - delta, 2*delta , 2* delta);
        g.setColor(Color.white);
        g.fillOval(xCenter - delta + 2, yCenter - delta + 2, 2*delta - 4, 2* delta - 4);
        
        g.setColor(getForeground());
        
        Polygon p = new Polygon();
        
        if ( zoomMode == ZOOMIN )
        {
            p.addPoint(xCenter - 2, yCenter - delta + edge);
            p.addPoint(xCenter - 2, yCenter - 2);
            p.addPoint(xCenter - delta + edge, yCenter - 2);
            p.addPoint(xCenter - delta + edge, yCenter + 2);
            p.addPoint(xCenter - 2, yCenter + 2);
            p.addPoint(xCenter - 2, yCenter + delta - edge);
            p.addPoint(xCenter + 2, yCenter + delta - edge);
            p.addPoint(xCenter + 2, yCenter + 2);
            p.addPoint(xCenter + delta - edge, yCenter + 2);
            p.addPoint(xCenter + delta - edge, yCenter - 2);
            p.addPoint(xCenter + 2, yCenter - 2);
            p.addPoint(xCenter + 2, yCenter - delta + edge);
        }
        else
        {
            p.addPoint(xCenter - delta + edge, yCenter - 2);
            p.addPoint(xCenter - delta + edge, yCenter + 2);
            p.addPoint(xCenter + delta - edge, yCenter + 2);
            p.addPoint(xCenter + delta - edge, yCenter - 2);
        }
        
        g.fillPolygon(p);
    }
    
    /**
     * Sets up a Thread object to control the scrolling
     * 
     * @author Joe Yanik
     * @version 1.0
     * @since May 30, 2002
     */
    class ZoomThread extends Thread
	{
	    public void run()
	    {
	        doZooming();
        }
        
        synchronized void doZooming()
	    {
	        while(!interrupted() )
	        {
	            if(!suspended)
	            {
	                double horizontalDelta  = zoomMode * (xMax - xMin)/20,
	                        verticalDelta = zoomMode * (yMax - yMin)/20;
	                xMin = xMin + horizontalDelta;
	                xMax = xMax - horizontalDelta; 
	                yMin = yMin + verticalDelta;
	                yMax = yMax - verticalDelta;
	                
		            
		            grapher.updateRanges(xMin, xMax, yMin, yMax);
		            try
		            {
		                sleep(delay);
		            }catch( InterruptedException exception){ exception.printStackTrace(); }
                }
                else
                {
                    synchronized(this)
                    {
                        while(suspended)
                        {
                            try
                            {
                                wait();
                            }catch( InterruptedException exception){ }
                        }
                    }
                }
            }
            
        }
        
        
	    synchronized void safeResume()
	    {
	        suspended = false;
	        notify();
	    }
	}
	
	/**
	 * Specifies the zoom mode
	 * 
	 * @param d The parameter determines whether it will zoom in or zoom out.
	 * @param z
	 */
	public void setZoomMode( int z )
	{
	    if ( z == -1 )
	    {
	        zoomMode = z;
	        setToolTipText("Zoom Out");
	    }
	    else if ( z == 1)
	    {
	        zoomMode = z;
	        setToolTipText("Zoom In");
	    }
	}
	
	/**
	 * Sets the number of milliseconds delay between shifts.
	 * 
	 * @param d The delay in milliseconds.
	 */
	public void setDelay ( int d)
	{
	    delay = d;
	}
	
	/**
	 * Sets the MathGrapher object to be scrolled.
	 * 
	 * @param g The MathGrapher object to be scrolled.
	 */
	public void setGrapher( edu.emporia.mathbeans.MathGrapher g)
	{
	    grapher = g;
	}
	
	

	class SymMouse extends java.awt.event.MouseAdapter
	{
		
		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == ZoomPanel.this)
				ZoomPanel_mouseReleased(event);
		}

		public void mousePressed(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == ZoomPanel.this)
				ZoomPanel_mousePressed(event);
		}
	}

	void ZoomPanel_mousePressed(java.awt.event.MouseEvent event)
	{
	    synchronized(grapher)
	        {
	            if( zooming != null )
	            {
	                updateParameters();		    
	                if( first )
	                {
		                zooming.start();
		                first = false;
		            }
		            else
		            {
		                zooming.safeResume();
		            }
		        }
		    }
	}

	void ZoomPanel_mouseReleased(java.awt.event.MouseEvent event)
	{
		if ( zooming != null ) 
		{
		    suspended = true;
		}
	}
	/**
	 * Updates the values of xMax, xMin, yMax, and yMin to 
	 * correspond to the current state of the MathGrapher.
	 */
	
	void updateParameters()
	{
	    xMin = grapher.getXMin(); 
	    xMax = grapher.getXMax();
	    yMin = grapher.getYMin();
	    yMax = grapher.getYMax();	            
	}

}