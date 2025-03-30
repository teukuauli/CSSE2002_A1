package game.core;

import game.ui.ObjectGraphic;
/**
 * Represents an asteroid in the game.
 * Asteroids move downward and can damage the player's ship upon collision.
 */
public class Asteroid extends DescendingEnemy {
    /**
     * Creates a new Asteroid instance with coordinates x and y.
     *
     */
    public Asteroid(int x, int y) {
        super(x, y);
    }

    /**
     * Updates the asteroid's position based on the current game tick.
     */
    @Override
    public void tick(int tick) {
        // Movement logic for asteroid
        // Basic implementation: move downward
        this.y += 1;
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     *
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🌑", "assets/asteroid.png");
    }
}