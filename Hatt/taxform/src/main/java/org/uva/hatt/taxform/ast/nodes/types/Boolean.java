package org.uva.hatt.taxform.ast.nodes.types;

import org.uva.hatt.taxform.ast.visitors.Visitor;

public class Boolean extends ValueType {

    public Boolean(int lineNumber) {
        super(lineNumber);
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}