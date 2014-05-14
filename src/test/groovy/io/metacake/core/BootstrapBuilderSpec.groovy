package io.metacake.core

import io.metacake.core.common.window.CakeWindow
import io.metacake.core.input.system.InputDevice
import io.metacake.core.output.system.OutputDevice
import io.metacake.core.process.state.EndState
import io.metacake.core.process.state.GameState
import spock.lang.Specification

class BootstrapBuilderSpec extends Specification {

    BootstrapBuilder builder = new BootstrapBuilder()

    def "BootstrapBuilder errors when it is given nothing"() {
        when: builder.createBootstrap()
        then: thrown RuntimeException
    }

    def "BootstrapBuilder errors when it is not given a window"() {
        setup:
        GameState state = Mock GameState

        when: builder.withInitialState(state).createBootstrap()
        then: thrown RuntimeException
    }

    def "BootstrapBuilder errors when it is not given a State"() {
        setup:
        CakeWindow window = Mock CakeWindow

        when: builder.withWindow(window).createBootstrap()
        then: thrown RuntimeException
    }

    def "BootstrapBuilder errors when it is not given output devices"() {
        setup:
        CakeWindow window = Mock CakeWindow
        GameState state = Mock GameState

        when: builder.withWindow(window).withInitialState(state).createBootstrap()
        then: thrown RuntimeException
    }

    def "BootstrapBuilder errors when its is given an empty set of output devices"() {
        setup:
        CakeWindow window = Mock CakeWindow
        GameState state = Mock GameState

        when: builder.withWindow(window).withInitialState(state).withOutputDevices().createBootstrap()
        then: thrown RuntimeException
    }

    def "A BootstrapBuilder with only the required configuration does not error"() {
        when: builder.withWindow(Mock(CakeWindow))
                .withInitialState(Mock(GameState))
                .withOutputDevices(Mock(OutputDevice))
                .createBootstrap()
        then: notThrown RuntimeException
    }

    def "A Bootstrapper with all configuration does not throw an error"() {
        when: builder.withWindow(Mock(CakeWindow))
                .withInitialState(Mock(GameState))
                .withInputDevices(Mock(InputDevice))
                .withOutputDevices(Mock(OutputDevice))
                .withLoopTime(40)
                .createBootstrap()
        then: notThrown RuntimeException
    }
}