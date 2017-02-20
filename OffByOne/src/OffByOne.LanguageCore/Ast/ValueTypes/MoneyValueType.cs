﻿namespace OffByOne.LanguageCore.Ast.ValueTypes
{
    using OffByOne.LanguageCore.Ast.ValueTypes.Base;

    public class MoneyValueType : NumericalValueType
    {
        public override bool Equals(object obj)
        {
            return obj is MoneyValueType;
        }

        public override int GetHashCode()
        {
            return int.MaxValue;
        }

        public override string ToString()
        {
            return "money";
        }
    }
}