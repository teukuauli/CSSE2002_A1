package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents an enemy in the game.
 * Enemies move downward and can damage the player's ship upon collision.
 */
public class Enemy extends DescendingEnemy {
    /**
     * Creates an enemy at the given coordinate.
     *
     */
    public Enemy(int x, int y) {
        super(x, y);
    }

    /**
     * Updates the enemy's position based on the current game tick.
     *
     */
    @Override
    public void tick(int tick) {
        // Movement logic for enemy
        // For a basic implementation, we could make it move downward
        this.y += 1;
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     *
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("👾", "assets/enemy.png");
    }
}