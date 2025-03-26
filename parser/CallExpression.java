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
        for (int i = 0; i < args.size()-1; i++) {
            args.get(i).printExp(tabs + "    ");
            System.out.println(tabs + "    ,");
        }
        args.get(args.size()-1).printExp(tabs + "    ");
        System.out.println(tabs + ")");
    }
}
