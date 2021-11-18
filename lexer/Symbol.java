package lexer;

public enum Symbol {

    EOF("eof"), 
    VAR("var"),
    IDENT("Ident"), 
    SEMICOLON(";"), 
    ASSIGN("="), 
    IF("if"), 
    ELSE("else"), 
    FOR("for"),
    IN("in"),
    DOTDOT(".."),
    PRINT("print"), 
    PRINTLN("println"),
    LEFTBRACE("{"),
    RIGHTBRACE("}"),
    WHILE("while"),
    OR("||"), 
    AND("&&"), 
    LEFTPAR("("), 
    RIGHTPAR(")"), 
    NOT("!"),
    LT("<"), 
    LE("<="),
    GT(">"), 
    GE(">="), 
    EQ("=="), 
    NEQ("!="),
    PLUS("+"), 
    MINUS("-"),  
    MULT("*"), 
    DIV("/"), 
    REMAINDER("%"),
    DIGIT("Digit"),
    NUMBER("Number"),
    LITERALSTRING("LiteralString"),
    INT("Int"),
    STRING("String"),
    BOOLEAN("Boolean"),
    TRUE("true"),
    FALSE("false"),
    PLUSPLUS("++");

    Symbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    private String name;

}
