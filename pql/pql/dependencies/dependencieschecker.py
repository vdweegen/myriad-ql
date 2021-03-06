# coding=utf-8
from pql.messages.error import Error
from pql.traversal.BinaryExpressionVisitor import BinaryExpressionVisitor
from pql.traversal.FormVisitor import FormVisitor
from pql.traversal.IdentifierVisitor import IdentifierVisitor
from pql.traversal.TypeVisitor import TypeVisitor


class DependenciesChecker(FormVisitor, BinaryExpressionVisitor, IdentifierVisitor, TypeVisitor):
    def boolean(self, node):
        return []

    def integer(self, node):
        return []

    def money(self, node):
        return []

    def string(self, node):
        return []

    def __init__(self, ast):
        self.ast = ast
        self.errors = list()

    def visit(self):
        properties = self.ast.apply(self, dict())
        self.errors.clear()
        self.ast.apply(self, properties)
        return self.errors

    def form(self, node, properties=None):
        for statement in node.statements:
            result = statement.apply(self, properties)
            if result is not None:
                key, value = result
                properties[key] = value
        return properties

    def conditional_if_else(self, node, local_properties=None):
        self.conditional_if(node, local_properties.copy())
        self.conditional(local_properties.copy(), node.else_statement_list)

    def conditional_if(self, node, local_properties=None):
        self.conditional(local_properties.copy(), node.statements)

    def conditional(self, local_properties, statements):
        for statement in statements:
            result = statement.apply(self, local_properties)
            if result is not None:
                key, value = result
                local_properties[key] = value

    def field(self, node, scope_properties=None):
        return node.name.name, node.name

    def assignment(self, node, scope_properties=None):
        bad_reference = []
        children = node.expression.apply(self)
        for child in children:
            if child.name not in scope_properties:
                bad_reference.append(child)
        if len(bad_reference) > 0:
            self.errors.append(Error("Field at {} had the following references that were not resolvable: {} "
                               .format(node.location,
                                       ["{}: {}".format(ref.name, ref.location) for ref in bad_reference]),
                                     bad_reference[0].location))
        return node.name.name, node.name

    def identifier(self, node):
        return [node]

    def greater_inclusive(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def addition(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def and_(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def subtraction(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def lower_inclusive(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def inequality(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def lower_exclusive(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def or_(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def multiplication(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def greater_exclusive(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def division(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)

    def equality(self, node):
        return node.lhs.apply(self) + node.rhs.apply(self)
