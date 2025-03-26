package parser;

public class ExpressionStatement extends Statement {
    private Expression expression;

    public ExpressionStatement(){

    }
    public void addExpr(Expression e)
    {
        expression=e;
    }

    public String printStmt(String tabs) {
        String statementString = "";
        statementString+=expression.printExp(tabs);
        statementString+=(tabs+";\n");
        return statementString;
    }
}

