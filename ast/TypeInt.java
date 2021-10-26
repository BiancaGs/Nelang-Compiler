package ast;

public class TypeInt extends Type {
    public TypeInt() {
        super("int");
    }

    public String getCname() {
        return "int";
    }

    @Override
    public String getCspecifier() {
        return "%d";
    }
}
