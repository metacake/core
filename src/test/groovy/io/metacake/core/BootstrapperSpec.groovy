package io.metacake.core

import io.metacake.core.common.TimedLoopThread
import io.metacake.core.common.window.CakeWindow
import io.metacake.core.input.InputDeviceName
import io.metacake.core.input.InputSystem
import io.metacake.core.input.system.InputDevice
import io.metacake.core.output.OutputDeviceName
import io.metacake.core.output.OutputSystem
import io.metacake.core.output.RenderingInstruction
import io.metacake.core.output.RenderingInstructionBundle
import io.metacake.core.output.system.OutputDevice
import io.metacake.core.process.ActionRecognizerPipe
import io.metacake.core.process.Bundle
import io.metacake.core.process.GameRunner
import io.metacake.core.process.state.EndState
import io.metacake.core.process.state.GameState
import io.metacake.core.process.state.UserState
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.nullValue
import static spock.util.matcher.HamcrestSupport.expect

class BootstrapperSpec extends Specification{
    InputDeviceName inputName = new InputDeviceName("input")
    OutputDeviceName outputName = new OutputDeviceName("output")

    def "Confirm bootstrap sequence order"() {
        setup:
        CakeWindow window = Spy(CakeWindow)

        InputDevice input = Mock(InputDevice)
        OutputDevice output = Mock(OutputDevice)

        Map<InputDeviceName,InputDevice> inputDevices = [(inputName): input]
        Map<OutputDeviceName,OutputDevice> outputDevices = [(outputName): output]

        GameState state = Mock(GameState)
        Bootstrapper bootstrapper = Spy(Bootstrapper, constructorArgs:[window, inputDevices, outputDevices,state])

        InputSystem inputSystem = Mock(InputSystem)
        OutputSystem outputSystem = Mock(OutputSystem)
        bootstrapper.bootstrapInputSystem() >> inputSystem
        bootstrapper.bootstrapOutputSystem() >> outputSystem

        when:
        GameRunner runner = bootstrapper.bootstrapSystem();

        then: input.bind(window)
        then: output.bind(window)
        then: bootstrapper.bootstrapInputSystem()
        then: bootstrapper.bootstrapOutputSystem()
        then: bootstrapper.bootstrapProcessLayer(inputSystem, outputSystem)
        then:
        inputSystem.startInputLoops()
        outputSystem.startOutputLoops()
    }

    @Timeout(10)
    def "did the shutdown occur sequentially and synchronously"() {
        setup:
        Semaphore lock = new Semaphore(1)
        Object rawWindow = new Object()
        CakeWindow window = Mock(CakeWindow)
        window.getRawWindow() >> {rawWindow}
        window.dispose() >> {
            rawWindow = null
            lock.release()
        }

        AtomicBoolean testFailed = new AtomicBoolean(false)

        InputDevice inputDevice = Mock(InputDevice)
        OutputDevice outputDevice = new OutputDevice() {
            CakeWindow w
            TimedLoopThread t

            void startOutputLoop() {
                lock.acquire()
                t = new TimedLoopThread({
                    try {
                        try { Thread.sleep(10) } catch (InterruptedException e) {}
                        (0..100000).each { w.getRawWindow().toString() }
                    } catch (Exception e) {
                       testFailed.set(true)
                       throw e
                    }
                })
                t.start()
            }
            OutputDeviceName name() {null}
            void render(Collection<RenderingInstruction> instructions) {}
            void shutdown() { t.requestStop() }
            void bind(CakeWindow cakeWindow) { this.w = cakeWindow }
        }

        GameState g = new UserState() {
            int i = 0
            Bundle tick(long delta, ActionRecognizerPipe pipe) {
                i += 1
                Bundle.getBundle().withState(i > 500 ? EndState.close() : this)
            }
        }

        def inputDevices = [(inputName): inputDevice]
        def outputDevices = [(outputName): outputDevice]
        Bootstrapper b = new Bootstrapper(window, inputDevices, outputDevices, g, 1)

        when:
        b.setupAndLaunchGame()
        lock.acquire()
        then:
        expect window.getRawWindow(), nullValue()
        expect testFailed.get(), is(false)//"Exception thrown from within render thread"
    }
}