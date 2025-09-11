package builder.player;

/**
 * Represents a player character in the game.
 * Provides access to position and base damage.
 */
public interface Player {

    /**
     * Gets the player's current x-coordinate.
     *
     * @return the x-coordinate
     */
    int getX();

    /**
     * Gets the player's current y-coordinate.
     *
     * @return the y-coordinate
     */
    int getY();

    /**
     * Gets the base damage value dealt by the player.
     *
     * @return the damage value
     */
    int getDamage();
}
