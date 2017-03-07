package org.ql.typechecker.expression;

import org.junit.Test;
import static org.junit.Assert.*;
import org.ql.ast.Identifier;
import org.ql.ast.type.StringType;

public class SymbolTableTest {
    @Test
    public void shouldPutIdentifierWhenPutExecuted() {
        SymbolTable symbolTable = new SymbolTable();

        symbolTable.put(new Identifier("example"), new StringType());

        assertTrue(symbolTable.containsKey("example"));
        assertSame(symbolTable.size(), 1);
    }

    @Test
    public void shouldGetIdentifierWhenGetExecuted() {
        SymbolTable symbolTable = new SymbolTable();
        Identifier identifier = new Identifier("example");
        StringType actualType = new StringType();

        symbolTable.put(identifier, actualType);

        assertSame(symbolTable.get(identifier), actualType);
    }

    @Test
    public void shouldContainKeyWhenContainsKeyExecuted() {
        SymbolTable symbolTable = new SymbolTable();
        Identifier identifier = new Identifier("example");
        StringType actualType = new StringType();

        symbolTable.put(identifier, actualType);

        assertTrue(symbolTable.containsKey(identifier));
    }
}