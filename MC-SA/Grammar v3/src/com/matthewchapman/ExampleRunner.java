package com.matthewchapman;

import com.matthewchapman.QL.Form;
import com.matthewchapman.antlr.QLLexer;
import com.matthewchapman.antlr.QLParser;
import com.matthewchapman.com.matthewchapman.Parsing.MCQLVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;


/**
 * Created by matt on 20/02/2017.
 */
public class ExampleRunner {

    public static void main(String[] args) {

        ExampleRunner runner = new ExampleRunner();

        runner.printIDs("form taxOfficeExample { \n" +
            "  \"Did you sell a house in 2010?\"\n" +
            "    hasSoldHouse: boolean\n" +
            "  \"Did you buy a house in 2010?\"\n" +
            "    hasBoughtHouse: boolean;\n" +
            "  \"Did you enter a loan?\"\n" +
            "    hasMaintLoan: boolean;\n" +
            "\n" +
            "  if hasSoldHouse {\n" +
            "    \"What was the selling price?\"\n" +
            "      sellingPrice: money;\n" +
            "    \"Private debts for the sold house:\"\n" +
            "      privateDebt: money;\n" +
            "    \"Value residue:\"\n" +
            "      valueResidue: money = \n" +
            "        (sellingPrice - privateDebt);\n" +
            "  }\n" +
            "\n" +
            "}");

    }

    private void printIDs(String input) {
        // Get our lexer
        QLLexer lexer = new QLLexer(new ANTLRInputStream(input));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        QLParser parser = new QLParser(tokens);

        // Specify our entry point
        QLParser.FormDeclarationContext formDeclarationContext = parser.formDeclaration();

        MCQLVisitor visitor = new MCQLVisitor();
        Form traverseResult = (Form) visitor.visit(formDeclarationContext);

        traverseResult.questionList.forEach(x -> System.out.println(x.name));
    }

}
