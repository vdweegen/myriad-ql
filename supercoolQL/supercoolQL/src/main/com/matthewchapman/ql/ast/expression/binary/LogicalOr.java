package com.matthewchapman.ql.ast.expression.binary;

import com.matthewchapman.ql.ast.Expression;
import com.matthewchapman.ql.ast.QLVisitable;
import com.matthewchapman.ql.validation.visitor.QLVisitor;

/**
 * Created by matt on 24/02/2017.
 * <p>
 * Logical OR class
 */
public class LogicalOr extends BinaryOperation implements QLVisitable {

    public LogicalOr(Expression left, Expression right, int line, int charPositionInLine) {
        super(left, right);
        super.setLine(line);
        super.setColumn(charPositionInLine);
    }

    @Override
    public String toString() {
        return "(" + getLeft() + " || " + getRight() + ")";
    }

    @Override
    public <T, C> T accept(QLVisitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
