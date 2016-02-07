// Syntax-error exception.
// Copyright 1996 by Darius Bacon; see the file COPYING.

// 07JUL1999 - version 1.0 - C. Pheatt

package EDU.emporia.mathtools;

public class MathSyntaxError extends Exception {
    public String stringToBeParsed = "";
    public MathSyntaxError(){super();}
    public MathSyntaxError (String complaint) { super (complaint); }
    public MathSyntaxError(String complaint, String stringToBeParsed )  
    {
        super(complaint);
        this.stringToBeParsed = stringToBeParsed;
    }
    
    public String getStringToBeParsed()
    {
        return stringToBeParsed;
    }
  
}
