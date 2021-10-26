package ast;

import java.util.ArrayList;

// Program ::= Stat { Stat }
public class Program {

    private ArrayList<Stat> statList;

    public Program(ArrayList<Stat> statList) {
        this.statList = statList;
    }

    public int run() {
        int t = 0;

        for (Stat stat : statList) {
            t += stat.run();
        }

        return t;
    }

    public void genC(PW pw) {
        // // Create C headers
        // pw.println("#include <stdio.h>");
        // pw.println("int main() {");
        // pw.add();

        // // VarList
        // varList.genC(pw);

        // // StatList
        // for (Stat stat : statList) {
        //     stat.genC(pw);
        // }

        // pw.sub();
        // pw.println("}");
    }

}
