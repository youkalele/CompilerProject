package parser;

import lowlevel.*;

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

    public void genLLcode(Function func)
    {
        
    }
}
