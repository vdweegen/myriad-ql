module QL
  module TypeChecker
    class CyclicDependencyChecker
      def visit_form(form, collected_data=nil)
        @variable_dependencies = collected_data
        form.statements.map { |statement| statement.accept(self) }
      end

      def visit_if_statement(if_statement)
        if_statement.body.map { |statement| statement.accept(self) }
      end

      def visit_if_else_statement(if_else_statement)
        if_else_statement.if_body.map { |statement| statement.accept(self) }
        if_else_statement.else_body.map { |statement| statement.accept(self) }
      end

      def visit_question(_)
      end

      def visit_computed_question(computed_question)
        computed_question.assignment.accept(self)
      end

      def visit_expression_sequence(expression_sequence)
        expression_sequence.expressions.reduce do |left, operation|
          operation.accept(left, self)
        end
      end

      def visit_boolean_negation(integer_negation)
        integer_negation.expression.accept(self)
      end

      def visit_integer_negation(boolean_negation)
        boolean_negation.expression.accept(self)
      end

      def visit_arithmetic_expression(left, binary_expression)
        left.accept(self)
        binary_expression.expression.accept(self)
      end

      def visit_boolean_expression(left, binary_expression)
        left.accept(self)
        binary_expression.expression.accept(self)
      end

      def visit_comparison_equal_expression(left, binary_expression)
        left.accept(self)
        binary_expression.expression.accept(self)
      end

      def visit_comparison_order_expression(left, binary_expression)
        left.accept(self)
        binary_expression.expression.accept(self)
      end

      def visit_integer_literal(integer_literal)
        integer_literal
      end

      def visit_boolean_literal(boolean_literal)
        boolean_literal
      end

      def visit_string_literal(string_literal)
        string_literal
      end

      def visit_variable(variable)
        cyclic_dependency_check(variable)
        variable
      end

      def cyclic_dependency_check(variable)
        dependent_variables = get_dependencies(variable)
        if dependent_variables
          dependent_variables.each do |dependent_variable|
            check_dependent_variables(variable, dependent_variable)
          end
        end
      end

      # add new dependency to original dependency hash if they exist, don't add duplicates
      def check_dependent_variables(variable, dependent_variable)
        next_dependent_variables = get_dependencies(dependent_variable)
        if next_dependent_variables
          add_dependencies(variable, next_dependent_variables)
          is_cyclic_dependency?(variable, dependent_variable)
        end
      end

      def get_dependencies(variable)
        @variable_dependencies[variable.name]
      end

      def add_dependencies(variable, dependencies)
        @variable_dependencies[variable.name] = get_dependencies(variable) | dependencies
      end

      # check for cyclic dependency if there is a dependency on itself, else visit the next variable
      def is_cyclic_dependency?(variable, dependent_variable)
        if @variable_dependencies[variable.name].map(&:name).include?(variable.name)
          NotificationTable.store(Notification::Error.new("question '#{variable.name}' has a cyclic dependency"))
        else
          visit_variable(dependent_variable)
        end
      end
    end
  end
end