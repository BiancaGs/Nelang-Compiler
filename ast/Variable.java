package ast;

// "var" Int Ident ";"
public class Variable {

    private String ident;
    private int value;

    public Variable(String ident, int value) {
        this.ident = ident;
        this.value = value;
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
