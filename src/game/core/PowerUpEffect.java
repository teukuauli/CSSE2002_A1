package game.core;

/**
 * Interface for objects that can apply effects to the player's ship.
 */
public interface PowerUpEffect {
    /**
     * Applies this power-up's effect to the specified ship.
     *
     * @param ship the ship to apply the effect to
     */
    void applyEffect(Ship ship);
}