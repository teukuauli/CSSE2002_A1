package builder.entities.tiles;

import builder.GameState;
import builder.entities.resources.Cabbage;
import builder.inventory.items.Bucket;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;

/**
 * Represents a dirt tile that can be tilled with a hoe and used for planting crops.
 */
public class Dirt extends Tile {
    private boolean tilled = false;

    /**
     * Constructs a dirt tile at the given position.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     */
    public Dirt(int x, int y) {
        super(x, y, SpriteGallery.field);
    }

    /**
     * Checks whether this dirt tile is tilled.
     *
     * @return true if the tile is tilled, false otherwise
     */
    public boolean isTilled() {
        return tilled;
    }

    /**
     * Tills this dirt tile, changing its appearance to the tilled sprite.
     */
    public void till() {
        this.tilled = true;
        setArt(SpriteGallery.tilled);
    }

    /**
     * Uses the tile with the current item in the player's inventory.
     * Hoe tills the dirt, and a bucket can plant a cabbage if conditions are met.
     *
     * @param state the current engine state
     * @param game  the current game state
     */
    @Override
    public void use(EngineState state, GameState game) {
        super.use(state, game);

        if (game.getInventory().getHolding() instanceof Hoe) {
            till();
        } else if (game.getInventory().getHolding() instanceof Bucket
                && getStackedEntities().isEmpty()
                && tilled
                && game.getInventory().getCoins() >= Cabbage.COST) {

            game.getInventory().addCoins(-Cabbage.COST);
            placeOn(new Cabbage(getX(), getY()));
        }
    }
}
