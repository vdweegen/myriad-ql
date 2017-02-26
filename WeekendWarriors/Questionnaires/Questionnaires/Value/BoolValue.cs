﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Questionnaires.Value
{
    class BoolValue : Value<bool>
    {
        public BoolValue(bool value)
        {
            this.Val = value;
        }

        public override bool AsBool()
        {
            return this.Val;
        }
    }
}