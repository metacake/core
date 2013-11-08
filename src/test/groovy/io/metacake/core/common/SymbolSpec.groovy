package io.metacake.core.common
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.core.IsEqual.equalTo
import static spock.util.matcher.HamcrestSupport.expect

class SymbolSpec extends Specification {
    Symbol symbol1 = new Symbol()
    Symbol symbol2 = new Symbol()
    Symbol symbol3 = new Symbol("Named")

    def "Symbols are referentially equality"() {
        expect:
        symbol1 == symbol1
        expect symbol1, equalTo(symbol1)

        symbol1 != symbol2
        expect symbol1, not(equalTo(symbol2))
    }

    def "Symbol with no name uses Hex of Hashcode"() {
        expect:
        symbol1.toString() == symbol1.getClass().getName() + "@" + Integer.toHexString(symbol1.hashCode())
    }

    def "Symbol with Name uses the name"() {
        expect:
        Symbol.PREFIX + "Named" == symbol3.toString()
    }
}
