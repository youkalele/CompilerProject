package parser;

import lowlevel.Function;

public abstract class Expression {

    protected int regNum;

    public int getRegNum()
    {
        return regNum;
    }
    public void setRegNum(int num)
    {
        regNum=num;
    }
    public String printExp(String tabs) {
        return "";
    }

    public void genLLcode(Function func)
    {

    }
}
