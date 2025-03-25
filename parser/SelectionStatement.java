package parser;

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

    public void printSelectionExp(String tabs) {
        booleanExpression.printExp(tabs + "\t");
        stmt.printStmt(tabs + "\t");
        elsePart.printStmt(tabs + "\t");
    }
}
