package parser;

import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;
import scanner.Token;

public class BinaryExpression extends Expression{
    private Expression lhs;
    private Token opType;
    private Expression rhs;
    protected int regNum;

    public BinaryExpression(Expression l, Token o, Expression r)
    {
        lhs=l;
        opType=o;
        rhs = r;
    }
    public BinaryExpression()
    {

    }

    public String printExp(String tabs) {
        String exprString="";
        String operand;
        switch (opType.getType().name()) {
            case "NOTEQUAL":
                operand = "!=";
                break;
            case "EQUALS":
                operand = "==";
                break;
            case "LT":
                operand = "<";
                break;
            case "LOET":
                operand = "<=";
                break;
            case "GT":
                operand = ">";
                break;
            case "GOET":
                operand = ">=";
                break;
            case "PLUS":
                operand = "+";
                break;
            case "MINUS":
                operand = "-";
                break;
            case "TIMES":
                operand = "*";
                break;
            case "DIVIDE":
                operand = "/";
                break;
            default:
                operand = "ERROR";
                break;
        }
        exprString+=(tabs + operand+"\n");
        exprString+=lhs.printExp(tabs + "    ");
        exprString+=rhs.printExp(tabs + "    ");

        return exprString;
    }

    public int getRegNum()
    {
        return regNum;
    }
    public void setRegNum(int num)
    {
        regNum=num;
    }

    public void genLLcode(Function func) {
        //call genCode on left and right child
        lhs.genLLcode(func);
        rhs.genLLcode(func);
        //get location of where children stored their results
        //get a new register for your result
        regNum = func.getNewRegNum();
        //add Operation to do your function
        OperationType operand=null;
        switch (opType.getType().name()) {
            case "NOTEQUAL":
                operand = OperationType.NOT_EQUAL;
                break;
            case "EQUALS":
                operand = OperationType.EQUAL;
                break;
            case "LT":
                operand = OperationType.LT;
                break;
            case "LOET":
                operand = OperationType.LTE;
                break;
            case "GT":
                operand = OperationType.GT;
                break;
            case "GOET":
                operand = OperationType.GTE;
                break;
            case "PLUS":
                operand = OperationType.ADD_I;
                break;
            case "MINUS":
                operand = OperationType.SUB_I;
                break;
            case "TIMES":
                operand = OperationType.MUL_I;
                break;
            case "DIVIDE":
                operand = OperationType.DIV_I;
                break;
            default:
                //throw new Exception("Womp womp");
                break;
        }
        Operation oper = new Operation(operand, func.getCurrBlock());
        Operand lhsOper = new Operand(OperandType.INTEGER, lhs.getRegNum());
        Operand rhsOper = new Operand(OperandType.INTEGER, rhs.getRegNum());
        oper.setSrcOperand(0, lhsOper);
        oper.setSrcOperand(1, rhsOper);
        Operand result = new Operand (OperandType.INTEGER, this.regNum);
        oper.setDestOperand(0, result);
        func.getCurrBlock().appendOper(oper);
    }
}
