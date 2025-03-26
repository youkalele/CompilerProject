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

    public String printDecl(String tabs) {
        String declString = "";
        declString+=(isVoid ? "void " : "int ");
        declString+=super.printDecl(tabs);
        declString+="(\n";
        for (int i = 0; i < params.size()-1; i++) {
            declString+=params.get(i).printParam(tabs + "    ");
            declString+=(tabs+",\n");
        }
        if(params.size()>0)
        {
            declString+=params.get(params.size()-1).printParam(tabs + "    ");
        }
        declString+=(tabs + ")\n" + tabs + "{\n");
        declString+=body.printStmt(tabs + "    ");
        declString+=(tabs + "}\n");

        return declString;
    }
}
