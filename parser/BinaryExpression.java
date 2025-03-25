package parser;

import scanner.Token;

public class BinaryExpression extends Expression{
    private Expression lhs;
    private Token opType;
    private Expression rhs;

    public BinaryExpression(Expression l, Token o, Expression r)
    {
        lhs=l;
        opType=o;
        rhs = r;
    }
    public BinaryExpression()
    {

    }

    public void printBinaryExp(String tabs) {
        lhs.printExp(tabs + "\t");
        System.out.println(tabs + "\t" + opType);
        rhs.printExp(tabs + "\t");
    }

}
