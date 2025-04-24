import compiler.*;
import parser.*;
import lowlevel.*;
import java.io.IOException;
import java.io.FileWriter;

public class CMinusCompiler implements Compiler {


    public CMinusCompiler() {
    }

    public void compile(String filePrefix) {

        String fileName = filePrefix + ".c-";
        try {
           CMinusParser parsingBoy = new CMinusParser(fileName);
           Program ast = parsingBoy.parse();

           CodeItem head = ast.genLLcode(); //FIXME starting at program and going down the tree: returns the head of the linked list

        } catch (Exception ioe) {

        }

    }

    public static void main(String[] args) {
        String filePrefix = args[1];
        CMinusCompiler myCompiler = new CMinusCompiler();
        myCompiler.compile(filePrefix);
    }
}
