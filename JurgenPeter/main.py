from gui.formapp import *
from ql.grammar import parse_file
from ql.visitors.dependency_checker import DependencyChecker
from ql.visitors.printer import Printer
from ql.visitors.symbol_checker import SymbolChecker
from ql.visitors.type_checker import TypeChecker


def report_error(message):
    print("\33[31mError: {}\33[39m".format(message))


def report_warning(message):
    print("\33[33mWarning: {}\33[39m".format(message))


def main():

    filename_inp = "exampleForm.txt"

    form = parse_file(filename_inp)
    Printer().visit(form)

    symbol_errors, symbol_warnings, symboltable = SymbolChecker().visit(form)
    type_errors, type_warnings = TypeChecker(symboltable).visit(form)
    dependency_errors, dependency_warnings = DependencyChecker().visit(form)

    errors = symbol_errors + type_errors + dependency_errors
    warnings = symbol_warnings + type_warnings + dependency_warnings

    for e in errors:
        report_error(e)

    for w in warnings:
        report_warning(w)

    if errors:
        return

    app = FormApp(form)
    app.start()

if __name__ == "__main__":
    main()
