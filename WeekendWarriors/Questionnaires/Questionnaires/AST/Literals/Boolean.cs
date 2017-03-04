﻿using System.Collections.Generic;
using Questionnaires.SemanticAnalysis.Messages;
using Questionnaires.SemanticAnalysis;
using Questionnaires.Value;

namespace Questionnaires.AST.Literals
{
    public class Boolean : IExpression
    {
        public Boolean(bool value)
        {
            this.Value = value;
        }

        public bool Value
        {
            get;
        }

        public bool CheckSemantics(QLContext context, List<Message> messages)
        {
            // Nothing to check for bool literal
            return true;
        }

        public IValue GetResultType(QLContext context)
        {
            return new BoolValue();
        }
    }
}
