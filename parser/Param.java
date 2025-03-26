
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

    public String printParam(String tabs) {
        return (tabs + "int " + id.getData())+"\n";
    }

}
