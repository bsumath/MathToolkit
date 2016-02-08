package edu.emporia.mathbeans;
import java.beans.*;

public class MathGridZoomFactorEditor extends PropertyEditorSupport
{
	
	public MathGridZoomFactorEditor()
	{
	    super();
	}
	
    public String[] getTags()
    {
        return new String[]{"1x","2x", "5x", "10x"};
    }
    
    public void setAsText(String s)
    {
        if (s.equals("1x")) setValue(new Integer(1));
        else if (s.equals("2x")) setValue(new Integer(2));
        else if (s.equals("5x")) setValue(new Integer(5));
        else if (s.equals("10x")) setValue(new Integer(10));
        else throw new IllegalArgumentException(s);
    }
    
    public String getAsText()
    {
        int mode = ((Integer)getValue()).intValue();
        switch ( mode)
        {
            case (1): return "1x";
            case (2): return "2x";
            case (5): return "5x";
            case (10): return "10x";
            default: return "Illegal Value";
        }
    }
    
    public String getJavaInitializationString()
    {
        switch ( ((Integer)getValue()).intValue())
        {
            case (1): return "1";
            case (2): return "2";
            case (5): return "5";
            case (10): return "10";
            default: return "";
        }
        
    }
	//{{DECLARE_CONTROLS
	//}}
}
         