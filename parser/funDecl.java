package parser;
import java.util.ArrayList;
import lowlevel.*;
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
            declString+=(tabs + "    ,\n");
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

    public CodeItem genLLcode()
    {

        Function func = new Function(isVoid ? 0 : 1, id.getData());
        if(!params.isEmpty())
        {
            FuncParam head = new FuncParam(1, params.get(0).getId().getData(), params.get(0).isArray());
            func.getTable().put(params.get(0).getId().getData(), func.getNewRegNum());
            FuncParam prevParam = head;
            for(int i = 1; i<params.size(); i++)
            {
                FuncParam p = new FuncParam(1, params.get(i).getId().getData(), params.get(i).isArray());
                func.getTable().put(params.get(i).getId().getData(), func.getNewRegNum());

                prevParam.setNextParam(p);
                prevParam = p;
            }
            func.setFirstParam(head);
        }

        func.createBlock0();
        BasicBlock block = new BasicBlock(func);
        func.appendBlock(block);
        func.setCurrBlock(block);
        body.genLLcode(func); //all statemtns and expressions are like this
        func.appendBlock(func.getReturnBlock());

        if(func.getFirstUnconnectedBlock()!=null)
        {
            func.appendBlock(func.getFirstUnconnectedBlock()); //FIXME
        }

    }
}
