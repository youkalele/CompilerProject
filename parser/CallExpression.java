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

    public void printCallExp(String tabs) {
        System.out.println(tabs + "\t" + id);
        for (Expression exp : args) {
            exp.printExp(tabs + "\t");
        }
    }
}
