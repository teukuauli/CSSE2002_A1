package game.exceptions;

/**
 * Exception thrown when a game object attempts to move beyond the game boundaries.
 */
public class BoundaryExceededException extends Exception {
    /**
     * Creates a new BoundaryExceeded Exception with the provided message.
     *
     */
    public BoundaryExceededException(String message) {
        super(message);
    }
}
