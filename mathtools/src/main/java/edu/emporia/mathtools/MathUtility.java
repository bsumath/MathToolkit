

package edu.emporia.mathtools;


import java.text.*;

/**
 * This class contains a number of static methods that can be useful
 * for creating mathematical applets.
 * 
 * @author Joe Yanik
 * @version 1.2
 * @since 7/7/00
 */
public class MathUtility
{

    /**
     * Keeps track of whether the parseDouble method has been called so the parser will
     * only be initialized the first time.
     */
    static boolean notInitialized = true;

    /**
     * Returns the logarithm to the base 10
     * 
     * @param x the x-value to be used for the logarithme
     * @return the log to the base 10 of x
     */
    public static double log10(double x)
    {
        return Math.log(x) * .43429448190325182765112891891660508229439700580366;
    }
    
    /**
     * Returns a String description of a number that is
     * suitable for fitting into a fixed width field.
     * 
     * @param x the number to be formatted
     * @param fieldWidth the width of the field (not counting the decimal point)
     * fieldWidth will always be at least 6.
     * @param leadingZeros The maximum number of leading zeros that should be
     * displayed to the left of the decimal point before
     * it shifts into exponential notation.
     * @return A String description of the number
     */
    public static String displayFormat( double x, int fieldWidth, int leadingZeros)
    {
        if ( x > Double.MAX_VALUE ) return "+Infinity";
        if ( x < (- Double.MAX_VALUE) ) return "-Infinity";
        if ( Double.isNaN(x)) return "NaN";
    
        DecimalFormat decform = new DecimalFormat();
        int exponentDigits = 0, fractionDigits = 0;
        double mantissa = 0.0;
        
        if (x == 0.0)  return "0";
        
        //make fieldWidth at least 6
        fieldWidth = Math.max(fieldWidth, 6);
        
        String output;
        
        //Allow room for the negative sign, if necessary
        if (x < 0) fieldWidth--;
        
        //Compute the exponent that would be used in base 10 
        //exponential notation
        int exponent = (int)Math.floor(log10(Math.abs(x)));
        
        //If there is enough room display the number in decimal form
        if((Math.abs(exponent) < (fieldWidth)) && (exponent > (-leadingZeros-2)))
        {
            fractionDigits = 
                (exponent >= 0 ? fieldWidth-exponent-1 : fieldWidth-1);
            decform.setMaximumFractionDigits(fractionDigits);
            decform.setGroupingSize(50);
            output = removeZeros(decform.format(x));
        }
        //otherwise put it into exponential notation
        else
        {
            exponentDigits = (int)Math.floor(log10(Math.abs(exponent)))+1;
            mantissa = x;
            if(exponent < 0) 
            {
                exponentDigits++;
                for ( int i = 0; i<-exponent ; i++)
	                mantissa = mantissa*10;
	        }
	        else
	        {
	            for ( int i = 0; i<exponent ; i++)
	                mantissa = mantissa/10;
	        }
	   
            decform.setMaximumFractionDigits(fieldWidth-exponentDigits-2);
            output = removeZeros(decform.format(mantissa))+"E"+exponent;
        }
        return output;
    }//end of displayFormat method
    
    /**
     * overloaded version of displayFormat that makes
     * leadingZeros equal to 4.
     * 
     * @param x The number to be formatted
     * @param fieldWidth The width of the field (not counting the decimal point)
     * @return A String description of the number.
     */
    public static String displayFormat(double x, int fieldWidth)
    {
        return displayFormat(x,fieldWidth,4);
    }
    
    /**
     * Overloaded version of displayFormat that makes
     * the fieldWidth 8 and the leadingZeros 4
     * 
     * @param x The number to be formatted
     * @return A String description of the number
     */
    public static String displayFormat(double x)
    {
        return displayFormat(x,8,4);
    }
    
    /**
     * Rounds the number off to a given number of
     * significant digits.
     * 
     * @param x The number to be rounded
     * @param d The number of significant digits
     * @return x rounded to d significant digits.
     */
	public static double roundDecimal( double x, int d)
	{
	    
	    if((d>0) && (x!=0))
	    {
	        double factor = Math.pow(10,Math.floor(log10(Math.abs(x))) -d +1);
	        x = Math.rint(x/factor)*factor;    
	    }
	    return x;
	}
	
	/**
	 * Removes extraneous trailing zeros from a decimal expression
	 * 
	 * @param s
	 */
	public static String removeZeros(String s)
	{
	    int decimalPlace = s.indexOf('.');
	    if ( decimalPlace > -1)
	    {
	        int stringLength = s.length();
	        while ( (s.charAt(stringLength-1) == '0')
	            && (stringLength > decimalPlace + 2))
	                 s = s.substring(0, --stringLength);
	    }
	    return s;
	}
	
	/**
	 * Attempts to convert a String expression into a double
	 * 
	 * @param s The String to be converted
	 * @return The double precision floating point number corresponding to the expression
	 * @exception edu.emporia.mathtools.MathSyntaxError This is thrown if the parser is unable to parse the expression
	 */
	public static double parseDouble ( String s)
	    throws MathSyntaxError
	{
	    double result;
	    if(notInitialized)
	    {
	        Variable.make("Pi").set_value(Math.PI);
	        Variable.make("PI").set_value(Math.PI);
	        Variable.make("e").set_value(Math.E);
	        Variable.make("E").set_value(Math.E);
	        Variable.make("NaN").set_value(Double.NaN);
	        Variable.make("infinity").set_value(Double.POSITIVE_INFINITY);
	        Variable.make("negativeInfinity").set_value(Double.NEGATIVE_INFINITY);
	        notInitialized = false;
	    }
	    
	    try {
	        result = Double.valueOf(s).doubleValue();
	    }catch (NumberFormatException e)
	    {
	        try {
	           result = Parser.parse(s).value();
	        }
	        catch(Syntax_error error)
	        {
	            throw new MathSyntaxError("Unable to parse " + s, s);
	        }
	    }
	    
	    return result;
	}
	            
	        
        
 }