package ast;

// PrintlnStat ::= "println" Expr ";"
public class PrintlnStat extends Stat {

    private Expr expr;

    public PrintlnStat(Expr expr) {
        this.expr = expr;
    }

    @Override
    public void genC(PW pw) {
        pw.print("printf(\"%d\\n\", ");
        expr.genC(pw);
        pw.println(" );");
    }

    @Override
    public int run() {
        return expr.run();
    }

}
