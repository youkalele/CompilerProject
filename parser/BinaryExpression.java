package parser;

import scanner.Token;
import lowlevel.*;

public class BinaryExpression extends Expression{
    private Expression lhs;
    private Token opType;
    private Expression rhs;
    protected int regNum;
    protected int lhsRegNum;
    protected int rhsRegNum;

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
    public int getLHSRegNum() {
        return rhsRegNum;
    }
    public int getRHSRegNum() {
        return lhsRegNum;
    }

    public void genLLcode(Function func) {
        //call genCode on left and right child
        lhs.genLLcode(func);
        rhs.genLLcode(func);
        //get location of where children stored their results
        lhsRegNum = lhs.getRegNum();
        rhsRegNum = rhs.getRegNum();
        //get a new register for your result
        regNum = func.getNewRegNum();
        //add Operation to do your function
        OperationType operand;
        switch (opType.getType().name()) {
            case "NOTEQUAL":
                operand = OperationType.NOT_EQUAL;
                break;
            case "EQUALS":
                operand = OperationType.EQ;
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
                operand = OperationType.GET;
                break;
            case "PLUS":
                operand = OperationType.Add_I;
                break;
            case "MINUS":
                operand = OperationType.Sub_I;
                break;
            case "TIMES":
                operand = OperationType.Mul_I;
                break;
            case "DIVIDE":
                operand = OperationType.Div_I;
                break;
            default:
                throw new Exception("Womp womp");
                break;
        BasicBlock block = new BasicBlock(func);
        Operation oper = new Operation(operand, block);
        func.appendBlock(block);
    }
}
