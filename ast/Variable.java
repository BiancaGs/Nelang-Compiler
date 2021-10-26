package ast;

// "var" Type Ident ";"
public class Variable {

    private Type type;
    private String ident;
    private int value;

    public Variable(Type type, String ident, int value) {
        this.type = type;
        this.ident = ident;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return ident;
    }

    public int getValue() {
        return value;
    }

    public void genC(PW pw) {
        pw.print(ident);
    }

    public int run() {
        return value;
    }

}
