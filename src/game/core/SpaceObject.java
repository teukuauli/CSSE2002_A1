package game.core;

import game.ui.ObjectGraphic;
import game.ui.Tickable;

/**
 * Interface for all objects that exist in the game space.
 * Provides methods for getting coordinates and rendering.
 */
public interface SpaceObject extends Tickable {
    /**
     * Returns the x coordinate of the SpaceObject.
     *
     */
    int getX();

    /**
     * Returns the y coordinate of the SpaceObject.
     *
     */
    int getY();

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     *
     */
    ObjectGraphic render();
}