package EDU.emporia.mathbeans;

import java.beans.*;

public class MathGridZoomModeEditor extends PropertyEditorSupport
{
	// The following function is a placeholder for control initialization.
	// You should call this function from a constructor or initialization function.
	public void vcInit() {
		//{{INIT_CONTROLS
		//}}
	}
	
	public MathGridZoomModeEditor()
	{
	    super();
	    vcInit();
	}
	
    public String[] getTags()
    {
        return new String[]{"Off","Show Point", "Zoom In", "Zoom Out", "Zoom Box"};
    }
    
    public void setAsText(String s)
    {
        if (s.equals("Off")) setValue(new Integer(MathGrid.ZOOMOFF));
        else if (s.equals("Show Point")) setValue(new Integer(MathGrid.SHOWPOINT));
        else if (s.equals("Zoom In")) setValue(new Integer(MathGrid.ZOOMIN));
        else if (s.equals("Zoom Out")) setValue(new Integer(MathGrid.ZOOMOUT));
        else if (s.equals("Zoom Box")) setValue(new Integer(MathGrid.ZOOMBOX));
        else throw new IllegalArgumentException(s);
    }
    
    public String getAsText()
    {
        
        int mode = ((Integer)getValue()).intValue();
        switch ( mode)
        {
            case (MathGrid.ZOOMOFF): return "Off";
            case (MathGrid.SHOWPOINT): return "Show Point";
            case (MathGrid.ZOOMIN): return "Zoom In";
            case (MathGrid.ZOOMOUT): return "Zoom Out";
            case (MathGrid.ZOOMBOX): return "Zoom Box";
            default: return "Illegal Value";
        }
    }
    
    
    public String getJavaInitializationString()
    {
        switch ( ((Integer)getValue()).intValue())
        {
            case (MathGrid.ZOOMOFF): return "EDU.emporia.mathbeans.MathGrid.ZOOMOFF";
            case (MathGrid.SHOWPOINT): return "EDU.emporia.mathbeans.MathGrid.SHOWPOINT";
            case (MathGrid.ZOOMIN): return "EDU.emporia.mathbeans.MathGrid.ZOOMIN";
            case (MathGrid.ZOOMOUT): return "EDU.emporia.mathbeans.MathGrid.ZOOMOUT";
            case (MathGrid.ZOOMBOX): return "EDU.emporia.mathbeans.MathGrid.ZOOMBOX";
            default: return "";
        }
        
    }
	//{{DECLARE_CONTROLS
	//}}
}
         