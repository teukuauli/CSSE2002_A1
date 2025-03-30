package game.core;

import game.ui.ObjectGraphic;

/**
 * A power-up that provides shield protection to the player's ship.
 * Increases the player's score when collected.
 */
public class ShieldPowerUp extends PowerUp {
    /**
     * Creates a new ShieldPowerUp with the given coordinates.
     *
     */
    public ShieldPowerUp(int x, int y) {
        super(x, y);
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     *
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("💠", "assets/shield.png");
    }

    /**
     * Applies the shield effect to the ship, increasing the score by 50.
     * Sends "Shield activated! Score increased by 50." to standard output (using System.out.println()).
     *
     */
    @Override
    public void applyEffect(Ship ship) {
        ship.addScore(50);
        System.out.println("Shield activated! Score increased by 50.");
    }
}