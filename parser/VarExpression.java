package parser;
import lowlevel.Function;
import scanner.Token;

public class VarExpression extends Expression {
    private Token id;
    private Expression indexExpr;

    public VarExpression(Token idToken, Expression e)
    {
        id=idToken; 
        indexExpr=e;
    }
    public VarExpression(Token idToken)
    {
        id=idToken; 
        indexExpr=null;
    }

    public String printExp(String tabs) {
        String exprString = "";
        exprString+=(tabs + id.getData() + "\n");
        if (indexExpr != null) {
            exprString+=(tabs+"[\n");
            exprString+=indexExpr.printExp(tabs + "    ");
            exprString+=(tabs+"]\n");

        }
        return exprString;
    }
    public Token getId()
    {
        return id;
    }
    public void genLLCode(Function func) {
        //Just look up your location in the symbol table (if not already done in previous pass)
        
        //If global, create a load oper
        
    }
}
