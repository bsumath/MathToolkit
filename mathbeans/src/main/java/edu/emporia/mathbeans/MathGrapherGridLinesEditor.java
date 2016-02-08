package edu.emporia.mathbeans;
import java.beans.*;

public class MathGrapherGridLinesEditor extends PropertyEditorSupport
{
	public MathGrapherGridLinesEditor()
	{
	    super();
	}
	
    public String[] getTags()
    {
        return new String[]{"Off", "Coarse", "Normal", "Fine"};
    }
    
    public void setAsText(String s)
    {
        if (s.equals("Off")) setValue(new Integer(MathGrapher.GRIDOFF));
        else if (s.equals("Coarse")) setValue(new Integer(MathGrapher.GRIDCOARSE));
        else if (s.equals("Normal")) setValue(new Integer(MathGrapher.GRIDNORMAL));
        else if (s.equals("Fine")) setValue(new Integer(MathGrapher.GRIDFINE));
        else throw new IllegalArgumentException(s);
    }
    
    public String getAsText()
    {
        
        int mode = ((Integer)getValue()).intValue();
        switch ( mode)
        {
            case (MathGrapher.GRIDOFF): return "Off";
            case (MathGrapher.GRIDCOARSE): return "Coarse";
            case (MathGrapher.GRIDNORMAL): return "Normal";
            case (MathGrapher.GRIDFINE): return "Fine";
            default: return "Illegal Value";
        }
    }
    
    public String getJavaInitializationString()
    {
        switch ( ((Integer)getValue()).intValue())
        {
            case (MathGrapher.GRIDOFF): return "edu.emporia.mathbeans.MathGrapher.GRIDOFF";
            case (MathGrapher.GRIDCOARSE): return "edu.emporia.mathbeans.MathGrapher.GRIDCOARSE";
            case (MathGrapher.GRIDNORMAL): return "edu.emporia.mathbeans.MathGrapher.GRIDNORMAL";
            case (MathGrapher.GRIDFINE): return "edu.emporia.mathbeans.MathGrapher.GRIDFINE";
            default: return "";
        }
        
    }
	//{{DECLARE_CONTROLS
	//}}
}
         