package EDU.emporia.mathbeans;
import java.awt.*;
import javax.swing.*;
import EDU.emporia.mathtools.*;
import javax.swing.table.*;
import java.util.Vector;
import javax.swing.border.*;




/**
 * A table to display the values of one or more parametric curves at selected points
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/6/00
 */
public class ParametricTable extends javax.swing.JScrollPane
{

    /**
     * The JTable to be displayed on the JScrollPane
     */
    protected JTable table;

    /**
     * The table column model
     */
    protected TableColumnModel tcm;

    /**
     * The header for the table.
     */
    protected JTableHeader header;

    /**
     * The model to be used to manage the data
     */
    protected ParametricTableModel mathModel;

    
    /**
     * The vector of t-values to be used when autoTValue is faalse
     */
    protected Vector tValue = new Vector();
    
    /**
     * The vector of curves to be represented by the table
     */
    protected Vector curve = new Vector();
    
    /**
     * The Column Model for the first column
     */
    protected TableColumn firstColumn;

    /**
     * The editor to be used as a cell editor. (Based on MathTextField)
     */
    protected DefaultCellEditor editor;

    /**
     * The renderer to be used to render the cells. (Based on MathTextField)
     */
    protected MathRenderer renderer;

    /**
     * The MathTextField object used for the editor
     */
    protected MathTextField mathField;

    /**
     * The number of columns in the table
     */
    protected int columns = 3;
    
    
    /**
     * Keeps track of whether or not anything has been added to the table yet
     */
    protected boolean graphAdded = false;

    /**
     * The title to appear at the top of the table
     */
    protected String title = "";
    
    /**
     * The TitledBorder used for the table
     */
    protected javax.swing.border.TitledBorder tableBorder;

    /**
     * The index of the column used to determine the title
     */
    protected int titledIndex = 1;
    

	public ParametricTable()
	{
	    super();
	    
	    tableBorder = new TitledBorder(title);
        tableBorder.setTitleColor(Color.black);
        setBorder(BorderFactory.createCompoundBorder(tableBorder, getBorder()));
        
        mathModel = new ParametricTableModel();
        table = new JTable(mathModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        tcm = table.getColumnModel();
        
        firstColumn = tcm.getColumn(0);
         
        renderer = new MathRenderer();
        renderer.setAutoNumberOfCharacters(false);
        table.setDefaultRenderer(String.class, renderer);
        
        mathField = new MathTextField();
        mathField.setAutoNumberOfCharacters(false);
        editor = new DefaultCellEditor(mathField);
        editor.setClickCountToStart(1);
        table.setDefaultEditor(String.class, editor);
        header = table.getTableHeader();
        
        header.setReorderingAllowed(false);
       
        table.setPreferredScrollableViewportSize(new Dimension(260, 200));
        
        setViewportView(table);
        curve.addElement(new MathFunction());
        
		//{{INIT_CONTROLS
		setSize(260,200);
		//}}
		
		//{{REGISTER_LISTENERS
		SymMouse aSymMouse = new SymMouse();
		header.addMouseListener(aSymMouse);
		//}}
	}

	//{{DECLARE_CONTROLS
	//}}

	/**
	 * Sets the minimum value of t that will be used when autoTValue is true
	 * 
	 * @param tMin The minimum value of t that will be used when autoTValue is true
	 */
	public void setTMin(double tMin)
	{
			this.tMin = tMin;
			mathModel.fireTableDataChanged();
	}

	/**
	 * Gets the minimum value of t that will be used when autoTValue is true
	 * 
	 * @return The minimum value of t that will be used when autoTValue is true
	 */
	public double getTMin()
	{
		return this.tMin;
	}

	/**
	 * Sets the increment between subsequent t-values displayed when autoTValue is true.
	 * 
	 * @param tDelta The increment between subsequent t-values displayed when autoTValue is true.
	 */
	public void setTDelta(double tDelta)
	{
		if ( tDelta > 0 )
	    {
            this.tDelta = tDelta;
	        mathModel.fireTableDataChanged();
	    }
	}

	/**
	 * Gets the increment between subsequent t-values displayed when autoTValue is true.
	 * 
	 * @return The increment between subsequent t-values displayed when autoTValue is true.
	 */
	public double getTDelta()
	{
		return this.tDelta;
	}

	/**
	 * Sets autoTValue which determines whether or not the values used will be automatically
	 * generated or will be based on user input
	 * 
	 * @param autoTValue True if automatic, false otherwise
	 */
	public void setAutoTValue(boolean autoTValue)
	{
		this.autoTValue = autoTValue;
		mathModel.fireTableDataChanged();
        mathModel.fireTableStructureChanged();
	}

	/**
	 * Gets autoTValue which determines whether or not the values used will be automatically
	 * generated or will be based on user input
	 * 
	 * @return True if automatic, false otherwise
	 */
	public boolean isAutoTValue()
	{
		return this.autoTValue;
	}

	/**
	 * Sets the f property which will be a parametric curve to be represented on the table
	 * 
	 * @param f The curve to be represented
	 */
	public void setF(EDU.emporia.mathtools.Graphable f)
	{
		this.f = f;
		addGraph(f);
	}

	/**
	 * Gets the f property which will be a parametric curve to be represented on the table
	 * 
	 * @return The curve that is represented
	 */
	public EDU.emporia.mathtools.Graphable getF()
	{
		return this.f;
	}

	/**
	 * Gets the g property which will be a parametric curve to be represented on the table
	 * 
	 * @param g The curve to be represented
	 */
	public void setG(EDU.emporia.mathtools.Graphable g)
	{
		this.g = g;
		addGraph(g);
	}

	/**
	 * Gets the g property which will be a parametric curve to be represented on the table
	 * 
	 * @return The curve that is represented
	 */
	public EDU.emporia.mathtools.Graphable getG()
	{
		return this.g;
	}

	/**
	 * Sets the value of xLabel which will determine what will be displayed at the top of
	 * the x-columns
	 * 
	 * @param xLabel The String to be used to determine what will be displayed at the top of the 
	 * x-columns
	 */
	public void setXLabel(java.lang.String xLabel)
	{
	    this.xLabel = xLabel;
	    mathModel.fireTableStructureChanged();
	}

	/**
	 * Gets the value of xLabel which will determine what will be displayed at the top of
	 * the x-columns
	 * 
	 * @return The String to be used to determine what will be displayed at the top of the 
	 * x-columns
	 */
	public java.lang.String getXLabel()
	{
		return this.xLabel;
	}

	/**
	 * Sets the value of yLabel which will determine what will be displayed at the top of
	 * the y-columns
	 * 
	 * @param yLabel The String to be used to determine what will be displayed at the top of the 
	 * y-columns
	 */
	public void setYLabel(java.lang.String yLabel)
	{
		this.yLabel = yLabel;
		mathModel.fireTableStructureChanged();
	}

	/**
	 * Gets the value of yLabel which will determine what will be displayed at the top of
	 * the y-columns
	 * 
	 * @return The String to be used to determine what will be displayed at the top of the 
	 * y-columns
	 */
	public java.lang.String getYLabel()
	{
		return this.yLabel;
	}

	/**
	 * Sets the String that will be displayed at the top of the t-column
	 * 
	 * @param tLabel The String that will be displayed at the top of the t-column
	 */
	public void setTLabel(java.lang.String tLabel)
	{
	    this.tLabel = tLabel;
	    mathModel.fireTableCellUpdated(-1, 0);
	}

	/**
	 * Gets the String that will be displayed at the top of the t-column
	 * 
	 * @return The String that will be displayed at the top of the t-column
	 */
	public java.lang.String getTLabel()
	{
		return this.tLabel;
	}

	/**
	 * Overrides method to adjust table when it is resized
	 * 
	 * @param a x-coordinate
	 * @param b y-coordinate
	 * @param c width
	 * @param d height
	 */
	public void setBounds(int a, int b, int c, int d)
	{
		super.setBounds(a,b,c,d);
        adjustTable();
	}

	/**
	 * Overrides method to adjust table when it is resized
	 * 
	 * @param a width
	 * @param b height
	 */
	public void setSize(int a, int b)
	{
		super.setSize(a, b);
        adjustTable();
	}
	
	/**
	 * Inner class to manage the data for the table
	 * 
	 * @author Joe Yanik
	 * @version 1.0
	 * @since 7/6/00
	 */
	class ParametricTableModel extends AbstractTableModel
    {

        /**
         * The index of the last row that can be edited
         */
        /**
         * The number of rows displayed
         */
        protected int rows, lastEditableRow;
    
        ParametricTableModel()
        {
            rows = 50;
            lastEditableRow = 0;
        }
        /**
         * Determines whether or not the cell is editable. Only the first column will be 
         * editable and this will only be true if autoTValue is false.
         * 
         * @param row The row of the cell
         * @param col The column of the cell
         * @return true only if it is in the first column, autoTValue is false, and if the row is 
         * not larger than lastEditableRow
         */
        
        public boolean isCellEditable( int row, int col)
        {
            if ((!autoTValue) && (col == 0) && (row <= lastEditableRow) ) 
                return true;
            else
                return false;
        }
        
        
        /**
         * Describes the class of the data for the column model
         * 
         * @param c The column
         * @return String
         */
        public Class getColumnClass(int c)
        {
            return String.class;
        }
        
        /**
         * Gives the number of columns
         * 
         * @return The number of columns
         */
        public int getColumnCount()
        {
            return columns;
        }
        
        /**
         * Gives the number of rows
         * 
         * @return The number of rows
         */
        public int getRowCount()
        {
            return rows;
        }
        /**
         * The name to be displayed at the top of the column
         * 
         * @param col The index of the column
         * @return The value of tLabel for the first column (at 0 index) and a version of the value of 
         * xLabel or yLabel for the other columns
         */
        
        public String getColumnName( int col)
        {
            if (col == 0 ) 
                return tLabel;
            else
            {
                int j = (col + 1)/2;
                String tag = ( j-1 == titledIndex ? "--" : "");
                if ( col%2  == 0)
                    return tag + yLabel + j + tag;
                else
                    return tag + xLabel + j + tag;
            }
        }
        
        /**
         * Sets the value to be displayed in the cell
         * 
         * @param value The value as an object
         * @param row The row of the cell
         * @param col The column of the cell
         */
        public void setValueAt(Object value, int row, int col)
        {
            if ( (value != null) && !(((String)value).equals(""))) 
            {
                try{
                    
                    double doubleValue = MathUtility.parseDouble((String)value);
                
                    if ( col == 0 )
                    {
                        if ( row == lastEditableRow ) 
                        {
                            tValue.addElement(new Double(doubleValue));
                            lastEditableRow++;
                        }
                        else 
                            tValue.setElementAt(new Double(doubleValue),row);
                        if ( row >= (rows - 2))
                            rows+=5;
                       }
                    fireTableDataChanged();
                }
                catch (MathSyntaxError error)
                {
                    Toolkit.getDefaultToolkit().beep();
	                System.err.println("Could not parse in SetValueAt method: " + error.getStringToBeParsed());
	                JOptionPane.showMessageDialog(null, 
	                    new JLabel("Could not parse: " + error.getStringToBeParsed()),
	                    "Error", JOptionPane.ERROR_MESSAGE);
	            }
            } 
        }
        
        /**
         * Gets the value displayed in the cell
         * 
         * @param row The row of the cell
         * @param col The column of the cell
         * @return The value as an object
         */
        public Object getValueAt(int row, int col)
        {
            if (!autoTValue)
            {
                if ( col == 0)
                {
                    if ( row < tValue.size())
                        return tValue.elementAt(row).toString();
                    else return "";
                }
                else
                {
                    if ( (row < tValue.size()) && (col > 0) && (col < columns) )
                    {
                        double tTemp = ((Double)(tValue.elementAt(row))).doubleValue();
                        try{
                            Point2D.Double p = (Point2D.Double)((Graphable)(curve.elementAt((col-1)/2))).getPoint(tTemp);
                            if ( col % 2 == 1)
                                return Double.toString(p.getX());
                            else
                                return Double.toString(p.getY());
                        }
                        catch (Graphable_error e){}
                    }
                    return "";
                }
            }
            else
            {
                double tTemp = tMin + row*tDelta;
                if ( row > rows - 5)
                {
                    rows += 50;
                    fireTableStructureChanged();
                }
                if ( col == 0)
                    return Double.toString(tTemp);
                else if (col < columns)
                {
                    Graphable tempCurve = (Graphable)(curve.elementAt((col-1)/2));
                    try{
                        Point2D.Double p = (Point2D.Double)(tempCurve.getPoint(tTemp));
                        if ( col % 2 == 1)
                            return Double.toString(p.getX());
                        else
                            return Double.toString(p.getY());
                    }
                    catch (Graphable_error e){}
                
                }
                return "";
            }  
        }
        
        /**
         * A method to adjust the table when a row is added or removed
         * 
         * @param j If positive the number of rows added. If negative the number removed
         */
        public void dataChanged( int j)
        {
            lastEditableRow += j;
            if ( lastEditableRow >= rows - 2)
                rows += 5;
            fireTableDataChanged();
        }
        
        /**
         * Resets the parameters of the table back to the initial position
         */
        public void resetTable()
        {
            rows = 50;
            lastEditableRow = 0;
            fireTableDataChanged();
        }
        
    }//end of ParametricTableModel inner class
    
    /**
     * Adds a graph to be represented on the table
     * 
     * @param f A parametric curve
     */
    public void addGraph(Graphable f)
	{
	    if ( !graphAdded)
	    {
	        curve.setElementAt(f, 0);
	        graphAdded = true;
	    }
	    else
	    {
	        curve.addElement(f);
	        columns += 2;
	    }
	    setTitledIndex((columns-3)/2);
	}
	
	/**
	 * Adds a point to the table
	 * 
	 * @param t The t-value for the point to be added
	 */
	public void addPoint( double t )
	{
	    if ( !autoTValue )
	    {
	        Double tTemp = new Double(t);
	        editor.stopCellEditing();
	        if ( !tValue.contains(tTemp) )
	        {
	            tValue.addElement(new Double(t));
	            mathModel.dataChanged(1);
	        }
	    }	    
	}
	
	/**
	 * Adds an array of points to the table
	 * 
	 * @param tArray An array of t-values for the points
	 */
	public void addPoints( double[] tArray)
    {
        if ( !autoTValue )
        {
            editor.stopCellEditing();
            int j = 0;
            for ( int i=0 ; i<tArray.length ; i++ )
            {
                Double tTemp = new Double(tArray[i]);
                if ( !tValue.contains(tTemp) )
                {
                    tValue.addElement( tTemp );
                    j++;
                }
            }
            mathModel.dataChanged(j);
        }
    }
    
    /**
     * Removes a point from the table
     * 
     * @param t The t-value of the point to be removed
     */
    public void removePoint( double t)
    {
        if ( !autoTValue )
        {
            editor.stopCellEditing();
            Double tTemp = new Double(t);
            if ( tValue.contains(tTemp))
            {
                tValue.removeElement(tTemp);
                mathModel.dataChanged(-1);
            }
        }
    }
    
    /**
     * Clears the table of all data
     */
    public void clearTable ()
    {
        if ( !autoTValue )
        {
            editor.stopCellEditing();
            tValue.removeAllElements();
            mathModel.resetTable();
        }
    }
	
	/**
	 * Adjusts the parameters of the table when it is resized
	 */
	public void adjustTable()
    {
        FontMetrics fm = getFontMetrics(table.getFont());
        int width = firstColumn.getWidth();
        int charNumber = (width -12)/fm.charWidth('9');
        renderer.setMaxNumberOfCharacters(charNumber);
        mathField.setMaxNumberOfCharacters(charNumber);
    }
    
    /**
     * Updates the table to reflect changes made in the curves
     */
    public void updateTable()
    {
        mathModel.fireTableStructureChanged();
    }
    
   
    
    class SymMouse extends java.awt.event.MouseAdapter
	{
		public void mousePressed(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == header)
				ParametricTable_mousePressed(event);
		}
	}

	void ParametricTable_mousePressed(java.awt.event.MouseEvent event)
	{
		TableColumnModel colModel = table.getColumnModel();
		int columnModelIndex = colModel.getColumnIndexAtX(event.getX());
		setTitledIndex((columnModelIndex-1)/2);
		
	}
	
	/**
	 * Sets the index of the column to be used to determine the title for the table
	 * 
	 * @param j The index of the column to be used to determine the title
	 */
	public void setTitledIndex(int j)
	{
	    if ( (j >= 0) && ( j < curve.size()))
	    {
	        titledIndex = j;
	        tableBorder.setTitle(((Graphable)(curve.elementAt(j))).getTitle());
	        repaint();
	        mathModel.fireTableStructureChanged();
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
				getContentPane().add(new ParametricTable());
			}
		}

		new DriverFrame().show();
	}


	/**
	 * The label to appear at the top of the t-column
	 */
	protected java.lang.String tLabel = "t";

	/**
	 * Keeps track of whether the t-values should be generated automatically or be 
	 * based on user input
	 */
	protected boolean autoTValue = false;

	/**
	 * The minimum t-value represented on when autoTValue is true
	 */
	protected double tMin = 0;

	/**
	 * The String used to determine the label at the top of the x-columns
	 */
	protected java.lang.String xLabel = "x";

	/**
	 * The String used to determine the label at the top of the y-columns
	 */
	protected java.lang.String yLabel = "y";

	/**
	 * A property to appear on the Property List that will correspond to a curve
	 * to be added to the table.
	 */
	protected EDU.emporia.mathtools.Graphable g;

	/**
	 * A property to appear on the Property List that will correspond to a curve
	 * to be added to the table.
	 */
	protected EDU.emporia.mathtools.Graphable f;

	/**
	 * The increment between subsequent t-values to be used when autoTValue is true
	 */
	protected double tDelta = 0.1;

}