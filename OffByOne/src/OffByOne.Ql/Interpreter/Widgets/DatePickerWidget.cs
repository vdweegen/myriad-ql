﻿namespace OffByOne.Ql.Interpreter.Widgets
{
    using System;
    using System.Windows.Controls;

    using OffByOne.Ql.Ast.Statements;
    using OffByOne.Ql.Interpreter.Widgets.Base;
    using OffByOne.Ql.Values;
    using OffByOne.Ql.Values.Contracts;

    public class DatePickerWidget : QuestionWidget
    {
        public DatePickerWidget(DateValue value, QuestionStatement statement, GuiEnvironment guiEnvironment)
            : base(value, statement, guiEnvironment)
        {
            this.CreateControls(statement);
        }

        protected DatePicker Input { get; private set; }

        public override void OnObserve(AnswerInput value)
        {
            base.OnObserve(value);
            this.Input.SelectedDate = ((DateValue)this.Value).Value;
        }

        protected virtual void UpdateValue(object target, object eventArgs)
        {
            this.Value = new DateValue(this.Input.SelectedDate.Value);
        }

        private void CreateControls(QuestionStatement statement)
        {
            var label = new Label { Content = statement.Label };
            this.Input = new DatePicker { SelectedDateFormat = DatePickerFormat.Short };
            this.Input.SelectedDateChanged += this.UpdateValue;
            this.Controls.Add(label);
            this.Controls.Add(this.Input);
        }
    }
}