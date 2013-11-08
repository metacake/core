package io.metacake.core.output

import spock.lang.Shared
import spock.lang.Specification

class RenderingInstructionBundleSpec extends Specification {

    @Shared OutputDeviceName testOutputDeviceName = new OutputDeviceName()
    @Shared RenderingInstruction one = Mock RenderingInstruction
    @Shared RenderingInstruction two = Mock RenderingInstruction
    @Shared RenderingInstruction three = Mock RenderingInstruction

    RenderingInstructionBundle bundle = new RenderingInstructionBundle()

    def "A Rendering Bundle starts empty"() {
        expect: bundle.getInstructions().isEmpty()
    }

    def "Can add and remove from a rendering bundle"() {
        setup:
        bundle.add testOutputDeviceName, one

        expect:
        bundle.getInstructions().size() == 1
        bundle.getInstructions().get(testOutputDeviceName).size() == 1
        bundle.getInstructions().get(testOutputDeviceName).get(0) == one
    }

    def "Can add multiple Instructions"() {
        setup:
        bundle.add testOutputDeviceName, one, two, three

        expect:
        bundle.getInstructions().size() == 1
        bundle.getInstructions().get(testOutputDeviceName).size() == 3
    }

    def "Order is maintained on add"() {
        setup:
        bundle.add testOutputDeviceName, one, two, three

        expect:
        bundle.getInstructions().get(testOutputDeviceName).get(0) == one
        bundle.getInstructions().get(testOutputDeviceName).get(1) == two
        bundle.getInstructions().get(testOutputDeviceName).get(2) == three
    }
}