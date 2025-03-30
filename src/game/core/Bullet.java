package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents a bullet projectile in the game.
 * Bullets move upward and can destroy enemies upon collision.
 */
public class Bullet extends ObjectWithPosition {
    /**
     * Creates a bullet at the given coordinates.
     *
     */
    public Bullet(int x, int y) {
        super(x, y);
    }

    /**
     * Moves Bullet upwards by one, regardless of what the provided game tick is.
     *
     */
    @Override
    public void tick(int tick) {
        // Move bullet upwards by one
        this.y -= 1;
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     *
     * @return the appropriate new ObjectGraphic
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🔺", "assets/bullet.png");
    }
}