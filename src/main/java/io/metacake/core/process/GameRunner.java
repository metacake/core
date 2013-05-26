package io.metacake.core.process;

import io.metacake.core.common.MilliTimer;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;
import io.metacake.core.input.ActionTrigger;
import io.metacake.core.input.InputSystem;
import io.metacake.core.input.system.InputDevice;
import io.metacake.core.output.OutputSystem;
import io.metacake.core.process.state.EndState;
import io.metacake.core.process.state.GameState;

/**
 * This class run is the main execution loop of the game
 *
 * @author florence
 * @author rpless
 */
//TODO : This should have its own interface and be passed into the Bootstrapper
public class GameRunner {
    private InputSystem inputSystem;
    private OutputSystem outputSystem;
    private boolean isRunning = false;
    private CakeWindow window;

    public GameRunner(InputSystem inputSystem, OutputSystem outputSystem, CakeWindow window) {
        this.inputSystem = inputSystem;
        this.outputSystem = outputSystem;
        this.window = window;
        window.addCloseObserver(new CloseObserver() {
            @Override
            public void onClose() {
                stop();
            }
        });
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
        isRunning = true;
        MilliTimer timer = new MilliTimer(interval);
        // TODO: exception handling
        while (isRunning && !state.isGameOver()) {
            outputSystem.addToRenderQueue(state);
            updateTriggers(state);
            timer.update();
            state = state.tick();
            timer.block();
        }
        end(state);
    }

    /**
     * Tell the main game loop to stop. If the main game loop is not running, this has no effect.
     */
    public void stop(){
        isRunning = false;
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
            for(ActionTrigger a : s.replaceActionTriggers()){
                inputSystem.bindActionTrigger(a.bindingDevice(),a);
            }
        }
    }

    /**
     * Render the last state and end the game
     * @param state the last state
     */
    private void end(GameState state){
        outputSystem.addToRenderQueue(state);
        outputSystem.shutdown();
        inputSystem.shutdown();
        if(state instanceof EndState && ((EndState)state).shouldCloseWindow()){
            window.close();
        }
    }

}