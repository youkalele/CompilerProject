

import java.lang.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class projOne {

    enum st {
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
    enum tt {
    ID,
    ELSE,
    IF,
    INT,
    RETURN,
    VOID,
    WHILE,
    NUM,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    LT,
    LOET,
    GT,
    GOET,
    EQUALS,
    NOTEQUAL,
    ASSIGN,
    SEMI,
    COMMA,
    OPEN_CURLY,
    CLOSE_CURLY,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPEN_PAREN,
    CLOSE_PAREN,
    COMMENT,
    EOF,
    ERROR
    }
    
    public static void main(String[] args) { 
        

        try{
            String sampleBookTest = Files.readString(Paths.get("sampleBookTest.c-"), Charset.forName("UTF-8"));
            String sampleMasterTest = Files.readString(Paths.get("sampleMasterTest.c-"), Charset.forName("UTF-8"));
            String sample3aError = Files.readString(Paths.get("sample3aError.c-"), Charset.forName("UTF-8"));
            String sampleNot = Files.readString(Paths.get("sampleNot.c-"), Charset.forName("UTF-8"));
            String sampleComment = Files.readString(Paths.get("sampleComment.c-"), Charset.forName("UTF-8"));
            String sampleEmpty = Files.readString(Paths.get("sampleEmpty.c-"), Charset.forName("UTF-8"));

            printTokenList(sampleBookTest);
            printTokenList(sampleMasterTest);
            printTokenList(sample3aError);
            printTokenList(sampleNot);
            printTokenList(sampleComment);
            printTokenList(sampleEmpty);

            outputTokenList(sampleBookTest, "sampleBookTest.compiled");
            outputTokenList(sampleMasterTest, "sampleMasterTest.compiled");
            outputTokenList(sample3aError, "sample3aError.compiled");
            outputTokenList(sampleNot, "sampleNot.compiled");
            outputTokenList(sampleComment, "sampleComment.compiled");
            outputTokenList(sampleEmpty, "sampleEmpty.compiled");
        }
        catch(Exception e)
        {
            System.err.print("file not found");
        }
        
        

    }

    static tt reservedLookup(String identifier) {
        tt returnType = tt.ID;
        switch (identifier) {
            case "else" -> returnType = tt.ELSE;
            case "if" -> returnType = tt.IF;
            case "int" -> returnType = tt.INT;
            case "return" -> returnType = tt.RETURN;
            case "void" -> returnType = tt.VOID;
            case "while" -> returnType = tt.WHILE;
            default -> {
            }
        }
        return returnType;
    }

    static void printTokenList(String fileString) {
        List<Pair<tt, String>> list = getTokens(fileString);
        Pair<tt, String> temp;
        while (!list.isEmpty()) { 
            temp = list.remove(0);
            System.out.print(temp.getTokenType());
            if (temp.getTokenType() == tt.ID || temp.getTokenType() == tt.NUM) {
                System.out.print("(" + temp.getData() + ")");
            }
            if (temp.getTokenType() != tt.EOF) {
                System.out.print(", ");
            }
        }
        System.out.println("\n"); //yes this intentionally prints 2 new lines, it looks nicer
    }

    static void outputTokenList(String fileString, String outfileName) {
        try {
            List<Pair<tt, String>> list = getTokens(fileString);
            Pair<tt, String> temp;
            String output = "";
            Path newPath = Paths.get(outfileName);
            while (!list.isEmpty()) { 
                temp = list.remove(0);
                output += temp.getTokenType().name();
                if (temp.getTokenType() == tt.ID || temp.getTokenType() == tt.NUM) {
                    output += "(" + temp.getData() + ")";
                }
                if (temp.getTokenType() != tt.EOF) {
                    output += " ";
                }
            }
            Files.writeString(newPath, output);
        } catch(Exception e) {
            System.err.print("file not found");
        }
    }
    
    static List<Pair<tt, String>> getTokens(String code) 
    {
        //The list to store the tokens
        List<Pair<tt, String>> li = new ArrayList<>();
        //holds the value of the token
        String tokenString="";
        // holds current token to be returned
        tt currentToken=tt.ERROR;

        st state = st.START;

        // flag to indicate saving character to tokenString
        boolean save;
        
        //ensure there is more character to read
        if(code.isBlank()) {
            li.add(new Pair(tt.EOF, ""));
            return li;
        }

        while (state != st.DONE) 
        {

            char c;
            if(code.isEmpty())
                c=' ';
            else
            {
                c = code.charAt(0);
                code=code.substring(1);
            }
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
                    else {
                        state=st.DONE;
                        save=false;
                        switch (c)
                        {
                            case (char) -1 -> {
                                save = false;
                                currentToken = tt.EOF;
                            }
                            case '+' -> currentToken = tt.PLUS;
                            case ',' -> currentToken = tt.COMMA;
                            case '*' -> currentToken = tt.TIMES;
                            case '-' -> currentToken = tt.MINUS;
                            case '(' -> currentToken = tt.OPEN_PAREN;
                            case ')' -> currentToken = tt.CLOSE_PAREN;
                            case ';' -> currentToken = tt.SEMI;
                            case '[' -> currentToken = tt.OPEN_BRACKET;
                            case ']' -> currentToken = tt.CLOSE_BRACKET;
                            case '{' -> currentToken = tt.OPEN_CURLY;
                            case '}' -> currentToken = tt.CLOSE_CURLY;
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
                        currentToken=tt.COMMENT;
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
                        currentToken=tt.NOTEQUAL;
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
                        currentToken=tt.COMMENT;
                    }
                    else {
                        code=c+code;
                        state=st.DONE;
                        currentToken=tt.DIVIDE;
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
                        currentToken = tt.EQUALS;
                    else
                    {
                        currentToken = tt.ASSIGN;
                    }
                    break;
                case INGT:
                    state = st.DONE;
                    if (c == '=')
                        currentToken = tt.GOET;
                    else
                    {
                        code=c+code;
                        save = false;
                        state = st.DONE; 
                        currentToken = tt.GT;
                    }
                    break;
                case INLT:
                    state = st.DONE;
                    if (c == '=')
                        currentToken = tt.LOET;
                    else
                    {
                        code=c+code;
                        save = false;
                        state = st.DONE; 
                        currentToken = tt.LT;
                    }
                    break;
                case INNUM:
                    if(Character.isLetter(c))
                    {
                        state = st.ERROR;
                    }
                    else if (!Character.isDigit(c))
                    {
                        code=c+code;
                        save = false;
                        state = st.DONE; 
                        currentToken=tt.NUM;
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
                        code=c+code;
                        save = false;
                        state=st.DONE;
                        currentToken = tt.ID;
                    }
                    break;
                case ERROR:
                    if(!(Character.isDigit(c)||Character.isLetter(c)))
                    {
                        code=c+code;
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
                if(currentToken == tt.ID)
                {
                    currentToken=reservedLookup(tokenString);
                    if(currentToken!=tt.ID)
                        tokenString="";
                }
                else
                    tokenString="";
            }
        }

        //recursive call
        Pair p = new Pair(currentToken, tokenString);
        li.add(p);
        li.addAll(getTokens(code));
        return li;
    }
}