package edu.emporia.mathbeans;
import java.beans.*;

public class MathGridGridLinesEditor extends PropertyEditorSupport
{
	public MathGridGridLinesEditor()
	{
	    super();
	}
	
    public String[] getTags()
    {
        return new String[]{"Off", "Coarse", "Normal", "Fine"};
    }
    
    public void setAsText(String s)
    {
        if (s.equals("Off")) setValue(new Integer(MathGrid.GRIDOFF));
        else if (s.equals("Coarse")) setValue(new Integer(MathGrid.GRIDCOARSE));
        else if (s.equals("Normal")) setValue(new Integer(MathGrid.GRIDNORMAL));
        else if (s.equals("Fine")) setValue(new Integer(MathGrid.GRIDFINE));
        else throw new IllegalArgumentException(s);
    }
    
    public String getAsText()
    {
        
        int mode = ((Integer)getValue()).intValue();
        switch ( mode)
        {
            case (MathGrid.GRIDOFF): return "Off";
            case (MathGrid.GRIDCOARSE): return "Coarse";
            case (MathGrid.GRIDNORMAL): return "Normal";
            case (MathGrid.GRIDFINE): return "Fine";
            default: return "Illegal Value";
        }
    }
    
    public String getJavaInitializationString()
    {
        switch ( ((Integer)getValue()).intValue())
        {
            case (MathGrid.GRIDOFF): return "edu.emporia.mathbeans.MathGrid.GRIDOFF";
            case (MathGrid.GRIDCOARSE): return "edu.emporia.mathbeans.MathGrid.GRIDCOARSE";
            case (MathGrid.GRIDNORMAL): return "edu.emporia.mathbeans.MathGrid.GRIDNORMAL";
            case (MathGrid.GRIDFINE): return "edu.emporia.mathbeans.MathGrid.GRIDFINE";
            default: return "";
        }
        
    }
	//{{DECLARE_CONTROLS
	//}}
}
         