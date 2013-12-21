package io.metacake.core.process;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.common.Disposable;
import io.metacake.core.common.MilliTimer;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputSystem;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.process.state.EndState;
import io.metacake.core.process.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

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

    private CustomizableMap<ActionRecognizerName, ActionRecognizer> recognizers = new CustomizableMap<>(new HashMap<>());

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
                updateRecognizers(state);
                state = state.tick(timer.update(), recognizers);
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
                safelyDispose(window);
                isWindowDisposed = true;
            }
        }
    }

    /**
     * Update any {@link io.metacake.core.input.ActionTrigger} that need to be updated.
     * <p>
     * The GameState will request that the GameRunner replace its ActionTriggers by returning true for
     * shouldReplaceActionTriggers().
     * </p>
     * @param state The current state
     */
    private void updateTriggers(GameState state) {
        Optional<Collection<ActionTrigger>> triggers = state.replaceActionTriggers();
        if(triggers.isPresent()) {
            inputSystem.releaseActionTriggers();
            triggers.get().forEach(inputSystem::bindActionTrigger);
        }
    }

    /**
     * Update any {@link io.metacake.core.process.ActionRecognizer}s that need to be updated.
     * @param state The current state
     */
    private void updateRecognizers(GameState state) {
        Optional<Collection<ActionRecognizer>> replaceRecognizers = state.replaceActionRecognizers();
        if(replaceRecognizers.isPresent()) {
            recognizers.clear();
            replaceRecognizers.get().forEach(recognizer -> recognizers.put(recognizer.getName(), recognizer));
        }
    }

    /**
     * Render the last state and end the game
     * @param state the last state
     */
    private void end(GameState state) {
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
        return !isRunning || (state instanceof EndState && ((EndState) state).shouldCloseWindow());
    }
}