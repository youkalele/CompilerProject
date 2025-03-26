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

    public String printStmt(String tabs) {
        String statementString = tabs+"while\n"+tabs+"(\n";
        statementString+=booleanExpression.printExp(tabs + "    ");
        statementString+=tabs+")\n"+tabs+"{\n";
        statementString+=stmt.printStmt(tabs + "    ");
        statementString+=tabs+"}\n";
        return statementString;
    }
}
