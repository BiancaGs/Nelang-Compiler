package ast;

public class TypeString extends Type {
    public TypeString() {
        super("String");
    }

    public String getCname() {
        return "char*";
    }

    @Override
    public String getCspecifier() {
        return "%s";
    }
}
