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

    public void printExp(String tabs) {
        System.out.println(tabs + id.getData() + "\n" + tabs + "(");
        if(args!=null){
            for (int i = 0; i < args.size()-1; i++) {
                args.get(i).printExp(tabs + "    ");
                System.out.println(tabs + "    ,");
            }
            if(args.size()>0)
            {
                args.get(args.size()-1).printExp(tabs + "    ");
                
            }
        }
        
        System.out.println(tabs + ")");
    }
}
