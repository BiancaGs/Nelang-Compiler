package ast;

// PrintStat ::= "print" Expr ";"
public class PrintStat extends Stat {

    private Expr expr;

    public PrintStat(Expr expr) {
        this.expr = expr;
    }

    @Override
    public void genC(PW pw) {
        pw.print("printf(\"%d\", ");
        expr.genC(pw);
        pw.print(" );");
    }

    @Override
    public int run() {
        return expr.run();
    }

}
