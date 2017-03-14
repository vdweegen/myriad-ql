package org.ql.gui.widgets;


import org.ql.ast.statement.Question;
import org.ql.evaluator.value.StringValue;
import org.ql.evaluator.value.Value;
import org.ql.gui.mediator.GUIMediator;

public class TextInputWidget extends InputWidget {

    public TextInputWidget(GUIMediator mediator, Question question) {
        super(mediator, question);
    }

    @Override
    protected Value value(String textFieldText) {
        return new StringValue(textFieldText);
    }
}
