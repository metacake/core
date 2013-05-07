package io.metacake.core.process;

import io.metacake.core.input.InputSystem;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.process.state.GameState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author florence
 * @author rpless
 */
public class GameRunnerTest {
    GameState s;
    InputSystem is;
    OutputSystem os;
    GameRunner system;

    @Before
    public void setup(){
        s = mock(GameState.class);
        is = mock(InputSystem.class);
        os = mock(OutputSystem.class);

        system = new GameRunner(is,os);
    }

    @Test
    public void stopActuallyStops() throws Exception{
    /*    final int loopTime = 5;
        Thread t = new Thread(){
            @Override
            public void run(){
                system.mainLoop(s,loopTime);
            }
        };

        t.start();

        Thread.sleep(5);
        //FACK, this calls system.exit()
        system.stop();

        Thread.sleep(5);

        assertFalse(t.isAlive());
        */
    }
}
