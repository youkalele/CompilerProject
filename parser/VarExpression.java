package parser;
import compiler.CMinusCompiler;
import lowlevel.Function;
import lowlevel.LowLevelException;
import lowlevel.Operand;
import lowlevel.Operation;
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
        String varName = id.getData();
        int varRegNum=-1;
        if(func.getTable().get(varName)!=null)
        {
            varRegNum = (int) func.getTable().get(varName);
        }
        else
        {
            if(CMinusCompiler.globalHash.get(varName.hashCode())!=null)
            {
                
                Operation load= new Operation(Operation.OperationType.LOAD_I, func.getCurrBlock());
                Operand name = new Operand(Operand.OperandType.STRING, varName);
                varRegNum=func.getNewRegNum();
                Operand dest = new Operand(Operand.OperandType.REGISTER, varRegNum);
                Operand zero = new Operand(Operand.OperandType.INTEGER, 0);

                load.setSrcOperand(0, name);
                load.setSrcOperand(1, zero);
                load.setDestOperand(0, dest);

                func.getCurrBlock().appendOper(load);

            }
            else{
                throw new LowLevelException("gay");
            }

        }

        this.regNum=varRegNum;
        
    }
}
