/**
 * MyInteger.java.
 */

package ql.astnodes.expressions.literals;

import ql.astnodes.types.Type;
import ql.astnodes.LineNumber;
import ql.astnodes.types.IntegerType;
import ql.visitorinterfaces.ExpressionVisitor;

public class MyInteger extends Literal {

    private final Type type;
    private final Integer value;

    public MyInteger(Integer value, LineNumber lineNumber) {
        super(lineNumber);
        this.value = value;
        this.type = new IntegerType(lineNumber);
    }

    public Type getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
