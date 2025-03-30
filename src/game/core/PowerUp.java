package game.core;

import game.ui.ObjectGraphic;

/**
 * Abstract base class for power-ups in the game.
 * Power-ups provide beneficial effects to the player's ship upon collection.
 */
public abstract class PowerUp extends ObjectWithPosition implements PowerUpEffect {
    /**
     * Creates a new PowerUp with the given coordinate.
     *
     */
    public PowerUp(int x, int y) {
        super(x, y);
    }

    /**
     * As PowerUps have no tick-dependent behaviour, this method should be left blank.
     *
     */
    @Override
    public void tick(int tick) {
        // PowerUps have no tick-dependent behavior
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * This method must be implemented by subclasses.
     *
     */
    @Override
    public abstract ObjectGraphic render();
}