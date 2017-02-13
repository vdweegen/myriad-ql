package ast.type;

public class IntegerType extends Type {

    // TODO why can you not use the constructor of superclass Type directly
	public IntegerType() {
		super("integer");
	}
	
	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);		
	}
	
}
