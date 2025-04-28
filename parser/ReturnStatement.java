package parser;

import lowlevel.*;
import lowlevel.Operation.OperationType;

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
        returnExpression.genLLcode(func); //If it returns an expression, call genCode on the Expr
        //Add Operation to move expression result into return register
        Operation move = new Operation(OperationType.ASSIGN, func.getCurrBlock());
        //Add jump Operation to exit block
        Operation jump = new Operation(OperationType.JMP, func.getCurrBlock());
    }
}
