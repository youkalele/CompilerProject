package parser;

import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;

public class IterationStatement extends Statement {
    private Expression booleanExpression;
    private Statement stmt;

    public IterationStatement() {

    }

    public void addExpr(Expression e) {
        booleanExpression = e;
    }

    public void addStmt(Statement s) {
        stmt = s;
    }

    public String printStmt(String tabs) {
        String statementString = tabs+"while\n"+tabs+"(\n";
        statementString+=booleanExpression.printExp(tabs + "    ");
        statementString+=tabs+")\n"+tabs+"{\n";
        statementString+=stmt.printStmt(tabs + "    ");
        statementString+=tabs+"}\n";
        return statementString;
    }

    public void genLLcode(Function func)
    {
        BasicBlock thenBlock = new BasicBlock(func);
        BasicBlock postBlock = new BasicBlock(func);

        booleanExpression.genLLcode(func); 
        
        Operand expr = new Operand(Operand.OperandType.REGISTER, booleanExpression.getRegNum()); 
        Operand zero = new Operand(Operand.OperandType.INTEGER, 0);
        Operand postOperand = new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum());
        Operand thenOperand = new Operand(Operand.OperandType.BLOCK, thenBlock.getBlockNum());

        //branch to post if condition is false
        Operation beq = new Operation(Operation.OperationType.BEQ, func.getCurrBlock());
        beq.setSrcOperand(0, expr);
        beq.setSrcOperand(1, zero);
        beq.setSrcOperand(2, postOperand);
        func.getCurrBlock().appendOper(beq);

        //move to then block
        func.appendToCurrentBlock(thenBlock);
        func.setCurrBlock(thenBlock);

        stmt.genLLcode(func);

        //go back to the beginning of thenBLock if the condition is not false
        Operation bne = new Operation(OperationType.BNE, func.getCurrBlock());
        bne.setSrcOperand(0, expr);
        bne.setSrcOperand(1, zero);
        bne.setSrcOperand(2, thenOperand);
        thenBlock.appendOper(bne);


        //move to post block
        func.appendToCurrentBlock(postBlock);
        func.setCurrBlock(postBlock);
    }
}
