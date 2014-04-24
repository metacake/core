package io.metacake.core.output

import spock.lang.Shared
import spock.lang.Specification

class InspectingRenderingBundleSpec extends Specification {
    @Shared OutputDeviceName testOutputDeviceName = new OutputDeviceName()
    RenderingInstruction mockInstruction = Mock RenderingInstruction
    InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle()

    def "Bundle is done when it is full"() {
        setup: bundle.add(testOutputDeviceName, mockInstruction)
        when: runBundleOnNull(bundle)
        then: bundle.isDone()
        when: bundle.reset()
        then: !bundle.isDone()
    }

    def "A nonempty Bundle is not done"() {
        when: bundle.add(testOutputDeviceName, mockInstruction)
        then: !bundle.isDone()
    }

    def "An empty bundle is done"() {
        expect: !bundle.isDone()
        when: bundle.getInstructions()
        then: bundle.isDone()
    }

    def "A bundle can be reused after a reset"() {
        when:
        bundle.getInstructions()
        bundle.reset()
        bundle.getInstructions()
        then: notThrown Exception
    }

    def "Cannot reuse a bundle if it has not been reset"() {
        when:
        bundle.getInstructions()
        bundle.getInstructions()
        then: thrown IllegalStateException
    }

    def runBundleOnNull(RenderingInstructionBundle bundle) {
        bundle.iterator().toList().each { entry ->
            entry.getValue().each { it.render null }
        }
    }
}