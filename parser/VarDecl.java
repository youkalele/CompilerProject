
package parser;

import scanner.Token;

public class VarDecl extends Declaration{
    private int arrVal;

    public VarDecl(Token idToken, int val)
    {
        id = idToken;
        arrVal = val;
    }
}
