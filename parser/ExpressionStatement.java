package parser;
public class ExpressionStatement extends Statement {
    private Expression expression;

    public ExpressionStatement(){

    }
    public void addExpr(Expression e)
    {
        expression=e;
    }
}

