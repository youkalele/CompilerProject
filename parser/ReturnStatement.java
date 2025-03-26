package parser;
public class ReturnStatement extends Statement {
    private Expression returnExpression;

    public ReturnStatement(){

    }
    public void addExpr(Expression e)
    {
        returnExpression=e;
    }

    public String printStmt(String tabs) {
        String statementString = "";
        statementString+=(tabs + "return\n");
        statementString+=returnExpression.printExp(tabs + "    ");
        statementString+=(tabs + "    " + ";\n");
        return statementString;
    }
}
