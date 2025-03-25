package parser;
public class AssignExpression extends Expression{
    
    private VarExpression lhs;
    private Expression rhs;

    
    public AssignExpression(VarExpression v, Expression e)
    {
        lhs = v;
        rhs =e;
    }

    public void printAssignExp(String tabs) {
        lhs.printVarExp(tabs + "\t");
        rhs.printExp(tabs + "\t");
    }
}
