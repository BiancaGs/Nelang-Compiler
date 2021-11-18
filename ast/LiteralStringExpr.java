package ast;

public class LiteralStringExpr extends Expr {
    
    private String literalString;

    public LiteralStringExpr( String literalString ) {
        this.literalString = literalString;
    }

    public Type getType() {
        return Type.stringType;
    }

    @Override
    public void genC( PW pw) {
        pw.print("\"" + literalString + "\"");
    }
	@Override
	public int run() {
		return 0;
	}

}
