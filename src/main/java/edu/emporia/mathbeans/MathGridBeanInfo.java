package EDU.emporia.mathbeans;

import java.beans.*;
import java.beans.SimpleBeanInfo;
//import symantec.itools.beans.*;

public class MathGridBeanInfo extends java.beans.SimpleBeanInfo
{
	public MathGridBeanInfo()
	{
	}

	/**
	 * Gets a BeanInfo for the superclass of this bean.
	 * @return BeanInfo[] containing this bean's superclass BeanInfo
	 */
	public BeanInfo[] getAdditionalBeanInfo()
	{
		try
		{
			BeanInfo[] bi = new BeanInfo[1];
			bi[0] = Introspector.getBeanInfo(beanClass.getSuperclass());
			return bi;
		}
		catch (IntrospectionException e)
		{
			throw new Error(e.toString());
		}
	}

	/**
	* Gets the BeanDescriptor for this bean.
	* @return an object of type BeanDescriptor
	* @see java.beans.BeanDescriptor
	*/
	public BeanDescriptor getBeanDescriptor()
	{
		BeanDescriptor bd = new BeanDescriptor(beanClass);
		return bd;
	}

	/**
	 * Gets an image that may be used to visually represent this bean
	 * (in the toolbar, on a form, etc).
	 * @param iconKind the type of icon desired, one of: BeanInfo.ICON_MONO_16x16,
	 * BeanInfo.ICON_COLOR_16x16, BeanInfo.ICON_MONO_32x32, or BeanInfo.ICON_COLOR_32x32.
	 * @return an image for this bean
	 * @see BeanInfo#ICON_MONO_16x16
	 * @see BeanInfo#ICON_COLOR_16x16
	 * @see BeanInfo#ICON_MONO_32x32
	 * @see BeanInfo#ICON_COLOR_32x32
	 */
	public java.awt.Image getIcon(int nIconKind)
	{
		java.awt.Image img = null;
		if (nIconKind == BeanInfo.ICON_COLOR_16x16)
				img = loadImage("COORDINATEGRID16.gif");
		if (nIconKind == BeanInfo.ICON_MONO_16x16)
			img = loadImage("COORDINATEGRID16.gif");
		if (nIconKind == BeanInfo.ICON_COLOR_32x32)
			img = loadImage("COORDINATEGRID32.gif");
		if (nIconKind == BeanInfo.ICON_MONO_32x32)
			img = loadImage("COORDINATEGRID32.gif");
		return img;
	}

	/**
	 * Returns descriptions of this bean's properties.
	 */
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try{
			PropertyDescriptor xMin = new PropertyDescriptor("xMin",beanClass, "getXMin","setXMin");
			xMin.setBound(true);
			PropertyDescriptor xMax = new PropertyDescriptor("xMax",beanClass, "getXMax","setXMax");
			xMax.setBound(true);
			PropertyDescriptor yMin = new PropertyDescriptor("yMin",beanClass, "getYMin","setYMin");
			yMin.setBound(true);
			PropertyDescriptor yMax = new PropertyDescriptor("yMax",beanClass, "getYMax","setYMax");
			yMax.setBound(true);
			PropertyDescriptor axesColor = new PropertyDescriptor("axesColor",beanClass, "getAxesColor","setAxesColor");
			PropertyDescriptor gridColor = new PropertyDescriptor("gridColor",beanClass, "getGridColor","setGridColor");
			PropertyDescriptor gridLines = new PropertyDescriptor("gridLines",beanClass, "getGridLines","setGridLines");
			PropertyDescriptor xLabel = new PropertyDescriptor("xLabel",beanClass, "getXLabel","setXLabel");
			PropertyDescriptor yLabel = new PropertyDescriptor("yLabel",beanClass, "getYLabel","setYLabel");
			PropertyDescriptor zoomMode = new PropertyDescriptor("zoomMode",beanClass, "getZoomMode","setZoomMode");
			PropertyDescriptor title = new PropertyDescriptor("title",beanClass, "getTitle","setTitle");
			PropertyDescriptor message = new PropertyDescriptor("message",beanClass, "getMessage","setMessage");
			PropertyDescriptor zoomFactor = new PropertyDescriptor("zoomFactor",beanClass, "getZoomFactor","setZoomFactor");
			PropertyDescriptor titleEnabled = new PropertyDescriptor("titleEnabled",beanClass, "isTitleEnabled","setTitleEnabled");
			PropertyDescriptor titleFont = new PropertyDescriptor("titleFont",beanClass, "getTitleFont","setTitleFont");
			PropertyDescriptor traceFont = new PropertyDescriptor("traceFont",beanClass, "getTraceFont","setTraceFont");
			PropertyDescriptor Layout = new PropertyDescriptor("Layout",beanClass, "getLayout","setLayout");
			Layout.setHidden(true);
			PropertyDescriptor[] rv = {
				xMin
				,xMax
				,yMin
				,yMax
				,axesColor
				,gridColor
				,gridLines
				,xLabel
				,yLabel
				,zoomMode
				,title
				,message
				,zoomFactor
				,titleEnabled
				,titleFont
				,traceFont
				,Layout
			};
			zoomMode.setPropertyEditorClass(MathGridZoomModeEditor.class);
			gridLines.setPropertyEditorClass(MathGridGridLinesEditor.class);
			zoomFactor.setPropertyEditorClass(MathGridZoomFactorEditor.class);
			return rv;
		}
		catch (IntrospectionException e)
		{
			throw new Error(e.toString());
		}
	}

	private final Class beanClass = MathGrid.class;
}