package ast.atom;

import ast.Visitor;
import ast.expression.Expression;

// TODO rename to Literal??
public abstract class Atom extends Expression {
	
	// Binary Operators
	public Atom add(Atom other) { return null; }
	public Atom sub(Atom other) { return null; }
	public Atom mul(Atom other) { return null; }
	public Atom div(Atom other) { return null; }
	
	public BoolAtom and(Atom other) { return null; }
	public BoolAtom or(Atom other) { return null; }
	public BoolAtom eq(Atom other) { return null; }
	public BoolAtom notEq(Atom other) { return null; }
	public BoolAtom greaterEq(Atom other) { return null; }
	public BoolAtom greater(Atom other) { return null; }
	public BoolAtom lessEq(Atom other) { return null; }
	public BoolAtom less(Atom other) { return null; }
	
	// Unary Operators
	public Atom plus() { return null; }
	public Atom min() { return null; }
	public BoolAtom not() { return null; }
	
	public Integer getNumber() { return null; }
	public Boolean getValue() { return null; }
	public String getString() { return null; }
	
	public abstract String getType();
	
	@Override
	public Atom evaluate() {
		return this;
	}
	
	@Override
	public abstract void accept(Visitor v);	
}
