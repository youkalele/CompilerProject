
package parser;

import scanner.Token;

public class VarDecl extends Declaration{
    private int arrVal=-1;

    public VarDecl(Token idToken, int val)
    {
        id = idToken;
        arrVal = val;
    }

    public String printDecl(String tabs) {
        String declString="";
        declString+=(tabs + "int ");
        declString+=super.printDecl(tabs);
        if(arrVal!=-1)
            declString+=("[" + arrVal + "]\n");
        return declString;
    }
}
