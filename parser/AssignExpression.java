package parser;
import scanner.*;
public class AssignExpression extends Expression{
    
    private VarExpression lhs;
    private Expression rhs;

    public AssignExpression(Token idToken, Expression e)
    {
        lhs = new VarExpression(idToken);
        rhs = e;
    }
    public AssignExpression(VarExpression v, Expression e)
    {
        lhs = v;
        rhs =e;
    }
}
