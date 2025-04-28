package parser;

import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;

public class SelectionStatement extends Statement {

    private Expression booleanExpression;
    private Statement stmt;
    private Statement elsePart;

    public SelectionStatement() {

    }

    public void addExpr(Expression e) {
        booleanExpression = e;
    }

    public void addStmt(Statement s) {
        stmt = s;
    }

    public void addElsePart(Statement s) {
        elsePart = s;
    }

    public String printStmt(String tabs) {
        String statementString = "";
        statementString+=(tabs + "if\n" + tabs + "(\n");
        statementString+=booleanExpression.printExp(tabs + "    ");
        statementString+=(tabs + ")\n" + tabs + "{\n");
        statementString+=stmt.printStmt(tabs + "    ");
        statementString+=(tabs + "}\n");
        if(elsePart!=null)
        {
            statementString+=(tabs + "else\n" + tabs + "{\n");
            statementString+=elsePart.printStmt(tabs + "    ");
            statementString+=(tabs + "}\n");
        }

        return statementString;
    }

    public void genLLcode(Function func)
    {
        BasicBlock thenBlock = new BasicBlock(func);
        BasicBlock postBlock = new BasicBlock(func);
        BasicBlock elseBlock = new BasicBlock(func);

        //evaluating branch
        booleanExpression.genLLcode(func);
        Operation branch = new Operation(Operation.OperationType.BEQ, func.getCurrBlock());
        Operand expr = new Operand(Operand.OperandType.REGISTER, booleanExpression.getRegNum());
        Operand zero = new Operand(Operand.OperandType.INTEGER, 0);
        Operand postOperand = new Operand(Operand.OperandType.BLOCK, postBlock);
        Operand elseOperand = new Operand(Operand.OperandType.BLOCK, elseBlock);
        branch.setSrcOperand(0, expr);
        branch.setSrcOperand(1, zero);
        if(elsePart==null)
            branch.setSrcOperand(2, postOperand);
        else
            branch.setSrcOperand(2, elseOperand);
        func.getCurrBlock().appendOper(branch);

        //move to thenBlock
        func.appendToCurrentBlock(thenBlock);
        func.setCurrBlock(thenBlock);
        stmt.genLLcode(func);

        
        func.appendToCurrentBlock(postBlock);

        if(elsePart!=null)
        {
            func.setCurrBlock(elseBlock);

            elsePart.genLLcode(func);

            Operation jump = new Operation(OperationType.JMP, func.getCurrBlock());
            jump.setSrcOperand(0, postOperand);
            elseBlock.appendOper(jump);
            func.appendUnconnectedBlock(elseBlock);
        }

        func.setCurrBlock(postBlock);
    }
}
