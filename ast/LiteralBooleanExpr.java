package ast;

public class LiteralBooleanExpr extends Expr {

    public static LiteralBooleanExpr True = new LiteralBooleanExpr(true);
    public static LiteralBooleanExpr False = new LiteralBooleanExpr(false);

    private Boolean value;

    public LiteralBooleanExpr(Boolean value) {
        this.value = value;
    }

    @Override
    public void genC(PW pw) {

        if (value == true) {
            pw.print("1");
        } else {
            pw.print("0");
        }
    }

    @Override
    public int run() {
        return 0;
    }

}
