﻿namespace OffByOne.Ql.Ast.Expressions.Unary
{
    using OffByOne.Ql.Ast.Expressions.Unary.Base;
    using OffByOne.Ql.Visitors.Contracts;

    public class NotExpression : UnaryExpression
    {
        public NotExpression(Expression expression)
            : base(expression)
        {
        }

        public override TResult Accept<TResult, TContext>(
            IExpressionVisitor<TResult, TContext> visitor,
            TContext context)
        {
            return visitor.Visit(this, context);
        }
    }
}
