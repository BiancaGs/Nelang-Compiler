package ast;

// AssignStat ::= Ident "=" Expr ";"
public class AssignStat extends Stat {

    private String ident;
    private Expr expr;

    public AssignStat(String ident, Expr expr) {
        this.ident = ident;
        this.expr = expr;
    }

    @Override
    public void genC(PW pw) {
        pw.print(ident);
        pw.print("= ");
        expr.genC(pw);
        pw.println(";");
    }

    @Override
    public int run() {
        return expr.run();
    }

}
