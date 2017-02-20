﻿namespace OffByOne.Ql.Ast.Statements
{
    using OffByOne.LanguageCore.Ast.Expressions.Base;
    using OffByOne.LanguageCore.Ast.Literals;
    using OffByOne.LanguageCore.Ast.ValueTypes.Base;

    public class QuestionStatement : Statement
    {
        public QuestionStatement(
            string identifier,
            ValueType type,
            StringLiteral question,
            Expression value = null)
        {
            this.Identifier = identifier;
            this.Type = type;
            this.Question = question;
            this.ComputedValue = value;
        }

        public string Identifier { get; private set; }

        public ValueType Type { get; private set; }

        public StringLiteral Question { get; private set; }

        public Expression ComputedValue { get; private set; }
    }
}