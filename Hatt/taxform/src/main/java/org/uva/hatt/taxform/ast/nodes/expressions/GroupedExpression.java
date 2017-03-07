package org.uva.hatt.taxform.ast.nodes.expressions;

import org.uva.hatt.taxform.ast.visitors.Visitor;

public class GroupedExpression extends Expression{

    private Expression expression;

    public GroupedExpression(int lineNumber) {
        super(lineNumber);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String evaluateExpression() {
        return expression.evaluateExpression();
    }

    @Override
    public <T> T accept(Visitor<T> visitor){
        return visitor.visit(this);
    }
}