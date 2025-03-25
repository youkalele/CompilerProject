
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
        String.out.println(tabs + id);
        String.out.println(tabs + isArray);
    }

}
