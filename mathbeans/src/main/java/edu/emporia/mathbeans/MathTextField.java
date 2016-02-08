package edu.emporia.mathbeans;
import java.awt.*;
import javax.swing.*;
import edu.emporia.mathtools.*;
import java.awt.event.*;


/**
 * A text field that can be used to display numerical data for mathematical purposes.
 * It can parse common arithmetic expressions
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/6/00
 */
public class MathTextField extends javax.swing.JTextField
{

    /**
     * The mathematical value represented in the text field
     */
    protected double mathValue = 0;

    /**
     * The error message that should be displayed in the case of a parsing
     * error
     */
    protected boolean errorMessage = false;
    
	public MathTextField()
	{
	    super();
	    
		//{{INIT_CONTROLS
		setSize(100,30);
		//}}
			
		//{{REGISTER_LISTENERS
		SymAction lSymAction = new SymAction();
		this.addActionListener(lSymAction);
		//}}
	}

	//{{DECLARE_CONTROLS
	//}}

	/**
	 * Sets the maximum number of characters (not counting the decimal point)
	 * that will be displayed in the text field. Numerical data will be 
	 * formatted to fit into that space. Must be at least 6.
	 * 
	 * @param maxNumberOfCharacters The maximum number of characters (not counting the decimal point) that
	 * will be displayed. Any value smaller than 6 will be ignored.
	 */
	public void setMaxNumberOfCharacters(int maxNumberOfCharacters)
	{
	    if ( maxNumberOfCharacters >= 6 )
			this.maxNumberOfCharacters = maxNumberOfCharacters;
	}

	/**
	 * Gets the maximum number of characters (not counting the decimal point)
	 * that will be displayed in the text field. Numerical data will be 
	 * formatted to fit into that space. Must be at least 6.
	 * 
	 * @return The maximum number of characters (not counting the decimal point) that
	 * will be displayed.
	 */
	public int getMaxNumberOfCharacters()
	{
		return this.maxNumberOfCharacters;
	}

	/**
	 * Sets the value of autoNumberOfCharacters which determines whether or not
	 * the number of characters should be determined by user preference or
	 * by the width of the text field
	 * 
	 * @param autoNumberOfCharacters If true the maximum number of characters will be based on the width of 
	 * the text field. Otherwise it will be based on the value of 
	 * maxNumberOfCharacters.
	 */
	public void setAutoNumberOfCharacters(boolean autoNumberOfCharacters)
	{
			this.autoNumberOfCharacters = autoNumberOfCharacters;
	}

	/**
	 * Returns the value of autoNumberOfCharacters which determines whether or not
	 * the number of characters should be determined by user preference or
	 * by the width of the text field
	 * 
	 * @return True if the maximum number of characters will be based on the width of 
	 * the text field. False if it will be based on the value of 
	 * maxNumberOfCharacters.
	 */
	public boolean isAutoNumberOfCharacters()
	{
		return this.autoNumberOfCharacters;
	}

	
	/**
	 * Overridden method to adjust the number of characters displayed when
	 * the text field is resized.
	 * 
	 * @param a x-coordinate
	 * @param b y-coordinate
	 * @param c with
	 * @param d height
	 */
	public void setBounds(int a, int b, int c, int d)
	{
		super.setBounds(a,b,c,d);
	    if (autoNumberOfCharacters)
	        adjustCharacterLength(c);
	}

	/**
	 * Overridden method to adjust the number of characters displayed when
	 * the text field is resized.
	 * 
	 * @param a The rectangle used in resizing
	 */
	public void setBounds(java.awt.Rectangle a)
	{
		super.setBounds(a);
	    if (autoNumberOfCharacters)
	        adjustCharacterLength(a.width);
	}

	/**
	 * Overridden method to adjust the number of characters displayed when
	 * the text field is resized.
	 * 
	 * @param a width
	 * @param b height
	 */
	public void setSize(int a, int b)
	{
		super.setSize(a,b);
		if (autoNumberOfCharacters)
	        adjustCharacterLength(a);
	}

	/**
	 * Overridden method to adjust the number of characters displayed when
	 * the text field is resized.
	 * 
	 * @param a Dimension used inresizing
	 */
	public void setSize(java.awt.Dimension a)
	{
		super.setSize(a);
		if (autoNumberOfCharacters)
	        adjustCharacterLength(a.width);
	}

	/**
	 * Overrides the setText method of JTextField to parse an arithmetic 
	 * expression.
	 * 
	 * @param s The text to be parsed
	 */
	public void setText(java.lang.String s)
	{
		String answer = s;
	    if ( !s.equals(""))
	    {
	        answer = "";
	        
	        try {
	            mathValue = MathUtility.parseDouble(s);
	            answer = MathUtility.displayFormat(mathValue, maxNumberOfCharacters);
	        }catch (MathSyntaxError error)
	        {
	            if (!errorMessage )
	            {
	                errorMessage = true;
	                Toolkit.getDefaultToolkit().beep();
	                System.err.println("Could not parse: " + s);
	                JOptionPane.showMessageDialog(null, 
	                        new JLabel("Could not parse: " + s),
	                        "Error", JOptionPane.ERROR_MESSAGE);
	                
	            }
	        }
	    }
	    super.setText(answer);
	    
	}
	
	/**
	 * Adjusts the number of characters to be displayed to accommodate the size
	 * 
	 * @param w The width in pixels
	 */
	public void adjustCharacterLength(int w)
	{
	    FontMetrics fm = getFontMetrics(getFont());
        int numeralWidth = fm.charWidth('9');
        maxNumberOfCharacters = (w-4)/numeralWidth;
    }
	
	/**
	 * Method to handle focus events. It tries to parse the expression when the
	 * focus is lost and selects the text when the focus is gained
	 * 
	 * @param e a focus event
	 */
	public void processFocusEvent(FocusEvent e)
	{
	    super.processFocusEvent(e);
	    int id = e.getID();
	    
	    switch (id)
	    {
	        case FocusEvent.FOCUS_LOST:
	            setText(getText());
	            break;
	        case FocusEvent.FOCUS_GAINED:
	            errorMessage = false;
	            selectAll();
	            break;
	    }
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
				getContentPane().add(new MathTextField());
			}
		}

		new DriverFrame().show();
	}


	/**
	 * The maximum number of characters (not counting the decimal point) to
	 * be displayed
	 */
	protected int maxNumberOfCharacters = 8;

	/**
	 * Determines whether maxNumberOfCharacters should be based on the size of 
	 * the text field
	 */
	protected boolean autoNumberOfCharacters = true;
	
	/**
	 * Returns the mathematical value corresponding to the String displayed in 
	 * the text field
	 * 
	 * @return The value
	 */
	public double getMathValue()
    {
        setText(getText());
	    return mathValue;
    }
    
    /**
     * Sets the mathematical value corresponding to be displayed in 
     * the text field
     * 
     * @param value The value to be displayed
     */
    public void setMathValue(double value)
    {
        mathValue = value;
        setText(Double.toString(value));
    }


	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == MathTextField.this)
				MathTextField_actionPerformed(event);
		}
	}

	void MathTextField_actionPerformed(java.awt.event.ActionEvent event)
	{
		setText(getText());
	}
}