package io.metacake.core

import io.metacake.core.common.window.CakeWindow
import io.metacake.core.process.state.GameState
import spock.lang.Specification

class BootstrapBuilderSpec extends Specification {

    def "BootstrapBuilder errors when it is given nothing"() {
        when: new BootstrapBuilder().createBootstrap()
        then: thrown RuntimeException
    }

    def "BootstrapBuilder errors when it is not given a window"() {
        setup:
        GameState state = Mock GameState
        BootstrapBuilder builder = new BootstrapBuilder()

        when: builder.withInitialState(state).createBootstrap()
        then: thrown RuntimeException
    }

    def "BootstrapBuilder errors when it is not given a State"() {
        setup:
        CakeWindow window = Mock CakeWindow
        BootstrapBuilder builder = new BootstrapBuilder()

        when: builder.withWindow(window).createBootstrap()
        then: thrown RuntimeException
    }
}