package parser;

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

    public void printStmt(String tabs) {
        for (Statement statement : stmts) {
            statement.printStmt(tabs + "    ");
        }
        for (Declaration declaration : localDecls) {
            declaration.printDecl(tabs + "    ");
        }
    }
}
