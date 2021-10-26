package ast;

import java.util.ArrayList;

// VarList ::= { "var" Type Ident ";" }
public class VarList extends Stat {

    private ArrayList<Variable> varList;

    public VarList() {
        varList = new ArrayList<Variable>();
    }

    public void addVar(Variable var) {
        varList.add(var);
    }

    public void genC(PW pw) {
        for (Variable var : varList) {
            pw.print("int ");
            var.genC(pw);
            pw.println(";");
        }
    }

    @Override
    public int run() {
        // TODO Auto-generated method stub
        return 0;
    }

}
