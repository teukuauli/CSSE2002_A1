package builder.world;

import builder.entities.tiles.Tile;
import engine.renderer.Dimensions;
import java.util.List;

/**
 * Represents a world that manages and provides access to tiles.
 */
public interface World {

    /**
     * Gets all tiles located at the given pixel coordinates.
     *
     * @param x          the x-coordinate in pixels
     * @param y          the y-coordinate in pixels
     * @param dimensions the dimensions used to convert pixels to tile positions
     * @return a list of tiles at the given position
     */
    List<Tile> tilesAtPosition(int x, int y, Dimensions dimensions);

    /**
     * Returns all tiles contained in the world.
     *
     * @return a list of all tiles
     */
    List<Tile> allTiles();

    /**
     * Places a tile in the world.
     *
     * @param tile the tile to place
     */
    void place(Tile tile);
}
