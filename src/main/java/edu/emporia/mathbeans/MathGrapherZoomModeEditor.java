package EDU.emporia.mathbeans;

import java.beans.*;

public class MathGrapherZoomModeEditor extends PropertyEditorSupport
{
	// The following function is a placeholder for control initialization.
	// You should call this function from a constructor or initialization function.
	public void vcInit() {
		//{{INIT_CONTROLS
		//}}
	}
	
	public MathGrapherZoomModeEditor()
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
        if (s.equals("Off")) setValue(new Integer(MathGrapher.ZOOMOFF));
        else if (s.equals("Show Point")) setValue(new Integer(MathGrapher.SHOWPOINT));
        else if (s.equals("Zoom In")) setValue(new Integer(MathGrapher.ZOOMIN));
        else if (s.equals("Zoom Out")) setValue(new Integer(MathGrapher.ZOOMOUT));
        else if (s.equals("Zoom Box")) setValue(new Integer(MathGrapher.ZOOMBOX));
        else throw new IllegalArgumentException(s);
    }
    
    public String getAsText()
    {
        
        int mode = ((Integer)getValue()).intValue();
        switch ( mode)
        {
            case (MathGrapher.ZOOMOFF): return "Off";
            case (MathGrapher.SHOWPOINT): return "Show Point";
            case (MathGrapher.ZOOMIN): return "Zoom In";
            case (MathGrapher.ZOOMOUT): return "Zoom Out";
            case (MathGrapher.ZOOMBOX): return "Zoom Box";
            default: return "Illegal Value";
        }
    }
    
    
    public String getJavaInitializationString()
    {
        switch ( ((Integer)getValue()).intValue())
        {
            case (MathGrapher.ZOOMOFF): return "EDU.emporia.mathbeans.MathGrapher.ZOOMOFF";
            case (MathGrapher.SHOWPOINT): return "EDU.emporia.mathbeans.MathGrapher.SHOWPOINT";
            case (MathGrapher.ZOOMIN): return "EDU.emporia.mathbeans.MathGrapher.ZOOMIN";
            case (MathGrapher.ZOOMOUT): return "EDU.emporia.mathbeans.MathGrapher.ZOOMOUT";
            case (MathGrapher.ZOOMBOX): return "EDU.emporia.mathbeans.MathGrapher.ZOOMBOX";
            default: return "";
        }
        
    }
	//{{DECLARE_CONTROLS
	//}}
}
         