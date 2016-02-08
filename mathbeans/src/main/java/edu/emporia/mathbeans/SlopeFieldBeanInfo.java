package edu.emporia.mathbeans;

import java.beans.*;
import java.beans.SimpleBeanInfo;
//import symantec.itools.beans.*;

public class SlopeFieldBeanInfo extends java.beans.SimpleBeanInfo
{
	public SlopeFieldBeanInfo()
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
				img = loadImage("SlopeField16.gif");
		if (nIconKind == BeanInfo.ICON_MONO_16x16)
			img = loadImage("SlopeField16.gif");
		if (nIconKind == BeanInfo.ICON_COLOR_32x32)
			img = loadImage("SlopeField32.gif");
		if (nIconKind == BeanInfo.ICON_MONO_32x32)
			img = loadImage("SlopeField32.gif");
		return img;
	}

	/**
	 * Returns descriptions of this bean's properties.
	 */
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try{
			PropertyDescriptor xDerivative = new PropertyDescriptor("xDerivative",beanClass, "getXDerivative","setXDerivative");
			PropertyDescriptor yDerivative = new PropertyDescriptor("yDerivative",beanClass, "getYDerivative","setYDerivative");
			PropertyDescriptor slopeRadius = new PropertyDescriptor("slopeRadius",beanClass, "getSlopeRadius","setSlopeRadius");
			PropertyDescriptor eulerDelta = new PropertyDescriptor("eulerDelta",beanClass, "getEulerDelta","setEulerDelta");
			PropertyDescriptor eulerPointNumber = new PropertyDescriptor("eulerPointNumber",beanClass, "getEulerPointNumber","setEulerPointNumber");
			PropertyDescriptor eulerGraphColor = new PropertyDescriptor("eulerGraphColor",beanClass, "getEulerGraphColor","setEulerGraphColor");
			PropertyDescriptor fieldColor = new PropertyDescriptor("fieldColor",beanClass, "getFieldColor","setFieldColor");
			PropertyDescriptor eulerMouseEnabled = new PropertyDescriptor("eulerMouseEnabled",beanClass, "isEulerMouseEnabled","setEulerMouseEnabled");
			PropertyDescriptor Layout = new PropertyDescriptor("Layout",beanClass, "getLayout","setLayout");
			Layout.setHidden(true);
			PropertyDescriptor[] rv = {
				xDerivative
				,yDerivative
				,slopeRadius
				,eulerDelta
				,eulerPointNumber
				,eulerGraphColor
				,fieldColor
				,eulerMouseEnabled
				,Layout
			};
			return rv;
		}
		catch (IntrospectionException e)
		{
			throw new Error(e.toString());
		}
	}

	private final Class beanClass = SlopeField.class;
}