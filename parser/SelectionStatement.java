package parser;

import lowlevel.*;

public class SelectionStatement extends Statement {

    private Expression booleanExpression;
    private Statement stmt;
    private Statement elsePart;

    public SelectionStatement() {

    }

    public void addExpr(Expression e) {
        booleanExpression = e;
    }

    public void addStmt(Statement s) {
        stmt = s;
    }

    public void addElsePart(Statement s) {
        elsePart = s;
    }

    public String printStmt(String tabs) {
        String statementString = "";
        statementString+=(tabs + "if\n" + tabs + "(\n");
        statementString+=booleanExpression.printExp(tabs + "    ");
        statementString+=(tabs + ")\n" + tabs + "{\n");
        statementString+=stmt.printStmt(tabs + "    ");
        statementString+=(tabs + "}\n");
        if(elsePart!=null)
        {
            statementString+=(tabs + "else\n" + tabs + "{\n");
            statementString+=elsePart.printStmt(tabs + "    ");
            statementString+=(tabs + "}\n");
        }

        return statementString;
    }

    public void genLLcode(Function func)
    {
        
    }
}
