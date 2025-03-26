
import parser.CMinusParser;
import parser.Program;
import java.lang.String;
public class Main{
    public static void main(String [] args)
    {
        CMinusParser p = new CMinusParser(args[0]);
        //CMinusParser p = new CMinusParser("CompilerProject/scanner/sampleBookTest.c-");

        Program output = p.parse();
        if(output!=null)
        {
            String s= output.printProgram();
            System.out.println(s);
        }
    }
}