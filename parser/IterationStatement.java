package parser;
public class IterationStatement extends Statement {
    private Expression booleanExpression;
    private Statement stmt;

    public IterationStatement() {

    }

    public void addExpr(Expression e) {
        booleanExpression = e;
    }

    public void addStmt(Statement s) {
        stmt = s;
    }

    public void printIterationStmt(String tabs) {
        booleanExpression.printExp(tabs + "\t");
        stmt.printStmt(tabs + "\t");
    }
}
