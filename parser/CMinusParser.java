
import parser.CompoundStatement;
import scanner.Token;

package parser;

import javax.lang.model.util.ElementScanner14;

import scanner.CMinusScanner2;
import scanner.*;

import scanner.Token.TokenType;

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
            retProg.addDecl(parseDecl());
        }
        //Remember that program REQURES at least ONE decl; throw an error if there are no decls
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
        } else if (local && scan.viewNextToken().getType() == Token.TokenType.OPEN_PAREN) {
            scan.getNextToken();
            return parseFunDecl1(Token.TokenType.INT, id);
        } else {
            //error
        }
        return null;
    }

    public Declaration parseFunDecl1(Token.TokenType type, Token id) //3
    {
        funDecl retDecl = new funDecl(id, type); //Capitalize this later
        if (scan.viewNextToken().getType() != Token.TokenType.VOID) {
            retDecl.setVoid();
            match(TokenType.CLOSE_PAREN);
        } else {
            while (scan.viewNextToken().getType() != Token.TokenType.CLOSE_PAREN) {
                retDecl.addParam(parseParam());
            }
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
        //while decls addDecl()
        while (scan.viewNextToken().getType() == TokenType.INT) {
            retStmt.addDecl(parseDecl(true));
        }
        while (scan.viewNextToken().getType() == TokenType.CLOSE_CURLY) {
            retStmt.addStmt(parseStatement());
        }
        //while stmt addStmt()
        //How to ensure that all decls come before all stmts? perchance generate the first/follow set? all local decls do have to start with int and no stmts do tho
        return null;
    }

    public Program parseProgram() //6
    {
        return null;
    }

    public Program parseProgram() //7
    {
        return null;
    }

    public Program parseProgram() //8
    {
        return null;
    }

    public Program parseProgram() //9
    {
        return null;
    }

    public Program parseProgram() //10
    {
        return null;
    }

    public Program parseProgram() //11
    {
        return null;
    }

    public Program parseProgram() //12
    {
        return null;
    }

    public Program parseProgram() //13
    {
        return null;
    }

    public Program parseProgram() //14
    {
        return null;
    }

    public Program parseProgram() //15
    {
        return null;
    }

    public Program parseProgram() //16
    {
        return null;
    }

    public Program parseProgram() //17
    {
        return null;
    }

    public Program parseProgram() //18
    {
        return null;
    }

    public Program parseProgram() //19
    {
        return null;
    }

    public Program parseProgram() //20
    {
        return null;
    }

    public Program parseProgram() //21
    {
        return null;
    }

    public Program parseProgram() //22
    {
        return null;
    }

    public Program parseProgram() //23
    {
        return null;
    }

    public Program parseProgram() //24
    {
        return null;
    }

    public int parseNum() //25
    {
        return 0;
    }

    public void match(TokenType t) {
        if (scan.viewNextToken().getType() == t) {
            scan.getNextToken();
        } else {
            //hold an error for this
        }
    }
}
