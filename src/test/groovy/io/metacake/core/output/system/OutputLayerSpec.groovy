package io.metacake.core.output.system

import io.metacake.core.output.OutputDeviceName
import io.metacake.core.output.Renderable
import io.metacake.core.output.RenderingInstruction
import io.metacake.core.output.RenderingInstructionBundle
import spock.lang.Shared
import spock.lang.Specification

class OutputLayerSpec extends Specification {
    @Shared OutputDeviceName name1 = new OutputDeviceName()
    @Shared OutputDeviceName name2 = new OutputDeviceName()

    OutputDevice outputDevice1 = Mock OutputDevice
    OutputDevice outputDevice2 = Mock OutputDevice
    OutputLayer system

    def setup() {
        Map devices = [(name1):outputDevice1, (name2):outputDevice2]
        system = new OutputLayer(devices)
    }

    def "Start output loops touches all devices once"() {
        when: system.startOutputLoops()
        then:
        1 * outputDevice1.startOutputLoop()
        1 * outputDevice2.startOutputLoop()
    }

    def "Add to Render Queue Passes Proper Values"() {
        setup:
        RenderingInstructionBundle bundle = new RenderingInstructionBundle()
        bundle.add(name1, Mock(RenderingInstruction), Mock(RenderingInstruction))
        Renderable renderable = Mock Renderable
        renderable.renderingInstructions() >> bundle

        when: system.addToRenderQueue(renderable)
        then:
        0 * outputDevice2.render([])

    }
}