package io.metacake.core.integration

import io.metacake.core.BootstrapBuilder
import io.metacake.core.common.window.CakeWindow
import io.metacake.core.input.ActionTrigger
import io.metacake.core.input.InputDeviceName
import io.metacake.core.input.system.InputDevice
import io.metacake.core.output.OutputDeviceName
import io.metacake.core.output.RenderingInstruction
import io.metacake.core.output.RenderingInstructionBundle
import io.metacake.core.output.system.OutputDevice
import io.metacake.core.process.ActionRecognizer
import io.metacake.core.process.ActionRecognizerName
import io.metacake.core.process.ActionRecognizerPipe
import io.metacake.core.process.RecognizerBucketName
import io.metacake.core.process.Transition
import io.metacake.core.process.state.EndState
import io.metacake.core.process.state.GameState
import spock.lang.Specification
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.equalTo
import static spock.util.matcher.HamcrestSupport.expect

class FullSystemSpec extends Specification {
    def inputName = new InputDeviceName()
    def outputName = new OutputDeviceName()
    def bucket = new RecognizerBucketName()
    def "test system"() {
        setup:
        def w = new Window()
        def i = new Input()
        def o = new Output()
        def s = new State(i)
        def trig = new Trigger()
        def r = new Recognizer()
        trig.addRecog(r)
        def t = Transition.to(s).withTriggers(trig).withBucket(bucket,r)
        BootstrapBuilder b = new BootstrapBuilder()
        b.withWindow(w)
                .withInputDevices(i)
                .withOutputDevices(o)
                .withInitialTransition(t)
        when: b.createAndLaunch()
        then:
        expect w.disposed, is(true)
        expect i.started, is(true)
        expect i.disposed, is(true)
        expect i.bound, is(w)
        expect o.started, is(true)
        expect o.disposed, is(true)
        expect o.bound, is(w)
        expect s.count, is(5)
        expect o.values, equalTo([1,2,3,4])

    }
    class State implements GameState {
        public State(Input d) {
            i = d
        }
        Input i
        def count = 0
        @Override
        Transition tick(long delta, ActionRecognizerPipe pipe) {
            count += 1
            i.newInput(count)
            i.newInput(count)
            RenderingInstructionBundle b = new RenderingInstructionBundle()
            pipe.emptyBucket(bucket).each() {_,r ->
                b.add(outputName,new Render(r.triggerWeight()))
            }
            if(count == 5) {
                Transition.to(EndState.close())
            } else {
                Transition.to(this).withInstructions(b)
            }
        }
    }
    class Window extends CakeWindow {
        boolean disposed = false
        @Override
        int getX() {
            return 0
        }

        @Override
        int getY() {
            return 0
        }

        @Override
        int getWidth() {
            return 0
        }

        @Override
        int getHeight() {
            return 0
        }

        @Override
        Object getRawWindow() {
            return null
        }

        @Override
        void dispose() {
            disposed = true
        }
    }
    class Input implements InputDevice {
        def started = false
        def disposed = false
        def bound = false
        def triggers = []

        def newInput(Integer i) {
            triggers.each() {
                if(it.isTriggeredBy(i)) {
                    it.give(i)
                }
            }
        }

        @Override
        InputDeviceName name() {
            return inputName
        }

        @Override
        void shutdown() {
            disposed = true
        }

        @Override
        void bind(CakeWindow w) {
            bound = w
        }

        @Override
        void addTrigger(ActionTrigger trigger) {
            triggers.add(trigger)
        }

        @Override
        void releaseTriggers() {
            triggers = []
        }

        @Override
        void startInputLoop() {
            started = true
        }
    }
    class Trigger implements ActionTrigger<Integer> {

        def trigger = false
        def recognizers = []
        @Override
        boolean isTriggeredBy(Integer event) {
            trigger = !trigger
            return trigger
        }

        def give(Integer i) {
            recognizers.each(){it.give(i)}
        }
        def addRecog(Recognizer r) {
            recognizers.add r
        }

        @Override
        InputDeviceName bindingDevice() {
            return inputName
        }
    }
    class Recognizer implements ActionRecognizer {
        def name = new ActionRecognizerName()
        def value = false
        @Override
        ActionRecognizerName getName() {
            return name
        }

        def give(Integer i) {
            value = i
        }

        @Override
        boolean wasTriggered() {
            if(value == false){false}else{true}
        }

        @Override
        int triggerWeight() {
            def v = value
            value = false
            return v
        }

        @Override
        void forgetActions() {
            value = false
        }
    }
    class Output implements OutputDevice {
        def started = false
        def disposed = false
        def bound = false
        def values = []

        void addValue(Integer i) {
            values.add(i)
        }

        @Override
        OutputDeviceName name() {
            return outputName
        }

        @Override
        void render(Collection<RenderingInstruction> instructions) {
            instructions.each(){it.render(this)}
        }

        @Override
        void startOutputLoop() {
            this.started = true
        }

        @Override
        void shutdown() {
            this.disposed = true
        }

        @Override
        void bind(CakeWindow cakeWindow) {
            bound = cakeWindow
        }
    }
    class Render implements RenderingInstruction<Output> {
        public Render(Integer i) {
            value = i
        }
        def value
        @Override
        void render(Output context) {
            context.addValue(value)
        }
    }
}
