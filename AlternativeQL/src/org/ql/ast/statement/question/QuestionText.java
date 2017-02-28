package org.ql.ast.statement.question;

import org.ql.ast.Node;

public class QuestionText extends Node {
    private final String questionText;

    public QuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String toString() {
        return questionText;
    }
}
