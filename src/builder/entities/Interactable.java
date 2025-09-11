package builder.entities;

import builder.GameState;
import engine.EngineState;

/**
 * Represents an entity that can be interacted with in the game.
 * Classes implementing this interface should define what happens
 * when the entity is interacted with.
 */
public interface Interactable {
    /**
     * Defines the action to be performed when the entity is interacted with.
     *
     * @param state the current state of the engine
     * @param game  the current state of the game
     */
    void interact(EngineState state, GameState game);
}
