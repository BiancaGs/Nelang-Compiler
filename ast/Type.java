package ast;

abstract public class Type {
    public Type(String name) {
        this.name = name;
    }

    public static Type booleanType = new TypeBoolean();
    public static Type intType = new TypeInt();
    public static Type stringType = new TypeString();

    public String getName() {
        return name;
    }

    abstract public String getCname();
    abstract public String getCspecifier();

    private String name;
}
