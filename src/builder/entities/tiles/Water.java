package builder.entities.tiles;

import builder.ui.SpriteGallery;

/**
 * Represents a water tile in the game.
 * Water tiles cannot be walked through.
 */
public class Water extends Tile {

    /**
     * Constructs a water tile at the given position.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     */
    public Water(int x, int y) {
        super(x, y, SpriteGallery.water);
    }

    /**
     * Returns whether this tile can be walked through.
     * For water tiles, this is always false.
     *
     * @return false, since water is not walkable
     */
    @Override
    public boolean canWalkThrough() {
        return false;
    }
}
