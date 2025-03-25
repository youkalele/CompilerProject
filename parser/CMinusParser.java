package parser;

import scanner.*;

import java.util.Scanner;
import java.util.ArrayList;

import javax.lang.model.util.ElementScanner14;

public class CMinusParser implements Parser { //match() is meant to assert that the given token is the same as the current token AND advance the Token pointer

    private Scanner scan;

    public CMinusParser(String file) {
        scan = new CMinusScanner2(file);
        scan.getNextToken();
    }

    public Program parse() {

        return parseProgram();
    }

    public Program parseProgram() //1
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

    public Declaration parseDecl(boolean local) {
        Declaration retDecl = null;
        if (scan.viewNextToken().getType() == Token.TokenType.INT) {
            scan.getNextToken();
            retDecl = parseDecl1(local); //go to decl'
        } else if (scan.viewNextToken().getType() == Token.TokenType.VOID) {
            match(TokenType.OPEN_PAREN);
            scan.getNextToken();
            retDecl = parseFunDecl1(Token.TokenType.VOID, scan.viewNextToken());
        } else {
            //error
            throw new CMinusParseError("Invalid Grammar: Invalid Declaration");
        }

        return retDecl;
    }

    public Declaration parseDecl1(boolean local) //2
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
            scan.getNextToken();
            return parseFunDecl1(Token.TokenType.INT, id);
        } else {
            //error
            throw new CMinusParseError("Invalid Grammar: Invalid Declaration");
        }
        return null;
    }

    public Declaration parseFunDecl1(Token.TokenType type, Token id) //3
    {
        funDecl retDecl = new funDecl(id, type); //Capitalize this later
        if (scan.viewNextToken().getType() != Token.TokenType.VOID) {
            retDecl.setVoid();
            match(TokenType.CLOSE_PAREN);
        } 
        else {
            for(int i=1; i>0; i--){
                retDecl.addParam(parseParam());
                if(scan.viewNextToken().getType() == Token.TokenType.COMMA)
                    i++;
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

    public Param parseParam() //4
    {
        match(TokenType.INT);
        Token idToken = scan.getNextToken();
        boolean isArr = false;
        if (scan.viewNextToken().getType() == TokenType.OPEN_BRACKET) {
            match(TokenType.CLOSE_BRACKET);
            isArr = true;
        }

        return new Param(idToken, isArr);

    }

    public CompoundStatement parseCompoundStatement() //5
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

    public Statement parseStatement() //6
    {
        switch (scan.viewNextToken().getType()) {
            case TokenType.OPEN_CURLY:
                return parseCompoundStatement();
                break;
            case TokenType.IF:
                return parseSelectionStatement();
                break;
            case TokenType.WHILE:
                return parseIterationStatement();
                break;
            case TokenType.RETURN:
                return parseReturnStatement();
                break;
            default:
                return parseExpressionStatement();
                break;
        }
    }

    public Statement parseSelectionStatement() //7
    {
        Statement retStmt = new SelectionStatement();
        match(TokenType.IF);
        match(TokenType.OPEN_PAREN);
        retStmt.addExpr(parseExpression());
        match(TokenType.CLOSE_PAREN);
        retStmt.addStmt(parseStatement());
        
        if (scan.viewNextToken().getType() == TokenType.ELSE) {
            retStmt.addElsePart(parseStatement());
        }

        return retStmt;
    }

    public Statement parseIterationStatement() //8
    {
        Statement retStmt = new IterationStatement();
        match(TokenType.WHILE);
        match(TokenType.OPEN_PAREN);
        retStmt.addExpr(parseExpression());
        match(TokenType.CLOSE_PAREN);
        retStmt.addStmt(parseStatement());
        return retStmt;
    }

    public Statement parseReturnStatement() //9
    {
        Statement retStmt = new ReturnStatement();
        match(TokenType.RETURN);
        if(scan.viewNextToken().getType()!=TokenType.SEMI)
            retStmt.addExpr(parseExpression());
        match(TokenType.SEMI);
        return retStmt;
    }

    public Statement parseExpressionStatement() //10
    {
        Statement retStmt = new ExpressionStatement();
        if(scan.viewNextToken().getType()!=TokenType.SEMI)
            retStmt.addExpr(parseExpression());
        match(TokenType.SEMI);
        return retStmt;
    }

    public Expression parseExpression() //11
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
    }

    public Expression parseExpression1(Token id) //12
    {
        if(scan.viewNextToken().getType()==TokenType.ASSIGN)
        {
            match(TokenType.ASSIGN);
            return new AssignExpression(id, parseExpression());
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

    public Expression parseSimpleExpression1(Expression lhs) //13
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

    public Expression parseExpression2(Expression lhs) //14
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

    public ArrayList<Expression> parseArgs() //15
    {
        ArrayList<Expression> args = new ArrayList<>();
        if(scan.viewNextToken().getType()!=TokenType.CLOSE_PAREN)
        {
            for(int i = 1; i>0; i--)
            {
                args.add(parseExpression);
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

    }

    public Expression parseAdditiveExpression1(Expression lhs) //16
    {
        if(isMulop(scan.viewNextToken().getType()))
        {
            return parseTerm1(lhs);
        }
        else if(isAddop(scan.viewNextToken()))
        {
            Token opType = scan.getNextToken();
            return new BinaryExpression(lhs, opType, parseTerm());
        }
        else{
            return lhs;
        }
    }

    public Expression parseAdditiveExpression() //17
    {

        Expression lhs = parseTerm();
        if(isAddop(scan.viewNextToken()))
        {
            Token opType = scan.getNextToken();
            return new BinaryExpression(lhs, opType, parseTerm());
        }
        else{
            return lhs;
        }
    }

    public Expression parseTerm1(Expression lhs) //18
    {
        Token opType = scan.getNextToken();

        return new BinaryExpression(lhs, opType, parseFactor());
    }

    public Expression parseTerm() //19
    {
        Expression lhs = parseFactor();
        if(isMulop(scan.viewNextToken()))
        {
            Token opType = scan.getNextToken();
            return new BinaryExpression(lhs, opType, parseFactor());
        }
        else{
            return lhs;
        }

    }

    public Expression parseFactor() //20
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
            return null;//errror
        }
    }

    public Expression parseVarCall(Token id) //21
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
        return 0;
    }

    public void match(TokenType t) {
        if (scan.viewNextToken().getType() == t) {
            scan.getNextToken();
        } else {
            throw new CMinusParseError("Match Error");
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
