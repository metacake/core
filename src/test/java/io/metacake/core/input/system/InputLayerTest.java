package io.metacake.core.input.system;

import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputDeviceName;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author florence
 * @author rpless
 */
public class InputLayerTest {

    InputDeviceName n1 = new InputDeviceName("n1");
    InputDeviceName n2 = new InputDeviceName("n2");
    InputDevice i1;
    InputDevice i2;
    InputLayer system;

    @Before
    public void setup(){
        i1 = mock(InputDevice.class);
        i2 = mock(InputDevice.class);

        Map<InputDeviceName,InputDevice> theMap = new HashMap<>();

        theMap.put(n1,i1);
        theMap.put(n2,i2);

        system = new InputLayer(theMap);
    }

    @Test
    public void bindActionTriggerCallsCorrectMethod(){
        ActionTrigger a = mock(ActionTrigger.class);
        when(a.bindingDevice()).thenReturn(n1);

        system.bindActionTrigger(a);

        verify(i1,times(1)).addTrigger(eq(a));
    }
}
