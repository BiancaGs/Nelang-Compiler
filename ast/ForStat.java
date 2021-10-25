package ast;

// ForStat ::= "for" Id "in" Expr ".." Expr StatList
public class ForStat extends Stat {

    private String ident;
    private Expr leftExpr;
    private Expr rightExpr;
    private StatList statList;

    public ForStat(String ident, Expr leftExpr, Expr rightExpr, StatList statList) {
        this.ident = ident;
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
        this.statList = statList;
    }

    @Override
    public void genC(PW pw) {
        pw.print("for ( int " + ident + " = ");
        leftExpr.genC(pw);
        pw.print(";");
        pw.print(ident + " <= ");
        rightExpr.genC(pw);
        pw.print(";");
        pw.print(ident + "++");
        pw.print(")");
        statList.genC(pw);
    }

    @Override
    public int run() {
        return statList.run();
    }

}
