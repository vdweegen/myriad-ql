# coding=utf-8
from sys import argv
from sys import exit

from PyQt5.QtWidgets import QApplication
from pql.gui.Editor import Editor


if __name__ == '__main__':
    app = QApplication(argv)
    file_window = Editor(argv)
    file_window.show()
    exit(app.exec_())

# TODO: Error messages aan parser toevoegen
# TODO: duplicate labels (warning)
# TODO: QLS
# NICE TO HAVES ---------------------------------
# TODO: Environment een class maken en een update value methode geven $$
# TODO: Export of values ?
# TODO: Make all fields mandatory
# TODO: NICE TO HAVE Dynamic types in operators ipv python operators
