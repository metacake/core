package io.metacake.core.process.state;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author florence
 * @author rpless
 */
public class EndStateTest {
    @Test
    public void endWithDoesNotCloseWindow(){
        EndState e = (EndState)EndState.endWith(mock(GameState.class));
        assertFalse(e.shouldCloseWindow());
    }

    @Test
    public void CloseWithDoesCloseWindow(){
        EndState e = (EndState)EndState.closeWith(mock(GameState.class));
        assertTrue(e.shouldCloseWindow());
    }
}
