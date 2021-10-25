package ast;

// WhileStat ::= "while" Expr StatList
public class WhileStat extends Stat {

    private Expr expr;
    private StatList statList;

    public WhileStat(Expr expr, StatList statList) {
        this.expr = expr;
        this.statList = statList;
    }

    @Override
    public void genC(PW pw) {
        pw.print("while ( ");
        expr.genC(pw);
        pw.print(")");
        statList.genC(pw);
    }

    @Override
    public int run() {
        return statList.run();
    }

}
