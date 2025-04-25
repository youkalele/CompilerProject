package parser;
import scanner.Token;
import lowlevel.*;

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

    public CodeItem genLLCode() {
        //Just look up your location in the symbol table (if not already done in previous pass)

        //If global, create a load oper
        
    }
}
