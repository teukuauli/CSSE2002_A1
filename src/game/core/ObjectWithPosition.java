package game.core;

/**
 * Base class for all game objects that have a position in 2D space.
 * Provides access to x and y coordinates.
 */
public abstract  class ObjectWithPosition implements SpaceObject {
    /**
     * The x coordinate of the Object
     */
    protected int x;

    /**
     * The y coordinate of the Object
     */
    protected int y;

    /**
     * Creates a movable and interactive object at the given coordinates.
     *
     */
    public ObjectWithPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the SpaceObject, where 0 represents the left-most space
     * with positive numbers extending to the right.
     *
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the SpaceObject, where 0 represents the top-most space
     * with positive numbers extending downwards.
     *
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Implementation of the tick method from the Tickable interface.
     * This method should be overridden by subclasses to provide specific behavior.
     *
     */
    @Override
    public void abstract tick(int tick) {
        // Default implementation, should be overridden by subclasses
    }
}