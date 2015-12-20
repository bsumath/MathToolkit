package EDU.emporia.mathbeans;

import java.beans.*;
import java.beans.SimpleBeanInfo;
import symantec.itools.beans.*;

public class SymbolicParametricCurveBeanInfo extends java.beans.SimpleBeanInfo
{
	public SymbolicParametricCurveBeanInfo()
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
				img = loadImage("Parametric16.gif");
		if (nIconKind == BeanInfo.ICON_MONO_16x16)
			img = loadImage("Parametric16.gif");
		if (nIconKind == BeanInfo.ICON_COLOR_32x32)
			img = loadImage("Parametric32.gif");
		if (nIconKind == BeanInfo.ICON_MONO_32x32)
			img = loadImage("Parametric32.gif");
		return img;
	}

	/**
	 * Returns descriptions of this bean's properties.
	 */
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try{
			PropertyDescriptor xFormula = new PropertyDescriptor("xFormula",beanClass, "getXFormula","setXFormula");
			PropertyDescriptor yFormula = new PropertyDescriptor("yFormula",beanClass, "getYFormula","setYFormula");
			PropertyDescriptor[] rv = {
				xFormula
				,yFormula
			};
			return rv;
		}
		catch (IntrospectionException e)
		{
			throw new Error(e.toString());
		}
	}

	private final Class beanClass = SymbolicParametricCurve.class;
}