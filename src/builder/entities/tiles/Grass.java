package builder.entities.tiles;

import builder.GameState;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;

/**
 * Represents a grass tile in the game.
 * Grass can be removed with a hoe and replaced with a dirt tile.
 */
public class Grass extends Tile {

    /**
     * Constructs a grass tile at the given position.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     */
    public Grass(int x, int y) {
        super(x, y, SpriteGallery.grass);
    }

    /**
     * Uses the grass tile with the current item in the player's inventory.
     * If holding a hoe, the grass is removed and replaced with dirt.
     *
     * @param state the current engine state
     * @param game  the current game state
     */
    @Override
    public void use(EngineState state, GameState game) {
        super.use(state, game);

        if (game.getInventory().getHolding() instanceof Hoe && !isMarkedForRemoval()) {
            markForRemoval();
            game.getWorld().place(new Dirt(getX(), getY()));
        }
    }
}
