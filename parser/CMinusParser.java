package parser;

import java.util.ArrayList;

import scanner.CMinusScanner2;
import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;

public class CMinusParser implements Parser { //match() is meant to assert that the given token is the same as the current token AND advance the Token pointer

    private Scanner scan;

    public CMinusParser(String file) {
        scan = new CMinusScanner2(file);
        //scan.getNextToken();
    }

    public Program parse() {


        try{
            return parseProgram();
        }
        catch(CMinusParseError e){
            //recover
            //;
            return new Program();
        }
        
    }

    public Program parseProgram() throws CMinusParseError //1
    {
        Program retProg = new Program();
        //get first token
        while (scan.viewNextToken().getType() != Token.TokenType.EOF) {
            retProg.addDecl(parseDecl(false));
        }
        //Remember that program REQURES at least ONE decl; throw an error if there are no decls
        if (retProg.getDecls().isEmpty()) {
            throw new CMinusParseError("Programs must contain at least one declaration.");
        }
        return retProg;

    }

    public Declaration parseDecl(boolean local) throws CMinusParseError {
        Declaration retDecl = null;
        if (scan.viewNextToken().getType() == Token.TokenType.INT) {
            scan.getNextToken();
            retDecl = parseDecl1(local); //go to decl'
        } else if (scan.viewNextToken().getType() == Token.TokenType.VOID) {
            scan.getNextToken();
            retDecl = parseFunDecl1(Token.TokenType.VOID, scan.getNextToken());
        } else {
            //error
            throw new CMinusParseError("Invalid Grammar: Invalid Declaration");
        }

        return retDecl;
    }

    public Declaration parseDecl1(boolean local) throws CMinusParseError  //2
    {
        Token id = scan.getNextToken();
        //descend into madness
        if (scan.viewNextToken().getType() == Token.TokenType.SEMI) {
            scan.getNextToken();
            return new VarDecl(id, -1);
        } else if (scan.viewNextToken().getType() == Token.TokenType.OPEN_BRACKET) {
            int arrayVal = -1;
            scan.getNextToken();
            arrayVal = parseNum();
            match(TokenType.CLOSE_BRACKET);
            match(TokenType.SEMI);
            return new VarDecl(id, arrayVal);//constructor needs to handle either array or not array
        } else if (!local && scan.viewNextToken().getType() == Token.TokenType.OPEN_PAREN) {
            return parseFunDecl1(Token.TokenType.INT, id);
        } else {
            //error
            throw new CMinusParseError("Invalid Grammar: Invalid Declaration");
        }
    }

    public Declaration parseFunDecl1(Token.TokenType type, Token id) throws CMinusParseError  //3
    {
        match(TokenType.OPEN_PAREN);
        funDecl retDecl = new funDecl(id, type); //Capitalize this later
        if (scan.viewNextToken().getType() == Token.TokenType.VOID) {
            scan.getNextToken();
            retDecl.setVoid();
            match(TokenType.CLOSE_PAREN);
        } 
        else {
            for(int i=1; i>0; i--){
                retDecl.addParam(parseParam());
                if(scan.viewNextToken().getType() == Token.TokenType.COMMA) {
                    i++;
                    scan.getNextToken();
                }
                else if(scan.viewNextToken().getType() != Token.TokenType.CLOSE_PAREN)
                    {//error non-delimited params
                        throw new CMinusParseError("Invalid Grammar: Non Delimited Parameters");
                    }
            }
            match(TokenType.CLOSE_PAREN);
        }

        retDecl.setBody(parseCompoundStatement());

        return retDecl;
    }

    public Param parseParam() throws CMinusParseError //4
    {
        match(TokenType.INT);
        Token idToken = scan.getNextToken();
        boolean isArr = false;
        if (scan.viewNextToken().getType() == TokenType.OPEN_BRACKET) {
            scan.getNextToken();
            match(TokenType.CLOSE_BRACKET);
            isArr = true;
        }

        return new Param(idToken, isArr);

    }

    public CompoundStatement parseCompoundStatement() throws CMinusParseError //5
    {
        match(TokenType.OPEN_CURLY);

        CompoundStatement retStmt = new CompoundStatement();
        while (scan.viewNextToken().getType() == TokenType.INT) {
            retStmt.addDecl(parseDecl(true));
        }
        while (scan.viewNextToken().getType() != TokenType.CLOSE_CURLY) {
            retStmt.addStmt(parseStatement());
        }

        match(TokenType.CLOSE_CURLY);

        return retStmt;
    }

    public Statement parseStatement() throws CMinusParseError //6
    {
        switch (scan.viewNextToken().getType()) {
            case OPEN_CURLY:
                return parseCompoundStatement();
            case IF:
                return parseSelectionStatement();
            case WHILE:
                return parseIterationStatement();
            case RETURN:
                return parseReturnStatement();
            default:
                return parseExpressionStatement();
        }
    }

    public Statement parseSelectionStatement() throws CMinusParseError //7
    {
        SelectionStatement retStmt = new SelectionStatement();
        match(TokenType.IF);
        match(TokenType.OPEN_PAREN);
        retStmt.addExpr(parseExpression());
        match(TokenType.CLOSE_PAREN);
        retStmt.addStmt(parseStatement());
        
        if (scan.viewNextToken().getType() == TokenType.ELSE) {
            scan.getNextToken();
            retStmt.addElsePart(parseStatement());
        }

        return retStmt;
    }

    public Statement parseIterationStatement() throws CMinusParseError //8
    {
        IterationStatement retStmt = new IterationStatement();
        match(TokenType.WHILE);
        match(TokenType.OPEN_PAREN);
        retStmt.addExpr(parseExpression());
        match(TokenType.CLOSE_PAREN);
        retStmt.addStmt(parseStatement());
        return retStmt;
    }

    public Statement parseReturnStatement() throws CMinusParseError //9
    {
        ReturnStatement retStmt = new ReturnStatement();
        match(TokenType.RETURN);
        if(scan.viewNextToken().getType()!=TokenType.SEMI)
            retStmt.addExpr(parseExpression());
        match(TokenType.SEMI);
        return retStmt;
    }

    public Statement parseExpressionStatement() throws CMinusParseError //10
    {
        ExpressionStatement retStmt = new ExpressionStatement();
        if(scan.viewNextToken().getType()!=TokenType.SEMI)
            retStmt.addExpr(parseExpression());
        match(TokenType.SEMI);
        return retStmt;
    }

    public Expression parseExpression() throws CMinusParseError //11
    {
        if(scan.viewNextToken().getType()==TokenType.ID)
        {
            Token id=scan.getNextToken();
            return parseExpression1(id);
        }
        else if(scan.viewNextToken().getType()==TokenType.NUM)
        {
            Token num = scan.getNextToken();
            return parseSimpleExpression1(new NumExpression(num));
        }
        else if(scan.viewNextToken().getType()==TokenType.OPEN_PAREN)
        {
            match(TokenType.OPEN_PAREN);
            Expression e = parseExpression();
            match(TokenType.CLOSE_PAREN);

            return parseSimpleExpression1(e);      
        }
        return null;
    }

    public Expression parseExpression1(Token id) throws CMinusParseError //12
    {
        if(scan.viewNextToken().getType()==TokenType.ASSIGN)
        {
            match(TokenType.ASSIGN);
            return new AssignExpression(new VarExpression(id), parseExpression());
        }
        else if(scan.viewNextToken().getType()==TokenType.OPEN_BRACKET)
        {
            //varExpression
            match(TokenType.OPEN_BRACKET);
            Expression e = parseExpression();
            match(TokenType.CLOSE_BRACKET);
            return parseExpression2(new VarExpression(id, e));
        }
        else if(scan.viewNextToken().getType()==TokenType.OPEN_PAREN)
        {
            match(TokenType.OPEN_PAREN);
            Expression e = new CallExpression(id, parseArgs());
            match(TokenType.CLOSE_PAREN);

            return parseSimpleExpression1(e);

        }
        else
        {
            return parseSimpleExpression1(new VarExpression(id));
        }
    }

    public Expression parseSimpleExpression1(Expression lhs) throws CMinusParseError  //13
    {
        //numexpression
        Expression newLHS = parseAdditiveExpression1(lhs);
        
        if(isRelop(scan.viewNextToken()))
        {
            Token opType = scan.getNextToken();
            Expression rhs = parseAdditiveExpression();
            return new BinaryExpression(lhs, opType, rhs);
        }
        else
        {
            return newLHS;
        }

    }

    public Expression parseExpression2(VarExpression lhs) throws CMinusParseError //14
    {
        if(scan.viewNextToken().getType()==TokenType.ASSIGN)
        {
            match(TokenType.ASSIGN);
            return new AssignExpression(lhs, parseExpression());
        }
        else
        {
            return parseSimpleExpression1(lhs);
        }
    }

    public ArrayList<Expression> parseArgs() throws CMinusParseError //15
    {
        ArrayList<Expression> args = new ArrayList<>();
        if(scan.viewNextToken().getType()!=TokenType.CLOSE_PAREN)
        {
            for(int i = 1; i>0; i--)
            {
                args.add(parseExpression());
                if(scan.viewNextToken().getType()==TokenType.COMMA)
                {
                    i++;
                    match(TokenType.COMMA);
                }
                else if(scan.viewNextToken().getType()!=TokenType.CLOSE_PAREN)
                {
                    //error non delimited args
                    throw new CMinusParseError("Invalid grammar: Non Delimited Arguments");
                }
            }

            return args;
            
        }
        return null;

    }

    public Expression parseAdditiveExpression1(Expression lhs) throws CMinusParseError  //16
    {
       
        Expression newLHS = parseTerm1(lhs);

        while(isAddop(scan.viewNextToken()))
        {
            Token opType = scan.getNextToken();
            newLHS= new BinaryExpression(newLHS, opType, parseTerm());
        }
         return newLHS;
    }

    public Expression parseAdditiveExpression() throws CMinusParseError //17
    {
        Expression newLHS = parseTerm();
        while(isAddop(scan.viewNextToken()))
        {
            Token opType = scan.getNextToken();
            newLHS = new BinaryExpression(newLHS, opType, parseTerm());
        }
        return newLHS;
    }

    public Expression parseTerm1(Expression lhs) throws CMinusParseError  //18
    {
        Expression newLHS = lhs;
        while(isMulop(scan.viewNextToken()))
        {
            Token opType = scan.getNextToken();
            newLHS = new BinaryExpression(newLHS, opType, parseFactor());
        }
        return newLHS;
    }

    public Expression parseTerm() throws CMinusParseError  //19
    { 
        Expression lhs = parseFactor(); //parseFactor returns an Expression
        while(isMulop(scan.viewNextToken())) {
            lhs = new BinaryExpression(lhs, scan.getNextToken(), parseFactor());
        }
        return lhs;
    }

    public Expression parseFactor() throws CMinusParseError //20
    {
        if(scan.viewNextToken().getType() == TokenType.ID)
        {
            Token id = scan.getNextToken();
            return parseVarCall(id);
        }
        else if(scan.viewNextToken().getType() == TokenType.NUM)
        {
            return new NumExpression(scan.getNextToken());
        }
        else if(scan.viewNextToken().getType() == TokenType.OPEN_PAREN)
        {
            match(TokenType.OPEN_PAREN);
            Expression e = parseExpression();
            match(TokenType.CLOSE_PAREN);
            return e;
        }
        else
        {
            throw new CMinusParseError("Invalid Grammar: Invalid Factor");
        }
    }

    public Expression parseVarCall(Token id) throws CMinusParseError //21
    {
        if(scan.viewNextToken().getType() == TokenType.OPEN_BRACKET)
        {
            match(TokenType.OPEN_BRACKET);
            Expression e = parseExpression();
            match(TokenType.CLOSE_BRACKET);
            return new VarExpression(id, e);
        }
        else if(scan.viewNextToken().getType() == TokenType.OPEN_PAREN)
        {
            match(TokenType.OPEN_PAREN);
            Expression e = parseExpression();
            match(TokenType.CLOSE_PAREN);
            return new CallExpression(id, parseArgs()); 
        }
        else
        {
            return new VarExpression(id);
        }
    }

    public int parseNum() //25
    {
        return Integer.parseInt(scan.getNextToken().getData());
    }

    public void match(TokenType t) throws CMinusParseError {
        if (scan.viewNextToken().getType() == t) {
            scan.getNextToken();
        } else {
            throw new CMinusParseError("Match Error: expected "+t.name());
            //hold an error for this
        }
    }
    public boolean isBinOp(Token op)
    {
        return isRelop(op)||isAddop(op)||isMulop(op);
        
    }
    public boolean isRelop(Token op)
    {
        return op.getType()==TokenType.GOET || op.getType()==TokenType.GT 
            || op.getType()==TokenType.LOET || op.getType()==TokenType.LT 
            || op.getType()==TokenType.EQUALS || op.getType()==TokenType.NOTEQUAL;
    }
    public boolean isAddop(Token op)
    {
        return op.getType()==TokenType.PLUS || op.getType()==TokenType.MINUS;
    }
    public boolean isMulop(Token op)
    {
        return op.getType()==TokenType.TIMES || op.getType()==TokenType.DIVIDE;  
    }


}
