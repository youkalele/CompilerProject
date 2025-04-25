package parser;

import lowlevel.*;

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

    public CodeItem genLLCode() {
        //Just call genCode on the Expr and do nothing else
        expression.genLLCode();
    }
}

