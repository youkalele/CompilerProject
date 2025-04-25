package parser;
import scanner.Token;
import lowlevel.*;
public class NumExpression extends Expression{
    private Token num;

    public NumExpression(Token n)
    {
        num=n;
    }

    public String printExp(String tabs) {
        return (tabs + num.getData()+"\n");
    }

    public CodeItem genLLCode() {
        //probably don't have to do much
        //you could assign yourself to a register,  or let parent handle
    }
}
