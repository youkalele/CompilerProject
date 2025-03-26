package parser;
import scanner.Token;
import scanner.Token.TokenType;
public abstract class Declaration {
    protected TokenType type;
    protected Token id;

    public void printDecl(String tabs){
        //print tokentype?
        System.out.println(tabs + id.getData());
    }
}
