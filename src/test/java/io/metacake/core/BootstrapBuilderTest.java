package io.metacake.core;

import io.metacake.core.BootstrapBuilder;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.process.state.GameState;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class BootstrapBuilderTest {

    @Test(expected = RuntimeException.class)
    public void testCreationWithNothing() {
        BootstrapBuilder builder = new BootstrapBuilder();
        builder.createBootstrap();
    }

    @Test(expected = RuntimeException.class)
    public void testCreationWithoutWindow() {
        GameState state = mock(GameState.class);
        BootstrapBuilder builder = new BootstrapBuilder();
        builder.withInitialState(state).createBootstrap();
    }

    @Test(expected = RuntimeException.class)
    public void testCreationWithoutState() {
        CakeWindow window = mock(CakeWindow.class);
        BootstrapBuilder builder = new BootstrapBuilder();
        builder.withWindow(window).createBootstrap();
    }
}