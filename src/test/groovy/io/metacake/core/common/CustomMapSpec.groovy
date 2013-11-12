package io.metacake.core.common
import spock.lang.Specification

import java.util.concurrent.Callable

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.notNullValue
import static org.spockframework.util.Assert.notNull
import static org.spockframework.util.Matchers.not
import static spock.util.matcher.HamcrestSupport.expect

class CustomMapSpec extends Specification {

    CustomizableMap<Symbol, String> map = new CustomizableMap([:])

    def "Optional Default returns the specified value"() {
        setup: Callable res = {'irrelevant'}
        expect: expect map.get(Symbol.genSym(), res), equalTo('irrelevant')
    }

    def "Optional Default returns the specified value when set"() {
        setup: map.setOnMissing {'irrelevant'}
        expect: map.get(Symbol.genSym()) == 'irrelevant'
    }

    def "When no default is provided the get throws an error on a missing element"() {
        when: map.get(Symbol.genSym())
        then: thrown RuntimeException
    }

    def "Can still get an existing key"() {
        setup:
        def name = Symbol.genSym()
        map.put name, 'data'
        expect: map.get(name) == 'data'
    }

    def "Does it iterate over the entry set"() {
        setup:
        def elements = (0..10).collect {Symbol.genSym(it.toString())}
        elements.each {map.put(it,it.toString())}
        expect:
        map.iterator().toList() == map.entrySet().toList()
    }
}