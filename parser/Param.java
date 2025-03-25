
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
        System.out.println(tabs + id);
        System.out.println(tabs + isArray);
    }

}
