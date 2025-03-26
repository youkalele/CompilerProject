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

    public void printStmt(String tabs) {
        System.out.println(tabs + "if\n" + tabs + "(");
        booleanExpression.printExp(tabs + "    ");
        System.out.println(tabs + ")\n" + tabs + "{");
        stmt.printStmt(tabs + "    ");
        System.out.println(tabs + "}");
        if(elsePart!=null)
        {
            System.out.println(tabs + "else\n" + tabs + "{");
            elsePart.printStmt(tabs + "    ");
            System.out.println(tabs + "}");
        }
    }
}
