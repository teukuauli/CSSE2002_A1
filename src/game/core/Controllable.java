package game.core;

import game.GameModel;
import game.ui.ObjectGraphic;
import game.exceptions.BoundaryExceededException;
import game.utility.Direction;

/**
 * Abstract class representing game objects that can be controlled by the player.
 * Provides movement functionality and boundary checking.
 */
public abstract class Controllable extends ObjectWithPosition {
    /**
     * Creates a controllable object at the given coordinates.
     *
     * @param x the given x coordinate
     * @param y the given y coordinate
     */
    public Controllable(int x, int y) {
        super(x, y);
    }

    /**
     * Moves the Controllable by one in the direction given.
     * Throws BoundaryExceededException if the Controllable is attempting to move outside the game boundaries.
     * A controllable is considered outside the game boundaries if they are at:
     * x-coordinate >= GAME_WIDTH
     * x-coordinate < 0
     * y-coordinate >= GAME_HEIGHT
     * y-coordinate < 0
     *
     * Argument given to the exception is "Cannot move {up/down/left/right}. Out of bounds!"
     * depending on the direction.
     *
     */
    public void move(Direction direction) throws BoundaryExceededException {
        // Calculate the new position based on the direction
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
        }

        // Check if the new position is outside the game boundaries
        if (newX >= GameModel.GAME_WIDTH) {
            throw new BoundaryExceededException("Cannot move right. Out of bounds!");
        }
        if (newX < 0) {
            throw new BoundaryExceededException("Cannot move left. Out of bounds!");
        }
        if (newY >= GameModel.GAME_HEIGHT) {
            throw new BoundaryExceededException("Cannot move down. Out of bounds!");
        }
        if (newY < 0) {
            throw new BoundaryExceededException("Cannot move up. Out of bounds!");
        }

        // Update the position if no boundary is exceeded
        x = newX;
        y = newY;
    }

    /**
     * Updates the controllable object based on the current game tick.
     * This method is inherited from Tickable via ObjectWithPosition.
     *
     * @param tick the current game tick
     */
    @Override
    public void tick(int tick) {
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * This method must be implemented by subclasses.
     *
     * @return the appropriate new ObjectGraphic.
     */
    @Override
    public abstract ObjectGraphic render();
}