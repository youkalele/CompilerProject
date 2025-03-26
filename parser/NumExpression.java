package parser;
import scanner.Token;
public class NumExpression extends Expression{
    private Token num;

    public NumExpression(Token n)
    {
        num=n;
    }

    public void printExp(String tabs) {
        System.out.println(tabs + num.getData());
    }
}
