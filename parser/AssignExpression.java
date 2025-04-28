package parser;

import compiler.CMinusCompiler;
import lowlevel.Function;
import lowlevel.LowLevelException;
import lowlevel.Operand;
import lowlevel.Operation;
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
        String varName=lhs.getId().getData();
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
        
        rhs.genLLcode(func);

        Operation assign= new Operation(Operation.OperationType.ASSIGN, func.getCurrBlock());
        Operand varReg = new Operand(Operand.OperandType.REGISTER, varRegNum);
        this.regNum = varRegNum;
        Operand src = new Operand(Operand.OperandType.REGISTER, rhs.getRegNum());
        assign.setSrcOperand(0, src);
        assign.setDestOperand(0, varReg);

        func.getCurrBlock().appendOper(assign);
    }
}
