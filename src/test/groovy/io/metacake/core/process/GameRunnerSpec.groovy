package io.metacake.core.process

import io.metacake.core.common.CustomizableMap
import io.metacake.core.common.window.CakeWindow
import io.metacake.core.input.InputSystem
import io.metacake.core.output.OutputSystem
import io.metacake.core.output.RenderingInstructionBundle
import io.metacake.core.process.state.EndState
import io.metacake.core.process.state.GameState
import io.metacake.core.process.state.UserState
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.Semaphore

class GameRunnerSpec extends Specification {
    InputSystem is = Mock(InputSystem)
    OutputSystem os = Mock(OutputSystem)
    CakeWindow window = Mock(CakeWindow)
    GameRunner runner = new GameRunner(is, os, window)
    GameState state = Spy(UserState)
    Semaphore mainLoopLock = new Semaphore(1)


    def setup() {
        mainLoopLock.acquire()
        state.tick(_, _) >> { time ->
            mainLoopLock.release()
            state
        }
        state.renderingInstructions() >> RenderingInstructionBundle.EMPTY_BUNDLE
    }

    @Timeout(10)
    def "runner.stop actually stops the game and disposes systems+window"() {
        when:
        int loopTime = 5
        Thread t = new Thread({ runner.mainLoop(state,loopTime) })

        t.start()
        mainLoopLock.acquire()
        runner.stop()
        t.join()

        then:
        1*is.dispose()
        1*os.dispose()
        1*window.dispose()
    }

    @Timeout(10)
    def "EndState.closeWith disposes systems and window"() {
        when: runGameWithEndState(EndState.closeWith(state))
        then:
        1*is.dispose()
        1*os.dispose()
        1* window.dispose()
    }

    @Timeout(10)
    def "EngState.endWith disposes ONLY systems, and stop disposes only window"() {
        when: runGameWithEndState(EndState.endWith(state))
        then:
        1*is.dispose()
        1*os.dispose()
        0* window.dispose()
        when: runner.stop()
        then:
        0*is.dispose()
        0*os.dispose()
        1* window.dispose()
    }

    @Timeout(10)
    def "Interrupts end game"() {
        Thread t = new Thread({runner.mainLoop(state,5)})
        t.run()
        mainLoopLock.acquire()
        t.interrupt()
        t.join()
    }

    def runGameWithEndState(GameState end) {
        int loopTime = 5 //milliseconds
        GameState runningState = new UserState() {
            int count = 0
            @Override
            GameState tick(long delta, CustomizableMap map) {
                count += 1
                count > 1000 ? end : this
            }
            @Override
            RenderingInstructionBundle renderingInstructions() {
                RenderingInstructionBundle.EMPTY_BUNDLE
            }
        }
        Thread t = new Thread({ runner.mainLoop(runningState,loopTime) })

        t.run()
        t.join()
    }
}