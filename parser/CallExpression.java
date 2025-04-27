package parser;
import java.util.ArrayList;

import DrG code.lowlevel.CodeItem;
import scanner.Token;
import lowlevel.*;

public class CallExpression extends Expression{
    private Token id;
    private ArrayList<Expression> args;

    public CallExpression(Token idToken, ArrayList<Expression> e)
    {
        id=idToken;
        args= e;
    }

    public String printExp(String tabs) {
        String exprString = "";
        exprString+=(tabs + id.getData() + "\n" + tabs + "(\n");
        if(args!=null){
            for (int i = 0; i < args.size()-1; i++) {
                exprString+=args.get(i).printExp(tabs + "    ");
                exprString+=(tabs + "    ,\n");
            }
            if(args.size()>0)
            {
                exprString+=args.get(args.size()-1).printExp(tabs + "    ");
                
            }
        }
        
        exprString+=(tabs + ")\n");


        return exprString;
    }

    public void genLLcode(Function func) {
        //a bit more complicated
        //call genCode on params to generate code for them (order doesn't matter because we're doing x64)
        //add operation to move each param to register or memory
        //add call operation
        //May want to add a Macro Operation for PostCall
        //  Or let a later pass just handle this
        //  For project, you will annotate Call with param size
        //Need to move return register into regular register
        //And annotate the Call node with this register
        //  What about saving registers, ala caller-save convention?
    }
}
