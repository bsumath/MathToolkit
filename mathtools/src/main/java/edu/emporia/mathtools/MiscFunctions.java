
package edu.emporia.mathtools;

public class MiscFunctions {
    
    public static double gamma(double parameter)
    {
	    double x, y, temp, series;
	    double coeficient[] = {76.18009172947146,     -86.50532032941677,
		                       24.01409824083091,      -1.231739572450155,
		                        0.1208650973866179e-2, -0.5395239384953e-5};

	    y = x = parameter;
	    temp = x + 5.5;
	    temp -= (x + 0.5) * Math.log(temp);
	    series=1.000000000190015;
	    for (int j=0; j<=5; j++) series += coeficient[j] / ++y;
	    return -temp + Math.log(2.5066282746310005 * series/x);
    }

    public static double fact(double n)
    {
	    double values[]={   1.0,                    1.0,                      2.0,                       6.0,
	                       24.0,                  120.0,                    720.0,                    5040.0,
	                    40320.0,               362880.0,                3628800.0,                39916800.0,
	                479001600.0,           6227020800.0,            87178291200.0,           1307674368000.0,
	           20922789888000.0,      355687428096000.0,       6402373705728000.0,      121645100408832000.0,
	      2432902008176640000.0, 51090942171709440000.0, 1124000727777607680000.0, 25852016738884976640000.0
	    };

	    if(n < 0.0) return Double.NaN;
	
	    if (n > 23 || (n != Math.round(n))) return Math.exp(gamma(n + 1.0));

	    return values[(int)Math.round(n)];
    }

    public java.lang.String toString()
	{
		return "MiscellaneousFunctions[" + 
		        version + "]";
	}
  
  String version = "Version 1.0 - 07JUL1999";
}

