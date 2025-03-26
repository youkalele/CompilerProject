package parser;
import java.util.ArrayList;

import scanner.Token;
import scanner.Token.TokenType;

public class funDecl extends Declaration {
    private ArrayList<Param> params = new ArrayList<Param>();
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

    public void printDecl(String tabs) {
        System.out.println(isVoid ? "void " : "int ");
        super.printDecl(tabs);
        System.out.println("(");
        for (int i = 0; i < params.size()-1; i++) {
            params.get(i).printParam(tabs + "    ");
            System.out.println(",");
        }
        params.get(params.size()-1).printParam(tabs + "    ");
        System.out.println("\n" + tabs + ")\n" + tabs + "{");
        body.printStmt(tabs + "    ");
        System.out.println(tabs + "}");
    }
}
