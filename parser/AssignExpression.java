package parser;
public class AssignExpression extends Expression{
    
    private VarExpression lhs;
    private Expression rhs;

    
    public AssignExpression(VarExpression v, Expression e)
    {
        lhs = v;
        rhs =e;
    }

    public void printExp(String tabs) {
        System.out.println(tabs + "="); //I forget what an assign expression is
        lhs.printExp(tabs + "    ");
        rhs.printExp(tabs + "    ");
    }
}
