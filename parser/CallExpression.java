package parser;
import java.util.ArrayList;

import scanner.Token;

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
}
