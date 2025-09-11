package builder.entities;

import builder.GameState;
import engine.EngineState;

/**
 * Represents an entity in the game that can be used.
 * Classes implementing this interface should define how the entity is used.
 */
public interface Usable {
    /**
     * Defines the behavior when this entity is used.
     *
     * @param state the current engine state
     * @param game  the current game state
     */
    void use(EngineState state, GameState game);
}
