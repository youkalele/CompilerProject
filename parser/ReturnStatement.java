package parser;
public class ReturnStatement extends Statement {
    private Expression returnExpression;

    public ReturnStatement(){

    }
    public void addExpr(Expression e)
    {
        returnExpression=e;
    }

    public void printReturnStmt(String tabs) {
        returnExpression.printExp(tabs + "\t");
    }
}
