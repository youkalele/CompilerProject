package parser;

import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;
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
        if (returnExpression != null) {
            statementString+=returnExpression.printExp(tabs + "    ");
        }
        statementString+=(tabs + "    " + ";\n");
        return statementString;
    }

    public void genLLcode(Function func)
    {
        if (returnExpression != null) {
            returnExpression.genLLcode(func); //If it returns an expression, call genCode on the Expr
            //Add Operation to move expression result into return register
            Operation move = new Operation(OperationType.ASSIGN, func.getCurrBlock());
            Operand retReg = new Operand(OperandType.MACRO, "RetReg");
            Operand result = new Operand(OperandType.REGISTER, returnExpression.getRegNum());
            move.setSrcOperand(0, result);
            move.setDestOperand(0, retReg);

            func.getCurrBlock().appendOper(move);
            //Add jump Operation to exit block
        }
            Operand exitBlock = new Operand(OperandType.BLOCK, func.getReturnBlock());
            Operation jump = new Operation(OperationType.JMP, func.getCurrBlock());
            jump.setSrcOperand(0, exitBlock);
            func.getCurrBlock().appendOper(jump);
        
    }
}
