package parser;
public class ReturnStatement extends Statement {
    private Expression returnExpression;

    public ReturnStatement(){

    }
    public void addExpr(Expression e)
    {
        returnExpression=e;
    }

    public void printStmt(String tabs) {
        System.out.println(tabs + "return");
        returnExpression.printExp(tabs + "    ");
        System.out.println(tabs + "    " + ";");
    }
}
