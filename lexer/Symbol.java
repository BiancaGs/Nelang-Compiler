package lexer;

public enum Symbol {

    EOF("eof"), 
    VAR("var"),
    INT("Int"),
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
    NUMBER("Number");

    Symbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    private String name;

}
