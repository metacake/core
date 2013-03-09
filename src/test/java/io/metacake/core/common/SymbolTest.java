package io.metacake.core.common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A simple set of test cases showing some of the properties of Symbols.
 */
public class SymbolTest {

    Symbol sym1 = new Symbol();
    Symbol sym2 = new Symbol();

    /**
     * This test is meant to show that symbols are only equal to themselves.
     */
    @Test
    public void symbolEquality() {
        assertEquals(sym1, sym1);
        assertSame(sym1, sym1);

        assertNotSame(sym1, sym2);
        assertFalse(sym1.equals(sym2));
    }
}