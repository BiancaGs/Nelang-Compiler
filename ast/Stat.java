package ast;

// Stat ::=  AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
abstract public class Stat {
    abstract public void genC(PW pw);
    abstract public int run();
}
