package parser;

import lowlevel.*;
import java.util.ArrayList;

public class CompoundStatement extends Statement {
    private ArrayList<Statement> stmts = new ArrayList<Statement>();
    private ArrayList<Declaration> localDecls = new ArrayList<Declaration>();

    public CompoundStatement()
    {

    }

    public void addDecl(Declaration s)
    {
        localDecls.add(s);
    }

    public void addStmt(Statement s)
    {
        stmts.add(s);
    }

    public String printStmt(String tabs) {
        String statementString="";
        for (Declaration declaration : localDecls) {
            statementString+=declaration.printDecl(tabs);
            statementString+=(tabs + "    ;\n");
        }
        for (Statement statement : stmts) {
            statementString+=statement.printStmt(tabs);
        }

        return statementString;
    }

    public void genLLcode(Function func)
    {
        for(Declaration d : localDecls)
        { 
            
            
        }
        for(Statement s : stmts)
        {
            s.genLLcode(func);
        }
    }
}
