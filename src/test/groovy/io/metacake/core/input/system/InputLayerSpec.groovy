package io.metacake.core.input.system

import io.metacake.core.input.ActionTrigger
import io.metacake.core.input.InputDeviceName
import spock.lang.Shared
import spock.lang.Specification

class InputLayerSpec extends Specification {
    @Shared InputDeviceName name1 = new InputDeviceName('n1')
    @Shared InputDeviceName name2 = new InputDeviceName('n2')

    InputLayer layer

    def "A Bind to an ActionTrigger calls the correct method"() {
        setup:
        InputDevice inputDevice1 = Mock InputDevice
        InputDevice inputDevice2 = Mock InputDevice
        layer = [(name1): inputDevice1, (name2): inputDevice2]
        ActionTrigger trigger = Mock ActionTrigger
        trigger.bindingDevice() >> name1

        when: layer.bindActionTrigger(trigger)
        then: 1 * inputDevice1.addTrigger(trigger)
    }
}