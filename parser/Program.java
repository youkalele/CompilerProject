package parser;

import java.util.ArrayList;

public class Program {
    private ArrayList<Declaration> decls = new ArrayList<Declaration>();

    public Program() {
        
    }

    public void addDecl(Declaration d)
    {
        decls.add(d);
    }

    public ArrayList<Declaration> getDecls() {
        return decls;
    }

    public void printProgram() {
        //shouldn't have an empty program if the parser caught it right
        //which it should have
        for (Declaration declaration : decls) {
            declaration.printDecl("");
        }
    }
    

    
}
