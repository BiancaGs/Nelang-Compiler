package lexer;

import java.util.Hashtable;

import errorHandling.CompilerError;

public class Lexer {

    public Lexer(char[] input, CompilerError error) {

        this.input = input;

        // Add an end-of-file label to make it easy to do the lexer
        input[input.length - 1] = '\0';

        // Current line number
        lineNumber = 1;

        // Token Position
        tokenPos = 0;

        this.error = error;
    }

    // Keywords Table - Hashtable from String to Symbol
    static private Hashtable<String, Symbol> keywordsTable;

    // Setting Keywords Table
    static {
        keywordsTable = new Hashtable<String, Symbol>();
        keywordsTable.put("var", Symbol.VAR);
        keywordsTable.put("if", Symbol.IF);
        keywordsTable.put("else", Symbol.ELSE);
        keywordsTable.put("for", Symbol.FOR);
        keywordsTable.put("in", Symbol.IN);
        keywordsTable.put("print", Symbol.PRINT);
        keywordsTable.put("println", Symbol.PRINTLN);
        keywordsTable.put("while", Symbol.WHILE);
    }

    public void nextToken() {

        char ch;

        while ((ch = input[tokenPos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
            // Count the number of lines
            if (ch == '\n')
                lineNumber++;
            tokenPos++;
        }

        if (ch == '\0') {
            token = Symbol.EOF;

        } else if (input[tokenPos] == '/' && input[tokenPos + 1] == '/') {
            // Comment found
            while (input[tokenPos] != '\0' && input[tokenPos] != '\n') {
                tokenPos++;
            }
            nextToken();

        } else {

            if (Character.isLetter(ch)) {

                // Get an Identifier or Keyword
                StringBuffer ident = new StringBuffer();

                // Get String Value
                while (Character.isLetter(input[tokenPos])) {
                    ident.append(input[tokenPos]);
                    tokenPos++;
                }
                stringValue = ident.toString();

                // If stringValue is in the list of Keywords, it is a Keyword
                Symbol value = keywordsTable.get(stringValue);

                // If is not in the list of Keywords, then it is an Identifier
                if (value == null) {
                    token = Symbol.IDENT;
                } else {
                    token = value;
                }

                // if (Character.isDigit(input[tokenPos])) {
                // error.signal("Word followed by a number");
                // }

            } else if (Character.isDigit(ch)) {

                // Get a Number/Digit
                StringBuffer number = new StringBuffer();

                while (Character.isDigit(input[tokenPos])) {
                    number.append(input[tokenPos]);
                    tokenPos++;
                }

                // If contains only 1 character, then it is a Digit, otherwise it is a Number
                if (number.length() == 1) {
                    token = Symbol.DIGIT;
                } else {
                    token = Symbol.NUMBER;
                }

                try {
                    numberValue = Integer.valueOf(number.toString()).intValue();
                } catch (NumberFormatException e) {
                    error.signal("Number out of limits");
                }

                if (numberValue >= MaxValueInteger) {
                    error.signal("Number out of limits");
                }

            } else {

                tokenPos++;

                switch (ch) {
                    case '+':
                        token = Symbol.PLUS;
                        break;
                    case '-':
                        token = Symbol.MINUS;
                        break;
                    case '*':
                        token = Symbol.MULT;
                        break;
                    case '/':
                        token = Symbol.DIV;
                        break;
                    case '%':
                        token = Symbol.REMAINDER;
                        break;
                    case '<':
                        if (input[tokenPos] == '=') {
                            tokenPos++;
                            token = Symbol.LE;
                        } else {
                            token = Symbol.LT;
                        }
                        break;
                    case '>':
                        if (input[tokenPos] == '=') {
                            tokenPos++;
                            token = Symbol.GE;
                        } else {
                            token = Symbol.GT;
                        }
                        break;
                    case '=':
                        if (input[tokenPos] == '=') {
                            tokenPos++;
                            token = Symbol.EQ;
                        } else {
                            token = Symbol.ASSIGN;
                        }
                        break;
                    case '!':
                        if (input[tokenPos] == '=') {
                            tokenPos++;
                            token = Symbol.NEQ;
                        } else {
                            token = Symbol.NOT;
                        }
                        break;
                    case '|':
                        if (input[tokenPos] == '|') {
                            tokenPos++;
                            token = Symbol.OR;
                        } else {
                            this.error.signal("Missing |");
                        }
                        break;
                    case '&':
                        if (input[tokenPos] == '&') {
                            tokenPos++;
                            token = Symbol.AND;
                        } else {
                            this.error.signal("Missing &");
                        }
                        break;
                    case '(':
                        token = Symbol.LEFTPAR;
                        break;
                    case ')':
                        token = Symbol.RIGHTPAR;
                        break;
                    case '{':
                        token = Symbol.LEFTBRACE;
                        break;
                    case '}':
                        token = Symbol.RIGHTBRACE;
                        break;
                    case '.':
                        if (input[tokenPos] == '.') {
                            tokenPos++;
                            token = Symbol.DOTDOT;
                        } else {
                            this.error.signal("Missing .");
                        }
                        break;
                    case ';':
                        token = Symbol.SEMICOLON;
                        break;
                    default:
                        error.signal("Invalid Character: '" + ch + "'");
                }
            }
        }
        lastTokenPos = tokenPos - 1;
    }

    // return the line number of the last token got with getToken()
    public int getLineNumber() {
        return lineNumber;
    }

    public String getCurrentLine() {

        int i = lastTokenPos;

        if (i == 0) {
            i = 1;
        } else if (i >= input.length) {
            i = input.length;
        }

        StringBuffer line = new StringBuffer();

        // Go to the beginning of the line
        while (i >= 1 && input[i] != '\n') {
            i--;
        }

        if (input[i] == '\n') {
            i++;
        }

        // Go to the end of the line putting it in variable line
        while (input[i] != '\0' && input[i] != '\n' && input[i] != '\r') {
            line.append(input[i]);
            i++;
        }

        return line.toString();
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public char getCharValue() {
        return charValue;
    }

    // Current token
    public Symbol token;
    private String stringValue;
    private int numberValue;
    private char charValue;

    private int tokenPos;
    // input[lastTokenPos] is the last character of the last token
    private int lastTokenPos;
    // Program given as input - source code
    private char[] input;

    // Number of current line. Starts with 1
    private int lineNumber;

    private CompilerError error;
    private static final int MaxValueInteger = 32768;
}
