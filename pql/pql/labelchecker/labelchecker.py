# coding=utf-8
from collections import defaultdict

from pql.messages.alert import Alert
from pql.traversal.FormVisitor import FormVisitor


class LabelChecker(FormVisitor):
    def __init__(self, ast):
        self.__symbol_table = defaultdict(list)
        self.ast = ast

    def visit(self):
        def build_alert_list(identifiers):
            alerts = list()
            for key, nodes in identifiers.items():
                if len(nodes) > 1:
                    alerts.append(
                        Alert("Form contained multiple declarations of the same label: {}, "
                                "at the following locations: {}"
                                  .format(key, [v.location for v in nodes]), nodes[0].location))
            return alerts

        self.__symbol_table.clear()
        self.ast.apply(self)
        return build_alert_list(self.__symbol_table)

    def form(self, node, args=None):
        for statement in node.statements:
            statement.apply(self)

    def conditional_if_else(self, node, args=None):
        self.conditional_if(node)
        for statement in node.else_statement_list:
            statement.apply(self)

    def conditional_if(self, node, args=None):
        for statement in node.statements:
            statement.apply(self)

    def field(self, node, args=None):
        self.__symbol_table[node.title].append(node.name)

    def assignment(self, node, args=None):
        self.field(node)
