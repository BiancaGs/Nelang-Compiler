package ast;

// IfStat ::= "if" Expr StatList [ "else" StatList ]
public class IfStat extends Stat {

    private Expr expr;
    private StatList leftStatList;
    private StatList rightStatList;

    public IfStat(Expr expr, StatList leftStatList, StatList rightStatList) {
        this.expr = expr;
        this.leftStatList = leftStatList;
        this.rightStatList = rightStatList;
    }

    @Override
    public void genC(PW pw) {
        pw.print("if (");
        expr.genC(pw);
        pw.print(")");
        leftStatList.genC(pw);
        if (rightStatList != null) {
            pw.print("else ");
            rightStatList.genC(pw);
        }
    }

    @Override
    public int run() {
        return 0;
    }

}
