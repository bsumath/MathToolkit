package EDU.emporia.mathbeans;
import java.awt.*;
import javax.swing.*;
import EDU.emporia.mathtools.*;
import javax.swing.table.*;
import java.util.Vector;
import javax.swing.border.*;



/**
 * A table to display the values of one or more functions at selected points.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/5/00
 */
public class FunctionTable extends javax.swing.JScrollPane
{

    /**
     * The table that is put on the ScrollPane
     */
    protected JTable table;

    /**
     * The table column model
     */
    protected TableColumnModel tcm;

    /**
     * The header for the table
     */
    protected JTableHeader header;

    /**
     * The model used to manipulate the data for the table
     */
    protected FunctionTableModel mathModel;

    /**
     * The vector that will hold the functions to be represented on the table.
     */
    /**
     * The vector used to hold the x-values that will be displayed when autoXValues if false.
     */
    protected Vector xValue = new Vector(), function = new Vector();

    /**
     * The first column of the table
     */
    protected TableColumn firstColumn;

    /**
     * The cell editor for the table (based on MathTextField).
     */
    protected DefaultCellEditor editor;

    /**
     * The cell renderer for the table (based on MathTextField).
     */
    protected MathRenderer renderer;

    /**
     * The MathTextField object used to construct the cell editor
     */
    protected MathTextField mathField;

    /**
     * The number of columns for the table
     */
    int columns = 2;

    /**
     * Keeps track of whether or not a graph has yet been added to the table
     */
    boolean graphAdded = false;

    /**
     * The title to be displayed at the top of the table
     */
    protected String title = "";
    
    /**
     * The titled border for the table
     */
    protected javax.swing.border.TitledBorder tableBorder;

    /**
     * The index of the column that will be used to determine the title to be displayed
     */
    protected int titledIndex = 1;

	public FunctionTable()
	{
	    super();
	    
		
		
       
        Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
        
        tableBorder = new TitledBorder(loweredBevelBorder,
                                            title, 
                                            TitledBorder.LEFT, 
                                            TitledBorder.ABOVE_TOP, 
                                            new Font("Serif", Font.PLAIN, 10),
                                            Color.black);
        setBorder(tableBorder);
        mathModel = new FunctionTableModel();
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
       
        table.setPreferredScrollableViewportSize(new Dimension(180, 200));
        
        
        setViewportView(table);
        
        
        function.addElement(new MathFunction());
        
        //{{INIT_CONTROLS
		setSize(180,200);
		//}}
        
        //{{REGISTER_LISTENERS
		SymMouse aSymMouse = new SymMouse();
		header.addMouseListener(aSymMouse);
		//}}
	}

	//{{DECLARE_CONTROLS
	//}}

	/**
	 * Sets the label to be displayed at the top of the first column
	 * 
	 * @param xLabel The label to be displayed.
	 */
	public void setXLabel(java.lang.String xLabel)
	{
			this.xLabel = xLabel;
			mathModel.fireTableCellUpdated(-1, 0);
	}

	/**
	 * Gets the label to be displayed at the top of the first columns
	 * 
	 * @return The label to be displayed
	 */
	public java.lang.String getXLabel()
	{
		return this.xLabel;
	}

	/**
	 * Sets the label to be displayed at the top of the y-columns. An index will be added
	 * to the label
	 * 
	 * @param yLabel the label to be used.
	 */
	public void setYLabel(java.lang.String yLabel)
	{
			this.yLabel = yLabel;
			mathModel.fireTableStructureChanged();
	}

	/**
	 * Gets the label to be displayed at the top of the y-columns. An index will be added to 
	 * the label at the top of each one
	 * 
	 * @return The label to be displayed
	 */
	public java.lang.String getYLabel()
	{
		return this.yLabel;
	}

	/**
	 * Sets the starting point to be used to determine the x-values when autoXValues is true.
	 * 
	 * @param xMin Sets the starting point to be used to determine the x-values when autoXValues is true.
	 */
	public void setXMin(double xMin)
	{
			this.xMin = xMin;
			mathModel.fireTableDataChanged();
	}

	/**
	 * Gets the starting point to be used to determine the x-values when autoXValues is true.
	 * 
	 * @return The starting point to be used to determine the x-values when autoXValues is true.
	 */
	public double getXMin()
	{
		return this.xMin;
	}

	/**
	 * Sets the increment to be used to be determine the list of x-values to be used 
	 * when autoXValues is true
	 * 
	 * @param xDelta The distance between subsequent x-values when autoXValues is true
	 */
	public void setXDelta(double xDelta)
	{
	    if ( xDelta > 0 )
	    {
            this.xDelta = xDelta;
	        mathModel.fireTableDataChanged();
	    }
	}

	/**
	 * Gets the increment to be used to be determine the list of x-values to be used 
	 * when autoXValues is true
	 * 
	 * @return The increment to be used to be determine the list of x-values to be used 
	 * when autoXValues is true
	 */
	public double getXDelta()
	{
		return this.xDelta;
	}

	/**
	 * Determines whether or not the table will be automatically generated or the user will
	 * be permitted to enter in x-values.
	 * 
	 * @param autoXValue When true the table will be automatically generated.
	 */
	public void setAutoXValue(boolean autoXValue)
	{
		this.autoXValue = autoXValue;
		mathModel.fireTableDataChanged();
        mathModel.fireTableStructureChanged();
	}

	/**
	 * Returns true if the table is automatically generated-false otherwise
	 * 
	 * @return The value of autoXValue
	 */
	public boolean isAutoXValue()
	{
		return this.autoXValue;
	}

	/**
	 * Sets the f property of the table which will display the function values for f on the 
	 * table
	 * 
	 * @param f The function to be represented
	 */
	public void setF(EDU.emporia.mathtools.Graphable f)
	{
		this.f = f;
		addGraph(f);
	}

	/**
	 * Gets the current value of the f property
	 * 
	 * @return f
	 */
	public EDU.emporia.mathtools.Graphable getF()
	{
		return this.f;
	}

	/**
	 * Sets the g property of the table which will display the function values for g on the 
	 * table
	 * 
	 * @param g The function to be represented
	 */
	public void setG(EDU.emporia.mathtools.Graphable g)
	{
		this.g = g;
		addGraph(g);
	}

	/**
	 * Gets the current value of the f property
	 * 
	 * @return g
	 */
	public EDU.emporia.mathtools.Graphable getG()
	{
		return this.g;
	}

	/**
	 * Overrides the setBounds method to adjust the editor and renderer in case the column
	 * width should change
	 * 
	 * @param a The x-coordinate of the table
	 * @param b The y-coordinate where the table is placed
	 * @param c The width of the table
	 * @param d The height of the table
	 */
	public void setBounds(int a, int b, int c, int d)
	{
		super.setBounds(a,b,c,d);
        adjustTable();
	}

	/**
	 * Overrides the setSize method to adjust the editor and renderer in case the column
	 * width should change
	 * 
	 * @param a The width of the table
	 * @param b The height of the table
	 */
	public void setSize(int a, int b)
	{
		super.setSize(a, b);
        adjustTable();
	}
	
	class FunctionTableModel extends AbstractTableModel
    {

        /**
         * The last row that can be edited
         */
        /**
         * The number of rows displayed
         */
        protected int rows, lastEditableRow;
    
        FunctionTableModel()
        {
            rows = 50;
            columns = 2;
            lastEditableRow = 0;
        }
        
        /**
         * Determines whether or not the cell is editable. Only the first column will be 
         * editable. The values of the other columns will depend on the function.
         * 
         * @param row The row of the cell
         * @param col The column of the cell
         * @return True if the cell is editable--false otherwise
         */
        public boolean isCellEditable( int row, int col)
        {
            if ((!autoXValue) && (col == 0) && (row <= lastEditableRow) ) 
                return true;
            else
                return false;
        }
        
        
        /**
         * Returns the class used to represent the column data.
         * 
         * @param c The column
         * @return String
         */
        public Class getColumnClass(int c)
        {
            return String.class;
        }
        
        /**
         * Determines the number of columns.
         * 
         * @return The number of columns
         */
        public int getColumnCount()
        {
            return columns;
        }
        
        /**
         * Determines the number of rows
         * 
         * @return The number of rows.
         */
        public int getRowCount()
        {
            return rows;
        }
        
        /**
         * Returns the name to be displayed at the top of the column. The value of xLabel will
         * be at the top of the first column (at index 0). All others will be of the form 
         * yLabel + i.
         * 
         * @param col The index of the column
         * @return The column name
         */
        public String getColumnName( int col)
        {
            if (col == 0 ) 
                return xLabel;
            else if ( col == titledIndex)
                return "--" + yLabel + col + "--";
            else
                return yLabel + col;
        }
        
        /**
         * Called after the cell has been edited to set the value of the cell
         * 
         * @param value The object representing the value for the cell
         * @param row The row being edited
         * @param col The column being edited
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
                            xValue.addElement(new Double(doubleValue));
                            lastEditableRow++;
                        }
                        else 
                            xValue.setElementAt(new Double(doubleValue),row);
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
         * Gets the current value represented by the cell
         * 
         * @param row The row of the cell
         * @param col The column of the cell
         * @return The String version of the value of the cell
         */
        public Object getValueAt(int row, int col)
        {
            if (!autoXValue)
            {
                if ( col == 0)
                {
                    if ( row < xValue.size())
                        return xValue.elementAt(row).toString();
                    else return "";
                }
                else
                {
                    if ( row < xValue.size() )
                    {
                        double x = ((Double)(xValue.elementAt(row))).doubleValue();
                        try{
                            double y = ((Graphable)(function.elementAt(col-1))).getPoint(x).getY();
                            return Double.toString(y);
                        }
                        catch (Graphable_error e){}
                    }
                    return "";
                }
            }
            else
            {
                double xTemp = xMin + row*xDelta;
                if ( row > rows - 5)
                {
                    rows += 50;
                    fireTableStructureChanged();
                }
                if ( col == 0)
                    return Double.toString(xTemp);
                else if (col < columns)
                {
                    try{
                        double yTemp = ((Graphable)(function.elementAt(col-1))).getPoint(xTemp).getY();
                        return Double.toString(yTemp);
                    }
                    catch (Graphable_error e){}
                
                }
                return "";
            }  
        }
        
        /**
         * This will be called whenever the data has been changed in the table because of the
         * removal or addition of a point
         * 
         * @param j If i>0 it represents the number of points added. If i<0 it represents the number removed.
         */
        public void dataChanged( int j)
        {
            lastEditableRow += j;
            if ( lastEditableRow >= rows - 2)
                rows += 5;
            fireTableDataChanged();
        }
        
        /**
         * Resets the table to the initial state.
         */
        public void resetTable()
        {
            rows = 50;
            lastEditableRow = 0;
            fireTableDataChanged();
        }
        
    }//end of FunctionTableModel inner class
    
    
	/**
	 * Adds a graph to be represented by the table
	 * 
	 * @param f A graphable object representing a function to be displayed
	 */
	public void addGraph(Graphable f)
	{
	    if ( !graphAdded)
	    {
	        function.setElementAt(f, 0);
	        graphAdded = true;
	    }
	    else
	    {
	        function.addElement(f);
	        columns++;
	    }
	    setTitledIndex(columns-1);
	}
	
	/**
	 * Adds a point to the table. If the point is already there it won't be added.
	 * (Only works if autoXValue is false.)
	 * 
	 * @param x The x-coordinate of the point
	 */
	public void addPoint( double x )
	{
	    if ( !autoXValue )
	    {
	        Double xTemp = new Double(x);
	        editor.stopCellEditing();
	        if ( !xValue.contains(xTemp) )
	        {
	            xValue.addElement(new Double(x));
	            mathModel.dataChanged(1);
	        }
	    }	    
	}
	
	/**
	 * Adds an array of points to the table. Any points already there will not be added.
	 * (Only works if autoXValue is false.)
	 * 
	 * @param xArray The array of points to be added
	 */
	public void addPoints( double[] xArray)
    {
        if ( !autoXValue )
        {
            editor.stopCellEditing();
            int j = 0;
            for ( int i=0 ; i<xArray.length ; i++ )
            {
                Double xTemp = new Double(xArray[i]);
                if ( !xValue.contains(xTemp) )
                {
                    xValue.addElement( xTemp );
                    j++;
                }
            }
            mathModel.dataChanged(j);
        }
    }
    /**
     * Removes a point from the table. (Only works if autoXValue is false.)
     * 
     * @param x The x-coordinate of the point to be removed.
     */
    
    public void removePoint( double x)
    {
        if ( !autoXValue )
        {
            editor.stopCellEditing();
            Double xTemp = new Double(x);
            if ( xValue.contains(xTemp))
            {
                xValue.removeElement(xTemp);
                mathModel.dataChanged(-1);
            }
        }
    }
    /**
     * Clears the table of all data. (Only works if autoXValue is false.)
     */
    
    public void clearTable ()
    {
        if ( !autoXValue )
        {
            editor.stopCellEditing();
            xValue.removeAllElements();
            mathModel.resetTable();
        }
    }
	/**
	 * Adjusts the editor and renderer to reflect the new column width.
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
     * Updates the table to reflect any changes made in the function that are represented/
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
				FunctionTable_mousePressed(event);
		}
	}

	void FunctionTable_mousePressed(java.awt.event.MouseEvent event)
	{
		TableColumnModel colModel = table.getColumnModel();
		int columnModelIndex = colModel.getColumnIndexAtX(event.getX());
		setTitledIndex(columnModelIndex);
		
	}
	
	/**
	 * Sets the column index that will determine the title to be displayed
	 * 
	 * @param j The index of the column whose title will be displayed
	 */
	public void setTitledIndex(int j)
	{
	    if ( (j > 0) && ( j <= function.size()))
	    {
	        titledIndex = j;
	        tableBorder.setTitle(((Graphable)(function.elementAt(j-1))).getTitle());
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
				getContentPane().add(new FunctionTable());
			}
		}

		new DriverFrame().show();
	}


	/**
	 * The label displayed at the top of the first column (the "x" column).
	 */
	protected java.lang.String xLabel = "x";

	/**
	 * Determines whether or not the x-values will be automatically generated or will be 
	 * based on user-input.
	 */
	protected boolean autoXValue = false;

	/**
	 * The label to be used to to construct the labels for the y-columns
	 */
	protected java.lang.String yLabel = "y";

	/**
	 * A Graphable object that can be added to the table through the property list
	 */
	protected EDU.emporia.mathtools.Graphable g;

	/**
	 * A Graphable object that can be added to the table through the property list
	 */
	protected EDU.emporia.mathtools.Graphable f;

	/**
	 * The minimum x-value displayed when autoXValues is true
	 */
	protected double xMin = 0;

	/**
	 * The increment between x-values when autoXValue is true
	 */
	protected double xDelta = 0.1;

}