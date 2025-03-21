/*
 * Copyright (C) 2025 Matthew Carter & Michael Dabney
 */

/* C- 1.0 language lexer specification */

import java.io.*;


%%                                                                                      //HEAD

%public
%class CMinusScanner2
%implements Scanner
%function scanToken
%type Token
%unicode

%line
%column


%{

    private BufferedReader inFile;
    private Token nextToken;

    public CMinusScanner2(String filename) 
    {
        try 
        {
            this.zzReader = new BufferedReader(new FileReader(filename));
            nextToken = scanToken();
        } catch (IOException e) 
        {
            System.err.println("oopsie your file not there");
        }
        
    }

    public Token getNextToken() 
    {
        try{
        Token returnToken = nextToken;
        if (nextToken.getType() != Token.TokenType.EOF)
            nextToken = scanToken();
        return returnToken;
        }
        catch (IOException e)
        {
            System.err.println("File Error");
            return null;
        }
    }

    public Token viewNextToken() {
        return nextToken;
    }
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]



/* identifiers */
Identifier = [:jletter:]+

/* integer literals */
Number = 0 | [1-9][0-9]*


badToken = ({Number}{Identifier}{Number}*|{Identifier}{Number}{Identifier}*)+

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%state INCOMMENT

%%                                                                              //BODY

<YYINITIAL> {

  "/*"                          { yybegin(INCOMMENT);}

  /* keywords */
  
  "else"                         { return new Token(Token.TokenType.ELSE); }
  "int"                          { return new Token(Token.TokenType.INT); }
  "if"                           { return new Token(Token.TokenType.IF); }
  "return"                       { return new Token(Token.TokenType.RETURN); }
  "void"                         { return new Token(Token.TokenType.VOID); }
  "while"                        { return new Token(Token.TokenType.WHILE); }


  /* separators */
  "("                            { return new Token(Token.TokenType.OPEN_PAREN); }
  ")"                            { return new Token(Token.TokenType.CLOSE_PAREN); }
  "{"                            { return new Token(Token.TokenType.OPEN_CURLY); }
  "}"                            { return new Token(Token.TokenType.CLOSE_CURLY); }
  "["                            { return new Token(Token.TokenType.OPEN_BRACKET); }
  "]"                            { return new Token(Token.TokenType.CLOSE_BRACKET); }
  ";"                            { return new Token(Token.TokenType.SEMI); }
  ","                            { return new Token(Token.TokenType.COMMA); }

  /* operators */
  "="                            { return new Token(Token.TokenType.ASSIGN); }
  ">"                            { return new Token(Token.TokenType.GT); }
  "<"                            { return new Token(Token.TokenType.LT); }
  "=="                           { return new Token(Token.TokenType.EQUALS); }
  "<="                           { return new Token(Token.TokenType.LOET); }
  ">="                           { return new Token(Token.TokenType.GOET); }
  "!="                           { return new Token(Token.TokenType.NOTEQUAL); }
  "+"                            { return new Token(Token.TokenType.PLUS); }
  "-"                            { return new Token(Token.TokenType.MINUS); }
  "*"                            { return new Token(Token.TokenType.TIMES); }
  "/"                            { return new Token(Token.TokenType.DIVIDE); }
  

  
  {badToken} {return new Token(Token.TokenType.ERROR);}
  
  /* numeric literals */
  
  {Number}            { return new Token(Token.TokenType.NUM, yytext()); }

                   

  /* identifiers */
  {Identifier}                   { return new Token(Token.TokenType.ID, yytext()); } 

  /* whitespace */
  {WhiteSpace}                    { /* ignore */ }
}
<INCOMMENT> {
    "*/"    { yybegin(YYINITIAL);}
    [^]  { /* nothing */}
    <<EOF>> { yybegin(YYINITIAL); return new Token(Token.TokenType.ERROR);}
}


/* error fallback */
[^]                              { return new Token(Token.TokenType.ERROR); }

<<EOF>>                          { return new Token(Token.TokenType.EOF); }