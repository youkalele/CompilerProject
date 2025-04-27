package parser;

import lowlevel.*;
public class AssignExpression extends Expression{
    
    private VarExpression lhs;
    private Expression rhs;

    
    public AssignExpression(VarExpression v, Expression e)
    {
        lhs = v;
        rhs =e;
    }

    public String printExp(String tabs) {
        String exprString = "";
        exprString+=(tabs + "=\n"); //I forget what an assign expression is
        exprString+=lhs.printExp(tabs + "    ");
        exprString+=rhs.printExp(tabs + "    ");
        return exprString;
    }

    public void genLLcode(Function func)
    {
        
    }
}
