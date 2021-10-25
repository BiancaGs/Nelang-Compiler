package ast;

import java.util.ArrayList;

// StatList ::=  "{" { Stat } "}"
public class StatList {

    private ArrayList<Stat> statList;

    public StatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }

    public void genC(PW pw) {
        pw.println("{");
        pw.add();
        for (Stat stat : statList) {
            stat.genC(pw);
        }
        pw.sub();
        pw.println("}");
    }

    public int run() {
        return 0;
    }

}
