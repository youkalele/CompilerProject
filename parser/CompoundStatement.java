package parser;

import java.util.ArrayList;

public class CompoundStatement extends Statement {
    private ArrayList<Statement> stmts;
    private ArrayList<Declaration> localDecls;

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

    public void printCompoundStmt(String tabs) {
        for (Statement statement : stmts) {
            statement.printStmt(tabs + "\t");
        }
        for (Declaration declaration : localDecls) {
            declaration.printDecl(tabs + "\t");
        }
    }
}
