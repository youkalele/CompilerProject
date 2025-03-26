
package parser;
import scanner.Token;
public class Param {
    private Token id;
    private boolean isArray;

    public Param(Token idToken, boolean arrQ)
    {
        id=idToken;
        isArray=arrQ;
    }

    public void printParam(String tabs) {
        System.out.print(tabs + id.getData());
        //System.out.print(tabs + "int " + id.getData());
    }

}
