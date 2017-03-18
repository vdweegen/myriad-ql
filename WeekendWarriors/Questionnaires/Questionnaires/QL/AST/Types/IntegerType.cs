﻿using Questionnaires.UI.Widgets;

namespace Questionnaires.Types
{
    public class IntegerType : Type<int>
    {
        public IntegerType()
        {
        }

        public IntegerType(int value)
        {
            Value = value;
        }

        public override IType Positive()
        {
            return new IntegerType(Value);
        }

        public override IType Negative()
        {
            return new IntegerType(-Value);
        }

        public override IType Add(IntegerType value)
        {
            return new IntegerType(Value + value.GetValue());
        }

        public override IType Add(MoneyType value)
        {
            return new MoneyType(Value + value.GetValue());
        }

        public override IType Subtract(IntegerType value)
        {
            return new IntegerType(Value - value.GetValue());
        }

        public override IType Subtract(MoneyType value)
        {
            return new MoneyType(Value - value.GetValue());
        }

        public override IType Multiply(IntegerType value)
        {
            return new IntegerType(Value * value.GetValue());
        }

        public override IType Multiply(MoneyType value)
        {
            return new MoneyType(Value * value.GetValue());
        }

        public override IType Divide(IntegerType value)
        {
            return new IntegerType(Value / value.GetValue());
        }

        public override IType Divide(MoneyType value)
        {
            return new MoneyType(Value / value.GetValue());
        }

        public override BooleanType LessThan(IntegerType value)
        {
            return new BooleanType(Value < value.GetValue());
        }

        public override BooleanType LessThan(MoneyType value)
        {
            return new BooleanType(Value < value.GetValue());
        }

        public override BooleanType LessThanOrEqual(IntegerType value)
        {
            return new BooleanType(Value <= value.GetValue());
        }

        public override BooleanType LessThanOrEqual(MoneyType value)
        {
            return new BooleanType(Value <= value.GetValue());
        }

        public override BooleanType GreaterThan(IntegerType value)
        {
            return new BooleanType(Value > value.GetValue());
        }

        public override BooleanType GreaterThan(MoneyType value)
        {
            return new BooleanType(Value > value.GetValue());
        }

        public override BooleanType GreaterThanOrEqual(IntegerType value)
        {
            return new BooleanType(Value >= value.GetValue());
        }

        public override BooleanType GreaterThanOrEqual(MoneyType value)
        {
            return new BooleanType(Value >= value.GetValue());
        }

        public override BooleanType EqualTo(IntegerType value)
        {
            return new BooleanType(Value == value.GetValue());
        }

        public override BooleanType EqualTo(MoneyType value)
        {
            return new BooleanType(Value == value.GetValue());
        }

        public override BooleanType InequalTo(IntegerType value)
        {
            return new BooleanType(Value != value.GetValue());
        }

        public override BooleanType InequalTo(MoneyType value)
        {
            return new BooleanType(Value != value.GetValue());
        }

        public override QuestionWidget GetWidget()
        {
            return new IntegerPickerWidget();
        }
    }
}
