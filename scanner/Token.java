package scanner;

public class Token {
    enum TokenType {
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
        EOF,
        ERROR,
        COMMENT
    }

    private TokenType tokenType;
    private String tokenData;

    public Token(TokenType type, String data) 
    {
        tokenType = type;
        tokenData = data;
    }
    public Token(TokenType type) 
    {
        this(type, null);
    }
    // some access methods
    public TokenType getType()
    {
        return tokenType;
    }
    public String getData()
    {
        return tokenData;
    }
}
    