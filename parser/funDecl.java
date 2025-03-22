package parser;
import java.util.ArrayList;

import scanner.Token;
import scanner.Token.TokenType;

public class funDecl extends Declaration {
    private ArrayList<Param> params;
    private boolean isVoid = false;
    private CompoundStatement body;

    public funDecl(Token idToken, TokenType retType)
    {
        id=idToken;
        type = retType;        
    }

    public void addParam(Param p)
    {
        params.add(p);
    }

    public void setVoid()
    {
        isVoid=true;
    }

    public void setBody(CompoundStatement c)
    {
        body = c;
    }
}
