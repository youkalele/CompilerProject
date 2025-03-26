
package parser;

import scanner.Token;

public class VarDecl extends Declaration{
    private int arrVal=-1;

    public VarDecl(Token idToken, int val)
    {
        id = idToken;
        arrVal = val;
    }

    public void printDecl(String tabs) {
        System.out.print(tabs + "int ");
        super.printDecl(tabs);
        if(arrVal!=-1)
            System.out.println("[" + arrVal + "]");
    }
}
