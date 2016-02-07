package EDU.emporia.mathbeans;
import java.awt.*;
import javax.swing.*;
import EDU.emporia.mathtools.*;
import javax.swing.table.*;
import java.util.Vector;


/**
 * MathTable creates a table designed to allow the user to create a list of 2-dimensional
 * points.
 * 
 * @author Joe Yanik
 * @version 1.0
 * @since 7/3/00
 */
public class MathTable extends javax.swing.JScrollPane
{

    /**
     * The table that will be displayed in the JScrollPane
     */
    JTable table;

    /**
     * The TableModel that will be in charge of managing the data
     */
    MathTableModel mathModel;
    /**
     * The x-values of the points
     */

    /**
     * The y-values of the points
     */
    /**
     * The xValues of the points
     */
    /**
     * The points corresponding to the data
     */
    Vector dataPoints = new Vector(), xValue = new Vector(), yValue = new Vector();

    /**
     * The columnModel that will hold the y-values
     */
    /**
     * The first columnModel which will hold the x-values
     */
    TableColumn firstColumn, secondColumn;

    /**
     * The cell editor that will use the MathTextField to do the editing
     */
    DefaultCellEditor editor;

    /**
     * The cell renderer that will use a MathTextField to be in charge of the 
     * rendering
     */
    MathRenderer renderer;

    /**
     * The MathTextField that will be used by the editor
     */
    MathTextField mathField;

    /**
     * The width of the column
     */
    int columnWidth = 10;

	public MathTable()
	{
	    super();
	    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    	    
	    mathModel = new MathTableModel();
        table = new JTable(mathModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        firstColumn = table.getColumn("x");
        secondColumn = table.getColumn("y");
        
        renderer = new MathRenderer();
        renderer.setAutoNumberOfCharacters(false);
        table.setDefaultRenderer(String.class, renderer);
        
        mathField = new MathTextField();
        mathField.setAutoNumberOfCharacters(false);
        editor = new DefaultCellEditor(mathField);
        editor.setClickCountToStart(1);
        table.setDefaultEditor(String.class, editor);
        
        table.setAutoCreateColumnsFromModel(false);
        
        table.setPreferredScrollableViewportSize(new Dimension(180,200));
        
        setViewportView(table);
	    
		//{{INIT_CONTROLS
		setSize(180,200);
		//}}
	}

	//{{DECLARE_CONTROLS
	//}}

	/**
	 * Sets the label that will be displayed at the top of the first column
	 * 
	 * @param xLabel The label to be displayed
	 */
	public void setXLabel(java.lang.String xLabel)
	{
	    this.xLabel = xLabel;
	    firstColumn.setHeaderValue(xLabel);
	    mathModel.fireTableCellUpdated(-1, 0);
	}

	/**
	 * Gets the label that is displayed at the top of the first column
	 */
	public java.lang.String getXLabel()
	{
		return this.xLabel;
	}

	/**
	 * Sets the label that will appear at the top of the second column.
	 * 
	 * @param yLabel The label to be displayed
	 */
	public void setYLabel(java.lang.String yLabel)
	{
	    this.yLabel = yLabel;
	    secondColumn.setHeaderValue(yLabel);
	    mathModel.fireTableCellUpdated(-1,1);
	}
	/**
	 * Gets the label to be displayed at the top of the second column.
	 */

	public java.lang.String getYLabel()
	{
		return this.yLabel;
	}
	
	/**
	 * MathTableModel is an inner class which contains the Table Model for the MathTable
	 * 
	 * @author Joe Yanik
	 * @version 1.0
	 * @since 7/3/00
	 */
	//inner class for the Table Model
	class MathTableModel extends AbstractTableModel
    {

        /**
         * The last row that can be edited
         */
        /**
         * The number of columns displayed. (Will always be 2)
         */
        /**
         * The number of rows displayed
         */
        protected int rows, columns, lastEditableRow;

        /**
         * Keeps track of whether the y part of the last editable row has been edited
         */
        /**
         * Keeps track of whether the x part of the last editable row has been edited
         */
        protected boolean xEdited = false, yEdited = false;
    
        MathTableModel()
        {
            rows = 1;
            columns = 2;
            lastEditableRow = 0;
        }
        
        /**
         * Determines whether or not the user can edit the cell
         * 
         * @param row The row of the cell
         * @param col The column of the cell
         * @return true if the cell can be edited. false otherwise
         */
        public boolean isCellEditable( int row, int col)
        {
            if ( row <= lastEditableRow ) 
                return true;
            else
                return false;
        }
        
        
        /**
         * All columns will be of the String class
         * 
         * @param c
         */
        public Class getColumnClass(int c)
        {
            return String.class;
        }
        
        /**
         * This will always return 2 since this table is designed for 2-dimensional points
         */
        public int getColumnCount()
        {
            return columns;
        }
        
        /**
         * The number of rows will grow as data is entered in
         * 
         * @return The number of rows
         */
        public int getRowCount()
        {
            return rows;
        }
        
        /**
         * This will be determined by the values of xLabel and yLabel
         * 
         * @param col
         * @return xLabel for column 0 and yLabel for column 1
         */
        public String getColumnName( int col)
        {
            if (col == 0 ) 
                return xLabel;
            else
                return yLabel;
        }
        
        /**
         * Called by the editor to set the value of the cell
         * 
         * @param value The value to be used (in the form of a String)
         * @param row The row of the cell
         * @param col The column of the cell
         */
        public void setValueAt(Object value, int row, int col)
        {
            if ( (value != null) && !(((String)value).equals(""))) 
            {
                try{
                    
                    double doubleValue = MathUtility.parseDouble((String)value);
                
                    if ( (row == lastEditableRow) )
                    {
                        if ( col == 0 ) 
                        {
                            if ( xEdited ) 
                                xValue.setElementAt( new Double(doubleValue), row );
                            else
                                xValue.addElement(new Double(doubleValue));
                            xEdited = true;
                        }
                        else 
                        {
                            if ( yEdited )
                                yValue.setElementAt( new Double(doubleValue), row);
                            else
                                yValue.addElement(new Double(doubleValue));
                            yEdited = true;
                        }
                
                        if ( xEdited && yEdited )
                        {
                            lastEditableRow++;
                            xEdited = yEdited = false;
                            dataPoints.addElement(new Point2D.Double(((Double)(xValue.elementAt(row))).doubleValue(),
                                    ((Double)(yValue.elementAt(row))).doubleValue()));
                        }
                        if ( row == (rows - 1))
                            rows++;
                    
                    }
                    else
                    {
                        Point2D.Double p = (Point2D.Double)(dataPoints.elementAt(row));
                        if ( col == 0 )
                        {
                            xValue.setElementAt(new Double(doubleValue),row);
                            p.setLocation(doubleValue, p.getY());
                        }
                        else 
                        {
                            yValue.setElementAt(new Double(doubleValue),row);
                            p.setLocation(p.getX(),doubleValue);
                        }
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
         * Returns the value in the cell in String form
         * 
         * @param row
         * @param col
         */
        public Object getValueAt(int row, int col)
        {
            if ( col == 0)
            {
                if ( row < xValue.size())
                    return xValue.elementAt(row).toString();
                else return "";
            }
            else
            {
                if ( row < yValue.size() )
                    return yValue.elementAt(row).toString();
                else return "";
            }
        }
        
        /**
         * This will be called when a point is added or removed externally.
         * 
         * @param j The number of points added (if positive) or removed (if negative)
         */
        public void dataChanged( int j)
        {
            lastEditableRow += j;
            rows = lastEditableRow + 1;
            fireTableDataChanged();
        }
        
       /**
        * Resets the table to its original state. This will be called when the table
        * is cleared.
        */
       public void resetTable()
        {
            rows = 1;
            lastEditableRow = 0;
            xEdited = false;
            yEdited = false;
            fireTableDataChanged();
        }
            
        
    }//end of MathTableModel inner class
    
    /**
     * Sets up the table so that the columns will fit properly.
     */
    public void adjustTable()
    {
        FontMetrics fm = getFontMetrics(table.getFont());
        int charNumber = (columnWidth -12)/fm.charWidth('9');
        renderer.setMaxNumberOfCharacters(charNumber);
        mathField.setMaxNumberOfCharacters(charNumber);
        firstColumn.setPreferredWidth(columnWidth);
        secondColumn.setPreferredWidth(columnWidth);
    }
    
    /**
     * Returns the data entered into the table in the form of points
     * 
     * @return An array of Point2D.Double objects corresponding to the data that has been 
     * entered in.
     */
    public Point2D.Double[] getPoints()
    {
        editor.stopCellEditing();
        int arraySize = dataPoints.size();
        Point2D.Double[] pointsArray = new Point2D.Double[arraySize];
        
        for ( int j=0 ; j < arraySize ; j++ )
        {
            pointsArray[j] = (Point2D.Double)(dataPoints.elementAt(j));
        }
        return pointsArray;
    }
    
    /**
     * Removes the point. (Uses the "equals" method to determine the point that matches)
     * 
     * @param p The point to be removed
     */
    public void removePoint( Point2D.Double p)
    {
        if ( dataPoints.contains(p))
        {
            editor.stopCellEditing();
            dataPoints.removeElement(p);
            xValue.removeElement(new Double(p.getX()));
            yValue.removeElement(new Double(p.getY()));
            mathModel.dataChanged(-1);
        }
    }
    
    /**
     * Removes the point with indicated coordinates from the table (if it is there)
     * 
     * @param x The x-coordinate of the point to be removed
     * @param y The y-coorindate of the point to be removed.
     */
    public void removePoint( double x, double y)
    {
        removePoint(new Point2D.Double(x,y));
    }
    
    /**
     * Clears out all data from the table.
     */
    public void clearTable ()
    {
        editor.stopCellEditing();
        dataPoints.removeAllElements();
        xValue.removeAllElements();
        yValue.removeAllElements();
        mathModel.resetTable();
    }
    
    /**
     * Adds a point to the table
     * 
     * @param p The point to be added
     */
    public void addPoint( Point2D.Double p)
    {
        editor.stopCellEditing();
        int dataPointsSize = dataPoints.size();
        
        dataPoints.addElement(p);
        
        if ( xValue.size() > dataPointsSize )
            xValue.insertElementAt(new Double(p.getX()), dataPointsSize);
        else
            xValue.addElement(new Double(p.getX()));
            
        if ( yValue.size() > dataPointsSize )
            yValue.insertElementAt(new Double(p.getY()),dataPointsSize);
        else
            yValue.addElement(new Double(p.getY()));
        
        mathModel.dataChanged(1);
    }
    
    /**
     * Adds an array of points to the table
     * 
     * @param pointArray The array to be added
     */
    public void addPoints( Point2D.Double[] pointArray)
    {
        for ( int i=0 ; i<pointArray.length ; i++ )
        {
            addPoint( pointArray[i]);
        }
    }
        

	/**
	 * Overridden to adjust the column size whenever it is called
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x,y,width,height);
        columnWidth = width/2-10;
        adjustTable();
	}
	/**
	 * Overridded to adjust the column size when it is called
	 * 
	 * @param a
	 */

	public void setBounds(java.awt.Rectangle a)
	{
		super.setBounds(a);
        columnWidth = (a.width)/2-10;
        adjustTable();
	}

	/**
	 * Overridden to adjust the column size when it is called
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
        columnWidth = width/2-10;
        adjustTable();
	}

	/**
	 * Overridden to adjust the column size when it is called
	 * 
	 * @param a
	 */
	public void setSize(java.awt.Dimension a)
	{
		setSize(a.width, a.height);
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
				this.setSize(300,400);
				getContentPane().add(new MathTable());
			}
		}

		new DriverFrame().show();
	}


	/**
	 * The label that will be displayed at the top of the first column
	 */
	protected java.lang.String xLabel = "x";

	/**
	 * The label that will be displayed at the top of the second column
	 */
	protected java.lang.String yLabel = "y";

}

