package org.uva.taxfree.ql.model.node.operators;

import org.uva.taxfree.ql.model.node.expression.ExpressionNode;

public class EqualsOperator extends UniformOperator {
    @Override
    public String evaluate(ExpressionNode left, ExpressionNode right) {
        return new Boolean(left.asString().equals(right.asString())).toString();
    }
}