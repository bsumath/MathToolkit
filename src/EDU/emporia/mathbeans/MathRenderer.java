 package EDU.emporia.mathbeans;
 import javax.swing.table.*;
 import java.awt.*;
 import javax.swing.*;
 
 /**
  * Used as a cell renderer for the math tables.
  * 
  * @author Joe Yanik
  * @version 1.0
  * @since 7/6/00
  */
 public class MathRenderer extends MathTextField implements TableCellRenderer
 {
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column)
    {
        if ( isSelected && hasFocus )
            setBackground(table.getSelectionBackground());
        else
            setBackground(table.getBackground());

        setText(value.toString());
              
        return this;
    }
 }