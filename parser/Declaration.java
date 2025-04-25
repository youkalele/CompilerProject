package parser;
import lowlevel.CodeItem;
import scanner.Token;
import scanner.Token.TokenType;
public abstract class Declaration {
    protected TokenType type;
    protected Token id;

    public String printDecl(String tabs){
        //print tokentype?
        return id.getData()+"\n";
    }

    public CodeItem genLLcode()
    {
        return null;
    }
}
