package EDU.emporia.mathbeans;

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
 * @since June 3, 2002
 * @see ScrollControlPanel
 */
public class DirectionPanel extends JPanel
{
    
    /**
     * The MathGrapher object to be scrolled.
     */
    protected EDU.emporia.mathbeans.MathGrapher grapher = new MathGrapher();
    /**
     * The delay (in milliseconds) will control the speed of the scroll.
     */
     
    protected int delay = 100;
    /**
     * The thread that will be do the scrolling.
     */
    
    protected ScrollThread scrolling = new ScrollThread();
    
    /**
     * Records the current state of this value for the 
     * MathGrapher object.
     */
    protected double xMin=-10, xMax=10, yMin=-10, yMax=10;
    
    /**
     * Keeps track of how many scrolling steps have been
     * implemented.
     */
    protected int counter=0;
    
    /**
     * Boolean to keep track of the first time that the mouse
     * is pressed so that it can start the thread.
     */
    protected boolean first = true;
    
    /**
     * boolean to record when the thread is suspended.
     */
    private volatile boolean suspended = false;
    
    protected double horizontalDelta, verticalDelta;
    
    /**
     * Static constant that will be assigned to the direction to determine
     * the directions of the scroll.
     */
    
    public static final int LEFT = 0, UPLEFT = 1, UP = 2, UPRIGHT = 3, RIGHT = 4,
                        DOWNRIGHT = 5, DOWN = 6, DOWNLEFT = 7, CENTER = 8;

    /**
     * A parameter to keep track of whether the scrolling
     * is up or down, left or right
     */
     protected int direction = 0;
     
    /**
     * Parameter that will be -1, 0, or 1 to adjust the vertical
     * component of the scroll.
     */
    protected int vertical = 0; 
    
     /**
     * Parameter that will be -1, 0, or 1 to adjust the horizontal
     * component of the scroll.
     */
    protected int horizontal = 0;
    
    
    /**
     * Default values for the ranges of x and y-values
     * to be displayed.
     */
    protected double defaultXMax=10, defaultXMin=-10, defaultYMax=10, defaultYMin=-10;
    
    public DirectionPanel()
    {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEtchedBorder());
    
		//{{REGISTER_LISTENERS
		SymMouse aSymMouse = new SymMouse();
		this.addMouseListener(aSymMouse);
		//}}
	}
    
    public DirectionPanel(EDU.emporia.mathbeans.MathGrapher g, int direction  )
    {
        setDirection(direction);
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
        int xDelta = width/4;
        int yDelta = height/4;
        int delta = Math.min(xDelta, yDelta);
        Polygon p;
        
        switch( direction )
        {
            case LEFT:
                 p = new Polygon(new int[]{width - xDelta, xDelta, width - xDelta, width/2}, 
                                 new int[]{yDelta, height/2 , height - yDelta, height/2},
                                 4);
                 break;
            case UPLEFT:
                 p = new Polygon(new int[]{width/2 + delta, width/2 - delta, width/2, width/2}, 
                                 new int[]{height/2, height/2 - delta , height/2 + delta, height/2},
                                 4);
                 break;
            case UP:
                 p = new Polygon(new int[]{xDelta, width/2, width - xDelta, width/2}, 
                                 new int[]{height - yDelta, yDelta , height - yDelta, height/2},
                                 4);
                 break;
            case UPRIGHT:
                 p = new Polygon(new int[]{width/2 - delta, width/2 + delta, width/2, width/2}, 
                                 new int[]{height/2, height/2 - delta , height/2 + delta, height/2},
                                 4);
                 break;
            case RIGHT:
                 p = new Polygon(new int[]{xDelta, width - xDelta, xDelta, width/2}, 
                                 new int[]{yDelta, height/2 , height - yDelta, height/2},
                                 4);
                 break;
            case DOWNRIGHT:
                 p = new Polygon(new int[]{width/2 - delta, width/2 + delta, width/2, width/2}, 
                                 new int[]{height/2, height/2 + delta , height/2 - delta, height/2},
                                 4);
                 break;
            case DOWN:
                 p = new Polygon(new int[]{xDelta, width/2, width - xDelta, width/2}, 
                                 new int[]{yDelta, height - yDelta , yDelta, height/2},
                                 4);
                 break;
            case DOWNLEFT:
                 p = new Polygon(new int[]{width/2 + delta, width/2 - delta, width/2, width/2}, 
                                 new int[]{height/2, height/2 + delta , height/2 - delta, height/2},
                                 4);
                 break;
            default:
                p = new Polygon();
                break;
        }
        
        if (direction == CENTER ) 
        {
            g.fillOval( width/2-delta, height/2 - delta, 2 * delta, 2 * delta);
            g.setColor(getBackground());
            g.fillOval( width/2 -delta + 3 , height/2 - delta  + 3, 2*(delta - 3), 2*(delta -3
            ));
            
        }
        else g.fillPolygon(p); 
    }
    
    /**
     * Sets up a Thread object to control the scrolling
     * 
     * @author Joe Yanik
     * @version 1.0
     * @since June 3, 2002
     */
    class ScrollThread extends Thread
	{
	    public void run()
	    {	        
	        doScrolling();
	    }
	    
	    synchronized void doScrolling()
	    {
	        while(!interrupted() )
	        {
	            if(!suspended)
	            {
	                if ( (++counter)%10 == 0 )
	                {
	                    horizontalDelta *= 2;
	                    verticalDelta *= 2;
	                }
	                if ( horizontalDelta != 0)
	                {
	                    xMin += horizontalDelta;
	                    xMax += horizontalDelta;
	                }
                    
                    if ( verticalDelta != 0)
                    {
                        yMin += verticalDelta;
                        yMax += verticalDelta;
		            }
		            
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
	 * Specifies the direction of the scroll
	 * 
	 * @param d The parameter determines the direction of the
	 * scroll.
	 */
	public void setDirection( int d )
	{
	    if ( (d >= 0 ) && ( d <= 8) )
	    {
	        direction = d;
	        switch( direction )
            {
                case LEFT:
                    horizontal = -1;
                    vertical = 0;
                    setToolTipText("Scroll Left");
                    break;
                case UPLEFT:
                    horizontal = -1;
                    vertical = 1;
                    setToolTipText("Scroll Up-Left");
                    break;
                case UP:
                    horizontal = 0;
                    vertical = 1;
                    setToolTipText("Scroll Up");
                    break;
                case UPRIGHT:
                    horizontal = 1;
                    vertical = 1;
                    setToolTipText("Scroll Up-Right");
                    break;
                case RIGHT:
                    horizontal = 1;
                    vertical = 0;
                    setToolTipText("Scroll Right");
                    break;
                case DOWNRIGHT:
                    horizontal = 1;
                    vertical = -1;
                    setToolTipText("Scroll Down-Right");
                    break;
                case DOWN:
                    horizontal = 0;
                    vertical = -1;
                    setToolTipText("Scroll Down");
                    break;
                case DOWNLEFT:
                    horizontal = -1;
                    vertical = -1;
                    setToolTipText("Scroll Down-Left");
                    break;
                default:
                    horizontal = 0;
                    vertical = 0;
                    setToolTipText("Return to Defaults");
                    break;
            }
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
	public void setGrapher( EDU.emporia.mathbeans.MathGrapher g)
	{
	    grapher = g;
	}
	
	/**
	 * This updates the default values that will be used
	 * when the MathGrapher is recentered.
	 * 
	 * @param xMin The minimum value shown on the x-axis
	 * @param xMax The maximum value shown on the x-axis
	 * @param yMin The minimum value displayed on the y-axis
	 * @param yMax The maximum value displayed on the y-axis.
	 */
	public void updateDefaults(double xMin, double xMax, double yMin, double yMax)
	{
	    defaultXMin = xMin;
	    defaultXMax = xMax;
	    defaultYMin = yMin;
	    defaultYMax = yMax;
	}

	class SymMouse extends java.awt.event.MouseAdapter
	{
		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == DirectionPanel.this)
				DirectionPanel_mouseReleased(event);
		}

		public void mousePressed(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == DirectionPanel.this)
				DirectionPanel_mousePressed(event);
		}
	}

	/**
	 * Method to start the scrolling thread to begin the scroll
	 * 
	 * @param event
	 */
	void DirectionPanel_mousePressed(java.awt.event.MouseEvent event)
	{
	    if ( direction == CENTER )
	    {
	        grapher.updateRanges(defaultXMin, defaultXMax, defaultYMin, defaultYMax);
	    }
	    else
	    {
	        synchronized(grapher)
	        {
	            if( scrolling != null )
	            {
	                updateParameters();		    
	                if( first )
	                {
		                scrolling.start();
		                first = false;
		            }
		            else
		            {
		                scrolling.safeResume();
		            }
		        }
		    }
		}
	}

	/**
	 * Suspends the thread when the mouse is released.
	 * 
	 * @param event
	 */
	void DirectionPanel_mouseReleased(java.awt.event.MouseEvent event)
	{
		if ( scrolling != null ) 
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
	    horizontalDelta = horizontal * (xMax - xMin)/20;
	    verticalDelta = vertical * (yMax - yMin)/20;
	            
	    counter = 0;
	}

}