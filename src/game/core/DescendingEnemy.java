package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents an enemy that moves downward at a slower pace.
 * Only moves when the game tick is a multiple of 10.
 */
public abstract class DescendingEnemy extends ObjectWithPosition {
    /**
     * Creates a movable and interactive object at the given coordinates that moves downwards.
     *
     */
    public DescendingEnemy(int x, int y) {
        super(x, y);
    }

    /**
     * Moves the DescendingEnemy downwards by one if the given tick is a multiple of 10.
     *
     */
    @Override
    public void tick(int tick) {
        // Only move downward when the tick is a multiple of 10
        if (tick % 10 == 0) {
            this.y += 1;
        }
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * This method must be implemented by subclasses.
     *
     */
    @Override
    public abstract ObjectGraphic render();
}