package builder.entities.tiles;

import builder.ui.SpriteGallery;
import builder.entities.resources.Ore;

/**
 * Represents an ore vein tile that contains an Ore resource.
 */
public class OreVein extends Tile {
    private Ore ore;

    /**
     * Constructs an ore vein tile at the given position and places an Ore on it.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     */
    public OreVein(int x, int y) {
        super(x, y, SpriteGallery.field);
        this.ore = new Ore(x, y);  
        placeOn(ore);
    }

    /**
     * Gets the Ore resource contained in this vein.
     *
     * @return the ore entity
     */
    public Ore getOre() {
        return ore;
    }
}
