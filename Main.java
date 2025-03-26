
import parser.CMinusParser;
import parser.Program;

import java.io.FileWriter;
import java.io.IOException;
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
            //*
            try 
            {
                FileWriter fileout = new FileWriter(args[0] + ".parsed");
                fileout.write(s);
                fileout.close();;
            } catch (IOException e) 
            {
                System.err.println("oopsie your file not there");
            }
            //*/
            System.out.println(args[0] + ".parsed");
        }
    }
}