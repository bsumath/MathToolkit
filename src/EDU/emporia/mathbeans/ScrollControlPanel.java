package EDU.emporia.mathbeans;

import java.awt.*;


/**
 * A class to create a control panel to allow scrolling of 
 * a MathGrapher object.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since May 30, 2002
 */
public class ScrollControlPanel extends javax.swing.JPanel
{

    /**
     * An array of DirectionPanels to control the scrolling
     */
    protected DirectionPanel[] scrollers = new DirectionPanel[9];
    
	public ScrollControlPanel()
	{
		//{{INIT_CONTROLS
		setLayout(new GridLayout(3,3,0,0));
		setSize(100,100);
		//}}
		
		scrollers[0] = new DirectionPanel(grapher, DirectionPanel.UPLEFT);
		scrollers[1] = new DirectionPanel(grapher, DirectionPanel.UP);
		scrollers[2] = new DirectionPanel(grapher, DirectionPanel.UPRIGHT);
		scrollers[3] = new DirectionPanel(grapher, DirectionPanel.LEFT);
		scrollers[4] = new DirectionPanel(grapher, DirectionPanel.CENTER);
		scrollers[5] = new DirectionPanel(grapher, DirectionPanel.RIGHT);
		scrollers[6] = new DirectionPanel(grapher, DirectionPanel.DOWNLEFT);
		scrollers[7] = new DirectionPanel(grapher, DirectionPanel.DOWN);
		scrollers[8] = new DirectionPanel(grapher, DirectionPanel.DOWNRIGHT);
		
		for ( int i=0 ; i < scrollers.length ; i++)
		{
		    scrollers[i].setForeground(getForeground());
		    add(scrollers[i]);
		    scrollers[i].setGrapher(grapher);
		}
	}

	//{{DECLARE_CONTROLS
	//}}

	/**
	 * Sets the delay that will be used in scrolling (in milliseconds)
	 * 
	 * @param scrollDelay The delay in milliseconds in scrolling. Smaller values will
	 * result in faster scrolls.
	 */
	public void setScrollDelay(int scrollDelay)
	{
			this.scrollDelay = scrollDelay;
			if ( scrollers != null)
		        for ( int i=0 ; i < scrollers.length ; i++)
		            if ( scrollers[i] != null ) scrollers[i].setDelay(scrollDelay);
		        
	}

	/**
	 * Gets the delay in milliseconds that will be used in scrolling
	 */
	public int getScrollDelay()
	{
		return this.scrollDelay;
	}

	/**
	 * Sets the MathGrapher object to be scrolled
	 * 
	 * @param grapher The MathGrapher object to be scrolled.
	 */
	public void setGrapher(EDU.emporia.mathbeans.MathGrapher grapher)
	{
		this.grapher = grapher;
		if ( scrollers != null)
		    for ( int i=0 ; i < scrollers.length ; i++)
		        if ( scrollers[i] != null ) scrollers[i].setGrapher(grapher);
		updateDefaults();   
	}

	/**
	 * Gets the MathGrapher object that will be scrolled.
	 */
	public EDU.emporia.mathbeans.MathGrapher getGrapher()
	{
		return this.grapher;
	}

	public java.awt.Dimension getPreferredSize()
	{
		return new Dimension(100, 100);
	}

	/**
	 * Overrides the setBackground method so that it also sets
	 * the background of the DirectionPanel components.
	 * 
	 * @param a
	 */
	public void setBackground(java.awt.Color a)
	{
	    super.setBackground(a);
		if ( scrollers != null)
		    for ( int i=0 ; i < scrollers.length ; i++)
		        if ( scrollers[i] != null ) scrollers[i].setBackground(a);
	}

	/**
	 * Overrides the setForeground method so that is also sets the 
	 * foreground of the DirectionPanel components
	 * 
	 * @param a
	 */
	public void setForeground(java.awt.Color a)
	{
	    super.setForeground(a);
		if ( scrollers != null)
		    for ( int i=0 ; i < scrollers.length ; i++)
		        if ( scrollers[i] != null ) scrollers[i].setForeground(a);
	}

	public java.lang.String toString()
	{
		return "ScrollerControlPanel for " + grapher.toString();
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
				getContentPane().add(new ScrollControlPanel());
			}
		}

		new DriverFrame().show();
	}
	
	/**
	 * Updates the default values to be used when the MathGrapher is
	 * recentered.
	 */
	public void updateDefaults()
	{
	    if ( scrollers != null)
	    {
	        double  xMin = grapher.getXMin(), 
		            xMax = grapher.getXMax(),
		            yMin = grapher.getYMin(),
		            yMax = grapher.getYMax();
		    for ( int i=0 ; i < scrollers.length ; i++)
		        if ( scrollers[i] != null ) 
		            scrollers[i].updateDefaults(xMin,xMax,yMin,yMax);
		}
	}
	    


	/**
	 * The delay (in milliseconds) used in the scrolling.
	 */
	protected int scrollDelay = 100;

	/**
	 * The MathGrapher object to be scrolled.
	 */
	protected EDU.emporia.mathbeans.MathGrapher grapher = new MathGrapher();

}