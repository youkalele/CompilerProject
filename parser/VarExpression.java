package parser;
import scanner.Token;

public class VarExpression extends Expression {
    private Token id;
    private Expression indexExpr;

    public VarExpression(Token idToken, Expression e)
    {
        id=idToken; 
        indexExpr=e;
    }
    public VarExpression(Token idToken)
    {
        id=idToken; 
        indexExpr=null;
    }

    public void printVarExp(String tabs) {
        System.out.println(tabs + id);
        indexExpr.printExp(tabs + "\t");
    }

}
