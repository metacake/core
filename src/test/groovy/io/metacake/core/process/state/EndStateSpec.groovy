package io.metacake.core.process.state

import io.metacake.core.process.ActionRecognizerPipe
import spock.lang.Specification

class EndStateSpec extends Specification {

    EndState closedEndState = ((EndState) EndState.close())
    EndState endedEndState = ((EndState) EndState.end())

    def "endWith does not close the window"() {
        expect: !endedEndState.shouldCloseWindow()
    }

    def "closeWith should close the window"() {
        expect: closedEndState.shouldCloseWindow()
    }

    def "If tick is somehow called it will always return itself"() {
        expect:
        closedEndState.tick(50, new ActionRecognizerPipe()).state() == closedEndState
        endedEndState.tick(50, new ActionRecognizerPipe()).state() == endedEndState
    }

    def "If replaceInputs is somehow called it always returns false"() {
        expect:
        !closedEndState.tick(50, new ActionRecognizerPipe()).state().replaceInputs()
        !endedEndState.tick(50, new ActionRecognizerPipe()).state().replaceInputs()
    }

    def "Replacing Action triggers is unsupported"() {
        when: closedEndState.replaceActionTriggers()
        then: thrown UnsupportedOperationException
    }

    def "Replacing Action recognizers is unsupported"() {
        when: closedEndState.replaceActionRecognizers()
        then: thrown UnsupportedOperationException
    }
}