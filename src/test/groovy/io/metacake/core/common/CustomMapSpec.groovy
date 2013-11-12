package io.metacake.core.common
import spock.lang.Specification

import java.util.concurrent.Callable

import static org.hamcrest.CoreMatchers.equalTo
import static spock.util.matcher.HamcrestSupport.expect

class CustomMapSpec extends Specification {

    CustomizableMap<Integer, String> map = new CustomizableMap([:])

    def "Optional Default returns the specified value"() {
        setup: Callable res = {'irrelevant'}
        expect: expect map.get(0, res), equalTo('irrelevant')
    }

    def "Optional Default returns the specified value when set"() {
        setup: map.setOnMissing {'irrelevant'}
        expect: map.get(0) == 'irrelevant'
    }

    def "When no default is provided the get throws an error on a missing element"() {
        when: map.get(0)
        then: thrown RuntimeException
    }

    def "Can still get an existing key"() {
        setup: map.put 0, 'data'
        expect: map.get(0) == 'data'
    }

    def "Does it iterate over the entry set"() {

    }
}