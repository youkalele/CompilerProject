package scanner;

import java.io.*;

public class CMinusScanner implements Scanner 
{
    enum st 
    {
        START,
        ERROR,
        INSLASH,
        INASTER,
        INCOMMENT,
        INNUM,
        INID,
        INASSIGN,
        INGT,
        INLT,
        INEXCLAM,
        DONE
    }

    private BufferedReader inFile;
    private Token nextToken;

    public CMinusScanner(String filename) 
    {
        try 
        {
            inFile = new BufferedReader(new FileReader(filename));
            nextToken = scanToken();
        } catch (FileNotFoundException e) 
        {
            System.err.println("oopsie your file not there");
        }
        
    }

    public Token getNextToken() 
    {
        Token returnToken = nextToken;
        if (nextToken.getType() != Token.TokenType.EOF)
            nextToken = scanToken();
        return returnToken;
    }

    public Token viewNextToken() {
        return nextToken;
    }

    private Token scanToken()
    {

        char c;
        //holds the value of the token
        String tokenString="";
        // holds current token to be returned
        Token.TokenType currentToken=Token.TokenType.ERROR;

        st state = st.START;
        // flag to indicate saving character to tokenString
        boolean save;

        while (state != st.DONE) 
        {
            try 
            {   
                this.inFile.mark(1);
                c = (char)this.inFile.read();
                save = true;
                //The body of the finite state machine
                switch (state) 
                {
                    case START:
                        if (Character.isDigit(c)) {
                            state = st.INNUM;
                        }
                        else if (Character.isLetter(c)) {
                            state = st.INID;
                        }
                        else if (c == '=') {
                            state = st.INASSIGN;
                        }
                        else if (c == '<') {
                            state = st.INLT;
                        }
                        else if (c == '>') {
                            state = st.INGT;
                        }
                        else if ((c == ' ') || (c == '\t') || (c == '\n') || (c=='\r')) {
                            save = false;
                        }
                        else if (c == '/') {
                            save = false;
                            state = st.INSLASH;
                        }
                        else if (c == '!') {
                            save = false;
                            state = st.INEXCLAM;
                        }
                        else 
                        {
                            state=st.DONE;
                            save=false;
                            switch (c)
                            {
                                case (char) -1 -> {
                                    save = false;
                                    currentToken = Token.TokenType.EOF;
                                }
                                case '+' -> currentToken = Token.TokenType.PLUS;
                                case ',' -> currentToken = Token.TokenType.COMMA;
                                case '*' -> currentToken = Token.TokenType.TIMES;
                                case '-' -> currentToken = Token.TokenType.MINUS;
                                case '(' -> currentToken = Token.TokenType.OPEN_PAREN;
                                case ')' -> currentToken = Token.TokenType.CLOSE_PAREN;
                                case ';' -> currentToken = Token.TokenType.SEMI;
                                case '[' -> currentToken = Token.TokenType.OPEN_BRACKET;
                                case ']' -> currentToken = Token.TokenType.CLOSE_BRACKET;
                                case '{' -> currentToken = Token.TokenType.OPEN_CURLY;
                                case '}' -> currentToken = Token.TokenType.CLOSE_CURLY;
                                default -> state = st.DONE; //returns error token
                            }
                    //add more special tokens
                        }
                        break;
                    case INASTER:
                        save = false;
                        if (c=='/') 
                        {
                            state = st.DONE;
                            currentToken=Token.TokenType.COMMENT;
                        }
                        else if(c!='*')
                        {
                            state=st.INCOMMENT;
                        }
                        break;
                    case INEXCLAM:
                        save = false;
                        if (c=='=') 
                        {
                            state = st.DONE;
                            currentToken=Token.TokenType.NOTEQUAL;
                        }
                        else
                        {
                            state = st.DONE; //returns error token
                        }
                        break;
                    case INSLASH:
                        if(c=='*')
                        {
                            state = st.INCOMMENT;
                            currentToken=Token.TokenType.COMMENT;
                        }
                        else {
                            this.inFile.reset();
                            state=st.DONE;
                            currentToken=Token.TokenType.DIVIDE;
                        }
                        break;
                    case INCOMMENT:
                        save = false;
                        if (c=='*') 
                            state = st.INASTER;
                        break;
                    case INASSIGN:
                        state = st.DONE;
                        if (c == '=')
                            currentToken = Token.TokenType.EQUALS;
                        else
                        {
                            currentToken = Token.TokenType.ASSIGN;
                        }
                        break;
                    case INGT:
                        state = st.DONE;
                        if (c == '=')
                            currentToken = Token.TokenType.GOET;
                        else
                        {
                            this.inFile.reset();
                            save = false;
                            state = st.DONE; 
                            currentToken = Token.TokenType.GT;
                        }
                        break;
                    case INLT:
                        state = st.DONE;
                        if (c == '=')
                            currentToken = Token.TokenType.LOET;
                        else
                        {
                            this.inFile.reset();
                            save = false;
                            state = st.DONE; 
                            currentToken = Token.TokenType.LT;
                        }
                        break;
                    case INNUM:
                        if(Character.isLetter(c))
                        {
                            state = st.ERROR;
                        }
                        else if (!Character.isDigit(c))
                        {
                            this.inFile.reset();
                            save = false;
                            state = st.DONE; 
                            currentToken=Token.TokenType.NUM;
                        }
                        break;
                    case INID:
                        if(Character.isDigit(c))
                        {
                            state = st.ERROR; //returns error token
                            save=false;
                        }
                        else if(!Character.isLetter(c))
                        {
                            this.inFile.reset();
                            save = false;
                            state=st.DONE;
                            currentToken = Token.TokenType.ID;
                        }
                        break;
                    case ERROR:
                        if(!(Character.isDigit(c)||Character.isLetter(c)))
                        {
                            this.inFile.reset();
                            state=st.DONE;
                        }
                        break;
                    case DONE:
                        throw new Error("DONE state reached prematurely");
                }

                if((save))
                {
                    tokenString+=c;
                }
                if(state == st.DONE)
                {
                    if(currentToken == Token.TokenType.ID)
                    {
                        currentToken=reservedLookup(tokenString);
                        if(currentToken!=Token.TokenType.ID)
                            tokenString="";
                    }
                    else
                        tokenString="";
                }
            }
            catch(Exception e)
            {
                System.err.println("coolio");
            }
        }
        return new Token(currentToken, tokenString);
    }

    
    private static Token.TokenType reservedLookup(String identifier)
    {
        Token.TokenType returnType = Token.TokenType.ID;
        switch (identifier) 
        {
            case "else" -> returnType = Token.TokenType.ELSE;
            case "if" -> returnType = Token.TokenType.IF;
            case "int" -> returnType = Token.TokenType.INT;
            case "return" -> returnType = Token.TokenType.RETURN;
            case "void" -> returnType = Token.TokenType.VOID;
            case "while" -> returnType = Token.TokenType.WHILE;
            default -> {
            }
        }
        return returnType;
    }
}