package ast;

import lexer.*;

public class CompositeExpr extends Expr {

    private Expr left, right;
    private Symbol oper;

    public CompositeExpr(Expr pleft, Symbol poper, Expr pright) {
        this.left = pleft;
        this.oper = poper;
        this.right = pright;
    }

    @Override
    public void genC(PW pw) {
        pw.out.print("(");
        left.genC(pw);
        pw.out.print(" " + oper.toString() + " ");
        right.genC(pw);
        pw.out.print(")");
    }

    @Override
    public int run() {
        int runLeft = left.run();
        int runRight = right.run();

        switch (oper) {
            case PLUS:
                return runLeft + runRight;
            case MINUS:
                return runLeft - runRight;
            case MULT:
                return runLeft * runRight;
            case DIV:
                if (runRight == 0) {
                    throw new Error("Trying to divide by zero");
                }
                return runLeft / runRight;
            case REMAINDER:
                if (runRight == 0) {
                    throw new Error("Trying to divide by zero");
                }
                return runLeft / runRight;
            default:
                return 0;
        }
    }

}