package io.metacake.core.process;

import io.metacake.core.common.MilliTimer;
import io.metacake.core.input.Action;
import io.metacake.core.input.InputSystem;
import io.metacake.core.output.OutputSystem;

/**
 * @author florence
 * @author rpless
 */
public class GameRunner {
    InputSystem inputSystem;
    OutputSystem outputSystem;
    private boolean isRunning = false;

    public GameRunner(InputSystem inputSystem, OutputSystem outputSystem) {
        this.inputSystem = inputSystem;
        this.outputSystem = outputSystem;
    }

    public void mainLoop(GameState state, long interval) {
        isRunning = true;
        MilliTimer timer = new MilliTimer(interval);
        // TODO: excpetion handling
        while (isRunning) {
            outputSystem.addToRenderQueue(state);
            updateTriggers(state);
            updateRecognizers(state);
            state = state.tick();
            timer.block();
        }
    }

    public void stop(){
        isRunning = false;
    }

    private void updateTriggers(GameState s){
        if(s.shouldReplaceActionTriggers()) {
            inputSystem.setActionTrigges(s.getNewActionTriggers());
        }
    }

    private void updateRecognizers(GameState s){
        if(s.shouldClearActions()){
            inputSystem.clearActions();
        } else {
            // CONCERN: This might be two slow. maybe we need to redesign action->recognizer bindings.
            for (Action a : inputSystem.getAndCleanActions()) {
                for(ActionRecognizer r : s.getRecongizers()) {
                    r.actionOccured(a);
                }
            }
        }
    }
}
