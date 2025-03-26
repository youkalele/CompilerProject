
import parser.CMinusParser;
import parser.Program;

public class Main{
    public static void main(String [] args)
    {
        CMinusParser p = new CMinusParser(args[0]);
        //CMinusParser p = new CMinusParser("CompilerProject/scanner/sampleBookTest.c-");

        Program output = p.parse();
        if(output!=null)
            output.printProgram();
    }
}