import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import ast.*;
import errorHandling.CompilerError;
import lexer.*;

// Program ::= VarList { Stat }
// VarList ::= { "var" Int Ident ";" }
// Stat ::=  AssignStat | IfStat | ForStat | PrintStat |
//             PrintlnStat | WhileStat
// AssignStat ::= Ident "=" Expr ";"
// IfStat ::= "if" Expr StatList [ "else" StatList ]
// ForStat ::= "for" Id "in" Expr ".." Expr StatList
// PrintStat ::= "print" Expr ";"
// PrintlnStat ::= "println" Expr ";"
// StatList ::=  "{" { Stat } "}"
// WhileStat ::= "while" Expr StatList
// Expr ::= AndExpr [ "||" AndExpr ]
// AndExpr ::= RelExpr [ "&&" RelExpr ]
// RelExpr ::= AddExpr [ RelOp AddExpr ]
// AddExpr ::= MultExpr { AddOp MultExpr }
// MultExpr ::= SimpleExpr { MultOp SimpleExpr }
// SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr
//                 | AddOp SimpleExpr | Ident
// RelOp ::= ’<’ | ’<=’ | ’>’ | ’>=’| ’==’ | ’!=’
// AddOp ::= ’+’| ’-’
// MultOp ::= ’*’ | ’/’ | '%’
// Number ::= [’+’|’-’] Digit { Digit }

public class Compiler {

    private Hashtable<String, Object> symbolTable;
    private CompilerError error;
    private Lexer lexer;

    public Program compile(char[] input, PrintWriter outError, PW pw) {

        symbolTable = new Hashtable<>();
        error = new CompilerError(outError);
        lexer = new Lexer(input, error);
        error.setLexer(lexer);

        lexer.nextToken();

        return program();
    }

    // Program ::= VarList { Stat }
    private Program program() {

        VarList varList = varList();

        // Stat ::= AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
        ArrayList<Stat> statList = new ArrayList<>();

        while (lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || lexer.token == Symbol.FOR
                || lexer.token == Symbol.PRINT || lexer.token == Symbol.PRINTLN || lexer.token == Symbol.WHILE) {
            Stat stat = stat();
            statList.add(stat);
        }

        return new Program(varList, statList);
    }

    // VarList ::= { "var" Int Ident ";" }
    private VarList varList() {

        VarList varList = new VarList();

        while (lexer.token == Symbol.VAR) {
            lexer.nextToken();
            Variable var = variable();

            if (symbolTable.get(var.getName()) != null) {
                error.signal("Variable '" + var.getName() + "' already declared");
            } else {
                symbolTable.put(var.getName(), var);
            }

            varList.addVar(var);
        }

        return varList;
    }

    // Stat ::= AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
    // - All above expressions classes will extend Stat
    private Stat stat() {

        Stat stat = null;

        if (lexer.token == Symbol.IDENT) {
            stat = assignStat();
        } else if (lexer.token == Symbol.IF) {
            stat = ifStat();
        } else if (lexer.token == Symbol.FOR) {
            stat = forStat();
        } else if (lexer.token == Symbol.PRINT) {
            stat = printStat();
        } else if (lexer.token == Symbol.PRINTLN) {
            stat = printlnStat();
        } else if (lexer.token == Symbol.WHILE) {
            stat = whileStat();
        }

        return stat;
    }

    // WhileStat ::= "while" Expr StatList
    private WhileStat whileStat() {

        lexer.nextToken();

        Expr expr = expr();

        StatList statList = statList();

        return new WhileStat(expr, statList);
    }

    // PrintlnStat ::= "println" Expr ";"
    private PrintlnStat printlnStat() {

        lexer.nextToken();

        Expr expr = expr();

        if (lexer.token != Symbol.SEMICOLON) {
            error.signal("; expected");
        }
        lexer.nextToken();

        return new PrintlnStat(expr);
    }

    // PrintStat ::= "print" Expr ";"
    private PrintStat printStat() {

        lexer.nextToken();

        Expr expr = expr();

        if (lexer.token != Symbol.SEMICOLON) {
            error.signal("; expected");
        }
        lexer.nextToken();

        return new PrintStat(expr);
    }

    // IfStat ::= "if" Expr StatList [ "else" StatList ]
    private IfStat ifStat() {

        lexer.nextToken();

        Expr expr = expr();

        StatList leftStatList = statList();

        StatList rightStatList = null;

        if (lexer.token == Symbol.ELSE) {
            lexer.nextToken();
            rightStatList = statList();
        }

        return new IfStat(expr, leftStatList, rightStatList);
    }

    // ForStat ::= "for" Id "in" Expr ".." Expr StatList
    private ForStat forStat() {

        lexer.nextToken();

        if (lexer.token != Symbol.IDENT) {
            error.signal("Identifier expected");
        }

        String ident = lexer.getStringValue();
        lexer.nextToken();

        // Test if variable was already declared        
        if (symbolTable.get(ident) != null) {
            error.signal("Variable '" + ident + "' already declared");
        } else {
            symbolTable.put(ident, new Variable(ident, 0));
        }

        if (lexer.token != Symbol.IN) {
            error.signal("in expected");
        }
        lexer.nextToken();

        Expr leftExpr = expr();

        if (lexer.token != Symbol.DOTDOT) {
            error.signal(".. expected");
        }
        lexer.nextToken();

        Expr rightExpr = expr();

        StatList statList = statList();

        symbolTable.remove(ident);

        return new ForStat(ident, leftExpr, rightExpr, statList);
    }

    // StatList ::= "{" { Stat } "}"
    private StatList statList() {

        if (lexer.token != Symbol.LEFTBRACE) {
            error.signal("{ expected");
        }
        lexer.nextToken();

        // Stat ::= AssignStat | IfStat | ForStat | PrintStat | PrintlnStat | WhileStat
        ArrayList<Stat> statList = new ArrayList<>();

        while (lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || lexer.token == Symbol.FOR
                || lexer.token == Symbol.PRINT || lexer.token == Symbol.PRINTLN || lexer.token == Symbol.WHILE) {
            Stat stat = stat();
            statList.add(stat);
        }

        if (lexer.token != Symbol.RIGHTBRACE) {
            error.signal("} expected");
        }
        lexer.nextToken();

        return new StatList(statList);
    }

    // AssignStat ::= Ident "=" Expr ";"
    private AssignStat assignStat() {

        String ident = lexer.getStringValue();
        lexer.nextToken();

        // Test if variable was declared
        if (symbolTable.get(ident) == null) {
            error.signal("Variable '" + ident + "' was not declared");
        }

        if (lexer.token != Symbol.ASSIGN) {
            error.signal("= expected");
        }
        lexer.nextToken();

        Expr expr = expr();

        if (lexer.token != Symbol.SEMICOLON) {
            error.signal("; expected");
        }
        lexer.nextToken();

        return new AssignStat(ident, expr);
    }

    // Expr ::= AndExpr [ "||" AndExpr ]
    private Expr expr() {
        Expr leftExpr, rightExpr;

        leftExpr = andExpr();

        if (lexer.token == Symbol.OR) {
            lexer.nextToken();
            rightExpr = andExpr();

            leftExpr = new CompositeExpr(leftExpr, Symbol.OR, rightExpr);
        }

        return leftExpr;
    }

    // AndExpr ::= RelExpr [ "&&" RelExpr ]
    private Expr andExpr() {
        Expr leftExpr, rightExpr;

        leftExpr = relExpr();

        if (lexer.token == Symbol.AND) {
            lexer.nextToken();
            rightExpr = relExpr();

            leftExpr = new CompositeExpr(leftExpr, Symbol.AND, rightExpr);
        }

        return leftExpr;
    }

    // RelExpr ::= AddExpr [ RelOp AddExpr ]
    // RelOp ::= ’<’ | ’<=’ | ’>’ | ’>=’| ’==’ | ’!=’
    private Expr relExpr() {
        Expr leftExpr, rightExpr;

        leftExpr = addExpr();

        if (lexer.token == Symbol.LT || lexer.token == Symbol.LE || lexer.token == Symbol.GT || lexer.token == Symbol.GE
                || lexer.token == Symbol.EQ || lexer.token == Symbol.NEQ) {
            Symbol op = lexer.token;
            lexer.nextToken();
            rightExpr = addExpr();

            leftExpr = new CompositeExpr(leftExpr, op, rightExpr);
        }

        return leftExpr;
    }

    // AddExpr ::= MultExpr { AddOp MultExpr }
    // AddOp ::= ’+’| ’-’
    private Expr addExpr() {
        Expr leftExpr, rightExpr;

        leftExpr = multExpr();

        while (lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS) {
            Symbol op = lexer.token;
            lexer.nextToken();
            rightExpr = multExpr();

            leftExpr = new CompositeExpr(leftExpr, op, rightExpr);
        }

        return leftExpr;
    }

    // MultExpr ::= SimpleExpr { MultOp SimpleExpr }
    private Expr multExpr() {

        Expr leftExpr, rightExpr;

        leftExpr = simpleExpr();

        while (lexer.token == Symbol.MULT || lexer.token == Symbol.DIV || lexer.token == Symbol.REMAINDER) {
            Symbol op = lexer.token;
            lexer.nextToken();
            rightExpr = simpleExpr();

            leftExpr = new CompositeExpr(leftExpr, op, rightExpr);
        }

        return leftExpr;
    }

    // SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr | AddOp SimpleExpr |
    // Ident
    private Expr simpleExpr() {
        Expr e;

        switch (lexer.token) {
            case NUMBER:
            case DIGIT:
                return number();
            case LEFTPAR:
                lexer.nextToken();
                e = expr();
                if (lexer.token != Symbol.RIGHTPAR) {
                    error.signal(") expected");
                }
                lexer.nextToken();
                return e;
            case NOT:
                lexer.nextToken();
                e = simpleExpr();
                return e;
            case PLUS:
                lexer.nextToken();
                e = simpleExpr();
                return e;
            case MINUS:
                lexer.nextToken();
                e = simpleExpr();
                return e;
            default:
                // An identifier
                if (lexer.token != Symbol.IDENT) {
                    error.signal("Identifier expected");
                }
                String ident = lexer.getStringValue();
                lexer.nextToken();

                // Test if variable was declared
                Variable v = (Variable) symbolTable.get(ident);
                if (v == null) {
                    error.signal("Variable '" + ident + "' was not declared");
                }

                return new VariableExpr(v);
        }

    }

    // Number ::= [’+’|’-’] Digit { Digit }
    private NumberExpr number() {

        int value = lexer.getNumberValue();
        lexer.nextToken();

        return new NumberExpr(value);
    }

    /**
     * Auxiliary Functions and Methods
     **/

    // "var" Int Ident ";"
    private Variable variable() {

        if (!(lexer.token == Symbol.IDENT && lexer.getStringValue().equals("Int"))) {
            error.signal("Int expected");
        }
        lexer.nextToken();

        if (lexer.token != Symbol.IDENT) {
            error.signal("Ident expected");
        }
        String ident = lexer.getStringValue();
        lexer.nextToken();

        if (lexer.token != Symbol.SEMICOLON) {
            error.signal("; expected");
        }
        lexer.nextToken();

        return new Variable(ident, 0);
    }

}
