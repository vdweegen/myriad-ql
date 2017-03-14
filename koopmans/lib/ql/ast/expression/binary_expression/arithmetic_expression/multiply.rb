module QL
  module AST
    class Multiply < ArithmeticExpression
      def eval(left, right)
        IntegerLiteral.new(left * right)
      end
    end
  end
end
