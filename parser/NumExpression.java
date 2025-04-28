package parser;

import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;
import scanner.Token;
public class NumExpression extends Expression{
    private Token num;

    public NumExpression(Token n)
    {
        num=n;
    }

    public String printExp(String tabs) {
        return (tabs + num.getData()+"\n");
    }

    public void genLLCode(Function func) {
        //probably don't have to do much
        //you could assign yourself to a register,  or let parent handle
        this.regNum=func.getNewRegNum();
        Operand numOpr= new Operand(Operand.OperandType.INTEGER, Integer.parseInt(num.getData()));
        Operand regOpr = new Operand(Operand.OperandType.REGISTER, this.regNum);
        Operation assign = new Operation(OperationType.ASSIGN, func.getCurrBlock());
        assign.setDestOperand(0, regOpr);
        assign.setSrcOperand(0, numOpr);
        func.getCurrBlock().appendOper(assign);
    }
}
