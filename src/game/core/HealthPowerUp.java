package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents a health power-up in the game.
 * This class extends PowerUp.
 */
public class HealthPowerUp extends PowerUp {
    /**
     * Creates a health power-up at the given coordinates
     *
     */
    public HealthPowerUp(int x, int y) {
        super(x, y);
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     *
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("❤", "assets/health.png");
    }

    /**
     * Applies the health effect to the ship, healing it for 20 health.
     * Sends "Health restored by 20!" to standard output (using System.out.println()).
     *
     */
    public void applyEffect(Ship ship) {
        ship.heal(20);
        System.out.println("Health restored by 20!");
    }
}