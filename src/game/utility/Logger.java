package game.utility;

/**
 * Functional interface for logging messages in the game.
 */
@FunctionalInterface
public interface Logger {
    /**
     * Logs a message.
     *
     * @param text the message to log
     */
    void log(String text);

}