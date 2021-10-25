package ast;

// Expr ::= AndExpr [ "||" AndExpr ]
abstract public class Expr {
    abstract public void genC(PW pw);
    abstract public int run();
}
