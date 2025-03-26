package parser;
import scanner.Token;
public class NumExpression extends Expression{
    private Token num;

    public NumExpression(Token n)
    {
        num=n;
    }

    public String printExp(String tabs) {
        return (tabs + num.getData()+"\n");
    }
}
