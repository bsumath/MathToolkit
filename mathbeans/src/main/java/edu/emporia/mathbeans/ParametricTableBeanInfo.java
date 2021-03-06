package edu.emporia.mathbeans;

import java.beans.*;
import java.beans.SimpleBeanInfo;
//import symantec.itools.beans.*;

public class ParametricTableBeanInfo extends java.beans.SimpleBeanInfo
{
	public ParametricTableBeanInfo()
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
				img = loadImage("ParametricTable16.gif");
		if (nIconKind == BeanInfo.ICON_MONO_16x16)
			img = loadImage("ParametricTable16.gif");
		if (nIconKind == BeanInfo.ICON_COLOR_32x32)
			img = loadImage("ParametricTable32.gif");
		if (nIconKind == BeanInfo.ICON_MONO_32x32)
			img = loadImage("ParametricTable32.gif");
		return img;
	}

	/**
	 * Returns descriptions of this bean's properties.
	 */
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try{
			PropertyDescriptor tMin = new PropertyDescriptor("tMin",beanClass, "getTMin","setTMin");
			PropertyDescriptor tDelta = new PropertyDescriptor("tDelta",beanClass, "getTDelta","setTDelta");
			PropertyDescriptor autoTValue = new PropertyDescriptor("autoTValue",beanClass, "isAutoTValue","setAutoTValue");
			PropertyDescriptor f = new PropertyDescriptor("f",beanClass, "getF","setF");
			PropertyDescriptor g = new PropertyDescriptor("g",beanClass, "getG","setG");
			PropertyDescriptor xLabel = new PropertyDescriptor("xLabel",beanClass, "getXLabel","setXLabel");
			PropertyDescriptor yLabel = new PropertyDescriptor("yLabel",beanClass, "getYLabel","setYLabel");
			PropertyDescriptor tLabel = new PropertyDescriptor("tLabel",beanClass, "getTLabel","setTLabel");
			PropertyDescriptor Layout = new PropertyDescriptor("Layout",beanClass, "getLayout","setLayout");
			Layout.setHidden(true);
			PropertyDescriptor[] rv = {
				tMin
				,tDelta
				,autoTValue
				,f
				,g
				,xLabel
				,yLabel
				,tLabel
				,Layout
			};
			return rv;
		}
		catch (IntrospectionException e)
		{
			throw new Error(e.toString());
		}
	}

	private final Class beanClass = ParametricTable.class;
}