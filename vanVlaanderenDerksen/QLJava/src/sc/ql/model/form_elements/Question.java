package sc.ql.model.form_elements;

import sc.ql.model.Atom;
import sc.ql.model.FormElement;
import sc.ql.model.NodeVisitor;
import sc.ql.model.expressions.Expression;

public class Question implements FormElement {
	private final String question; 
	private final Atom<String> id;
	private final Atom.Type type;
	private final Expression expression;
	
	public Question(String question, Atom<String> id, Atom.Type type, Expression expression) {
		this.question = question;
		this.id = id;
		this.type = type;
		this.expression = expression;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public Atom<String> getId() {
		return this.id;
	}
	
	public Atom.Type getType() {
		return this.type;
	}
	
	public Expression getExpression() {
		return this.expression;
	}
	
	public Type getElementType() {
		return Type.QUESTION;
	}

	@Override
	public <T> T accept(NodeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}