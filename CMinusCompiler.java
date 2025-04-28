import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import compiler.Compiler;
import lowlevel.CodeItem;
import parser.CMinusParser;
import parser.Program;

public class CMinusCompiler implements Compiler {

    //public static HashMap globalHash = new HashMap(); 

    public CMinusCompiler() {
    }

    @Override
    public void compile(String filePrefix) throws IOException {

        String fileName = filePrefix;
        try {
           CMinusParser parsingBoy = new CMinusParser(fileName);
           Program ast = parsingBoy.parse();
           ast.printProgram();

           CodeItem head = ast.genLLcode();
           File outFileFile = new File(filePrefix + ".ll");
           PrintWriter outfile = new PrintWriter(outFileFile);
           head.printLLCode(outfile);
           outfile.close();

        } catch (IOException ioe) {

        }

    }

    public static void main(String[] args) {
        String filePrefix = args[0];
        CMinusCompiler myCompiler = new CMinusCompiler();
        try {
            myCompiler.compile(filePrefix);
        } catch (IOException e) {
            //dang
        }
    }
}
