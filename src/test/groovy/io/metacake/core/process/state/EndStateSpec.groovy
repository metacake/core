package io.metacake.core.process.state

import spock.lang.Specification

class EndStateSpec extends Specification {

    def "endWith does not close the window"() {
        expect:
        !((EndState) EndState.endWith(Mock(GameState))).shouldCloseWindow()
    }

    def "closeWith should close the window"() {
        expect:
        ((EndState) EndState.closeWith(Mock(GameState))).shouldCloseWindow()
    }
}