package parser;

import java.util.ArrayList;
import lowlevel.*;

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

    public String printProgram() {
        //shouldn't have an empty program if the parser caught it right
        //which it should have
        String declListString ="";
        for (Declaration declaration : decls) {
            declListString+=declaration.printDecl("");
            declListString+="\n";
        }
        return declListString;
    }

    public CodeItem genLLcode()
    {
        
        CodeItem head = decls.get(0).genLLcode();
        CodeItem prevDecl = head;
        for(int i = 1; i<decls.size()-1; i++)
        {
            CodeItem declItem = decls.get(i).genLLcode();
            prevDecl.setNextItem(declItem);
            prevDecl=declItem;
        }


        return head;
    }
    

    
}
