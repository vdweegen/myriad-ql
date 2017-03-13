from gui.visitors.widget_creator import WidgetCreator
from gui.visitors.gui_builder import GuiBuilder


class QlGuiBuilder(GuiBuilder):

    def visit_form(self, node):
        for element in node.body:
            self.visit(element)
        self.create_exit_button()

    def visit_question(self, node):
        widget_constructor = WidgetCreator().create(node.datatype)
        widget = widget_constructor(self.app, node)
        widget.set_listener(self.listener)
        self.widgets[node.name] = widget

    def visit_computed_question(self, node):
        self.visit_question(node)
        self.widgets[node.name].disable()

    def visit_if_conditional(self, node):
        for element in node.ifbody:
            self.visit(element)

    def visit_ifelse_conditional(self, node):
        for element in node.ifbody:
            self.visit(element)
        for element in node.elsebody:
            self.visit(element)