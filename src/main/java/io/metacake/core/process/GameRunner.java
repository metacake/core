package io.metacake.core.process;

import io.metacake.core.common.Disposable;
import io.metacake.core.common.MilliTimer;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputSystem;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.process.state.EndState;
import io.metacake.core.process.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class run is the main execution loop of the game
 *
 * @author florence
 * @author rpless
 */
public class GameRunner {
    private static Logger logger = LoggerFactory.getLogger(GameRunner.class);

    private InputSystem inputSystem;
    private OutputSystem outputSystem;
    private boolean isRunning = false;
    private boolean isStopped = false;
    private boolean isWindowDisposed = false;
    private CakeWindow window;

    public GameRunner(InputSystem inputSystem, OutputSystem outputSystem, CakeWindow window) {
        this.inputSystem = inputSystem;
        this.outputSystem = outputSystem;
        this.window = window;
        window.addCloseObserver(this::stop);
    }

    /**
     * Execute the main game loop in the current thread. This method returns after #stop() has been called.
     * <p>
     * The game loop will attempt to put {@code interval} milliseconds between the start of each game loop.
     * This will fail if the GameState#tick takes more than {@code interval} milliseconds to run
     * When #stop is called the function will terminate after the current state finishes its tick cycle
     * @param state the initial state of the game
     * @param interval the number of milliseconds requested to be between the start of each loop.
     */
    public void mainLoop(GameState state, long interval) {
        logger.info("starting main loop");
        isRunning = true;
        MilliTimer timer = new MilliTimer(interval);
        try {
            while (isRunning && !state.isGameOver()) {
                outputSystem.addToRenderQueue(state);
                updateTriggers(state);
                timer.update();
                state = state.tick();
                timer.block();
            }
        } catch (Exception e) {
            logger.error("Error in main loop, terminating execution",e);
        } finally {
            end(state);
        }
    }

    /**
     * Tell the main game loop to stop.
     * The main event loop will try to close the window if this method is called.
     *
     * If the main game loop is not running, this will shut down the window.
     */
    public void stop() {
        synchronized (this) {
            if(isRunning) {
                isRunning = false;
            } else if (isStopped && !isWindowDisposed) {
                logger.info("Disposing of window because loop had already stopped");
                window.dispose();
                isWindowDisposed = true;
            }
        }
    }

    /**
     * Update any ActionTriggers that need to be updated.
     * <p>
     * The GameState will request that the GameRunner replace its ActionTriggers by returning true for
     * shouldReplaceActionTriggers().
     * </p>
     * @param s The current state
     */
    private void updateTriggers(GameState s){
        if(s.shouldReplaceActionTriggers()) {
            inputSystem.releaseActionTriggers();
            s.replaceActionTriggers().forEach(inputSystem::bindActionTrigger);
        }
    }

    /**
     * Render the last state and end the game
     * @param state the last state
     */
    private void end(GameState state){
        logger.info("beginning system shutdown");
        safelyDispose(outputSystem);
        safelyDispose(inputSystem);
        if(shouldCloseWindow(state)) {
            safelyDispose(window);
            isWindowDisposed = true;
        }
        isRunning = false;
        isStopped = true;
        logger.info("shutdown complete");
    }

    private void safelyDispose(Disposable d) {
        try {
            d.dispose();
        } catch (Exception e) {
            logger.error("error in disposing " + d.getClass().getSimpleName(), e);
        }
    }

    /**
     * Should the window be closed on shutdown?
     * @param state the last state
     * @return if the window should be closed
     */
    private boolean shouldCloseWindow(GameState state) {
        return !isRunning
                || (state instanceof EndState && ((EndState) state).shouldCloseWindow());
    }

}