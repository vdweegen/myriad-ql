package com.matthewchapman.ql.visitor;

import com.matthewchapman.ql.ast.statement.CalculatedQuestion;
import com.matthewchapman.ql.ast.statement.IfElseStatement;
import com.matthewchapman.ql.ast.statement.IfStatement;
import com.matthewchapman.ql.ast.statement.Question;

/**
 * Created by matt on 18/03/2017.
 */
public interface QLStatementVisitor<T, C> {

    //Statement
    T visit(Question question, C context);

    T visit(IfStatement ifStatement, C context);

    T visit(IfElseStatement ifElseStatement, C context);

    T visit(CalculatedQuestion calculatedQuestion, C context);

}