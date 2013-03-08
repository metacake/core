package io.metacake.core.common;

/**
 * This class is meant to be used as extendable enumerations
 * <p></p>
 * for example :
 * public class MovementAction extends Symbol {
 *   public static final GO_UP = new MovementAction();
 *   public static final GO_DOWN = new MovementAction();
 *   public static final GO_LEFT = new MovementAction();
 *   public static final GO_RIGHT = new MovementAction();
 * }
 *
 *
 * @author florence
 * @author rpless
 */
public class Symbol {
    @Override
    public final boolean equals(Object that) {
        return this == that;
    }
    @Override
    public final int hashCode(){
        return super.hashCode();
    }
}
