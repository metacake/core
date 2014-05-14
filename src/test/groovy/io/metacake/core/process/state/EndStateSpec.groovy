package io.metacake.core.process.state

import io.metacake.core.output.RenderingInstructionBundle
import io.metacake.core.process.ActionRecognizerPipe
import spock.lang.Specification

class EndStateSpec extends Specification {

    EndState closedEndState = ((EndState) EndState.closeWith(Mock(GameState)))
    EndState endedEndState = ((EndState) EndState.endWith(Mock(GameState)))

    def "endWith does not close the window"() {
        expect: !endedEndState.shouldCloseWindow()
    }

    def "closeWith should close the window"() {
        expect: closedEndState.shouldCloseWindow()
    }

    def "If tick is somehow called it will always return itself"() {
        expect:
        closedEndState.tick(50, new ActionRecognizerPipe()) == closedEndState
        endedEndState.tick(50, new ActionRecognizerPipe()) == endedEndState
    }

    def "If replaceInputs is somehow called it always returns false"() {
        expect:
        !closedEndState.tick(50, new ActionRecognizerPipe()).replaceInputs()
        !endedEndState.tick(50, new ActionRecognizerPipe()).replaceInputs()
    }

    def "Replacing Action triggers is unsupported"() {
        when: closedEndState.replaceActionTriggers()
        then: thrown UnsupportedOperationException
    }

    def "Replacing Action recognizers is unsupported"() {
        when: closedEndState.replaceActionRecognizers()
        then: thrown UnsupportedOperationException
    }

    def "Calling for a RenderInstructionBundle delegates to the given GameState"() {
        setup:
        GameState state = Mock GameState
        RenderingInstructionBundle bundle = new RenderingInstructionBundle()
        state.renderingInstructions() >> bundle

        expect: EndState.endWith(state).renderingInstructions() == bundle
    }
}