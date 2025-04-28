package parser;
import java.util.ArrayList;

import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;
import scanner.Token;

public class CallExpression extends Expression{
    private Token id;
    private ArrayList<Expression> args;
    protected int regNum;

    public CallExpression(Token idToken, ArrayList<Expression> e)
    {
        id=idToken;
        args= e;
    }

    public String printExp(String tabs) {
        String exprString = "";
        exprString+=(tabs + id.getData() + "\n" + tabs + "(\n");
        if(args!=null){
            for (int i = 0; i < args.size()-1; i++) {
                exprString+=args.get(i).printExp(tabs + "    ");
                exprString+=(tabs + "    ,\n");
            }
            if(args.size()>0)
            {
                exprString+=args.get(args.size()-1).printExp(tabs + "    ");
                
            }
        }
        
        exprString+=(tabs + ")\n");


        return exprString;
    }
    public int getParamNum()
    {
        return args.size();
    }

    public void genLLcode(Function func) {
        for (Expression expression : args) {
            expression.genLLcode(func);

            Operand param = new Operand(OperandType.REGISTER, expression.getRegNum());
            Operation pass = new Operation(OperationType.PASS, func.getCurrBlock());
            pass.setSrcOperand(0, param);
            func.getCurrBlock().appendOper(pass);
        }

        //add call operation
        Operation call = new Operation(OperationType.CALL, func.getCurrBlock());
        Operand funcName= new Operand(OperandType.STRING, id.getData());
        call.setSrcOperand(0, funcName);
        this.regNum = func.getNewRegNum();

        Operand retReg = new Operand(OperandType.MACRO, "RetReg");
        Operand thisReg = new Operand(OperandType.REGISTER, this.regNum);
        Operation move = new Operation(OperationType.ASSIGN, func.getCurrBlock());
        move.setSrcOperand(0, retReg);
        move.setDestOperand(0, thisReg);

        func.getCurrBlock().appendOper(move);
    }
}
