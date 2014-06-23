package io.metacake.core.process.state

import io.metacake.core.process.ActionRecognizerPipe
import spock.lang.Specification

class EndStateSpec extends Specification {

    EndState closedEndState = ((EndState) EndState.close())
    EndState endedEndState = ((EndState) EndState.end())

    def "end does not close the window"() {
        expect: endedEndState.type() == GameState.Type.END
    }

    def "closeWith should close the window"() {
        expect: closedEndState.type() == GameState.Type.CLOSE
    }

    def "If tick is somehow called it will always return itself"() {
        expect:
        closedEndState.tick(50, new ActionRecognizerPipe()).state() == closedEndState
        endedEndState.tick(50, new ActionRecognizerPipe()).state() == endedEndState
    }
}