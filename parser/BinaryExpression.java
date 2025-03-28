package parser;

import scanner.Token;

public class BinaryExpression extends Expression{
    private Expression lhs;
    private Token opType;
    private Expression rhs;

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

}
