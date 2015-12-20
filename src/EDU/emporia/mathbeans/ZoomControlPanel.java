package EDU.emporia.mathbeans;

import java.awt.*;



/**
 * A ZoomControlPanel is intended to be associated with a MathGrapher.
 * It allows zooming in and out of the associated MathGrapher.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since June 1, 2002
 */
public class ZoomControlPanel extends javax.swing.JPanel
{
    /**
	 * The MathGrapher object to be zoomed.
	 */
	protected EDU.emporia.mathbeans.MathGrapher grapher;
	
	/**
	 * The delay (in milliseconds) used in the zooming.
	 */
	protected int zoomDelay = 100;
	
    /**
     * ZoomPanel to enable zooming in.
     * 
     * @see ZoomPanel
     */
    protected ZoomPanel zoomInPanel = new ZoomPanel(grapher,ZoomPanel.ZOOMIN);
    
    /**
     * ZoomPanel to enable zooming out.
     * 
     * @see ZoomPanel
     */
    protected ZoomPanel zoomOutPanel = new ZoomPanel(grapher, ZoomPanel.ZOOMOUT);

	public ZoomControlPanel()
	{
		//{{INIT_CONTROLS
		setLayout(new GridLayout(2,1,0,0));
		setSize(50,100);
		//}}
		
		zoomInPanel.setForeground(getForeground());
		zoomOutPanel.setForeground(getForeground());
		
		add(zoomInPanel);
		add(zoomOutPanel);
	}

	//{{DECLARE_CONTROLS
	//}}
	
	/**
	 * Sets the MathGrapher object to be scrolled
	 * 
	 * @param grapher The MathGrapher object to be scrolled.
	 */
	public void setGrapher(EDU.emporia.mathbeans.MathGrapher grapher)
	{
		this.grapher = grapher;
		
		if ( (zoomInPanel != null) && (zoomOutPanel != null) )
	    {
		    zoomInPanel.setGrapher(grapher);
		    zoomOutPanel.setGrapher(grapher);
        }
	}

	/**
	 * Gets the grapher object that is controlled by the panel.
	 */
	public EDU.emporia.mathbeans.MathGrapher getGrapher()
	{
		return this.grapher;
	}

	/**
	 * Sets the delay (in milliseconds) that controls the speed of
	 * zooming.
	 * 
	 * @param zoomDelay The delay.
	 */
	public void setZoomDelay(int zoomDelay)
	{
	    this.zoomDelay = zoomDelay;
	    if ( (zoomInPanel != null) && (zoomOutPanel != null) )
	    {
		    zoomInPanel.setDelay(zoomDelay);
		    zoomOutPanel.setDelay(zoomDelay);
        }
	}

	/**
	 * Gets the delay (in milliseconds) to be used to control
	 * the speed of the zooming.
	 * 
	 * @return The delay that controls the zooming speed.
	 */
	public int getZoomDelay()
	{
		return this.zoomDelay;
	}
	
	public java.awt.Dimension getPreferredSize()
	{
		return new Dimension(50, 100);
	}
	
	/**
	 * Overrides the setBackground  method so that it also changes
	 * the background color of the ZoomPanel components.
	 * 
	 * @param a
	 */
	public void setBackground(java.awt.Color a)
	{
	    super.setBackground(a);
		if ( (zoomInPanel != null) && (zoomOutPanel != null) )
	    {
		    zoomInPanel.setBackground(a);
		    zoomOutPanel.setBackground(a);
        }
	}
	/**
	 * Overrides the setForeground method so that it also sets the
	 * Foreground color for the ZoomPanel components.
	 * 
	 * @param a
	 */

	public void setForeground(java.awt.Color a)
	{
	    super.setForeground(a);
		if ( (zoomInPanel != null) && (zoomOutPanel != null) )
	    {
		    zoomInPanel.setForeground(a);
		    zoomOutPanel.setForeground(a);
        }
	}

	public java.lang.String toString()
	{
		return "ZoomControlPanel for " + grapher.toString();
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
				setSize(50,100);
				getContentPane().add(new ZoomControlPanel());
			}
		}

		new DriverFrame().show();
	}
	

}